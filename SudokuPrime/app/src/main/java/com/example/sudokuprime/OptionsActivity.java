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

    SharedPreferences settings;
    SharedPreferences.Editor editor;

    Boolean musicEnabled;
    Boolean soundEnabled;
    Boolean visualizeSolvingEnabled;
    Button musicBtn;
    Button soundBtn;
    Button visualizeSolvingBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        settings = getApplicationContext().getSharedPreferences("Options", 0);
        editor = settings.edit();

        musicBtn = findViewById(R.id.buttonMusic);
        soundBtn = findViewById(R.id.buttonSound);
        visualizeSolvingBtn = findViewById(R.id.buttonVisualizer);
    }

    @Override
    protected void onStart() {
        super.onStart();

        musicEnabled = settings.getBoolean("Music", true);
        soundEnabled = settings.getBoolean("Sound", true);
        visualizeSolvingEnabled = settings.getBoolean("visualizeSolving", false);
        if (!musicEnabled) {
            musicBtn.setText("Music ✗");
        }
        if (!soundEnabled) {
            soundBtn.setText("Sound ✗");
        }
        if (visualizeSolvingEnabled) {
            visualizeSolvingBtn.setText("Visualize Solving ✓");
        }
    }

    public void toggleSound(View v) {
        // change text of button to "Music ✗"" or "Music ✓"
        if (soundBtn.getText().equals("Sound ✗")) {
            soundBtn.setText("Sound ✓");
            editor.putBoolean("Sound", true);
            editor.apply();
            // turn on system sound
            AudioManager manager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            manager.setStreamVolume(AudioManager.STREAM_ALARM, manager.getStreamMaxVolume(AudioManager.STREAM_ALARM), AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);

        } else {
            soundBtn.setText("Sound ✗");
            editor.putBoolean("Sound", false);
            editor.apply();
            // turn off system sound
            AudioManager manager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            manager.setStreamVolume(AudioManager.STREAM_ALARM, 0, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
        }
    }

    public void toggleMusic(View v) {
        // change text of button to "Music ✗"" or "Music ✓"
        if (musicBtn.getText().equals("Music ✗")) {
            musicBtn.setText("Music ✓");
            editor.putBoolean("Music", true);
            editor.apply();
            playBGMusic();

        } else {
            musicBtn.setText("Music ✗");
            editor.putBoolean("Music", false);
            editor.apply();
            stopService(new Intent(this, BackgroundMusicService.class));
        }
    }

    public void Back(View v) {
        onBackPressed();
    }

    public void playBGMusic() {
        Intent bg = new Intent(this, BackgroundMusicService.class);
        startService(bg);
    }

    public void toggleVisualizeSolving(View v) {
        if (visualizeSolvingBtn.getText().equals("Visualize Solving ✓")) {
            visualizeSolvingBtn.setText("Visualize Solving ✗");
            editor.putBoolean("visualizeSolving", false);
            editor.apply();

        } else {
            visualizeSolvingBtn.setText("Visualize Solving ✓");
            editor.putBoolean("visualizeSolving", true);
            editor.apply();
        }

    }

}