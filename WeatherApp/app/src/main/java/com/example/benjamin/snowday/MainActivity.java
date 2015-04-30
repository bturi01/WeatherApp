package com.example.benjamin.snowday;

import org.json.JSONException;

import com.example.benjamin.snowday.model.Weather;
import com.example.benjamin.snowday.adapter.DailyForecastPageAdapter;
//import com.example.benjamin.snowday.model.Location;
import com.example.benjamin.snowday.model.Weather;
import com.example.benjamin.snowday.model.WeatherForecast;

import android.content.Intent;
import android.app.ActionBar;
import android.widget.*;
import android.util.Log;
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
import android.location.Address;
import android.location.LocationManager;

import java.util.List;
import java.util.Locale;

public class MainActivity extends FragmentActivity {
    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";


    protected TextView cityText;
    protected TextView condDescr;
    protected TextView temp;
    //private TextView press;
    //private TextView windSpeed;
    //private TextView windDeg;
    protected TextView unitTemp;

    //private TextView hum;
    protected ImageView imgView;
    protected Button searchButton;
    protected EditText locationString;

    private static String forecastDaysNum = "7";
    protected ViewPager pager;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar ab = getActionBar();
        ab.setHomeButtonEnabled(false);
        ab.setDisplayHomeAsUpEnabled(false);

        searchButton = (Button) findViewById(R.id.go_button);
        locationString = (EditText) findViewById(R.id.location_entry);
        /*searchButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        String city = locationString.getText().toString();


                        setContentView(R.layout.output);
                        ActionBar ab = getActionBar();
                        ab.setDisplayHomeAsUpEnabled(true);
                        cityText = (TextView) findViewById(R.id.cityText);
                        condDescr = (TextView) findViewById(R.id.skydesc);
                        temp = (TextView) findViewById(R.id.temp);

                        //hum = (TextView) findViewById(R.id.hum);
                        //press = (TextView) findViewById(R.id.press);
                        //windSpeed = (TextView) findViewById(R.id.windSpeed);
                        //windDeg = (TextView) findViewById(R.id.windDeg);
                        imgView = (ImageView) findViewById(R.id.condIcon);

                        JSONWeatherTask task = new JSONWeatherTask();
                        task.execute(new String[]{city});

                    }
                });*/

        /*get_current_location = (Button) findViewById(R.id.get_current_location);
        // getLocationButton is the name of your button.  Not the best name, I know.
        get_current_location.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // instantiate the location manager, note you will need to request permissions in your manifest
                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                // get the last know location from your location manager.
                Location location= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                // now get the lat/lon from the location and do something with it.
                JSONWeatherTask task = new JSONWeatherTask();
                Geocoder gcd = new Geocoder(context, Locale.getDefault());
                List<Address> addresses = gcd.getFromLocation(lat, lng, 1);
                addresses.get(0).getLocality());
                task.execute(location.getLatitude(), location.getLongitude());
            }
        });*/

        /*setContentView(R.layout.output);

        cityText = (TextView) findViewById(R.id.cityText);
        condDescr = (TextView) findViewById(R.id.condDesc);
        temp = (TextView) findViewById(R.id.temp);
        hum = (TextView) findViewById(R.id.hum);
        press = (TextView) findViewById(R.id.press);
        windSpeed = (TextView) findViewById(R.id.windSpeed);
        windDeg = (TextView) findViewById(R.id.windDeg);
        imgView = (ImageView) findViewById(R.id.imgView);

        //JSONWeatherTask task = new JSONWeatherTask();
        //task.execute(new String[]{city});*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public void locationSearch(View view){
        locationManager = (LocationManager)this.getSystemService(LOCATION_SERVICE);
        Location location;
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double old_latitude;
        double old_longitude;
        //if (location!= null) {
            old_latitude = location.getLatitude();
            old_longitude = location.getLongitude();
        //}
        Intent intent = new Intent(this, Output.class);

        Geocoder gcd = new Geocoder(getApplicationContext(),Locale.getDefault());
        //double lat = location.getLatitude();
        //double lng = location.getLatitude();
        List<Address> addresses;
        try {
            addresses = gcd.getFromLocation(old_latitude,old_longitude, 1);
            String city = addresses.get(0).getLocality();
            intent.putExtra(EXTRA_MESSAGE, city);
            startActivity(intent);
        }
        catch(java.io.IOException e){
            System.out.println(e);
        }


    }
    public void search(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, Output.class);
        locationString = (EditText) findViewById(R.id.location_entry);
        String message = locationString.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    protected class JSONWeatherTask extends AsyncTask<String, Void, Weather> {

        @Override
        protected Weather doInBackground(String... params) {
            //Weather weather = new Weather();
            String data = ((new WeatherHttpClient()).getWeatherData(params[0]));

            //try {

            Weather weather = new Weather();
            try {
                weather = JSONWeatherParser.getWeatherData(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            // Let's retrieve the icon
            //weather.iconData = ((new Output())).getImage(weather.currentCondition.getIcon());

            //} catch (JSONException e) {
            //e.printStackTrace();
            //}
            return weather;

        }


        @Override
        protected void onPostExecute(Weather weather) {
            // Let's retrieve the icon
            //weather.iconData = ((new Output())).getImage(weather.currentCondition.getIcon());
            super.onPostExecute(weather);

            if (weather.iconData != null && weather.iconData.length > 0) {
                Bitmap img = BitmapFactory.decodeByteArray(weather.iconData, 0, weather.iconData.length);
                imgView.setImageBitmap(img);
            }

            cityText.setText(weather.location.getCity() + "," + weather.location.getCountry());
            condDescr.setText(weather.currentCondition.getCondition());
            temp.setText("" + Math.round((weather.temperature.getTemp() - 273.15)) + "°C");
            //hum.setText("" + weather.currentCondition.getHumidity() + "%");
            //press.setText("" + weather.currentCondition.getPressure() + " hPa");
            //windSpeed.setText("" + weather.wind.getSpeed() + " mps");
            //windDeg.setText("" + weather.wind.getDeg() + "°");

        }


    }

    protected class JSONForecastWeatherTask extends AsyncTask<String, Void, WeatherForecast> {

        @Override
        protected WeatherForecast doInBackground(String... params) {

            String data = ((new WeatherHttpClient()).getForecastWeatherData(params[0], params[1], params[2]));
            WeatherForecast forecast = new WeatherForecast();
            try {
                forecast = JSONWeatherParser.getForecastWeather(data);
                System.out.println("Weather [" + forecast + "]");
                // Let's retrieve the icon
                //weather.iconData = ( (new WeatherHttpClient()).getImage(weather.currentCondition.getIcon()));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return forecast;

        }


        @Override
        protected void onPostExecute(WeatherForecast forecastWeather) {
            // Let's retrieve the icon
            //forecast.iconData = ((new Output())).getImage(weather.currentCondition.getIcon());
            super.onPostExecute(forecastWeather);

            DailyForecastPageAdapter adapter = new DailyForecastPageAdapter(Integer.parseInt(forecastDaysNum), getSupportFragmentManager(), forecastWeather);

            pager.setAdapter(adapter);
        }
    }
}