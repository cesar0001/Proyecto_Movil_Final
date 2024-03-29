package com.example.proyecto_movil1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyecto_movil1.Usuarios.Usuarios;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    SharedPreferences shp;
    SharedPreferences.Editor shpEditor;

    TextInputLayout txtnombre, txtPassword;

    public static String nombre ="";
    public static String id_usuario ="";
    public static String telefono ="";
    public static String latitud ="";
    public static String longitud ="";
    public static String direccion ="";
    public static String usuario ="";
    public static String contraseña ="";
    public static String correo ="";
    public static String activo ="";
    public static String tipo_usuario ="";
    public static String url_foto ="";

    public static String LatitudGuardada = "";
    public static String LongitudGuardada = "";

    CoordinatorLayout snackbar_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        } else {
            locationStart();
        }

        txtnombre = (TextInputLayout) findViewById(R.id.txtNombreUsuario);
        txtPassword = (TextInputLayout) findViewById(R.id.txtPassword);

        snackbar_layout = (CoordinatorLayout)findViewById(R.id.snackbar_layout1);



        findViewById(R.id.btnIniciarSesion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //obtenemos que el cambio este vacio para que mande un mensaje que esta vacio
                 if (txtnombre.getEditText().getText().toString().trim().length()==0){
                    txtnombre.setHelperText("Este campo es Obligatorio");
                }else{
                    txtnombre.setHelperText(null);
                 }

                if (txtPassword.getEditText().getText().toString().trim().length()==0){
                    txtPassword.setHelperText("Este campo es Obligatorio");
                }else{
                    txtPassword.setHelperText(null);
                 }


                if(txtnombre.getEditText().getText().toString().trim().length() != 0 &&
                   txtPassword.getEditText().getText().toString().trim().length() !=0){
                    //Toast.makeText(getApplicationContext(),"Se ha ingresado",Toast.LENGTH_SHORT).show();
                    buscarUsuario();
                }else{
                    Toast.makeText(getApplicationContext(),"Campos nullos",Toast.LENGTH_SHORT).show();

                }


            }
        });

        findViewById(R.id.btncrearU).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateUsers.class);
                startActivity( intent );
            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();
        shp = getSharedPreferences("myPreferences", MODE_PRIVATE);
        CheckLogin();

    }


    private void CheckLogin() {

        try {


            if (shp == null)
                shp = getSharedPreferences( "myPreferences", MODE_PRIVATE );

            String userName = shp.getString( "name", "" );
            String usuario = shp.getString( "guardar_usuario", "" );
            String contr = shp.getString( "guardar_contraseña", "" );

            if (userName != null && !userName.equals( "" ) ) {

                Intent i = new Intent( this, DashBoard.class );
                startActivity( i );
                finish();
            }

        } catch (Exception e) {
            Toast.makeText( this, "MainActivity " + e.getMessage().toString(), Toast.LENGTH_LONG ).show();
        }

    }

    private void DoLogin(String userid) {
        try {
            //if (password.equals("12")) {
                if (shp == null)
                    shp = getSharedPreferences("myPreferences", MODE_PRIVATE);

                shpEditor = shp.edit();
                shpEditor.putString("name", userid);
                shpEditor.putString("guardar_usuario", getUsuario());
                shpEditor.putString("guardar_contraseña", getContraseña());
                shpEditor.commit();

                Intent i = new Intent(this, DashBoard.class);
                startActivity(i);
                finish();
            //} else {
                //txtInfo.setText("Invalid Credentails");
            //}
        } catch (Exception ex) {
            //txtInfo.setText(ex.getMessage().toString());
            Toast.makeText( this, "MainActivity DoLogin" + ex.getMessage().toString(), Toast.LENGTH_LONG ).show();

        }
    }


    public void clickRecuperarPassword(View view){
        Intent intent = new Intent(getApplicationContext(), Usuarios.class );
        startActivity( intent );
    }



    private void buscarUsuario(){


        String url = "http://167.99.158.191/Api_pedidos_ProyectoFinal/login.php";


        HashMap<String, String> params = new HashMap<String, String>();

        params.put("usuario", txtnombre.getEditText().getText().toString().toLowerCase());
        params.put("contrasenia", txtPassword.getEditText().getText().toString());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest( url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {


                        try {
                            //jsonObject = response.getJSONObject(i);
                            //snackbar( jsonObject.getString("correo" ) );
                            setNombre( jsonObject.getString( "nombres" )  );
                            setId_usuario( jsonObject.getString( "id_usuario" ) );
                            setTelefono( jsonObject.getString( "telefono" ) );
                            setLatitud( jsonObject.getString( "latitud" ) );
                            setLongitud( jsonObject.getString( "longitud" ) );
                            setDireccion( jsonObject.getString( "direccion" ) );
                            setUrl_foto( jsonObject.getString( "url_foto" ) );
                            setUsuario( jsonObject.getString( "usuario" ) );
                            setContraseña( jsonObject.getString( "contrasenia" ) );
                            setCorreo( jsonObject.getString( "correo" ) );
                            setTipo_usuario( jsonObject.getString( "descripcion" ) );

                            setLatitudGuardada( jsonObject.getString( "latitud" ) );
                            setLongitudGuardada( jsonObject.getString( "longitud" ) );

                            if (txtnombre.getEditText().getText().toString().equals( getUsuario() ) && txtPassword.getEditText().getText().toString().equals( getContraseña() )) {
                                snackbar( "Contraseña correcta" );

                                DoLogin(txtnombre.getEditText().getText().toString());
                                //Intent i = new Intent(MainActivity.this,DashBoard.class);
                                //startActivity( i );
                                //finish();
                            } else {
                                snackbar( "Contraseña incorrecta" );
                            }


                        } catch (JSONException e) {
                            snackbar( "Contraseña incorrecta" );
                            //Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }




                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                snackbar(  "Error qui: " + error.getMessage());
            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);

    }

    public void snackbar(String mensaje){
        //Snackbar snackbar = Snackbar.make(v,"Este es ejemplo Snackbar",Snackbar.LENGTH_LONG);
        //puede ser v de View pero se pone CoordinatorLayout tanto en  el layout
        Snackbar snackbar = Snackbar.make(snackbar_layout,mensaje,Snackbar.LENGTH_LONG);

        //setAnchorView con esto hace que el snackbar se muestre mas arriba y no moleste en el
        //componente
        //snackbar.setAnchorView(floatingActionButton);
        snackbar.setDuration(4000);
        snackbar.setAnimationMode( BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
        /*
        snackbar.setAction("Okey", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

         */
        snackbar.show();
    }


    public static String getNombre() {
        return nombre;
    }

    public static void setNombre(String nombre) {
        MainActivity.nombre = nombre;
    }

    public static String getTelefono() {
        return telefono;
    }

    public static void setTelefono(String telefono) {
        MainActivity.telefono = telefono;
    }

    public static String getLatitud() {
        return latitud;
    }

    public static void setLatitud(String latitud) {
        MainActivity.latitud = latitud;
    }

    public static String getLongitud() {
        return longitud;
    }

    public static void setLongitud(String longitud) {
        MainActivity.longitud = longitud;
    }

    public static String getDireccion() {
        return direccion;
    }

    public static void setDireccion(String direccion) {
        MainActivity.direccion = direccion;
    }

    public static String getUsuario() {
        return usuario;
    }

    public static void setUsuario(String usuario) {
        MainActivity.usuario = usuario;
    }

    public static String getContraseña() {
        return contraseña;
    }

    public static void setContraseña(String contraseña) {
        MainActivity.contraseña = contraseña;
    }

    public static String getCorreo() {
        return correo;
    }

    public static void setCorreo(String correo) {
        MainActivity.correo = correo;
    }

    public static String getActivo() {
        return activo;
    }

    public static void setActivo(String activo) {
        MainActivity.activo = activo;
    }

    public static String getTipo_usuario() {
        return tipo_usuario;
    }

    public static void setTipo_usuario(String tipo_usuario) {
        MainActivity.tipo_usuario = tipo_usuario;
    }

    public static String getUrl_foto() {
        return url_foto;
    }

    public static String getId_usuario() {
        return id_usuario;
    }

    public static String getLatitudGuardada() {
        return LatitudGuardada;
    }

    public static void setLatitudGuardada(String latitudGuardada) {
        LatitudGuardada = latitudGuardada;
    }

    public static String getLongitudGuardada() {
        return LongitudGuardada;
    }

    public static void setLongitudGuardada(String longitudGuardada) {
        LongitudGuardada = longitudGuardada;
    }

    public static void setId_usuario(String id_usuario) {
        MainActivity.id_usuario = id_usuario;
    }

    public static void setUrl_foto(String url_foto) {
        MainActivity.url_foto = url_foto;
    }


    private void locationStart() {
        LocationManager mlocManager = (LocationManager) getSystemService( Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
        Local.setMainActivity(this);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) Local);


    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationStart();
                return;
            }
        }
    }

    public void setLocation(Location loc) {
        //Obtener la direccion de la calle a partir de la latitud y la longitud
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /* Aqui empieza la Clase Localizacion */
    public class Localizacion implements LocationListener {
        MainActivity mainActivity;

        public MainActivity getMainActivity() {
            return mainActivity;
        }

        public void setMainActivity(MainActivity mainActivity) {
            this.mainActivity = mainActivity;
        }

        @Override
        public void onLocationChanged(Location loc) {
            // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
            // debido a la deteccion de un cambio de ubicacion

            loc.getLatitude();
            loc.getLongitude();



            MainActivity.setLatitud(loc.getLatitude()+"");
            MainActivity.setLongitud(loc.getLongitude()+"");


            this.mainActivity.setLocation(loc);
        }

        @Override
        public void onProviderDisabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es desactivado

        }

        @Override
        public void onProviderEnabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es activado

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case LocationProvider.AVAILABLE:
                    Log.d("debug", "LocationProvider.AVAILABLE");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                    break;
            }
        }
    }
    ///

}