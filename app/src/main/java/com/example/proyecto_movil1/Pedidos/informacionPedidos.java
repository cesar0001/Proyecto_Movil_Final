package com.example.proyecto_movil1.Pedidos;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
 import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyecto_movil1.MainActivity;
import com.example.proyecto_movil1.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class informacionPedidos extends Fragment {

    private List<Modelo_informacion_Pedidos_Productos> mLista = new ArrayList<>();
    private ListView mlistView;
    ListAdapterPedidosProductos mAdapter;

    private Button btnUbicacion;

    TextView total;

    public static String id_pedidos;
    public static String latitud_info;
    public static String longitud_info;

//xml se llama lista productos para adaptador
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate( R.layout.fragment_informacion_pedidos, container, false );
        mlistView = viewGroup.findViewById( R.id.ListaPedidosLlevados );
        total = viewGroup.findViewById( R.id.textoTotalIP );
        btnUbicacion = viewGroup.findViewById( R.id.btndelivery );


        //http://167.99.158.191/Api_pedidos_ProyectoFinal/pedidos/getDetallePedido.php
        //{"id_pedido":12}
        btnUbicacion.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String geoUri = "http://maps.google.com/maps?q=loc:" + latitud_info + "," + longitud_info + " (" + "My Ubicacion" + ")";
                Uri location = Uri.parse(geoUri);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
                startActivity(mapIntent);

            }
        } );

        /*
        if(!MainActivity.getTipo_usuario().equals( "Repartidor" )){
            btnUbicacion.setVisibility( View.GONE );
        }else{
            btnUbicacion.setVisibility( View.VISIBLE );
        }

         */

        BusquedadPedidosProductos();

        return viewGroup;
    }

    private void BusquedadPedidosProductos() {

        //mandar todo los productos por id del pedido


        String url = "http://167.99.158.191/Api_pedidos_ProyectoFinal/pedidos/getDetallePedido.php";


        JSONObject json = new JSONObject();
        try {
            json.put("id_pedido",id_pedidos);
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
            CustomJsonArrayRequestp jsonRequest = new CustomJsonArrayRequestp( Request.Method.POST, url, json,
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
                                        Modelo_informacion_Pedidos_Productos modelo = new Modelo_informacion_Pedidos_Productos();
                                        modelo.setNombre( jsonObject.getString( "nombre" ) );
                                        modelo.setCantidad(jsonObject.getString("cantidad"));
                                        modelo.setPrecio(jsonObject.getString("precio"));
                                        modelo.setUrl(jsonObject.getString("url_foto"));
                                        latitud_info = jsonObject.getString( "latitud" );
                                        longitud_info = jsonObject.getString( "longitud" );
                                        total.setText( jsonObject.getString( "total_pedido" ) +" Lps." );

                                        mLista.add(modelo);


                                        mAdapter = new ListAdapterPedidosProductos(getActivity().getApplicationContext(), R.layout.item_row_lista_productos, mLista);

                                        mlistView.setAdapter(mAdapter);

                                    } catch (JSONException e) {
                                        Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                            } else { //If the response is not valid, the request also failed
                               // Toast.makeText(getActivity().getApplicationContext(), "Sin Datos", Toast.LENGTH_SHORT).show();

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

}

class CustomJsonArrayRequestp extends JsonRequest<JSONArray> {
    public CustomJsonArrayRequestp(int method, String url, JSONObject jsonRequest, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) {
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