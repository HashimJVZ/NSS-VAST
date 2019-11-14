package com.vast.nss.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vast.nss.Model.UserList;
import com.vast.nss.R;

import java.util.ArrayList;
import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.MyViewHolder> implements Filterable {

    private Context context;
    private List<UserList> userList;
    private List<String> userListAll;

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
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        myViewHolder.user_name.setText(userList.get(position).getUser_name());
        myViewHolder.user_id.setText(userList.get(position).getUser_id());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<String> filteredList = new ArrayList<>();

            if(constraint.toString().isEmpty()){
                filteredList.addAll(userListAll);
            } else {
                for (String userList : userListAll)
                    if (userList.toLowerCase().contains());
            }
            return null;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

        }
    };

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView user_name;
        TextView user_id;

        MyViewHolder(View view) {
            super(view);

            user_name = view.findViewById(R.id.user_name);
            user_id = view.findViewById(R.id.user_id);
        }
    }
}
