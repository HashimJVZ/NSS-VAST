package com.vast.nss.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vast.nss.ClickListener;
import com.vast.nss.R;

public class EventFragment extends Fragment {

    public interface ClickListnerEvent {
        void clicked();
    }

    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;

    private ClickListnerEvent clickListner;

    public EventFragment(ClickListnerEvent clickListner) {
        this.clickListner = clickListner;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event, container, false);

        floatingActionButton = view.findViewById(R.id.event_creation_fab);
        //this is only for the admin

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListner.clicked();
            }
        });

        return view;
    }

    public void setClickListner(ClickListnerEvent clickListner) {
        this.clickListner = clickListner;
    }
}
