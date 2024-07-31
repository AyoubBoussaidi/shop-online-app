package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.Model.Products;
import com.example.myapplication.Model.Users;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private DatabaseReference ProductsRef;
    DatabaseReference database;
    MyAdapter myAdapter;
    ArrayList<Users> list;
    private Button searchBtn;
    private EditText inputSearchText;
    private String SearchInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        recyclerView = findViewById(R.id.userList);
        database = FirebaseDatabase.getInstance().getReference("Users");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        list = new ArrayList<>();
        myAdapter = new MyAdapter(this, list);
        recyclerView.setAdapter(myAdapter);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchInput = inputSearchText.getText().toString();
                //onStart();
            }
        });


        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Users user = dataSnapshot.getValue(Users.class);
                    list.add(user);


                }
                myAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

   /* public void onStart( ) {
        super.onStart();
        SearchInput = inputSearchText.getText().toString();
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Users");


        FirebaseRecyclerOptions options1 =
                new FirebaseRecyclerOptions.Builder<Users>().
                        setQuery(ProductsRef.orderByChild("Login").startAt(SearchInput).endAt(SearchInput + "\uf8ff"), Users.class)
                        .build();

        FirebaseRecyclerAdapter<Users, MyAdapter.MyViewHolder> adapter =
                new FirebaseRecyclerAdapter<Users, MyAdapter.MyViewHolder>(options1) {
                    @Override
                    protected void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position, @NonNull Users model) {

                        Users user = list.get(position);
                        holder.firstName.setText(user.getLogin());
                        holder.lastName.setText(user.getTelephone());


                        //added code

                        holder.remove.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getParent().getBaseContext());
                                builder.setTitle("Suppression");
                                builder.setMessage("vous voulez supprimer ce client...?");

                                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        FirebaseDatabase.getInstance().getReference().child("Users")
                                                .child(user.getTelephone()).removeValue();
                                    }
                                });

                                builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });

                                builder.show();
                            }
                        });


                        holder.edit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                final DialogPlus dialogPlus = DialogPlus.newDialog(getParent())
                                        .setContentHolder(new ViewHolder(R.layout.dialogcontent))
                                        .setExpanded(true, 1100)
                                        .create();

                                View myview = dialogPlus.getHolderView();

                                final EditText name = myview.findViewById(R.id.uname);
                                final EditText course = myview.findViewById(R.id.ucourse);
                                final EditText email = myview.findViewById(R.id.uemail);
                                final EditText phone = myview.findViewById(R.id.uphone);
                                Button submit = myview.findViewById(R.id.usubmit);

                                name.setText(user.getNom_Prenom());
                                course.setText(user.getPassword());
                                email.setText(user.getLogin());
                                phone.setText(user.getTelephone());

                                dialogPlus.show();

                                submit.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Map<String, Object> map = new HashMap<>();
                                        map.put("Nom_Prenom", name.getText().toString());
                                        map.put("Login", email.getText().toString());
                                        map.put("Password", course.getText().toString());

                                        FirebaseDatabase.getInstance().getReference().child("Users")
                                                .child(user.getTelephone()).updateChildren(map)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        dialogPlus.dismiss();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        dialogPlus.dismiss();
                                                    }
                                                });
                                    }
                                });


                            }
                        });

                    }

                    @NonNull
                    @Override
                    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_list_item_in_admin, parent, false);
                        return new MyAdapter.MyViewHolder(v);
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }*/


}