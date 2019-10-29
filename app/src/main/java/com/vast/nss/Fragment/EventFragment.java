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
import java.util.List;

public class EventFragment extends Fragment {

    public interface ClickListnerEvent {
        void clicked();
    }

    private RecyclerView eventRecyclerView;
    private FloatingActionButton floatingActionButton;

    private EventAdapter eventAdapter;

    private ClickListnerEvent clickListner;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference =  firebaseDatabase.getReference();

    public EventFragment(ClickListnerEvent clickListener) {
        this.clickListner = clickListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event, container, false);
        Log.d("mylog","Oncreate");

        eventRecyclerView = view.findViewById(R.id.eventRecyclerView);
        eventRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //this is only for the admin
        floatingActionButton = view.findViewById(R.id.event_creation_fab);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListner.clicked();
            }
        });

        Query query = databaseReference.child("events").orderByKey();

        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("mylog","onDataChange");

                List<Event> list = new ArrayList<>();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Event event = new Event();
                    event.setTitle(ds.child("title").getValue().toString());
                    event.setLocation(ds.child("location").getValue().toString());
                    event.setDate(ds.child("date").getValue().toString());
                    event.setCategory(ds.child("category").getValue().toString());
                    event.setHours(ds.child("hours").getValue().toString());


                    list.add(event);

                }
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

    public void setClickListner(ClickListnerEvent clickListner) {
        this.clickListner = clickListner;
    }
}
