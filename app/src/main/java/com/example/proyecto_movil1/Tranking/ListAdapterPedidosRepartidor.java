package com.example.proyecto_movil1.Tranking;

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

public class ListAdapterPedidosRepartidor extends ArrayAdapter {

    private List<Modelo_Repartidor> mLista;
    private Context context;
    private int resourceLayout;

    public ListAdapterPedidosRepartidor(@NonNull Context context, int resource, @NonNull List<Modelo_Repartidor> objects) {
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

        Modelo_Repartidor modelo = mLista.get(position);

        TextView nom = view.findViewById( R.id.NombrePedido );
        nom.setText( modelo.getPedidos() );

        TextView fecha = view.findViewById(R.id.fechaPedido );
        fecha.setText(modelo.getFecha());

        TextView status = view.findViewById(R.id.estado );
        status.setText(modelo.getStatus());

        //return super.getView(position, convertView, parent);
        return view;
    }
}
