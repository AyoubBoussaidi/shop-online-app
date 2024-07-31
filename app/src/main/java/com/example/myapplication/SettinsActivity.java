package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Prevalant.Prevalant;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettinsActivity extends AppCompatActivity {
    private CircleImageView profileImageView;
    private EditText  fullNameEditText,userPhoneEditText, addressEditText;
    private TextView profileChangeTextBtn,  closeTextBtn, saveTextButton;

    private Uri imageUri;
    private String myUrl = "";
    private StorageTask uploadTask;
    private StorageReference storageProfilePrictureRef;
    private String checker = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settins);
        storageProfilePrictureRef= FirebaseStorage.getInstance().getReference().child("Profile pictures");
        fullNameEditText = (EditText) findViewById(R.id.settings_full_name);
        profileImageView = (CircleImageView) findViewById(R.id.settings_profile_image);
        userPhoneEditText = (EditText) findViewById(R.id.settings_phone_number);
        addressEditText = (EditText) findViewById(R.id.settings_address);
        profileChangeTextBtn = (TextView) findViewById(R.id.profile_image_change_btn);
        closeTextBtn = (TextView) findViewById(R.id.close_settings_btn);
        saveTextButton = (TextView) findViewById(R.id.update_account_settings_btn);

        userInfoDisplay(profileImageView,fullNameEditText, userPhoneEditText, addressEditText);

        closeTextBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view)
            {
                finish();
            }
        });


        saveTextButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view)
            {
                if (checker.equals("clicked"))
                {
                    userInfoSaved();
                }
                else
                {
                    updateOnlyUserInfo();
                }
            }
        });


        profileChangeTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                checker = "clicked";

                CropImage.activity(imageUri)
                        .setAspectRatio(1, 1)
                        .start(SettinsActivity.this);
            }
        });
    }

    private void updateOnlyUserInfo()
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");

        HashMap<String, Object> userMap = new HashMap<>();
        userMap. put("Nom_Prenom", fullNameEditText.getText().toString());
        userMap. put("Login", addressEditText.getText().toString());
        userMap. put("Telephone", userPhoneEditText.getText().toString());
        ref.child(Prevalant.OnlineUser.getTelephone()).updateChildren(userMap);

        startActivity(new Intent(SettinsActivity.this, HomeActivity.class));
        Toast.makeText(SettinsActivity.this, "Mise à jour réussie des informations de profil.", Toast.LENGTH_SHORT).show();
        finish();
    }

    //onActivityResult body
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE  &&  resultCode==RESULT_OK  &&  data!=null)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();

            profileImageView.setImageURI(imageUri);
        }
        else
        {
            Toast.makeText(this, "Erreur, réessayer.", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(SettinsActivity.this, SettinsActivity.class));
            finish();
        }
    }
    //onActivityResult end

    //userInfoDisplay body
    private void userInfoDisplay(final CircleImageView profileImageView,final EditText fullNameEditText, final EditText userPhoneEditText, final EditText addressEditText)
    {
        DatabaseReference UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalant.OnlineUser.getTelephone());

        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    if (dataSnapshot.child("image").exists())
                    {
                        String image = dataSnapshot.child("image").getValue().toString();
                        String phone = dataSnapshot.child("Telephone").getValue().toString();
                        String address = dataSnapshot.child("Login").getValue().toString();
                        String name = dataSnapshot.child("Nom_Prenom").getValue().toString();

                        Picasso.get().load(image).into(profileImageView);
                        userPhoneEditText.setText(phone);
                        addressEditText.setText(address);
                        fullNameEditText.setText(name);
                    }
                }
            }
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    //userInfoDisplay end

    private void userInfoSaved()
    {
        if (TextUtils.isEmpty(fullNameEditText.getText().toString()))
        {
            Toast.makeText(this, "Le nom et prenom est obligatoire.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(addressEditText.getText().toString()))
        {
            Toast.makeText(this, "L'adresse email est obligatoire.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(userPhoneEditText.getText().toString()))
        {
            Toast.makeText(this, "Le numero du telephone est obligatoire.", Toast.LENGTH_SHORT).show();
        }
        else if(checker.equals("clicked"))
        {
            uploadImage();
        }
    }

    private void uploadImage() {


            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Mettre à jour le profil");
            progressDialog.setMessage("Veuillez patienter pendant que nous mettons à jour les informations de votre compte");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            if (imageUri != null)
            {
                final StorageReference fileRef = storageProfilePrictureRef
                        .child(Prevalant.OnlineUser.getTelephone() + ".jpg");

                uploadTask = fileRef.putFile(imageUri);

                uploadTask.continueWithTask(new Continuation() {
                    @Override
                    public Object then(@NonNull Task task) throws Exception
                    {
                        if (!task.isSuccessful())
                        {
                            throw task.getException();
                        }

                        return fileRef.getDownloadUrl();
                    }
                })
                        .addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task)
                            {
                                if (task.isSuccessful())
                                {
                                    Uri downloadUrl = task.getResult();
                                    myUrl = downloadUrl.toString();

                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");

                                    HashMap<String, Object> userMap = new HashMap<>();
                                    userMap. put("Nom_Prenom", fullNameEditText.getText().toString());
                                    userMap. put("Login", addressEditText.getText().toString());
                                    userMap. put("Telephone", userPhoneEditText.getText().toString());
                                    userMap. put("image", myUrl);
                                    ref.child(Prevalant.OnlineUser.getTelephone()).updateChildren(userMap);

                                    progressDialog.dismiss();

                                    startActivity(new Intent(SettinsActivity.this, HomeActivity.class));
                                    Toast.makeText(SettinsActivity.this, "Mise à jour réussie des informations de profil.", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                                else
                                {
                                    progressDialog.dismiss();
                                    Toast.makeText(SettinsActivity.this, "Error.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
            else
            {
                Toast.makeText(this, "l'image n'est pas sélectionnée.", Toast.LENGTH_SHORT).show();
            }
    }
}