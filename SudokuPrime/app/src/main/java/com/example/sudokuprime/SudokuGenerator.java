package com.example.sudokuprime;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;


public class SudokuGenerator {

    public static int[][] getRandomBoard(String difficulty){
        // Host url
        String host = "https://sugoku.herokuapp.com/board";
        String charset = "UTF-8";
        String query = host + "?" + String.format("difficulty=%s", difficulty.toLowerCase());
        // Format query for preventing encoding problems
        JSONObject json = null;
        int[][] board = new int[9][9];
        try {
            json = new GetAPIData().execute(query).get();
            JSONArray boardArray, row;
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    boardArray = json.getJSONArray("board");
                    row = boardArray.getJSONArray(i);
                    board[i][j] = row.getInt(j);
                }
            }
            return board;

        } catch (Exception e) {
            e.printStackTrace();
            if (json != null) {
                Log.d("THE JSON ARRAY", json.toString());
            }
            return board;
        }


        // encoding urls
        // URLEncoder.encode(query, charset)




    }
}
