/*
 Infix to Postfix Prefix
 */
package infixtopostfix;

import java.util.*;
import java.io.*;

public class InfixToPostfix
{
    public static void main (String args[]) throws IOException
    {
        LinkedList Stack = new LinkedList();
        File infile = new File("expressions.txt");
        Scanner in = new Scanner(infile);
        int i;
        String s = in.nextLine();
        System.out.println("infix expression is: " + s);
        
        /*
        Converting Infix to postfix
        */
        String postfix = convertInfixToPostfix(Stack, s);
        
      
        /*
        Converting Infix to prefix
        */
        String thePrefix = convertInfixToPrefix(Stack, s);
        
        // end of converting infix to pretfix
        
        /*
        Converting postfix to prefix
        */
        String prefix = convertPostfixToPrefix(Stack, postfix);
        
        
        /*
        Prefix evaluation
        */
//        String reverse ="";
//        // reverse prefix string
//        for(int j = prefix.length() - 1; j >=0; j--) {
//            reverse = reverse + prefix.charAt(j);
//            
//        }
        StringBuilder reverse = new StringBuilder(prefix);
        reverse.reverse();
        

        Scanner input2 = new Scanner(reverse.toString());
        input2.useDelimiter("[^0-9]+");
        i = 0;
        int operand1, operand2;
        while (i < reverse.length())
        {
            if (Character.isDigit(reverse.charAt(i)))
            {
                Stack.push(new Node(input2.nextInt()));

                while (Character.isDigit(reverse.charAt(i)))
                    i++;
                
            }
            else if (reverse.charAt(i) == ' ')
                i++;
            else
            {
                // arrangement of operand is different than postfix evaluation
                operand1 = Stack.pop().getNum();  
                operand2 = Stack.pop().getNum();
                switch (reverse.charAt(i)) {
                    case '+':
                        Stack.push(new Node(operand1 + operand2));
                        break;
                    case '-':
                        Stack.push(new Node(operand1 - operand2));
                        break;
                    case '*':
                        Stack.push(new Node(operand1 * operand2));
                        break;
                    case '/':
                        Stack.push(new Node(operand1 / operand2));
                        break;
                    case '^':
                        Stack.push(new Node((int)Math.pow(operand1, 
                                operand2)));
                        break;
                    default:
                        break;
                }
                i++;
            }

        }
        System.out.println("Prefix evaluation result is: " + 
                Stack.pop().getNum());
        
        
        /*
        Postfix evaluation
        */
        Scanner inline = new Scanner(postfix);
        inline.useDelimiter("[^0-9]+");
        i = 0;

        while (i < postfix.length())
        {
            if (Character.isDigit(postfix.charAt(i)))
            {
                Stack.push(new Node(inline.nextInt()));

                while (Character.isDigit(postfix.charAt(i)))
                    i++;
                
            }
            else if (postfix.charAt(i) == ' ')
                i++;
            else
            {
                operand2 = Stack.pop().getNum();
                operand1 = Stack.pop().getNum();
                switch (postfix.charAt(i)) {
                    case '+':
                        Stack.push(new Node(operand1 + operand2));
                        break;
                    case '-':
                        Stack.push(new Node(operand1 - operand2));
                        break;
                    case '*':
                        Stack.push(new Node(operand1 * operand2));
                        break;
                    case '/':
                        Stack.push(new Node(operand1 / operand2));
                        break;
                    case '^':
                        Stack.push(new Node((int)Math.pow(operand1, operand2)));
                        break;
                    default:
                        break;
                }
                i++;
            }

        }
        System.out.println("Postfix evaluation result is: " + 
                Stack.pop().getNum());
        //  end of postfix evaluation

    }
    
