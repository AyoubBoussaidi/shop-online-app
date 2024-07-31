package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Interface.ItemClickListner;
import com.example.myapplication.Model.AdminOrders;
import com.example.myapplication.Model.Products;
import com.example.myapplication.Prevalant.Prevalant;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminOrdersActivity extends AppCompatActivity {

    private RecyclerView ordersList;
    private DatabaseReference ordersRef;

    MyAdapterOrders myAdapterP;
    ArrayList<AdminOrders> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_orders);

        ordersRef= FirebaseDatabase.getInstance().getReference().child("AdminOrders");
        ordersList=findViewById(R.id.orders_list);

        ordersList.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        myAdapterP=new MyAdapterOrders(this,list);
        ordersList.setAdapter(myAdapterP);

        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    AdminOrders product = dataSnapshot.getValue(AdminOrders.class);
                    list.add(product);


                }
                myAdapterP.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

     @Override
   protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<AdminOrders> options=
                new FirebaseRecyclerOptions.Builder<AdminOrders>().setQuery(ordersRef,AdminOrders.class)
                        .build();

        FirebaseRecyclerAdapter<AdminOrders,AdminOrdersViewHolder> adapter=new
                FirebaseRecyclerAdapter<AdminOrders, AdminOrdersViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull AdminOrdersViewHolder holder, int position, @NonNull AdminOrders model) {
                            holder.userName.setText("Nom : "+ model.getNom());
                            holder.userPhoneNumber.setText("Telephone: "+ model.getTelephone());
                            holder.userDateTime.setText("Demende a :"+ model.getDate()+", "+model.getTime());
                            holder.userTotalPrice.setText("Montant totale : "+ model.getMontant_Total());
                            holder.UserAdressCity.setText("Adresse complet: "+ model.getAdresse()+", "+model.getVille());

                            holder.showordersbtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    String uID=getRef(position).getKey();
                                    Intent intent=new Intent(AdminOrdersActivity.this,AdminUserOrderProductsActivity.class);
                                    intent.putExtra("uid",uID);
                                    startActivity(intent);
                                }
                            });

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CharSequence options[] =new CharSequence[]{
                                        "Oui",
                                        "Non"
                                };
                                androidx.appcompat.app.AlertDialog.Builder builder=new AlertDialog.Builder(AdminOrdersActivity.this);
                                builder.setTitle(" Confirmation de la commande");
                                //builder.setMessage("voulez-vous livrer les produits de cette commande ? sinon cliquez sur non");
                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                        if(which==0){
                                            String uID=getRef(position).getKey();
                                            removeOrder(uID);
                                        }
                                        if(which==1){

                                        }
                                    }
                                });
                                builder.show();
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public AdminOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_layout,parent,false);
                        return  new AdminOrdersViewHolder(view);
                    }
                };
        ordersList.setAdapter(adapter);
        adapter.startListening();
    }



    public static class AdminOrdersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView userName,userPhoneNumber,userTotalPrice,userDateTime,UserAdressCity;
        public Button showordersbtn;
        ItemClickListner listner;
        public AdminOrdersViewHolder(@NonNull View itemView) {
            super(itemView);

            userName=itemView.findViewById(R.id.order_user_name);
            userPhoneNumber=itemView.findViewById(R.id.order_phone_number);
            userTotalPrice=itemView.findViewById(R.id.order_total_price);
            userDateTime=itemView.findViewById(R.id.order_date_time);
            UserAdressCity=itemView.findViewById(R.id.order_adress_city);
            showordersbtn=itemView.findViewById(R.id.show_all_products_oreders);
        }

        public void setItemClickListner(ItemClickListner listner){
            this.listner=listner;
        }

        @Override
        public void onClick(View v) {
            listner.onClick(v,getAdapterPosition(),false);
        }
    }


    private void removeOrder(String uID) {
 ordersRef.child(uID).removeValue();
    }
}