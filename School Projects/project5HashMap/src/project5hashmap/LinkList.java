/*
Author:      Meng W. Hsiao (mxh126730)
Course:      CS 2336.003
Date:        4/15/2016
Assignment:  Project 5
Compiler:    Netbean 8.0.2
Description: write a program to find the league leaders from file and output
             to a file.  Combining HashMap and Linked List technique to 
             store player data and find league leaders
 */
package project5hashmap;

/**
 *
 * @author Meng
 */
import java.io.*;

public class LinkList {
    private Player head, tail;
    private int linkListSize = 0;
    
    // default constructor 
    public LinkList() {
        head = null;
        tail = null;
    }
    
    /************************************************************************
     * insert the new node to become the first node in the list 
     *
     * @param newNode
     */
    public void insertFirstLink(Player newNode) {

        
        //if the player is not in the list already, create new node
        if(findPlayer(newNode.getName()) == null) {

            newNode.next = head;
            head = newNode;
            linkListSize++;

            if(tail == null)
                tail = head;
        }
        else {
            
            //add new record to existing record by calling the set function
            newNode = findPlayer(newNode.getName());
            newNode.combineRecord(newNode.getBattingRecord());
        }
    }
    
    /************************************************************************
     * insert the new node to become the last node in the list 
     * 
     * @param newNode
     */
    public void insertLastLink(Player newNode) {
        
        //if the player is not in the list already, create new node
        if(findPlayer(newNode.getName()) == null) {
            

            
            if(tail == null)
                head = tail = newNode;
            
            else {
                tail.next = newNode;
                tail = tail.next;
            }
            
            linkListSize++;
        }
        else {
            
            //add new record to existing record by calling the set function
            newNode = findPlayer(newNode.getName());
            newNode.combineRecord(newNode.getBattingRecord());
        }
    }     
  
    
    
    /**********************************************************************
     * Is the linked list empty
     * 
     * @return 
     */
    public boolean isEmpty() {
        
        return (head == null);
    }
    
    /*************************************************************************
     * find existing player, linear search
     * 
     * @param name
     * @return 
     */
 
     public Player findPlayer(String name) {
        Player currentNode = head;  // starting from the head of the list
        
        if(!isEmpty()) {
            
            while(currentNode.getName().compareToIgnoreCase(name) != 0) {
                if(currentNode.next == null) {
                    return null;  // no player in the list, return null
                }
                else {
                    currentNode = currentNode.next;  //go to the next node
                } 
            }
            return currentNode;  // return the matching node
        }
        else {
            return null;  // the list is empty, return null
        }
        
    }
    
    /*************************************************************************
     * print the link list 
     * 
     * @param out 
     * @param homeTeam 
     */

    public void display(PrintWriter out, boolean homeTeam) {
        Player currentNode = head;

        // go through each node in the linked list until reach null
        while(currentNode != null) {
            if(currentNode.getHomeTeam() == homeTeam) {
                out.printf("%-20s%-10d%-10d%-10d%-15d%-20d%-15d%-20.3f%-10.3f",
                currentNode.getName(), currentNode.getAB(), currentNode.getH(), 
                currentNode.getBB(), currentNode.getK(), currentNode.getHBP(), 
                currentNode.getS(), currentNode.getBA(), currentNode.getOB());
                out.println();
            }    

            currentNode = currentNode.next;

        }
    }
    
    /*********************************************************************
     * display top 3 league leaders
     * @param choice
     * @param out 
     */

