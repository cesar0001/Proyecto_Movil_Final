package com.example.proyecto_movil1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyecto_movil1.Creditos.Creditos;
import com.example.proyecto_movil1.Pedidos.MisPedidos;
import com.example.proyecto_movil1.Usuarios.Modificar_Usuarios;
import com.example.proyecto_movil1.categorias.CrearProductos;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class DashBoard extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    SharedPreferences shp;
    SharedPreferences.Editor shpEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_dash_board );

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

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

                        /*
                        new Thread(new Runnable() {
                            public void run() {
                                try {
                                    Logout();
                                } catch (Exception e) {
                                    //e.printStackTrace();
                                    Toast.makeText( getApplicationContext(),"dashboard cerrar "+e.getMessage(),Toast.LENGTH_SHORT ).show();
                                }
                            }
                        }).start();

                         */
                        fragment= new Categoria_Fragmento();
                        Logout();

                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,fragment).commit();

                return true;
            }
        });



    }

    /*
    @Override
    protected void onStart() {
        super.onStart();
        CheckLogin();

    }

     */

    /*
    @Override
    protected void onResume() {
        super.onResume();
        shp = getSharedPreferences("myPreferences", MODE_PRIVATE);
        //CheckLogin();

    }

     */

    public void CheckLogin() {
        try {


            if (shp == null)
                shp = getSharedPreferences( "myPreferences", MODE_PRIVATE );


            String userName = shp.getString( "name", "" );
            String usuario = shp.getString( "guardar_usuario", "" );
            String contr = shp.getString( "guardar_contraseña", "" );

            if (userName != null && !userName.equals( "" )) {
                //metodo
                 buscarUsuario( usuario, contr );

            } else {
                Intent i = new Intent( DashBoard.this, MainActivity.class );
                startActivity( i );
                finish();
            }
        } catch (Exception e) {
            Toast.makeText( this, e.getMessage().toString(), Toast.LENGTH_LONG ).show();
        }

    }


    public void Logout() {
        try {
            if (shp == null)
                shp = getSharedPreferences( "myPreferences", MODE_PRIVATE );

            Categoria_Fragmento.Carrito_Compras.clear();


                shpEditor = shp.edit();
                shpEditor.putString( "name", "" );
                shpEditor.putString( "guardar_usuario", "" );
                shpEditor.putString( "guardar_contraseña", "" );
                shpEditor.commit();

                Intent i = new Intent( DashBoard.this, MainActivity.class );
                startActivity( i );
                finish();


            //shp = null;
            //shpEditor = null;


        } catch (Exception ex) {
            Toast.makeText( this, ex.getMessage().toString(), Toast.LENGTH_LONG ).show();
        }
    }

    private void buscarUsuario(String user,String passuser){


        String url = "http://167.99.158.191/Api_pedidos_ProyectoFinal/login.php";


        HashMap<String, String> params = new HashMap<String, String>();

        params.put("usuario",  user);
        params.put("contrasenia", passuser);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest( url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {


                        try {
                            //jsonObject = response.getJSONObject(i);
                            //snackbar( jsonObject.getString("correo" ) );
                            MainActivity.setNombre( jsonObject.getString( "nombres" )  );
                            MainActivity.setId_usuario( jsonObject.getString( "id_usuario" ) );
                            MainActivity.setTelefono( jsonObject.getString( "telefono" ) );
                            MainActivity.setLatitud( jsonObject.getString( "latitud" ) );
                            MainActivity.setLongitud( jsonObject.getString( "longitud" ) );
                            MainActivity.setDireccion( jsonObject.getString( "direccion" ) );
                            MainActivity.setUrl_foto( jsonObject.getString( "url_foto" ) );
                            MainActivity.setUsuario( jsonObject.getString( "usuario" ) );
                            MainActivity.setContraseña( jsonObject.getString( "contrasenia" ) );
                            MainActivity.setCorreo( jsonObject.getString( "correo" ) );
                            MainActivity.setTipo_usuario( jsonObject.getString( "descripcion" ) );

                            MainActivity.setLatitudGuardada( jsonObject.getString( "latitud" ) );
                            MainActivity.setLongitudGuardada( jsonObject.getString( "longitud" ) );

                            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new Categoria_Fragmento()).commit();


                        } catch (JSONException e) {
                             //Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }




                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

             }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);

    }

}