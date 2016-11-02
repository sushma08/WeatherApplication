package com.example.sushma.group57_hw05;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/*
* Assignment: 5
* File name: ModifiedAdapter.java
* Team members: Vinayak Kolhapure and Sushma Reddy
* */

public class ModifiedAdapter extends ArrayAdapter<Weather>{
    List<Weather> mData;
    Context mContext;
    int mResource;
    public ModifiedAdapter(Context context, int resource, List<Weather> objects) {
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
        ImageView view = (ImageView) convertView.findViewById(R.id.imageView);

        String url =weather.getIconUrl();
        Picasso.with(getContext()).load(url).into(view);

        TextView timTextView = (TextView) convertView.findViewById(R.id.textViewTime);
        String climate = "<font color='#C8C8C8'><br />"+weather.getClimateType()+"</font>";
        timTextView.setText(Html.fromHtml(weather.getTime()+climate));
        TextView tempTextView = (TextView) convertView.findViewById(R.id.textViewTemp);
        tempTextView.setText(weather.getTemperature()+(char) 0x00B0+"F");
        return convertView;
    }
}
