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
 */
public class Ant extends Creature {
    private int numOfTurns_InGame = 0, turnSurvived;
      
    Ant() {
        turnSurvived = 0;
        super.setLabel('a');
    }
    
    // this second constructor is for the breed function, 
    // also provide current game turn counter + 1 for it to be ready for the
    // next move since I am using the counter to make sure an object doesn't
    // move twice in the game.
    Ant(int turn) {
        this();
        numOfTurns_InGame = turn + 1;
    }
    
    @Override
    public void move(Creature[][] grid, int antRowPos, int antColPos, 
                    int turn) {
        // index of beetles array 0,1,2,3 representing North, East, South, West
        int [] beetles = {0,0,0,0}; 
        int count = 0;
        
        
        //check and see if this object has alrady moved for this turn
        //only move if it hasn't already moved.
        if (numOfTurns_InGame == turn) {
            numOfTurns_InGame++;
            turnSurvived++;
            
            //scan north direction
            if(antRowPos - 1 >= 0)
                for(int i = antRowPos - 1; i >= 0; i--) {
                    try {
                        count++;
                        if(grid[i][antColPos].getLabel() == 'B') {
                            beetles[0] = count;
                            break;
                        }
                    } catch (NullPointerException e) {}
                }

                
            
            //scan east direction
            count = 0;
            if(antColPos + 1 < grid[0].length)
                for(int i = antColPos + 1; i < grid[0].length; i++) {
                    try {
                        count++;
                        if(grid[antRowPos][i].getLabel() == 'B') {
                            beetles[1] = count;
                            break;
                        }
                    } catch (NullPointerException e) {}
                }

                
            
             //scan south direction          
            count = 0;
            if(antRowPos + 1 < grid.length)
                for(int i = antRowPos + 1; i < grid.length; i++) {
                    try {
                        count++;
                        if(grid[i][antColPos].getLabel() == 'B') {
                            beetles[2] = count;
                            break;
                        }
                    } catch (NullPointerException e) {}
                }

                
            
            
            //scan west direction
            count = 0;
            if(antColPos - 1 >= 0)
                for(int i = antColPos - 1; i >= 0; i--) {
                    try {
                        count++;
                        if(grid[antRowPos][i].getLabel() == 'B') {
                            beetles[3] = count;
                            break;
                        }
                    } catch (NullPointerException e) {}
                }

                
            //*************************************************************
            //use the following algorithm to find the direction to move
            int max, maxIndex;
            boolean move, northPriority, eastPriority;
            move = northPriority = eastPriority = false;
            
            //beetle array index 0 = North direction
            //beetle array index 1 = East direction
            //beetle array index 2 = South direction
            //beetle array index 3 = West direction
            
            int temp1, temp2;
            temp1 = temp2 = 0;
            
            max = beetles[0];  // initial statement
            maxIndex = 0;
            for(int i = 0; i < beetles.length; i++) {
                if (i < 2) { 
                    // Remember that a ant should move in the opposite 
                    // direction of beetles.
                    
                    // First check North for no beetle and at least one beetle
                    // at the South direction, if true, move north and not to 
                    // care about East direction and break the loop
                    
                    // However, if not able to break the loop then check the 
                    // East direction for no beetle but at least one beetle at 
                    // the West direction, move to East and break the loop

                    if (beetles[i] == 0 && beetles[i + 2] > 0) {
                        maxIndex = i;
                        temp1 = beetles [ i + 2 ];
                        move = true;
                        northPriority = false;
                        break;
                    }
                    
                    // set move north priority to true but don't break the loop
                    // there maybe other things going on that we care
                    // such as no beetle at the West but there is a beetle
                    // at the East so in this case we will have to move to 
                    // West eventhough North and South are both clear
                    //*** always move in the opposite direction of beetles.
                    if (beetles[0] == 0 && beetles[2] == 0 ) {
                        northPriority = true;  
                    }
                }    
                
                if (i >= 2) {
                    // same idea but in the opposite way from statements above
                    if (beetles[i] == 0 && beetles[i - 2] > 0) {
                        maxIndex = i;
                        temp2 = beetles [i - 2];
                        move = true;
                        eastPriority = false;
                        northPriority = false;
                        break;
                    }
                    if (beetles[3] == 0 && beetles[1] == 0 ) {
                        eastPriority = true;
                    }
                }
                // if surrounded by beetles in all direction,
                // move towards the farthest beetle distance, MAX
                if(beetles[i] > max) {
                    max = beetles[i];
                    maxIndex = i;
                    move = true;
                }
                
                // in case beetles are all equal distance away from ant
                // set the move flag to true, but don't update the index.
                // index was already set a 0 which means move north as the
                // highest priority. 
                // this will make sure that current max value is representd
                // by its first discoverd index value
                if(beetles[i] == max && beetles[i] != 0) {
                    move = true;
                }    
            }
            if(maxIndex == 0) {
                if(temp1 > beetles[3] && (beetles[1] == 0 || beetles[1] 
                        > beetles[3] ) && beetles[3] != 0)
                    maxIndex = 1;
                if(temp1 > beetles[1] && (beetles[3] == 0 || beetles[3] 
                        > beetles[1] ) && beetles[1] != 0)
                    maxIndex = 3;
            }
      
            if(maxIndex == 1) {
                if(temp1 > beetles[0] && (beetles[2] == 0 || beetles[2] 
                        > beetles[0] ) && beetles[0] != 0)
                  maxIndex = 2;
                if(temp1 > beetles[2] && (beetles[0] == 0 || beetles[0] 
                        > beetles[2] ) && beetles[2] != 0)
                  maxIndex = 0;
            }    
            if(maxIndex == 2) {
              if(temp2 > beetles[1] && (beetles[3] == 0 || beetles[3] 
                      > beetles[1] ) && beetles[1] != 0)
                  maxIndex = 3;
              if(temp2 > beetles[3] && (beetles[1] == 0 || beetles[1] 
                      > beetles[3] ) && beetles[3] != 0)
                  maxIndex = 1;
            }

            if(maxIndex == 3) {
                if(temp2 > beetles[0] && (beetles[2] == 0 || beetles[2] 
                        > beetles[0] ) && beetles[0] != 0)
                  maxIndex = 2;
                if(temp2 > beetles[2] && (beetles[0] == 0 || beetles[0] 
                        > beetles[2] ) && beetles[2] != 0)
                  maxIndex = 0;
            }
            // move north to escape and no beetle at north
            if(northPriority && beetles[1] == beetles[3])  
                maxIndex = 0;
            
            // move east to escape and no beetle at east
            if(eastPriority && beetles[0] == beetles[2])   
                maxIndex = 1;
            //***************************************************************
            
            //if move is true then do the following to move object
            if(move) {
                switch(maxIndex) {
                    case 0: // move North direction if within range and null
                        if(antRowPos - 1 >= 0 && 
                                grid[antRowPos - 1][antColPos] == null) {
                            grid[antRowPos - 1][antColPos] = 
                                    grid[antRowPos][antColPos];
                            grid[antRowPos][antColPos] = null;
                        }
                        break;
                    case 1: // move East direction if within range and null
                        if(antColPos + 1 < grid[0].length && 
                                grid[antRowPos][antColPos + 1] == null) {
                            grid[antRowPos][antColPos + 1] = 
                                    grid[antRowPos][antColPos];
                            grid[antRowPos][antColPos] = null;
                        }
                        break;
                    case 2: // move South direction if within range and null
                        if(antRowPos + 1 < grid.length && 
                                grid[antRowPos + 1][antColPos] == null) {
                            grid[antRowPos + 1][antColPos] = 
                                    grid[antRowPos][antColPos];
                            grid[antRowPos][antColPos] = null;
                        }
                        break;
                    case 3: // move West direction if within range and null
                        if(antColPos - 1 >= 0 && 
                                grid[antRowPos][antColPos - 1] == null) {
                            grid[antRowPos][antColPos - 1] = 
                                    grid[antRowPos][antColPos];
                            grid[antRowPos][antColPos] = null;
                        }
                        break;
                }
            }
        }
    }
    
    //Breed function for ant if survived 3 turns, and then reset turnSurvived.
    //turnSurvived counter will get reset even if there is no space to breed
    @Override
    public void breed(Creature[][] grid, int row, int col, int turn) {
      
        if (turnSurvived == 3) {
            while (true) {
                //try to breed north if fail go East
                if (row - 1 >= 0 )
                    if (grid[row - 1][col] == null) {
                        grid[row - 1][col] = new Ant(turn);
                        break;
                    }
                //try to breed East, if fail go South
                if (col + 1 < grid[0].length) 
                    if (grid[row][col + 1] == null){
                        grid[row][col + 1] = new Ant(turn);
                        break;
                    }

                //try to breed South, if fail go west
                if (row + 1 < grid.length)
                    if(grid[row + 1][col] == null){
                        grid[row + 1][col] = new Ant(turn);
                        break;
                    }

                //try to breed West, if fail no breed
                if (col - 1 >= 0) 
                    if(grid[row][col - 1] == null){
                        grid[row][col - 1] = new Ant(turn);
                        break;
                    }
                break;
            }
            turnSurvived = 0;
        }
    }
}
