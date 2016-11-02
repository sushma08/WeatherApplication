package com.example.sushma.group57_hw05;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

/*
* Assignment: 5
* File name: MainActivity.java
* Team members: Vinayak Kolhapure and Sushma Reddy
* */

public class MainActivity extends AppCompatActivity {
    String cityNAme, stateName;
    public static ListView favList;
    public static TextView tv;
    FavModWeather favAdaptor;
    public static ArrayList<Weather> weatherObjs;
    SharedPreferences sharedPrefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.textView);
        favList = (ListView) findViewById(R.id.listViewFavorites);
        if(weatherObjs==null)
            weatherObjs = new ArrayList<>();

        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        Gson gson = new Gson();
        String json = sharedPrefs.getString("TAG", null);
        Type type = new TypeToken<ArrayList<Weather>>(){}.getType();
        weatherObjs = gson.fromJson(json, type);
        //WeatherUtil.fromJSON(sharedPrefs);

        if(weatherObjs !=null) {

            if (weatherObjs.size() == 0) {
                tv.setText("There is no city in your Favorites");
            } else {
                tv.setText("Favorites");
                favAdaptor = new FavModWeather(this, R.layout.favorite_list, weatherObjs);
                favAdaptor.setNotifyOnChange(true);
                MainActivity.favList.setAdapter(favAdaptor);
            }
        }

        favList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(weatherObjs.size()!=0) {
                    weatherObjs.remove(position);
                    Log.d("demo","position " + position);
                    tv.setText("Favorites");

                    //update json
//                    SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                    String json = sharedPrefs.getString("TAG", null);
                    try {
                        JSONArray jsonArray = new JSONArray(json);
                        JSONArray modifiedArray = new JSONArray();
                        if (jsonArray != null) {
                            for (int i=0;i<jsonArray.length();i++)
                            {
                                if (i != position)
                                {
                                    modifiedArray.put(jsonArray.get(i));
                                }
                            }
                        }
                        SharedPreferences.Editor editor = sharedPrefs.edit();
                        String jsonNew = modifiedArray.toString();
                        editor.putString("TAG", jsonNew);
                        editor.commit();
                        Toast.makeText(getApplicationContext(),"Favorite Deleted",Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        Log.e("error",e.toString());
                    }
                    favAdaptor = new FavModWeather(MainActivity.this,R.layout.favorite_list, weatherObjs);
                    favAdaptor.setNotifyOnChange(true);
                    MainActivity.favList.setAdapter(favAdaptor);
                }else{
                    tv.setText("There is no city in your Favorites");
                }
                return false;
            }
        });

        favList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Weather weather = (Weather) favList.getItemAtPosition(position);
                Intent intent = new Intent(getBaseContext(), DetailsActivity.class);
                intent.putExtra("clouds", weather.getClouds());
                intent.putExtra("dewpoint", weather.getDewpoint());
                intent.putExtra("feelslike", weather.getFeelsLike());
                intent.putExtra("humidity", weather.getHumidity());
                intent.putExtra("image", weather.getIconUrl());
                intent.putExtra("climatetype", weather.getClimateType());
                intent.putExtra("windspeed", weather.getWindSpeed());
                intent.putExtra("winddir", weather.getWindDirection());
                intent.putExtra("pressure", weather.getPressure());
                intent.putExtra("maxtemp", weather.getMaximumTemp());
                intent.putExtra("mintemp", weather.getMinimumTemp());
                intent.putExtra("temp", weather.getTemperature());
                intent.putExtra("time", weather.getTime());
                intent.putExtra("state", weather.getState());
                intent.putExtra("city", weather.getCity().replace("_"," "));
                startActivity(intent);
            }
        });
    }

    public void showCityWeather(View view) {
        cityNAme = ((EditText) findViewById(R.id.editTextCity)).getText().toString();
        stateName = ((EditText) findViewById(R.id.editTextState)).getText().toString();
        if (cityNAme.equals("") || stateName.equals("")) {
            Toast.makeText(getApplicationContext(), "Please enter both city and state fields...", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(this, CityWeather.class);
            cityNAme = cityNAme.replace(' ', '_');
            intent.putExtra("city", cityNAme);
            intent.putExtra("state", stateName);
            startActivity(intent);
        }
    }
}