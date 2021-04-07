package com.example.baatcheet.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.baatcheet.R;
import com.example.baatcheet.adapters.UpdatesAdapter;
import com.example.baatcheet.adapters.UserAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Updates extends Fragment {

    RecyclerView updatesRecyclerView;
    public Updates() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_updates, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updatesRecyclerView = view.findViewById(R.id.updatesRecyclerView);
        FirebaseFirestore.getInstance().collection("Friend Requests")
                .document("Requested").collection(FirebaseAuth.getInstance().getUid())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    List<String> keys = new ArrayList<>();
                    for(QueryDocumentSnapshot snapshot : task.getResult()){
                        if(snapshot.exists()){
                            keys.add(snapshot.getId());
                        }
                    }
                    UpdatesAdapter adapter = new UpdatesAdapter(keys);
                    updatesRecyclerView.setAdapter(adapter);
                    updatesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                    updatesRecyclerView.setHasFixedSize(true);
                }
            }
        });
    }
}