    static String convertInfixToPostfix(LinkedList Stack, String s) {
        String postfix = "";
        int i = 0;
        
        while (i < s.length())
        {
            char item = s.charAt(i);
         
            if (item == '(') {
                Stack.push(new Node(item)); 
            }
            else if (item == '^')
            {
                while (Stack.getTop() != null && 
                        Stack.getTop().getPrecedence() >= 3 )
                    postfix += Stack.pop().getOp();
                Stack.push(new Node(item));
            }
            else if (item == '*' || item == '/')
            {
                while (Stack.getTop() != null && 
                        Stack.getTop().getPrecedence() >= 2)
                    postfix += Stack.pop().getOp();
                Stack.push(new Node(item));
            }
            else if (item == '+' || item == '-')
            {
                while (Stack.getTop() != null && 
                        Stack.getTop().getPrecedence() >= 1)
                    postfix += Stack.pop().getOp();
                Stack.push(new Node(item));
            }
            else if (item == ')')
            {
                while (Stack.getTop().getPrecedence() > 0)
                    postfix += Stack.pop().getOp();
                Stack.pop();
            }
            else if (Character.isDigit(item))
            {
                while (i < s.length() && Character.isDigit(s.charAt(i))) {
                    
                    postfix += s.charAt(i++);
                }
                    
                postfix += " ";
                continue;
            }
            
            i++;
            
        }
        while (Stack.getTop() != null)
            postfix += Stack.pop().getOp();
        
        System.out.println("infix to postfix expression is: " + postfix);
        // end of converting infix to postfix
        
        return postfix;
    }
    static String convertInfixToPrefix(LinkedList Stack, String s) {
        StringBuilder reverseInfix = new StringBuilder(s);
        reverseInfix.reverse();


        String thePrefix = "";
        int i = 0;
        
        while (i < reverseInfix.length())
        {
            char item = reverseInfix.charAt(i);
         
            if (item == ')') {
                Stack.push(new Node(item)); 
            }
            else if (item == '^')
            {
                while (Stack.getTop() != null && 
                        Stack.getTop().getPrecedence() > 3 )
                    thePrefix += Stack.pop().getOp();
                Stack.push(new Node(item));
            }
            else if (item == '*' || item == '/')
            {
                while (Stack.getTop() != null && 
                        Stack.getTop().getPrecedence() > 2)
                    thePrefix += Stack.pop().getOp();
                Stack.push(new Node(item));
            }
            else if (item == '+' || item == '-')
            {
                while (Stack.getTop() != null && 
                        Stack.getTop().getPrecedence() > 1)
                    thePrefix += Stack.pop().getOp();
                Stack.push(new Node(item));
            }
            else if (item == '(')
            {
                while (Stack.getTop().getPrecedence() > 0)
                    thePrefix += Stack.pop().getOp();
                Stack.pop();
            }
            else if (Character.isDigit(item))
            {
                while (i < reverseInfix.length() && 
                        Character.isDigit(reverseInfix.charAt(i))) {
                    
                    thePrefix += s.charAt(i++);
                }
                    
                thePrefix += " ";
                continue;
            }
            
            i++;
            
        }
        while (Stack.getTop() != null)
            thePrefix += Stack.pop().getOp();
        
        reverseInfix = new StringBuilder(thePrefix);
        thePrefix = reverseInfix.reverse().toString();
        
        System.out.println("infix to prefix expression is: " + thePrefix);
        return thePrefix;
    }
    static String convertPostfixToPrefix(LinkedList Stack, String postfix) {
        String prefix = "";
        String string_Right, string_Middle;
        Scanner input = new Scanner(postfix);
        input.useDelimiter("[^0-9]+");
        int i = 0;
        
        int num;
        while(i < postfix.length()) {
            
            if(Character.isDigit(postfix.charAt(i))) {
                num = input.nextInt();

                Stack.push(new Node(num+""));

                
                while (Character.isDigit(postfix.charAt(i)))
                    i++;
                
            }
            else if (postfix.charAt(i) == ' ')
                i++;
            else {
                string_Right = Stack.pop().getString();
                string_Middle = Stack.pop().getString();
                prefix = postfix.charAt(i) + string_Middle + " " + 
                        string_Right;
                Stack.push(new Node(prefix));
                i++;
            }
            
        }
        System.out.println("Converting postfix to prefix expression is: " + 
                prefix);
        return prefix;
    }
}


class LinkedList 
{
    private Node top;
    
    LinkedList()
    {   top = null;    }
    
    LinkedList(Node[] objects)
    {
        for (int i = 0; i <objects.length; i++)
            push(objects[i]);
    }
    
    public Node getTop()
    {   return top;    }
    
    public void setHead(Node n)
    {   top = n;   }
    
    public void print() {
        Node cur = top;
        
        while(cur != null) {
            
            System.out.println(cur.getNum() +" " + cur.getOp());
            cur = cur.next;
        }
        
    }
    
    public void push(Node cur)
    {
        if (top == null)
           top = cur;
        else //if(cur.compareTo(top) < 0)
        {
            cur.next = top;
            top = cur;
        }

    }
    
    public Node pop()
    {
        Node hold = null;
        if (top == null)
            System.out.println("Empty list!");
        else //if(search.compareTo(top) == 0)
        {
            hold = top;
            top = top.next;
            hold.next = null;
        }

        return hold;
                
    }

}
class Node implements Comparable<Node>
{   int precedence;
    char op;
    int num;
    String s;
    
    Node next;
    
    Node()
    { next = null; }
    
    Node(int n)
    { num = n;  precedence = 0;    }
    
    Node(String s) {
        this.s = s;
        precedence = 0;
    }
    
    Node(char c) {
        op = c;

        switch(c) {

            case '+': precedence = 1; break;
            case '-': precedence = 1; break;   
            case '/': precedence = 2; break;
            case '*': precedence = 2; break;    
            case '^': precedence = 3; break;

        }

    }
    public int getPrecedence() {
        return precedence;
    }
    public int getNum()
    { return num;   }
    
    public void setNum(int n)
    {  num = n;  }

    public char getOp()
    { return op;   }
    
    public void setOp(char c)
    {  op = c;  }
    
    public Node getNext()
    { return next;  }
    
    public void setNext(Node n)
    {  next = n;    }
    
    public String getString() {
        
        return s;
    }
    
    @Override
    public int compareTo(Node n)
    {
        return this.num - n.num;
    }
    
    
}