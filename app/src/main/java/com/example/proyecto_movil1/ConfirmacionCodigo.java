package com.example.proyecto_movil1;



import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import android.os.StrictMode;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class ConfirmacionCodigo extends AppCompatActivity {


    // variales que se mandan a tener los datos cuando se registra un usuario, de la clase CreateUsers
    public static String nombreCC ="";
    public static String telefonoCC="";
    public static String direccionCC="";
    public static String usuarioCC="";
    public static String contraseniaCC="";
    public static String correoCC="";
    public static String latitudCC="";
    public static String longitudCC="";
    public static Bitmap url_fotoCC;


    private long numeroRandom = 0;

    //Correo y contraseña del que va a enviar el mensaje
    private String correo = "servidormovil420@gmail.com";
    private String contraseña = "servidormovil";


    private TextView textmsj;
    private TextInputLayout textCodigo;
    private Button recaptcha1;
    CoordinatorLayout snackbar_layout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_confirmacion_codigo );

        try {


            textmsj = (TextView) findViewById( R.id.textMsj1 );
            textCodigo = (TextInputLayout) findViewById( R.id.CodigoSeguridad1 );
            textmsj.setText( "Su codigo esta en el correo: " + getCorreoCC() );
            snackbar_layout = (CoordinatorLayout)findViewById(R.id.snackbar_confirm);


            recaptcha1 = (Button) findViewById( R.id.recaptcha1 );
            recaptcha1.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MetodoGenerarCodigo_EnviarCorreo();
                    Toast.makeText( getApplicationContext(), "Codigo enviado", Toast.LENGTH_SHORT ).show();
                }
            } );


            findViewById( R.id.btnCode1 ).setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (textCodigo.getEditText().getText().toString().equals( String.valueOf( numeroRandom ) ) == true) {

                        Crear();
                    } else {
                        snackbar( "El codigo es incorrecto." );
                    }
                }
            } );

            MetodoGenerarCodigo_EnviarCorreo();
        } catch (Exception e) {
            Toast.makeText( getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT ).show();

        }
    }



    private void MetodoGenerarCodigo_EnviarCorreo(){

        // cree este metodo para que al darle reenviar codigo envie al correo el codigo
        //van de la mano
        CodigoAleatorio();
        EnviarCorreo();

    }

    private void CodigoAleatorio() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            numeroRandom = ThreadLocalRandom.current().nextInt( 100, 10000 );
        } else {

            Random aleatorio = new Random( System.currentTimeMillis() );
// Producir nuevo int aleatorio entre 0 y 99
            numeroRandom = 100 + aleatorio.nextInt( 10000 );
// Más código

// Refrescar datos aleatorios
            aleatorio.setSeed( System.currentTimeMillis() );
// ... o mejor
            aleatorio.setSeed( aleatorio.nextLong() );

        }
    }

    private void EnviarCorreo() {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy( policy );
        Properties props = new Properties();
        props.put( "mail.smtp.host", "smtp.gmail.com" );
        props.put( "mail.smtp.socketFactory.port", "465" );
        props.put( "mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory" );
        props.put( "mail.smtp.auth", "true" );
        props.put( "mail.smtp.port", "465" );

        try {
            Session session;

            session = Session.getDefaultInstance( props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication( correo, contraseña );
                }
            } );

            if (session != null) {

                String correoEnviar = getCorreoCC();
                //String correoEnviar = "pinedacesar253@gmail.com";

                Message mimeMessage = new MimeMessage( session );

                mimeMessage.setFrom( new InternetAddress( correo ) );
                mimeMessage.setSubject( "Codigo para registrarse en nuestra app." );

                mimeMessage.setRecipients( Message.RecipientType.TO, InternetAddress.parse( correoEnviar ) );

                //Cuerpo
                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                String cuerpo = "Que tenga buenos dias, "+getNombreCC() + " se le saluda de parte de el supermercado" +
                        " el econimico. \n\n este es su codigo actual que se le a enviado a la hora "+currentDateTimeString+"" +
                        " para poder registrarse. \n\nCodigo: "+ String.valueOf( numeroRandom );

                mimeMessage.setContent( cuerpo, "text/html; charset=utf-8" );
                Transport.send( mimeMessage );
            }

        } catch (MessagingException e) {
            e.printStackTrace();
        }


    }

    public String GetStringImage(Bitmap imagen) {
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        imagen.compress( Bitmap.CompressFormat.JPEG, 100, ba );
        byte[] imagebyte = ba.toByteArray();
        String encode = Base64.encodeToString( imagebyte, Base64.DEFAULT );
        return "data:image/png;base64," + encode;
    }

    private void Crear()
    {

        String url = "http://167.99.158.191/Api_pedidos_ProyectoFinal/Usuarios/crear_usuarios.php";

        HashMap<String, String> params = new HashMap<String, String>();


        params.put("nombres", nombreCC.toLowerCase());
        params.put("telefonos", telefonoCC);
        params.put("direccion", direccionCC);
        params.put("usuario", usuarioCC);
        params.put("contrasenia", contraseniaCC);
        params.put("correo", correoCC);
        params.put("latitud", latitudCC);
        params.put("longitud", longitudCC);
        params.put("url_foto", GetStringImage( url_fotoCC ));

        /*
        formato del json
        {
            "nombres":"nombres",
                "telefonos":"telefonos",
                "direccion":"direccion",
                "usuario":"usuario",
                "contrasenia":"contrasenia",
                "correo":"correo",
                "url_foto":""
        }

         */

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest( url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AlertaDialogo( "Informacion Guardada Exitosamente!!!","Registro" );
                        Intent intent = new Intent( getApplicationContext(), MainActivity.class );
                        startActivity( intent );
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // VolleyLog.d("Error", "Error: " + error.getMessage());
                AlertaDialogo( "Error al conectarse al servidor","Error" );
            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);


    }

    public void snackbar(String mensaje){
        //Snackbar snackbar = Snackbar.make(v,"Este es ejemplo Snackbar",Snackbar.LENGTH_LONG);
        //puede ser v de View pero se pone CoordinatorLayout tanto en  el layout
        Snackbar snackbar = Snackbar.make(snackbar_layout,mensaje,Snackbar.LENGTH_LONG);

        //setAnchorView con esto hace que el snackbar se muestre mas arriba y no moleste en el
        //componente
        //snackbar.setAnchorView(floatingActionButton);
        snackbar.setDuration(4000);
        snackbar.setAnimationMode( BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
        /*
        snackbar.setAction("Okey", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

         */
        snackbar.show();
    }


    private void AlertaDialogo(String mensaje, String title){

        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setMessage(mensaje)
                .setCancelable(false)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog titulo = alerta.create();
        titulo.setTitle(title);
        titulo.show();

    }



    //get y set

    public static String getNombreCC() {
        return nombreCC;
    }

    public static void setNombreCC(String nombreCC) {
        ConfirmacionCodigo.nombreCC = nombreCC;
    }

    public static String getTelefonoCC() {
        return telefonoCC;
    }

    public static void setTelefonoCC(String telefonoCC) {
        ConfirmacionCodigo.telefonoCC = telefonoCC;
    }

    public static String getDireccionCC() {
        return direccionCC;
    }

    public static void setDireccionCC(String direccionCC) {
        ConfirmacionCodigo.direccionCC = direccionCC;
    }

    public static String getUsuarioCC() {
        return usuarioCC;
    }

    public static void setUsuarioCC(String usuarioCC) {
        ConfirmacionCodigo.usuarioCC = usuarioCC;
    }

    public static String getContraseniaCC() {
        return contraseniaCC;
    }

    public static void setContraseniaCC(String contraseniaCC) {
        ConfirmacionCodigo.contraseniaCC = contraseniaCC;
    }

    public static String getCorreoCC() {
        return correoCC;
    }

    public static void setCorreoCC(String correoCC) {
        ConfirmacionCodigo.correoCC = correoCC;
    }

    public static String getLatitudCC() {
        return latitudCC;
    }

    public static void setLatitudCC(String latitudCC) {
        ConfirmacionCodigo.latitudCC = latitudCC;
    }

    public static String getLongitudCC() {
        return longitudCC;
    }

    public static void setLongitudCC(String longitudCC) {
        ConfirmacionCodigo.longitudCC = longitudCC;
    }

    public static Bitmap getUrl_fotoCC() {
        return url_fotoCC;
    }

    public static void setUrl_fotoCC(Bitmap url_fotoCC) {
        ConfirmacionCodigo.url_fotoCC = url_fotoCC;
    }


    //fin



}