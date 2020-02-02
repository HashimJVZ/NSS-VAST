package com.vast.nss.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.vast.nss.Model.Attendance;
import com.vast.nss.R;

import java.util.List;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.MyViewHolder> {

    private Context context;
    private List<Attendance> attendanceList;

    public AttendanceAdapter(Context context, List<Attendance> attendaceList){
        this.context = context;
        this.attendanceList = attendaceList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_attendance, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.attendanceTitle.setText(attendanceList.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView attendanceTitle;
//        TextView locationTextView;
//        TextView dateTextView;
//        TextView categoryTextView;
//        TextView hoursTextView;
        CardView attendanceCardView;

        MyViewHolder(@NonNull View itemView){
            super(itemView);

            attendanceTitle = itemView.findViewById(R.id.attendanceTitle);
//            locationTextView = itemView.findViewById(R.id.locationTextview);
//            dateTextView = itemView.findViewById(R.id.dateTextView);
//            categoryTextView = itemView.findViewById(R.id.categoryTextView);
//            hoursTextView = itemView.findViewById(R.id.hoursTextView);
            attendanceCardView = itemView.findViewById(R.id.attendanceCardView);

        }
    }
}
