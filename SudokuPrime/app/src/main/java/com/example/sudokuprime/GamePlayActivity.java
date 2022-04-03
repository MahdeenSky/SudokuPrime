package com.example.sudokuprime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

public class GamePlayActivity extends AppCompatActivity {

    private SudokuBoard gameBoard;
    private SudokuSolver gameBoardSolver;
    private SudokuGenerator gameBoardGenerator;

    private Button enterBTN;
    private int[][] puzzle;

    private Chronometer chronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        // get the difficulty
        Bundle bundle = getIntent().getExtras();
        String difficulty = bundle.getString("difficulty");
        Log.i("DIfficulty", difficulty);

        gameBoard = findViewById(R.id.SudokuBoard);
        gameBoardSolver = gameBoard.getSolver();
        chronometer = findViewById(R.id.chronometer);
        //TODO: have difficulty string change depending on difficulty selected in DifficultySelectActivity
        puzzle = gameBoardGenerator.getRandomBoard(difficulty);
    }
    @Override
    protected void onStart() {
        super.onStart();
        gameBoardSolver.importBoard(puzzle);
        puzzle = SudokuWinCheck.copyBoard(puzzle);

        // timer
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
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
        gameBoardSolver.setNumberPos(0);
    }

    public void reset(View view) {
        gameBoardSolver.importBoard(puzzle);
        gameBoard.invalidate();
        puzzle = SudokuWinCheck.copyBoard(puzzle);
    }

    public void solve(View view) {
        gameBoardSolver.getEmptyBoxIndexes();

        SolveBoardThread solveBoardThread = new SolveBoardThread();
        new Thread(solveBoardThread).start();

        chronometer.stop();
        gameBoard.invalidate();
    }

    public void back(View v) {
        onBackPressed();
    }


    // parsing the timer
    public static int getSecondsFromDurationString(String value){

        String [] parts = value.split(":");

        // Wrong format, no value for you.
        if(parts.length < 2 || parts.length > 3)
            return 0;

        int seconds = 0, minutes = 0, hours = 0;

        if(parts.length == 2){
            seconds = Integer.parseInt(parts[1]);
            minutes = Integer.parseInt(parts[0]);
        }
        else if(parts.length == 3){
            seconds = Integer.parseInt(parts[2]);
            minutes = Integer.parseInt(parts[1]);
            hours = Integer.parseInt(parts[0]);
        }

        return seconds + (minutes*60) + (hours*3600);
    }

    class SolveBoardThread implements Runnable {
        @Override
        public void run() {
            gameBoardSolver.solveVisually(gameBoard);
        }
    }



}
