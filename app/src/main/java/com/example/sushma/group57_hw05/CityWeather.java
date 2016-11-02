package com.example.sushma.group57_hw05;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/*
* Assignment: 5
* File name: CityWeather.java
* Team members: Vinayak Kolhapure and Sushma Reddy
* */

public class CityWeather extends AppCompatActivity implements GetAsyncTemp.Temp{
    public static ProgressDialog pd;
    public static ArrayList<Weather> weatherList;
    public static List<Weather> favObjs;
    ModifiedAdapter adapter;
    FavModWeather favAdaptor;
    ListView lv;
    TextView tvLoc;
    public static String intentCityNam, intentStateNam, min, max, city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather);
        pd = new ProgressDialog(this);
        lv = (ListView) findViewById(R.id.listView);

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gsonForMain = new Gson();
        String jsonForMain = sharedPrefs.getString("TAG", null);
        Type type = new TypeToken<ArrayList<Weather>>(){}.getType();
        favObjs = gsonForMain.fromJson(jsonForMain, type);

        if(favObjs == null)
            favObjs = Collections.synchronizedList(new ArrayList<Weather>());
        tvLoc = (TextView) findViewById(R.id.textViewLocation);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                      @Override
                                      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                          Weather weather = (Weather) lv.getItemAtPosition(position);
                                          Intent intent = new Intent(getBaseContext(), DetailsActivity.class);
                                          intent.putExtra("clouds", weather.getClouds());
                                          intent.putExtra("dewpoint", weather.getDewpoint());
                                          intent.putExtra("feelslike",weather.getFeelsLike());
                                          intent.putExtra("humidity",weather.getHumidity());
                                          intent.putExtra("image",weather.getIconUrl());
                                          intent.putExtra("climatetype", weather.getClimateType());
                                          intent.putExtra("windspeed", weather.getWindSpeed());
                                          intent.putExtra("winddir", weather.getWindDirection());
                                          intent.putExtra("pressure",weather.getPressure());
                                          intent.putExtra("maxtemp",max);
                                          intent.putExtra("mintemp",min);
                                          intent.putExtra("temp",weather.getTemperature());
                                          intent.putExtra("time",weather.getTime());
                                          intent.putExtra("city",city);
                                          intent.putExtra("state",intentStateNam);
                                          startActivity(intent);
                                      }
                                  }

        );
        if(getIntent().getExtras()!=null){
            Intent intent = getIntent();
            intentCityNam = intent.getStringExtra("city");
            intentStateNam = intent.getStringExtra("state");
            city = intentCityNam.replace("_"," ");
            tvLoc.setText(" "+city+", "+intentStateNam);
            new GetAsyncTemp(this,this).execute("http://api.wunderground.com/api/2b2159a997ac4ffa/hourly/q/"+intentStateNam+"/"+intentCityNam+".json");
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_file,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action:
                Weather weather = weatherList.get(0);
                weather.setState(intentStateNam);
                weather.setCity(intentCityNam);
                weather.setMinimumTemp(min);
                weather.setMaximumTemp(max);
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                weather.setDate(dateFormat.format(calendar.getTime()));
                if(favObjs.size()==0)
                    favObjs.add(weather);
                int newFav = 0;
                List<Weather> tempFavs = new ArrayList<Weather>();
                for(Weather fav : favObjs) {
                    if(fav.getCity().equalsIgnoreCase(weather.getCity())) {
                        fav.setTemperature(weather.getTemperature());
                        fav.setDate(weather.getDate());
                        newFav +=1;
                    }
                }
                if(newFav == 0)
                    tempFavs.add(weather);
                newFav = 0;

                for (Weather tempW : tempFavs) {
                    favObjs.add(tempW);
                }

                Toast.makeText(getApplicationContext(),"Added to Favorites",Toast.LENGTH_SHORT).show();
                MainActivity.tv.setText("Favorites");
                SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPrefs.edit();
                String json = gson.toJson(favObjs);
                editor.putString("TAG", json);
                editor.commit();

                Gson gsonForMain = new Gson();
                String jsonForMain = sharedPrefs.getString("TAG", null);
                Type type = new TypeToken<ArrayList<Weather>>(){}.getType();
                MainActivity.weatherObjs = gsonForMain.fromJson(jsonForMain, type);

                //WeatherUtil.toJSON(sharedPrefs,favObjs);
                favAdaptor = new FavModWeather(this,R.layout.favorite_list, favObjs);
                favAdaptor.setNotifyOnChange(true);
                MainActivity.favList.setAdapter(favAdaptor);
        }
        return super.onOptionsItemSelected(item);
    }

    public void setupData(ArrayList<Weather> weatherList) {
        adapter = new ModifiedAdapter(this,R.layout.linear_test, weatherList);
        adapter.setNotifyOnChange(true);
        lv.setAdapter(adapter);
    }

    @Override
    public void getTemp(ArrayList<Weather> NList,int min,int max) {
        Log.d("After", "Inside Top Stories size is " + NList.size());
        this.min = min+"";
        this.max = max+"";
        weatherList=NList;
        Log.d("myList", weatherList.toString());
        setupData(weatherList);
    }
}