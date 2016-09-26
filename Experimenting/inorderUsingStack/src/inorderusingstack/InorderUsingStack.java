/*
Author:      Meng W. Hsiao (mxh126730)
Course:      CS 2336.003
Date:        4/10/2016
Assignment:  Homework 6
Compiler:    Netbean 8.0.2
Description: traverse the binary tree inOrder without using recursion 
 */
package inorderusingstack;

public class InorderUsingStack {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        BinaryTree tree = new BinaryTree();
        

        tree.insert(new Node(60));
        tree.insert(new Node(53));
        tree.insert(new Node(100));
        tree.insert(new Node(45));
        tree.insert(new Node(57));
        tree.insert(new Node(59));
        tree.insert(new Node(67));
        tree.insert(new Node(107));
        tree.insert(new Node(101));
        
        tree.insert(new Node(56));
        tree.insert(new Node(55));

        
        System.out.println("inOrder");
        tree.inorderStack();
        System.out.println();
        tree.inorderRecursion(tree.root);
        System.out.println();
        System.out.println("num of non leaves: " + tree.num);
        
        //following is not required by this assignment
        System.out.println("preOrder");
        tree.preorderStack();
        System.out.println();
        System.out.println("postOrder");
        tree.postorderStack();
        System.out.println();

    }
    
}

// binary tree class
class BinaryTree {
    Node root;
    int num = 0;
    Stack stack = new Stack();
    
    public BinaryTree() {
        root = null;
    }    
    public boolean insert(Node n) {

        Node parent = root;
        Node cur = root;
        
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
                else
                    return false;

            }  // end of while loop
     
            if (n.compareTo(parent) > 0) {
                parent.right = n;
            }
            else
                parent.left = n;

        }

        return true;  // new node inserted
    }
    
    public void inorderRecursion(Node root) {
        if(root != null) {
            inorderRecursion(root.left);
            boolean leaves = root.left == null && root.right == null;
            if(!leaves)
                num++;
            System.out.print(root.getNum() + " ");
            inorderRecursion(root.right);
            
        }
    }
    
    // for this assignemnt and it's working.
    public void inorderStack() {
        Node cur = root;
        
        while(!(cur == null && stack.getTop() == null)) {
            
            // going down the tree
            if(cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            else { // cur is null
                if(stack.getTop() != null) {
                    cur = stack.pop();
                    System.out.print(cur + " ");
                    cur = cur.right;
                }
            }    
        }
    }
    
    // not required for this assignment for testing purpose
    public void preorderStack() {
        Node cur = root;
        stack.push(cur);
        
        while(stack.getTop() != null) {
            cur = stack.pop();
            if(cur != null) {
                System.out.print(cur + " ");
                if(cur.right != null)
                    stack.push(cur.right);
                if(cur.left != null)
                    stack.push(cur.left);
            }            
        }
    }

    public void postorderStack() {
        Node head = root;

        stack.push(root);

        while (!stack.isEmpty()) {
          Node next = stack.getTop();

          boolean finishedSubtrees = 
                  (next.right == head || next.left == head);
          boolean isLeaf = (next.left == null && next.right == null);
          
          if (finishedSubtrees || isLeaf) {
              
            stack.pop();
            System.out.print(next + " ");
            head = next;
            
          }
          else {
              
            if (next.right != null) {
              stack.push(next.right);
            }
            if (next.left != null) {
              stack.push(next.left);
            }
            
          }
        }  // end of while loop
        
    }    
    
}

// Stack class
class Stack {
    Node top;
    
    public Stack() {
        top = null;
    }
    public void push(Node n) {
        if(top == null) {
            top = n;
        }
        else {
            n.next = top;
            top = n;
        }
    }
    
    public Node pop() {
        Node hold = null;
        
        if (top == null)
            System.out.println("Empty list!");
        else 
        {
            hold = top;
            top = top.next;
            hold.next = null;
        }

        return hold;
    }
    
    public Node getTop() {
        return top;
    }
    
    public boolean isEmpty() {
        return top == null;
    }
}

// node class
class Node implements Comparable<Node> {
    
    int num;
    Node next;
    Node left, right;
    
    public Node() {
        num = 0;
        next = left = right = null;
    }
    public Node(int num) {
        this.num = num;
    }
    public void setNum(int num) {
        this.num = num;
    }
    public int getNum() {
        return num;
    }
    
    @Override
    public int compareTo(Node n)
    {
        return this.num - n.num;
    }
    
    @Override
    public String toString() {
        return num + "";
    }    
}