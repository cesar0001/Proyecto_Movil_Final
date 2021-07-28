package com.example.proyecto_movil1;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyecto_movil1.Carrito.CarritoCompra;
import com.example.proyecto_movil1.Pedidos.PedidosFragmento;
import com.example.proyecto_movil1.categorias.CrearCategorias;
import com.example.proyecto_movil1.categorias.CrearProductos;
import com.example.proyecto_movil1.categorias.MostrarCategorias;

import java.util.ArrayList;
import java.util.List;

public class Categoria_Fragmento extends Fragment implements View.OnClickListener{

    private CardView cardProductos;
    private CardView cardPedidos;
    private CardView cardTracking;
    private CardView cardRecomendado;
    private CardView cardCrearCategoria;
    private CardView cardCrearProducto;
    private TextView mydashboard;

    public static List<CarritoCompra> Carrito_Compras = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_categoria_fragmento, container, false);

        mydashboard = viewGroup.findViewById( R.id.myDashboard );
        mydashboard.setText( "Bienvenido " + MainActivity.getNombre()+"!!!" );

        cardProductos = viewGroup.findViewById( R.id.cardProductos );
        cardProductos.setOnClickListener( this::onClick );

        cardPedidos = viewGroup.findViewById( R.id.cardPedidos );
        cardPedidos.setOnClickListener( this::onClick );

        cardTracking = viewGroup.findViewById( R.id.cardTracking );
        cardTracking.setOnClickListener( this::onClick );

        cardRecomendado = viewGroup.findViewById( R.id.cardProductRecom );
        cardRecomendado.setOnClickListener( this::onClick );

        cardCrearCategoria = viewGroup.findViewById( R.id.cardCrearCategoria );
        cardCrearCategoria.setOnClickListener( this::onClick );

        cardCrearProducto = viewGroup.findViewById( R.id.cardCrearProducto );
        cardCrearProducto.setOnClickListener( this::onClick );

        if(!MainActivity.getTipo_usuario().equals( "Administrador" )){
             cardCrearCategoria.setVisibility( View.GONE );
             cardCrearProducto.setVisibility( View.GONE );
        }

        return viewGroup;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.cardProductos:

                //Toast.makeText(getContext(), "Productos", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(MainMenu.this,DriverManager.class));
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new MostrarCategorias()).commit();


                break;
            case R.id.cardPedidos:

                if(Carrito_Compras.size()==0){
                    AlertaDialogo( "Debe de agregar productos al carrito para crear pedido","Sin productos" );
                }else{
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new PedidosFragmento()).commit();
                }


                //Toast.makeText(getContext(), "pedidos", Toast.LENGTH_SHORT).show();
                break;

            case R.id.cardTracking:

                Toast.makeText(getContext(), "Tracking", Toast.LENGTH_SHORT).show();
                break;

            case R.id.cardProductRecom:

                Toast.makeText(getContext(), "producto recomendado", Toast.LENGTH_SHORT).show();
                break;

            case R.id.cardCrearCategoria:

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new CrearCategorias()).commit();

                break;

            case R.id.cardCrearProducto:

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new CrearProductos()).commit();


                 break;


        }


    }

    private void AlertaDialogo(String mensaje, String title) {

        AlertDialog.Builder alerta = new AlertDialog.Builder( getActivity() );
        alerta.setMessage( mensaje )
                .setCancelable( false )
                .setPositiveButton( "Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                } );

        AlertDialog titulo = alerta.create();
        titulo.setTitle( title );
        titulo.show();

    }





}

