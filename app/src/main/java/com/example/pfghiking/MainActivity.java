package com.example.pfghiking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    Button myButton_is;
    Button myButton_cc;
    TextView AM_textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        getSupportActionBar().hide();

        // Obtain the FirebaseAnalytics instance.



        // --------------- CAMBIAR DE COLOR LOS BOTONES ---------------------- //

        Button myButton_is  = this.<Button>findViewById(R.id.button_iniciar_sesion_AM);
        myButton_is.setBackgroundColor(0XFF6A5F4B);

        Button myButton_cc  = this.<Button>findViewById( R.id.button_crear_cuenta_AM );
        myButton_cc.setBackgroundColor(0XFF6A5F4B);


        // ---------------  BOTONES DE INTENT ---------------------- //

        myButton_is.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent is_MA = new Intent(MainActivity.this, Iniciar_Sesion.class);
                is_MA.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity( is_MA );
                finish();

            }
        } );

        myButton_cc.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cc_MA = new Intent(MainActivity.this, Crear_Cuenta.class);
                cc_MA.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity( cc_MA );
            }
        } );





    }
}