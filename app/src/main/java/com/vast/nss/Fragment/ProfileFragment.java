package com.vast.nss.Fragment;

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

public class ProfileFragment extends Fragment {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference =  firebaseDatabase.getReference();

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



//        String user = FirebaseAuth.getInstance().getUid();
        String user = "9645"; //replace userName from shared pref
        databaseReference.child("profile").child(user).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = (String) dataSnapshot.child("name").getValue();
                String nssId = dataSnapshot.getKey();
                String collegeId = (String) dataSnapshot.child("collegeId").getValue();
                String unit= Objects.requireNonNull(nssId).substring(0,3);
                String dept = (String) dataSnapshot.child("department").getValue();
                String contact = (String) dataSnapshot.child("contact").getValue();
                long communityHour = (long) dataSnapshot.child("communityHour").getValue();
                long campHour = (long) dataSnapshot.child("campHour").getValue();
                long orientationHour = (long) dataSnapshot.child("orientationHour").getValue();
                long campusHour = (long) dataSnapshot.child("campusHour").getValue();
                long hours = communityHour + orientationHour + campusHour;

                CircleImageView profile_pic = view.findViewById(R.id.profilePic);
//        Uri photoUrl = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhotoUrl();
                String photoUrl = "https://pbs.twimg.com/profile_images/1200213251948081152/p1dWQLrP_400x400.jpg";//replace link to storage
                Picasso.get().load(photoUrl).into(profile_pic);

                profileName.setText(name);
                profileNssId.setText(nssId);
                profileCollegeId.setText(collegeId);
                profileUnit.setText(unit);
                profileDept.setText(dept);
                profileContact.setText(contact);

                String community = String.valueOf(communityHour);
                String camp = String.valueOf(campHour);
                String orientation = String.valueOf(orientationHour);
                String campus = String.valueOf(campusHour);
                String totalHours = String.valueOf(hours);
                profileCommunityHour.setText(community);
                profileCampHour.setText(camp);
                profileOrientationHour.setText(orientation);
                profileCampusHour.setText(campus);
                profileHours.setText(totalHours);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;

    }
}
