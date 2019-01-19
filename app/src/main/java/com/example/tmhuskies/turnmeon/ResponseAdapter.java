package com.example.tmhuskies.turnmeon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ResponseAdapter extends ArrayAdapter<ResponseOption> {

    public ResponseAdapter(ArrayList<ResponseOption> responseOptions, Context context) {
        super(context, 0, responseOptions);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ResponseOption responseOption = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.response_option, parent, false);
        }

        TextView title = convertView.findViewById(R.id.responseText);
        title.setText(responseOption.getTitle());

        ImageButton playBtn = (ImageButton) convertView.findViewById(R.id.imageButton);
        playBtn.setTag(position);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (Integer) view.getTag();
                ResponseOption ro = getItem(position);
                ro.getAudio().start();
            }
        });

        TextView textOption = (TextView) convertView.findViewById(R.id.responseText);
        textOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return convertView;
    }


}
