package com.example.tmhuskies.turnmeon;

import android.media.MediaPlayer;

public class ResponseOption {

    private String title;
    private MediaPlayer audio;

    public ResponseOption(String title, MediaPlayer audio) {
        this.title = title;
        this.audio = audio;
    }

    public String getTitle() {
        return title;
    }

    public MediaPlayer getAudio() {
        return audio;
    }
}
