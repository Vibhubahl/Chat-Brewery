package com.example.baatcheet.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baatcheet.R;
import com.example.baatcheet.modelclass.UserModel;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private List<UserModel> data;
    private List<String> key;

    public UserAdapter(List<UserModel> data, List<String> key) {
        this.data = data;
        this.key = key;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(data.get(position).getName());
        holder.phone.setText(data.get(position).getPhone());
        holder.email.setText(data.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return key.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView phone;
        TextView email;
        ImageButton plus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textview_rc_name);
            phone = itemView.findViewById(R.id.textview_rc_phone);
            email = itemView.findViewById(R.id.textview_rc_email);
            plus = itemView.findViewById(R.id.add_friend_button);
        }
    }
}
