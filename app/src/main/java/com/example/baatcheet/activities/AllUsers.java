package com.example.baatcheet.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.example.baatcheet.R;
import com.example.baatcheet.adapters.UserAdapter;
import com.example.baatcheet.modelclass.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AllUsers extends AppCompatActivity {

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);
        context = getApplicationContext();

        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        FirebaseFirestore.getInstance().collection("Users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    List<String> keys = new ArrayList<>();
                    List<UserModel> data = new ArrayList<>();
                    for (QueryDocumentSnapshot snapshot : task.getResult()){
                        UserModel model = snapshot.toObject(UserModel.class);
                        String key = snapshot.getId();
                        if(!FirebaseAuth.getInstance().getCurrentUser().getUid().equals(key)){
                            data.add(model);
                            keys.add(key);
                        }
                    }
                    UserAdapter adapter = new UserAdapter(data, keys, context);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setHasFixedSize(true);
                }
            }
        });

//        FirebaseDatabase.getInstance().getReference("users").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.hasChildren() && snapshot.exists()) {
//                    List<String> key = new ArrayList<>();
//                    List<UserModel> data = new ArrayList<>();
//                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
//                        UserModel model = snapshot1.getValue(UserModel.class);
//                        String s = snapshot1.getKey();
//                        if(!FirebaseAuth.getInstance().getCurrentUser().getUid().equals(s)){
//                            data.add(model);
//                            key.add(s);
//                        }
//                    }
//
//                    UserAdapter adapter = new UserAdapter(data, key);
//                    recyclerView.setAdapter(adapter);
//                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//                    recyclerView.setHasFixedSize(true);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(AllUsers.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}