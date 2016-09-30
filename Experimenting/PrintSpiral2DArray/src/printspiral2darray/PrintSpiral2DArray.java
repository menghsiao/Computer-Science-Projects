/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package printspiral2darray;

/**
 *
 * @author meng
 */
public class PrintSpiral2DArray {
 
    public static void main(String[] args) {
        
        int array[][] = {{1, 2, 3},{4, 5, 6}, {7, 8, 9}};
        printSpiral(array);
    }
    
    //print spiral method
    public static void printSpiral(int array[][]) {
        //create variables for each boundary
        int top, bottom, left, right;
        
        //initial boundary value
        top = 0;
        bottom = array.length - 1;
        left = 0;
        right = array[0].length - 1;
        
        int dir = 0;
        while( top <= bottom && left <= right) {
            //going right when direction = 0
            switch (dir) {
                case 0:
                    for(int i = left; i <= right; i++) {
                        System.out.println(array[top][i]);
                    }   
                    top++; // new top
                    dir = 1; // going down
                    break;
                case 1:
                    for(int i = top; i <= bottom; i++) {
                        System.out.println(array[i][right]);
                    }   
                    right--;
                    dir = 2;  // going left
                    break;
                case 2:
                    for(int i = right; i >= left; i--) {
                        System.out.println(array[bottom][i]);
                    }   
                    bottom--;
                    dir = 3;  // going up
                    break;
                case 3:
                    for(int i = bottom; i >= top; i--) {
                        System.out.println(array[i][left]);
                    }   
                    left++;
                    dir = 0;  // going right
                    break;
            }
        }
    }
}
