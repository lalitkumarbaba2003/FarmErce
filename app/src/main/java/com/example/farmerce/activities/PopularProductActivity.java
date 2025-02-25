package com.example.farmerce.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmerce.R;
import com.example.farmerce.adapters.PopularProductAdapter;
import com.example.farmerce.models.PopularProductModel;
import com.example.farmerce.models.ViewAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class PopularProductActivity extends AppCompatActivity {

    FirebaseFirestore firestore;
    RecyclerView recyclerView;
    PopularProductAdapter popularProductAdapter;
    List<PopularProductModel> popularProductModelList;
    ProgressBar progressBar;
    Toolbar toolbar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        toolbar = findViewById(R.id.detailed_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firestore = FirebaseFirestore.getInstance();
        String name = getIntent().getStringExtra("name");
        popularProductModelList = new ArrayList<>();
        popularProductAdapter = new PopularProductAdapter(this, popularProductModelList);

        progressBar = findViewById(R.id.detailed_prgbar);
        progressBar.setVisibility(View.VISIBLE);

        if(name != null  && name.equalsIgnoreCase("Onion")) {
            firestore.collection("PopularProducts").whereEqualTo("name","Onion").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){
                        PopularProductModel popularProductModel = documentSnapshot.toObject(PopularProductModel.class);
                        popularProductModelList.add(popularProductModel);
                        popularProductAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                    }
                }
            });
        }

        if(name != null  && name.equalsIgnoreCase("Potato")) {
            firestore.collection("PopularProducts").whereEqualTo("name","Potato").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){
                        PopularProductModel popularProductModel = documentSnapshot.toObject(PopularProductModel.class);
                        popularProductModelList.add(popularProductModel);
                        popularProductAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                    }
                }
            });
        }

        if(name != null  && name.equalsIgnoreCase("Sweetcorn")) {
            firestore.collection("PopularProducts").whereEqualTo("name","Sweetcorn").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){
                        PopularProductModel popularProductModel = documentSnapshot.toObject(PopularProductModel.class);
                        popularProductModelList.add(popularProductModel);
                        popularProductAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                    }
                }
            });
        }

        if(name != null  && name.equalsIgnoreCase("Tomato")) {
            firestore.collection("PopularProducts").whereEqualTo("name","Tomato").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){
                        PopularProductModel popularProductModel = documentSnapshot.toObject(PopularProductModel.class);
                        popularProductModelList.add(popularProductModel);
                        popularProductAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                    }
                }
            });
        }

    }
}
