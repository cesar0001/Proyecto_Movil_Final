package com.example.proyecto_movil1;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.proyecto_movil1.Carrito.CarritoCompra;
import com.example.proyecto_movil1.P_Recomendados.Recomendados_Fragmento;
import com.example.proyecto_movil1.Pedidos.PedidosFragmento;
import com.example.proyecto_movil1.Tranking.FragmentoMapa;
import com.example.proyecto_movil1.Tranking.FragmentoPedidosRepartidor;
import com.example.proyecto_movil1.categorias.CrearCategorias;
import com.example.proyecto_movil1.categorias.CrearProductos;
import com.example.proyecto_movil1.categorias.MostrarCategorias;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Categoria_Fragmento extends Fragment implements View.OnClickListener{

    private CardView cardProductos;
    private CardView cardPedidos;
    private CardView cardTracking;
    private CardView cardRecomendado;
    private CardView cardCrearCategoria;
    private CardView cardCrearProducto;
    private TextView mydashboard;

    private CircleImageView circleImageView;

    AlertDialog dialogA;

    public static List<CarritoCompra> Carrito_Compras = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_categoria_fragmento, container, false);

        circleImageView = viewGroup.findViewById( R.id.profile_image );
        circleImageView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VistaPerfil();
            }
        } );

        Picasso.with(getContext()).load(MainActivity.getUrl_foto()).into(circleImageView);

        mydashboard = viewGroup.findViewById( R.id.myDashboard );
        mydashboard.setText( "Bienvenido " + MainActivity.getNombre()+"!!!" );
        //Toast.makeText( getContext(),MainActivity.getNombre(),Toast.LENGTH_SHORT ).show();

        cardProductos = viewGroup.findViewById( R.id.cardProductos );
        cardProductos.setOnClickListener( this::onClick );

        cardPedidos = viewGroup.findViewById( R.id.cardPedidos );
        cardPedidos.setOnClickListener( this::onClick );

        cardTracking = viewGroup.findViewById( R.id.cardPedidos_Repartidor );
        cardTracking.setOnClickListener( this::onClick );

        cardRecomendado = viewGroup.findViewById( R.id.cardProductRecom );
        cardRecomendado.setOnClickListener( this::onClick );

        cardCrearCategoria = viewGroup.findViewById( R.id.cardCrearCategoria );
        cardCrearCategoria.setOnClickListener( this::onClick );

        cardCrearProducto = viewGroup.findViewById( R.id.cardCrearProducto );
        cardCrearProducto.setOnClickListener( this::onClick );

        if(!MainActivity.getTipo_usuario().equals( "Administrador" )){
             cardCrearCategoria.setVisibility( View.GONE );
             cardCrearProducto.setVisibility( View.GONE );
        }



        return viewGroup;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.cardProductos:

                //Toast.makeText(getContext(), "Productos", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(MainMenu.this,DriverManager.class));
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new MostrarCategorias()).commit();


                break;
            case R.id.cardPedidos:

                if(Carrito_Compras.size()==0){
                    AlertaDialogo( "Debe de agregar productos al carrito para crear pedido","Sin productos" );
                }else{
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new PedidosFragmento()).commit();
                }


                //Toast.makeText(getContext(), "pedidos", Toast.LENGTH_SHORT).show();
                break;

            case R.id.cardPedidos_Repartidor:

                //Toast.makeText(getContext(), "Tracking", Toast.LENGTH_SHORT).show();

                if(MainActivity.getTipo_usuario().equals( "Repartidor" )){
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new FragmentoPedidosRepartidor()).commit();

                }else{

                    FragmentoMapa.setLatitud_map( Double.parseDouble( MainActivity.getLatitudGuardada() ) );
                    FragmentoMapa.setLongitud_map( Double.parseDouble( MainActivity.getLongitudGuardada()) );
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new FragmentoMapa()).commit();

                }



                break;

            case R.id.cardProductRecom:

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new Recomendados_Fragmento()).commit();

                break;

            case R.id.cardCrearCategoria:

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new CrearCategorias()).commit();

                break;

            case R.id.cardCrearProducto:

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new CrearProductos()).commit();


                 break;


        }


    }

    private void AlertaDialogo(String mensaje, String title) {

        AlertDialog.Builder alerta = new AlertDialog.Builder( getActivity() );
        alerta.setMessage( mensaje )
                .setCancelable( false )
                .setPositiveButton( "Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                } );

        AlertDialog titulo = alerta.create();
        titulo.setTitle( title );
        titulo.show();

    }


    private void VistaPerfil(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable( false );
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_dialog_perfil,null);
        builder.setView(view);

        dialogA = builder.create();
        dialogA.show();



        Button btnAc = view.findViewById(R.id.btnUbicacionMiPerfil );
        btnAc.setText("Ubicacion");
        btnAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(),"Click en aceptar ",Toast.LENGTH_SHORT).show();
                String geoUri = "http://maps.google.com/maps?q=loc:" + MainActivity.getLatitudGuardada() + "," + MainActivity.getLongitudGuardada() + " (" + "My Ubicacion" + ")";
                Uri location = Uri.parse(geoUri);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
                startActivity(mapIntent);

            }
        });


        Button btnCa = view.findViewById(R.id.btncancelarR );
        btnCa.setText("Cerrar");
        btnCa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(),"Click en Cancelar",Toast.LENGTH_SHORT).show();
                dialogA.dismiss();
            }
        });


        TextView Nombre = view.findViewById( R.id.miPerfiltxtNombre );
        Nombre.setText( MainActivity.getNombre() );

        TextView correo = view.findViewById( R.id.miPerfiltxtCorreo );
        correo.setText( MainActivity.getCorreo() );

        TextView telefono = view.findViewById( R.id.miPerfiltxtTelefono );
        telefono.setText( "Tel: "+MainActivity.getTelefono() );

        TextView direccion = view.findViewById( R.id.miPerfiltxtDireccion );
        direccion.setText( "Direccion: " + MainActivity.getDireccion() );

        CircleImageView imagen = view.findViewById( R.id.IMGPerfil );
        Picasso.with(getContext()).load(MainActivity.getUrl_foto()).into(imagen);



    }



}

