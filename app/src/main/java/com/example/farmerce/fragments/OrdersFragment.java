package com.example.farmerce.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.farmerce.R;
import com.example.farmerce.adapters.MyOrdersAdapter;
import com.example.farmerce.models.MyOrdersModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class OrdersFragment extends Fragment {

    FirebaseFirestore db;
    FirebaseAuth auth;
    RecyclerView recyclerView;
    MyOrdersAdapter myOrdersAdapter;
    List<MyOrdersModel> myOrdersModelList;
    ProgressBar progressBar;
    int totalBill;

    public OrdersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_my_orders, container, false);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        progressBar = root.findViewById(R.id.orders_prgbar);
        progressBar.setVisibility(View.VISIBLE);

        recyclerView = root.findViewById(R.id.orders_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setVisibility(View.GONE);

        myOrdersModelList = new ArrayList<>();
        myOrdersAdapter = new MyOrdersAdapter(getActivity(), myOrdersModelList);
        recyclerView.setAdapter(myOrdersAdapter);

        db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("MyOrder").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){
                                String documentId = documentSnapshot.getId();
                                MyOrdersModel myOrdersModel = documentSnapshot.toObject(MyOrdersModel.class);
                                myOrdersModel.setDocumentId(documentId);
                                myOrdersModelList.add(myOrdersModel);
                                myOrdersAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });

        return root;
    }
}