package com.example.sudokuprime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WinScnActivity extends AppCompatActivity {

    private Button playAgainBTN;
    private Button homeBTN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win_scn);

        playAgainBTN = findViewById(R.id.playAgainButton);
        homeBTN = findViewById(R.id.homeButton);

        playAgainBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toDifficultySelect();
            }
        });
        homeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toHomeMenuActivity();
            }
        });
    }
    private void toHomeMenuActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private void toDifficultySelect() {
        Intent intent = new Intent(this, DifficultySelectActivity.class);
        startActivity(intent);
    }
}
