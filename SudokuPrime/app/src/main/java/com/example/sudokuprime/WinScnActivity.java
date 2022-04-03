package com.example.sudokuprime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WinScnActivity extends AppCompatActivity {

    private TextView timedText;
    private Button playAgainBTN;
    private Button homeBTN;

    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win_scn);

        timedText = findViewById(R.id.timedText);
        playAgainBTN = findViewById(R.id.playAgainButton);
        homeBTN = findViewById(R.id.homeButton);

        this.setTimedText(timedText);

        playVictoryMusic();

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

    public void toHomeMenuActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        player.stop();
        player.release();
        startActivity(intent);
    }
    public void toDifficultySelect() {
        Intent intent = new Intent(this, DifficultySelectActivity.class);
        player.stop();
        player.release();
        playBGMusic();
        startActivity(intent);
    }

    public void setTimedText(TextView tv) {
        Bundle bundle = getIntent().getExtras();
        int timeTaken = bundle.getInt("timeTaken");
        Log.i("timeTaken", String.valueOf(timeTaken));
        tv.setText(String.format("You took %d seconds to solve the Sudoku!", timeTaken));
    }

    public void playVictoryMusic() {
        stopService(new Intent(this, BackgroundMusicService.class));
        player = MediaPlayer.create(this, R.raw.victory);
        player.setVolume(30, 30);
        player.start();
    }

    public void playBGMusic() {
        Intent bg = new Intent(this, BackgroundMusicService.class);
        startService(bg);
    }
}
