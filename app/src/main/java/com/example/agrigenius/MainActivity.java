package com.example.agrigenius;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.agrigenius.diseasedetect.diseasedetection;
import com.example.agrigenius.marketplace.buyandsellmarket;

import com.example.agrigenius.profitcalculation.Profitwindow;
import com.example.agrigenius.settings.setting;
import com.example.agrigenius.weather.weatherupdate;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView imageMenu;
    Button crprec,buyingMarket,cropselling,diseasedetectionbutton;
    TextView address, temp;

    private static final String WEBSITE_URL = "https://greenvillmarket.blogspot.com/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_View);
        crprec=findViewById(R.id.crprec);
        imageMenu=findViewById(R.id.imageMenu);
        buyingMarket=findViewById(R.id.market);
        cropselling=findViewById(R.id.cropselling);
        diseasedetectionbutton=findViewById(R.id.disease);
        Button profitmanage = findViewById(R.id.profitmanage);

        profitmanage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Profitwindow.class);
                startActivity(intent);
            }
        });




        imageMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });



        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch (item.getItemId()) {
                    case R.id.mHome:

                        Toast.makeText(MainActivity.this, "No Backed Work in Home", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.weather:

                        Intent weather = new Intent(MainActivity.this, weatherupdate.class);
                        startActivity(weather);

                        finish();
                        drawerLayout.closeDrawers();
                        break;


                    case R.id.setting:
                        //Toast.makeText(MainActivity.this, "No Backed Work in Home", Toast.LENGTH_SHORT).show();
                         Intent intent = new Intent(MainActivity.this, setting.class);
                        startActivity(intent);
                         finish();
                        drawerLayout.closeDrawers();
                        break;


                    case R.id.about:
                        Toast.makeText(MainActivity.this, "About Developer", Toast.LENGTH_SHORT).show();

                       // Intent intent = new Intent(MainActivity.this,Aboutme.class);
                       // startActivity(intent);
                       // finish();
                        drawerLayout.closeDrawers();
                        break;


                    case R.id.policy:

                       // Intent intent2 = new Intent(MainActivity.this,Policy.class);
                       // startActivity(intent2);
                        //finish();
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.mRate:
                        Toast.makeText(MainActivity.this, "Rate us", Toast.LENGTH_SHORT).show();

                        drawerLayout.closeDrawers();
                        break;
                }

                return false;
            }
        });





        crprec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 Intent intent = new Intent(MainActivity.this,cropsRecommendation.class);
                 startActivity(intent);
                finish();
            }
        });










        buyingMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri webpage = Uri.parse(WEBSITE_URL);
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

                // Check if there's a browser available to handle the intent
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        cropselling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, buyandsellmarket.class);
                startActivity(intent);
                finish();
            }
        });


        diseasedetectionbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, diseasedetection.class);
                startActivity(intent);
                finish();
            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed(); // Call the super method to perform the default back action
        // Additional code to handle the back button action if needed
    }



}