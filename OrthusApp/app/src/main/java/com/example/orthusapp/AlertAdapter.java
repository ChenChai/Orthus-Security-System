package com.example.orthusapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class AlertAdapter extends BaseAdapter {

    LayoutInflater inflater;
    ArrayList<String> sensorList;
    ArrayList<String> timestampList;
    ArrayList<String> keyList;

    public AlertAdapter(Context context, ArrayList<String> sensorList, ArrayList<String> timestampList, ArrayList<String> keyList){


    }

    @Override
    public int getCount() {
        return keyList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
