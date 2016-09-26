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
public class Beetle extends Creature{
    private int numOfTurns_InGame = 0, turnSurvived, notEatAntCounter;

    Beetle() {
        turnSurvived = 0;
        notEatAntCounter = 0;
        super.setLabel('B');
    }
    
    // this second constructor is for the breed function, 
    // also provide current game turn counter + 1 for it to be ready for the
    // next move since I am using the counter to make sure an object doesn't
    // move twice in the game.
    Beetle(int turn) {
        this();
        numOfTurns_InGame = turn + 1;
    }
    @Override
    public void move(Creature[][] grid, int rowPos, int colPos, int turn) {
        // index of ants array 0,1,2,3 representing North, East, South, West
        int[] ants = {0, 0, 0, 0};  // distance of nearest ant, N, E, S, W
        int[] tie = {0,0,0,0};      // direction of tie, N, E, S, W  
        
        // number of neighbors for each nearest ant at equal distance away
        // from the beetle at N, E, S, W directions
        int[] neighbors = {0,0,0,0};   

        int count = 0;

        //check and see if this object has alrady moved for this turn
        //only move if it hasn't already moved.
        if(numOfTurns_InGame == turn){
            numOfTurns_InGame++;
            turnSurvived++;
            notEatAntCounter++;
            
            // scan north direction
            for(int i = rowPos - 1; i >= 0; i--) {
                try {
                    count++;
                    if(grid[i][colPos].getLabel() == 'a') {
                        ants[0] = count;
                        break;
                    }
                } catch (NullPointerException e) {}
            }

            // scan east direction
            count = 0;

            for(int i = colPos + 1; i < grid[0].length; i++) {
                try {
                    count++;
                    if(grid[rowPos][i].getLabel() == 'a') {
                        ants[1] = count;
                        break;
                    }
                } catch (NullPointerException e) {}
            }

            // scan south direction    
            count = 0;
            for(int i = rowPos + 1; i < grid.length; i++) {
                try {
                    count++;
                    if(grid[i][colPos].getLabel() == 'a') {
                        ants[2] = count;
                        break;
                    }
                } catch (NullPointerException e) {}
            }

            // scan west section
            count = 0;

            for(int i = colPos - 1; i >= 0; i--) {
                try {
                    count++;
                    if(grid[rowPos][i].getLabel() == 'a') {
                        ants[3] = count;
                        break;
                    }
                } catch (NullPointerException e) {
                }
            }

            //use the following algorithm to find the direction to move
            //find the nearest ant with least distance away from this beetle
            int temp = 20; 
            int index = 0;
            boolean move, thereIsTie;
            move = thereIsTie = false;

            //find the index to move, i in the sequence of direction N, E, S, W
            //and should represent the index of the smallest value in ants[]
            //and not equal to zero
            for(int i = 0; i < ants.length; i++) {
                if ( ants[i] != 0 && ants[i] < temp) {
                    temp = ants[i];
                    index = i;
                    move = true;
                }
                else if (ants[i] == temp) {
                    tie[i] = 1;
                    thereIsTie = true;
                }
            }
            
            // if there is a tie, calls the search Neighbors algorithm
            // and move towards ant with most neighbors.
            if(thereIsTie) {
                int antRowPos, antColPos;
                tie[index] = 1;
                for(int i = 0; i < tie.length; i++) {
                    if(tie[i] == 1) {   // there is a tie at this index
                        switch(i) {
                            case 0:
                                // north
                                antRowPos = rowPos - ants[i];
                                antColPos = colPos;
                                neighbors[i] = searchNeighbor(grid, antRowPos, 
                                        antColPos);
                                break;
                                
                            case 1:
                                //east
                                antRowPos = rowPos;
                                antColPos = colPos + ants[i];

                                neighbors[i] = searchNeighbor(grid, antRowPos, 
                                        antColPos);
                                break;
                            case 2:
                                //south
                                antRowPos = rowPos + ants[i];
                                antColPos = colPos;
                                neighbors[i] = searchNeighbor(grid, antRowPos, 
                                        antColPos);
                                break;
                            case 3:
                                //west
                                antRowPos = rowPos;
                                antColPos = colPos - ants[i];
                                neighbors[i] = searchNeighbor(grid, antRowPos, 
                                        antColPos);
                                break;
                        }
                    }
                }
                
                temp = neighbors[0];
                index = 0;
                for(int i = 0; i < neighbors.length; i++) {
                    if(neighbors[i] > temp) {
                        index = i;          // direction of ant
                        temp = neighbors[i];  //  most neighbors
                    }
                }
                
            }

            //if no ants, move to the farthest edge
            if(!move) {
                int[] edge = {0, 0, 0, 0}; // north, east, south, west
                
                
                edge[0] = rowPos - 0;   
                edge[1] = grid[0].length - colPos - 1;
                edge[2] = grid.length - rowPos - 1;
                edge[3] = colPos - 0;
                
                temp = edge[0];
                for(int i = 0; i < edge.length; i++) {
                    if(edge[i] > temp) {
                        temp = edge[i];  //  farthest edge
                        index = i;       // direction   
                    }
                }
            }
            
            //Looks like the beetle will always try to move no matter what
            //move the beetle according the the resulting index value
            switch(index) {
                case 0: // move North direction if within range and null
                    if(rowPos - 1 >= 0 && 
                            (grid[rowPos - 1][colPos] == null ||
                            grid[rowPos - 1][colPos].getLabel() == 'a')) {

                        //if it's an ant, reset notEatAntCounter
                        if (grid[rowPos - 1][colPos] != null && 
                                grid[rowPos - 1][colPos].getLabel() == 'a') 
                            notEatAntCounter = 0;


                        grid[rowPos - 1][colPos] = grid[rowPos][colPos];
                        grid[rowPos][colPos] = null;
                    }
                    break;
                    
                case 1: // move East direction if within range and null
                    if(colPos + 1 < grid[0].length && 
                            (grid[rowPos][colPos + 1] == null ||
                            grid[rowPos][colPos + 1].getLabel() == 'a')) {

                        //if it's an ant, reset notEatAntCounter
                        if (grid[rowPos][colPos + 1] != null &&
                                grid[rowPos][colPos + 1].getLabel() == 'a')
                            notEatAntCounter = 0;

                        grid[rowPos][colPos + 1] = grid[rowPos][colPos];
                        grid[rowPos][colPos] = null;
                    }
                    break;
                    
                case 2: // move South direction if within range and null
                    if(rowPos + 1 < grid.length && 
                            (grid[rowPos + 1][colPos] == null ||
                            grid[rowPos + 1][colPos].getLabel() == 'a')) {

                        //if it's an ant, reset notEatAntCounter
                        if (grid[rowPos + 1][colPos] != null && 
                                grid[rowPos + 1][colPos].getLabel() == 'a')
                            notEatAntCounter = 0;

                        grid[rowPos + 1][colPos] = grid[rowPos][colPos];
                        grid[rowPos][colPos] = null;
                    }
                    break;
                    
                case 3: // move West direction if within range and null
                    if(colPos - 1 >= 0 && 
                            (grid[rowPos][colPos - 1] == null ||
                            grid[rowPos][colPos - 1].getLabel() == 'a')) {

                        //if it's an ant, reset notEatAntCounter
                        if (grid[rowPos][colPos - 1] != null &&
                                grid[rowPos][colPos - 1].getLabel() == 'a')
                            notEatAntCounter = 0;

                        grid[rowPos][colPos - 1] = grid[rowPos][colPos];
                        grid[rowPos][colPos] = null;
                    }
                    break;
            }  
        }
    }
    
