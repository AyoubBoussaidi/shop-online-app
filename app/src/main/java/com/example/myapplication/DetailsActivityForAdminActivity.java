package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;

import android.widget.ImageView;
import android.widget.TextView;
import com.example.myapplication.Model.Products;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.squareup.picasso.Picasso;

public class DetailsActivityForAdminActivity extends AppCompatActivity {


    private ImageView productImage;
    private TextView productPrice,productDescription,productName;
    private String productId="",state="Normale";
    private int total=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_for_admin);

        productId=getIntent().getStringExtra("Pid");
        productImage=findViewById(R.id.product_image_details_for_admin);
        productPrice=findViewById(R.id.product_price_details_for_admin);
        productDescription=findViewById(R.id.product_description_details_for_admin);
        productName=findViewById(R.id.product_name_details_for_admin);

        getProductDetails(productId);


    }

    @Override
    protected void onStart() {
        super.onStart();

    }



    private void getProductDetails(String productId) {

        DatabaseReference productRef= FirebaseDatabase.getInstance().getReference().child("Products");

        productRef.child(productId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    Products product=snapshot.getValue(Products.class);
                    productName.setText(product.getDesignation());
                    productDescription.setText(product.getDescription());
                    productPrice.setText(product.getPrix()+"DH");
                    Picasso.get().load(product.getImage()).into(productImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}