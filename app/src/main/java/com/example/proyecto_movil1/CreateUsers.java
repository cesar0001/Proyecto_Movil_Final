package com.example.proyecto_movil1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
 import android.graphics.Bitmap;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputLayout;



public class CreateUsers extends AppCompatActivity {

    static final int RESULT_GALLERY_IMG = 100;
    Bitmap photo = null;
    ImageView imageView;

    public static String CULatitud;
    public static String CULongitud;

    Button buttonNext;

    TextInputLayout txtUser, txtPhone, txtDirection, txtUsuarioOf, txtContra, txtCorreo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_create_users );

        getSupportActionBar().hide();

        imageView = (ImageView) findViewById( R.id.imagenCrearUser );
        buttonNext = (Button) findViewById( R.id.buttonNext );
        txtUser = (TextInputLayout) findViewById( R.id.txtuser );
        txtPhone = (TextInputLayout) findViewById( R.id.txttelefono );
        txtDirection = (TextInputLayout) findViewById( R.id.txtDireccion );
        txtUsuarioOf = (TextInputLayout) findViewById( R.id.txtUsuarioOf );
        txtContra = (TextInputLayout) findViewById( R.id.contraCreateUsers );
        txtCorreo = (TextInputLayout) findViewById( R.id.CorreoCUN );

        //buttonNext.setVisibility( View.INVISIBLE );


        findViewById( R.id.buttonNext ).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (photo == null) {
                    AlertaDialogo( "Ingrese la foto","Foto" );
                } else {
                    if (txtUser.getEditText().getText().toString().trim().length() == 0) {
                        txtUser.setError( "Ingrese este campo" );
                    } else {
                        txtUser.setError( null );
                        if (txtPhone.getEditText().getText().toString().trim().length() == 0) {
                            txtPhone.setError( "Ingrese este campo" );
                        } else {
                            txtPhone.setError( null );
                            if (txtDirection.getEditText().getText().toString().trim().length() == 0) {
                                txtDirection.setError( "Ingrese este campo" );
                            } else {
                                txtDirection.setError( null );
                                if (txtContra.getEditText().getText().toString().trim().length() == 0) {
                                    txtContra.setError( "Ingrese este campo" );
                                } else {
                                    txtContra.setError( null );
                                    if (txtCorreo.getEditText().getText().toString().trim().length() == 0) {
                                        txtCorreo.setError( "Ingrese este campo" );
                                    } else {
                                        txtCorreo.setError( null );
                                        if (txtUsuarioOf.getEditText().getText().toString().trim().length() == 0) {
                                            txtUsuarioOf.setError( "Ingrese este campo" );
                                        } else {
                                            txtUsuarioOf.setError( null );
                                            Intent intent = new Intent( getApplicationContext(), ConfirmacionCodigo.class );

                                            //enviar datos a la clase confirmar_codigo

                                            ConfirmacionCodigo.setNombreCC( txtUser.getEditText().getText().toString() );
                                            ConfirmacionCodigo.setTelefonoCC( txtPhone.getEditText().getText().toString() );
                                            ConfirmacionCodigo.setDireccionCC( txtDirection.getEditText().getText().toString() );
                                            ConfirmacionCodigo.setUsuarioCC( txtUsuarioOf.getEditText().getText().toString() );
                                            ConfirmacionCodigo.setContraseniaCC( txtContra.getEditText().getText().toString() );
                                            ConfirmacionCodigo.setCorreoCC( txtCorreo.getEditText().getText().toString() );
                                            ConfirmacionCodigo.setLatitudCC( MainActivity.getLatitud() );
                                            ConfirmacionCodigo.setLongitudCC( MainActivity.getLongitud() );
                                            ConfirmacionCodigo.setUrl_fotoCC( photo );




                                            startActivity( intent );

                                        }

                                    }


                                }
                            }
                        }
                    }
                }

            }
        } );


    }


    private void AlertaDialogo(String mensaje, String title) {

        AlertDialog.Builder alerta = new AlertDialog.Builder( this );
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



    public void AbrirGaleria(View view) {
        GaleriaImagenes();
    }

    private void GaleriaImagenes() {
        Intent photoPickerIntent = new Intent( Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
        startActivityForResult( photoPickerIntent, RESULT_GALLERY_IMG );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );

        Uri imageUri;

        if (resultCode == RESULT_OK && requestCode == RESULT_GALLERY_IMG) {
            imageUri = data.getData();
            //uri = imageUri;
            imageView.setImageURI( imageUri );

            try {
                photo = MediaStore.Images.Media.getBitmap( this.getContentResolver(), imageUri );
            } catch (Exception ex) {
            }
        }
    }




}