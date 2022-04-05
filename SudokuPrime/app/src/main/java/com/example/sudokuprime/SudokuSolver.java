package com.example.sudokuprime;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SudokuSolver {

    int[][] board;
    int[][] originalBoard;
    int[][] solvedBoard;
    ArrayList<ArrayList<Object>> emptyBoxIndex;
    Set<ArrayList<Integer>> hintIndex = new HashSet<>();

    private static int selected_row;
    private static int selected_column;

    Boolean isSolved = false;
    Boolean isVisualized = false;
    Boolean isVisualizing = false;

    SudokuSolver() {
        selected_row = -1;
        selected_column = -1;

        board = new int[9][9];
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                board[r][c] = 0;
            }
        }

        emptyBoxIndex = new ArrayList<>();
    }

    public void importBoard(int[][] board_) {

        this.board = deepCopy(board_);
        this.originalBoard = deepCopy(board_);
        this.solvedBoard = deepCopy(board_);
        this.solve();
    }

    public void getEmptyBoxIndexes () {
        emptyBoxIndex = new ArrayList<>();
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c< 9; c++) {
                if (this.board[r][c] == 0) {
                    this.emptyBoxIndex.add(new ArrayList<>());
                    this.emptyBoxIndex.get(this.emptyBoxIndex.size() - 1).add(r);
                    this.emptyBoxIndex.get(this.emptyBoxIndex.size() - 1).add(c);
                }
            }
        }
    }

    private boolean check(int[][] board, int row, int col) {
        if (board[row][col] > 0) {
            for (int i = 0; i < 9; i++) {
                if (board[i][col] == board[row][col] && row != i) {
                    return false;
                }
                if (board[row][i] == board[row][col] && col != i) {
                    return false;
                }
            }

            int boxRow = row / 3;
            int boxCol = col / 3;

            for (int r = boxRow * 3; r < 3 * boxRow + 3; r++) {
                for (int c = boxCol * 3; c < 3 * boxCol + 3; c++) {
                    if (board[r][c] == board[row][col] && row != r && col != c) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean solveVisually(SudokuBoard display) {
        this.isSolved = true;
        int row = -1;
        int col = -1;

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (this.board[r][c] == 0) {
                    row = r;
                    col = c;
                    break;
                }
            }
        }

        if (row == -1 || col == -1) {
            return true;
        }

        for (int i = 1; i < 10; i++) {
            this.board[row][col] = i;
            if (this.isVisualized) {
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            display.invalidate();

            if (check(this.board, row, col)) {
                if (solveVisually(display)) {
                    return true;
                }
            }
            this.board[row][col] = 0;
        }
        return false;
    }

    public Boolean solve() {
        int row = -1;
        int col = -1;

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (this.solvedBoard[r][c] == 0) {
                    row = r;
                    col = c;
                    break;
                }
            }
        }

        if (row == -1 || col == -1) {
            return true;
        }

        for (int i = 1; i < 10; i++) {
            this.solvedBoard[row][col] = i;
            if (check(this.solvedBoard, row, col)) {
                if (solve()) {
//                    Log.i("ARRAY", Arrays.deepToString(this.solvedBoard));
                    return true;
                }
            }
            this.solvedBoard[row][col] = 0;
        }
        return false;
    }

    public void resetBoard() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                board[r][c] = 0;
            }
        }
        this.emptyBoxIndex = new ArrayList<>();
    }

    public void setNumberPos(int num) {
        if (selected_row != -1 && selected_column != -1) {
            if (this.board[selected_row - 1][selected_column - 1] == num) {
                this.board[selected_row - 1][selected_column - 1] = 0;
            } else {
                this.board[selected_row - 1][selected_column - 1] = num;
            }
        }
    }

    public static int[][] deepCopy(int[][] original) {
        if (original == null) {
            return null;
        }

        final int[][] result = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            result[i] = Arrays.copyOf(original[i], original[i].length);
            // For Java versions prior to Java 6 use the next:
            // System.arraycopy(original[i], 0, result[i], 0, original[i].length);
        }
        return result;
    }

    public void setHint() {
        // get a random array from emptyBoxIndex
        if (this.emptyBoxIndex.size() != 0) {
            int randomIndex = (int) (Math.random() * this.emptyBoxIndex.size());
            int row = (int) this.emptyBoxIndex.get(randomIndex).get(0);
            int col = (int) this.emptyBoxIndex.get(randomIndex).get(1);
            ArrayList<Integer> currentIndex = new ArrayList<>(Arrays.asList(row, col));
            this.hintIndex.add(currentIndex);
            this.board[row][col] = this.solvedBoard[row][col];
        }
    }

    public Boolean originalIndex() {
        if (selected_row != -1 && selected_column != -1) {
            return this.originalBoard[selected_row - 1][selected_column - 1] != 0;
        }
        return false;
    }

    public Boolean isValidIndex(int r, int c) {
        return (r > -1) && (r < 9) && (c > -1) && (c < 9);
    }


    public int[][] getBoard() {
        return this.board;
    }

    public void resetHintIndex() {
        this.hintIndex = new HashSet<>();
    }

    public ArrayList<ArrayList<Object>> getEmptyBoxIndex() {
        return emptyBoxIndex;
    }

    public int getSelectedRow() {
        return selected_row;
    }

    public void setSelectedRow(int row) {
        selected_row = row;
    }

    public int getSelectedColumn() {
        return selected_column;
    }

    public void setSelectedColumn(int column) {
        selected_column = column;
    }
}
