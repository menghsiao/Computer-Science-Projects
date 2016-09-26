/*
 * Assignment 4 for CS 3376.501
 * Programmer: Meng Hsiao
 * NetID: mxh126730
 * Description:
 * This source file contain fork and pipe process to find prime numbers.
 * The pipe create 11 clouds.  One for the parent. And 10 others for 10
 * children created from fork().
 * Each children is responsible to find prime number within its own range.
 * The ultimate target is to find all the prime number from 2 to 1,000,000.
 *

 */
#include<unistd.h>
#include<stdbool.h>
#include<stdio.h>
#include<string.h>
#include<math.h>


void findPrime(int, int[][2]);

int main() {
	const int NUM_OF_CHILDREN = 10;
	pid_t pid;
	int fd[NUM_OF_CHILDREN + 1][2];


	int ret = pipe(fd[0]);  // first pipe for the parent
	if(ret == -1 ) {
		perror("pipe error");
		return -1;
	}

	findPrime(0, fd);  // find the first set of prime before fork()

	// create 10 pipes and fork 10 children to run each range of number
	// to find prime number
	int childrenCount = 1;
	for(; childrenCount <= NUM_OF_CHILDREN; childrenCount++) {
		int ret = pipe(fd[childrenCount]);
		if(ret == -1 ) {
			perror("pipe error");
			return -1;
		}

		pid = fork();

		if( pid == -1 ) {
			perror("fork failed");
			break;
		}
		else if ( pid == 0 ){
			// close the child read file descriptor
			int j = 0;
			for(; j <= childrenCount; j++) {
				// close all the read file descriptor to other pipe that
				// was open and also close all the other write file descriptor
				// to other pipes except its own
				close(fd[childrenCount][0]);
				if(j != childrenCount)
					close(fd[j][1]);  // close other pipe write descriptor
			}
			break;
		}
		else {
			// close the parent write file descriptor
			close(fd[childrenCount][1]);
		}
	}

	// child process
	if(pid == 0) {

		findPrime(childrenCount, fd);

	}
	// parent process
	else {
		int answer, res;
		int j = 0;
		int primeArray[100000];
		int arrayCounter = 0;

		// read from each pipe
		do {
			// keeps reading until reach the end of process marker "-1"
			do {
				res = read(fd[j][0], &answer, sizeof(int));
				if(res != 0 && answer != -1) {
				    primeArray[arrayCounter] = answer;
				    // can output to stdout from here but the assignment
				    // is asking for an array to store the prime numbers
					//printf("%d\n", answer);
					arrayCounter++;
				}
				else
					close(fd[j][0]);
			}while(answer != -1);

			j++;
		}while(j < NUM_OF_CHILDREN + 1);

		// output to stdout of all the prime numbers from the array
		int k = 0;
		for(; k < arrayCounter; k++)
			printf("%d\n", primeArray[k]);


	}

	return 0;
}


// function to find prime numbers
// take childNum and file descriptor as parameter
// at this time only the write to file descriptor [1] is open
void findPrime(int childNum, int fd[][2]) {

	int endNum = -1;  // to indicate the end of file
	int starting = 100000, end;

	// sets different ranges to find prime numbers
	if (childNum == 0) {
		starting = 2;
		end = 1000;
	}
	else if(childNum == 1) {
		starting = 1001;
		end = 100000;
	}
	else {
		starting = starting * (childNum - 1) + 1;
		end = starting - 1 + 100000;
	}


	// find prime numbers
	int i = starting;
	for(; i <= end; i++) {
		bool prime = 1;

		int j = 2;
		for(; j <= sqrt(i); j++) {
			if( i % j == 0 ) {
				prime = 0;
				break;  // break the loop as soon as it's not prime
			}
		}

		// write to file descriptor when it is prime number
		if (prime) {
			write(fd[childNum][1], &i, sizeof(i));

		}

	}  // end of for loop

	// write the marker to indicate the end for this file descriptor
	write(fd[childNum][1], &endNum, sizeof(endNum));

	close(fd[childNum][1]);  // close the write file descriptor


}

