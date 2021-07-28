package com.example.proyecto_movil1.Creditos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.proyecto_movil1.R;

public class Creditos extends Fragment {





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate( R.layout.fragment_creditos, container, false );

        TextView te = viewGroup.findViewById( R.id.textCreditos );

        String texto = "Participantes del Grupo #5 \n\n" +
                        "-Cesar Pineda (201730010275).\n" +
                "-Juan Paz (201410010078).\n" +
                "-Jairo Perdomo (201910110022).\n" +
                "-Nahomi Moradel (201810030050).\n" +
                "-Joel Turcios (200610520037).\n" +
                "-Marlon Girón (201820010222).\n" +
                "-Axel Reyes (201710120055).\n"
                ;


        te.setText( texto );



        return  viewGroup;
    }
}