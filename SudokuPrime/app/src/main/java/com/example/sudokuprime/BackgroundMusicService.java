package com.example.sudokuprime;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;

public class BackgroundMusicService extends Service {
    private static final String TAG = null;
    MediaPlayer player;

    public IBinder onBind(Intent arg0) {

        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();


        player = MediaPlayer.create(this, R.raw.bg);
        player.setLooping(true); // Set looping
        player.setVolume(100, 100);

    }

    public int onStartCommand(Intent intent, int flags, int startId) {

        player.start();

        return Service.START_STICKY;
    }

    public IBinder onUnBind(Intent arg0) {
        // TODO Auto-generated method stub

        return null;
    }

    @Override
    public void onDestroy() {

        player.stop();
        player.reset();
        player.release();
    }

}
