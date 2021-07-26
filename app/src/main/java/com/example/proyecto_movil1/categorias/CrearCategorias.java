package com.example.proyecto_movil1.categorias;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyecto_movil1.Categoria_Fragmento;
import com.example.proyecto_movil1.MainActivity;
import com.example.proyecto_movil1.R;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;


public class CrearCategorias extends Fragment {

    static final int RESULT_GALLERY_IMG = 100;
    private Bitmap photo = null;
    private ImageView imageView;
    private Button btn;
    private TextInputLayout textInputLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate( R.layout.fragment_crear_categorias, container, false );


        textInputLayout = viewGroup.findViewById( R.id.txtCategoriaName );

        imageView = viewGroup.findViewById( R.id.imgCategory );
        imageView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GaleriaImagenes();
            }
        } );

        btn = viewGroup.findViewById( R.id.btnregistrarcategoria );
        btn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(photo==null){
                    AlertaDialogo( "No ha seleccionado la foto","Sin foto" );
                }else{
                    if(textInputLayout.getEditText().getText().toString().trim().length()==0){
                        textInputLayout.setError( "Campo Obligatorio" );
                    }else{
                        textInputLayout.setError( null );
                        AlertaDialogo( "bien","ads" );
                    }
                }


            }
        } );

        return viewGroup;
    }

    private void GaleriaImagenes() {
        Intent photoPickerIntent = new Intent( Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
        startActivityForResult( photoPickerIntent, RESULT_GALLERY_IMG );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        Uri imageUri;
        if (resultCode == getActivity().RESULT_OK && requestCode == RESULT_GALLERY_IMG) {
            imageUri = data.getData();
            //uri = imageUri;
            imageView.setImageURI( imageUri );

            try {
                photo = MediaStore.Images.Media.getBitmap( getActivity().getContentResolver(), imageUri );
            } catch (Exception ex) {
            }
        }
    }

    private void AlertaDialogo(String mensaje, String title) {

        AlertDialog.Builder alerta = new AlertDialog.Builder( getContext() );
        alerta.setMessage( mensaje )
                .setCancelable( false )
                .setPositiveButton( "Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                } );

        AlertDialog titulo = alerta.create();
        titulo.setTitle( title );
        titulo.show();

    }

    public String GetStringImage(Bitmap imagen) {
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        imagen.compress( Bitmap.CompressFormat.JPEG, 100, ba );
        byte[] imagebyte = ba.toByteArray();
        String encode = Base64.encodeToString( imagebyte, Base64.DEFAULT );
        return "data:image/png;base64," + encode;
    }

    private void CrearCategoria()
    {

        /*
        String url = "http://167.99.158.191/Api_pedidos_ProyectoFinal/Usuarios/crear_usuarios.php";

        HashMap<String, String> params = new HashMap<String, String>();


        params.put("nombres", nombreCC.toLowerCase());
        params.put("telefonos", telefonoCC);
        params.put("direccion", direccionCC);
        params.put("usuario", usuarioCC);
        params.put("contrasenia", contraseniaCC);
        params.put("correo", correoCC);
        params.put("latitud", latitudCC);
        params.put("longitud", longitudCC);
        params.put("url_foto", GetStringImage( url_fotoCC ));


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



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest( url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AlertaDialogo( "Informacion Guardada Exitosamente!!!","Registro" );
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new Categoria_Fragmento()).commit();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // VolleyLog.d("Error", "Error: " + error.getMessage());
                AlertaDialogo( "Error al conectarse al servidor","Error" );
            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(jsonObjectRequest);

        */

    }

}