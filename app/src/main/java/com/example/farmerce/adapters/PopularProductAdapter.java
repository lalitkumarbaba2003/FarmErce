package com.example.farmerce.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.farmerce.R;
import com.example.farmerce.activities.DetailedActivity;
import com.example.farmerce.activities.PopularProductActivity;
import com.example.farmerce.activities.ViewAllActivity;
import com.example.farmerce.models.PopularProductModel;

import java.util.List;

public class PopularProductAdapter extends RecyclerView.Adapter<PopularProductAdapter.ViewHolder> {

    Context context;
    List<PopularProductModel> popularProductModelList;

    public PopularProductAdapter(Context context, List<PopularProductModel> popularProductModelList) {
        this.context = context;
        this.popularProductModelList = popularProductModelList;
    }

    @NonNull
    @Override
    public PopularProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_product, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PopularProductAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(popularProductModelList.get(position).getImg_url()).into(holder.popimg);
        holder.name.setText(popularProductModelList.get(position).getName());
        holder.description.setText(popularProductModelList.get(position).getDescription());
        holder.rating.setText(popularProductModelList.get(position).getRating());
        holder.discount.setText(popularProductModelList.get(position).getDiscount());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PopularProductActivity.class);
                intent.putExtra("name", popularProductModelList.get(position).getName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return popularProductModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView popimg;
        TextView name,description,rating,discount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            popimg = itemView.findViewById(R.id.pop_img);
            name = itemView.findViewById(R.id.pop_name);
            description = itemView.findViewById(R.id.pop_des);
            rating = itemView.findViewById(R.id.pop_rating);
            discount = itemView.findViewById(R.id.pop_discount);
        }
    }
}
