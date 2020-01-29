package com.vast.nss.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vast.nss.R;

public class ProfileFragment extends Fragment {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference =  firebaseDatabase.getReference();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView profileName = view.findViewById(R.id.profile_name);
        TextView profileId = view.findViewById(R.id.profile_userid);
        TextView profileHours = view.findViewById(R.id.profile_hours);
        TextView profileUnit = view.findViewById(R.id.profile_unit);
        TextView profileDept = view.findViewById(R.id.profile_dept);
        TextView profileContact = view.findViewById(R.id.profile_contact);
        TextView profileCommunityHour = view.findViewById(R.id.community_hour);
        TextView profileCampHour = view.findViewById(R.id.camp_hour);
        TextView profileOrientationHour = view.findViewById(R.id.orientation_hour);
        TextView profileCampusHour = view.findViewById(R.id.campus_hour);

//        String user = FirebaseAuth.getInstance().getUid();
//        String name = databaseReference.child("profile").child(user).child("name").;




        return view;


    }
}
