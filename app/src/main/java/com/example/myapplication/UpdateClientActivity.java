package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.databinding.ActivityUpdateClientBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class UpdateClientActivity extends AppCompatActivity {

    ActivityUpdateClientBinding binding;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateClientBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Tele = binding.userName.getText().toString();
                String Login = binding.Login.getText().toString();
                String Password = binding.Password.getText().toString();


                updateData(Tele,Login,Password);

            }
        });

    }

    private void updateData(String tele, String login, String password) {

        HashMap User = new HashMap();
        User.put("Telephone",tele);
        User.put("Login",login);
        User.put("Password",password);

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.child(tele).updateChildren(User).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {

                if (task.isSuccessful()){

                    binding.userName.setText("");
                    binding.Login.setText("");
                    binding.Password.setText("");
                    Toast.makeText(UpdateClientActivity.this," Informations modifier avec succes",Toast.LENGTH_SHORT).show();

                }else {

                    Toast.makeText(UpdateClientActivity.this,"Échec de modifier ces informations",Toast.LENGTH_SHORT).show();

                }

            }
        });

    }


}