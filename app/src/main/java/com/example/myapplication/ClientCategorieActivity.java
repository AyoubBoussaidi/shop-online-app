package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ClientCategorieActivity extends AppCompatActivity {

    private ImageView routeur;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_categorie);

        routeur=findViewById(R.id.routeur_from_categorie_tosearch_client);

        routeur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ClientCategorieActivity.this,SearchByCategorieActivityActivity.class);
                startActivity(intent);
            }
        });
    }
}