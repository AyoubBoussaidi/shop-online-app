package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AdminCategorieActivity extends AppCompatActivity {
    private ImageView routeurfromCategorie,tShirts, sportsTShirts, sportsPants, shortSportsPants;
    private ImageView watches, backbag, hatCaps, sportsTrainners;
    private ImageView headPhones, laptops, mobilePhones,glasses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_categorie);

        tShirts=(ImageView) findViewById(R.id.t_shirts);
        sportsTShirts=(ImageView) findViewById(R.id.sports_t_shirts);
        sportsPants=(ImageView) findViewById(R.id.sports_pant);
        shortSportsPants=(ImageView) findViewById(R.id.short_sports_pant);

        watches=(ImageView) findViewById(R.id.watches);
        backbag=(ImageView) findViewById(R.id.back_bag);
        hatCaps=(ImageView) findViewById(R.id.hats_caps);
        sportsTrainners=(ImageView) findViewById(R.id.sports_trainner);

        headPhones=(ImageView) findViewById(R.id.headphones);
        laptops=(ImageView) findViewById(R.id.laptop_pc);
        mobilePhones=(ImageView) findViewById(R.id.mobilephones);
        glasses=(ImageView) findViewById(R.id.sun_glasses);
        routeurfromCategorie=(ImageView) findViewById(R.id.routeur_from_categorie);

        routeurfromCategorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategorieActivity.this,AdminGestionProduitActivity.class);
                startActivity(intent);
            }
        });

        tShirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategorieActivity.this,addProductActivity.class);
                intent.putExtra("Categorie","T-shirts");
                startActivity(intent);
            }
        });

        sportsTShirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategorieActivity.this,addProductActivity.class);
                intent.putExtra("Categorie","T-shirts de sport");
                startActivity(intent);
            }
        });

        sportsPants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategorieActivity.this,addProductActivity.class);
                intent.putExtra("Categorie","Pantalons");
                startActivity(intent);
            }
        });

        shortSportsPants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategorieActivity.this,addProductActivity.class);
                intent.putExtra("Categorie","Shorts");
                startActivity(intent);
            }
        });

        watches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategorieActivity.this,addProductActivity.class);
                intent.putExtra("Categorie","Montres");
                startActivity(intent);
            }
        });

        backbag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategorieActivity.this,addProductActivity.class);
                intent.putExtra("Categorie","Sacs");
                startActivity(intent);
            }
        });

        hatCaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategorieActivity.this,addProductActivity.class);
                intent.putExtra("Categorie","Chapeaux");
                startActivity(intent);
            }
        });
        sportsTrainners.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategorieActivity.this,addProductActivity.class);
                intent.putExtra("Categorie","Chaussures");
                startActivity(intent);
            }
        });

        headPhones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategorieActivity.this,addProductActivity.class);
                intent.putExtra("Categorie","Écouteurs");
                startActivity(intent);
            }
        });
        laptops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategorieActivity.this,addProductActivity.class);
                intent.putExtra("Categorie","Ordinateurs");
                startActivity(intent);
            }
        });

        mobilePhones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategorieActivity.this,addProductActivity.class);
                intent.putExtra("Categorie","Téléphones");
                startActivity(intent);
            }
        });

        glasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCategorieActivity.this,addProductActivity.class);
                intent.putExtra("Categorie","Lunettes");
                startActivity(intent);
            }
        });
    }
}