package com.vast.nss.Dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.vast.nss.R;

import java.util.Objects;

public class EventDeleteDialog extends DialogFragment {

    private ImageView deleteView, cancelView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_event_delete, container, false);

        deleteView.findViewById(R.id.delete_icon);
        cancelView.findViewById(R.id.dialog_cancel);

        cancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objects.requireNonNull(getDialog()).dismiss();
            }
        });

        deleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("events").child(eventKey);
//                databaseReference.removeValue();
//                EventAdapter.delete();
            }
        });

        return view;

    }
}
