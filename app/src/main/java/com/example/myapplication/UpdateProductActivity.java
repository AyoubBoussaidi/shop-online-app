package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.example.myapplication.databinding.ActivityUpdateProductBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class UpdateProductActivity extends AppCompatActivity {

    ActivityUpdateProductBinding binding;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.updateProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = binding.pId.getText().toString();
                String description = binding.Pdescription.getText().toString();
                String prix = binding.Pprix.getText().toString();
                String quantite = binding.Pquantite.getText().toString();
                String designation =binding.Pdesignation.getText().toString();


                updateData(id,description,prix,quantite,designation);

            }
        });

    }

    private void updateData(String id, String description, String prix,String quantite,String designation) {

        HashMap Product = new HashMap();
       Product.put("Designation",designation);
        Product.put("Description",description);
        Product.put("Prix",prix);
        Product.put("Quantite",quantite);

        databaseReference = FirebaseDatabase.getInstance().getReference("Products");
        databaseReference.child(id).updateChildren(Product).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {

                if (task.isSuccessful()){

                    binding.pId.setText("");
                    binding.Pdescription.setText("");
                    binding.Pprix.setText("");
                    binding.Pquantite.setText("");
                    binding.Pdesignation.setText("");
                    Toast.makeText(UpdateProductActivity.this,"Informations modifier avec succes",Toast.LENGTH_SHORT).show();

                }else {

                    Toast.makeText(UpdateProductActivity.this,"Échec de modifier ces informations",Toast.LENGTH_SHORT).show();

                }

            }
        });

    }


}