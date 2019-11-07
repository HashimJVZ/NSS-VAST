package com.vast.nss;

import android.content.Intent;
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
import com.vast.nss.Adapter.EventAdapter;
import com.vast.nss.Fragment.DatePickerFragment;
import com.vast.nss.Model.Event;

import java.util.HashMap;
import java.util.List;

public class EventCreationActivity extends AppCompatActivity  {

    static TextView setdateTextview;
    DatabaseReference databaseReference;

//    private List<Event> eventList;
//    private int count;
//
//    public EventCreationActivity() {
//        count = eventList.size();
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_creation);

        setdateTextview = findViewById(R.id.setdate_textView);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categoty_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Button button = findViewById(R.id.createBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                int new_count = count+1;
//                Log.d("temptag", "new_count="+new_count);

                EditText editTextTitle = findViewById(R.id.editTextTitle);
                EditText editTextLocation = findViewById(R.id.editTextLocation);
                EditText editTextHours = findViewById(R.id.editTextHours);

                String title = editTextTitle.getText().toString();
                String location = editTextLocation.getText().toString();
                String hours = editTextHours.getText().toString();
                String temp = setdateTextview.getText().toString();
                String date = temp.substring(6);
                String category = spinner.getSelectedItem().toString();
                Log.d("mylog","title="+ title);
                Log.d("mylog","location="+ location);
                Log.d("mylog","hours="+ hours);
                Log.d("mylog","date="+ date);
                Log.d("mylog","spinner="+ category);

                HashMap<String, Object> map = new HashMap<>();
                map.put("title",title);
                map.put("location",location);
                map.put("hours",hours);
                map.put("category",category);
                map.put("date",date);

                //todo replace "e4" below with incrementing getItemCount() from EventAdapter
                databaseReference.child("events").child("e4").updateChildren(map).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        startActivity(new Intent(EventCreationActivity.this, MainActivity.class));
                    }
                });
            }
        });
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public static void changeTExt(int year, int month, int day) {
        String date = "Date: "+day+"/"+month+"/"+year;
        setdateTextview.setText(date);
    }




}
