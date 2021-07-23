package com.example.proyecto_movil1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    TextInputLayout txtnombre, txtPassword;

    public static String nombre ="";
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

    CoordinatorLayout snackbar_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        txtnombre = (TextInputLayout) findViewById(R.id.txtNombreUsuario);
        txtPassword = (TextInputLayout) findViewById(R.id.txtPassword);

        snackbar_layout = (CoordinatorLayout)findViewById(R.id.snackbar_layout);

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
                Intent intent = new Intent(getApplicationContext(),CrearUsuarios.class);
                startActivity( intent );
            }
        });


    }



    public void clickRecuperarPassword(View view){
        Toast.makeText(getApplicationContext(),"Click en etiqueta olvidar contraseña",Toast.LENGTH_SHORT).show();
    }

    private void buscarUsuario(){

         String url = "http://167.99.158.191/pedidos/login.php?nombre="+txtnombre.getEditText().getText().toString() +
                 "&contra="+txtPassword.getEditText().getText().toString();

        //String url = "http://167.99.158.191/pedidos/login.php?nombre=jturcios&contra=123456";

         JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject = null;


                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject( i );
                        //Toast.makeText( getApplicationContext(), ""+jsonObject.getString("correo"),Toast.LENGTH_SHORT ).show();
                        setNombre( jsonObject.getString( "nombres" )  );
                        setTelefono( jsonObject.getString( "telefono" ) );
                        setLatitud( jsonObject.getString( "latitud" ) );
                        setLongitud( jsonObject.getString( "longitud" ) );
                        setDireccion( jsonObject.getString( "direccion" ) );
                        setUrl_foto( jsonObject.getString( "url_foto" ) );
                        setUsuario( jsonObject.getString( "usuario" ) );
                        setContraseña( jsonObject.getString( "contrasenia" ) );
                        setCorreo( jsonObject.getString( "correo" ) );
                        setTipo_usuario( jsonObject.getString( "descripcion" ) );


                    } catch (JSONException e) {
                        Toast.makeText( getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT ).show();
                    }
                }

                if (txtnombre.getEditText().getText().toString().equals( getUsuario() ) && txtPassword.getEditText().getText().toString().equals( getContraseña() )) {
                    snackbar( "Contraseña correcta" );
                } else {
                    snackbar( "Contraseña incorrecta" );
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error de conexion al buscar", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
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

    public static void setUrl_foto(String url_foto) {
        MainActivity.url_foto = url_foto;
    }
}