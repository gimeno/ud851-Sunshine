package com.example.android.sunshine.sync;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;

import com.example.android.sunshine.data.WeatherContract;
import com.example.android.sunshine.utilities.NetworkUtils;
import com.example.android.sunshine.utilities.OpenWeatherJsonUtils;

import java.net.URL;

//  COMPLETED (1) Create a class called SunshineSyncTask
class SunshineSyncTask {

    //  COMPLETED (2) Within SunshineSyncTask, create a synchronized public static void method called syncWeather
    public static void syncWeather(Context context) {


        try {

            // COMPLETED (3) Within syncWeather, fetch new weather data
            URL weatherRequestUrl = NetworkUtils.getUrl(context);
            String jsonWeatherResponse = NetworkUtils
                    .getResponseFromHttpUrl(weatherRequestUrl);
            ContentValues[] simpleJsonWeatherData =
                    OpenWeatherJsonUtils.getWeatherContentValuesFromJson(context, jsonWeatherResponse);

            // COMPLETED (4) If we have valid results, delete the old data and insert the new
            if (simpleJsonWeatherData != null && simpleJsonWeatherData.length > 0) {
                ContentResolver contentResolver = context.getContentResolver();
                contentResolver.delete(WeatherContract.WeatherEntry.CONTENT_URI,
                        null,
                        null);
                contentResolver.bulkInsert(WeatherContract.WeatherEntry.CONTENT_URI,
                        simpleJsonWeatherData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}