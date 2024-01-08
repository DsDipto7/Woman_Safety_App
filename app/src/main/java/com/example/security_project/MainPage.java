package com.example.security_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

public class MainPage extends AppCompatActivity {

    CardView card1, card2, card3,card4;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        card1=findViewById(R.id.c1);
        card2=findViewById(R.id.c2);
        card3=findViewById(R.id.c3);
        card4=findViewById(R.id.c4);

        navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawer_id);
        toolbar = findViewById(R.id.toolbar_id);


        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainPage.this,Alert.class);
                startActivity(i);
                //finish();
            }
        });

        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainPage.this, Map.class);
                startActivity(i);
                //finish();
            }
        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainPage.this, AddContact.class);
                startActivity(i);
            }
        });
        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainPage.this, HelpSMS.class);
                startActivity(i);
            }
        });

        navigationView.setNavigationItemSelectedListener(item ->
        {

            if(item.getItemId() == R.id.home_id){
                Intent intent = new Intent(MainPage.this, MainPage.class);
                startActivity(intent);
                //finish();
            }

            if(item.getItemId() == R.id.policeStations){
                Intent intent = new Intent(MainPage.this, Stations.class);
                startActivity(intent);
               // finish();
            }




            if(item.getItemId() == R.id.share){
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBodyText = "Share it";
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject here");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBodyText);
                startActivity(Intent.createChooser(sharingIntent, "Share"));
                return true;
            }
            if(item.getItemId() == R.id.about_usid){
                Intent intent = new Intent(MainPage.this, about_us.class);
                startActivity(intent);
            }
            return true;
        });
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


}
