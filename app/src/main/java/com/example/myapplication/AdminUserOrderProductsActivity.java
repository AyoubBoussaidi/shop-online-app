package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.Model.Panier;
import com.example.myapplication.Model.Products;
import com.example.myapplication.ViewHolder.CartViewHolder;
import com.example.myapplication.ViewHolder.OrdersViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminUserOrderProductsActivity extends AppCompatActivity {

    private RecyclerView productList;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference cartListRef;

    private String userID="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_order_products);

        userID=getIntent().getStringExtra("uid");

        productList=findViewById(R.id.product_list_in_orders);
        productList.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        productList.setLayoutManager(layoutManager);

        cartListRef= FirebaseDatabase.getInstance().getReference().child("Panier").child("Admin View").child(userID).child("Products");

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Panier> options=
                new FirebaseRecyclerOptions.Builder<Panier>()
                .setQuery(cartListRef,Panier.class).build();

        FirebaseRecyclerAdapter<Panier, OrdersViewHolder> adapter=new FirebaseRecyclerAdapter<Panier, OrdersViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull OrdersViewHolder holder, int position, @NonNull Panier model) {
                holder.txtProductName.setText(model.getPdesignation());
                holder.txtProductPrice.setText("Prix = "+model.getPrix());
                holder.txtProductquantity.setText("Quantite = "+model.getQuantite());


            }

            @NonNull
            @Override
            public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_in_order_items,parent,false);
                OrdersViewHolder holder=new OrdersViewHolder(view);
                return holder;
            }
        };

        productList.setAdapter(adapter);
        adapter.startListening();
    }
}