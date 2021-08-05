package com.example.proyecto_movil1.Tranking;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyecto_movil1.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class FragmentoMapa extends Fragment {

    public static Double latitud_map=0.00,longitud_map=0.00;



    public static Double getLatitud_map() {
        return latitud_map;
    }

    public static void setLatitud_map(Double latitud_map) {
        FragmentoMapa.latitud_map = latitud_map;
    }

    public static Double getLongitud_map() {
        return longitud_map;
    }

    public static void setLongitud_map(Double longitud_map) {
        FragmentoMapa.longitud_map = longitud_map;
    }

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {

            LatLng sydney = new LatLng( latitud_map, longitud_map );
            googleMap.addMarker( new MarkerOptions().position( sydney ).title( "Mi Ubicacion" ) );
            googleMap.moveCamera( CameraUpdateFactory.newLatLng( sydney ) );
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate( R.layout.fragment_fragmento_mapa, container, false );
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById( R.id.map );
        if (mapFragment != null) {
            mapFragment.getMapAsync( callback );
        }
    }
}