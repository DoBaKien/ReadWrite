package com.example.readobjectfb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{

    private ArrayList<User> mListUser;
    private Context ctx;
    private LayoutInflater layoutInflater;


    public UserAdapter(ArrayList<User> mListUser, Context ctx) {
        this.mListUser = mListUser;
        layoutInflater = LayoutInflater.from(ctx) ;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = layoutInflater.inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = mListUser.get(position);
        holder.tvName.setText(user.getName());
        holder.tvId.setText(String.valueOf(user.getId()));

    }

    @Override
    public int getItemCount() {
        return mListUser.size();
    }


    public class UserViewHolder extends RecyclerView.ViewHolder{

        TextView tvName,tvId;
        UserAdapter userAdapter;

        public UserViewHolder(@NonNull View itemView, UserAdapter adapter) {
            super(itemView);
            tvName= itemView.findViewById(R.id.tvname);
            tvId= itemView.findViewById(R.id.tvid);

            this.userAdapter = adapter;
        }
    }
}
