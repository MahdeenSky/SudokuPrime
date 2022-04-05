package com.example.sudokuprime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences settings = getApplicationContext().getSharedPreferences("Options", 0);
        Boolean musicEnabled = settings.getBoolean("Music", true);
        Boolean soundEnabled = settings.getBoolean("Sound", true);
        if (!musicEnabled) {
            Button musicBtn = findViewById(R.id.buttonMusic);
            musicBtn.setText("Music ✗");
        }
        if (!soundEnabled) {
            Button soundBtn = findViewById(R.id.buttonSound);
            soundBtn.setText("Sound ✗");
        }
        
    }

    public void toggleSound(View v) {
        Button soundBtn = findViewById(R.id.buttonSound);
        // change text of button to "Music ✗"" or "Music ✓"
        if (soundBtn.getText().equals("Sound ✗")) {
            soundBtn.setText("Sound ✓");
        } else {
            soundBtn.setText("Sound ✗");
        }

        SharedPreferences settings = getApplicationContext().getSharedPreferences("Options", 0);
        Boolean soundEnabled = settings.getBoolean("Sound", true);

        if (soundEnabled) {
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("Sound", false);
            editor.apply();
            // turn off system sound
            AudioManager manager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            manager.setStreamVolume(AudioManager.STREAM_ALARM, 0, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
        } else {
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("Sound", true);
            editor.apply();
            // turn on system sound
            AudioManager manager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            manager.setStreamVolume(AudioManager.STREAM_ALARM, manager.getStreamMaxVolume(AudioManager.STREAM_ALARM), AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
        }
    }

    public void toggleMusic(View v) {
        Button musicBtn = findViewById(R.id.buttonMusic);
        // change text of button to "Music ✗"" or "Music ✓"
        if (musicBtn.getText().equals("Music ✗")) {
            musicBtn.setText("Music ✓");
        } else {
            musicBtn.setText("Music ✗");
        }

        // fetching the current option for music
        SharedPreferences settings = getApplicationContext().getSharedPreferences("Options", 0);
        Boolean musicEnabled = settings.getBoolean("Music", true);

        if (musicEnabled) {
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("Music", false);
            editor.apply();
            stopService(new Intent(this, BackgroundMusicService.class));
        } else {
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("Music", true);
            editor.apply();
            playBGMusic();
        }
    }

    public void Back(View v) {
        onBackPressed();
    }

    public void playBGMusic() {
        Intent bg = new Intent(this, BackgroundMusicService.class);
        startService(bg);
    }

}