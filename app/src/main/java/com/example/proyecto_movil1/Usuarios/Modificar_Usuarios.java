package com.example.proyecto_movil1.Usuarios;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyecto_movil1.Categoria_Fragmento;

 import com.example.proyecto_movil1.MainActivity;
import com.example.proyecto_movil1.R;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;


public class Modificar_Usuarios extends Fragment {

    private TextInputLayout txtuser, txttelefono,txtdireccion,txtcontra,txtcorreo;
    private Button btn;

    static final int RESULT_GALLERY_IMG1 = 1000;
    private Bitmap photo = null;
    private ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate( R.layout.fragment_modificar__usuarios, container, false );

        //imageView = viewGroup.findViewById( R.id.imgModificarA );
        txtuser = viewGroup.findViewById( R.id.txtMuser );
        txttelefono = viewGroup.findViewById( R.id.txttelefonoModi );
        txtdireccion = viewGroup.findViewById( R.id.txtDireccionModi );
        txtcontra = viewGroup.findViewById( R.id.contraPassUser );
        txtcorreo = viewGroup.findViewById( R.id.CorreoCUNModifi );


        imageView = viewGroup.findViewById( R.id.imgModificarA );
        imageView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText( getContext(),"sa",Toast.LENGTH_SHORT ).show();
                 GaleriaImagenes();
            }
        } );

        btn= viewGroup.findViewById( R.id.buttonNextModific );
        btn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validacion();
            }
        } );

        llenarCampos();


        return viewGroup;
    }

    public String GetStringImage(Bitmap imagen) {
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        imagen.compress( Bitmap.CompressFormat.JPEG, 100, ba );
        byte[] imagebyte = ba.toByteArray();
        String encode = Base64.encodeToString( imagebyte, Base64.DEFAULT );
        return "data:image/png;base64," + encode;
    }

    private void llenarCampos(){

        Picasso.with(getContext()).load(MainActivity.getUrl_foto()).into(imageView);
        txtuser.getEditText().setText( MainActivity.getNombre() );
        txttelefono.getEditText().setText( MainActivity.getTelefono() );
        txtdireccion.getEditText().setText( MainActivity.getDireccion() );
        txtcontra.getEditText().setText( MainActivity.getContraseña() );
        txtcorreo.getEditText().setText( MainActivity.getCorreo() );


    }

    public void validacion(){
        if (photo == null) {
            AlertaDialogo( "Ingrese la foto","Foto" );
            photo = null;
        } else {
            if (txtuser.getEditText().getText().toString().trim().length() == 0) {
                txtuser.setError( "Ingrese este campo" );
            } else {
                txtuser.setError( null );
                if (txttelefono.getEditText().getText().toString().trim().length() == 0) {
                    txttelefono.setError( "Ingrese este campo" );
                } else {
                    txttelefono.setError( null );
                    if (txtdireccion.getEditText().getText().toString().trim().length() == 0) {
                        txtdireccion.setError( "Ingrese este campo" );
                    } else {
                        txtdireccion.setError( null );
                        if (txtcontra.getEditText().getText().toString().trim().length() == 0) {
                            txtcontra.setError( "Ingrese este campo" );
                        } else {
                            txtcontra.setError( null );
                            if (txtcorreo.getEditText().getText().toString().trim().length() == 0) {
                                txtcorreo.setError( "Ingrese este campo" );
                            } else {
                                txtcorreo.setError( null );
                                ModificarUsuarios();

                            }


                        }
                    }
                }
            }
        }
    }




    private void AlertaDialogo(String mensaje, String title) {

        AlertDialog.Builder alerta = new AlertDialog.Builder( getActivity() );
        alerta.setMessage( mensaje )
                .setCancelable( false )
                .setPositiveButton( "Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(photo!=null){
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new Categoria_Fragmento()).commit();
                            dialog.cancel();
                        }

                    }
                } );

        AlertDialog titulo = alerta.create();
        titulo.setTitle( title );
        titulo.show();

    }

    private void GaleriaImagenes() {
        Intent photoPickerIntent = new Intent( Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
        startActivityForResult( photoPickerIntent, RESULT_GALLERY_IMG1 );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );

        if (resultCode == getActivity().RESULT_OK && requestCode == RESULT_GALLERY_IMG1) {
            Uri imageUri;
            imageUri = data.getData();
            //uri = imageUri;
            imageView.setImageURI( imageUri );

            try {
                photo = MediaStore.Images.Media.getBitmap( getActivity().getContentResolver(), imageUri );
            } catch (Exception ex) {
                Toast.makeText( getContext(),ex.getMessage(),Toast.LENGTH_SHORT ).show();
            }
        }
    }

    private void ModificarUsuarios(){

        String url = "http://167.99.158.191/Api_pedidos_ProyectoFinal//Usuarios/updateUser.php";

        HashMap<String, String> params = new HashMap<String, String>();

        /*
        {
            "nombres": "CAPITAN KIKO",
                "telefonos": "88888888",
                "latitud": "99999999",
                "longitud": "CHOLUTECA",
                "url_foto": "",
                "correo":"",
                "direccion":"",
                "id_usuario":25,
                "contrasenia":"messi"
        }

         */
        String variable = GetStringImage( photo );

        params.put("nombres", txtuser.getEditText().getText().toString().toLowerCase());
        params.put("telefonos", txttelefono.getEditText().getText().toString());
        params.put("latitud", MainActivity.getLatitud());
        params.put("longitud", MainActivity.getLongitud());
        params.put("url_foto", variable);
        params.put("correo", txtcorreo.getEditText().getText().toString());
        params.put("direccion", txtdireccion.getEditText().getText().toString());
        params.put("id_usuario", MainActivity.getId_usuario());
        params.put("contrasenia", txtcontra.getEditText().getText().toString());

        /*
        formato del json
        {
            "nombres":"nombres",
                "telefonos":"telefonos",
                "direccion":"direccion",
                "usuario":"usuario",
                "contrasenia":"contrasenia",
                "correo":"correo",
                "url_foto":""
        }

         */

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest( url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        ActualizarDatosDelUsuario();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // VolleyLog.d("Error", "Error: " + error.getMessage());
                AlertaDialogo( "Error al conectarse al servidor","Error" );
            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonObjectRequest);

    }

    private void ActualizarDatosDelUsuario(){


        String url = "http://167.99.158.191/Api_pedidos_ProyectoFinal/login.php";


        HashMap<String, String> params = new HashMap<String, String>();

        params.put("usuario", MainActivity.getUsuario());
        params.put("contrasenia", MainActivity.getContraseña());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest( url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {


                        try {


                            MainActivity.setLatitudGuardada( jsonObject.getString( "latitud" ) );
                            MainActivity.setLongitudGuardada( jsonObject.getString( "longitud" ) );
                            MainActivity.setNombre(  jsonObject.getString( "nombres" ) );

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


                            AlertaDialogo( "Informacion Modificada Exitosamente!!!","Registro" );

                        } catch (JSONException e) {
                            //snackbar( "Contraseña incorrecta" );
                            //Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }




                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

             }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonObjectRequest);

    }


}