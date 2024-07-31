package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;
import java.util.Calendar;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;


public class aboutasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutas);


        Element adsElement = new Element();
        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.applogo)
                .setDescription(" ShopOnline est une excellente solution pour mettre " +
                        "en place une campagne de fidélisation client mais également" +
                        " de conquête de nouveaux consommateurs.Un outil idéal pour faire vendre " +
                        " plus efficacement, de manière plus directe et simple que via une vaste campagne" +
                        "  de publicité notamment.Permettre aussi de faire des publicités des marques et autres\n" )
                .addItem(new Element().setTitle("Version 1.0"))
                .addGroup("Contact  nous!")
                .addEmail("mcommerceghizlaneayoub@gmail.com")
                .addPlayStore("com.example.myapplication")   //Replace all this with your package name
                .addInstagram("m_commerce_with_g_a")//Your instagram id

                .addItem(createCopyright())
                .create();
        setContentView(aboutPage);
    }

    private Element createCopyright() {
        Element copyright = new Element();
        @SuppressLint("DefaultLocale") final String copyrightString = String.format("Copyright %d m-commerce", Calendar.getInstance().get(Calendar.YEAR));
        copyright.setTitle(copyrightString);
        // copyright.setIcon(R.mipmap.ic_launcher);
        copyright.setGravity(Gravity.CENTER);
        copyright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(aboutasActivity.this, copyrightString, Toast.LENGTH_SHORT).show();
            }
        });
        return copyright;
    }

}