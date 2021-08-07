package com.example.proyecto_movil1.Tranking;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
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
 import com.example.proyecto_movil1.Pedidos.ListAdapterMisPedidos;
 import com.example.proyecto_movil1.Pedidos.Modelo_MisPedidos;
import com.example.proyecto_movil1.Pedidos.informacionPedidos;
import com.example.proyecto_movil1.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class FragmentoPedidosRepartidor extends Fragment {

    private List<Modelo_MisPedidos> mLista = new ArrayList<>();
    private ListView mlistView;
    ListAdapterMisPedidos mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate( R.layout.fragment_fragmento_pedidos_repartidor, container, false );

        mlistView = viewGroup.findViewById( R.id.ListaPedidosReparti );

        mlistView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                informacionPedidos.id_pedidos = mLista.get( position ).getPedido();
                //informacionPedidos.latitud_info = mLista.get( position ).getLatitud();
                //informacionPedidos.longitud_info = mLista.get( position ).getLongitud();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new informacionPedidos()).commit();


            }
        } );

        ListaPedidos();

        return viewGroup;
    }


    private void ListaPedidos(){
        /*
        String url = "http://167.99.158.191/Api_pedidos_ProyectoFinal/pedidos/getPedidosByRepartidor.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;

                mLista = new ArrayList<>();
                //ListAdapter mAdapter;

                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        Modelo_Repartidor modelo = new Modelo_Repartidor();
                        modelo.setNombre( jsonObject.getString( " " ) );
                        modelo.setFecha(jsonObject.getString(" "));
                        modelo.setStatus(jsonObject.getString(""));

                        mLista.add(modelo);

                        mAdapter = new ListAdapterPedidosRepartidor(getActivity().getApplicationContext(), R.layout.item_row_pedidos_repartidor, mLista);

                        mlistView.setAdapter(mAdapter);

                    } catch (JSONException e) {
                        Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(), "Error de conexion al buscar", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonArrayRequest);

         */

        String url = "http://167.99.158.191/Api_pedidos_ProyectoFinal/pedidos/getPedidosByRepartidor.php";


        JSONObject json = new JSONObject();
        try {
            json.put("id_usuario", MainActivity.getUsuario() );
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
            CustomJsonArrayRequestA jsonRequest = new CustomJsonArrayRequestA( Request.Method.POST, url, json,
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

                                        Modelo_MisPedidos modelo = new Modelo_MisPedidos();

                                        modelo.setPedido( jsonObject.getString( "id_pedido" ) );
                                        modelo.setFecha( jsonObject.getString( "fecha" ) );
                                        modelo.setStatus(jsonObject.getString("descripcion"));

                                        mLista.add(modelo);


                                        mAdapter = new ListAdapterMisPedidos(getActivity().getApplicationContext(), R.layout.item_row_mis_pedidos, mLista);

                                        mlistView.setAdapter(mAdapter);

                                    } catch (JSONException e) {
                                       // Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                            } else { //If the response is not valid, the request also failed
                              //  Toast.makeText(getActivity().getApplicationContext(), "Sin Datos", Toast.LENGTH_SHORT).show();

                                //Log.e("ErrorOnRequest", "The server responded correctly, but with an empty array!");
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //Toast.makeText(getActivity().getApplicationContext(), "onErrorResponse "+error.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
            );
            // Add the request to the RequestQueue.
            queue.add(jsonRequest);
        }



    }


}


class CustomJsonArrayRequestA extends JsonRequest<JSONArray> {
    public CustomJsonArrayRequestA(int method, String url, JSONObject jsonRequest, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) {
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