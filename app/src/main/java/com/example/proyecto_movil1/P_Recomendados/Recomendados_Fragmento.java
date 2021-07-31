package com.example.proyecto_movil1.P_Recomendados;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RatingBar;
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


public class Recomendados_Fragmento extends Fragment {

    private List<Modelo_Recomendados> mLista = new ArrayList<>();
    private ListView mlistView;
    ListAdapterProductosRecomendados mAdapter;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate( R.layout.fragment_recomendados_fragmento, container, false );

        mlistView = viewGroup.findViewById( R.id.listaProductosRecomendados );
        //mlistView.setOnItemClickListener(this);


        MostrarProductosRecomendados();

        return viewGroup;
    }


    private void MostrarProductosRecomendados(){

        String url = "http://167.99.158.191/Api_pedidos_ProyectoFinal/Productos/getRatingProductos.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;

                mLista = new ArrayList<>();
                //ListAdapter mAdapter;

                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);

                        Modelo_Recomendados modelo = new Modelo_Recomendados();
                        modelo.setRatingBar( jsonObject.getString( "rating" ) );
                        modelo.setUrl_photo( jsonObject.getString( "url_foto" ) );
                        modelo.setCategoria( jsonObject.getString( "descripcion" ) );
                        modelo.setProducto( jsonObject.getString( "nombre" ) );


                        mLista.add(modelo);


                        mAdapter = new ListAdapterProductosRecomendados(getActivity().getApplicationContext(), R.layout.item_row_productos_recomendados, mLista);

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