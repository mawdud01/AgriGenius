package com.example.agrigenius.profitcalculation;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.agrigenius.R;

import java.util.ArrayList;
import java.util.List;

public class profitcalculator extends AppCompatActivity {

    private Spinner cropsSpinner;
    private LinearLayout detailsLayout;

    // Database-related variables
    private SQLiteDatabase database;
    TextView totalProfit;
    EditText totalSell;
    Button calculateProfit, deleteCrops; // Added deleteCrops button
    private static final String DATABASE_NAME = "ProfitCalculator";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profitcalculator);

        // Initialize database
        database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        // Initialize UI components
        cropsSpinner = findViewById(R.id.customerSpinner);
        detailsLayout = findViewById(R.id.tableLayout);
        totalProfit = findViewById(R.id.totalprofit);
        totalSell = findViewById(R.id.totalsell);
        calculateProfit = findViewById(R.id.totalprofitcalculate);
        deleteCrops = findViewById(R.id.deletecrops); // Initialize the deleteCrops button

        // Populate the crop spinner with crop names from the database
        List<String> cropsNames = fetchCropsNames();
        ArrayAdapter<String> cropsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cropsNames);
        cropsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cropsSpinner.setAdapter(cropsAdapter);

        // Handle crop selection
        cropsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Clear previous details
                detailsLayout.removeAllViews();

                String selectedCrop = cropsSpinner.getSelectedItem().toString();
                displayCropDetails(selectedCrop);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });

        calculateProfit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateAndDisplayProfit();
            }
        });

        deleteCrops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedCrop = cropsSpinner.getSelectedItem().toString();
                if (!selectedCrop.isEmpty()) {
                    // Call a method to delete the selected crop and its history
                    deleteCropAndHistory(selectedCrop);
                }
            }
        });
    }

    private void displayCropDetails(String cropName) {
        Cursor cursor = database.rawQuery("SELECT * FROM buyingcost_table WHERE customer_name = ?", new String[]{cropName});

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String materialName = cursor.getString(cursor.getColumnIndex("fertilizer_name"));
                double buyingAmount = cursor.getDouble(cursor.getColumnIndex("amount"));
                String date = cursor.getString(cursor.getColumnIndex("date"));

                TextView textView = new TextView(this);
                textView.setText("Material Name: " + materialName + "\n" +
                        "Buying Amount: $" + String.format("%.2f", buyingAmount) + "\n" +
                        "Date: " + date + "\n");

                detailsLayout.addView(textView);
            }
            cursor.close();
        }
    }

    private List<String> fetchCropsNames() {
        List<String> cropsNames = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT DISTINCT customer_name FROM buyingcost_table", null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                cropsNames.add(cursor.getString(cursor.getColumnIndex("customer_name")));
            }
            cursor.close();
        }

        return cropsNames;
    }

    private void calculateAndDisplayProfit() {
        String totalSellText = totalSell.getText().toString();
        if (totalSellText.isEmpty()) {
            Toast.makeText(this, "Please enter the total sell amount.", Toast.LENGTH_SHORT).show();
            return;
        }

        double totalSellAmount = Double.parseDouble(totalSellText);
        double totalBuyingAmount = calculateTotalBuyingAmount(cropsSpinner.getSelectedItem().toString());
        double profit = totalSellAmount - totalBuyingAmount;

        totalProfit.setText("Total Profit: " + String.format("%.2f", profit) + " BDT");
    }

    private double calculateTotalBuyingAmount(String cropName) {
        Cursor cursor = database.rawQuery("SELECT SUM(amount) AS total FROM buyingcost_table WHERE customer_name = ?", new String[]{cropName});

        if (cursor != null && cursor.moveToFirst()) {
            double totalBuyingAmount = cursor.getDouble(cursor.getColumnIndex("total"));
            cursor.close();
            return totalBuyingAmount;
        }
        return 0.0; // Return 0 if there's no data or an error occurs.
    }

    private void deleteCropAndHistory(String cropName) {
        database.execSQL("DELETE FROM buyingcost_table WHERE customer_name = ?", new String[]{cropName});
        // After deleting, you might want to update the spinner and clear detailsLayout
        updateCropsSpinner();
        detailsLayout.removeAllViews();
        totalProfit.setText("");
    }

    private void updateCropsSpinner() {
        List<String> cropsNames = fetchCropsNames();
        ArrayAdapter<String> cropsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cropsNames);
        cropsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cropsSpinner.setAdapter(cropsAdapter);
    }
}
