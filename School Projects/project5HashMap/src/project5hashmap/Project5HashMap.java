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
import java.util.*;


public class Project5HashMap {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception{
        
        // get keyMap
        HashMap<String, Character> keyMap = new HashMap<>();
        keyMap = getKeyMap(keyMap);
        
        // get player Map
        HashMap<String, Object> playerMap = getPlayerMap(keyMap);
        
        // to player linked list to sort
        LinkList playerList = toLinkedList(playerMap);
        
        // display to output file
        outputToFile(playerList);
 
    } // end of main method
    
    // get keyMap from the key file
    public static HashMap<String, Character> getKeyMap
        (HashMap<String, Character> map) throws Exception {
            
        File keyFile = new File("keyfile.txt");
        Scanner input = new Scanner(keyFile);
        
        if(!keyFile.exists()) {
            System.out.println("keyfile.txt does not exist");
            System.exit(0);
        }
        String s;
        char value = ' ';
        
        // go through each line of the file
        // and set the value to reflect the category
        while(input.hasNext()) {
            s = input.nextLine();
            if(s.contains("OUTS"))
                value = 'O';
            else if(s.contains("STRIKEOUT"))
                value = 'K';
            else if(s.contains("HITS"))
                value = 'H';
            else if(s.contains("WALK"))
                value = 'W';
            else if(s.contains("SACRIFICE"))
                value = 'S';    
            else if(s.contains("HIT BY PITCH"))
                value = 'P';
            else if(s.contains("ERRORS"))
                value = 'E';

            //  only add to the hash table if it is a key
            if(!s.contains("#") && s.length() != 0) {
                map.put(s, value);

            }    
            
        } // end of while loop

        input.close();
        
        return map;
    } // end of getKeyMap method
    
    // get playerMap from the playbyplay file by matching the keyMap
    public static HashMap<String, Object> getPlayerMap
        (HashMap<String, Character> keyMap) throws Exception {
            
        File playByPlay = new File("playbyplay.txt");
        Scanner input = new Scanner(playByPlay);
        
        if(!playByPlay.exists()) {
            System.out.println("keyfile.txt does not exist");
            System.exit(0);
        }
        
        HashMap<String, Object> playerMap  = new HashMap<>();
        
        // go through each line of the file
        while(input.hasNext()) {
            String homeTeam;
            String playerName;
            String key;
            char eachRecord = ' ';
            
            homeTeam = input.next();  // get "H" or "A"

            playerName = input.next();
            key = input.next();  // this is the result of the play
            
            // get the record matching the key
            if(keyMap.containsKey(key))
                eachRecord = keyMap.get(key); 
            else
                System.out.println("ERROR: Player " + playerName 
                        + " has invalid key: " + key);

            // if the player is not already in the hash table
            if(!playerMap.containsKey(playerName)) {
                
                // create player object
                Player player = new Player(playerName, eachRecord);
                boolean valid = homeTeam.compareTo("H") == 0 ||
                        homeTeam.compareTo("A") == 0;

                if(valid && homeTeam.compareTo("H") == 0) {
                    player.setTeam(true);  // it's a home team
                }
                else if(!valid)
                    System.out.println("ERROR: which team is " + 
                            playerName + " on? Invalid character: " 
                            + homeTeam);
               
                playerMap.put(playerName, player);  // add to the hash table
            }
            else {
                // if the player is already in the hash table
                Player player = (Player)playerMap.get(playerName);
                player.combineRecord(String.valueOf(eachRecord));
                playerMap.put(playerName, player);
                
            }


        }
        input.close();
        return playerMap;
    }  // end of getPlayerMap method
    
    // put into linked list and sort the players and also output to file
    public static LinkList toLinkedList(HashMap<String, Object> playerMap){

        LinkList playerList = new LinkList();     // combination of players
        
        Set set = playerMap.entrySet();
        Iterator i = set.iterator();
        
        // go through the hash table and add all into a linked list
        while(i.hasNext()) {
            Map.Entry me = (Map.Entry)i.next();
            
            Player player = (Player) me.getValue();
            playerList.insertLastLink(player);
        }
        
        return playerList;
    } // end of toLinkedList method
    
    // output to file method
    public static void outputToFile(LinkList playerList) throws Exception{
        File outputFile = new java.io.File("boxscore.txt");
        
        //create PrintWriter object to write text to output file.

        PrintWriter output = new PrintWriter(outputFile);
         //print out the column headers
        output.printf("%-20s%-10s%-10s%-10s%-15s%-20s%-15s%-20s%-10s", 
                "Player Name", "At-bats", "Hits", "Walks", "Strikeouts", 
                "Hits by pitch", "Sacrifices", "Batting average", 
                "On-base percentage");
        output.println();   

        
        playerList.sortByName();  // sort all the players by name
        playerList.display(output, false);  // display Away team players
        output.println();
        playerList.display(output, true);  //  display home team players
        

        
        // using loop to sort the list by each category 
        // and output top 3 leaders
        output.println();
        output.println("LEADERS");
        for (int category = 1; category <= 6 ; category++) {
            playerList.sortByRecord(category);
            playerList.displayLeader(category, output);
            output.println();
        }

        output.close();  // close the output file
    } // end of outputToFile method

} // end of class

