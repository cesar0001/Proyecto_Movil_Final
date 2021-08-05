package com.example.proyecto_movil1.Pedidos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyecto_movil1.ListAdapter;
import com.example.proyecto_movil1.R;
import com.example.proyecto_movil1.categorias.Modelo_Categoria;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class informacionPedidos extends Fragment {

    private List<Modelo_informacion_Pedidos_Productos> mLista = new ArrayList<>();
    private ListView mlistView;
    ListAdapterPedidosProductos mAdapter;

//xml se llama lista productos para adaptador
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate( R.layout.fragment_informacion_pedidos, container, false );
        mlistView = viewGroup.findViewById( R.id.ListaPedidosLlevados );



        return viewGroup;
    }

    private void BusquedadPedidosProductos() {

        String url = "http://167.99.158.191/Api_pedidos_ProyectoFinal/Categoria/getCategorias.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;

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

                        mLista.add(modelo);


                        mAdapter = new ListAdapterPedidosProductos(getActivity().getApplicationContext(), R.layout.item_row_lista_productos, mLista);

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
    }

}