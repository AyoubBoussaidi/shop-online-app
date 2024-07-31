package com.example.myapplication.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Interface.ItemClickListner;
import com.example.myapplication.R;

public class SearchProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
    public TextView txtProductName, txtProductDescription, txtProductPrice;
    public ImageView imageView;
    public ItemClickListner listner;
    public SearchProductViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView=(ImageView) itemView.findViewById(R.id.product_image_in_search);
        txtProductName=(TextView) itemView.findViewById(R.id.product_name_in_search);
        txtProductDescription=(TextView) itemView.findViewById(R.id.product_description_in_search);
        txtProductPrice=(TextView) itemView.findViewById(R.id.product_price_in_search);
    }
    public void setItemClickListner(ItemClickListner listner){
        this.listner=listner;
    }

    @Override
    public void onClick(View v) {
        listner.onClick(v,getAdapterPosition(),false);
    }
}