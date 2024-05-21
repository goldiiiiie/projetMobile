package com.example.projetandroid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Weather extends AppCompatActivity {
    ImageView img;
    TextView ville, tmp, tmpmin, tmpmax, txtpression, txthumidite, txtdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        img = findViewById(R.id.img);
//        img.setImageResource(R.drawable.meteo);

        ville = findViewById(R.id.txtville);
        tmp = findViewById(R.id.temp);
        tmpmin = findViewById(R.id.tempmin);
        tmpmax = findViewById(R.id.tempmax);
        txtpression = findViewById(R.id.pression);
        txthumidite = findViewById(R.id.humid);
        txtdate = findViewById(R.id.date);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ville.setText(query);
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "https://api.openweathermap.org/data/2.5/weather?q=" + query + "&appid=c5a35829c69b20713c96a0d45ba5a8e8";
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Date date = new Date(jsonObject.getLong("dt") * 1000);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy' T 'HH:mm");
                            String dateString = simpleDateFormat.format(date);
                            JSONObject main = jsonObject.getJSONObject("main");
                            int Temp = (int) (main.getDouble("temp") - 273.15);
                            int TempMin = (int) (main.getDouble("temp_min") - 273.15);
                            int TempMax = (int) (main.getDouble("temp_max") - 273.15);
                            int Pression = main.getInt("pressure");
                            int Humidite = main.getInt("humidity");
                            JSONArray weather = jsonObject.getJSONArray("weather");
                            txtdate.setText(dateString);
                            tmp.setText(String.valueOf(Temp + " C"));
                            tmpmin.setText(String.valueOf(TempMin + " C"));
                            tmpmax.setText(String.valueOf(TempMax + " C"));
                            txtpression.setText(String.valueOf(Pression + " hPa"));
                            txthumidite.setText(String.valueOf(Humidite + "%"));
                            String meteo = weather.getJSONObject(0).getString("main");
                            Toast.makeText(getApplicationContext(), meteo, Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                queue.add(stringRequest);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }
//    public void setImage(String s)
//    {
//        ImageView imgview = findViewById(R.id.img);
//        if(s.equals("Rain")){
//            imgview.setImageResource(R.drawable.rain);
//        }else if(s.equals("Clear")){
//            imgview.setImageResource(R.drawable.clear);
//        }else if(s.equals("Thunderstorm")){
//        imgview.setImageResource(R.drawable.thunderstorm);
//        }else if(s.equals("Clouds")){
//            imgview.setImageResource(R.drawable.cloudy);
//        }
//    }

}