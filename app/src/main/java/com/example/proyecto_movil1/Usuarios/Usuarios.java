package com.example.proyecto_movil1.Usuarios;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyecto_movil1.MainActivity;
import com.example.proyecto_movil1.R;
import com.google.android.material.textfield.TextInputLayout;


import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Usuarios extends AppCompatActivity {

    private TextInputLayout textInputLayout;
    private TextInputLayout textInputLayoutuser;
    private Button send;

    //Correo y contraseña del que va a enviar el mensaje
    private String correo = "servidormovil420@gmail.com";
    private String contraseña = "servidormovil";

    String pass_bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_usuarios );

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textInputLayout = (TextInputLayout)findViewById( R.id.campoCorreo );
        textInputLayoutuser = (TextInputLayout)findViewById( R.id.campoUsuarioslOGIN ) ;
        send = (Button)findViewById( R.id.sendEmail );

        send.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(textInputLayout.getEditText().getText().toString().trim().length() == 0 ){
                    textInputLayout.setError( "Este campo es obligatorio" );
                }else{
                    textInputLayout.setError( null );
                    if(textInputLayoutuser.getEditText().getText().toString().trim().length() == 0 ){
                        textInputLayoutuser.setError( "Este campo es obligatorio" );
                    }else{
                        textInputLayoutuser.setError( null );
                        buscarUsuario();

                    }

                }

            }
        } );


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

                String correoEnviar = textInputLayout.getEditText().getText().toString();
                //String correoEnviar = "pinedacesar253@gmail.com";

                Message mimeMessage = new MimeMessage( session );

                mimeMessage.setFrom( new InternetAddress( correo ) );
                mimeMessage.setSubject( "Codigo para registrarse en nuestra app." );

                mimeMessage.setRecipients( Message.RecipientType.TO, InternetAddress.parse( correoEnviar ) );

                //Cuerpo
                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                String cuerpo = "Que tenga buenos dias, se le saluda de parte de el supermercado" +
                        " el economico. \n\n este es su contraseña actual que se le a enviado a esta hora "+currentDateTimeString+"" +
                        " \n\nContraseña: " +pass_bd;

                mimeMessage.setContent( cuerpo, "text/html; charset=utf-8" );
                Transport.send( mimeMessage );

            }

        } catch (MessagingException e) {
            //e.printStackTrace();
        }

        Toast.makeText( getApplicationContext(),"Correo enviado exitosamente!!!",Toast.LENGTH_SHORT ).show();


    }

    private void buscarUsuario(){


        String url = "http://167.99.158.191/Api_pedidos_ProyectoFinal/pedidos/getPassword.php";


        HashMap<String, String> params = new HashMap<String, String>();

        params.put("usuario", textInputLayoutuser.getEditText().getText().toString());
        params.put("correo", textInputLayout.getEditText().getText().toString());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest( url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {


                        try {


                         pass_bd =  jsonObject.getString( "contrasenia" );
                         EnviarCorreo();

                        } catch (JSONException e) {
                            //snackbar( "Contraseña incorrecta" );
                            Toast.makeText(getApplicationContext(), "tomar pass  "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }




                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}