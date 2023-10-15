package com.example.agrigenius.profitcalculation;



import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.agrigenius.R;

public class Profitmanager extends AppCompatActivity {
    private EditText editTextName, editTextCreationDate;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profitmanager);

        editTextName = findViewById(R.id.editTextName);

        editTextCreationDate = findViewById(R.id.editTextCreationDate);

        saveButton = findViewById(R.id.saveButton);


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String creationDate = editTextCreationDate.getText().toString();


                // Check if the email is valid or not
                if (name.isEmpty()) {
                    Toast.makeText(Profitmanager.this, "Add Something.", Toast.LENGTH_SHORT).show();
                    return;
                }




                SQLiteDatabase db = openOrCreateDatabase("ProfitCalculator", MODE_PRIVATE, null);
                db.execSQL("CREATE TABLE IF NOT EXISTS CropsAdd(name TEXT UNIQUE, creation_date TEXT)");

                ContentValues values = new ContentValues();
                values.put("name", name);
                values.put("creation_date", creationDate);


                // Check if a customer with the same name
                Cursor cursor = db.rawQuery("SELECT * FROM CropsAdd WHERE name=?", new String[]{name});
                if (cursor.getCount() > 0) {
                    Toast.makeText(Profitmanager.this, "Crops with the same name already exists. Enter a different username.", Toast.LENGTH_SHORT).show();
                } else {
                    // No customer with the same name, insert the new customer
                    long result = db.insert("CropsAdd", null, values);

                    if (result != -1) {
                        Toast.makeText(Profitmanager.this, "Crops and amount added successfully.", Toast.LENGTH_SHORT).show();
                        // Clear input fields
                        clearInputFields();
                    } else {
                        Toast.makeText(Profitmanager.this, "Error adding customer.", Toast.LENGTH_SHORT).show();
                    }
                }
                cursor.close();
            }
        });

    }

    private void clearInputFields() {
        editTextName.setText("");
        editTextCreationDate.setText("");

    }



    public void showDatePickerDialog(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                        editTextCreationDate.setText(selectedDate);
                    }
                },
                // Set the initial date (optional)
                2023, 0, 1
        );

        // Show the DatePickerDialog
        datePickerDialog.show();
    }
}

