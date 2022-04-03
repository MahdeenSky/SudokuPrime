package com.example.sudokuprime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_menu);

    }

    @Override
    protected void onStart() {
        super.onStart();

        // music
        SharedPreferences settings = getApplicationContext().getSharedPreferences("Options", 0);
        Boolean musicEnabled = settings.getBoolean("Music", true);
        if (musicEnabled) {
            playBGMusic();
        }
    }

    public void openDifficultySelectActivity(View v) {
        startActivity(new Intent(MainActivity.this, DifficultySelectActivity.class));
    }

    public void openOptionsActivity(View v) {
        startActivity(new Intent(MainActivity.this, OptionsActivity.class));
    }

    public void playBGMusic() {
        Intent bg = new Intent(this, BackgroundMusicService.class);
        startService(bg);
    }

}