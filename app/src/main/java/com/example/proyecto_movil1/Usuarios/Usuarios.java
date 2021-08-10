package com.example.proyecto_movil1.Usuarios;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.proyecto_movil1.R;
import com.google.android.material.textfield.TextInputLayout;


import java.text.DateFormat;
import java.util.Date;
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
    private Button send;

    //Correo y contraseña del que va a enviar el mensaje
    private String correo = "servidormovil420@gmail.com";
    private String contraseña = "servidormovil";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_usuarios );

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textInputLayout = (TextInputLayout)findViewById( R.id.campoCorreo );
        send = (Button)findViewById( R.id.sendEmail );

        send.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(textInputLayout.getEditText().getText().toString().trim().length() == 0 ){
                    textInputLayout.setError( "Este campo es obligatorio" );
                }else{
                    textInputLayout.setError( null );
                    EnviarCorreo();
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
                        " \n\nEsta Opcion esta en modo Beta.";

                mimeMessage.setContent( cuerpo, "text/html; charset=utf-8" );
                Transport.send( mimeMessage );

            }

        } catch (MessagingException e) {
            //e.printStackTrace();
        }

        Toast.makeText( getApplicationContext(),"Correo enviado exitosamente!!!",Toast.LENGTH_SHORT ).show();


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}