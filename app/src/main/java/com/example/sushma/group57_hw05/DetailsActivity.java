package com.example.sushma.group57_hw05;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/*
* Assignment: 5
* File name: DetailsActivity.java
* Team members: Vinayak Kolhapure and Sushma Reddy
* */

public class DetailsActivity extends AppCompatActivity {
    TextView tvLoc, tvFeels, tvClouds, tvPressure, tvDewpoints, tvHumidity, tvWinds, tvMax, tvMin, tvClimateType, tvTemp;
    ImageView imageV;
    FavModWeather favAdaptor;
    DetailsActivity con;
    String city, state, maxtemp, mintemp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        tvLoc = (TextView)findViewById(R.id.textViewDetailsLoc);
        tvFeels = (TextView)findViewById(R.id.textViewFeelsLike);
        tvClouds = (TextView)findViewById(R.id.textViewClouds);
        tvPressure = (TextView)findViewById(R.id.textViewPressure);
        tvDewpoints = (TextView)findViewById(R.id.textViewDewpoint);
        tvHumidity = (TextView)findViewById(R.id.textViewHumidity);
        tvWinds = (TextView)findViewById(R.id.textViewWinds);
        tvMax = (TextView)findViewById(R.id.textViewMaxTemp);
        tvMin = (TextView)findViewById(R.id.textViewMinTemp);
        tvClimateType =(TextView)findViewById(R.id.textViewClimateType);
        tvTemp = (TextView)findViewById(R.id.textViewtemp);
        imageV = (ImageView)findViewById(R.id.imageViewDetails);

        if(getIntent().getExtras()!=null) {
            Intent intent = getIntent();
            String clouds = intent.getStringExtra("clouds");
            String dewpoint = intent.getStringExtra("dewpoint");
            String feelslike = intent.getStringExtra("feelslike");
            String humidity = intent.getStringExtra("humidity");
            String image = intent.getStringExtra("image");
            String climatetype = intent.getStringExtra("climatetype");
            String windspeed = intent.getStringExtra("windspeed");
            String winddir = intent.getStringExtra("winddir");
            String pressure = intent.getStringExtra("pressure");
            maxtemp = intent.getStringExtra("maxtemp");
            mintemp = intent.getStringExtra("mintemp");
            String temp = intent.getStringExtra("temp");
            city = intent.getStringExtra("city");
            String time = intent.getStringExtra("time");
            String finalCity = city.substring(0,1).toUpperCase()+city.substring(1).
                    toLowerCase();
            state = intent.getStringExtra("state");
            String location = finalCity + ", " + state.toUpperCase() + " ("+ time + ")";
            tvLoc.setText(location);
            tvFeels.setText(feelslike+" Fahrenheit");
            tvPressure.setText(pressure+" hPa");
            tvDewpoints.setText(dewpoint+" Fahrenheit");
            tvHumidity.setText(humidity+"%");
            tvWinds.setText(windspeed+" mph, "+winddir);
            tvMax.setText(maxtemp+" Fahrenheit");
            tvMin.setText(mintemp+" Fahrenheit");
            String climate = "<font color='#C8C8C8'>"+climatetype+"</font>";
            tvClimateType.setText(Html.fromHtml(climate));
            tvTemp.setText(temp+(char) 0x00B0+"F");
            tvClouds.setText(clouds);
            Picasso.with(this).load(image).into(imageV);
        }
    }

    /*public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_file,menu);
        return true;
    }*/

    /*public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action:
                Weather weather = CityWeather.weatherList.get(0);
                weather.setState(state);
                weather.setCity(city);
                weather.setMinimumTemp(mintemp);
                weather.setMaximumTemp(maxtemp);
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                weather.setDate(dateFormat.format(calendar.getTime()));
                CityWeather.favObjs.add(weather);
                Toast.makeText(getApplicationContext(),"Fav added",Toast.LENGTH_SHORT).show();
                SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPrefs.edit();
                String json = gson.toJson(CityWeather.favObjs);
                editor.putString("TAG", json);
                editor.commit();
                favAdaptor = new FavModWeather(this,R.layout.favorite_list, CityWeather.favObjs);
                favAdaptor.setNotifyOnChange(true);
                MainActivity.favList.setAdapter(favAdaptor);
        }
        return super.onOptionsItemSelected(item);
    }*/
}
