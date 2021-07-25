package com.example.proyecto_movil1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

public class DashBoard extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_dash_board );

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new Home_Fragmento()).commit();


        bottomNavigationView = findViewById(R.id.ButtonNavigation);;
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {

                Fragment fragment = null;

                switch (item.getItemId()){
                    case R.id.item1:
                        fragment= new Home_Fragmento();
                        break;
                    case R.id.item2:
                        fragment= new Categoria_Fragmento();
                        break;
                    case R.id.item3:
                        fragment= new Home_Fragmento();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,fragment).commit();

                return true;
            }
        });


    }
}