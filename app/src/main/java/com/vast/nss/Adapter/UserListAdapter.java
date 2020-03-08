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
import java.util.Collection;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.MyViewHolder> implements Filterable {

    private Context context;
    private List<UserList> userList;
    private List<String> userListAll;

    Filter filter = new Filter() {
        //runs on background thread
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<String> filteredList = new ArrayList<>();

            if (constraint.toString().isEmpty()) {
                filteredList.addAll(userListAll);
            } else {
                for (String user : userListAll) {
                    if (user.toLowerCase().contains(constraint.toString())) {
                        filteredList.add(user);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        //runs on UI thread
        @Override
        protected void publishResults(CharSequence constraint, FilterResults filterResults) {
            userList.clear();
            userList.addAll((Collection<? extends UserList>) filterResults.values);
            notifyDataSetChanged();

        }
    };

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

    public UserListAdapter(Context context, List<UserList> userList) {
        this.context = context;
        this.userList = userList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView user_name;
        TextView user_id;
        CircleImageView photo;

        MyViewHolder(View view) {
            super(view);

            user_name = view.findViewById(R.id.user_name);
            user_id = view.findViewById(R.id.user_id);
            photo = view.findViewById(R.id.photo);
        }
    }
}
