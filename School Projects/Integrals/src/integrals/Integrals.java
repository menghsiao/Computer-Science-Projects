/*
Author:      Meng W. Hsiao (mxh126730)
Course:      CS 2336.003
Date:        4/10/2016
Assignment:  Project 4
Compiler:    Netbean 8.0.2
Description: Solve definite or indefinite integral problems using binary tree
 */
package integrals;

/**
 *
 * @author Meng
 */
import java.io.*;
import java.util.*;

public class Integrals {

    /**
     * @param args the command line arguments
     */

    
    public static void main(String[] args) throws Exception{
              
        BinaryTree tree = new BinaryTree();
        
        File inFile = new File("integrals.txt");
        if(!inFile.exists()) {
            System.out.println("File does not exist");
            System.exit(0);
        }
        
        
        File outFile = new File("answers.txt");
        PrintWriter output = new PrintWriter(outFile);
        
        String s, definiteString, integralString;
        boolean isDefinite;
        int upperLimit, lowerLimit;
        double value;  // value for definite integrals
        
        Scanner input = new Scanner(inFile);
        while (input.hasNext()) {

            upperLimit = lowerLimit = 0;
            value = 0.0;
            
            s = input.nextLine();   // the whole line
            Scanner sub = new Scanner(s);  // another input scanner object
            definiteString = sub.next();  // read the definite string
            
            integralString = sub.nextLine(); // read the integral string
            
             /*
                Create a stringbuilder object to delete all the whitespaces
                All whitespace appears randomly and it's not clean to 
                read all the datas I need from the string.
                So, after delete all the whitespaces, add whitespace characters
                infront of each math operators '+' and '-' except if it's '-'
                after the exponent character '^'.  Also add whitespace infront
                of "dx"
             */     
            StringBuilder term = new StringBuilder(
                    integralString.replaceAll(" ", ""));
            int i = 0;
            
            // add space infront of every operator '+' or '-' with exception
            while(i < term.length()) {
                if(term.charAt(i) == '+') {
                    term.insert(i, " ");
                    i+= 2;
                }
                // in case of "x^-1", don't add space infront of '-'
                else if(i > 0 && term.charAt(i) == '-' && 
                        term.charAt(i - 1) != '^') {
                    term.insert(i, " ");
                    i+= 2;
                }
                // also add a whitespace infront of "dx"
                else if(term.charAt(i) == 'd') {
                    term.insert(i, " ");
                    i+=2;
                }
                else
                    i++;
            }
            
            //  find the upper and lower limit of the integral if any
            String[] token = definiteString.split("[|]");
            if(token.length == 2) {
                isDefinite = true;
                lowerLimit = Integer.parseInt(token[0]);
                upperLimit = Integer.parseInt(token[1]);
            }
            else
                isDefinite = false;

            //  process each term obtain from the polynomial string
            Scanner readTerm = new Scanner(term.toString());

            int trigCounter = 0;  // for the trig expression
            while(readTerm.hasNext()) {
                String termString = readTerm.next();
                if(termString.compareTo("dx") != 0) {
                    value = processEachTerm(tree, termString, 
                            lowerLimit, upperLimit, value, 
                            trigCounter, isDefinite);
                    
                    trigCounter++;
                }
            }
            
            // display the polynomial expression after antiderivative
            tree.inOrder(tree.root);
            
            
            // in case when the result is an 0 expression, print out zero
            // else print out the normal way
            if(isDefinite) {
                if(tree.getS() == null) {
                    output.printf("0, %s = %6.3f\n", definiteString, value);
                    output.println();
                }    
                else {
                    output.printf("%s, %s = %6.3f\n", tree.getS(), 
                            definiteString, value);
                    output.println();
                }
            }
            // in case when the result is an 0 expression, print out zero
            else {
                if(tree.getS() == null)
                    output.println("0 + C or C");
                else
                    output.println(tree.getS() + " + C");
            }

            tree.resetTree();  // reset the tree

        } // end of while(input.hasNext())
        
        input.close();
        output.close();

    }  // end of main method
    
    public static double processEachTerm(BinaryTree tree, String term, 
            int lowerLimit, int upperLimit, double value, int trigCounter, 
            boolean isDefinite) {
        
        String[] token;
        int coef = 1, deg = 0;  // default values
        

        if(term.contains("s")) {
            IntegralNode n = new IntegralNode(term, -20 - trigCounter);
            tree.insert(n);
            
        }
        else {
            token = term.split("[x^]");

            if(token.length >= 1) {

                //negative of positive one when sign symbols without numbers
                //  example " -x " or " +x ", maybe not need to check "+"
                // since the default coef is 1 already.
                if(token[0].compareTo("-") == 0 || 
                        token[0].compareTo("+") == 0) {
                    coef = Integer.parseInt(token[0] + coef);  
                }
                // when token[0] has a value not ""
                else if (token[0].compareTo("") != 0)  
                    coef = Integer.parseInt(token[0]);

                // the degree will be at token[2] if length is > 1
                if(token.length > 1)
                    deg = Integer.parseInt(token[2]);
            }

            // change degree value to 1 when there is a "x" in the expression
            if (term.contains("x") && deg == 0) {
                deg = 1;
            }
            
            // every term in the express is a node eventhough it may be 
            // combined with an existing node in the binary tree later
            // when inserting into the tree.  If it's degree is same with an
            // existing node, it will combine its coef with the existing node
            IntegralNode n = new IntegralNode(coef, deg);
            tree.insert(n);
            
            // even if it will combine with an existing node, we can still
            // go ahead and calculte the value of this term
            //if this is an definite integral expression
            if(isDefinite) {
                value += n.getValue(upperLimit) - n.getValue(lowerLimit);
            }

        }
        
        return value;  // return value of each term passed into this function
    }  //  end of processEachTerm method
    
    
} //  end of class Integrals