    // Beetle breed if survived 8 turns and then reset the counter.
    // turnSurvived counter will get reset even if there is no space to breed
    @Override
    public void breed(Creature[][] grid, int row, int col, int turn) {
        if (turnSurvived == 8) {
            while (true) {
                // try to breed north, if fail go East
                if (row - 1 >= 0 )
                    if (grid[row - 1][col] == null) {
                        grid[row - 1][col] = new Beetle(turn);
                        break;
                    }
                
                // try to breed East, if fail go South
                if (col + 1 < grid[0].length) 
                    if (grid[row][col + 1] == null){
                        grid[row][col + 1] = new Beetle(turn);
                        break;
                    }

                // try to breed South, if fail go West
                if (row + 1 < grid.length)
                    if(grid[row + 1][col] == null){
                        grid[row + 1][col] = new Beetle(turn);
                        break;
                    }

                // try to breed West, if fail no breed
                if (col - 1 >= 0) 
                    if(grid[row][col - 1] == null){
                        grid[row][col - 1] = new Beetle(turn);
                        break;
                    }
                break;
            }
            turnSurvived = 0;
        }
    }

    public void starve(Creature[][] grid, int rowPos, int colPos) {
        if(notEatAntCounter == 5)
            grid[rowPos][colPos] = null;
    }
    
    public int searchNeighbor(Creature[][] grid, int row, int col) {
        int count = 0;  // number of neighbors\
        int topBoundary, bottomBoundary, leftBoundary, rightBoundary;
        
        //set the boundaries to avoid array index out of bound exception
        if(row - 1 >= 0)
            topBoundary = row - 1;
        else
            topBoundary = row;
        
        if(row + 1 < grid.length)
            bottomBoundary = row + 1;
        else
            bottomBoundary = row;
        
        if(col - 1 >= 0)
            leftBoundary = col - 1;
        else
            leftBoundary = col;
        
        if(col + 1 < grid[0].length)
            rightBoundary = col + 1;
        else
            rightBoundary = col;

        for(int i = topBoundary; i <= bottomBoundary; i++) {
            for (int j = leftBoundary; j <= rightBoundary; j++ ) {
                    if ( i != row || j != col) {
                        try {
                            if (grid[i][j].getLabel() == 'a') {
                                count++;
                            }
                        } catch(NullPointerException e) {}
                    } 
            }
        }
        return count;
    }
}

