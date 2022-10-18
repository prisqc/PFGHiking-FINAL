package com.example.pfghiking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Crear_Ruta extends AppCompatActivity {

    Button myButton_pu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_crear_ruta );

        //Para ocultar el actionBar
        getSupportActionBar().hide();

        Button myButton_pu  = this.<Button>findViewById(R.id.button_publicar_NR);
        myButton_pu.setBackgroundColor(0XFF6A5F4B);


        myButton_pu.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            //Cuando publicas la nueva ruta, te lleva a la pantalla principal... por verificar!!!
                Intent pu_NR = new Intent(Crear_Ruta.this, Principal.class);
                pu_NR.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity( pu_NR );

            }
        } );


    }


}