package com.example.agrigenius;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class cropsRecommendation extends AppCompatActivity {
    Spinner phSpinner, soilTypeSpinner, climateSpinner;
    Button recommendButton;
    TextView recommendationTextView;
    ArrayAdapter<CharSequence> phAdapter, soilTypeAdapter, climateAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crops_recommendation);
        phSpinner = findViewById(R.id.phSpinner);
        soilTypeSpinner = findViewById(R.id.soilTypeSpinner);
        climateSpinner = findViewById(R.id.climateSpinner);
        recommendButton = findViewById(R.id.recommendButton);
        recommendationTextView = findViewById(R.id.recommendationTextView);



        // Create ArrayAdapter and set it to each spinner
        phAdapter = ArrayAdapter.createFromResource(this, R.array.ph_options, android.R.layout.simple_spinner_item);
        phAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        phSpinner.setAdapter(phAdapter);

        soilTypeAdapter = ArrayAdapter.createFromResource(this, R.array.soil_type_options, android.R.layout.simple_spinner_item);
        soilTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        soilTypeSpinner.setAdapter(soilTypeAdapter);

        climateAdapter = ArrayAdapter.createFromResource(this, R.array.climate_options, android.R.layout.simple_spinner_item);
        climateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        climateSpinner.setAdapter(climateAdapter);

        recommendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Capture user input from the spinners
                String phLevel = phSpinner.getSelectedItem().toString();
                String soilType = soilTypeSpinner.getSelectedItem().toString();
                String climate = climateSpinner.getSelectedItem().toString();

                // Perform recommendation logic
                String recommendedCrops = recommendCrops(phLevel, soilType, climate);

                // Display recommendations
                recommendationTextView.setText("Recommended crops: " + recommendedCrops);
            }
        });














    }


    // Updated recommendation logic with conditions for multiple crops
    private String recommendCrops(String phLevel, String soilType, String climate) {
        if (climate.equalsIgnoreCase("temperate")) {
            if (soilType.equalsIgnoreCase("loamy")) {
                if (phLevel.equalsIgnoreCase("Acidic")) {
                    return "Blueberries, Potatoes, Corn";
                } else if (phLevel.equalsIgnoreCase("Neutral")) {
                    return "Apples, Cherries, Grapes";
                } else if (phLevel.equalsIgnoreCase("Alkaline")) {
                    return "Peppers";
                }
            } else if (soilType.equalsIgnoreCase("sandy")) {
                if (phLevel.equalsIgnoreCase("Acidic")) {
                    return "Carrots, Radishes";
                } else if (phLevel.equalsIgnoreCase("Neutral")) {
                    return "Beans, Lettuce";
                } else if (phLevel.equalsIgnoreCase("Alkaline")) {
                    return "Cucumbers";
                }
            } else if (soilType.equalsIgnoreCase("clayey")) {
                if (phLevel.equalsIgnoreCase("Acidic")) {
                    return "Squash, Strawberries";
                } else if (phLevel.equalsIgnoreCase("Neutral")) {
                    return "Soybeans";
                } else if (phLevel.equalsIgnoreCase("Alkaline")) {
                    return "Date Palms";
                }
            } // Add more soil types as needed
        } else if (climate.equalsIgnoreCase("tropical")) {
            if (soilType.equalsIgnoreCase("loamy")) {
                if (phLevel.equalsIgnoreCase("Acidic")) {
                    return "Pineapples, Raspberries, Strawberries";
                } else if (phLevel.equalsIgnoreCase("Neutral")) {
                    return "Oranges, Peaches";
                } else if (phLevel.equalsIgnoreCase("Alkaline")) {
                    return "Mangoes, Tomatoes";
                }
            } else if (soilType.equalsIgnoreCase("sandy")) {
                if (phLevel.equalsIgnoreCase("Acidic")) {
                    return "Bananas, Papayas";
                } else if (phLevel.equalsIgnoreCase("Neutral")) {
                    return "Coconuts, Limes";
                } else if (phLevel.equalsIgnoreCase("Alkaline")) {
                    return "Papayas, Pineapples";
                }
            } else if (soilType.equalsIgnoreCase("clayey")) {
                if (phLevel.equalsIgnoreCase("Acidic")) {
                    return "Cassava, Taro";
                } else if (phLevel.equalsIgnoreCase("Neutral")) {
                    return "Coffee, Cocoa";
                } else if (phLevel.equalsIgnoreCase("Alkaline")) {
                    return "Sugarcane";
                }
            } // Add more soil types as needed
        } else if (climate.equalsIgnoreCase("arid")) {
            if (soilType.equalsIgnoreCase("loamy")) {
                if (phLevel.equalsIgnoreCase("Acidic")) {
                    return "Almonds, Figs";
                } else if (phLevel.equalsIgnoreCase("Neutral")) {
                    return "Pomegranates";
                } else if (phLevel.equalsIgnoreCase("Alkaline")) {
                    return "Date Palms";
                }
            } else if (soilType.equalsIgnoreCase("sandy")) {
                if (phLevel.equalsIgnoreCase("Acidic")) {
                    return "Pistachios, Pistachios";
                } else if (phLevel.equalsIgnoreCase("Neutral")) {
                    return "Jojoba";
                } else if (phLevel.equalsIgnoreCase("Alkaline")) {
                    return "Aloe Vera, Cactus";
                }
            } else if (soilType.equalsIgnoreCase("clayey")) {
                if (phLevel.equalsIgnoreCase("Acidic")) {
                    return "Sesame, Strawberries";
                } else if (phLevel.equalsIgnoreCase("Neutral")) {
                    return "Chickpeas";
                } else if (phLevel.equalsIgnoreCase("Alkaline")) {
                    return "Sugarcane, Cotton";
                }
            } // Add more soil types as needed
        } // Add more climate conditions as needed

        return "No specific recommendations for this input.";
    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(cropsRecommendation.this,MainActivity.class);
        startActivity(intent);
        finish();
    }



}
