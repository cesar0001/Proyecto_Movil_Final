package com.example.proyecto_movil1.categorias;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyecto_movil1.R;
import com.google.android.material.textfield.TextInputLayout;


public class detalles_Productos extends Fragment {


    private TextInputLayout show;
    private Button incremento, decremento;
    private TextView TVprecio,TVtolal,TVUnidad;

    private int numero = 1;

    public static String Nombre_pro,Precio_pro="0";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate( R.layout.fragment_detalles_productos, container, false );


        show = viewGroup.findViewById( R.id.txtshowNumero );
        incremento = viewGroup.findViewById( R.id.incremento );
        decremento = viewGroup.findViewById( R.id.decremento );

        TVprecio = viewGroup.findViewById( R.id.txtShoName );
        TVprecio.setText( Nombre_pro );

        TVUnidad = viewGroup.findViewById( R.id.txtShoPrecio );
        TVUnidad.setText( "Precio Unitaria "+Precio_pro );

        TVtolal = viewGroup.findViewById( R.id.txtShowTotal );
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


        return viewGroup;

    }


    private void calcular(){

        try {
            Double total = Double.parseDouble( String.valueOf( numero ) ) * Double.parseDouble( Precio_pro );
            TVtolal.setText( String.valueOf( total ) );
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
}