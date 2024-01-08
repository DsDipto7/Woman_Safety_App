package com.example.security_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class StationsDetails extends AppCompatActivity {
    TextView t1,t2,t3,t4,t5;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stations_details);


 
        t2=findViewById(R.id.roadNo);
        t3=findViewById(R.id.thana);

        t5=findViewById(R.id.branchMobile);

        Intent intent=getIntent();

        String roadNo=intent.getStringExtra("RoadNo");
        String thana=intent.getStringExtra("Thana");

        String mobile=intent.getStringExtra("Mobile");






        t2.setText("Road No: "+roadNo);
        t3.setText("Thana: "+thana);

        t5.setText("Mobile No:"+mobile);
    }
}