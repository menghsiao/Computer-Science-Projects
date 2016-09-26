/*
 ============================================================================
 Name        : Assignment #2
 Class       : CS3376.501
 Author      : Meng Hsiao (mxh126730)

 Description : C program that performs some function of a file preprocessor
 ============================================================================
 */

#include <stdio.h>
#include <unistd.h>
#include <time.h>
#include <string.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <stdlib.h>


const char KEY[] = "#include";
const char TIME[] = "<time>";

void replaceString(char*, long int);

/*
This function will process the input file and send to stdout console screen
keep reading the file until end of file
scan the first word of the first line in the file
and count how many byte char read
See if the word equals the key phrase "#include"
if equals to "#include", move the string pointer forward to how many
characters read
read the following word followed by space and exclude <> symbols
calls the file to stdout function to process the file.

else just do the normal read process and compare the word to <time>,
if equals <time>, print out the time in seconds since.
else just output the words and move the pointer forward
*/

void fileToStdout( char filename[] ) {
	FILE *inFile;
	char lineBuffer[256];	// contains each line of the file
	char wordBuffer[256];	// contains each words of the line
	char *p;	// pointer for scanning each word in the line
	int used;	// to record bytes read (No. of characters read)

	time_t now;	// to store the time value
	struct tm feb4;	// time structure contains date and time.
	long int seconds;  // to record the difference in seconds since

	//initialize feb4 tm structure to localtime
	time(&now);
	feb4 = *localtime(&now);


	// modify to Feb 4, 2016 5:30 PM
	feb4.tm_hour = 17;
	feb4.tm_min = 30;
	feb4.tm_mon = 1;
	feb4.tm_mday = 4;
	feb4.tm_year = 116;
	feb4.tm_sec = 0;
	inFile = fopen(filename, "r");	// open filename to process

	if(!inFile) {
		fprintf(stderr, "Unable to open input file '%s'\n", filename);
		return;
	}

	// keeps reading while not end of file
	while(fgets(lineBuffer, 256, inFile) != NULL) {
		p = lineBuffer;	// set pointer for scanning each word

		sscanf(p, "%s%n", wordBuffer, &used);

		if (strcmp(wordBuffer, KEY) == 0) {  // if equals "#include"
			p += used;						// move pointer forward
			sscanf(p, " <%[^>]", wordBuffer);
			fileToStdout(wordBuffer);
		}
		else {
			// do the normal read process
			do {
				// normal scan to process each word and record bytes read
				// into used variable
				sscanf(p, "%s%n", wordBuffer, &used);

				int i = 0;
				// print the white space characters between words.
				for(; i < used - strlen(wordBuffer); i++)
					printf("%c", ' ');

				if(used >= 1) {
					//search string <time> inside string
					if(strstr(wordBuffer, TIME) != NULL) {
						time(&now);
						seconds = difftime(now, mktime(&feb4));
						replaceString(wordBuffer, seconds);
					}
					else
						printf("%s", wordBuffer);  // just output word
				}
				p += used;	// move to the next word
			} while (sscanf(p, "%s", wordBuffer) == 1 );
			printf("\n");
		}
	}
	fclose(inFile);	// close the file
}

/*
This function will process the standard input and output to -f filename
Using the freopen() function to redirect stdout to filename specified.
keep reading the file until end of file
scan the first word of the first line in the file
and count how many byte char read
See if the word equals the key phrase "#include"
if equals to "#include", move the string pointer forward to how many
characters read
read the following word followed by space and exclude <> symbols
calls the file to stdout function to process the file.

else just do the normal read process and compare the word to <time>,
if equals <time>, print out the time in seconds since.
else just output the words and move the pointer forward
*/
void stdinToFile(char filename[]) {
	FILE *outFile;	// output file
	char lineBuffer[256], wordBuffer[256]; // lines and words
	char *p;	// pointer to scan each word in line
	int used;	// to see how many characters read

	time_t now;	// to store the time value
	struct tm feb4;	// time structure contains date and time.
	double seconds;	// to record the difference in seconds since

	//initialize feb4 tm structure to localtime
	time(&now);
	feb4 = *localtime(&now);

	// modify to Feb 4, 2016 5:30 PM
	feb4.tm_hour = 17; feb4.tm_min = 30; feb4.tm_mon = 1; feb4.tm_mday = 4;
	feb4.tm_year = 116; feb4.tm_sec = 0;

	outFile = freopen(filename, "w+", stdout);

	// keeps reading each line while not end of file
	while(fgets(lineBuffer, 256, stdin) != NULL) {
		p = lineBuffer;		// set pointer for scanning each word
		sscanf(p, "%s%n", wordBuffer, &used);
		if (strcmp(wordBuffer, KEY) == 0) {  // if equals "#include"
			p += used;
			sscanf(p, " <%[^>]", wordBuffer);
			fileToStdout(wordBuffer);
			fflush(outFile);  // force it to write to file
		}
		else {
			do {
				sscanf(p, "%s%n", wordBuffer, &used);

				int i = 0;
				// print the white space characters between words.
				for(; i < used - strlen(wordBuffer); i++)
					printf("%c", ' ');

				if(used >= 1) {
					// //search string <time> inside string
					if(strstr(wordBuffer, TIME) != NULL) {
						time(&now);
						seconds = difftime(now, mktime(&feb4));
						replaceString(wordBuffer, seconds);
					}
					else {
						printf("%s", wordBuffer);  // output normally
					}
				}
				p += used;  // move forward pointer of bytes read
			} while (sscanf(p, "%s", wordBuffer) == 1 );
			printf("\n");
		}
		// to make sure it gets write to file when the user
		// use the CONTROL-C command
		fflush(outFile);
	}
	fclose(outFile);	// close the outFile

}

