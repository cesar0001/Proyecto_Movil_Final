package com.example.proyecto_movil1.Carrito;

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

public class ListAdapterCarrito extends ArrayAdapter {

    private List<CarritoCompra> mLista;
    private Context context;
    private int resourceLayout;

    public ListAdapterCarrito(@NonNull Context context, int resource, @NonNull List<CarritoCompra> objects) {
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

        CarritoCompra modelo = mLista.get(position);

        //imagen
        ImageView imageView = view.findViewById( R.id.imgProductosLLevado );
        Picasso.with(context).load(modelo.getUrl()).into(imageView);

        //nombre
        TextView nom = view.findViewById( R.id.txtNombrePedidos );
        nom.setText( modelo.getNombre() );

        //precio
        TextView precio = view.findViewById(R.id.txtPrecioP);
        precio.setText("Precio Unitario: "+modelo.getPrecio());

        //cantidad
        TextView cantidad = view.findViewById(R.id.txtCant);
        cantidad.setText("Cantidad: "+modelo.getCantidad());

        //return super.getView(position, convertView, parent);
        return view;
    }
}
