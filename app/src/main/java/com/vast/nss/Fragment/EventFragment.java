package com.vast.nss.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.vast.nss.Adapter.EventAdapter;
import com.vast.nss.Model.Event;
import com.vast.nss.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class EventFragment extends Fragment {

    public interface ClickListenerEvent {
        void clicked();
    }

//    private int count;

    private RecyclerView eventRecyclerView;
    private FloatingActionButton floatingActionButton;

    private EventAdapter eventAdapter;

    private ClickListenerEvent clickListener;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    public EventFragment(ClickListenerEvent clickListener) {
        this.clickListener = clickListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event, container, false);
        Log.d("mylog", "Oncreate");

        eventRecyclerView = view.findViewById(R.id.eventRecyclerView);
        eventRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        floatingActionButton = view.findViewById(R.id.event_creation_fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAdmin()) {
                    clickListener.clicked();
                    floatingActionButton.setClickable(false);
                } else {
                    Toast.makeText(getContext(), "You are not an Admin", Toast.LENGTH_SHORT).show();
                }
            }
        });


        Query query = databaseReference.child("events").orderByKey();

        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("mylog", "onDataChange");

                List<Event> list = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    Event event = new Event();
                    event.setTitle(Objects.requireNonNull(ds.child("title").getValue()).toString());
                    event.setLocation(Objects.requireNonNull(ds.child("location").getValue()).toString());
                    event.setDate(Objects.requireNonNull(ds.child("date").getValue()).toString());
                    event.setCategory(Objects.requireNonNull(ds.child("category").getValue()).toString());
                    event.setHours((Long) Objects.requireNonNull(ds.child("hours").getValue()));
                    event.setEventKey(ds.getKey());

                    list.add(event);
                }
                Collections.reverse(list);
                eventAdapter = new EventAdapter(getContext(), list);
                eventRecyclerView.setAdapter(eventAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("mylog", databaseError.getMessage());
            }
        });

        return view;
    }

    private boolean isAdmin() {
        SharedPreferences sharedPreferences = Objects.requireNonNull(this.getActivity()).getSharedPreferences("SharedPref", MODE_PRIVATE);
        return sharedPreferences.getBoolean("isAdmin", false);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("mylog", "Onresume");
        floatingActionButton.setClickable(true);
    }

}
