package com.example.proyecto_movil1.Pedidos;

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

public class ListAdapterPedidosProductos extends ArrayAdapter {

    private List<Modelo_informacion_Pedidos_Productos> mLista;
    private Context context;
    private int resourceLayout;

    public ListAdapterPedidosProductos(@NonNull Context context, int resource, @NonNull List<Modelo_informacion_Pedidos_Productos> objects) {
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

        Modelo_informacion_Pedidos_Productos modelo = mLista.get(position);

        ImageView imageView = view.findViewById(R.id.imgProductosLLevado);
        Picasso.with(context).load(modelo.getUrl()).into(imageView);

        TextView nombre = view.findViewById(R.id.Name_product );
        nombre.setText(modelo.getNombre());

        TextView cantidad = view.findViewById(R.id.Cantidad_p );
        cantidad.setText(modelo.getCantidad());

        TextView precio = view.findViewById(R.id.Precio);
        precio.setText(modelo.getPrecio());

        //return super.getView(position, convertView, parent);
        return view;
    }
}
