package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Model.Products;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public  class MyAdapterProduct extends RecyclerView.Adapter<MyAdapterProduct.MyViewHolder> {

    Context context;

    ArrayList<Products> list;


    public MyAdapterProduct(Context context, ArrayList<Products> list) {
        this.context = context;
        this.list = list;
    }
    public MyAdapterProduct( ArrayList<Products> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.produit_list_item,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Products products = list.get(position);
        Glide.with(context).load(products.getImage()).into(holder.image);
        holder.designation.setText(products.getDesignation());
        holder.description.setText(products.getDescription());
        holder.prix.setText(products.getPrix()+"DH");
        holder.quantite.setText(products.getQuantite());
        //added code

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("Suppression");
                builder.setMessage("vous voulez supprimer ce produit...?");

                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("Produits")
                                .child(products.getPid()).removeValue();
                    }
                });

                builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.show();
            }
        });



        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus=DialogPlus.newDialog(context)
                        .setContentHolder(new ViewHolder(R.layout.dialogcontentproduct))
                        .setExpanded(true,1400)
                        .create();

                View myview=dialogPlus.getHolderView();

                final EditText name=myview.findViewById(R.id.pdesignation);
                final EditText course=myview.findViewById(R.id.pdescription);
                final EditText prix=myview.findViewById(R.id.pprix);
                final EditText quantite=myview.findViewById(R.id.pquantite);
                Button submit=myview.findViewById(R.id.usubmitproduct);

                name.setText(products.getDesignation());
                course.setText(products.getDescription());
                prix.setText(products.getPrix()+" DH");
                quantite.setText(products.getQuantite());

                dialogPlus.show();

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map=new HashMap<>();
                        map.put("Designation",name.getText().toString());
                        map.put("Description",course.getText().toString());
                        map.put("Prix",prix.getText().toString());
                        map.put("Quantite",quantite.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("Products")
                                .child(products.getPid()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });


            }
        });

        //end of it


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView designation, description, prix,quantite,showDescription,maskDescription;
        ImageView image,remove,edit;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image_p);
            designation = itemView.findViewById(R.id.designation);
            description = itemView.findViewById(R.id.description);
            prix = itemView.findViewById(R.id.prix);
            quantite = itemView.findViewById(R.id.quantite);
            showDescription=itemView.findViewById(R.id.show_description);
            maskDescription=itemView.findViewById(R.id.mask_description);
            remove=itemView.findViewById(R.id.remove_btn_product_admin);
            edit=itemView.findViewById(R.id.update_product_info_btn);

            showDescription.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    description.setVisibility(View.VISIBLE);
                    maskDescription.setVisibility(View.VISIBLE);
                    showDescription.setVisibility(View.INVISIBLE);

                }
            });
            maskDescription.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    description.setVisibility(View.INVISIBLE);
                    maskDescription.setVisibility(View.INVISIBLE);
                    showDescription.setVisibility(View.VISIBLE);
                }
            });
        }
    }

}