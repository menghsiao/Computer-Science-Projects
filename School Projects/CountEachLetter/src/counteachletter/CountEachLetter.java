/*
Author:      Meng W. Hsiao (mxh126730)
Course:      CS 2336.003
Date:        1/29/2016
Assignment:  Homework#2 - #12.30 - pg. 492
Compiler:    Netbean 8.0.2
Description: display the occurances of each letter from a file
 */
package counteachletter;


import java.io.*;
import java.util.*;
        
public class CountEachLetter {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception{
        int[] lowerCaseLetters = new int[26];
        int[] upperCaseLetters = new int[26];
             
        System.out.print("Enter a file name to open:  ");
        Scanner inputFileName = new Scanner(System.in); 
        String s = inputFileName.nextLine();
       
        File inputFile = new File(s);
        if (!inputFile.exists()) {
            System.out.println("File does not exist");
            System.exit(0);
        }       
        Scanner input = new Scanner(inputFile);
        
        //read each line in the file
        while (input.hasNext()) {
            String lineOfChar = input.nextLine();
            countLetters(lineOfChar, upperCaseLetters, lowerCaseLetters);
        }
        input.close();
        displayResult(upperCaseLetters, lowerCaseLetters);
    }
    
    //count appearance of each letter and update the counters of each letter
    //in the array of uppercase and lower case array.
    public static void countLetters(String chars, int upperArray[], 
            int lowerArray[]) {
        
        for (int i = 0; i < chars.length(); i++) {
                if (chars.charAt(i) >= 65 && 
                        chars.charAt(i) <= 90)
                    upperArray[chars.charAt(i) % 65]++; //upper case letters
                
                else if (chars.charAt(i) >= 97 && 
                        chars.charAt(i) <= 122)
                    lowerArray[chars.charAt(i) % 97]++; //lower case letters
             }
    }
    
    //display the result on the screen
    public static void displayResult(int upperArray[], int lowerArray[]) {
        
        for (int i = 65; i <=90; i++)
            System.out.println("Number of " + (char)i + "'s:  " + 
                        upperArray[i % 65]);
           
        for (int j = 97; j <=122; j++)
            System.out.println("Number of " + (char)j + "'s:  " + 
                        lowerArray[j % 97]);
    }

}
