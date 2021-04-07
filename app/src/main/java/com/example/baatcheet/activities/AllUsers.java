package com.example.baatcheet.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.baatcheet.R;
import com.example.baatcheet.adapters.UserAdapter;
import com.example.baatcheet.modelclass.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class AllUsers extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        FirebaseFirestore.getInstance().collection("users").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<String> keys = new ArrayList<>();
                List<UserModel> data = new ArrayList<>();
                for (QueryDocumentSnapshot snapshot1 : task.getResult()) {
                    UserModel model = snapshot1.toObject(UserModel.class);
                    String s = snapshot1.getId();
                    if (!FirebaseAuth.getInstance().getCurrentUser().getUid().equals(s)) {
                        data.add(model);
                        keys.add(s);
                    }
                }

                UserAdapter adapter = new UserAdapter(data, keys);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setHasFixedSize(true);
            } else if (task.isCanceled()) {
                Toast.makeText(AllUsers.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}