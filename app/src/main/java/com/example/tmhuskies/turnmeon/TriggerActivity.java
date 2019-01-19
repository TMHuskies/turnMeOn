package com.example.tmhuskies.turnmeon;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

public class TriggerActivity extends AppCompatActivity {

    private ImageButton confirmButton;
    private EditText triggerText;
    private SharedPreferences savedCodeWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trigger);

        // Set up variables
        confirmButton = findViewById(R.id.confirmTrigger);
        triggerText = findViewById(R.id.enterTriggerText);
        savedCodeWord = getSharedPreferences("userPref", MODE_PRIVATE);

        confirmButton.setOnClickListener(confirmButtonListener);
    }

    // Grab the string and save onto SharedPreferences
    private void saveCodeWord(String code) {
        SharedPreferences.Editor preferencesEditor = savedCodeWord.edit();
        preferencesEditor.putString("codeWord", code);
        preferencesEditor.commit();
    }

    // Listen on confirm button to save the codeword onto SharedPreferences.
    public View.OnClickListener confirmButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!triggerText.getText().toString().isEmpty()) {
                saveCodeWord(triggerText.getText().toString());

                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(triggerText.getWindowToken(),0);
            }
        }
    };
}
