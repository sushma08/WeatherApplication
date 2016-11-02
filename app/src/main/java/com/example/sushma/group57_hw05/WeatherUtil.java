package com.example.sushma.group57_hw05;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/*
* Assignment: 5
* File name: WeatherUtil.java
* Team members: Vinayak Kolhapure and Sushma Reddy
* */

public class WeatherUtil {
        public static ArrayList<Weather> parseweather(String result) throws JSONException {
            ArrayList<Weather> weatherList = new ArrayList<Weather>();
            JSONObject root = new JSONObject(result);
            JSONArray weatherJSONArray = root.getJSONArray("hourly_forecast");
            for (int i = 0; i < weatherJSONArray.length(); i++) {
                JSONObject weatherJSON = weatherJSONArray.getJSONObject(i);
                Weather weather = Weather.createWeather(weatherJSON);
                weatherList.add(weather);
            }
            Log.d("Size of list is "," "+weatherList.size());
            return weatherList;
    }

    public static void fromJSON(SharedPreferences prefs) {
        ArrayList<Weather> weathersLocal = new ArrayList<Weather>();
        Gson gson = new Gson();
        String json = prefs.getString("TAG", null);
        Type type = new TypeToken<ArrayList<Weather>>(){}.getType();
        weathersLocal = gson.fromJson(json, type);
        MainActivity.weatherObjs = weathersLocal;
    }

    public static void toJSON(SharedPreferences prefs, List<Weather> favorites) {
        Gson gson = new Gson();
        SharedPreferences.Editor editor = prefs.edit();
        String json = gson.toJson(favorites);
        editor.putString("TAG", json);
        editor.commit();
    }
}
