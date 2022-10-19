package com.example.pfghiking;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Perfil extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDataBase;
    private ModelUser user;
    private TextView tvName;
    private Button editarPerfil;
    private Button crearRuta;
    private Button misRutas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_perfil );

        //Para ocultar el actionBar
        getSupportActionBar().hide();


        mAuth = FirebaseAuth.getInstance();


        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDataBase = FirebaseDatabase.getInstance( "https://pfghiking-default-rtdb.europe-west1.firebasedatabase.app/" );


        tvName = findViewById( R.id.TV_name_Profile);

        editarPerfil = findViewById( R.id.button_editar_PE );
        editarPerfil.setBackgroundColor(0XFF6A5F4B);

        crearRuta = findViewById( R.id.button_CrearRuta_PE );
        crearRuta.setBackgroundColor(0XFF6A5F4B);

        misRutas = findViewById( R.id.button_MisRutas_PE );
        misRutas.setBackgroundColor(0XFF6A5F4B);

    }
}