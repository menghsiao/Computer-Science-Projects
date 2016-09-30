/*
Author:      Meng W. Hsiao (NET ID: mxh126730)
Course:      CS 2336.003
Date:        01/18/2016
Assignment:  Homework 1 #2.6 - pg. 70
Compiler:    NetBeans IDE 8.0.2
Description: Sum all the digits of an integer between 0 and 1000
 */
package sumthedigit;

/**
 *
 * @author Meng
 */
import java.util.Scanner;
        
public class SumTheDigit {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Scanner input = new Scanner(System.in);
        int digits = -1;
        
        //ask user to enter a number between 0 and 1000
        while (digits < 0 || digits > 1000) {
            System.out.print("Enter a number between 0 and 1000:  ");
            digits = input.nextInt();
        }
        
        //Calculating Sum of the digits
        System.out.println("The sum of the digits is " 
                            + (digits / 1000 + (digits % 1000) / 100 
                            + (digits % 100) / 10 + (digits % 10)));
    }
    
}
