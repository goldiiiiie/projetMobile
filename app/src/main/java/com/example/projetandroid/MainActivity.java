package com.example.projetandroid;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    CardView cardView1 ;
    CardView cardView2 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        cardView1 = findViewById(R.id.card5);
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Maps.class);
                startActivity(intent);
                finish();
            }
        });

        cardView2 = findViewById(R.id.card10);
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Weather.class);
                startActivity(intent);
                finish();
            }
        });





        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_home:
// Action lorsque l'élément "Home" est sélectionné
                    return true;
                case R.id.menu_contact:
// Action lorsque l'élément "Contact" est sélectionné
                    return true;
                case R.id.menu_signout:
// Action lorsque l'élément "Sign Out" est sélectionné
                    return true;
                default:
                    return false;
            }
        });


    }
}