//when there is no option argument or any arguments, use this function to
//take stdin to stdout
/*
This function will process the standard input and output to stdout.
keep reading the file until end of file
scan the first word of the first line in the file
and count how many byte char read
See if the word equals the key phrase "#include"
if equals to "#include", move the string pointer forward to how many
characters read
read the following word followed by space and exclude <> symbols
calls the file to stdout function to process the file.

else just do the normal read process and compare the word to <time>,
if equals <time>, print out the time in seconds since.
else just output the words and move the pointer forward
*/
void stdinToStdout() {
	char lineBuffer[256];	// contains each line of the file
	char wordBuffer[256];	// contains each words of the line
	char *p;	// pointer to scan each word in line
	int used;	// to see how many characters read


	time_t now;	// to store the time value
	struct tm feb4;	// time structure contains date and time.
	double seconds;	// to record the difference in seconds since

	//initialize feb4 tm structure to localtime
	time(&now);
	feb4 = *localtime(&now);

	// modify to Feb 4, 2016 5:30 PM
	feb4.tm_hour = 17; feb4.tm_min = 30; feb4.tm_mon = 1; feb4.tm_mday = 4;
	feb4.tm_year = 116; feb4.tm_sec = 0;


	// keeps reading each line until end of file
	while(fgets(lineBuffer, 256, stdin) != NULL){
		p = lineBuffer;  // set pointer to scan each word
		sscanf(p, "%s%n", wordBuffer, &used);
		if (strcmp(wordBuffer, KEY) == 0) {
			p += used;	// move the pointer forward
			sscanf(p, " <%[^>]", wordBuffer);
			fileToStdout(wordBuffer);
		}
		else {
			do {
				sscanf(p, "%s%n", wordBuffer, &used);
				int i = 0;
				// print the white space characters between words.
				for(; i < used - strlen(wordBuffer); i++)
					printf("%c", ' ');

				if(used >= 1) {
					//search string <time> inside string
					if(strstr(wordBuffer, TIME) != NULL){
						time(&now);
						seconds = difftime(now, mktime(&feb4));
						replaceString(wordBuffer, seconds);

					}
					else {
						printf("%s", wordBuffer);
					}
				}
				p+=used;	//move forward the pointer of bytes read
			} while (sscanf(p, "%s", wordBuffer) == 1 );
			printf("\n");
		}
	}
}

// main function able to take the option argument and arguments
int main(int argc, char *argv[])
{
	int opt;
	struct stat attr;	// structure data about file will be stored
	char date[20];	// date buffer to store date info

	while ((opt = getopt(argc, argv, ":f:")) != -1) {
		switch(opt) {
		case 'f':
			// equals 3 when there is option + option arg (filename)
			if (argc == 3) {

				// calls the stdin output to file function
				stdinToFile(optarg);
			}
			else {
				FILE *outFile;	// output file pointer
				//redirect stdout to filename (option arg)
				outFile = freopen(optarg, "w+", stdout);

				for(; optind < argc; optind++) {
					// get the status of file store into attr
					stat(argv[optind], &attr);

					//covert time to local time and formate it using strftime
					strftime(date, 128, "%Y_%m_%d_%H_%M_%S\n",
							localtime(&attr.st_mtime));

					fputs(argv[optind], stderr);	// display filename
					fputs(" ", stderr);
					fputs(date, stderr);  // display the date format specified

					//calls the function to process the file to stdout
					fileToStdout(argv[optind]);
				}
				fclose(outFile);	// close the outFile
			}
			break;
		case ':':
			// user must provide option argument
			printf("-f option needs a value such as -f <filename>\n");
			break;
		case '?':
			// to catch any unknown uption not specified
			printf("unknown option: %c\n", optopt);
			break;
		}
	}

	int i = optind;	//use this variable to iterate through the loop

	// List the file name and its last modified date and time to stderr
	for(; i < argc; i++) {
		stat(argv[i], &attr);
		strftime(date, 128, "%Y_%m_%d_%H_%M_%S\n", localtime(&attr.st_mtime));
		fputs(argv[i], stderr);
		fputs(" ", stderr);
		fputs(date, stderr);

	}
	// 	finally when optind is less than argc, there is filename
	//  in the argument to process.  Process each file
	for(; optind < argc; optind++) {
		fileToStdout(argv[optind]);
	}

	//	when argc equals 1, there is no option, option arg, nor arguments
	if(argc == 1) {
		stdinToStdout();
	}

	return 0;
}

void replaceString(char *old, long int new)
{	char buffer[256];	// buffer to hold the new string
	char *p;	// pointer to the address of the substring to be replaced

	p = strstr(old, TIME);  // find the pointer of the substring

	// copy number of characters from old string to buffer
	// p - old gives you number of characters
	strncpy(buffer, old, p - old);

	// forward the buffer to (p - old) characters from the starting address
	// writes the "new" data into the buffer, follow by the pointer forwarded
	// to the remaining of the line which is strlen(TIME) length away from P
	sprintf(buffer + (p - old), "%ld%s", new, p + strlen(TIME));

	// now keep checking and see if there are more <time> in the substring
	// if so, call the replaceString() function recursively again.
	if(strstr(buffer, TIME) != NULL) {

		replaceString(buffer, new);
	}
	else
		printf("%s", buffer);  // output to stdout the new string result.

}

