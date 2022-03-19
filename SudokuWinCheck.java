package com.example.sudokuapp1022;

import java.util.HashSet;

public class SudokuWinCheck {

    private static final int GRID_SIZE = 9;

    /**
     *isBoardSolved
     * @param board
     * Pre-cond: assumes all entries in board are from 0 - 9
     * @return true if board is solved by traditional Sudoku rules (col, row, box have uniqye numbers 1-9.
     * Otherwise, false.
     * Will change to work with 2-D arr of Strings instead
     * Might change to work with instances of Sudoku objects, the array being an attr
     */
    public static boolean isBoardSolved(int[][] board) {
        return rowCheck(board) && colCheck(board) && boxCheck(board);
    }

    public static boolean rowCheck(int [][] board) {
        HashSet<Integer> numsInRow;
        int num;
        for(int row = 0; row < GRID_SIZE; row++) {
            numsInRow = new HashSet<>();
            for(int col = 0; col < GRID_SIZE; col++) {
                num = board[row][col];
                if(num == 0 || !numsInRow.add(num)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean colCheck(int [][] board) {
        HashSet<Integer> numsInCol;
        int num;
        for(int col = 0; col < GRID_SIZE; col++) {
            numsInCol = new HashSet<>();
            for(int row = 0; row < GRID_SIZE; row++) {
                num = board[row][col];
                if(num == 0 || !numsInCol.add(num)) {
                    return false;
                }
            }
        }
        return true;
    }
    public static boolean boxCheck(int[][] board) {
        HashSet<Integer> numsInBox;
        int num, boxRow, boxCol;
        for(int boxNum = 0; boxNum < GRID_SIZE; boxNum++) {
            boxRow = boxNum - boxNum % 3;
            boxCol = 3 * boxNum % 3;
            numsInBox = new HashSet<>();
            for(int i = boxRow; i < boxRow + 3; i++) {
                for(int j = boxCol; j < boxCol + 3; j++) {
                    num = board[i][j];
                    if(num == 0 || !numsInBox.add(num)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
