package com.vast.nss.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.vast.nss.AttendanceActivity;
import com.vast.nss.Model.Attendance;
import com.vast.nss.R;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.MyViewHolder> {

    private Context context;
    private List<Attendance> attendanceList;

    public AttendanceAdapter(Context context, List<Attendance> attendanceList) {
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

        if (isAdmin()) {
            holder.attendanceCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, AttendanceActivity.class);
                    Log.d("key", "key= " + key);
                    intent.putExtra("key", key);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return attendanceList.size();
    }

    private boolean isAdmin() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("SharedPref", MODE_PRIVATE);
        return sharedPreferences.getBoolean("isAdmin", false);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView attendanceTitle;
        TextView attendanceLocation;
        TextView getAttendanceDate;
        CardView attendanceCardView;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            attendanceTitle = itemView.findViewById(R.id.attendanceTitle);
            attendanceLocation = itemView.findViewById(R.id.attendanceLocation);
            getAttendanceDate = itemView.findViewById(R.id.attendanceDate);
            attendanceCardView = itemView.findViewById(R.id.attendanceCardView);

        }
    }
}
