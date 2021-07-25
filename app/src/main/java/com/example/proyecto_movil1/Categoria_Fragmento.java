package com.example.proyecto_movil1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

public class Categoria_Fragmento extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_categoria__fragmento, container, false);

        ImageView imageView = viewGroup.findViewById( R.id.imageView2 );

        imageView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText( getContext(),"click",Toast.LENGTH_SHORT ).show();
            }
        } );

        return viewGroup;
    }


}