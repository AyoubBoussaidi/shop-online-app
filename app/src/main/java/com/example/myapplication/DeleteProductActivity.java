package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;

import android.view.View;
import android.widget.Toast;
import com.example.myapplication.databinding.ActivityDeleteProductBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DeleteProductActivity extends AppCompatActivity {

    ActivityDeleteProductBinding binding;
    DatabaseReference reference;
    private StorageReference ProductImagesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeleteProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.deleteproductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = binding.idproduct.getText().toString();
                if (!username.isEmpty()){

                    deleteData(username);

                }else{

                    Toast.makeText(DeleteProductActivity.this,"Please Enter Username",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void deleteData(String username) {

        reference = FirebaseDatabase.getInstance().getReference("Products");
        ProductImagesRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        reference.child(username).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()){

                    Toast.makeText(DeleteProductActivity.this,"Supprimé avec succès",Toast.LENGTH_SHORT).show();
                    binding.idproduct.setText("");


                }else {

                    Toast.makeText(DeleteProductActivity.this,"Échec",Toast.LENGTH_SHORT).show();


                }

            }
        });
    }
}