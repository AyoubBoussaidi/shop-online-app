package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.Model.AdminOrders;
import java.util.ArrayList;

public class MyAdapterOrders  extends RecyclerView.Adapter<MyAdapterOrders.MyViewHolderOrders>{

    Context context;

    ArrayList<AdminOrders> list;


    public MyAdapterOrders(Context context, ArrayList<AdminOrders> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public MyAdapterOrders.MyViewHolderOrders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.orders_layout,parent,false);
        return  new MyViewHolderOrders(v);
    }



    public void onBindViewHolder(@NonNull MyAdapterOrders.MyViewHolderOrders holder, int position) {

        AdminOrders products = list.get(position);
        holder.userName.setText("Nom : "+ products.getNom());
        holder.userPhoneNumber.setText("Telephone: "+ products.getTelephone());
        holder.userDateTime.setText("Demende a :"+ products.getDate()+", "+products.getTime());
        holder.userTotalPrice.setText("Montant totale : "+ products.getMontant_Total());
        holder.UserAdressCity.setText("Adresse complet: "+ products.getAdresse()+", "+products.getVille());

        //added code

    }

    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolderOrders extends RecyclerView.ViewHolder{

        TextView userName,userPhoneNumber,userTotalPrice,userDateTime,UserAdressCity;

        public MyViewHolderOrders(@NonNull View itemView) {
            super(itemView);
            userName=itemView.findViewById(R.id.order_user_name);
            userPhoneNumber=itemView.findViewById(R.id.order_phone_number);
            userTotalPrice=itemView.findViewById(R.id.order_total_price);
            userDateTime=itemView.findViewById(R.id.order_date_time);
            UserAdressCity=itemView.findViewById(R.id.order_adress_city);


        }

    }

}
