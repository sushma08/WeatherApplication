package com.example.sushma.group57_hw05;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

/*
* Assignment: 5
* File name: FavModWeather.java
* Team members: Vinayak Kolhapure and Sushma Reddy
* */

public class FavModWeather extends ArrayAdapter<Weather> {
    List<Weather> mData;
    Context mContext;
    int mResource;
    public FavModWeather(Context context, int resource, List<Weather> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mData = objects;
        this.mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource,parent,false);
        }

        Weather weather = mData.get(position);
        TextView timTextView = (TextView) convertView.findViewById(R.id.textViewCityState);
        String city = weather.getCity().replace("_"," ");
        String state = weather.getState().toUpperCase();
        timTextView.setText(city.substring(0,1).toUpperCase()+city.substring(1).
                toLowerCase()+", "+state);
        TextView tempTextView = (TextView) convertView.findViewById(R.id.textViewTempDate);
        String date = "<br />Updated On: "+weather.getDate();
        tempTextView.setText(Html.fromHtml(weather.getTemperature()+(char) 0x00B0+"F"+date));
        convertView.setBackgroundColor(Color.parseColor("#BCD2EE"));
        return convertView;
    }
}


