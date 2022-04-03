package com.example.sudokuprime;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test_winCheck01() {
        int[][] board = {{1, 2, 3, 4, 5, 6, 7, 8, 9},
                {2, 3, 4, 5, 6, 7, 8, 9, 1},
                {3, 4, 5, 6, 7, 8, 9, 1, 2},
                {4, 5, 6, 7, 8, 9, 1, 2, 3},
                {5, 6, 7, 8, 9, 1, 2, 3, 4},
                {6, 7, 8, 9, 1, 2, 3, 4, 5},
                {7, 8, 9, 1, 2, 3, 4, 5, 6},
                {8, 9, 1, 2, 3, 4, 5, 6, 7},
                {9, 1, 2, 3, 4, 5, 6, 7, 8}};
        boolean row = SudokuWinCheck.rowCheck(board);
        boolean col = SudokuWinCheck.colCheck(board);
        boolean box = SudokuWinCheck.boxCheck(board);
        boolean sol = SudokuWinCheck.isBoardSolved(board);
        assertTrue("rowCheck returned false on correctly solved rows", row);
        assertTrue("colCheck returned false on correctly solved columns", col);
        assertFalse("boxCheck returned true on incorrectly solved boxes", box);
        assertFalse("isBoardSolved returned true on incorrectly solved board", sol);
    }

    @Test
    public void test_winCheck02() {
        int[][] board = {{1, 2, 3, 4, 5, 6, 7, 8, 9},
                {2, 3, 4, 5, 6, 7, 8, 9, 1},
                {3, 4, 0, 6, 7, 0, 9, 1, 0},
                {4, 5, 6, 7, 8, 9, 1, 2, 3},
                {5, 6, 7, 8, 9, 1, 2, 3, 4},
                {6, 0, 8, 9, 0, 2, 3, 0, 5},
                {7, 8, 9, 1, 2, 3, 4, 5, 6},
                {8, 9, 1, 2, 3, 4, 5, 6, 7},
                {0, 1, 2, 0, 4, 5, 0, 7, 8}};
        boolean row = SudokuWinCheck.rowCheck(board);
        boolean col = SudokuWinCheck.colCheck(board);
        boolean box = SudokuWinCheck.boxCheck(board);
        boolean sol = SudokuWinCheck.isBoardSolved(board);
        assertFalse("rowCheck returned true on a board containing 0s", row);
        assertFalse("colCheck returned true on a board containing 0s", col);
        assertFalse("boxCheck returned true on a board containing 0s", box);
        assertFalse("isBoardSolved returned true on a board containing 0s", sol);
    }
    @Test
    public void test_winCheck03() {
        int[][] board = {{7, 3, 5, 6, 1, 4, 8, 9, 2},
                {8, 4, 2, 9, 7, 3, 5, 6, 1},
                {9, 6, 1, 2, 8, 5, 3, 7, 4},
                {2, 8, 6, 3, 4, 9, 1, 5, 7},
                {4, 1, 3, 8, 5, 7, 9, 2, 6},
                {5, 7, 9, 1, 2, 6, 4, 3, 8},
                {1, 5, 7, 4, 9, 2, 6, 8, 3},
                {6, 9, 4, 7, 3, 8, 2, 1, 5},
                {3, 2, 8, 5, 6, 1, 7, 4, 9}};
        boolean row = SudokuWinCheck.rowCheck(board);
        boolean col = SudokuWinCheck.colCheck(board);
        boolean box = SudokuWinCheck.boxCheck(board);
        boolean sol = SudokuWinCheck.isBoardSolved(board);
        assertTrue("rowCheck returned false on correctly solved rows", row);
        assertTrue("colCheck returned false on correctly solved columns", col);
        assertTrue("boxCheck returned false on correctly solved boxes", box);
        assertTrue("isBoardSolved returned false on correctly solved board", sol);
    }


    @Test
    public void test_copyBoard01() {
        int[][] board = new int[9][9];
        int[][] boardCopy = SudokuWinCheck.copyBoard(board);
        assertArrayEquals("boardCopy did not copy board correctly", board, boardCopy);
        assertNotSame("boardCopy did not create a new object", board, boardCopy);
    }

    @Test
    public void testcopyBoard02() {
        int[][] boardExpected = {{7, 3, 5, 6, 1, 4, 8, 9, 2},
                {8, 4, 2, 9, 7, 3, 5, 6, 1},
                {9, 6, 1, 2, 8, 5, 3, 7, 4},
                {2, 8, 6, 3, 4, 9, 1, 5, 7},
                {4, 1, 3, 8, 5, 7, 9, 2, 6},
                {5, 7, 9, 1, 2, 6, 4, 3, 8},
                {1, 5, 7, 4, 9, 2, 6, 8, 3},
                {6, 9, 4, 7, 3, 8, 2, 1, 5},
                {3, 2, 8, 5, 6, 1, 7, 4, 9}};
        int[][] boardActual = SudokuWinCheck.copyBoard(boardExpected);
        assertArrayEquals("boardCopy did not copy board correctly", boardExpected, boardActual);
        assertNotSame("boardCopy did not create a new object", boardExpected, boardActual);
    }

    @Test
    public void sudoku_solver01() {
        SudokuSolver ss = new SudokuSolver();
        int selRow, selCol;
        selRow = ss.getSelectedRow();
        selCol = ss.getSelectedColumn();
        assertEquals(-1, selRow);
        assertEquals(-1, selCol);
        ss.setSelectedRow(3);
        ss.setSelectedColumn(3);
        selRow = ss.getSelectedRow();
        selCol = ss.getSelectedColumn();
        assertEquals(3, selRow);
        assertEquals(3, selCol);
    }

    @Test
    public void sudoku_solver02() {
        int[][] originalBoard = new int[9][9];
        int[][] currBoard;
        SudokuSolver ss = new SudokuSolver();
        ss.importBoard(originalBoard);
        currBoard = ss.getBoard();
        originalBoard = SudokuWinCheck.copyBoard(originalBoard);
        ss.setNumberPos(3);
        assertArrayEquals("board attribute was updated by setNumPos for unattainable indices", originalBoard, currBoard);
    }

    @Test
    public void sudoku_solver03() {
        int[][] originalBoard = {{0, 3, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 5, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0}};
        int[][] currBoard;
        SudokuSolver ss = new SudokuSolver();
        ss.importBoard(originalBoard);
        currBoard = ss.getBoard();
        originalBoard = SudokuWinCheck.copyBoard(originalBoard);
        ss.setSelectedRow(3);
        ss.setSelectedColumn(3);
        ss.setNumberPos(9);
        assertEquals("setNumPos did not update the board attribute's pos", 9, currBoard[2][2]);
        assertNotEquals("board attribute does not differ from original board after setNumPos call",
                originalBoard, currBoard);
        ss.setSelectedRow(1);
        ss.setSelectedColumn(2);
        ss.setNumberPos(3);
        assertEquals("setNumPos(3) did not change the board attribute's position (1, 2) from 3 to 0",
                0, currBoard[0][1]);
        ss.setSelectedRow(6);
        ss.setSelectedColumn(6);
        ss.setNumberPos(3);
        assertEquals("setNumPos(3) did not change the board attribute's position (6, 6) from 5 to 3",
                3, currBoard[5][5]);
    }
}
