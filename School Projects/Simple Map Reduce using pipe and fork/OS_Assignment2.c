/*
 * Assignment 2 for CS 4348
 * Programmer: Meng Hsiao
 * NetID: mxh126730
 * Description:  map-reduce model
 */

#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include <stdlib.h>
#include <stdbool.h>
#include <errno.h>

int main(int argc, char *argv[])
{
	FILE *inFile;
	char fileName[128];
	int num_of_mappers;
	char key[30];
	pid_t pid;
	int starting = 0;
	int end = 0;
	int fileSize;
	const int endNum = -1;

	if(argc != 4) {
		perror("wrong number of argument\n");
		printf("Follow the format:  ./a.out "
				"[Number_of_mappers] [keyword] [filename]\n");
		return -1;

	}

	//  check if the number of mappers from the argv[1] is a number only
	char buffer[20];
	strcpy(buffer, argv[1]);

	int i = 0;
	int flag = 0;
	for(; i < strlen(buffer); i++) {
		if(!isdigit(buffer[i])) {
			flag = 1;
			break;
		}
	}
	if(flag) {
		fprintf(stderr, "first argument requires a positive int number\n");
		return -1;
	}

	//  assign appropriate number to num_of_mappers variable

	num_of_mappers = (int) strtol(argv[1], (char **)NULL, 10);
	if (num_of_mappers <= 0) {
		num_of_mappers = 1;
	}

	// creating more than one pipe to process each sequentially
	// by the reducer at the end
	int pfd[num_of_mappers][2];	//  set the array of pipes available
	strcpy(key, argv[2]);  //  set the keyword from the argv
	strcpy(fileName, argv[3]);  //  set the filename from the argv

	inFile = fopen(fileName, "r");	// open filename to process
	if(!inFile) {
		fprintf(stderr, "Unable to open input file\n");
		return -1;
	}
	else {
		// find out the size of the text file
		fseek(inFile, 0, SEEK_END);
		fileSize = ftell(inFile);
	}

	// find out each blocksize, divided equally by the num_of_mappers
	int blockSize = fileSize / num_of_mappers;

	// create pipe and fork() child process
	int childrenCount = 0;
	for(; childrenCount < num_of_mappers; childrenCount++) {
		if(childrenCount != num_of_mappers - 1) {
			// this is what the end value should be
			end = blockSize * (childrenCount + 1);

			fseek(inFile, end, SEEK_SET);  // seek to that position in file

			/*  find out if need to add few more space until we reach the
			    whitespace character.  We don't want to set the end to be
			    in the middle of the word */
			int add = 0;
			char c;
			do {
				c = fgetc(inFile);

				if ( c != ' ' )
					add++;
			}while(c != ' ');

			end = end + add;

		}  // end of if statement
		else {  //  if this is the last children to be created
			end = fileSize;  // set end to equal the size of the file
		}

		// create pipe, then fork() child process
		int ret = pipe(pfd[childrenCount]);
		if(ret == -1 ) {
			perror("pipe error");
			return -1;
		}
		pid = fork();

		if( pid == -1 ) {
			perror("\n\n********** FORK FAILED *********\n\n");
			return -1;
		}
		else if ( pid == 0 ){
			// close the child read file descriptor
			int j = 0;
			for(; j <= childrenCount; j++) {
				// close all the read file descriptor to other pipe that
				// was open and also close all the other write file descriptor
				// to other pipes except its own
				close(pfd[childrenCount][0]);
				if(j != childrenCount)
					close(pfd[j][1]);  // close other pipe write descriptor
			}
			fclose(inFile);
			break;
		}
		else {
			// close the parent write file descriptor
			close(pfd[childrenCount][1]);
		}

		starting = end + 1;  // increment starting position

	} // end of for loop

	// child process, the mapper
	if(pid == 0) {
		FILE *childInFile;
		childInFile = fopen(fileName, "r");	// open filename to process
		if(!childInFile) {
			fprintf(stderr, "Unable to open input file\n");
			return -1;
		}

		int childStarting, childEnd;
		childStarting = starting;
		childEnd = end;
		char wordBuffer[256];	// contains each words of the line

		// write into the pipe of the starting and end offset
		write(pfd[childrenCount][1], &starting, sizeof(starting));
		write(pfd[childrenCount][1], &end, sizeof(end));

		// count keyword matches.
		// start with the starting position by fseek()
		fseek(childInFile, childStarting, SEEK_SET);
		int count = 0;
		int cur = 0;

		cur = ftell(childInFile);
		while(cur < childEnd) {
			fscanf(childInFile, "%s", wordBuffer);
			cur = ftell(childInFile);
			if (cur > childEnd) {
				fprintf(stderr, "error reading the file\n");
				return -1;
			}
			if(strcasestr(wordBuffer, key)) {
				count++;
			}
		} // end while loop

		// write the total frequency of keyword appears,
		// and write the end marker into the pipe
		write(pfd[childrenCount][1], &count, sizeof(count));
		write(pfd[childrenCount][1], &endNum, sizeof(endNum));

		fclose(childInFile);
		close(pfd[childrenCount][1]);  // close the pipe write descriptor
		exit(1);
	} // end of child process

	// parent process, the single reducer
	else {
		int answer, res;
		int j = 0;  // counter for num of mappers
		int totalFrequency = 0;

		printf("The number of mappers is %d\n", num_of_mappers);
		printf("The keyword is ==> %s\n", key);

		// read from each pipe
		do {
			// keeps reading until reach the end of process marker "-1"
			int frequency = 0;
			printf("\nStarting & ending offset (bytes) for Mapper#%d are:  "
					, (j + 1));

			int k = 0;  // to distinguish the first two values are offsets

			// keep reading the pipe until reach endNum
			do {
				res = read(pfd[j][0], &answer, sizeof(int));
				if(res != 0 && answer != -1) {
					if (k < 2) {
						printf("%d ", answer);
					}
					else {

						frequency = answer;
					}
				}
				k++;
			}while(answer != -1);

			close(pfd[j][0]);
			printf("\nFrequency of the keyword found for "
				"Mapper#%d is:  %d\n",(j+1), frequency);

			totalFrequency += frequency;
			j++;
		}while(j < num_of_mappers);

		printf("\nTotal frequency of the keyword in the file is ==> %d\n",
				totalFrequency);
	}

	printf("\nparent exit\n");
	fclose(inFile);
	return 0;
}
