package com.example.proyecto_movil1.categorias;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.example.proyecto_movil1.R;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MostrarProductos extends Fragment  {

    public static String id_Categoria,descripcion_categoria;

    private List<Modelo_Productos> mLista = new ArrayList<>();
    private ListView mlistView;
    private ListAdapterProductos mAdapter;
    TextView mensaje;
    TextInputLayout buscar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup)  inflater.inflate( R.layout.fragment_mostrar_productos, container, false );

        mlistView = viewGroup.findViewById( R.id.listProductos );
        mensaje = viewGroup.findViewById( R.id.msjCategoria );
        mensaje.setText( "Tabla Categorias de " + descripcion_categoria );
        buscar = viewGroup.findViewById( R.id.txtBuscadorProductos );


        mlistView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText( getContext(),"d32",Toast.LENGTH_SHORT ).show();

                detalles_Productos.setNombre_pro( mLista.get( position ).getNombre() );
                detalles_Productos.setPrecio_pro( mLista.get( position ).getPrecio_venta() );
                //
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new detalles_Productos()).commit();


            }
        } );

        buscar.getEditText().addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().trim().length() == 0) {
                    BusquedadProductos( getId_Categoria(), "" );
                } else {
                    BusquedadProductos( getId_Categoria(), s.toString() );
                }

            }
        } );


        BusquedadProductos( getId_Categoria(), "" );


        return viewGroup;
    }

    private void BusquedadProductos(String Categoria,String nombre) {


        String url = "http://167.99.158.191/Api_pedidos_ProyectoFinal/Productos/searchProducto.php";


        JSONObject json = new JSONObject();
        try {
            json.put("id_categoria",Categoria);
            json.put("nombre", nombre);
         } catch( JSONException e){
            Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

            e.printStackTrace();
            json = null; //GC this
        }

//checkConnection is a custom method
        if (json != null ) {
            // Instantiate the RequestQueue
            RequestQueue queue = Volley.newRequestQueue(getActivity());

            // Request a string response from the provided URL.
            CustomJsonArrayRequest jsonRequest = new CustomJsonArrayRequest(Request.Method.POST, url, json,
                    new Response.Listener<JSONArray>(){
                        @Override
                        public void onResponse(JSONArray response) {
                            if (response != null && response.length() > 0) { //If the response is valid

                                JSONObject jsonObject = null;

                                //Toast.makeText(getActivity().getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                                mLista = new ArrayList<>();
                                //ListAdapter mAdapter;

                                for (int i = 0; i < response.length(); i++) {
                                    try {
                                        jsonObject = response.getJSONObject(i);

                                        Modelo_Productos modelo = new Modelo_Productos();

                                        modelo.setId_producto( jsonObject.getString( "id_producto" ) );
                                        modelo.setNombre( jsonObject.getString( "nombre" ) );
                                        modelo.setDescripcion( jsonObject.getString("descripcion"));
                                        modelo.setPrecio_venta( jsonObject.getString( "precio_venta" ) );
                                        modelo.setUrl(jsonObject.getString("url_foto"));

                                        mLista.add(modelo);


                                        mAdapter = new ListAdapterProductos(getActivity().getApplicationContext(), R.layout.item_row_productos, mLista);

                                        mlistView.setAdapter(mAdapter);

                                    } catch (JSONException e) {
                                        Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                            } else { //If the response is not valid, the request also failed
                                Toast.makeText(getActivity().getApplicationContext(), "array vacio", Toast.LENGTH_SHORT).show();

                                //Log.e("ErrorOnRequest", "The server responded correctly, but with an empty array!");
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getActivity().getApplicationContext(), "onErrorResponse "+error.getMessage(), Toast.LENGTH_SHORT).show();

                         }
                    }
            );
            // Add the request to the RequestQueue.
            queue.add(jsonRequest);
        }

    }


    public static String getId_Categoria() {
        return id_Categoria;
    }

    public static void setId_Categoria(String id_Categoria) {
        MostrarProductos.id_Categoria = id_Categoria;
    }

    public static String getDescripcion_categoria() {
        return descripcion_categoria;
    }

    public static void setDescripcion_categoria(String descripcion_categoria) {
        MostrarProductos.descripcion_categoria = descripcion_categoria;
    }




}
// fin de la clase principal

class CustomJsonArrayRequest extends JsonRequest<JSONArray> {
    public CustomJsonArrayRequest(int method, String url, JSONObject jsonRequest, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) {
        super(method, url, (jsonRequest == null) ? null : jsonRequest.toString(), listener, errorListener);
    }

    @Override
    protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
            return Response.success(new JSONArray(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }



}