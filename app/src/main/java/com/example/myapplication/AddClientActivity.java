package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AddClientActivity extends AppCompatActivity
{
    private Button CreateAccountButton;
    private EditText InputLogin, InputPassword, InputTelephone,InputNomPrenom;
    private ImageView routeur;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);


        CreateAccountButton = (Button) findViewById(R.id.add_new_client);
        InputLogin = (EditText) findViewById(R.id.Login_client);
        InputPassword = (EditText) findViewById(R.id.Motdepasse);
        InputTelephone = (EditText) findViewById(R.id.Nom_prenom_client);
        InputNomPrenom=findViewById(R.id.NomPrenom);
        routeur=findViewById(R.id.routeur_add_client);
        loadingBar = new ProgressDialog(this);


        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                CreateAccount();
            }
        });

        routeur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddClientActivity.this,AdminGestionClientActivity.class);
                startActivity(intent);
            }
        });

    }



    private void CreateAccount()
    {
        String login = InputLogin.getText().toString();
        String password = InputPassword.getText().toString();
        String telephone = InputTelephone.getText().toString();
        String nomprenom=InputNomPrenom.getText().toString();
        if (TextUtils.isEmpty(login))
        {
            Toast.makeText(this, "entrez votre login ", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "entrez votre mot de passe ", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(telephone))
        {
            Toast.makeText(this, "entrez votre numero de Telephone ", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(nomprenom))
        {
            Toast.makeText(this, "entrez votre Nom et Prenom ", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Ajout d'un client");
            loadingBar.setMessage("s'il vout plait , attendez ...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidateNomPrenom(login, password, telephone,nomprenom);
        }
    }



    private void ValidateNomPrenom(final String Login, final String Password, final String Telephone,final String nomprenom)
    {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (!(dataSnapshot.child("Users").child(Telephone).exists()))
                {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("Login",Login);
                    userdataMap.put("Password",Password);
                    userdataMap.put("Telephone",Telephone);
                    userdataMap.put("Nom_Prenom",nomprenom);


                    RootRef.child("Users").child(Telephone).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(AddClientActivity.this, "Vous avez Ajouter Un Client.", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

                                        Intent intent = new Intent(AddClientActivity.this, AdminGestionClientActivity.class);
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        loadingBar.dismiss();
                                        Toast.makeText(AddClientActivity.this, "Errer Resaeu", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else
                {
                    Toast.makeText(AddClientActivity.this, " Un compte avec cette numero de Telephone  deja existe.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(AddClientActivity.this, "Veuillez réessayer en utilisant un autre numéro de Telephone.", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(AddClientActivity.this, AdminGestionActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
