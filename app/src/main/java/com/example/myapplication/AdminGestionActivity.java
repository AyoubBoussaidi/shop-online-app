package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Model.AdminOrders;

//import com.example.myapplication.databinding.ActivityUpdateClientBinding;


public class AdminGestionActivity extends AppCompatActivity {
     CardView gestionClient,gestionProduits,gestionCommandes,gestionaboutus;
     ImageView lougout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_gestion);
        gestionClient=findViewById(R.id.gestion_clients);
        gestionProduits=findViewById(R.id.gestion_produits);
        gestionCommandes=findViewById(R.id.gestion_commandes);
        gestionaboutus=findViewById(R.id.aboutUs);
        lougout=findViewById(R.id.admin_logout);

        gestionClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminGestionActivity.this, AdminGestionClientActivity.class);
                startActivity(intent);
            }
        });

        gestionProduits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminGestionActivity.this,AdminGestionProduitActivity.class);
                startActivity(intent);
            }
        });

        gestionCommandes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminGestionActivity.this, AdminOrdersActivity.class);
                startActivity(intent);
            }
        });

        lougout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminGestionActivity.this,clientLogin.class);
                startActivity(intent);
            }
        });
        gestionaboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminGestionActivity.this, aboutasActivity.class);
                startActivity(intent);
            }
        });


    }


}