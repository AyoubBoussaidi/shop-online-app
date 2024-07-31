package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.myapplication.Model.Products;
import com.example.myapplication.Prevalant.Prevalant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import com.rey.material.widget.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ProductDetailsActivity extends AppCompatActivity {

    private FloatingActionButton addToCartBtn;
    private ImageView productImage;
    private ElegantNumberButton numberButton;
    private TextView productPrice,productDescription,productName;
    private String productId="",state="Normale";
    private int total=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        productId=getIntent().getStringExtra("Pid");
        addToCartBtn=findViewById(R.id.pd_add_to_cart_btn);
        productImage=findViewById(R.id.product_image_details);
        numberButton=findViewById(R.id.number_btn);
        productPrice=findViewById(R.id.product_price_details);
        productDescription=findViewById(R.id.product_description_details);
        productName=findViewById(R.id.product_name_details);

        getProductDetails(productId);

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                addingToCartList();

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        /*chekOrderState();
        if(state.equals("Order Placed") || state.equals("Order Shipped")){
            Toast.makeText(ProductDetailsActivity.this,"you can add Purchase  more products , once your order is shipped or confirmed",Toast.LENGTH_LONG).show();
        }else {
            addingToCartList();
        }*/
    }

    private void addingToCartList()
    {
        String saveCurrentTime,saveCurrentDate;
        Calendar calForDate=Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate=currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss");
        saveCurrentTime=currentDate.format(calForDate.getTime());

        final DatabaseReference cartListRef=FirebaseDatabase.getInstance().getReference().child("Panier");

        final HashMap<String,Object> cartMap=new HashMap<>();
        cartMap.put("Pid",productId);
        cartMap.put("Pdesignation",productName.getText().toString());
        cartMap.put("Prix",productPrice.getText().toString());
        cartMap.put("Date",saveCurrentDate);
        cartMap.put("Temps",saveCurrentTime);
        cartMap.put("Quantite",numberButton.getNumber());
        cartMap.put("Remise", "");

        cartListRef.child("User View")
                .child(Prevalant.OnlineUser.getTelephone()).child("Products")
                .child(productId).updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    cartListRef.child("Admin View")
                            .child(Prevalant.OnlineUser.getTelephone()).child("Products")
                            .child(productId).updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(ProductDetailsActivity.this, "Ajouter Au Panier", Toast.LENGTH_SHORT).show();

                                Intent intent=new Intent(ProductDetailsActivity.this, HomeActivity.class);
                                startActivity(intent);

                            }
                        }
                    });
                }
            }
        });

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
                    productPrice.setText(product.getPrix());
                    Picasso.get().load(product.getImage()).into(productImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /*private void chekOrderState(){
        DatabaseReference  orderRef;
        orderRef=FirebaseDatabase.getInstance().
                getReference().child("AdminOrders").child(Prevalant.OnlineUser.getTelephone());

        orderRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String shippingState=snapshot.child("Etat").getValue().toString();

                    if(shippingState.equals("livre")){

                       state="Order Shipped";

                    }else if(shippingState.equals("pas livre")){
                        state="Order Placed";
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }*/
}