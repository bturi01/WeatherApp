package com.example.benjamin.snowday;

import org.json.JSONException;

import com.example.benjamin.snowday.model.Weather;
import com.example.benjamin.snowday.adapter.DailyForecastPageAdapter;
//import com.example.benjamin.snowday.model.Location;
import com.example.benjamin.snowday.model.Weather;
import com.example.benjamin.snowday.model.WeatherForecast;

import android.content.Intent;
import android.app.ActionBar;
import android.util.Log;
import android.widget.*;
import android.view.*;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import android.location.Location;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.content.Context;
import android.location.Criteria;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Locale;
/**
 * Created by Chase on 4/23/15.
 */
public class Output extends MainActivity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.output);

        Intent intent = getIntent();

        pager = (ViewPager) findViewById(R.id.pager);
    String city = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
    ActionBar ab = getActionBar();
    ab.setDisplayHomeAsUpEnabled(true);
        JSONWeatherTask task = new JSONWeatherTask();
        task.execute(new String[]{city});//from city
        JSONForecastWeatherTask task1 = new JSONForecastWeatherTask();
        task1.execute(new String[]{city, "en", "7"});
    cityText = (TextView) findViewById(R.id.cityText);
    condDescr = (TextView) findViewById(R.id.skydesc);
    temp = (TextView) findViewById(R.id.temp);
        imgView = (ImageView) findViewById(R.id.condIcon);



    }

}


