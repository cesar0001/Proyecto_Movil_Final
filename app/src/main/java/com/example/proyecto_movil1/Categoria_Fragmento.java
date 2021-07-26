package com.example.proyecto_movil1;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyecto_movil1.categorias.CrearCategorias;
import com.example.proyecto_movil1.categorias.CrearProductos;
import com.example.proyecto_movil1.categorias.MostrarCategorias;

public class Categoria_Fragmento extends Fragment implements View.OnClickListener{

    private CardView cardProductos;
    private CardView cardPedidos;
    private CardView cardTracking;
    private CardView cardSetting;
    private CardView cardCrearCategoria;
    private CardView cardCrearProducto;
    private TextView mydashboard;

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

        cardSetting = viewGroup.findViewById( R.id.cardSetting );
        cardSetting.setOnClickListener( this::onClick );

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

                Toast.makeText(getContext(), "pedidos", Toast.LENGTH_SHORT).show();
                break;

            case R.id.cardTracking:

                Toast.makeText(getContext(), "Tracking", Toast.LENGTH_SHORT).show();
                break;

            case R.id.cardSetting:

                Toast.makeText(getContext(), "Setting", Toast.LENGTH_SHORT).show();
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

