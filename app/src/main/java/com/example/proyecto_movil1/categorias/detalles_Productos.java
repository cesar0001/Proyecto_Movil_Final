package com.example.proyecto_movil1.categorias;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyecto_movil1.Carrito.CarritoCompra;
import com.example.proyecto_movil1.Categoria_Fragmento;
import com.example.proyecto_movil1.R;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;


public class detalles_Productos extends Fragment {


    private TextInputLayout show;
    private Button incremento, decremento,agregar;
    private TextView TVprecio,TVtolal,TVUnidad;
    private ImageView img;

    private int numero = 1;

    public static String Nombre_pro,Precio_pro="0",Url_photo_detalle,id_producto_detalles;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate( R.layout.fragment_detalles_productos, container, false );


        show = viewGroup.findViewById( R.id.txtshowNumero );
        img= viewGroup.findViewById( R.id.imgdetalle );
        incremento = viewGroup.findViewById( R.id.incremento );
        decremento = viewGroup.findViewById( R.id.decremento );

        Picasso.with(getContext()).load(Url_photo_detalle).into(img);

        TVprecio = viewGroup.findViewById( R.id.txtShoName );
        TVprecio.setText( Nombre_pro );

        TVUnidad = viewGroup.findViewById( R.id.txtShoPrecio );
        TVUnidad.setText( "Precio Unitario: "+Precio_pro );

        TVtolal = viewGroup.findViewById( R.id.txtShowTotal );

        agregar = viewGroup.findViewById( R.id.agregar );

        calcular();


        show.getEditText().setText( String.valueOf( numero ) );

        incremento.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numero++;
                show.getEditText().setText( String.valueOf( numero ) );

                //calcular
                calcular();

            }
        } );

        decremento.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(numero<=0){

                }else{
                    numero--;
                    show.getEditText().setText( String.valueOf( numero ) );
                    calcular();
                }

            }
        } );

        agregar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CarritoCompra carrito = new CarritoCompra();
                carrito.setNombre( Nombre_pro );
                carrito.setCantidad( show.getEditText().getText().toString() );
                carrito.setPrecio( Precio_pro );
                carrito.setUrl( Url_photo_detalle );
                carrito.setId_Producto( id_producto_detalles );

                Categoria_Fragmento.Carrito_Compras.add( carrito );
                Toast.makeText( getContext(), "Tiene: "+Categoria_Fragmento.Carrito_Compras.size() +" elementos en el Carrito de compras.",Toast.LENGTH_SHORT).show();

            }
        } );


        return viewGroup;

    }


    private void calcular(){

        try {
            Double total = Double.parseDouble( String.valueOf( numero ) ) * Double.parseDouble( Precio_pro );
            TVtolal.setText( "Total: "+String.valueOf( total ) );
        }catch (Exception e){
            Toast.makeText( getContext(),e.getMessage(),Toast.LENGTH_SHORT ).show();
        }

    }

    public static String getNombre_pro() {
        return Nombre_pro;
    }

    public static void setNombre_pro(String nombre_pro) {
        Nombre_pro = nombre_pro;
    }

    public static String getPrecio_pro() {
        return Precio_pro;
    }

    public static void setPrecio_pro(String precio_pro) {
        Precio_pro = precio_pro;
    }

    public static String getUrl_photo_detalle() {
        return Url_photo_detalle;
    }

    public static void setUrl_photo_detalle(String url_photo_detalle) {
        Url_photo_detalle = url_photo_detalle;
    }

    public static String getId_producto_detalles() {
        return id_producto_detalles;
    }

    public static void setId_producto_detalles(String id_producto_detalles) {
        detalles_Productos.id_producto_detalles = id_producto_detalles;
    }
}