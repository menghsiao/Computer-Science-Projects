/*
Author:      Meng W. Hsiao (mxh126730)
Course:      CS 2336.003
Date:        2/18/2016
Assignment:  Project 2
Compiler:    Netbean 8.0.2
Description: 
            Implement object-oriented programming in Java with inheritance 
            and polymorphism.
            AI program to simulate Ant Wars with given information of ants and
            beetles from a file.
            There are specifications to Ant and Beetle class regarding their
            1.) Movements
            2.) Beetle starve  &
            3.) Breedings
            The object's awareness of the surrounding is included in this 
            program.
 */

package antwars;

/**
 *
 * @author Meng
 */
import java.util.*;
import java.io.*;

public class AntWars {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    
    public static void main(String[] args) throws Exception{
        //Started out with creating 2-dimensional array of Creature base object.
        Creature[][] grid = new Creature[10][10];
        
        inputFromFile(grid);  //function call to get input from file

        System.out.println("Populating the 2 x 2 grid given from the input " +
                "file");
        System.out.println("*********************************************");
        displayResult(grid);
        System.out.print("Enter number of turns to watch==> ");
        Scanner input = new Scanner(System.in);
        int turn = input.nextInt();

        int count = 0;
        
        //while within the number of turn to watch, play the game 
        //and display the current sate of the grid to the cosole
        do {

            playGame(grid, count);
            count++;
            System.out.println("\nTurn " + count);
            displayResult(grid);
            System.out.print("Press Enter to continue ");
            input = new Scanner(System.in);
            input.nextLine();
            
        } while (count < turn);

    }
    
    //Reading from file method
    public static void inputFromFile(Creature[][] grid) throws Exception{
        File inputFile = new java.io.File("world.txt");

        //check file existance before opening and reading
        if (!inputFile.exists()) {
            System.out.println("File does not exist");
            System.exit(0);
        }
        //create scanner object to read text from input file
        Scanner input = new Scanner(inputFile);
        
        int row = 0;  //row counter
        while(input.hasNext()) {
            String s = input.nextLine();  // read each line of the file
            char[] chars = s.toCharArray();  //convert the string to char array
            
            // Process each element of the array and create Ant of Beetle 
            // object based on the character input 'B' or 'a'
            for(int col = 0; col < chars.length; col++) {
                try {
                    if(chars[col] == 'B')
                        grid[row][col] = new Beetle();
                    else if(chars[col] == 'a')
                        grid[row][col] = new Ant();
                } catch (ArrayIndexOutOfBoundsException a) {
                    System.out.println("Warning: some row or column " + 
                            "element is cut off because it's longer than " +
                            "the intended 10 X 10 grid size for this game\n");
                    break;
                }
            }
            row++;  // update row counter
        }
    }
    
    public static void playGame(Creature[][] grid, int turn) {
        // Move Beetles first
        for(int col = 0; col < 10; col++)
            for(int row = 0; row < 10; row++){
                try {
                    if(grid[row][col].getLabel() == 'B') {
                        grid[row][col].move(grid, row, col, turn);
                    }
                        
                } catch(NullPointerException e) {}
            }
        //move Ants 
        for(int col = 0; col < 10; col++)
            for(int row = 0; row < 10; row++){
                try {
                    if(grid[row][col].getLabel() == 'a') {
                        grid[row][col].move(grid, row, col, turn);
                    }    
                } catch(NullPointerException e) {}
            }
        //beetles starve if not eat any ants in 5 turns.
        Beetle b;
        for(int col = 0; col < 10; col++)
            for(int row = 0; row < 10; row++){
                try {
                    if(grid[row][col].getLabel() == 'B') {
                        b = (Beetle)grid[row][col];
                        b.starve(grid, row, col);  //check and see
                    }
                } catch(NullPointerException e) {}
            }
       
        //ants breed
        for(int col = 0; col < 10; col++)
            for(int row = 0; row < 10; row++){
                try {
                    if(grid[row][col].getLabel() == 'a') {
                        grid[row][col].breed(grid, row, col, turn); 
                    }
                } catch(NullPointerException e) {}
            }
        
        //beetle breed 
        for(int col = 0; col < 10; col++)
            for(int row = 0; row < 10; row++){
                try {
                    if(grid[row][col].getLabel() == 'B')
                        grid[row][col].breed(grid, row, col, turn);
                } catch(NullPointerException e) {}
            }
    }
    
    public static void displayResult(Creature[][] grid) {
        System.out.print("\n  ");
        for(int i = 0; i < 10; i++)
            System.out.print("|" + i);
        System.out.println("");
        
        for (int i = 0; i < grid.length; i++) {
            System.out.print("|" + i);
            for (int j = 0; j < grid[0].length; j++) {
                try {
                    
                    System.out.printf("|%c",grid[i][j].getLabel());
                }catch (NullPointerException e) {
                    System.out.print("| ");
                }
            }
            System.out.println("|");
            System.out.println("-----------------------");
        }
    }
}
