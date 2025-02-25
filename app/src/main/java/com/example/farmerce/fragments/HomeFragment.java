package com.example.farmerce.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.farmerce.R;
import com.example.farmerce.activities.SearchActivity;
import com.example.farmerce.adapters.HomeCategoryAdapter;
import com.example.farmerce.adapters.PopularProductAdapter;
import com.example.farmerce.adapters.RecommendedProductAdapter;
import com.example.farmerce.adapters.ViewAllAdapter;
import com.example.farmerce.databinding.ActivityMainBinding;
import com.example.farmerce.models.HomeCategoryModel;
import com.example.farmerce.models.PopularProductModel;
import com.example.farmerce.models.RecommendedProductModel;
import com.example.farmerce.models.ViewAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HomeFragment extends Fragment {

    ActivityMainBinding binding;
    ScrollView scrollView;
    ProgressBar progressBar;
    RecyclerView popularPro, homeExpCat, recommendedpro;
    FirebaseFirestore db;
    //Popular product
    List<PopularProductModel> popularProductModelList;
    PopularProductAdapter popularProductAdapter;
    //search view
    EditText search_box;
    private List<ViewAllModel> viewAllModelList;
    private RecyclerView recyclerViewSearch;
    private ViewAllAdapter viewAllAdapter;
    //Home Category (Explore cat)
    List<HomeCategoryModel> categoryList;
    HomeCategoryAdapter homeCategoryAdapter;
    //Recommended product
    List<RecommendedProductModel> recommendedProductModelList;
    RecommendedProductAdapter recommendedProductAdapter;
    TextView home_infoslider, view_all_cats;
    FloatingActionButton gotoCart;
    MaterialSearchBar searchBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        db = FirebaseFirestore.getInstance();

        ImageSlider imageSlider = root.findViewById(R.id.imageslider);
        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.slider1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.slider2, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.slider4, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.slider3, ScaleTypes.FIT));
        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        popularPro = root.findViewById(R.id.pop_pro);
        homeExpCat = root.findViewById(R.id.exp_cat);
        recommendedpro = root.findViewById(R.id.rec_pro);
        scrollView = root.findViewById(R.id.home_scroll_view);

        progressBar = root.findViewById(R.id.home_prg_bar);
        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);

        home_infoslider = root.findViewById(R.id.home_infoslider);
        home_infoslider.setSelected(true);
        home_infoslider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_nav_home_to_nav_Updates);
            }
        });

        view_all_cats = root.findViewById(R.id.allexplore);
        view_all_cats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_nav_home_to_nav_category);
            }
        });

        gotoCart = root.findViewById(R.id.go_to_cart);
        gotoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_nav_home_to_nav_Cart);
            }
        });

        //Search Bar
        searchBar = root.findViewById(R.id.searchBar);
        searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                intent.putExtra("query", text.toString());
                startActivity(intent);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

        //popular prodcuts

        //Home Category (Explore cat)
        homeExpCat.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        categoryList = new ArrayList<>();
        homeCategoryAdapter = new HomeCategoryAdapter(getActivity(), categoryList);
        homeExpCat.setAdapter(homeCategoryAdapter);

        db.collection("ExploreCategories")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){
                                HomeCategoryModel homeCategory = document.toObject(HomeCategoryModel.class);
                                categoryList.add(homeCategory);
                                homeCategoryAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                                scrollView.setVisibility(View.VISIBLE);
                            }
                        } else {
                            Toast.makeText(getActivity(), "Error "+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        //Recommended product
        recommendedpro.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        recommendedProductModelList = new ArrayList<>();
        recommendedProductAdapter = new RecommendedProductAdapter(getActivity(), recommendedProductModelList);
        recommendedpro.setAdapter(recommendedProductAdapter);

        db.collection("RecommendedCategories")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){
                                RecommendedProductModel recommendedProductModel = document.toObject(RecommendedProductModel.class);
                                recommendedProductModelList.add(recommendedProductModel);
                                recommendedProductAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                                scrollView.setVisibility(View.VISIBLE);
                            }
                        } else {
                            Toast.makeText(getActivity(), "Error "+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        //////////search view
        recyclerViewSearch = root.findViewById(R.id.search_rec);
        search_box = root.findViewById(R.id.search_box);
        viewAllModelList = new ArrayList<>();
        viewAllAdapter = new ViewAllAdapter(getContext(), viewAllModelList);
        recyclerViewSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewSearch.setAdapter(viewAllAdapter);
        recyclerViewSearch.setHasFixedSize(true);
        search_box.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()){
                    viewAllModelList.clear();
                    viewAllAdapter.notifyDataSetChanged();
                } else {
                    searchProduct(s.toString());
                }
            }
        });
        return root;
    }

    private void searchProduct(String type) {
        if(!type.isEmpty()) {

            db.collection("AllProducts").whereEqualTo("type",type).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful() && task.getResult() != null){
                                viewAllModelList.clear();
                                viewAllAdapter.notifyDataSetChanged();
                                for (DocumentSnapshot doc : task.getResult().getDocuments()){
                                    ViewAllModel viewAllModel = doc.toObject(ViewAllModel.class);
                                    viewAllModelList.add(viewAllModel);
                                    viewAllAdapter.notifyDataSetChanged();
                                }

                            }

                        }
                    });
        }
    }


}