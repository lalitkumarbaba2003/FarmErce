package com.example.farmerce.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.farmerce.adapters.ViewAllAdapter;
import com.example.farmerce.databinding.ActivitySearchBinding;
import com.example.farmerce.models.ViewAllModel;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    ActivitySearchBinding binding;
    ViewAllAdapter viewAllAdapter;
    ArrayList<ViewAllModel> list;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        list = new ArrayList<>();
        viewAllAdapter = new ViewAllAdapter(this, list);

        db = FirebaseFirestore.getInstance();


        String query = getIntent().getStringExtra("query");

        getSupportActionBar().setTitle(query);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getProducts(query);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        binding.searchproductList.setLayoutManager(layoutManager);
        binding.searchproductList.setAdapter(viewAllAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    void getProducts(String query) {
        RequestQueue queue = Volley.newRequestQueue(this);

        CollectionReference cons = db.collection("AllProducts");
        String url = cons + "?q=" + query;

        StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
            try {
                JSONObject object = new JSONObject(response);
                if(object.getString("status").equals("success")){
                    JSONArray productsArray = object.getJSONArray("list");
                    for(int i =0; i< productsArray.length(); i++) {
                        JSONObject childObj = productsArray.getJSONObject(i);
                        ViewAllModel product = new ViewAllModel(
                                childObj.getString("name"),
                                childObj.getString("description"),
                                childObj.getString("rating"),
                                childObj.getString("price"),
                                childObj.getString("img_url"),
                                childObj.getString("type")
                        );
                        list.add(product);
                    }
                    viewAllAdapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> { });

        queue.add(request);
    }

}
