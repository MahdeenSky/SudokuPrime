package com.example.sudokuprime;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import java.util.Random;



public class SudokuGenerator {
    // public static void main( String[] args ) {
    //     SudokuGenerator b = new SudokuGenerator("easy");
    //     b.MakeSudoku();
    // }

    public int[][] board = new int[9][9];
    public int difficulty = 1;

    public SudokuGenerator(String difficulty) {
        if (difficulty.equals("easy")) {
            this.difficulty = 24;
        } else if (difficulty.equals("medium")) {
            this.difficulty = 16;
        } else if (difficulty.equals("hard")) {
            this.difficulty = 8;
        }
    }
    

    public int[][] MakeSudoku() {
        
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = 0;
            }
        }

        for (int i = 0; i < difficulty; i++) {

            int row = new Random().nextInt(9);
            int col = new Random().nextInt(9);
            int num = new Random().nextInt(9);

            while (board[row][col] != 0 || !(checkValid(row,col,num))) {
                row = new Random().nextInt(9);
                col = new Random().nextInt(9);
                num = new Random().nextInt(9);
            }
            board[row][col] = num;
        }
        // printSudoku();
        return board;
    }


    public Boolean checkValid(int row, int col, int num) {
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


    // public void printSudoku() {
    //     for (int i = 0; i < 9; i++) {
    //         for (int j = 0; j < 9; j++) {
    //             System.out.print(board[i][j] + "  ");
    //         }
    //         System.out.println();
    //     }
    // }
}



// public class SudokuGenerator {

//     public static int[][] getRandomBoard(String difficulty){
//         // Host url
//         String host = "https://sugoku.herokuapp.com/board";
//         String charset = "UTF-8";
//         String query = host + "?" + String.format("difficulty=%s", difficulty);
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

