package com.example.agrigenius.weather;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.agrigenius.MainActivity;
import com.example.agrigenius.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class weatherupdate extends AppCompatActivity {
    private final String CITY = "dhaka,bd";
    private final String API = "06c921750b9a82d8f5d1294e1586276f"; // Use your API key

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weatherupdate);


        WeatherTask weatherTask = new WeatherTask();
        weatherTask.execute();
    }

    private class WeatherTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            RelativeLayout mainContainer = findViewById(R.id.mainContainer);

            mainContainer.setVisibility(View.GONE);
        }

        @Override
        protected String doInBackground(String... params) {
            String response;
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL("https://api.openweathermap.org/data/2.5/weather?q=" + CITY + "&units=metric&appid=" + API).openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                reader.close();
                connection.disconnect();
                response = stringBuilder.toString();
            } catch (Exception e) {
                response = null;
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                JSONObject jsonObj = new JSONObject(result);
                JSONObject main = jsonObj.getJSONObject("main");
                JSONObject sys = jsonObj.getJSONObject("sys");
                JSONObject wind = jsonObj.getJSONObject("wind");
                JSONObject weather = jsonObj.getJSONArray("weather").getJSONObject(0);

                long updatedAt = jsonObj.getLong("dt");
                String updatedAtText = "Updated at: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(new Date(updatedAt * 1000));
                String temp = main.getString("temp") + "°C";
                String tempMin = "Min Temp: " + main.getString("temp_min") + "°C";
                String tempMax = "Max Temp: " + main.getString("temp_max") + "°C";
                String pressure = main.getString("pressure");
                String humidity = main.getString("humidity");

                long sunrise = sys.getLong("sunrise");
                long sunset = sys.getLong("sunset");
                String windSpeed = wind.getString("speed");
                String weatherDescription = weather.getString("description");

                String address = jsonObj.getString("name") + ", " + sys.getString("country");

                TextView addressText = findViewById(R.id.address);
                addressText.setText(address);

                TextView updatedAtTextView = findViewById(R.id.updated_at);
                updatedAtTextView.setText(updatedAtText);

                TextView statusText = findViewById(R.id.status);
                statusText.setText(weatherDescription.toUpperCase());

                TextView tempText = findViewById(R.id.temp);
                tempText.setText(temp);



                RelativeLayout mainContainer = findViewById(R.id.mainContainer);
                mainContainer.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }













    }        @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(weatherupdate.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
