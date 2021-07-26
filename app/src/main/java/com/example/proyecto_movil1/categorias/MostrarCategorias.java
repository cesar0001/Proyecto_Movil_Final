package com.example.proyecto_movil1.categorias;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyecto_movil1.ListAdapter;
import com.example.proyecto_movil1.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MostrarCategorias extends Fragment implements AdapterView.OnItemClickListener{

    private List<Modelo_Categoria> mLista = new ArrayList<>();
    private ListView mlistView;
    ListAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate( R.layout.fragment_mostrar_categorias, container, false );

        mlistView = viewGroup.findViewById( R.id.listview );
        mlistView.setOnItemClickListener(this);

        BusquedadCategoria();

        return viewGroup;
    }

    private void BusquedadCategoria() {

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
                        Modelo_Categoria modelo = new Modelo_Categoria();
                        modelo.setId( jsonObject.getString( "id_categoria" ) );
                        modelo.setDescripcion(jsonObject.getString("descripcion"));
                        modelo.setUrl(jsonObject.getString("url_foto"));

                        mLista.add(modelo);


                        mAdapter = new ListAdapter(getActivity().getApplicationContext(), R.layout.item_row, mLista);

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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        //http://167.99.158.191/Api_pedidos_ProyectoFinal/Productos/searchProducto.php
        //{"id_categoria":2, "nombre": "frijoles"}
        //{"id_categoria":2, "nombre": ""} muestra todo


        /*
        Intent intent = new Intent(getApplicationContext(), SegundoActividad.class);
        intent.putExtra("id", mLista.get(position).getId());
        intent.putExtra("nombre", mLista.get(position).getNombre());
        intent.putExtra("telefono", mLista.get(position).getTelefono());
        intent.putExtra("url", mLista.get(position).getUrl());
        intent.putExtra("latitud", mLista.get(position).getLatitud());
        intent.putExtra("longitud", mLista.get(position).getLongitud());
        startActivityForResult(intent, resultado);

         */


    }


}