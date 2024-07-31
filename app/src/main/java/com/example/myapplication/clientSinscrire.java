package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class clientSinscrire extends AppCompatActivity
{
    private Button CreateAccountButton;
    private EditText InputLogin, InputPassword, InputTele,InputName;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_sinscrire);


        InputName=findViewById(R.id.register_name_input);
        CreateAccountButton = (Button) findViewById(R.id.register_btn);
        InputLogin = (EditText) findViewById(R.id.register_Login);
        InputPassword = (EditText) findViewById(R.id.register_Password_Input);
        InputTele = (EditText) findViewById(R.id.register_Tele_input);
        loadingBar = new ProgressDialog(this);


        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                CreateAccount();
            }
        });
    }



    private void CreateAccount()
    {
        String login = InputLogin.getText().toString();
        String password = InputPassword.getText().toString();
        String tele = InputTele.getText().toString();
        String name=InputName.getText().toString();
        if (TextUtils.isEmpty(login))
        {
            Toast.makeText(this, "entrez votre Email ", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "entrez votre mot de passe ", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(tele))
        {
            Toast.makeText(this, "entrez votre Telephone ", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(name))
        {
            Toast.makeText(this, "entrez votre Nom et Prenom ", Toast.LENGTH_SHORT).show();
        }else if(password.length() < 6)
        {
            Toast.makeText(this, "entrez a moins 6 caractere", Toast.LENGTH_SHORT).show();
        }else if(!Patterns.EMAIL_ADDRESS.matcher(login).matches())
        {
            Toast.makeText(this, "s'il vous plait entrez un  email", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Creation de compte");
            loadingBar.setMessage("s'il vout plait , attendez ...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidateCIN(login, password, tele,name);
        }
    }



    private void ValidateCIN(final String Login, final String Password, final String Telephone,final String Name)
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
                    userdataMap.put("Nom_Prenom",Name);

                    RootRef.child("Users").child(String.valueOf(Telephone)).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(clientSinscrire.this, "Vous avez Inscris.", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

                                        Intent intent = new Intent(clientSinscrire.this, clientLogin.class);
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        loadingBar.dismiss();
                                        Toast.makeText(clientSinscrire.this, "Errer Resaeu", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else
                {
                    Toast.makeText(clientSinscrire.this, " Un compte avec cette numero  deja existe.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(clientSinscrire.this, "Veuillez réessayer en utilisant un autre numéro de telephone.", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(clientSinscrire.this, WelcomeActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}