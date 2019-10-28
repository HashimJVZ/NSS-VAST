package com.vast.nss.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vast.nss.R;
import com.vast.nss.Model.Event;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {

    private Context context;
    private List<Event> eventList;

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
        myViewHolder.hoursTextView.setText(eventList.get(i).getHours());
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titleTextView;
        TextView locationTextView;
        TextView dateTextView;
        TextView categoryTextView;
        TextView hoursTextView;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            titleTextView = itemView.findViewById(R.id.titleTextView);
            locationTextView = itemView.findViewById(R.id.locationTextview);
            dateTextView = itemView.findViewById(R.id.date_textView);
            categoryTextView = itemView.findViewById(R.id.categoryTextView);
            hoursTextView = itemView.findViewById(R.id.hoursTextView);

        }
    }
}
