package com.example.pfghiking;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Crear_Ruta extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_crear_ruta );

        //Para ocultar el actionBar
        getSupportActionBar().hide();

    }
}