package com.example.myapplication.TestSearchAdmin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Model.Products;
import com.example.myapplication.Model.Users;
import com.example.myapplication.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;


public class MyAdapterProductV3 extends FirebaseRecyclerAdapter<Products,MyAdapterProductV3.myviewholder>
{
    public MyAdapterProductV3(@NonNull FirebaseRecyclerOptions<Products> options)
    {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, final int position ,final Products products)
    {
        Glide.with(holder.image.getContext()).load(products.getImage()).into(holder.image);
        holder.designation.setText(products.getDesignation());
        holder.description.setText(products.getDescription());
        holder.prix.setText(products.getPrix()+"DH");
        holder.quantite.setText(products.getQuantite());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus=DialogPlus.newDialog(holder.justimage.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialogcontentproduct))
                        .setExpanded(true,1300)
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
                                .child(getRef(position).getKey()).updateChildren(map)
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


        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.justimage.getContext());
                builder.setTitle("Suppression");
                builder.setMessage("Supprimer...?");

                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("Users")
                                .child(getRef(position).getKey()).removeValue();
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

    } // End of OnBindViewMethod

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.produit_list_item,parent,false);
        return new myviewholder(view);
    }


    class myviewholder extends RecyclerView.ViewHolder
    {
        TextView designation, description, prix,quantite,showDescription,maskDescription;
        ImageView image,remove,edit,justimage;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            justimage=itemView.findViewById(R.id.justImageP);
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
