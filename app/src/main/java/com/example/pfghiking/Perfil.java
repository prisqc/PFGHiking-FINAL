package com.example.pfghiking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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



    // Pasa información del usuario del registro
        String id = mAuth.getCurrentUser().getUid();
        mDataBase.getReference().child( "Users" ).child(  mAuth.getCurrentUser().getUid() ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String name = snapshot.child("usuario").getValue().toString();
                    tvName.setText(name);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        } );



        //Acción para cambiar de página de Perfil a Crear Ruta
        crearRuta.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Perfil.this, Crear_Ruta.class);
                i.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity( i );
                finish();

            }
        } );




        //Acción para cambiar de página de Perfil a Mis Rutas
        misRutas.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Perfil.this,MisRutas.class);
                i.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity( i );
                finish();

            }
        } );




    }
}