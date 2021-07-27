package com.example.proyecto_movil1.categorias;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.proyecto_movil1.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListAdapterProductos extends ArrayAdapter {

    private List<Modelo_Productos> mLista;
    private Context context;
    private int resourceLayout;

    public ListAdapterProductos(@NonNull Context context, int resource, @NonNull List<Modelo_Productos> objects) {
        super(context, resource, objects);
        this.context = context;
        this.mLista = objects;
        this.resourceLayout = resource;

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;

        if(view==null){
            view = LayoutInflater.from(context).inflate(resourceLayout,null);
        }

        Modelo_Productos modelo = mLista.get(position);

        ImageView imageView = view.findViewById( R.id.imageP);
        Picasso.with(context).load(modelo.getUrl()).into(imageView);

        TextView nom = view.findViewById( R.id.txtNombreP );
        nom.setText( modelo.getNombre() );

        TextView descrip = view.findViewById(R.id.txtDescripcionRowP);
        descrip.setText(modelo.getDescripcion());

        //return super.getView(position, convertView, parent);
        return view;
    }
}
