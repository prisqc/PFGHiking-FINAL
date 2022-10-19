package com.example.pfghiking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Crear_Ruta extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextDescr;
    private EditText editTextDist;
    private EditText editTextDesn;
    private EditText editTexttime;
    private EditText editTextPais;
    private EditText editTextCity;
    private Button myButton_pu;
    private String title = "";
    private String description = "";
    private String distancia = "";
    private String desnivel = "";
    private String time = "";
    private String pais = "";
    private String city = "";
    private ModelUser usuario;

    private FirebaseAuth mAuth;
    private FirebaseDatabase mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_crear_ruta );

        //Para ocultar el actionBar
        getSupportActionBar().hide();

        editTextName = findViewById( R.id.ET_nombreRuta_CR );
        editTextDescr = findViewById( R.id.ET_descRuta_CR );
        editTextDist = findViewById( R.id.ET_distRuta_CR );
        editTextDesn = findViewById( R.id.ET_desnRuta_CR );
        editTexttime = findViewById( R.id.ET_timeRuta_CR );
        editTextPais = findViewById( R.id.ET_paisRuta_CR );
        editTextCity = findViewById( R.id.ET_cityRuta_CR );


        Button myButton_pu  = this.<Button>findViewById(R.id.button_publicar_CR);
        myButton_pu.setBackgroundColor(0XFF6A5F4B);


        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance( "https://pfghiking-default-rtdb.europe-west1.firebasedatabase.app/" );


        myButton_pu.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            //Cuando publicas la nueva ruta, te lleva a la pantalla principal... por verificar!!!
                Intent pu_CR = new Intent(Crear_Ruta.this, Principal.class);
                pu_CR.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity( pu_CR );

            }
        } );


    }


}