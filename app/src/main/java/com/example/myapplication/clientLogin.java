package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Model.Users;
import com.example.myapplication.Prevalant.Prevalant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;



public class clientLogin extends AppCompatActivity
{
    private EditText InputLogin, InputPassword,InputCIN;
    private Button LoginButton;
    private ProgressDialog loadingBar;
    private TextView AdminLink, NotAdminLink;

    private String parentDbName = "Users";
    private CheckBox chkBoxRememberMe;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_login);

        LoginButton = (Button) findViewById(R.id.login_btn);
        InputPassword = (EditText) findViewById(R.id.input_password);
        InputLogin = (EditText) findViewById(R.id.login_input);
        InputCIN=(EditText) findViewById(R.id.input_tele);
        AdminLink = (TextView) findViewById(R.id.admin_panel_link);
        NotAdminLink = (TextView) findViewById(R.id.not_admin_panel_link);
        loadingBar = new ProgressDialog(this);


        chkBoxRememberMe = (CheckBox) findViewById(R.id.remember_mechikd);
        Paper.init(this);


        AdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                LoginButton.setText("Se Connecter");
                AdminLink.setVisibility(View.INVISIBLE);
                NotAdminLink.setVisibility(View.VISIBLE);
                chkBoxRememberMe.setVisibility(View.INVISIBLE);
                parentDbName = "Admins";
            }
        });
        NotAdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                LoginButton.setText(" Se Connecter");
                AdminLink.setVisibility(View.VISIBLE);
                NotAdminLink.setVisibility(View.INVISIBLE);
                chkBoxRememberMe.setVisibility(View.VISIBLE);
                parentDbName = "Users";
            }
        });
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                LoginUser();
            }
        });

    }

    private void LoginUser()
    {
        String login = InputLogin.getText().toString();
        String tele=InputCIN.getText().toString();
        String password = InputPassword.getText().toString();

        if (TextUtils.isEmpty(login))
        {
            Toast.makeText(this, "Veuillez entrer votre login...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(tele))
        {
            Toast.makeText(this, "Veuillez entrer votre numero de telephone...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Veuillez entrer votre mot de passe...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Connection");
            loadingBar.setMessage("Veuillez patienter pendant que nous vérifions les informations d'identification.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            AllowAccessToAccount(login,tele, password);
        }
    }
    private void AllowAccessToAccount(final String login,final String tele, final String password)
    {
        if(chkBoxRememberMe.isChecked())
        {
            Paper.book().write(Prevalant.UserLoginKey, login);
            Paper.book().write(Prevalant.UserTeleKey,tele);
            Paper.book().write(Prevalant.UserPasswordKey, password);
        }

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();


        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.child(parentDbName).child(tele).exists())
                {
                    Users usersData = dataSnapshot.child(parentDbName).child(tele).getValue(Users.class);

                    if (usersData.getTelephone().equals(tele))
                    {
                        if (usersData.getPassword().equals(password))
                        {
                            if(usersData.getLogin().equals(login)){


                                if (parentDbName.equals("Admins"))
                                {
                                    Toast.makeText(clientLogin.this, "Bonjour Admin,  vous êtes connecté avec succès..", Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();

                                    Intent intent = new Intent(clientLogin.this, AdminGestionActivity.class);
                                    startActivity(intent);
                                }
                                else if (parentDbName.equals("Users"))
                                {
                                    Toast.makeText(clientLogin.this, "vous  êtes connecté avec succès...", Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();

                                    Intent intent = new Intent(clientLogin.this, HomeActivity.class);
                                    Prevalant.OnlineUser = usersData;
                                    startActivity(intent);
                                }
                            }
                            else {
                                loadingBar.dismiss();
                                Toast.makeText(clientLogin.this, "Login incorrect.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            loadingBar.dismiss();
                            Toast.makeText(clientLogin.this, "Mot De Passe incorrect.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else
                {
                    Toast.makeText(clientLogin.this, "Il n'existe pas de compte avec ce numéro de Telephone ", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}