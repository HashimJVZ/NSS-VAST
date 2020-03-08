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
import com.vast.nss.Adapter.UserListAdapter;
import com.vast.nss.Model.UserList;
import com.vast.nss.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserListFragment extends Fragment {

    private RecyclerView userListRecyclerView;

    private UserListAdapter userListAdapter;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);

        userListRecyclerView = view.findViewById(R.id.userListRecyclerView);
        userListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Query query = databaseReference.child("profile").orderByKey();

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<UserList> list = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    UserList userList = new UserList();
                    userList.setUser_name(Objects.requireNonNull(ds.child("name").getValue()).toString());
                    userList.setUser_id(Objects.requireNonNull(ds.child("nssID").getValue()).toString());

                    list.add(userList);
                }
                userListAdapter = new UserListAdapter(getContext(), list);
                userListRecyclerView.setAdapter(userListAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }
}
