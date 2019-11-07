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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.vast.nss.Adapter.UserListAdapter;
import com.vast.nss.R;

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

        //todo from here

        return view;
    }
}
