package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.TestSearchAdmin.UserListActivityV3;

public class AdminGestionClientActivity extends AppCompatActivity{
    CardView ajouterClient,afficherClient,supprimerClient,moddifierClient;
    ImageView routeur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_gestion_client);
        ajouterClient=findViewById(R.id.Ajouter_client);
        afficherClient=findViewById(R.id.Affciher_client);
        routeur=findViewById(R.id.routeur_gestion_client);


        ajouterClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminGestionClientActivity.this,AddClientActivity.class);
                startActivity(intent);
            }
        });
      /*  afficherClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminGestionClientActivity.this, UserListActivity.class);
                startActivity(intent);
            }
        });*/
        afficherClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminGestionClientActivity.this, UserListActivityV3.class);
                startActivity(intent);
            }
        });

        routeur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminGestionClientActivity.this, AdminGestionActivity.class);
                startActivity(intent);
            }
        });
    }
}