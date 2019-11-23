package com.vast.nss.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vast.nss.R;

public class ProfileFragment extends Fragment {

    private TextView profileName, profileId, profileHours, profileUnit, profileDept, profileContact,
        communityHour, campHour, orientationHour, campusHour;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference =  firebaseDatabase.getReference();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//        profileName = profileName.findViewById(R.id.profile_name);
//        profileId = profileId.findViewById(R.id.profile_userid);
//        profileHours = profileHours.findViewById(R.id.profile_hours);
//        profileUnit = profileUnit.findViewById(R.id.profile_unit);
//        profileDept = profileDept.findViewById(R.id.profile_dept);
//        profileContact = profileContact.findViewById(R.id.profile_contact);
//        communityHour = communityHour.findViewById(R.id.community_hour);
//        campHour = campHour.findViewById(R.id.camp_hour);
//        orientationHour = orientationHour.findViewById(R.id.orientation_hour);
//        campusHour = campusHour.findViewById(R.id.campus_hour);



        return inflater.inflate(R.layout.fragment_profile1, container, false);


    }
}
