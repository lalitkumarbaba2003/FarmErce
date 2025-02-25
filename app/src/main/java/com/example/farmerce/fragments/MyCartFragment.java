package com.example.farmerce.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.farmerce.R;
import com.example.farmerce.activities.PlacedOrderActivity;
import com.example.farmerce.adapters.MyCartAdapter;
import com.example.farmerce.models.MyCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MyCartFragment extends Fragment {

    FirebaseFirestore db;
    FirebaseAuth auth;
    TextView overTotalAmount;
    RecyclerView recyclerView;
    MyCartAdapter mycartAdapter;
    List<MyCartModel> myCartModelList;
    ProgressBar progressBar;
    Button buynow;
    FirebaseFirestore firestore;
    int totalBill;

    public MyCartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_my_cart, container, false);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        progressBar = root.findViewById(R.id.mycart_prgbar);
        progressBar.setVisibility(View.VISIBLE);

        buynow = root.findViewById(R.id.cart_buynowbtn);
        recyclerView = root.findViewById(R.id.cart_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setVisibility(View.GONE);

        overTotalAmount = root.findViewById(R.id.cart_total_price);

        myCartModelList = new ArrayList<>();
        mycartAdapter = new MyCartAdapter(getActivity(), myCartModelList);
        recyclerView.setAdapter(mycartAdapter);

        firestore = FirebaseFirestore.getInstance();

        db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("AddToCart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){
                                String documentId = documentSnapshot.getId();
                                MyCartModel myCartModel = documentSnapshot.toObject(MyCartModel.class);
                                myCartModel.setDocumentId(documentId);
                                myCartModelList.add(myCartModel);
                                mycartAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                            }
                            calculateTotalAmount(myCartModelList);
                        }
                    }
                });

        buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PlacedOrderActivity.class);
                intent.putExtra("itemList", (Serializable) myCartModelList);
                startActivity(intent);
            }
        });

        return root;
    }

    private void calculateTotalAmount(List<MyCartModel> myCartModelList) {
        double totalAmount = 0.0;
        for (MyCartModel myCartModel : myCartModelList){
            totalAmount += myCartModel.getTotalPrice();
        }
        overTotalAmount.setText("Total Amount :" + totalAmount);
    }
}