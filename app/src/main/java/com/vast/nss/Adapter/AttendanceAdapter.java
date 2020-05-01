package com.vast.nss.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
        final String dbEventKey = attendanceList.get(position).getKey();
        final String category = attendanceList.get(position).getCategory();
        final long hours = attendanceList.get(position).getHours();


        if (isAdmin()) {
            holder.attendanceCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, AttendanceActivity.class);
                    Log.d("hashim", "dbEventKey= " + dbEventKey + "\ncategory=" + category + "\nhours=" + hours);
                    intent.putExtra("dbEventKey", dbEventKey);
                    context.startActivity(intent);
                }
            });
        } else {
            Toast.makeText(context, "You are not an Admin", Toast.LENGTH_SHORT).show();
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
