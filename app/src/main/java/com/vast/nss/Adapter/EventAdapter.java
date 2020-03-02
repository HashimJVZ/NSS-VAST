package com.vast.nss.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vast.nss.Model.Event;
import com.vast.nss.R;
import com.vast.nss.SearchActivity;
import com.vast.nss.SignUpActivity;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {

    private Context context;
    private List<Event> eventList;
    private String eventKey;

    public EventAdapter(Context context, List<Event> eventList){
        this.context = context;
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_event, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.titleTextView.setText(eventList.get(i).getTitle());
        myViewHolder.locationTextView.setText(eventList.get(i).getLocation());
        myViewHolder.dateTextView.setText(eventList.get(i).getDate());
        myViewHolder.categoryTextView.setText(eventList.get(i).getCategory());
        String hours = ("Hours: " + eventList.get(i).getHours());
        myViewHolder.hoursTextView.setText(hours);


        myViewHolder.eventCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SignUpActivity.class);
                context.startActivity(intent);
            }
        });

        myViewHolder.eventCardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //todo : create dialog here

                Dialog deleteDialog = new Dialog(context);
                deleteDialog.setContentView(R.layout.dialog_event_delete);
                deleteDialog.show();
                eventKey = eventList.get(i).getEventKey();
//                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("events").child(eventKey);
//                databaseReference.removeValue();

                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titleTextView;
        TextView locationTextView;
        TextView dateTextView;
        TextView categoryTextView;
        TextView hoursTextView;
        CardView eventCardView;

        MyViewHolder(@NonNull View itemView){
            super(itemView);

            titleTextView = itemView.findViewById(R.id.titleTextView);
            locationTextView = itemView.findViewById(R.id.locationTextview);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            categoryTextView = itemView.findViewById(R.id.categoryTextView);
            hoursTextView = itemView.findViewById(R.id.hoursTextView);
            eventCardView = itemView.findViewById(R.id.eventCardView);

        }
    }

//    public static void delete(){
//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("events").child(eventKey);
//        databaseReference.removeValue();
//
//    }
}
