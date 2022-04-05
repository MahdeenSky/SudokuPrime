package com.example.sudokuprime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

import java.util.ArrayList;

public class GamePlayActivity extends AppCompatActivity {

    private SudokuBoard gameBoard;
    private SudokuSolver gameBoardSolver;

    private int[][] puzzle;
    private Button currentBtn;
    private int BtnNum;

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

        puzzle = SudokuGenerator.getRandomBoard(difficulty);
    }
    @Override
    protected void onStart() {
        super.onStart();

        gameBoardSolver.importBoard(puzzle);
        startTimer();
    }

    private void startTimer() {
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
    }

    private Boolean checkSudoku() {
        return SudokuWinCheck.isBoardSolved(gameBoardSolver.getBoard()) && isNotIndexHint() && isNotOriginalIndex();
    }

    private void openWinScnActivity() {
        if (isNotSolved()) {
            chronometer.stop();
            int seconds = GamePlayActivity.getSecondsFromDurationString(chronometer.getText().toString());
            Intent i = new Intent(this, WinScnActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("timeTaken", seconds);
            i.putExtras(bundle);
            startActivity(i);
        }
    }

    private boolean isNotIndexHint() {
        int selRow = gameBoardSolver.getSelectedRow();
        int selCol = gameBoardSolver.getSelectedColumn();
//        Log.i("Selected Indexes", String.valueOf(selRow) + String.valueOf(selCol));
        ArrayList<Integer> currentIndex = new ArrayList<>();
        currentIndex.add(selRow - 1);
        currentIndex.add(selCol - 1);
//        Log.i("is it a Hint", Boolean.toString(gameBoardSolver.hintIndex.stream().anyMatch(currentIndex::equals)));
//        Log.i("Hints", gameBoardSolver.hintIndex.toString());
        return gameBoardSolver.hintIndex.stream().noneMatch(currentIndex::equals);
    }

    private boolean isNotSolved() {
        return !gameBoardSolver.isSolved;
    }

    private boolean isNotOriginalIndex() {
        return !gameBoardSolver.originalIndex();
    }

    public void onNumBtnClicked(View v) {
        if (isNotIndexHint() && isNotSolved() && isNotOriginalIndex()) {
            currentBtn = (Button) v;
            BtnNum = Integer.parseInt(currentBtn.getText().toString());
            gameBoardSolver.setNumberPos(BtnNum);
            gameBoard.invalidate();
        }
        if (checkSudoku()) {
            openWinScnActivity();
        }
    }
//    public void BTNOnePress(View view) {
//        if(isPuzzlePosZero() && !isIndexHint() && !isGameSolved()) {
//            gameBoardSolver.setNumberPos(1);
//            gameBoard.invalidate();
//        }
//        if (checkSudoku()) {
//            openWinScnActivity();
//        }
//    }
//
//    public void BTNTwoPress(View view) {
//        if(isPuzzlePosZero() && !isIndexHint() && !isGameSolved()) {
//            gameBoardSolver.setNumberPos(2);
//            gameBoard.invalidate();
//        }
//        if (checkSudoku()) {
//            openWinScnActivity();
//        }
//    }
//
//    public void BTNThreePress(View view) {
//        if(isPuzzlePosZero() && !isIndexHint() && !isGameSolved()) {
//            gameBoardSolver.setNumberPos(3);
//            gameBoard.invalidate();
//        }
//        if (checkSudoku()) {
//            openWinScnActivity();
//        }
//    }
//
//    public void BTNFourPress(View view) {
//        if(isPuzzlePosZero() && !isIndexHint() && !isGameSolved()) {
//            gameBoardSolver.setNumberPos(4);
//            gameBoard.invalidate();
//        }
//        if (checkSudoku()) {
//            openWinScnActivity();
//        }
//    }
//
//    public void BTNFivePress(View view) {
//        if(isPuzzlePosZero() && !isIndexHint() && !isGameSolved()) {
//            gameBoardSolver.setNumberPos(5);
//            gameBoard.invalidate();
//        }
//        if (checkSudoku()) {
//            openWinScnActivity();
//        }
//    }
//
//    public void BTNSixPress(View view) {
//        if(isPuzzlePosZero() && !isIndexHint() && !isGameSolved()) {
//            gameBoardSolver.setNumberPos(6);
//            gameBoard.invalidate();
//        }
//        if (checkSudoku()) {
//            openWinScnActivity();
//        }
//    }
//
//    public void BTNSevenPress(View view) {
//        if(isPuzzlePosZero() && !isIndexHint() && !isGameSolved()) {
//            gameBoardSolver.setNumberPos(7);
//            gameBoard.invalidate();
//        }
//        if (checkSudoku()) {
//            openWinScnActivity();
//        }
//    }
//
//    public void BTNEightPress(View view) {
//        if(isPuzzlePosZero() && !isIndexHint() && !isGameSolved()) {
//            gameBoardSolver.setNumberPos(8);
//            gameBoard.invalidate();
//        }
//        if (checkSudoku()) {
//            openWinScnActivity();
//        }
//    }
//
//    public void BTNNinePress(View view) {
//        if(isPuzzlePosZero() && !isIndexHint()  && !isGameSolved()) {
//            gameBoardSolver.setNumberPos(9);
//            gameBoard.invalidate();
//        }
//        if (checkSudoku()) {
//            openWinScnActivity();
//        }
//    }

    public void clear(View view) {
        if (isNotIndexHint() && isNotSolved() && isNotOriginalIndex()) {
            gameBoardSolver.setNumberPos(0);
            gameBoard.invalidate();
        }
    }

    public void reset(View view) {
        gameBoardSolver.isSolved = false;
        gameBoardSolver.importBoard(puzzle);
        gameBoardSolver.resetHintIndex();
        gameBoard.invalidate();
        startTimer();
    }

    public void solve(View view) {
        if (isNotSolved()) {
            gameBoardSolver.board = gameBoardSolver.originalBoard;
            gameBoardSolver.getEmptyBoxIndexes();

            SolveBoardThread solveBoardThread = new SolveBoardThread();
            new Thread(solveBoardThread).start();

            chronometer.stop();
            gameBoard.invalidate();
        }
    }

    public void back(View v) {
        onBackPressed();
    }

    public void hint(View v) {
        if (isNotSolved()) {
        gameBoardSolver.getEmptyBoxIndexes();

        if (gameBoardSolver.emptyBoxIndex.size() != 0) {
            gameBoardSolver.setHint();
            }
        }
    }


    // parsing the timer
    public static int getSecondsFromDurationString(String value){

        String [] parts = value.split(":");

        // Wrong format, no value for you.
        if(parts.length < 2 || parts.length > 3)
            return 0;

        int seconds, minutes, hours = 0;

        if(parts.length == 2){
            seconds = Integer.parseInt(parts[1]);
            minutes = Integer.parseInt(parts[0]);
        }
        else {
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
