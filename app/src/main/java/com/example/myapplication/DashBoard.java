package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;

public class DashBoard extends AppCompatActivity {
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        drawerLayout=findViewById(R.id.drawer_layout);
    }
    /*public void ClickMenu(View view)
    {
        HomeActivity.openDrawer(drawerLayout);
    }
    public void ClickLogo(View view)
    {
        HomeActivity.closeDrawer(drawerLayout);
    }
    public void ClickHome(View view)
    {
        HomeActivity.redireteActivity(this,HomeActivity.class);
    }
    public void ClickDashboard(View view)
    {
        recreate();
    }
    public void ClickAboutUs(View view)
    {
        HomeActivity.redireteActivity(this,AboutUs.class);
    }
    public void ClickLogout(View view){
        HomeActivity.logout(this);
    }
    protected void onPause(){
        super.onPause();
        HomeActivity.closeDrawer(drawerLayout);
    }*/
}