package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Model.Panier;
import com.example.myapplication.Prevalant.Prevalant;
import com.example.myapplication.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TextView txtTotalAmount,txtMsg1;
    private Button nextprocessBtn,paymentButton;
    ImageView calculatebtn;
    private int overTotalPrice=0;
    //add paypale methode
    private static PayPalConfiguration config = new PayPalConfiguration()

            // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
            // or live (ENVIRONMENT_PRODUCTION)
            .environment(PayPalConfiguration.ENVIRONMENT_NO_NETWORK)

            .clientId("AUZbVP1f3M667pais0DH8D4hysTqjjAkhmgOAG8bHwiHgORDN-TyigbhFhunffuOpYBi8qUI4RGKeRgv");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        recyclerView=findViewById(R.id.cart_list);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //getLoaderManager().initLoader(CART_LOADER, null, this);

        paymentButton = (Button) findViewById(R.id.button_payment);
        //Paypal Intent
        Intent intent = new Intent(this, PayPalService.class);

        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        startService(intent);

        nextprocessBtn=findViewById(R.id.next_process_btn);
        txtTotalAmount=findViewById(R.id.total_price);
        calculatebtn=findViewById(R.id.calculator_total_price_in_cart);
        txtMsg1=findViewById(R.id.msg1);
        nextprocessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //txtTotalAmount.setText("Prix Total = "+String.valueOf(overTotalPrice)+" DH");
                Intent intent =new Intent(CartActivity.this,ConfirmFinalOrderActivity.class);
                intent.putExtra("Total Price", String.valueOf(overTotalPrice));
                startActivity(intent);
                finish();
            }
        });
        calculatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtTotalAmount.setText("Prix Total = "+String.valueOf(overTotalPrice)+" DH");
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        //chekOrderState();

        final DatabaseReference cartListRef= FirebaseDatabase.getInstance().getReference().child("Panier");
        FirebaseRecyclerOptions<Panier> options=new FirebaseRecyclerOptions.Builder<Panier>()
                .setQuery(cartListRef.child("User View")
                        .child(Prevalant.OnlineUser.getTelephone()).child("Products"), Panier.class)
                .build();

        FirebaseRecyclerAdapter<Panier, CartViewHolder> adapter=new FirebaseRecyclerAdapter<Panier, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull Panier model) {
                holder.txtProductName.setText(model.getPdesignation());
                holder.txtProductPrice.setText("Prix = "+model.getPrix());
                holder.txtProductquantity.setText("Quantite = "+model.getQuantite());

                int oneTypeProductTPrice=(Integer.parseInt(model.getPrix())* Integer.parseInt(model.getQuantite()));
                overTotalPrice= overTotalPrice+ oneTypeProductTPrice;

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CharSequence options[] =new CharSequence[]{
                                "Modifier",
                                "Supprimer"
                        };
                        AlertDialog.Builder builder=new AlertDialog.Builder(CartActivity.this);
                        builder.setTitle("Options : ");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                if(which==0){
                                    Intent intent=new Intent(CartActivity.this,ProductDetailsActivity.class);
                                    intent.putExtra("Pid",model.getPid());
                                    startActivity(intent);
                                }
                                if(which==1){
                                    cartListRef.child("User View").child(Prevalant.OnlineUser.getTelephone())
                                            .child("Products").child(model.getPid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(CartActivity.this,"Produit supprimer",Toast.LENGTH_SHORT);
                                                Intent intent=new Intent(CartActivity.this,HomeActivity.class);
                                                startActivity(intent);
                                            }
                                        }
                                    });
                                }
                            }
                        });
                        builder.show();
                    }
                });

            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout,parent,false);
                CartViewHolder holder=new CartViewHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
    /*private void chekOrderState(){
        DatabaseReference  orderRef;
        orderRef=FirebaseDatabase.getInstance().
                getReference().child("AdminOrders").child(Prevalant.OnlineUser.getTelephone());

        orderRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String shippingState=snapshot.child("Etat").getValue().toString();
                    String userName=snapshot.child("Nom").getValue().toString();
                    if(shippingState.equals("livre")){

                        txtTotalAmount.setText("Dear "+userName+"\n"+"Order is chipped successefully");
                        recyclerView.setVisibility(View.GONE);
                        txtMsg1.setVisibility(View.VISIBLE);
                        txtMsg1.setText("Congratulations your final order has been blaced successfully soon youl will received your order at your home adresse");
                        nextprocessBtn.setVisibility(View.GONE);
                        Toast.makeText(CartActivity.this,"you can perchase more product once your recived your first final order",Toast.LENGTH_SHORT).show();

                    }else if(shippingState.equals("pas livre")){

                        txtTotalAmount.setText("Shipping state = not shipped");
                        recyclerView.setVisibility(View.GONE);
                        txtMsg1.setVisibility(View.VISIBLE);
                        nextprocessBtn.setVisibility(View.GONE);
                        Toast.makeText(CartActivity.this,"you can perchase more product once your recived your first final order",Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }*/

    public void paymentClick(View pressed) {

        // PAYMENT_INTENT_SALE will cause the payment to complete immediately.
        // Change PAYMENT_INTENT_SALE to
        //   - PAYMENT_INTENT_AUTHORIZE to only authorize payment and capture funds later.
        //   - PAYMENT_INTENT_ORDER to create a payment for authorization and capture
        //     later via calls from your server.

        PayPalPayment payment = new PayPalPayment(new BigDecimal(overTotalPrice), "USD", "Being payment for items ordered" ,
                PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this, PaymentActivity.class);

        // send the same configuration for restart resiliency
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirm != null) {
                try {
                    Log.i("paymentExample", confirm.toJSONObject().toString(4));

                    // TODO: send 'confirm' to your server for verification.
                    // see https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
                    // for more details.

                } catch (JSONException e) {
                    Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("paymentExample", "The user canceled.");
        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
        }
    }
}