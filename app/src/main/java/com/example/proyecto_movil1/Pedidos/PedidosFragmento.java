package com.example.proyecto_movil1.Pedidos;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyecto_movil1.Carrito.CarritoCompra;
import com.example.proyecto_movil1.Carrito.ListAdapterCarrito;
import com.example.proyecto_movil1.Categoria_Fragmento;
import com.example.proyecto_movil1.R;

import java.util.ArrayList;
import java.util.List;


public class PedidosFragmento extends Fragment {


    //private List<CarritoCompra> mLista = new ArrayList<>();
    private ListView mlistView;
    ListAdapterCarrito mAdapter;

   private TextView textoTotal;

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
        textoTotal = viewGroup.findViewById( R.id.textoTotal );

        mlistView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertaDialogo( "¿Desea eliminar este producto del carrito?","Eliminar producto del carrito",position);

            }
        } );

        CargarListaCarrito();


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

    private void TotalProductos(){

        Double suma = 0.0;
        if(Categoria_Fragmento.Carrito_Compras.size()!=0) {


            for (int iterador = 0; iterador < Categoria_Fragmento.Carrito_Compras.size(); iterador++) {

                try {

                    Double cantidad = Double.parseDouble( Categoria_Fragmento.Carrito_Compras.get( iterador ).getCantidad() );
                    Double precio = Double.parseDouble( Categoria_Fragmento.Carrito_Compras.get( iterador ).getPrecio() );

                    Double producto = cantidad * precio;


                    suma = suma + producto;
                    textoTotal.setText( String.valueOf( suma ) );

                } catch (Exception e) {
                    Toast.makeText( getActivity(), "Error al calcular total", Toast.LENGTH_SHORT ).show();
                    textoTotal.setText( "0.00" );
                }


            }
        }else{
            textoTotal.setText( "0.00" );
        }

    }


}