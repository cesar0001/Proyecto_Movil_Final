package com.example.proyecto_movil1.Pedidos;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

 import androidx.appcompat.app.AlertDialog;
 import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
 import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyecto_movil1.Carrito.ListAdapterCarrito;
import com.example.proyecto_movil1.Categoria_Fragmento;
import com.example.proyecto_movil1.MainActivity;
import com.example.proyecto_movil1.R;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;


public class PedidosFragmento extends Fragment {


    //private List<CarritoCompra> mLista = new ArrayList<>();
    private ListView mlistView;
    ListAdapterCarrito mAdapter;
    private Button btnpedidos;

    private String SubTotal="", ImpuestoTotal="", TotalFinal="";
    private int Posicion;

   private TextView textoSubTotal,textoshowImpuesto,textTotal;

    /*
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

    }

     */




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate( R.layout.fragment_pedidos_fragmento, container, false );

        mlistView = viewGroup.findViewById( R.id.listPedidoCarrito );
        textoSubTotal = viewGroup.findViewById( R.id.textosubTotal );

        textoshowImpuesto = viewGroup.findViewById( R.id.textoshowImpuesto );
        textTotal = viewGroup.findViewById( R.id.textTotal );

        mlistView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertaDialogo( "¿Desea eliminar este producto del carrito?","Eliminar producto del carrito",position);

            }
        } );

        CargarListaCarrito();

        btnpedidos = viewGroup.findViewById( R.id.btnPedido );
        btnpedidos.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    public void run() {
                        GuardarPedido();
                    }
                }).start();

            }
        } );





        return viewGroup;
    }

    private void AlertaDialogo(String mensaje, String title,int posicionActual) {

        AlertDialog.Builder alerta = new AlertDialog.Builder( getActivity() );
        alerta.setMessage( mensaje )
                .setCancelable( false )
                .setPositiveButton( "Eliminar Carrito", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        eliminarListaCarrito( posicionActual );
                        CargarListaCarrito();
                        Toast.makeText( getContext(),"Producto Eliminado",Toast.LENGTH_SHORT ).show();
                        dialog.cancel();
                    }
                } ).setNegativeButton( "Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        } );

        AlertDialog titulo = alerta.create();
        titulo.setTitle( title );
        titulo.show();

    }

    public void CargarListaCarrito() {

        //carga los datos y los pone al listview personalizado
        mAdapter = new ListAdapterCarrito( getActivity().getApplicationContext(), R.layout.item_row_pedidos, Categoria_Fragmento.Carrito_Compras );

        mlistView.setAdapter( mAdapter );

        //calcula la cantidad X precio y lo suma
        TotalProductos();

    }

    private void eliminarListaCarrito(int pos)
    {
        Categoria_Fragmento.Carrito_Compras.remove( pos );
        TotalProductos();

    }

    private void BorrarCarritoCompleto()
    {
        Categoria_Fragmento.Carrito_Compras.clear();
        TotalProductos();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new Categoria_Fragmento()).commit();


    }

    private void TotalProductos(){

        Double suma = 0.0;
        Double impuesto = 0.0;
        Double temporal = 0.0;

        if(Categoria_Fragmento.Carrito_Compras.size()!=0) {


            for (int iterador = 0; iterador < Categoria_Fragmento.Carrito_Compras.size(); iterador++) {

                try {

                    Double cantidad = Double.parseDouble( Categoria_Fragmento.Carrito_Compras.get( iterador ).getCantidad() );
                    Double precio = Double.parseDouble( Categoria_Fragmento.Carrito_Compras.get( iterador ).getPrecio() );

                    Double producto = cantidad * precio;


                    suma = suma + producto;
                    textoSubTotal.setText( String.valueOf( Math.round(suma*100.0)/100.0  ) );

                    impuesto = suma * 0.15;
                    textoshowImpuesto.setText( String.valueOf( Math.round(impuesto*100.0)/100.0  ) );

                    temporal = suma + impuesto;
                    textTotal.setText( String.valueOf( Math.round(temporal*100.0)/100.0 ) );

                } catch (Exception e) {
                    Toast.makeText( getActivity(), "Error al calcular total", Toast.LENGTH_SHORT ).show();
                    textoSubTotal.setText( "0.00" );
                }


            }

            SubTotal = String.valueOf( suma );
            ImpuestoTotal = String.valueOf( impuesto );
            TotalFinal = String.valueOf( temporal );

        }else{
            textoSubTotal.setText( "0.00" );
            textoshowImpuesto.setText( "0.00" );
            textTotal.setText( "0.00" );
        }

    }

    private void GuardarPedido()
    {

        String url = "http://167.99.158.191/Api_pedidos_ProyectoFinal/pedidos/insertProductoPedido.php";

        int tamaño = Categoria_Fragmento.Carrito_Compras.size();
        Posicion = 0;
        while (Posicion < tamaño) {

            HashMap<String, String> params = new HashMap<String, String>();


            params.put( "id_usuario",  MainActivity.getId_usuario() );
            params.put( "id_producto", Categoria_Fragmento.Carrito_Compras.get( Posicion ).getId_Producto() );
            params.put( "cantidad",  Categoria_Fragmento.Carrito_Compras.get( Posicion ).getCantidad());


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest( url, new JSONObject( params ),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            //AlertaDialogo( "Informacion Guardada Exitosamente!!!","Registro" );
                            Posicion++;

                            if(Posicion == tamaño){
                                AlertaDialogoM( "Informacion Guardada Exitosamente!!!","Registro" );
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // VolleyLog.d("Error", "Error: " + error.getMessage());
                    //AlertaDialogo( "Error al conectarse al servidor", "Error" );
                    Toast.makeText( getContext(),"Error al conectarse al servidor",Toast.LENGTH_SHORT ).show();
                }
            } );


            RequestQueue requestQueue = Volley.newRequestQueue( getContext() );
            requestQueue.add( jsonObjectRequest );
        }

    }

    private void AlertaDialogoM(String mensaje, String title){

        AlertDialog.Builder alerta = new AlertDialog.Builder(getActivity());
        alerta.setMessage(mensaje)
                .setCancelable(false)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BorrarCarritoCompleto();
                        dialog.cancel();
                    }
                });

        AlertDialog titulo = alerta.create();
        titulo.setTitle(title);
        titulo.show();

    }


}