    public void displayLeader(int choice, PrintWriter out) {
        Player currentNode = head;
        int firstPlace = 0, secondPlace = 0, thirdPlace = 0;

        
        //  display each category 
        switch (choice) {
                case 1:
                    out.printf("BATTING AVERAGE (BA)");
                    out.println();
                    break;
                case 2:
                    out.println("ON-BASE PERCENTAGE (OB%)");
                    break;
                case 3:
                    out.printf("HITS (H)");
                    out.println();
                    break;
                case 4:
                    out.printf("WALKS (BB)");
                    out.println();
                    break;
                case 5:
                    out.printf("STRIKEOUTS (K)");
                    out.println();
                    break;
                case 6:
                    out.printf("HIT BY PITCH (HBP)");
                    out.println();
                    break;
                
            }
        
        // display first, second, and third place of each category
         
        switch (choice) {
            
            // find batting average leaders
            case 1:
                
                // check for ties for first place
                while(currentNode != null) {
                    if(firstPlace == 0)
                        out.printf("%-10.3f\t%s\n", currentNode.getBA(), 
                        currentNode.getName());
                    else
                        out.printf(", %s\n", currentNode.getName());

                    if(currentNode.next != null) {
                        if(currentNode.getBA() == currentNode.next.getBA()) {
                            firstPlace++;
                            currentNode = currentNode.next;
                        }
                        
                        // if no tie, break the while loop
                        else {
                            currentNode = currentNode.next;
                            break;
                        } 
                    }
                    else
                        currentNode = currentNode.next;
                }
                
                // display second place if only two ties for first place
                if(firstPlace < 2 && currentNode != null) {
                    out.println();
                    
                    // check for ties for second place
                    while(currentNode != null) {
                        if(secondPlace == 0)
                            out.printf("%-10.3f\t%s\n", currentNode.getBA(), 
                            currentNode.getName());
                        else
                            out.printf(", %s\n", currentNode.getName());
                        
                        if(currentNode.next != null) {
                            if(currentNode.getBA() == 
                                    currentNode.next.getBA()) {
                                secondPlace++;
                                currentNode = currentNode.next;
                            }
                            
                            // if no tie, break the while loop
                            else {
                                currentNode = currentNode.next;
                                break;
                            } 
                        }
                        else
                            currentNode = currentNode.next;
                    }
                }  
                
                // if there is no tie for first place and second places.
                // display third places
                if(firstPlace == 0 && secondPlace == 0 
                        && currentNode != null) {
                    out.println();

                    //check for ties for third places
                    while(currentNode != null) {
                        if(thirdPlace == 0)
                            out.printf("%-10.3f\t%s\n", currentNode.getBA(), 
                            currentNode.getName());
                        else
                            out.printf(", %s\n", currentNode.getName());
                        
                        if(currentNode.next != null) {
                            if(currentNode.getBA() == 
                                    currentNode.next.getBA()) {
                                 currentNode = currentNode.next;
                                 thirdPlace++;
                            }
                            
                            // if no tie, break the loop
                            else {
                                break;
                            } 
                        }
                        else
                            currentNode = currentNode.next;
                    }
                }
                
                out.println();    
                break;  // break for case 1
                
            // the rest of the cases follows the same algorithm from case 1
            // but it compares different values to find first, second and 
            // third places.
            
            // find On Base percentage leaders
            case 2:
                // check for ties for first place
                while(currentNode != null) {
                    if(firstPlace == 0)
                        out.printf("%-10.3f\t%s\n", currentNode.getOB(), 
                        currentNode.getName());
                    else
                        out.printf(", %s\n", currentNode.getName());
                    
                    if(currentNode.next != null) {
                        if(currentNode.getOB() == currentNode.next.getOB()) {
                            firstPlace++;
                            currentNode = currentNode.next;
                        }
                        else {
                            currentNode = currentNode.next;
                            break;
                        } 
                    }
                    else
                        currentNode = currentNode.next;
                }
                
                if(firstPlace < 2 && currentNode != null) {
                    out.println();
                    // check for ties 
                    while(currentNode != null) {
                        if(secondPlace == 0)
                            out.printf("%-10.3f\t%s\n", currentNode.getOB(), 
                            currentNode.getName());
                        else
                            out.printf(", %s\n", currentNode.getName());
                        
                        if(currentNode.next != null) {
                            if(currentNode.getOB() == 
                                    currentNode.next.getOB()) {
                                secondPlace++;
                                currentNode = currentNode.next;
                            }
                            else {
                                currentNode = currentNode.next;
                                break;
                            } 
                        }
                        else
                            currentNode = currentNode.next;
                    }
                }  
                
                if(firstPlace == 0 && secondPlace == 0 
                        && currentNode != null) {
                    out.println();
                    // check for ties 
                    while(currentNode != null) {
                        if(thirdPlace == 0)
                            out.printf("%-10.3f\t%s\n", currentNode.getOB(), 
                            currentNode.getName());
                        else
                            out.printf(", %s\n", currentNode.getName());
                        
                        if(currentNode.next != null) {
                            if(currentNode.getOB() == 
                                    currentNode.next.getOB()) {
                                 currentNode = currentNode.next;
                                 thirdPlace++;
                            }
                            else {
                                break;
                            } 
                        }
                        else
                            currentNode = currentNode.next;
                    }
                }
                
                out.println();    
                break;
                
            // find Hits leaders    
            case 3:
                // check for ties for first place
                while(currentNode != null) {
                    if(firstPlace == 0)
                        out.printf("%-10d\t%s\n", currentNode.getH(), 
                        currentNode.getName());
                    else
                        out.printf(", %s\n", currentNode.getName());
                    
                    if(currentNode.next != null) {
                        if(currentNode.getH() == currentNode.next.getH()) {
                            firstPlace++;
                            currentNode = currentNode.next;
                        }
                        else {
                            currentNode = currentNode.next;
                            break;
                        } 
                    }
                    else
                        currentNode = currentNode.next;
                }
                
                if(firstPlace < 2 && currentNode != null) {
                    out.println();
                    // check for ties 
                    while(currentNode != null) {
                        if(secondPlace == 0)
                            out.printf("%-10d\t%s\n", currentNode.getH(), 
                            currentNode.getName());
                        else
                            out.printf(", %s\n", currentNode.getName());
                        
                        if(currentNode.next != null) {
                            if(currentNode.getH() == 
                                    currentNode.next.getH()) {
                                secondPlace++;
                                currentNode = currentNode.next;
                            }
                            else {
                                currentNode = currentNode.next;
                                break;
                            } 
                        }
                        else
                            currentNode = currentNode.next;
                    }
                }  
                
                if(firstPlace == 0 && secondPlace == 0 
                        && currentNode != null) {
                    out.println();
                    // check for ties 
                    while(currentNode != null) {
                        if(thirdPlace == 0)
                            out.printf("%-10d\t%s\n", currentNode.getH(), 
                            currentNode.getName());
                        else
                            out.printf(", %s\n", currentNode.getName());
                        
                        if(currentNode.next != null) {
                            if(currentNode.getH() == 
                                    currentNode.next.getH()) {
                                 currentNode = currentNode.next;
                                 thirdPlace++;
                            }
                            else {
                                break;
                            } 
                        }
                        else
                            currentNode = currentNode.next;
                    }
                }
                
                out.println();    
                break;
            
            // find Walks leaders
            case 4:
                // check for ties 
                while(currentNode != null) {
                    if(firstPlace == 0)
                        out.printf("%-10d\t%s\n", currentNode.getBB(), 
                        currentNode.getName());
                    else
                        out.printf(", %s\n", currentNode.getName());
                    
                    if(currentNode.next != null) {
                        if(currentNode.getBB() == currentNode.next.getBB()) {
                            firstPlace++;
                            currentNode = currentNode.next;
                        }
                        else {
                            currentNode = currentNode.next;
                            break;
                        } 
                    }
                    else
                        currentNode = currentNode.next;
                }
                
                if(firstPlace < 2 && currentNode != null) {
                    out.println();
                    // check for ties 
                    while(currentNode != null) {
                        if(secondPlace == 0)
                            out.printf("%-10d\t%s\n", currentNode.getBB(), 
                            currentNode.getName());
                        else
                            out.printf(", %s\n", currentNode.getName());
                        
                        if(currentNode.next != null) {
                            if(currentNode.getBB() == 
                                    currentNode.next.getBB()) {
                                secondPlace++;
                                currentNode = currentNode.next;
                            }
                            else {
                                currentNode = currentNode.next;
                                break;
                            } 
                        }
                        else
                            currentNode = currentNode.next;
                    }
                }  
                
                if(firstPlace == 0 && secondPlace == 0
                        && currentNode != null) {
                    out.println();
                    // check for ties 
                    while(currentNode != null) {
                        if(thirdPlace == 0)
                            out.printf("%-10d\t%s\n", currentNode.getBB(), 
                            currentNode.getName());
                        else
                            out.printf(", %s\n", currentNode.getName());
                        
                        if(currentNode.next != null) {
                            if(currentNode.getBB() == 
                                    currentNode.next.getBB()) {
                                 currentNode = currentNode.next;
                                 thirdPlace++;
                            }
                            else {
                                break;
                            } 
                        }
                        else
                            currentNode = currentNode.next;
                    }
                }
                
                out.println();    
                break;
            
            // find Strikeouts leaders    
            case 5:
                // check for ties 
                while(currentNode != null) {
                    if(firstPlace == 0)
                        out.printf("%-10d\t%s\n", currentNode.getK(), 
                        currentNode.getName());
                    else
                        out.printf(", %s\n", currentNode.getName());
                    
                    if(currentNode.next != null) {
                        if(currentNode.getK() == currentNode.next.getK()) {
                            firstPlace++;
                            currentNode = currentNode.next;
                        }
                        else {
                            currentNode = currentNode.next;
                            break;
                        } 
                    }
                    else
                        currentNode = currentNode.next;
                }
                
                if(firstPlace < 2 && currentNode != null) {
                    out.println();
                    // check for ties 
                    while(currentNode != null) {
                        if(secondPlace == 0)
                            out.printf("%-10d\t%s\n", currentNode.getK(), 
                            currentNode.getName());
                        else
                            out.printf(", %s\n", currentNode.getName());
                        
                        if(currentNode.next != null) {
                            if(currentNode.getK() == 
                                    currentNode.next.getK()) {
                                secondPlace++;
                                currentNode = currentNode.next;
                            }
                            else {
                                currentNode = currentNode.next;
                                break;
                            } 
                        }
                        else
                            currentNode = currentNode.next;
                    }
                }  
                
                if(firstPlace == 0 && secondPlace == 0
                        && currentNode != null) {
                    out.println();
                    // check for ties     
                    while(currentNode != null) {
                        if(thirdPlace == 0)
                            out.printf("%-10d\t%s\n", currentNode.getK(), 
                            currentNode.getName());
                        else
                            out.printf(", %s\n", currentNode.getName());

                        if(currentNode.next != null) {
                            if(currentNode.getK() == 
                                    currentNode.next.getK()) {
                                 currentNode = currentNode.next;
                                 thirdPlace++;
                            }
                            else {
                                break;
                            } 
                        }
                        else
                            currentNode = currentNode.next;
                    }
                }
                
                out.println();    
                break;
            
            // find hit by pitch leaders    
            case 6:
                // check for ties 
                while(currentNode != null) {
                    if(firstPlace == 0)
                        out.printf("%-10d\t%s\n", currentNode.getHBP(), 
                        currentNode.getName());
                    else
                        out.printf(", %s\n", currentNode.getName());

                    if(currentNode.next != null) {
                        if(currentNode.getHBP() == currentNode.next.getHBP()) {
                            firstPlace++;
                            currentNode = currentNode.next;
                        }
                        else {
                            currentNode = currentNode.next;
                            break;
                        } 
                    }
                    else
                        currentNode = currentNode.next;
                }
                
                if(firstPlace < 2 && currentNode != null) {
                    out.println();
                    // check for ties 
                    while(currentNode != null) {
                        if(secondPlace == 0)
                            out.printf("%-10d\t%s\n", currentNode.getHBP(), 
                            currentNode.getName());
                        else
                            out.printf(", %s\n", currentNode.getName());

                        if(currentNode.next != null) {
                            if(currentNode.getHBP() == 
                                    currentNode.next.getHBP()) {
                                secondPlace++;
                                currentNode = currentNode.next;
                            }
                            else {
                                currentNode = currentNode.next;
                                break;
                            } 
                        }
                        else
                            currentNode = currentNode.next;
                    }
                }  
                
                if(firstPlace == 0 && secondPlace == 0
                        && currentNode != null) {
                    out.println();
                    // check for ties 
                    while(currentNode != null) {
                        if(thirdPlace == 0)
                            out.printf("%-10d\t%s\n", currentNode.getHBP(), 
                            currentNode.getName());
                        else
                            out.printf(", %s\n", currentNode.getName());

                        if(currentNode.next != null) {
                            if(currentNode.getHBP() == 
                                    currentNode.next.getHBP()) {
                                 currentNode = currentNode.next;
                                 thirdPlace++;
                            }
                            else {
                                break;
                            } 
                        }
                        else
                            currentNode = currentNode.next;
                    }
                }
                
                out.println();    
                break;
                
        } // end of switch statement
    } // end of displayLeader function

