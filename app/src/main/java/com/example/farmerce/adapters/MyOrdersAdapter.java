package com.example.farmerce.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.farmerce.R;
import com.example.farmerce.models.MyCartModel;
import com.example.farmerce.models.MyOrdersModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MyOrdersAdapter extends RecyclerView.Adapter<MyOrdersAdapter.ViewHolder> {

    Context context;
    List<MyOrdersModel> myOrdersModelList;
    FirebaseFirestore firestore;
    FirebaseAuth auth;

    public MyOrdersAdapter(Context context, List<MyOrdersModel> myOrdersModelList) {
        this.context = context;
        this.myOrdersModelList = myOrdersModelList;
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_order_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(myOrdersModelList.get(position).getProductName());
        holder.pro_quantity.setText(myOrdersModelList.get(position).getProductQuantity());
        holder.price.setText(myOrdersModelList.get(position).getProductPrice());
        holder.quantity.setText(myOrdersModelList.get(position).getTotalQuantity());
        holder.totalPrice.setText(String.valueOf(myOrdersModelList.get(position).getTotalPrice()));
        holder.status.setText(myOrdersModelList.get(position).getProductStatus());
        Glide.with(context).load(myOrdersModelList.get(position).getImg_url()).into(holder.pro_image);
    }

    @Override
    public int getItemCount() {
        return myOrdersModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name,pro_quantity, price, quantity, totalPrice, status ;
        ImageView pro_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.ordered_pro_name);
            pro_quantity = itemView.findViewById(R.id.ordered_pro_desc);
            price = itemView.findViewById(R.id.ordered_pro_price);
            quantity = itemView.findViewById(R.id.ordered_total_quantity);
            totalPrice = itemView.findViewById(R.id.ordered_pro_total_price);
            status = itemView.findViewById(R.id.ordered_pro_status);
            pro_image = itemView.findViewById(R.id.ordered_pro_img);
        }
    }
}
