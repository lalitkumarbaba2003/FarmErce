package com.example.farmerce.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.farmerce.R;
import com.example.farmerce.models.PopularProductModel;
import com.example.farmerce.models.ViewAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.protobuf.StringValue;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailedActivity extends AppCompatActivity {

    FirebaseFirestore firestore;
    FirebaseAuth auth;
    TextView name, price, rating, description;
    TextView quantity;
    int totalQuantity = 1;
    int totalPrice = 0;
    String price1;
    int calprice;
    ImageView detailedImg, addItem, removeItem;
    Button addToCart;
    Toolbar toolbar;
    ViewAllModel viewAllModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        toolbar = findViewById(R.id.detailed_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        final Object object = getIntent().getSerializableExtra("detail");
        if (object instanceof ViewAllModel){
            viewAllModel = (ViewAllModel) object;
        }

        quantity = findViewById(R.id.detailed_quantity);
        detailedImg = findViewById(R.id.detailed_img);
        addItem = findViewById(R.id.detailed_add_item);
        removeItem = findViewById(R.id.detailed_remove_item);
        name = findViewById(R.id.detailed_pro_name);
        price = findViewById(R.id.detailed_price);
        rating = findViewById(R.id.detailed_rating);
        description = findViewById(R.id.detailed_desc);

        if (viewAllModel != null){
            Glide.with(getApplicationContext()).load(viewAllModel.getImg_url()).into(detailedImg);
            price.setText("Price : ₹"+viewAllModel.getPrice());
            rating.setText(viewAllModel.getRating());
            description.setText(viewAllModel.getDescription());
            name.setText(viewAllModel.getName());
            price1 = viewAllModel.getPrice();
            calprice = Integer.parseInt(price1);
            totalPrice = calprice * totalQuantity;
        }

        addToCart = findViewById(R.id.detailed_addtocartbtn);
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addedToCart();
            }
        });

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalQuantity < 10){
                    totalQuantity++;
                    quantity.setText(String.valueOf(totalQuantity));
                    totalPrice = calprice * totalQuantity;
                }
                if (totalQuantity == 10){
                    Toast.makeText(DetailedActivity.this, "Max quantity reached!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalQuantity > 0){
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));
                    totalPrice = calprice * totalQuantity;
                }
            }
        });
    }

    private void addedToCart() {
        String saveCurrentDate, saveCurrentTime;
        Calendar calForDT = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("dd MM, yyyy");
        saveCurrentDate = currentDate.format(calForDT.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a" );
        saveCurrentTime = currentTime.format(calForDT.getTime());

        final HashMap<String, Object> cartMap = new HashMap<>();

        cartMap.put("productName", viewAllModel.getName());
        cartMap.put("productQuantity",viewAllModel.getDescription());
        cartMap.put("productPrice", price.getText().toString());
        cartMap.put("currentDate", saveCurrentDate);
        cartMap.put("currentTime", saveCurrentTime);
        cartMap.put("totalQuantity", quantity.getText().toString());
        cartMap.put("totalPrice", totalPrice);

        firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("AddToCart").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(DetailedActivity.this, "Added To Cart", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

    }

    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}