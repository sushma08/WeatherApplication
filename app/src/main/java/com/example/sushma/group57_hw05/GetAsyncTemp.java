package com.example.sushma.group57_hw05;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/*
* Assignment: 5
* File name: GetAsyncTemp.java
* Team members: Vinayak Kolhapure and Sushma Reddy
* */

public class GetAsyncTemp extends AsyncTask<String,Void,ArrayList<Weather>> {
    Temp activity;
    Context mContext;
    GetAsyncTemp(Temp activity, Context context){
        this.activity = activity;
        mContext = context;
    }
    @Override
    protected ArrayList<Weather> doInBackground(String... params) {
        publishProgress();
        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statusCode = con.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = null;
                StringBuilder sb = new StringBuilder();
                String line = null;
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                line = reader.readLine();
                while (line != null) {
                    sb.append(line);
                    try {
                        line = reader.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return WeatherUtil.parseweather(sb.toString());
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        CityWeather.pd.setMessage("Loading Hourly Data...");
        CityWeather.pd.show();
    }

    @Override
    protected void onPostExecute(ArrayList<Weather> weathers) {
        super.onPostExecute(weathers);
        if(weathers==null){
            Toast.makeText(mContext,"Sorry city and state you entered doesn't exist!",Toast.LENGTH_LONG).show();
        }else{
            ArrayList<Integer> maxTemp = new ArrayList<Integer>();
            for(Weather weather:weathers){
                maxTemp.add(Integer.parseInt(weather.getTemperature()));
            }
            int max = Collections.max(maxTemp);
            int min = Collections.min(maxTemp);
            activity.getTemp(weathers,max,min);
        }
        CityWeather.pd.dismiss();
    }

    static public interface Temp
    {
        public void getTemp(ArrayList<Weather> NList, int min, int max);
    }
}
