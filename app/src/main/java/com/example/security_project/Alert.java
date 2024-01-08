package com.example.security_project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Alert extends AppCompatActivity {

    Button start_btn,stop_btn;
    MediaPlayer mediaPlayer;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);

        start_btn=findViewById(R.id.start_btn);
        stop_btn=findViewById(R.id.stop_btn);

        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer==null)
                {
                    mediaPlayer=MediaPlayer.create(getApplicationContext(),R.raw.siren);
                    mediaPlayer.start();
                }
            }
        });

        stop_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer!=null)
                {
                    mediaPlayer.stop();
                    mediaPlayer=null;
                }
            }
        });
    }
}