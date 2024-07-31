package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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

import io.paperdb.Paper;


public class WelcomeActivity extends AppCompatActivity
{
    private TextView joinNowButton, loginButton;
    private ProgressDialog loadingBar;
   // private String parentDbName = "Users";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        joinNowButton = (TextView) findViewById(R.id.main_join_now_btn);
        loginButton = (TextView) findViewById(R.id.main_login_btn);
        loadingBar = new ProgressDialog(this);


        Paper.init(this);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(WelcomeActivity.this, clientLogin.class);
                startActivity(intent);
            }
        });


        joinNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(WelcomeActivity.this, clientSinscrire.class);
                startActivity(intent);
            }
        });


        String UserLoginKey = Paper.book().read(Prevalant.UserLoginKey);
        String UserPasswordKey = Paper.book().read(Prevalant.UserPasswordKey);
        String UserTeleKey = Paper.book().read(Prevalant.UserTeleKey);

        if (UserLoginKey != "" && UserPasswordKey != "" && UserTeleKey!= "")
        {
            if (!TextUtils.isEmpty(UserLoginKey)  &&  !TextUtils.isEmpty(UserPasswordKey) && !TextUtils.isEmpty(UserTeleKey) )
            {
                AllowAccess(UserLoginKey, UserTeleKey,UserPasswordKey);

                loadingBar.setTitle("Déjà connecté");
                loadingBar.setMessage("S'il vous plaît, attendez.....");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
            }
        }
    }



    private void AllowAccess(final String login,final String telephone, final String password)
    {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();


        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.child("Users").child(telephone).exists())
                {
                    Users usersData = dataSnapshot.child("Users").child(telephone).getValue(Users.class);

                    if (usersData.getTelephone().equals(telephone))
                    {
                        if (usersData.getLogin().equals(login))
                        {
                            if (usersData.getPassword().equals(password))
                            {

                                Toast.makeText(WelcomeActivity.this, "Veuillez patienter, vous êtes déjà connecté...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(WelcomeActivity.this, HomeActivity.class);
                                Prevalant.OnlineUser = usersData;
                                startActivity(intent);
                            }
                            else
                            {
                                loadingBar.dismiss();
                                Toast.makeText(WelcomeActivity.this, "Mot De Passe incorrect.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
                else
                {
                    Toast.makeText(WelcomeActivity.this, "Un compte avec cette CIN  deja existe", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
