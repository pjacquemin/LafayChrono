package com.example.patrick.lafaychrono_lite;

import android.media.MediaPlayer;
import android.net.Uri;

public class MediaPlayerSingleton {
    private static MediaPlayerSingleton instance = null;
    private MediaPlayer media_player;

    private MediaPlayerSingleton(MainActivity activity){
        Uri notification = Uri.parse("android.resource://"
                + activity.getPackageName() + "/" + R.raw.countdown_finished);
        media_player = MediaPlayer.create(activity.getApplicationContext(), notification);
    }

    public static MediaPlayerSingleton getInstance(MainActivity activity){
        if(instance == null)
        {
            instance = new MediaPlayerSingleton(activity);
        }

        return instance;
    }

    public MediaPlayer getMediaPlayer(){
        return media_player;
    }
}
