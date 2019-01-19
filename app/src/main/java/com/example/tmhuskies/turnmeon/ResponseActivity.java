package com.example.tmhuskies.turnmeon;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ResponseActivity extends AppCompatActivity {

    private ArrayList<ResponseOption> fileNames;
    private ArrayAdapter<ResponseOption> arrayAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);

        arrayAdapter = new ArrayAdapter<>(this, R.layout.activity_response);
        listView = (ListView) findViewById(R.id.response_list);

        fileNames = new ArrayList<>();

        MediaPlayer audio1 = MediaPlayer.create(this, R.raw.smrt_door_closing);
        fileNames.add(new ResponseOption(AudioFiles.track1, audio1));
        MediaPlayer audio2 = MediaPlayer.create(this, R.raw.gmod_version_1);
        fileNames.add(new ResponseOption(AudioFiles.track2, audio2));
        MediaPlayer audio3 = MediaPlayer.create(this, R.raw.rick_roll);
        fileNames.add(new ResponseOption(AudioFiles.track3, audio3));
        MediaPlayer audio4 = MediaPlayer.create(this, R.raw.my_name_jeff);
        fileNames.add(new ResponseOption(AudioFiles.track4, audio4));
        MediaPlayer audio5 = MediaPlayer.create(this, R.raw.you_on_kazoo);
        fileNames.add(new ResponseOption(AudioFiles.track5, audio5));

        arrayAdapter = new ResponseAdapter(fileNames, this);
        listView.setAdapter(arrayAdapter);


    }

}
