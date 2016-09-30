/*
 * To change this license header, choose License Headers in Project
Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binarytreecomplete;

/**
 *
 * @author Meng
 */
public class BinaryTreeComplete {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();
  //      Node NewRoot = null;
        tree.root = new Node(1);
        tree.root.left = new Node(2);
        tree.root.right = new Node(3);
        tree.root.left.right = new Node(5);
        tree.root.left.left = new Node(4);
        tree.root.right.left = new Node(6);
        tree.root.right.right = new Node(9);
         
        int node_count = tree.countNode(tree.root);
        int index = 0;
         
        System.out.println("node count is " + node_count);
        if (tree.isComplete(tree.root, index, node_count))
            System.out.println("The binary tree is complete");
        else
            System.out.println("The binary tree is not complete"); 
        
        if (tree.isFullTree(tree.root))
            System.out.println("The binary tree is full");
        else
            System.out.println("The binary tree is not full"); 
        
        int nonLeaf = tree.countNonLeave(tree.root);
        System.out.println("num non leaf is " + nonLeaf);
        
        if(tree.isStrict(tree.root))
            System.out.println("The tree is strict");
        else
            System.out.println("the tree is not strict");
        
        int leaf = tree.countLeave(tree.root);
        System.out.println("num leave is " + leaf);
    }
}

class BinaryTree {
    Node root;
    
    public BinaryTree() {
        root = null;
    }
    
    public int countNonLeave(Node node) {
        if( node == null ) // empty tree
            return 0;
        
        // is a leaf
        boolean leaves = node.left == null && node.right == null;
        if( leaves )
            return 0;
        
        return ( 1 + countNonLeave(node.left) + countNonLeave(node.right) );
        
    }
    
    public int countLeave(Node node) {
        if( node == null ) // empty tree
            return 0;
               // is a leaf
        boolean leaves = node.left == null && node.right == null;
        if( leaves )
            return 1;
        else
            return ( countLeave(node.left) + countLeave(node.right) );
    }
    
    public boolean isStrict(Node node) {
        if(node == null)
            return false;
        boolean noChildren = node.left == null && node.right == null;
        boolean oneChildren = node.left != null || node.right != null;
        boolean twoChildren = node.left != null && node.right != null;

        if(noChildren) {
            return true;
        }
        if(oneChildren && !twoChildren)
            return false;
        
        if(twoChildren)
            return ( isStrict(node.left) && isStrict(node.right));
        
        return false;
    }
    public int countNode(Node node) {
        if (node == null)
            return 0;
        
        return ( 1 + countNode(node.left) + countNode(node.right) );
    }
    
    boolean isComplete(Node node, int index, int number_nodes)
    {   if(index == 0)
            System.out.println("starting from root");
        else
            if(index % 2 == 0)
                System.out.println("right " + index);
            else
                System.out.println("left " + index);
        // An empty tree is complete
        if (node == null)        
           return true;
        
        // If index assigned to current node is more than
        // number of nodes in tree, then tree is not complete
        if (index >= number_nodes)
           return false;
 
        // Recur for left and right subtrees
        return (isComplete(node.left, 2 * index + 1, number_nodes)
            && isComplete(node.right, 2 * index + 2, number_nodes));
 
    }
    boolean isFullTree(Node node)
    {
        // if empty tree
        if(node == null)
        return true;
         
        // if leaf node
        boolean leafNode = node.left == null && node.right == null; 
        if(leafNode )
            return true;
         
        // if both left and right subtrees are not null
        // they are full
        if((node.left!=null) && (node.right!=null))
            return (isFullTree(node.left) && isFullTree(node.right));
         
        // if none work
        return false;
    }
}

class Node {
    int data;
    Node left;
    Node right;
    
    Node(int num) {
        data = num;
    }
}