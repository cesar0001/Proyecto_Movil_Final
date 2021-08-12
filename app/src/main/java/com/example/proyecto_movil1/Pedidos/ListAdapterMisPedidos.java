package com.example.proyecto_movil1.Pedidos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.proyecto_movil1.R;

import java.util.List;

public class ListAdapterMisPedidos extends ArrayAdapter {

    private List<Modelo_MisPedidos> mLista;
    private Context context;
    private int resourceLayout;

    public ListAdapterMisPedidos(@NonNull Context context, int resource, @NonNull List<Modelo_MisPedidos> objects) {
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

        Modelo_MisPedidos modelo = mLista.get(position);


        TextView nombre = view.findViewById(R.id.Name_product );
        nombre.setText("Pedido #"+modelo.getPedido());

        TextView fecha = view.findViewById(R.id.Cantidad_p );
        fecha.setText(modelo.getFecha());

        TextView status = view.findViewById(R.id.misstatus);
        status.setText(modelo.getStatus());

        //return super.getView(position, convertView, parent);
        return view;
    }
}