    /*****************************************************************
     * bubble sort by name
     */
    public void sortByName() {
        Player currentNode = head;
        Player previousNode;
        boolean needNextPass = true;
        boolean sortHomeTeam;
        
        if(isEmpty())
            System.out.println("There is nothing in the file");

        // bubble sort
        while (needNextPass) {

            needNextPass = false;
            while (currentNode != null) {
                
                // if the next node's name is greater than the current node
                // swap

                if (currentNode.next != null && currentNode.getName().
                        compareToIgnoreCase(currentNode.next.getName()) > 0) {

                    Player nextNode = currentNode.next;
                    if(currentNode != head) {

                        previousNode = findPreviousNode(currentNode);
                        previousNode.next = nextNode;
                        currentNode.next = nextNode.next;
                        nextNode.next = currentNode;

                        needNextPass = true;
                    }

                    //swapping the node if the current node is a head
                    if(currentNode == head) {
                        currentNode.next = nextNode.next;
                        nextNode.next = currentNode;
                        head = nextNode;

                        needNextPass = true;
                    }
                }

                currentNode = currentNode.next;  // go through the linked list
            }
            currentNode = head;  // reset current node to head for next pass
        } // end of while needNextPass loop

    }
    
    /*******************************************************************
     * bubble sort by record
     * @param choice 
     */
    public void sortByRecord(int choice) {

        Player currentNode = head;
        Player previousNode;
        boolean needNextPass = true;
        boolean swap = false;
        
        while (needNextPass) {

            needNextPass = false;  // always start out with false for next pass
            while (currentNode != null) {
                
                // compare the current and next value and swap if necessary
                if (currentNode.next != null) {
                    switch(choice) {
                    case 1:
                        if(currentNode.getBA() < currentNode.next.getBA())
                            swap = true; 
                        break;
                    case 2:
                        if(currentNode.getOB() < currentNode.next.getOB())
                            swap = true; 
                        break;
                    case 3:
                        if(currentNode.getH() < currentNode.next.getH())
                            swap = true;
                        break;    
                    case 4:
                        if(currentNode.getBB() < currentNode.next.getBB())
                            swap = true; 
                        break;
                    case 5: 
                        if(currentNode.getK() > currentNode.next.getK())
                            swap = true;  
                        break;
                    case 6:
                        if(currentNode.getHBP() < currentNode.next.getHBP())
                            swap = true; 
                        break;
                }
                    
                    if(swap) {
                        Player nextNode = currentNode.next;
                        if(currentNode != head) {
                            
                            previousNode = findPreviousNode(currentNode);
                            previousNode.next = nextNode;
                            currentNode.next = nextNode.next;
                            nextNode.next = currentNode;

                            needNextPass = true;  // needs next pass
                            swap = false;  // reset swap
                        }

                        //swapping the node if the current node is a head
                        if(currentNode == head) {
                            currentNode.next = nextNode.next;
                            nextNode.next = currentNode;
                            head = nextNode;

                            needNextPass = true;
                            swap = false;
                        }
                    }
                }
                
                currentNode = currentNode.next; // go through the linked list
            }  // end of while currentNode != null loop
            
            currentNode = head; // reset currentNode to head for next pass
        } // end of while needNextPass loop
        
    }
    
    /**********************************************************************
     * find the previous node of the current node
     * 
     * @param currentNode
     * @return 
     */
    public Player findPreviousNode(Player currentNode) {
        
        // assuming previous node is head
        Player previousNode = head;
        
        if(currentNode == head)
            return null;  // no previous node
        
        while(previousNode.next != currentNode)
            previousNode = previousNode.next;

        return previousNode;
    }  
}
