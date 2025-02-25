package com.example.farmerce.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.farmerce.MainActivity;
import com.example.farmerce.R;
import com.example.farmerce.fragments.OrdersFragment;
import com.example.farmerce.models.MyCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlacedOrderActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseFirestore firestore;
    TextView gotoHome, gotoOrders;
    ImageView bb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placed_order);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        bb = findViewById(R.id.po_back_btn);
        bb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlacedOrderActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        gotoHome = findViewById(R.id.go_to_home);
        gotoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlacedOrderActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        gotoOrders = findViewById(R.id.go_to_orders);
        gotoOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlacedOrderActivity.this, OrdersFragment.class);
                startActivity(intent);
                finish();
            }
        });

        List<MyCartModel> list = (ArrayList<MyCartModel>) getIntent().getSerializableExtra("itemList");

        if(list != null && list.size() > 0) {
            for (MyCartModel model : list){

                final HashMap<String, Object> cartMap = new HashMap<>();
                cartMap.put("productName", model.getProductName());
                cartMap.put("productQuantity", model.getProductQuantity());
                cartMap.put("productPrice", model.getProductPrice());
                cartMap.put("currentDate", model.getCurrentDate());
                cartMap.put("currentTime", model.getCurrentTime());
                cartMap.put("totalQuantity", model.getTotalQuantity());
                cartMap.put("totalPrice", model.getTotalPrice());
                cartMap.put("productStatus", model.setProductStatus("Ordered"));

                firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                        .collection("MyOrder").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                Toast.makeText(PlacedOrderActivity.this, "Order Placed", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }

    }
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}