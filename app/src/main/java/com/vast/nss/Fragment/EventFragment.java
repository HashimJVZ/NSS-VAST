package com.vast.nss.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class EventFragment extends Fragment {

    public interface ClickListenerEvent {
        void clicked();
    }

//    private int count;

    private RecyclerView eventRecyclerView;
    private FloatingActionButton floatingActionButton;

    private EventAdapter eventAdapter;

    private ClickListenerEvent clickListner;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference =  firebaseDatabase.getReference();

    public EventFragment(ClickListenerEvent clickListener) {
        this.clickListner = clickListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event, container, false);
        Log.d("mylog","Oncreate");

        eventRecyclerView = view.findViewById(R.id.eventRecyclerView);
        eventRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //todo: make this is only for the admin
        //******************************************************************
        floatingActionButton = view.findViewById(R.id.event_creation_fab);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListner.clicked();
                floatingActionButton.setClickable(false);
            }
        });
        //******************************************************************

        Query query = databaseReference.child("events").orderByKey();

        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("mylog","onDataChange");

                List<Event> list = new ArrayList<>();
                for(DataSnapshot ds : dataSnapshot.getChildren()){

                    Event event = new Event();
                    event.setTitle(Objects.requireNonNull(ds.child("title").getValue()).toString());
                    event.setLocation(Objects.requireNonNull(ds.child("location").getValue()).toString());
                    event.setDate(Objects.requireNonNull(ds.child("date").getValue()).toString());
                    event.setCategory(Objects.requireNonNull(ds.child("category").getValue()).toString());
                    event.setHours(Objects.requireNonNull(ds.child("hours").getValue()).toString());
                    event.setEventKey(ds.getKey());

                    list.add(event);
                }
                Collections.reverse(list);
                eventAdapter = new EventAdapter(getContext(), list);
                eventRecyclerView.setAdapter(eventAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("mylog",databaseError.getMessage());
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("mylog","Onresume");
        floatingActionButton.setClickable(true);
    }

}
