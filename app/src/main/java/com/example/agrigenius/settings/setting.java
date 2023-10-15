package com.example.agrigenius.settings;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.agrigenius.MainActivity;
import com.example.agrigenius.R;

public class setting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);




        RadioGroup languageRadioGroup = findViewById(R.id.languageRadioGroup);
        RadioButton englishRadioButton = findViewById(R.id.englishRadioButton);
        RadioButton bengaliRadioButton = findViewById(R.id.bengaliRadioButton);

        LanguageManager languageManager=new LanguageManager(this);
        String currentLanguage = languageManager.getCurrentLanguage();


// Set the radio button based on the current language
        if (currentLanguage.equals("en")) {
            englishRadioButton.setChecked(true);
        } else if (currentLanguage.equals("bn")) {
            bengaliRadioButton.setChecked(true);
        }

        languageRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.englishRadioButton) {
                languageManager.updateResource("en");

            } else if (checkedId == R.id.bengaliRadioButton) {
                languageManager.updateResource("bn");
            }

        });

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(setting.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}