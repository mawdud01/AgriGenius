package com.example.agrigenius.diseasedetect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.agrigenius.MainActivity;
import com.example.agrigenius.R;
import com.example.agrigenius.diseasedetect.allcrops.appleclass;
import com.example.agrigenius.diseasedetect.allcrops.tomatoclass;




public class diseasedetection extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diseasedetection);


        ImageButton tomato = findViewById(R.id.tomato);
        ImageButton apple = findViewById(R.id.applebutton);





        tomato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(diseasedetection.this, tomatoclass.class);
                startActivity(intent2);
            }
        });

        apple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(diseasedetection.this, appleclass.class);
                startActivity(intent2);
            }
        });





    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(diseasedetection.this, MainActivity.class);
        startActivity(intent);
        finish();
    }


}