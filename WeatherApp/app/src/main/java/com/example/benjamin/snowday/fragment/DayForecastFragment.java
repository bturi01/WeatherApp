package com.example.benjamin.snowday.fragment;

/**
 * Created by BU on 4/21/15.
 */

import com.example.benjamin.snowday.Output;
import com.example.benjamin.snowday.R;
import com.example.benjamin.snowday.WeatherHttpClient;

import com.example.benjamin.snowday.adapter.DailyForecastPageAdapter;
import com.example.benjamin.snowday.model.DayForecast;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class DayForecastFragment extends Fragment{

        private DayForecast dayForecast;
        private ImageView iconWeather;

        public DayForecastFragment() {}

        public void setForecast(DayForecast dayForecast) {

            this.dayForecast = dayForecast;

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.dayforecast_fragment, container, false);

            TextView tempView = (TextView) v.findViewById(R.id.tempForecast);
            TextView descView = (TextView) v.findViewById(R.id.skydescForecast);
            tempView.setText( "High: "+(int) (dayForecast.forecastTemp.max - 275.15) + "\nLow: " + (int) (dayForecast.forecastTemp.min - 275.15) );
            descView.setText(dayForecast.weather.currentCondition.getDescr());
            iconWeather = (ImageView) v.findViewById(R.id.forCondIcon);
            // Now we retrieve the weather icon
            JSONIconWeatherTask task = new JSONIconWeatherTask();
            task.execute(new String[]{dayForecast.weather.currentCondition.getIcon()});

            return v;
        }



        private class JSONIconWeatherTask extends AsyncTask<String, Void, byte[]> {

            @Override
            protected byte[] doInBackground(String... params) {

                byte[] data = null;

                try {

                    // Let's retrieve the icon
                   // ((new Output())).getImage(weather.currentCondition.getIcon());
                   // data = ( (new Output()).getImage(params[0]));

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return data;
            }




            @Override
            protected void onPostExecute(byte[] data) {
                super.onPostExecute(data);

                if (data != null) {
                    Bitmap img = BitmapFactory.decodeByteArray(data, 0, data.length);
                    iconWeather.setImageBitmap(img);
                }
            }



        }
    }


