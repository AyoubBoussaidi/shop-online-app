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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.Users;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;

    ArrayList<Users> list;


    public MyAdapter(Context context, ArrayList<Users> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.client_list_item_in_admin,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Users user = list.get(position);
        holder.tele.setText(user.getLogin());
        holder.login.setText(user.getTelephone());


        //added code

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("Suppression");
                builder.setMessage("vous voulez supprimer ce client...?");

                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("Users")
                                .child(user.getTelephone()).removeValue();
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
                        .setContentHolder(new ViewHolder(R.layout.dialogcontent))
                        .setExpanded(true,1300)
                        .create();

                View myview=dialogPlus.getHolderView();

                final EditText name=myview.findViewById(R.id.uname);
                final EditText course=myview.findViewById(R.id.ucourse);
                final EditText email=myview.findViewById(R.id.uemail);
                final  EditText phone=myview.findViewById(R.id.uphone);
                Button submit=myview.findViewById(R.id.usubmit);

                name.setText(user.getNom_Prenom());
                course.setText(user.getPassword());
                email.setText(user.getLogin());
                phone.setText(user.getTelephone());

                dialogPlus.show();

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map=new HashMap<>();
                        map.put("Nom_Prenom",name.getText().toString());
                        map.put("Login",email.getText().toString());
                        map.put("Password",course.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("Users")
                                .child(user.getTelephone()).updateChildren(map)
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

        TextView tele, login;
        ImageView remove,edit;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tele = itemView.findViewById(R.id.tvfirstName);
            login = itemView.findViewById(R.id.tvlastName);
            remove=itemView.findViewById(R.id.remove_btn);
            edit=itemView.findViewById(R.id.update_user_info_btn);

        }
    }

}