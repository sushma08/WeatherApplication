package com.example.sushma.group57_hw05;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/*
* Assignment: 5
* File name: Weather.java
* Team members: Vinayak Kolhapure and Sushma Reddy
* */

public class Weather implements Serializable{

    private static Weather userInstance = null;
    String time, temperature, dewpoint, clouds, iconUrl, windSpeed, windDirection, climateType,
            humidity, feelsLike, maximumTemp, minimumTemp, pressure, city, state, date;

    public Weather(){}

    public String getCity() {
        return city;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getDewpoint() {
        return dewpoint;
    }

    public void setDewpoint(String dewpoint) {
        this.dewpoint = dewpoint;
    }

    public String getClouds() {
        return clouds;
    }

    public void setClouds(String clouds) {
        this.clouds = clouds;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getClimateType() {
        return climateType;
    }

    public void setClimateType(String climateType) {
        this.climateType = climateType;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(String feelsLike) {
        this.feelsLike = feelsLike;
    }

    public String getMaximumTemp() {
        return maximumTemp;
    }

    public void setMaximumTemp(String maximumTemp) {
        this.maximumTemp = maximumTemp;
    }

    public String getMinimumTemp() {
        return minimumTemp;
    }

    public void setMinimumTemp(String minimumTemp) {
        this.minimumTemp = minimumTemp;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }


    @Override
    public String toString() {
        return "Weather{" +
                "time='" + time + '\'' +
                ", temperature='" + temperature + '\'' +
                ", dewpoint='" + dewpoint + '\'' +
                ", clouds='" + clouds + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", windSpeed='" + windSpeed + '\'' +
                ", windDirection='" + windDirection + '\'' +
                ", climateType='" + climateType + '\'' +
                ", humidity='" + humidity + '\'' +
                ", feelsLike='" + feelsLike + '\'' +
                ", maximumTemp='" + maximumTemp + '\'' +
                ", minimumTemp='" + minimumTemp + '\'' +
                ", pressure='" + pressure + '\'' +
                '}';
    }

    public static Weather createWeather(JSONObject weatherJSON) throws JSONException {

        Weather weather =new Weather();
        JSONObject temp = weatherJSON.getJSONObject("temp");
        weather.setTemperature(temp.getString("english"));
        JSONObject dewpoint = weatherJSON.getJSONObject("dewpoint");
        weather.setDewpoint(dewpoint.getString("english"));
        JSONObject time = weatherJSON.getJSONObject("FCTTIME");
        String timeinAmPm = time.getString("hour");
        int timeAmPm = Integer.parseInt(timeinAmPm);
        if(timeAmPm>12){
            timeAmPm = timeAmPm - 12;
            timeinAmPm = timeAmPm+":00 pm";
        }else if(timeAmPm==0){
            timeinAmPm = "12:00 am";
        }else {
            timeinAmPm = timeAmPm+":00 am";
        }
        weather.setTime(timeinAmPm);
        weather.setClimateType(weatherJSON.getString("wx"));
        weather.setClouds(weatherJSON.getString("condition"));
        JSONObject feelslike = weatherJSON.getJSONObject("feelslike");
        weather.setFeelsLike(feelslike.getString("english"));
        weather.setHumidity(weatherJSON.getString("humidity"));
        weather.setIconUrl(weatherJSON.getString("icon_url"));
        JSONObject mslp = weatherJSON.getJSONObject("mslp");
        weather.setPressure(mslp.getString("metric"));
        JSONObject wdir = weatherJSON.getJSONObject("wdir");
        weather.setWindDirection(wdir.getString("degrees")+(char) 0x00B0+wdir.getString("dir"));
        JSONObject wspeed = weatherJSON.getJSONObject("wspd");
        weather.setWindSpeed(wspeed.getString("english"));

        return weather;
    }

}
