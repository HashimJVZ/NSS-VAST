package com.vast.nss;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vast.nss.Fragment.DatePickerFragment;

import java.util.HashMap;

public class EventCreationActivity extends AppCompatActivity {

    static TextView setDateTextView;
    DatabaseReference databaseReference;

    public static void changeText(int year, int month, int day) {
        String date = "Date: " + day + "/" + month + "/" + year;
        setDateTextView.setText(date);
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_creation);


        setDateTextView = findViewById(R.id.setdate_textView);
        databaseReference = FirebaseDatabase.getInstance().getReference();


        final Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.category_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Button button = findViewById(R.id.createBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                EditText editTextTitle = findViewById(R.id.editTextTitle);
                EditText editTextLocation = findViewById(R.id.editTextLocation);
                EditText editTextHours = findViewById(R.id.editTextHours);

                String title = editTextTitle.getText().toString();
                String location = editTextLocation.getText().toString();
                String hrs = editTextHours.getText().toString();
                long hours = Long.parseLong(hrs);
                String temp = setDateTextView.getText().toString();
                String date = temp.substring(6);
                String category = spinner.getSelectedItem().toString();
                Log.d("mylog", "title=" + title);
                Log.d("mylog", "location=" + location);
                Log.d("mylog", "hours=" + hours);
                Log.d("mylog", "date=" + date);
                Log.d("mylog", "spinner=" + category);

                HashMap<String, Object> map = new HashMap<>();
                map.put("title", title);
                map.put("location", location);
                map.put("hours", hours);
                map.put("category", category);
                map.put("date", date);

                databaseReference.child("events").push().updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        finish();
                    }
                });
            }
        });
    }
}

