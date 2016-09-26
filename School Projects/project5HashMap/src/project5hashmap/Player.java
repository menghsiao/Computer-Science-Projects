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


public class Player {
    private String name, battingRecord;
    private int hit_total = 0, at_bat_total = 0, plate_appearance_total = 0,
        out_total = 0, strikeout_total = 0, walk_total = 0, hbp_total = 0,
        sacrifice_total = 0;
    private boolean homeTeam = false;

    
    Player next;
    
    /**
     Default constructor
     */
    public Player() {
        
    }
    
    //overloading construction
    public Player(String name, String battingRecord) {
        this.name = name;
        this.battingRecord = battingRecord; 
        setRecord(battingRecord);

    }
    public Player(String name, char eachRecord) {
        this.name = name;
        this.battingRecord = eachRecord + "";
        setRecord(this.battingRecord);
    }    
    
    
    //process each character of the batting record, the sum up the total 
    //for what we need.
    private void setRecord(String battingRecord) {
        
        for (int i=0; i < battingRecord.length(); i++) {
            switch(battingRecord.charAt(i)) {
                case 'H':   hit_total++;
                            at_bat_total++;
                            plate_appearance_total++; break;
                case 'O':   out_total++;
                            at_bat_total++;
                            plate_appearance_total++; break;
                case 'K':   strikeout_total++;
                            at_bat_total++;
                            plate_appearance_total++; break;
                case 'W':   walk_total++;
                            plate_appearance_total++; break;
                case 'P':   hbp_total++;
                            plate_appearance_total++; break;
                case 'S':   plate_appearance_total++; 
                            sacrifice_total++;  break;
                case 'E':   at_bat_total++; // 'E' for Error
                            plate_appearance_total++; break;
                default:    break;
            }
        }
        
    }
    
    public void setTeam(boolean homeTeam) {

        this.homeTeam = homeTeam;
    }    
    
    public void combineRecord(String battingRecord) {
        this.battingRecord += battingRecord;
        setRecord(battingRecord);
    }
    
    // Display player's name, batting record, and stats
    public void display() {
        System.out.printf("%-20s%-10s%-10s%-10s%-15s%-20s%-15s%-20s%-10s\n", 
                "Player Name", "At-bats", "Hits", "Walks", "Strikeouts", 
                "Hits by pitch", "Sacrifices", "Batting average", 
                "On-base percentage");
        
        System.out.printf("%-20s%-10d%-10d%-10d%-15d%-20d%-15d%-20.3f%-10.3f",
                name, at_bat_total, hit_total, walk_total, strikeout_total, 
                hbp_total, sacrifice_total, getBA(), getOB());
        
    } 
            
    /****************************************************************
     The following is a series of getter method to get batting records.
     */        
    public String getName() {
        return name;
    }
    
    public double getBA() {
        if(at_bat_total == 0)
            return 0;
        else
            return (double)hit_total / at_bat_total;
    }
    
    public double getOB() {
        return (hit_total + walk_total + hbp_total) / 
               (double)plate_appearance_total;
    }
    
    public int getK() {
        return strikeout_total;
    }
    
    public int getBB() {
        return walk_total;
    }
    
    public int getHBP() {
        return hbp_total; 
    }
    
    public int getH() {
        return hit_total;
    }
    
    public int getAB() {
        return at_bat_total;
    }
    
    public int getS() {
        return sacrifice_total;
    }
    
    public int getPA() {
        return plate_appearance_total;
    }
    
    public boolean getHomeTeam() {
        return homeTeam;
    }
    
    public String getBattingRecord() {
        return battingRecord;
    }
    
    //to string method
    @Override
    public String toString() {
        if(homeTeam)
            return "HOME Player: " + name + " has batting record: " 
                    + battingRecord;
        else
            return "AWAY Player: " + name + " has batting record: "
                    + battingRecord;
    }
    
    
}
