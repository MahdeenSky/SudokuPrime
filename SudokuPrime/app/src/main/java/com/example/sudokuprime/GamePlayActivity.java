package com.example.sudokuprime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GamePlayActivity extends AppCompatActivity {

    private SudokuBoard gameBoard;
    private SudokuSolver gameBoardSolver;
    private SudokuGenerator gameBoardGenerator;

    private Button enterBTN;
    private int[][] puzzle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        gameBoard = findViewById(R.id.SudokuBoard);
        gameBoardSolver = gameBoard.getSolver();
        //TODO: have difficulty string change depending on difficulty selected in DifficultySelctActivity
        puzzle = gameBoardGenerator.getRandomBoard("easy");
        enterBTN = findViewById(R.id.enterButton);
        enterBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWinScnActivity();
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        gameBoardSolver.importBoard(puzzle);
        puzzle = SudokuWinCheck.copyBoard(puzzle);
    }

    private void openWinScnActivity() {
        Intent intent = new Intent(this, WinScnActivity.class);
        int[][] currBoard = gameBoardSolver.getBoard();
        if(SudokuWinCheck.isBoardSolved(currBoard))
            startActivity(intent);

    }

    private boolean isPuzzlePosZero() {
        int selRow = gameBoardSolver.getSelectedRow();
        int selCol = gameBoardSolver.getSelectedColumn();
        if(selRow == -1 && selCol == -1) {
            return false;
        }
        return puzzle[selRow - 1][selCol - 1] == 0;
    }

    public void BTNOnePress(View view) {
        if(isPuzzlePosZero()) {
            gameBoardSolver.setNumberPos(1);
            gameBoard.invalidate();
        }
    }

    public void BTNTwoPress(View view) {
        if(isPuzzlePosZero()) {
            gameBoardSolver.setNumberPos(2);
            gameBoard.invalidate();
        }
    }

    public void BTNThreePress(View view) {
        if(isPuzzlePosZero()) {
            gameBoardSolver.setNumberPos(3);
            gameBoard.invalidate();
        }
    }

    public void BTNFourPress(View view) {
        if(isPuzzlePosZero()) {
            gameBoardSolver.setNumberPos(4);
            gameBoard.invalidate();
        }
    }

    public void BTNFivePress(View view) {
        if(isPuzzlePosZero()) {
            gameBoardSolver.setNumberPos(5);
            gameBoard.invalidate();
        }
    }

    public void BTNSixPress(View view) {
        if(isPuzzlePosZero()) {
            gameBoardSolver.setNumberPos(6);
            gameBoard.invalidate();
        }
    }

    public void BTNSevenPress(View view) {
        if(isPuzzlePosZero()) {
            gameBoardSolver.setNumberPos(7);
            gameBoard.invalidate();
        }
    }

    public void BTNEightPress(View view) {
        if(isPuzzlePosZero()) {
            gameBoardSolver.setNumberPos(8);
            gameBoard.invalidate();
        }
    }

    public void BTNNinePress(View view) {
        if(isPuzzlePosZero()) {
            gameBoardSolver.setNumberPos(9);
            gameBoard.invalidate();
        }
    }
    public void clear(View view) {
        gameBoardSolver.importBoard(puzzle);
        gameBoard.invalidate();
        puzzle = SudokuWinCheck.copyBoard(puzzle);
    }

}
