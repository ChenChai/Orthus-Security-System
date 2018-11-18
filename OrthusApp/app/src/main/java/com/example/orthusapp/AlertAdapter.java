package com.example.orthusapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class AlertAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<String> sensorList;
    private ArrayList<String> timestampList;
    private ArrayList<String> keyList;

    public AlertAdapter(Context context, ArrayList<String> sensorList, ArrayList<String> timestampList, ArrayList<String> keyList){
        this.sensorList = sensorList;
        this.timestampList = timestampList;
        this.keyList = keyList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return keyList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    public void add(String sensorId, String timeStamp){
        sensorList.add(0, sensorId);
        timestampList.add(0, timeStamp);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = inflater.inflate(R.layout.list_alerts, null);
        TextView sensorIdTextView = (TextView) v.findViewById(R.id.sensorIdTextView);
        TextView alertTimeTextView = (TextView) v.findViewById(R.id.alertTimeTextView);

        sensorIdTextView.setText(sensorList.get(i));
        alertTimeTextView.setText(timestampList.get(i));

        return v;
    }
}
