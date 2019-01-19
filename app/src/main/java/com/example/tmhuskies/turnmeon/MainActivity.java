package com.example.tmhuskies.turnmeon;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;

import edu.cmu.pocketsphinx.Assets;
import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.RecognitionListener;
import edu.cmu.pocketsphinx.SpeechRecognizer;
import edu.cmu.pocketsphinx.SpeechRecognizerSetup;

public class MainActivity extends AppCompatActivity implements RecognitionListener {

    private SharedPreferences savedSettings;
    private static final String KWS_SEARCH = "CODE-WORD";
    private String KEYPHRASE = "hello phone";
    private String AUDIOFILE = AudioFiles.track1;
    MediaPlayer player;

    private static final int PERMISSIONS_REQUEST_RECORD_AUDIO = 1;

    private SpeechRecognizer recognizer;
    private Button responseBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        responseBtn = findViewById(R.id.responseButton);

        savedSettings = getSharedPreferences("userPref", MODE_PRIVATE);

        // Check if user granted permission to record audio.
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    PERMISSIONS_REQUEST_RECORD_AUDIO);

        }

        // Listen to Trigger Button
        Button trigger = findViewById(R.id.triggerButton);
        trigger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TriggerActivity.class));
            }
        });

        // Listen to Response Button
        Button response = findViewById(R.id.responseButton);
        response.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ResponseActivity.class));
            }
        });

        // Listen to Activate Button
        Button activate = findViewById(R.id.activateButton);
        activate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = savedSettings.getString("codeWord", null);
                String audioPath = savedSettings.getString("responseAudio", null);

                if (code == null) {
                    Toast.makeText(getApplicationContext(), "Please set your Trigger Word!",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                if (audioPath == null) {
                    Toast.makeText(getApplicationContext(), "Please choose your response audio!",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                // Activate PocketSphinx in the background
                Toast.makeText(getApplicationContext(), "TurnMeOn Activated!",
                        Toast.LENGTH_LONG).show();
                runRecognizerSetup(code, audioPath);

            }
        });

        // Listen from Deactivate button
        Button deactivate = findViewById(R.id.deactivateButton);
        deactivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deactivateApp();
            }
        });
    }

    //Deactivates recognizer and media player.
    private void deactivateApp() {
        if (recognizer != null) {
            recognizer.stop();
        }
        if (player != null) {
            if (player.isPlaying()) {
                player.stop();
            }
        }

        Toast.makeText(getApplicationContext(), "TurnMeOn Deactivated!",
                Toast.LENGTH_LONG).show();
    }

    private void runRecognizerSetup(String codeword, String audioPath) {

        this.KEYPHRASE = codeword;
        this.AUDIOFILE = audioPath;

        // Recognizer initialization is a time-consuming and it involves IO,
        // so we execute it in async task
        class MyTask extends AsyncTask<Void, Void, Exception> {

            private WeakReference<MainActivity> activityWeakReference;

            MyTask(MainActivity context) {
                activityWeakReference = new WeakReference<>(context);
            }

            @Override
            protected Exception doInBackground(Void... params) {
                try {
                    Assets assets = new Assets(MainActivity.this);
                    File assetDir = assets.syncAssets();
                    setupRecognizer(assetDir);
                } catch (IOException e) {
                    return e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Exception result) {
                MainActivity activity = activityWeakReference.get();
                if (activity == null || activity.isFinishing()) {
                    return;
                }

                if (result != null) {
                    System.out.println(result.getMessage());
                } else {
                    recognizer.startListening(KWS_SEARCH);
                }
            }
        }

        new MyTask(this).execute();

    }

    private void setupRecognizer(File assetsDir) throws IOException {
        float thres = (float)(1 * Math.pow(10, -18));

        recognizer = SpeechRecognizerSetup.defaultSetup()
                .setAcousticModel(new File(assetsDir, "en-us-ptm"))
                .setDictionary(new File(assetsDir, "cmudict-en-us.dict"))
                .setKeywordThreshold(thres)
                .getRecognizer();

        recognizer.addListener(this);

        // Create keyword-activation search.
        recognizer.addKeyphraseSearch(KWS_SEARCH, KEYPHRASE);
    }

    @Override
    public void onPartialResult(Hypothesis hypothesis) {
        if (hypothesis == null) {
            return;
        }

        String text = hypothesis.getHypstr();
        if (text.equals(KEYPHRASE)) {
            Toast.makeText(getApplicationContext(), "Partial - Voice recognition success!",
                    Toast.LENGTH_LONG).show();
            recognizer.stop();
        }

    }

    @Override
    public void onResult(Hypothesis hypothesis) {
        if (hypothesis == null) {
            return;
        }

        String text = hypothesis.getHypstr();
        if (text.equals(KEYPHRASE)) {
            Toast.makeText(getApplicationContext(), "onResult - recognition success!",
                    Toast.LENGTH_LONG).show();
            recognizer.stop();
            playAlarm();
            //recognizer.startListening(KWS_SEARCH);

            recognizer.startListening(KWS_SEARCH);
            //playAlarm();
        }
    }

    private void playAlarm() {

        switch (AUDIOFILE) {
        case AudioFiles.track1:
            player = MediaPlayer.create(this, R.raw.smrt_door_closing);
            break;
        case AudioFiles.track2:
            player = MediaPlayer.create(this, R.raw.gmod_version_1);
            break;
        case AudioFiles.track3:
            player = MediaPlayer.create(this, R.raw.rick_roll);
            break;
        case AudioFiles.track4:
            player = MediaPlayer.create(this, R.raw.my_name_jeff);
            break;
        case AudioFiles.track5:
            player = MediaPlayer.create(this, R.raw.you_on_kazoo);
            break;
        default:
            player = MediaPlayer.create(this, R.raw.smrt_door_closing);
            break;
        }

        AudioManager am = (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);
        am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        am.setStreamVolume(AudioManager.STREAM_MUSIC, am.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
        try {
            player.setVolume(100, 100);
            player.setLooping(true);
//            player.prepare();
            player.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEndOfSpeech() {
        if (!recognizer.getSearchName().equals(KWS_SEARCH)) {
            recognizer.stop();
            recognizer.startListening(KWS_SEARCH);
        }
    }

    @Override
    public void onBeginningOfSpeech() {
    }

    @Override
    public void onError(Exception error) {
        System.out.println(error.getMessage());
    }

    @Override
    public void onTimeout() {
        recognizer.stop();
        recognizer.startListening(KWS_SEARCH);
    }
}
