package com.example.sudokuprime;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;



// Board generation
// March 29 2022

import java.util.ArrayList;

public class boardGen {

    /*
    public boardGen(String difficulty):
        Pre-condition: difficulty is either "easy", "medium", "hard", or "ANS"
        Post-condition: difficulty is set to the inputted difficulty

    public MakeSudoku():
        Pre-condition: void
        Post-condition: Generates a sudoku board and returns a boolean value that is used for the recursive calls

    public setDifficulty():
        Pre-condition: void
        Post-condition: solved_board is set to a solved sudoku board with the same number of zeros as difficulty

    public getboard():
        Pre-condition: void
        Post-condition: returns the current board
    
    public getAnswer():
        Pre-condition: void
        Post-condition: returns the solved board
    
    public getindex(int row, int col):
        Pre-condition: row and col are valid indexes (0-8, 0-8)
        Post-condition: returns the value of the solved board at the given row and col

    public formatBoard(int[][] sudoku_Board):
        Pre-condition: sudoku_Board is a valid sudoku board (solved or unsolved)
        Post-condition: returns a string representation of the sudoku board for pasting purposes
    
    public printBoard(int[][] sudoku_Board):
        Pre-condition: sudoku_Board is a valid sudoku board (solved or unsolved)
        Post-condition: prints the sudoku board to the console with some formatting to make it easier to read
    
    private Boolean checkValid(int row, int col, int num):
        Pre-condition: row, col, and num are valid indexes (0-8, 0-8, 1-9)
        Post-condition: returns a boolean value that is true if the given number exists once in the row, column, and 3x3 box, and false otherwise
    
    private void setDifficulty():
        Pre-condition: void
        Post-condition: solved_board is assigned to the current board before it has random values removed to create a difficulty level
    */

    public static void main( String[] args ) {
        boardGen b = new boardGen("easy");
        b.MakeSudoku();
        int[][] sudoku_board = b.getBoard();
        int[][] ans = b.getAnswer();
        printBoard(sudoku_board);
        System.out.println("\n\n-------------------------------------\n\n");
        printBoard(ans);
    }

    
    public int[][] board = new int[9][9];
    public int[][] solved_board = new int[9][9];
    public int difficulty = 57;

    public boardGen(String difficulty) {
        if (difficulty.equals("ANS")) {
            this.difficulty = 1;
        } else if (difficulty.equals("easy")) {
            this.difficulty = 57;
        } else if (difficulty.equals("medium")) {
            this.difficulty = 65;
        } else if (difficulty.equals("hard")) {
            this.difficulty = 73;
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = 0;
            }
        }
    }
    
    
    public Boolean MakeSudoku() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col] == 0) {

                    ArrayList<Integer> used = new ArrayList<Integer>();
                    while (true) {
                        int num = (int) (Math.random()*(9)) + 1;
                        if (!used.contains(num)) {
                            used.add(num);
                        }
                        if (used.size() == 8) {
                            break;
                        }
                    }

                    for (int tryNum : used) {
                        if (checkValid(row, col, tryNum)) {
                            board[row][col] = tryNum;
                            
                            if (MakeSudoku()) {
                                return true;
                            } else {
                                board[row][col] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        setDifficulty();
        return true;
    }
    
    
    private void setDifficulty() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                solved_board[i][j] = board[i][j];
            }
        }
        for (int i = 0; i < difficulty; i++) {
            int row = (int) (Math.random()*(9));
            int col = (int) (Math.random()*(9));
            board[row][col] = 0;
        }
    }


    private Boolean checkValid(int row, int col, int num) {
        Boolean valid = true;

        for (int x = 0; x < 9; x++) {
            if (board[x][col] == num) {
                valid = false;
            }
        }
        
        for (int y = 0; y < 9; y++) {
            if (board[row][y] == num) {
                valid = false;
            }
        }
        
        int rowsection = (int) row / 3;
        int colsection = (int) col / 3;
        
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (board[rowsection * 3 + x][colsection*3 + y] == num) {
                    valid = false;
                }
            }
        }
        return valid;
    }
    
    
    public int getindex(int row, int col) {
        // this method uses index values as input, not row and col values (0-8, 0-8)
        int hint = solved_board[row][col];
        return hint;
    }


    public int[][] getBoard() {
        return board;
    }


    public int[][] getAnswer() {
        return solved_board;
    }
    
    
    public static String formatBoard(int[][] sudoku_Board) {
        String retval = "";
        String t1 = "|-----------------------|";
        String t2 = "|-------+-------+-------|";

        retval += t1 + "\n";
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0 && i != 0 && i != 8) {
                retval += t2 + "\n";
            }
            for (int j = 0; j < 9; j++) {
                if (j % 3 == 0) {
                    retval += "| ";
                }
                if (j == 8) {
                    retval += sudoku_Board[i][j] + " |\n";
                } else if (j == 0) {
                    retval += sudoku_Board[i][j] + " ";
                } else {
                    retval += sudoku_Board[i][j] + " ";
                }  
            }
        }
        retval += t2 + "\n";
        return retval;
    }


    public static void printBoard(int[][] sudoku_Board) {
        String t1 = "|-----------------------|";
        String t2 = "|-------+-------+-------|";
        System.out.println(t1);
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0 && i != 0 && i != 8) {
                System.out.println(t2);
            }
            for (int j = 0; j < 9; j++) {
                String num = Integer.toString(sudoku_Board[i][j]);
                if (num.equals("0")) {
                    num = " ";
                }
                if (j % 3 == 0) {
                    System.out.print("| ");
                }
                if (j == 8) {
                    System.out.println(num + " |");
                } else if (j == 0) {
                    System.out.print(num + " ");
                } else {
                    System.out.print(num + " ");
                }  
            }
        }
        System.out.println(t2);
    }
}




// public class SudokuGenerator {

//     public static int[][] getRandomBoard(String difficulty){
//         // Host url
//         String host = "https://sugoku.herokuapp.com/board";
//         String charset = "UTF-8";
//         String query = host + "?" + String.format("difficulty=%s", difficulty.toLowerCase());
//         // Format query for preventing encoding problems
//         JSONObject json = null;
//         int[][] board = new int[9][9];
//         try {
//             json = new GetAPIData().execute(query).get();
//             JSONArray boardArray, row;
//             for (int i = 0; i < 9; i++) {
//                 for (int j = 0; j < 9; j++) {
//                     boardArray = json.getJSONArray("board");
//                     row = boardArray.getJSONArray(i);
//                     board[i][j] = row.getInt(j);
//                 }
//             }
//             return board;

//         } catch (Exception e) {
//             e.printStackTrace();
//             if (json != null) {
//                 Log.d("THE JSON ARRAY", json.toString());
//             }
//             return board;
//         }


//         // encoding urls
//         // URLEncoder.encode(query, charset)




//     }
// }
