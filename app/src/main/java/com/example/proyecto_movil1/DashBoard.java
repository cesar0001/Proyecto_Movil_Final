package com.example.proyecto_movil1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.proyecto_movil1.Creditos.Creditos;
import com.example.proyecto_movil1.Pedidos.MisPedidos;
import com.example.proyecto_movil1.Usuarios.Modificar_Usuarios;
import com.example.proyecto_movil1.categorias.CrearProductos;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

public class DashBoard extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    SharedPreferences shp;
    SharedPreferences.Editor shpEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_dash_board );

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new Categoria_Fragmento()).commit();

        shp = getSharedPreferences("myPreferences", MODE_PRIVATE);
        CheckLogin();

        bottomNavigationView = findViewById(R.id.ButtonNavigation);;
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {

                Fragment fragment = null;

                switch (item.getItemId()){
                    case R.id.item1:
                        fragment= new Categoria_Fragmento();
                        break;
                    case R.id.item2:
                        fragment= new Creditos();
                        break;
                    case R.id.item3:
                        fragment= new Modificar_Usuarios();
                        break;
                    case R.id.item4:
                        fragment= new MisPedidos();
                        break;

                    case R.id.cerrar_sesion:
                        Logout();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,fragment).commit();

                return true;
            }
        });


    }

    public void CheckLogin() {
        if (shp == null)
            shp = getSharedPreferences("myPreferences", MODE_PRIVATE);


        String userName = shp.getString("name", "");

        if (userName != null && !userName.equals("")) {

        } else
        {
            Intent i = new Intent(DashBoard.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }
    public void Logout() {
        try {
            if (shp == null)
                shp = getSharedPreferences("myPreferences", MODE_PRIVATE);

            shpEditor = shp.edit();
            shpEditor.putString("name", "");
            shpEditor.commit();

            Intent i = new Intent(DashBoard.this, MainActivity.class);
            startActivity(i);
            finish();

        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

}