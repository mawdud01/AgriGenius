package com.example.agrigenius.profitcalculation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.agrigenius.R;

public class Profitwindow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profitwindow);

        Button addcrops = findViewById(R.id.addcrops);
        Button addcost = findViewById(R.id.addamount);
        Button display = findViewById(R.id.display);

        addcrops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profitwindow.this, Profitmanager.class);
                startActivity(intent);
            }
        });



        addcost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profitwindow.this, AddallCost.class);
                startActivity(intent);
            }
        });

        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profitwindow.this, profitcalculator.class);
                startActivity(intent);
            }
        });
    }
}