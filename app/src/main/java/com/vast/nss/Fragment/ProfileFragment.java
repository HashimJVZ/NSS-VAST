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
import com.vast.nss.R;

public class ProfileFragment extends Fragment {

    TextView profileName, profileId, profileHours, profileUnit, profileDept, ProfileContact,
        communityHour, campHour, orientationHour, campusHour;

    DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        profileName = profileName.findViewById(R.id.profile_name);
        profileId = profileId.findViewById(R.id.profile_userid);
        profileHours = profileHours.findViewById(R.id.profile_hours);

        return inflater.inflate(R.layout.fragment_profile1, container, false);




    }
}
