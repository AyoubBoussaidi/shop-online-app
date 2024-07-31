package com.example.myapplication;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Prevalant.Prevalant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ConfirmFinalOrderActivity extends AppCompatActivity {
    private EditText nameEditText,phoneEditText,adresseEditText,cityEditText;
    private Button confirmOrderBtn;
    private ImageView routeurConfirmFinalOrder,calculatorTotalPrice;
    private TextView totalPriceOfConfiramation;
    private String totalAmount = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order);
        totalAmount=getIntent().getStringExtra("Total Price");
        Toast.makeText(this, "Prix total = "+ totalAmount+" DH", Toast.LENGTH_SHORT).show();
        confirmOrderBtn=(Button)findViewById(R.id.confirm_final_order_btn);
        calculatorTotalPrice=findViewById(R.id.calculatot_total_price);
        totalPriceOfConfiramation=findViewById(R.id.total_confirmation_price);
        routeurConfirmFinalOrder=findViewById(R.id.routeur_confirm_final_order);

        nameEditText=(EditText)findViewById(R.id.shipment_name);
        phoneEditText=(EditText)findViewById(R.id.shipment_phone_number);
        adresseEditText=(EditText)findViewById(R.id.shipment_address);
        cityEditText=(EditText)findViewById(R.id.shipment_cityr);

        confirmOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Check();
            }
        });

        calculatorTotalPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalPriceOfConfiramation.setText(totalPriceOfConfiramation.getText().toString()+totalAmount+" DH");

            }
        });
        routeurConfirmFinalOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ConfirmFinalOrderActivity.this,CartActivity.class);
                startActivity(intent);
            }
        });

    }

    private void Check() {
        if(TextUtils.isEmpty(nameEditText.getText().toString())){
            Toast.makeText(this, "Merci de saisir votre nom complet.", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(phoneEditText.getText().toString())){
            Toast.makeText(this, "Merci de saisir votre numero de telephone.", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(adresseEditText.getText().toString())){
            Toast.makeText(this, "Merci de saisir votre adresse.", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(cityEditText.getText().toString())){
            Toast.makeText(this, "Merci de saisir votre ville.", Toast.LENGTH_SHORT).show();
        }
        else{
            confirmOrder();
        }
    }

    private void confirmOrder() {
        final String saveCurrentDate, saveCurrentTime;
        /*Calendar CalForDate=Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd, yyy");
        saveCurrentDate=currentDate.format(CalForDate.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss");
        saveCurrentTime=currentDate.format(CalForDate.getTime());*/

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd,yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss");
        saveCurrentTime = currentTime.format(calendar.getTime());

        final DatabaseReference ordersRef= FirebaseDatabase.getInstance().getReference()
                .child("AdminOrders").child(Prevalant.OnlineUser.getTelephone() );
        HashMap<String,Object> ordersMap=new HashMap<>();
        ordersMap.put("Montant_Total",totalAmount);
        ordersMap.put("Nom",nameEditText.getText().toString());
        ordersMap.put("Telephone",phoneEditText.getText().toString());
        ordersMap.put("Adresse",adresseEditText.getText().toString());
        ordersMap.put("Ville",cityEditText.getText().toString());
        ordersMap.put("Date",saveCurrentDate);
        ordersMap.put("Time",saveCurrentTime);
        ordersMap.put("Etat","pas livre");
        ordersRef.updateChildren(ordersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                FirebaseDatabase.getInstance().getReference().child("Panier").child("User View")
                        .child(Prevalant.OnlineUser.getTelephone())
                        .removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(ConfirmFinalOrderActivity.this, "Votre commande final a ete passe avec succes.", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(ConfirmFinalOrderActivity.this,HomeActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                            }
                        });
            }
        });
    }
}