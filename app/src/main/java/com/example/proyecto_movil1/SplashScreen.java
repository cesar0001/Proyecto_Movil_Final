package com.example.proyecto_movil1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splash_screen );

        getSupportActionBar().hide();

        TimerTask timer = new TimerTask() {
            @Override
            public void run() {
                Intent pantalla = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(pantalla);
                finish();
            }
        };

        Timer tiempo = new Timer();
        tiempo.schedule(timer,2000);

    }
}