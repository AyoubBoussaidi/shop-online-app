package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Handler;
import android.widget.ImageView;

public class SplashScreenActivity extends AppCompatActivity {

    // Duration for which each image will be displayed (in milliseconds)
    private static final int IMAGE_DISPLAY_DURATION = 4000;

    // Array of image resources
    private static final int[] IMAGE_RESOURCES = {
            R.drawable.image1,
            R.drawable.image2
    };

    private ImageView imageView;
    private int currentImageIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        imageView = findViewById(R.id.imageView);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Start displaying the images
        displayImages();
    }

    private void displayImages() {
        if (currentImageIndex < IMAGE_RESOURCES.length) {
            // Set the current image resource
            imageView.setImageResource(IMAGE_RESOURCES[currentImageIndex]);

            // Move to the next image after the specified duration
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    currentImageIndex++;
                    displayImages();
                }
            }, IMAGE_DISPLAY_DURATION);
        } else {
            // All images displayed, start the main activity
            startActivity(new Intent(SplashScreenActivity.this, WelcomeActivity.class));
            finish();
        }
    }
}
