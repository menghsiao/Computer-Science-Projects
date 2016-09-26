/*
Author:      Meng W. Hsiao (mxh126730)
Course:      CS 2336.003
Date:        2/18/2016
Assignment:  Project 2
Compiler:    Netbean 8.0.2
Description: Implement object-oriented programming in Java with inheritance 
 */
package antwars;

/**
 *
 * @author Meng
 * creating a base class Creature
 */
abstract public class Creature {
    private char label;
    
    public abstract void move (Creature[][] grid, int rowPos, int colPos, 
                                int turn); 
    public abstract void breed (Creature[][] grid, int rowPos, int colPos, 
                                int turn);
    public char getLabel() {
        return label;
    }
    public void setLabel(char c) {
        label = c;
    }
}
