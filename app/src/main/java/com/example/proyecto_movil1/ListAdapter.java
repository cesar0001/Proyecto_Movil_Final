package com.example.proyecto_movil1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

 import com.example.proyecto_movil1.categorias.Modelo_Categoria;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListAdapter extends ArrayAdapter {

    private List<Modelo_Categoria> mLista;
    private Context context;
    private int resourceLayout;

    public ListAdapter(@NonNull Context context, int resource, @NonNull List<Modelo_Categoria> objects) {
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

        Modelo_Categoria modelo = mLista.get(position);

        ImageView imageView = view.findViewById(R.id.image);
        Picasso.with(context).load(modelo.getUrl()).into(imageView);

        TextView descrip = view.findViewById(R.id.txtDescripcionRow);
        descrip.setText(modelo.getDescripcion());

        //return super.getView(position, convertView, parent);
        return view;
    }
}
