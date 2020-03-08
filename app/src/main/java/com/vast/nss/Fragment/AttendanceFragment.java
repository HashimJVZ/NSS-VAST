package com.vast.nss.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.vast.nss.Adapter.AttendanceAdapter;
import com.vast.nss.Model.Attendance;
import com.vast.nss.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AttendanceFragment extends Fragment {

    private RecyclerView attendanceRecyclerView;
    private AttendanceAdapter attendanceAdapter;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendance, container, false);

        attendanceRecyclerView = view.findViewById(R.id.attendanceRecyclerView);
        attendanceRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Query query = databaseReference.child("events").orderByKey();
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Attendance> list = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    Attendance attendance = new Attendance();
                    attendance.setTitle((String) ds.child("title").getValue());
                    attendance.setLocation((String) ds.child("location").getValue());
                    attendance.setDate((String) ds.child("date").getValue());
                    attendance.setKey(ds.getKey());

                    list.add(attendance);
                }
                Collections.reverse(list);
                attendanceAdapter = new AttendanceAdapter(getContext(), list);
                attendanceRecyclerView.setAdapter(attendanceAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }
}
