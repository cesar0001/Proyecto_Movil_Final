package com.example.proyecto_movil1.Pedidos;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyecto_movil1.Categoria_Fragmento;
import com.example.proyecto_movil1.MainActivity;
import com.example.proyecto_movil1.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class informacionPedidos extends Fragment {

    private List<Modelo_informacion_Pedidos_Productos> mLista = new ArrayList<>();
    private ListView mlistView;
    ListAdapterPedidosProductos mAdapter;

    private Button btnUbicacion;


    TextView total;

    public static String id_pedidos;
    public static String latitud_info;
    public static String longitud_info;
    public static String status_pedido;
    public static String nombre_pedido;
    public static String correo_pedido;
    public static String telefono_pedido;
    public static String direccion_pedido;
    public static String url_pedido;


    String calificador="";
    AlertDialog dialogA;
    AlertDialog dialogInforDetalle;

    TextView can;

//xml se llama lista productos para adaptador
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate( R.layout.fragment_informacion_pedidos, container, false );
        mlistView = viewGroup.findViewById( R.id.ListaPedidosLlevados );
        total = viewGroup.findViewById( R.id.textoTotalIP );
        btnUbicacion = viewGroup.findViewById( R.id.btndelivery );

        /*
        can = viewGroup.findViewById( R.id.cancelarPedido );

        if(MainActivity.getTipo_usuario().equals( "Repartidor" )){
            can.setText( "Entregar pedido" );

        }else{
            //can.setBackgroundColor( Color.parseColor("#5EA796") );
            if(status_pedido.equals( "ENTREGADO" )){
                can.setText( "Calificar Pedido" );
            }else{
                can.setBackgroundColor( Color.parseColor("#5EA796") );
                can.setText( "" );
            }

        }

        can.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText( getContext(),"asd",Toast.LENGTH_SHORT ).show();
                if(can.getText().equals( "Entregar pedido" )){
                    Entregar();
                }

                if(status_pedido.equals( "ENTREGADO" )){
                    if(can.getText().equals( "Calificar Pedido" )){
                        Rating();
                    }
                }


            }
        } );

         */

        //http://167.99.158.191/Api_pedidos_ProyectoFinal/pedidos/getDetallePedido.php
        //{"id_pedido":12}
        btnUbicacion.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                String geoUri = "http://maps.google.com/maps?q=loc:" + latitud_info + "," + longitud_info + " (" + "My Ubicacion" + ")";
                Uri location = Uri.parse(geoUri);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
                startActivity(mapIntent);

                 */


                OpcionesParaUsuarios();
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


    private void Rating(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable( false );
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_dialog,null);
        builder.setView(view);

        dialogA = builder.create();
        dialogA.show();



        Button btnAc = view.findViewById(R.id.btnUbicacionMiPerfil );
        btnAc.setText("Evaluacion");
        btnAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(),"Click en aceptar ",Toast.LENGTH_SHORT).show();
                CalificarPedido();

            }
        });


        Button btnCa = view.findViewById(R.id.btncancelarR );
        btnCa.setText("Cancelar");
        btnCa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(),"Click en Cancelar",Toast.LENGTH_SHORT).show();
                dialogA.dismiss();
            }
        });


        TextView caja = view.findViewById( R.id.cajaEvaluador );

        RatingBar ratingBar = view.findViewById(R.id.ratingbar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                //Toast.makeText(getApplicationContext(),"rating"+String.valueOf(v),Toast.LENGTH_SHORT).show();
                caja.setText( String.valueOf(v) +"/5.0");
                calificador = String.valueOf(v);
            }
        });


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

    private void CalificarPedido(){


        String url = "http://167.99.158.191/Api_pedidos_ProyectoFinal/pedidos/calificarPedido.php";


        HashMap<String, String> params = new HashMap<String, String>();

        params.put("id_pedido",id_pedidos );
        params.put("calificacion",  calificador  );

        //Toast.makeText( getContext(),id_pedidos+"   "+calificador,Toast.LENGTH_SHORT ).show();


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest( url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText( getContext(),"Pedido Calificado.",Toast.LENGTH_SHORT ).show();

                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new Categoria_Fragmento()).commit();
                        dialogA.dismiss();


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // VolleyLog.d("Error", "Error: " + error.getMessage());
                //Toast.makeText( getContext(),error.getMessage(),Toast.LENGTH_SHORT ).show();
                Toast.makeText( getContext(),"Error Pedido Calificado. "+error.getMessage(),Toast.LENGTH_SHORT ).show();

                //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new Categoria_Fragmento()).commit();
                //dialogA.dismiss();

            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonObjectRequest);

    }


    private void Entregar(){


        String url = "http://167.99.158.191/Api_pedidos_ProyectoFinal/pedidos/entregarPedido.php";


        HashMap<String, String> params = new HashMap<String, String>();

        params.put("id_pedido",id_pedidos );


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest( url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText( getContext(),"Pedido entregado.",Toast.LENGTH_SHORT ).show();

                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new Categoria_Fragmento()).commit();



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // VolleyLog.d("Error", "Error: " + error.getMessage());
                //Toast.makeText( getContext(),error.getMessage(),Toast.LENGTH_SHORT ).show();
                Toast.makeText( getContext(),"Pedido entregado.",Toast.LENGTH_SHORT ).show();

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new Categoria_Fragmento()).commit();


            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonObjectRequest);

    }

    private void OpcionesParaUsuarios(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        //builder.setCancelable( false );
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_dialog_informacion_pedido,null);
        builder.setView(view);

        dialogInforDetalle = builder.create();
        dialogInforDetalle.show();



        Button btnAc = view.findViewById(R.id.btnUbicacionMiPerfilPedidos );
        Button ubicacion = view.findViewById( R.id.btnubicacionRPedidos );
        ubicacion.setText( "Mi Ubicacion" );
        ubicacion.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String geoUri = "http://maps.google.com/maps?q=loc:" + latitud_info + "," + longitud_info + " (" + "My Ubicacion" + ")";
                Uri location = Uri.parse(geoUri);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
                startActivity(mapIntent);

            }
        } );

        if(MainActivity.getTipo_usuario().equals( "Repartidor" )){
            btnAc.setText( "Entregar pedido" );
            ubicacion.setVisibility( View.VISIBLE );

        }else{
            //can.setBackgroundColor( Color.parseColor("#5EA796") );
            if(status_pedido.equals( "ENTREGADO" )){
                btnAc.setText( "Calificar Pedido" );
            }

            ubicacion.setVisibility( View.GONE );


        }


        //btnAc.setText("Ubicacion");


        btnAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(),"Click en aceptar ",Toast.LENGTH_SHORT).show();
                /*
                String geoUri = "http://maps.google.com/maps?q=loc:" + MainActivity.getLatitudGuardada() + "," + MainActivity.getLongitudGuardada() + " (" + "My Ubicacion" + ")";
                Uri location = Uri.parse(geoUri);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
                startActivity(mapIntent);

                 */

                if(btnAc.getText().equals( "Entregar pedido" )){
                    Entregar();
                }

                if(status_pedido.equals( "ENTREGADO" )){
                    if(btnAc.getText().equals( "Calificar Pedido" )){
                        Rating();
                    }
                }else{
                    String geoUri = "http://maps.google.com/maps?q=loc:" + latitud_info + "," + longitud_info + " (" + "My Ubicacion" + ")";
                    Uri location = Uri.parse(geoUri);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
                    startActivity(mapIntent);
                }


                dialogInforDetalle.dismiss();

            }
        });


        Button btnCa = view.findViewById(R.id.buttonClose );
        btnCa.setText("Cerrar");
        btnCa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(),"Click en Cancelar",Toast.LENGTH_SHORT).show();
                dialogInforDetalle.dismiss();
            }
        });


        TextView Nombre = view.findViewById( R.id.miPerfiltxtNombrePedidos );
        Nombre.setText( nombre_pedido );

        TextView correo = view.findViewById( R.id.miPerfiltxtCorreoPedidos );
        correo.setText( correo_pedido );

        TextView telefono = view.findViewById( R.id.miPerfiltxtTelefonoPedidos );
        telefono.setText( "Tel: " + telefono_pedido );

        TextView direccion = view.findViewById( R.id.miPerfiltxtDireccionPedidos );
        direccion.setText( "Direccion: " + direccion_pedido );

        CircleImageView imagen = view.findViewById( R.id.IMGPerfilPedidos );
        Picasso.with(getContext()).load(url_pedido).into(imagen);


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