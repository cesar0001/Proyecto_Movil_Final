package com.example.proyecto_movil1.Tranking;

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
 import com.example.proyecto_movil1.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentoPedidosRepartidor extends Fragment {

    private List<Modelo_Repartidor> mLista = new ArrayList<>();
    private ListView mlistView;
    ListAdapterPedidosRepartidor mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate( R.layout.fragment_fragmento_pedidos_repartidor, container, false );

        mlistView = viewGroup.findViewById( R.id.ListaPedidosReparti );


        return viewGroup;
    }


    private void ListaPedidos(){
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
    }


}