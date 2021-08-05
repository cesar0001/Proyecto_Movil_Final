package com.example.proyecto_movil1.P_Recomendados;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.proyecto_movil1.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListAdapterProductosRecomendados extends ArrayAdapter {

    private List<Modelo_Recomendados> mLista;
    private Context context;
    private int resourceLayout;

    public ListAdapterProductosRecomendados(@NonNull Context context, int resource, @NonNull List<Modelo_Recomendados> objects) {
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

        Modelo_Recomendados modelo = mLista.get(position);

        ImageView imageView = view.findViewById( R.id.imagePRecomendados );
        Picasso.with(context).load(modelo.getUrl_photo()).into(imageView);

        //categoria
        TextView categoria = view.findViewById( R.id.Name_product );
        categoria.setText( modelo.getCategoria() );

        //producto
        TextView productos = view.findViewById(R.id.txtNameProductsR );
        productos.setText(modelo.getProducto());

        RatingBar calificador = view.findViewById( R.id.ratingBar1 );
        float valor = (Float.parseFloat( modelo.getRatingBar() )  );
        calificador.setRating( valor );
        //calificador.setEnabled( false );

        //return super.getView(position, convertView, parent);
        return view;
    }
}
