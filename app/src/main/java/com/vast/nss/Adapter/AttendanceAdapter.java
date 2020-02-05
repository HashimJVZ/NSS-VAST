package com.vast.nss.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseUser;
import com.vast.nss.AttendanceActivity;
import com.vast.nss.Model.Attendance;
import com.vast.nss.R;

import java.util.List;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.MyViewHolder> {

    private Context context;
    private List<Attendance> attendanceList;

    public AttendanceAdapter(Context context, List<Attendance> attendanceList){
        this.context = context;
        this.attendanceList = attendanceList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_attendance, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.attendanceTitle.setText(attendanceList.get(position).getTitle());
        holder.attendanceLocation.setText(attendanceList.get(position).getLocation());
        holder.getAttendanceDate.setText(attendanceList.get(position).getDate());
        final String key = attendanceList.get(position).getKey();

        holder.attendanceCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AttendanceActivity.class);
                intent.putExtra("key", key);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return attendanceList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView attendanceTitle;
        TextView attendanceLocation;
        TextView getAttendanceDate;
//        TextView categoryTextView;
//        TextView hoursTextView;
        CardView attendanceCardView;

        MyViewHolder(@NonNull View itemView){
            super(itemView);

            attendanceTitle = itemView.findViewById(R.id.attendanceTitle);
            attendanceLocation = itemView.findViewById(R.id.attendanceLocation);
            getAttendanceDate = itemView.findViewById(R.id.attendanceDate);
//            categoryTextView = itemView.findViewById(R.id.categoryTextView);
//            hoursTextView = itemView.findViewById(R.id.hoursTextView);
            attendanceCardView = itemView.findViewById(R.id.attendanceCardView);

        }
    }
}
