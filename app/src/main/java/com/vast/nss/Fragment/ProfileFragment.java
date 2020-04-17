package com.vast.nss.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.vast.nss.R;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment {

    private CircleImageView profile_pic;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);

        final TextView profileName = view.findViewById(R.id.profile_name);
        final TextView profileNssId = view.findViewById(R.id.profile_nssId);
        final TextView profileCollegeId = view.findViewById(R.id.profile_collegeId);
        final TextView profileUnit = view.findViewById(R.id.profile_unit);
        final TextView profileDept = view.findViewById(R.id.profile_dept);
        final TextView profileContact = view.findViewById(R.id.profile_contact);
        final TextView profileCommunityHour = view.findViewById(R.id.community_hour);
        final TextView profileCampHour = view.findViewById(R.id.camp_hour);
        final TextView profileOrientationHour = view.findViewById(R.id.orientation_hour);
        final TextView profileCampusHour = view.findViewById(R.id.campus_hour);
        final TextView profileHours = view.findViewById(R.id.profile_hours);

        String user = getUser();
        databaseReference.child("profile").child(user).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = (String) dataSnapshot.child("name").getValue();
                String nssId = dataSnapshot.getKey();
                String collegeId = (String) dataSnapshot.child("collegeId").getValue();
                String unit = Objects.requireNonNull(nssId).substring(0, 3);
                String dept = (String) dataSnapshot.child("department").getValue();
                String contact = (String) dataSnapshot.child("contact").getValue();
                String photoUrl = (String) dataSnapshot.child("photoUrl").getValue();
                long communityHour = (long) dataSnapshot.child("communityHour").getValue();
                long campHour = (long) dataSnapshot.child("campHour").getValue();
                long orientationHour = (long) dataSnapshot.child("orientationHour").getValue();
                long campusHour = (long) dataSnapshot.child("campusHour").getValue();
                long hours = communityHour + orientationHour + campusHour;

                profileName.setText(name);
                profileNssId.setText(nssId);
                profileCollegeId.setText(collegeId);
                profileUnit.setText(unit);
                profileDept.setText(dept);
                profileContact.setText(contact);
                profileCommunityHour.setText(String.valueOf(communityHour));
                profileCampHour.setText(String.valueOf(campHour));
                profileOrientationHour.setText(String.valueOf(orientationHour));
                profileCampusHour.setText(String.valueOf(campusHour));
                profileHours.setText(String.valueOf(hours));

                profile_pic = view.findViewById(R.id.profilePic);
                Picasso.get().load(photoUrl).into(profile_pic);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String getUser() {
        SharedPreferences sharedPreferences = Objects.requireNonNull(this.getActivity()).getSharedPreferences("SharedPref", MODE_PRIVATE);
        return sharedPreferences.getString("userName", null);
    }


}
