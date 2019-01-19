package com.example.tmhuskies.turnmeon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class ResponseActivity extends AppCompatActivity {

    private RecyclerView mUserResponseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);

        mUserResponseList = (RecyclerView)findViewById(R.id.response_list);
        mUserResponseList.setHasFixedSize(true);
        mUserResponseList.setLayoutManager(new LinearLayoutManager(this));
    }
}
