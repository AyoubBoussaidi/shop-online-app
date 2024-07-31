package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.TestSearchAdmin.ProductListActivityV3;

public class AdminGestionProduitActivity extends AppCompatActivity {
    CardView ajouterProduit,afficherProduit,supprimerrProduit,modifierProduit;
    ImageView routeur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_gestion_produit);
        ajouterProduit=findViewById(R.id.Ajouter_produit);
        afficherProduit=findViewById(R.id.Affciher_produit);
        //supprimerrProduit=findViewById(R.id.Supprimer_produit);
        //modifierProduit=findViewById(R.id.Modifier_produit);
        routeur=findViewById(R.id.routeur_gestion_product);

        ajouterProduit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminGestionProduitActivity.this,AdminCategorieActivity.class);
                startActivity(intent);
            }
        });
        afficherProduit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminGestionProduitActivity.this, ProductListActivityV3.class);
                startActivity(intent);
            }
        });

        routeur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminGestionProduitActivity.this,AdminGestionActivity.class);
                startActivity(intent);
            }
        });
        /*supprimerrProduit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminGestionProduitActivity.this,DeleteProductActivity.class);
                startActivity(intent);
            }
        });
        modifierProduit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminGestionProduitActivity.this,UpdateProductActivity.class);
                startActivity(intent);
            }
        });
        modifierProduit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminGestionProduitActivity.this,AdminGestionActivity.class);
                startActivity(intent);
            }
        });*/
    }
}