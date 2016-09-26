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
public class BinaryTree {
    protected IntegralNode root;
    protected int size = 0;
    protected double value;
    protected String s = "";

    // default constructor
    public BinaryTree() {
        root = null;
    }
    
    // insert function
    public boolean insert(IntegralNode n) {

        IntegralNode parent = null;
        IntegralNode cur = root;
        
        if (root == null)
            root = n;
        else {

            while(cur != null) {
                if (n.compareTo(cur) > 0) {
                    parent = cur;
                    cur = cur.right;
                }
                else if (n.compareTo(cur) < 0) {
                    parent = cur;
                    cur = cur.left;
                }    
                else {
                    cur.combineCoef(n.getCoef());  // combine nodes
                    return false;  // new node didn't insert, only combine
                }

            }  // end of while loop, found the parent node
            
            if (n.compareTo(parent) > 0) {
                parent.right = n;
            }
            else
                parent.left = n;
            
        }
        size++;
        return true;  // new node inserted
    }
    
    
    // delete a node from the tree if needed
    public IntegralNode delete(IntegralNode n) 
            throws CloneNotSupportedException{
        IntegralNode parent = null;
        IntegralNode cur = root;
        
        
        while(cur != null) {
            
            if(n.compareTo(cur) < 0) {
                parent = cur;
                cur = cur.left;
            }
            else if(n.compareTo(cur) > 0) {
                parent = cur;
                cur = cur.right;
            }
            else
                break;  // find the node we want to delete
            
        }
        
        if( cur == null)
            return null;  // node is not in the tree
        
        //case1: cur has no left child
        if(cur.left == null) {
            
            // connect the parent with the right child of cur
            if(parent == null) {
                root = cur.right;
            }
            else {
                if(n.compareTo(parent) < 0)
                    parent.left = cur.right;
                else
                    parent.right = cur.right;
            }
            
        }
        else {
            
            // case 2: the cur has a left child.
            // Locate the rightmost node in the left subtree of
            // current node and also its parent.
            
            IntegralNode parentOfRightmost = cur;
            IntegralNode rightmost = cur.left;  // left subtree
            
            while(rightmost.right != null) {
                parentOfRightmost = rightmost;
                rightmost = rightmost.right; 
                
            }

            // replace the element in current by the element in rightmost
            cur.setCoef(rightmost.getCoef());
            cur.setDegree(rightmost.getDegree());
            // call this function to update its antiderivative string
            cur.findAntiDerivative();


            //Eliminate rightmost node
            if(parentOfRightmost.right == rightmost) {
                parentOfRightmost.right = rightmost.left;
            }
            else
                // when parentOfRightmost == current;
                parentOfRightmost.left = rightmost.left;
        }
        
        
        size --;
        return cur;
        
    }
    
    public IntegralNode getRoot() {
        return root;
    }
    
    // reset the tree
    public void resetTree() {
        root = null;
        s = "";
        size = 0;
        value = 0;
    }
    
    public IntegralNode search(IntegralNode n) {
        IntegralNode cur = root;
        
        while(cur != null) {
            if(n.compareTo(cur) < 0) {

                cur = cur.left;
            }    
            else if (n.compareTo(cur) > 0) {

                cur = cur.right;
            }    
            else
                return cur;
        }    
        
        return null;
    }
    
    public IntegralNode findParent(IntegralNode n) {
        IntegralNode parent = null;
        IntegralNode cur = root;
        
        while(cur != null) {
            if(n.compareTo(cur) < 0) {
                parent = cur;
                cur = cur.left;
            }    
            else if (n.compareTo(cur) > 0) {
                parent = cur;
                cur = cur.right;
            }    
            else
                return parent;
        }    
        
        return null;
    }
    public void inOrderPrint(IntegralNode root) {
        if(root != null) {
            inOrderPrint(root.left);
            System.out.print(root.getDegree() + " ");
            inOrderPrint(root.right);
            
        }  
    }
    
    // use the function to traverse the tree inOrder to put together
    // a string of all the node's string value in the tree
    public void inOrder(IntegralNode root) {

        if(root != null) {
            inOrder(root.left);
            
            // hightest degree with be at the front of the string at the end
            if(root.getCoef() != 0)
                s = root + " " + s; 
            
            inOrder(root.right);
        }    
    }
    
    // return the string obtain from the inOrder function
    public String getS() {
        if(s.length() > 0) {
            // if the first char is a '+' then we get rid of it
            if(s.charAt(0) == '+')
                return s.replaceFirst("[+]", "").trim(); 
            else
                return s;  // we keep the '-' sign if it's negative term
        }
        else
            return null;

       // return null;
    }
    public boolean isEmpty() {
        return ( root == null );
    }
    
}
