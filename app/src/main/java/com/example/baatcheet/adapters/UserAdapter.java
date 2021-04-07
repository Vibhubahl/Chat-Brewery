package com.example.baatcheet.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baatcheet.R;
import com.example.baatcheet.modelclass.UserModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private List<UserModel> data;
    private List<String> key;
    private Context context;

    public UserAdapter(List<UserModel> data, List<String> key, Context context) {
        this.data = data;
        this.key = key;
        this.context = context;
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
        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> req = new HashMap<>();
                req.put("Time", Calendar.getInstance().getTime());
                FirebaseFirestore.getInstance().collection("Friend Requests")
                        .document("Send").collection(FirebaseAuth.getInstance().getUid()).document(key.get(position))
                        .set(req).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "Friend Request Send", Toast.LENGTH_SHORT).show();
                    }
                });
                req.put("Time", Calendar.getInstance().getTime());
                FirebaseFirestore.getInstance().collection("Friend Requests")
                        .document("Requested").collection(key.get(position)).document(FirebaseAuth.getInstance().getUid())
                        .set(req).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                });
            }
        });
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
