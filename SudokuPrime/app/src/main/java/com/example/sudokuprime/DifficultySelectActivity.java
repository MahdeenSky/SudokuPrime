package com.example.sudokuprime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

public class DifficultySelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty_select);
    }

    public void Back(View v) {
        onBackPressed();
    }

    public void playGame(View v) {
        Button b = (Button) v;
        String buttonText = b.getText().toString();
        // passing in the value of difficulty to the gameplay activity
        if (!buttonText.equals("Back ⇐")) {
            Intent i = new Intent(this, GamePlayActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("difficulty", buttonText);
            i.putExtras(bundle);
            startActivity(i);
        }
    }
}