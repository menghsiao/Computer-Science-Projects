/*
 * Assignment 1 for CS 4348
 * Programmer: Meng Hsiao
 * NetID: mxh126730
 * Description:  Using fork() to create two child process and use pipe() for
 * communication between child and parent processes.  Second child process
 * is to list the current working directory using the execve() function.
 */


#include<unistd.h>
#include<stdlib.h>
#include<stdio.h>
#include<stdbool.h>
#include<string.h>
#include<math.h>




int main() {
	const int NUM_OF_CHILDREN = 2;  // 2 child process will be created
	int num, res;
	int cow = 10;
	pid_t pid;
	int status;
	int pfd[2];
	char *const parmList[] = {"ls","./", NULL};
	char *const envParms[] = {NULL};

	// create a pipe
	if(pipe(pfd) == -1)
	{
		perror("pipe failed");
		exit(EXIT_FAILURE);
	}

	// fork two child process
	int childrenCount = 1;
	for(; childrenCount <= NUM_OF_CHILDREN; childrenCount++) {


		pid = fork();

		if( pid == -1 ) {
			perror("fork failed");
			break;
		}
		else if ( pid == 0 ){
			// child process
			break;
		}

	}

	// child process
	if(pid == 0) {
		if(childrenCount == 2) {
			//second child process

			printf("\nInside Child Process# #%d\n", childrenCount);
			printf("Remaining code is replaced by execve()\n");
			execve("/bin/ls", parmList, envParms);
			exit(1);
		}
		else {
			// first child process
			close(pfd[0]);  // close the read file descriptor
			printf("Inside Child Process# #%d\n", childrenCount);

			// write the initial value of cow to pipe
			if( write(pfd[1], &cow, sizeof(cow)) == -1)
			{
				perror("Write to pipe failed");
				exit(EXIT_FAILURE);
			}

			cow = 1000;  // change the value to 1000 in the child process

			// write the new value cow in the child process to pipe
			if( write(pfd[1], &cow, sizeof(cow)) == -1)
			{
				perror("Write to pipe failed");
				exit(EXIT_FAILURE);
			}

			close(pfd[1]);  // close the write file descriptor
			exit(1);

		}
	}

	else {
		// parent process
		close(pfd[1]);  // close the write file descriptor

		wait(&status);

		printf("\nInside Parent Process to read from the pipe\n");
		printf("Initial value of cow in the child and parent process\n");

		if( read(pfd[0], &num, sizeof(int)) == -1)
		{
			perror("Read from pipe failed");
			exit(EXIT_FAILURE);
		}

		printf("The value of cow in the child process is:  %d\n", num);
		printf("The value of cow in the parent process is:  %d\n\n", cow);

		printf("After assign 1000 to cow in the child process\n");

		if( read(pfd[0], &num, sizeof(int)) == -1)
		{
			perror("Read from pipe failed");
			exit(EXIT_FAILURE);
		}
		printf("The value of cow in the child process is:  %d\n", num);
		printf("The value of cow in the parent process is:  %d\n\n", cow);
		close(pfd[0]);  // close the read file descriptor

		wait(&status);
		printf("Parent process is terminated.\n");
	}


	return 0;


} // end of main()


