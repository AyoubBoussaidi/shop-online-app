package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.myapplication.Model.Products;
import com.example.myapplication.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class SearchByCategorieActivityActivity extends AppCompatActivity {

    private DatabaseReference ProductsRef;
    private ImageView see_categorie;
    private Button searchBtn;
    private EditText inputSearchText;
    private RecyclerView searchList;
    RecyclerView.LayoutManager layoutManager;
    private String SearchInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_categorie_activity);


        see_categorie=findViewById(R.id.info_see_categorie);
        searchBtn = findViewById(R.id.serching_btn_categorie);
        inputSearchText = (EditText)findViewById(R.id.serch_product_name_with_categ);
        searchList = findViewById(R.id.searching_list_categorie);
        searchList = findViewById(R.id.searching_list_categorie);
        searchList.setHasFixedSize(true);
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");
        layoutManager = new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(layoutManager);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        searchList.setLayoutManager(layoutManager);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchInput = inputSearchText.getText().toString();
                onStart();
            }
        });

        see_categorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SearchByCategorieActivityActivity.this,ClientCategorieActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        SearchInput = inputSearchText.getText().toString();
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");
        FirebaseRecyclerOptions<Products> options =
                new FirebaseRecyclerOptions.Builder<Products>()
                        .setQuery(ProductsRef.orderByChild("Categorie").startAt(SearchInput).endAt(SearchInput+"\uf8ff"), Products.class)
                        .build();


        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Products model)
                    {
                        holder.txtProductName.setText(model.getDesignation());
                        holder.txtProductDescription.setText(model.getDescription());
                        holder.txtProductPrice.setText( model.getPrix() + "DH");
                        Picasso.get().load(model.getImage()).into(holder.imageView);

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent=new Intent(SearchByCategorieActivityActivity.this,ProductDetailsActivity.class);
                                intent.putExtra("Pid",model.getPid());
                                startActivity(intent);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_layout, parent, false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;
                    }
                };
        searchList.setAdapter(adapter);
        adapter.startListening();
    }
}