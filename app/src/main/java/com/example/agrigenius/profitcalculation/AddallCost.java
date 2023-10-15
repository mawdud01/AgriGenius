package com.example.agrigenius.profitcalculation;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.agrigenius.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddallCost extends AppCompatActivity {

    private Spinner customerSpinner;
    private EditText fertilizerNameEditText, amountEditText, totalBuyingAmountEditText, editTextCreationDate;
    private Button addItemsButton;

    // Database-related variables
    private SQLiteDatabase database;
    private static final String DATABASE_NAME = "ProfitCalculator";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addall_cost);

        // Initialize database
        database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        // Create the "buyingcost_table" if it doesn't exist
        createBuyingCostTable();

        // Initialize UI components
        customerSpinner = findViewById(R.id.customerSpinner);
        fertilizerNameEditText = findViewById(R.id.materialname);
        amountEditText = findViewById(R.id.totalbuyingamount);
        totalBuyingAmountEditText = findViewById(R.id.total_amount);
        addItemsButton = findViewById(R.id.placeOrderButton);
        editTextCreationDate = findViewById(R.id.editTextCreationDate);

        totalBuyingAmountEditText.setEnabled(false); // Make it non-editable

        editTextCreationDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        // Populate the customer spinner with customer names from the database
        List<String> cropsNames = fetchCropsNames();
        ArrayAdapter<String> customerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cropsNames);
        customerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        customerSpinner.setAdapter(customerAdapter);

        addItemsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve selected customer (cropsname)
                String selectedCustomer = customerSpinner.getSelectedItem().toString();

                // Retrieve fertilizer name, amount, and total buying amount from EditTexts
                String fertilizerName = fertilizerNameEditText.getText().toString().trim();
                String amountText = amountEditText.getText().toString().trim();
                String totalBuyingAmountText = totalBuyingAmountEditText.getText().toString().trim();
                String date = editTextCreationDate.getText().toString().trim();

                if (fertilizerName.isEmpty() || amountText.isEmpty()) {
                    Toast.makeText(AddallCost.this, "Please enter fertilizer name and amount.", Toast.LENGTH_SHORT).show();
                    return;
                }

                double amount = Double.parseDouble(amountText);
                double totalBuyingAmount = totalBuyingAmountText.isEmpty() ? amount : Double.parseDouble(totalBuyingAmountText) + amount;

                // Save the data to the database
                boolean success = saveDataToDatabase(selectedCustomer, fertilizerName, amount, date, totalBuyingAmount);

                if (success) {
                    Toast.makeText(AddallCost.this, "Data added successfully.", Toast.LENGTH_SHORT).show();
                    clearFields();
                } else {
                    Toast.makeText(AddallCost.this, "Error adding data.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Method to create the "buyingcost_table" if it doesn't exist
    private void createBuyingCostTable() {
        database.execSQL("CREATE TABLE IF NOT EXISTS buyingcost_table (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "customer_name TEXT, " +
                "fertilizer_name TEXT, " +
                "amount REAL, " +
                "date TEXT, " + // Add the date column
                "total_buying_amount REAL" +
                ")");
    }

    // Method to save data to the database
    private boolean saveDataToDatabase(String customerName, String fertilizerName, double amount, String date, double totalBuyingAmount) {
        try {
            ContentValues values = new ContentValues();
            values.put("customer_name", customerName);
            values.put("fertilizer_name", fertilizerName);
            values.put("amount", amount);
            values.put("date", date); // Add the date
            values.put("total_buying_amount", totalBuyingAmount);

            long result = database.insert("buyingcost_table", null, values);

            return result != -1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to clear input fields
    private void clearFields() {
        fertilizerNameEditText.setText("");
        amountEditText.setText("");
        totalBuyingAmountEditText.setText("");
        editTextCreationDate.setText("");
    }

    private List<String> fetchCropsNames() {
        List<String> cropsNames = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT name FROM CropsAdd", null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                cropsNames.add(cursor.getString(cursor.getColumnIndex("name")));
            }
            cursor.close();
        }

        return cropsNames;
    }

    public void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                        editTextCreationDate.setText(selectedDate);
                    }
                },
                year, month, day
        );

        // Show the DatePickerDialog
        datePickerDialog.show();
    }
}
