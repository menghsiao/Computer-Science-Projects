/*
Author:      Meng W. Hsiao (mxh126730)
Course:      CS 2336.003
Date:        1/29/2016
Assignment:  Homework#2 - #6.18 - pg. 238
Compiler:    Netbean 8.0.2
Description: check passwords from file and output invalid password to file
 */
package validpassword;


import java.io.*;
import java.util.*;

public class ValidPassword {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        
        System.out.print("Enter a file name to open:  ");
        Scanner inputFileName = new Scanner(System.in); 
        String s = inputFileName.nextLine();
       
        File inputFile = new File(s);
        File outputFile = new File("InvalidPwds.txt");
        PrintWriter output = new PrintWriter(outputFile);
        
        if (!inputFile.exists()) {
            System.out.println("File does not exist");
            System.exit(0);
        } 
        Scanner input = new Scanner(inputFile);
        checkPasswords(input, output);
        input.close();
        output.close();
    }
    
    public static void checkPasswords(Scanner in, PrintWriter out) {
        //check each password separated by space
        while (in.hasNext()) {
            String s = in.next();
            int digitCount = 0;
            
            boolean isValidPwd = true,
                    isEightChars = true,
                    isOnlyLetterAndDigits = true,
                    isAtLeastTwoDigits = true;
            //check number of characters
            if (s.length() < 8) {
                isValidPwd = false;
                isEightChars = false;
            }
            
            //go through each character to make sure it's letter or digits
            for (int i = 0; i < s.length(); i++) {
                //if not letter or digit, set invalid pwd flag
                if (!Character.isLetterOrDigit(s.charAt(i))) {
                    isValidPwd = false;
                    isOnlyLetterAndDigits = false;
                }
                //if it's digit, add the total number of digits
                if (Character.isDigit(s.charAt(i)))
                    digitCount++;
            }
            //if total number of digits is less than 2
            if (digitCount < 2) {
                isValidPwd = false;
                isAtLeastTwoDigits = false;
            }
            //output to file of invalid pwd
            if (!isValidPwd) { 
                out.print("Invalid password:  " + s + " <-- ");
                if (!isEightChars)
                    out.print("Must Have at least eight characters; ");
                if (!isOnlyLetterAndDigits)
                    out.print("Must consists of only letters and digits; ");
                if (!isAtLeastTwoDigits)
                    out.print("Must contain at least two digits; ");
                out.println();
            }
        }
        
    }
 
}
