package com.example.proyecto_movil1.categorias;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyecto_movil1.Categoria_Fragmento;
import com.example.proyecto_movil1.R;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;


public class CrearProductos extends Fragment {


    static final int RESULT_GALLERY_IMG = 100;
    private Bitmap photo = null;
    private ImageView imageView;
    private Button btn;
    private Button btnGuardar;
    private TextInputLayout textoCode_Product,textoNombre,textoDescripcion,textoExistancia,textoPrecio;
    private Spinner combo;

    ArrayList<String> ListaCategoria;
    ArrayList<Modelo_Categoria> lista;

    private int PosicionCombo=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate( R.layout.fragment_crear_productos, container, false );

        textoCode_Product = viewGroup.findViewById( R.id.textoCode_Product );
        textoNombre = viewGroup.findViewById( R.id.textoNombre );
        textoDescripcion = viewGroup.findViewById( R.id.textoDescripcion );
        textoExistancia = viewGroup.findViewById( R.id.textoExistancia );
        textoPrecio = viewGroup.findViewById( R.id.textoPrecio );


        combo = viewGroup.findViewById( R.id.spinner );
        btnGuardar = viewGroup.findViewById( R.id.btnCrearPro );
        btnGuardar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CrearProductos();
            }
        } );

        imageView = viewGroup.findViewById( R.id.imgCrearProducts );
        imageView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GaleriaImagenes();
            }
        } );

        ObtenerCombo();

        combo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                PosicionCombo = position;
                //lista.get(position).getNombres();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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

    private void CrearProductos()
    {


        String url = "http://167.99.158.191/Api_pedidos_ProyectoFinal/Productos/crearProducto.php";

        HashMap<String, String> params = new HashMap<String, String>();


        params.put("id_categoria", lista.get( PosicionCombo ).getId());
        params.put("codigo_producto", textoCode_Product.getEditText().getText().toString());
        params.put("nombre", textoNombre.getEditText().getText().toString());
        params.put("descripcion", textoDescripcion.getEditText().getText().toString());
        params.put("existencia", textoExistancia.getEditText().getText().toString());
        params.put("precio_venta", textoPrecio.getEditText().getText().toString());
        params.put("url_foto", GetStringImage( photo ));


/*
        formato del json
        {

            "id_categoria":3,
            "codigo_producto":"465489465",
            "nombre":"algo",
            "descripcion":"para venta",
            "existencia":100,
            "precio_venta":35.96,
            "url_foto":"none"
        }

 */



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



    }

    private void ObtenerCombo(){


        String url = "http://167.99.158.191/Api_pedidos_ProyectoFinal/Categoria/getCategorias.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;

                Modelo_Categoria listModel = null;
                lista = new ArrayList<Modelo_Categoria>();

                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        listModel = new Modelo_Categoria();


                        if(jsonObject.getString( "activo" ).equals( "true" )){
                            listModel.setId( jsonObject.getString( "id_categoria" ) );
                            listModel.setDescripcion( jsonObject.getString( "descripcion" ) );
                        }




                        //listModel.setUrl( jsonObject.getString( "url_foto" ) );


                        lista.add(listModel);


                    } catch (JSONException e) {
                        Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }


                fillCombo();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(), "Error de conexion al buscar", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonArrayRequest);

    }

    private void fillCombo() {

        ListaCategoria = new ArrayList<String>();

        for(int i = 0; i<lista.size(); i++){
            ListaCategoria.add(lista.get(i).getDescripcion() ) ;
        }

        ArrayAdapter<CharSequence> adp = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item,ListaCategoria);
        combo.setAdapter(adp);

    }


}

