package com.example.tmhuskies.turnmeon;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ResponseAdapter extends ArrayAdapter<ResponseOption> {

    public ResponseAdapter(ArrayList<ResponseOption> responseOptions, Context context) {
        super(context, 0, responseOptions);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ResponseOption responseOption = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.reponse_option, parent, false);
        }

        TextView title = convertView.findViewById(R.id.responseText);
        title.setText(responseOption.getTitle());
        return convertView;
    }
/*
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_response, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ResponseOption responseOption = responseOptions.get(position);
        holder.titleView.setText(responseOption.getTitle());
    }

    @Override
    public int getItemCount() {
        return responseOptions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //View mView;
        public TextView titleView;

        public ViewHolder(View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.responseText);
            //mView = itemView;
        }
        /*public void setTitle(String title){
            TextView titleView = mView.findViewById(R.id.responseText);
            titleView.setText(title);
        }
    }*/
}
