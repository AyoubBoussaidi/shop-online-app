package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.databinding.ActivityDeleteClientBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DeleteClientActivity extends AppCompatActivity {

    ActivityDeleteClientBinding binding;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeleteClientBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.deletedataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = binding.etusername.getText().toString();
                if (!username.isEmpty()){

                    deleteData(username);

                }else{

                    Toast.makeText(DeleteClientActivity.this,"Please Enter Username",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void deleteData(String username) {

        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(username).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()){

                    Toast.makeText(DeleteClientActivity.this,"Supprimé avec succès",Toast.LENGTH_SHORT).show();
                    binding.etusername.setText("");


                }else {

                    Toast.makeText(DeleteClientActivity.this,"Échec",Toast.LENGTH_SHORT).show();


                }

            }
        });
    }
}