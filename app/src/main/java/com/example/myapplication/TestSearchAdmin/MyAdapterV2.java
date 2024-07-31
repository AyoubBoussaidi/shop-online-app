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


public class MyAdapterV2 extends FirebaseRecyclerAdapter<Users,MyAdapterV2.myviewholder>
{
    public MyAdapterV2(@NonNull FirebaseRecyclerOptions<Users> options)
    {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, final int position, @NonNull final Users users)
    {
        holder.nomPrenom.setText(users.getNom_Prenom());
        holder.tele.setText(users.getTelephone());
        holder.Login.setText(users.getLogin());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus=DialogPlus.newDialog(holder.justimage.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialogcontent))
                        .setExpanded(true,1300)
                        .create();

                View myview=dialogPlus.getHolderView();
                final EditText Login=myview.findViewById(R.id.uemail);
                final EditText tele=myview.findViewById(R.id.uphone);
                final EditText nomprenom=myview.findViewById(R.id.uname);
                Button submit=myview.findViewById(R.id.usubmit);

                Login.setText(users.getLogin());
                tele.setText(users.getTelephone());
                nomprenom.setText(users.getNom_Prenom());

                dialogPlus.show();

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map=new HashMap<>();
                        map.put("Login",Login.getText().toString());
                        map.put("Telephone",tele.getText().toString());
                        map.put("Nom_Prenom",nomprenom.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("Users")
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


        holder.delete.setOnClickListener(new View.OnClickListener() {
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
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.client_list_item_in_admin,parent,false);
        return new myviewholder(view);
    }


    class myviewholder extends RecyclerView.ViewHolder
    {
        ImageView edit,delete,justimage;
        TextView Login,tele,nomPrenom;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            Login=(TextView)itemView.findViewById(R.id.tvfirstName);
            tele=(TextView)itemView.findViewById(R.id.tvlastName);
            nomPrenom=(TextView)itemView.findViewById(R.id.nomPrenom);
            justimage=itemView.findViewById(R.id.justimage);

            edit=(ImageView)itemView.findViewById(R.id.update_user_info_btn);
            delete=(ImageView)itemView.findViewById(R.id.remove_btn);
        }
    }
}