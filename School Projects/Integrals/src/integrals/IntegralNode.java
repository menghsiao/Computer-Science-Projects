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
public class IntegralNode implements Comparable<IntegralNode>{
    protected IntegralNode left, right;
    protected int coef, deg, degAnswer, numBeforeX;
    protected double coefAnswer;
    protected String antiDerivative_String, coef_String;
    protected boolean isTrig = false;
    
    // default constructor
    public IntegralNode() {
        left = right = null;
    }
    // overloaded constructor
    public IntegralNode(int coef, int deg) {
        this.coef = coef;
        this.deg = deg;
        antiDerivative();
    }
    // overloaded constructor for trig node
    public IntegralNode(String coef_String, int deg) {
        isTrig = true;
        this.coef_String = coef_String;
        this.deg = deg;
        coef = 1;
        antiDerivative();
    }
    public void findAntiDerivative() {
        antiDerivative();
    }
    // find the antiderivative, fractions will be enclosed in parentheses
    private void antiDerivative() {
        String sign;
        if (!isTrig) {  // if not a trig node
            
            if (deg == -1) {
                sign = ((coef > 0) ? "+": "-");
                antiDerivative_String = sign + " " + Math.abs(coef) +
                        "ln(|x|)";
            }
            else {

                degAnswer = deg + 1;
                coefAnswer = (double)coef / degAnswer;

                // simplify fractional numbers
                int numerator = coef;
                int denominator = degAnswer;
                int gcd = gcd(numerator, denominator);

                numerator = ((denominator > 0) ? 1: -1) * numerator / gcd;

                denominator = Math.abs(denominator) / gcd;

                sign = ((numerator > 0) ? "+": "-");
                
                // putting the coef together into the string first
                if(denominator == 1 && Math.abs(numerator) == 1) {
                    antiDerivative_String ="";
                }
                else if(denominator == 1 && Math.abs(numerator) != 1)
                    antiDerivative_String = Math.abs(numerator) + "";
                else
                    antiDerivative_String = "(" + Math.abs(numerator) + "/" 
                            + denominator + ")";
                
                // then add the remaining x^ and degree after antiderivative
                // if there is any of those.
                if(degAnswer == 1)
                    antiDerivative_String += "x";
                else
                    antiDerivative_String += "x^" + degAnswer;
                
                // at the end add the sign + or - into the string
                antiDerivative_String = sign + " " + antiDerivative_String;
            }   
        }
        else {  // if this is a trig node
            String[] token = coef_String.split("[sincosx]");

            numBeforeX = 1;
            if(token.length >= 1) {
                if(token[0].compareTo("-") == 0 || 
                            token[0].compareTo("+") == 0) {
                        coef = Integer.parseInt(token[0] + 
                                coef);  
                }
                else if (token[0].compareTo("") != 0)  
                    coef = Integer.parseInt(token[0]);
                
                if(token.length > 1) {
                    numBeforeX = Integer.parseInt(token[token.length - 1]);
                }    
            } 
            coefAnswer = (double) coef / numBeforeX;
            
            // simplify fractional numbers
            int numerator = coef;
            int denominator = numBeforeX;
            int gcd = gcd(numerator, denominator);

            numerator = ((denominator > 0) ? 1: -1) * numerator / gcd;

            denominator = Math.abs(denominator) / gcd;

            sign = ((numerator > 0) ? "+": "-");

            if(denominator == 1 && Math.abs(numerator) == 1) {
                antiDerivative_String ="";
            }
            else if(denominator == 1 && Math.abs(numerator) != 1)
                antiDerivative_String = Math.abs(numerator) + "";
            else
                antiDerivative_String = "(" + Math.abs(numerator) + "/" 
                        + denominator + ")";
            
            // antiderivative sin and cos and also taking care of the sign
            //  + or -
            if(coef_String.contains("cos"))
                antiDerivative_String += "sin";
            
            else if (coef_String.contains("sin")) {
                antiDerivative_String += "cos";
                sign = ((numerator * -1 > 0) ? "+": "-");
            }
            
            // at the end add the sign + or - into the string
            if(Math.abs(numBeforeX) != 1)
                antiDerivative_String = sign + " " + antiDerivative_String + 
                        " " + numBeforeX + "x";
            else
                antiDerivative_String = sign + " " + antiDerivative_String + 
                        " " + "x";
        }
    }
    // find the greatest common denominator
    private int gcd(int n, int d) {
        int n1 = Math.abs(n);
        int n2 = Math.abs(d);
        int gcd = 1;
        
        for(int i = 1; i <= n1 && i <= n2; i++) {
            if (n1 % i == 0 && n2 % i == 0)
                gcd = i;
        }    
        return gcd;
    }
    public void setCoef(int coef) {
        this.coef = coef;
    }
    public void setDegree(int deg) {
        this.deg = deg;
    }
    public int getCoef() {
        return coef;
    }    
    public int getDegree() {
        return deg;
    }
    public double getCoefAnswer() {
        return coefAnswer;
    }    
    public int getDegreeAnswer() {
        return degAnswer;
    }
    // combine terms with same degree together
    public void combineCoef(int new_Coef) {
        coef = coef + new_Coef;
        antiDerivative();
    }    
    //  pass the x value into this function to find the value of the term
    //  this function will be called when this is an definite integral
    public double getValue(int xValue) {

        if(deg == -1)
            return coefAnswer * Math.log(xValue);
        else 
            return coefAnswer * Math.pow(xValue, degAnswer);
    }
    @Override
    public int compareTo(IntegralNode n)
    {
        return this.deg - n.deg;
    }
    @Override
    public String toString() {

        return antiDerivative_String;
    }
}
