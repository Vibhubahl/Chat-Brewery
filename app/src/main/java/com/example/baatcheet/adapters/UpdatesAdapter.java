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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdatesAdapter extends RecyclerView.Adapter<UpdatesAdapter.ViewHolder> {

    List<String> keys;
    FirebaseFirestore ref = FirebaseFirestore.getInstance();
    public UpdatesAdapter(List<String> keys) {
        this.keys = keys;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_item_updates,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ref.collection("Users").document(keys.get(position))
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    UserModel model = task.getResult().toObject(UserModel.class);
                    holder.name.setText(model.getName());
                    holder.phone.setText(model.getPhone());
                    holder.email.setText(model.getEmail());
                    holder.accept.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Map<String,Object> friend = new HashMap<>();
                            friend.put(keys.get(position),keys.get(position));
                            ref.collection("Friends").document(FirebaseAuth.getInstance().getUid()).set(friend, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                }
                            });
                            ref.collection("Friend Requests").document("Requested")
                                    .collection(FirebaseAuth.getInstance().getUid()).document(keys.get(position))
                                    .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                }
                            });
                        }
                    });
                    holder.reject.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ref.collection("Friend Requests").document("Requested")
                                    .collection(FirebaseAuth.getInstance().getUid()).document(keys.get(position))
                                    .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                }
                            });
                        }
                    });
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return keys.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView phone;
        TextView email;
        ImageButton accept,reject;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textview_rc_name);
            phone = itemView.findViewById(R.id.textview_rc_phone);
            email = itemView.findViewById(R.id.textview_rc_email);
            accept = itemView.findViewById(R.id.acceptReq);
            reject = itemView.findViewById(R.id.rejectReq);
        }
    }
}
