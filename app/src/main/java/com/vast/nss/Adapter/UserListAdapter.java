package com.vast.nss.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vast.nss.Model.UserList;
import com.vast.nss.R;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.MyViewHolder> {

    private Context context;
    private List<UserList> userList;

    public UserListAdapter(Context context, List<UserList> userList){
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_user, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView user_name;
        TextView user_id;

        public MyViewHolder(View view) {
            super(view);

            user_name = view.findViewById(R.id.user_name);
            user_id = view.findViewById(R.id.user_id);

        }
    }
}
