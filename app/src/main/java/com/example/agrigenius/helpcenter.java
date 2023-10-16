package com.example.agrigenius;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class helpcenter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helpcenter);
    }


    public void openWhatsApp(View view) {

        String phoneNumber = "+8801728776684";
        String message = "Hello, let's chat!";

        // Create an Intent with the ACTION_VIEW action and a WhatsApp URI
        Uri uri = Uri.parse("smsto:" + phoneNumber);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.putExtra("sms_body", message);


        // Set the package
        intent.setPackage("com.whatsapp");


        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "WhatsApp is not installed on your phone", Toast.LENGTH_SHORT).show();
        }
    }
}
