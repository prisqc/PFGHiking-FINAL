package com.example.pfghiking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance( "https://pfghiking-default-rtdb.europe-west1.firebasedatabase.app/" );


        myButton_pu  = this.<Button>findViewById(R.id.button_publicar_CR);
        myButton_pu.setBackgroundColor(0XFF6A5F4B);

        myButton_pu.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                title = editTextName.getText().toString();
                description = editTextDescr.getText().toString();
                distancia = editTextDist.getText().toString();
                desnivel = editTextDesn.getText().toString();
                time = editTexttime.getText().toString();
                pais = editTextPais.getText().toString();
                city = editTextCity.getText().toString();

                mData.getReference().child( "Users" ).child( mAuth.getCurrentUser().getUid() ).addValueEventListener( new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        mData.getReference().child( "Usuario" ).child( mAuth.getCurrentUser().getUid() ).removeEventListener( this );
                        if(dataSnapshot.exists()){
                            ModelUser usu = dataSnapshot.getValue( ModelUser.class);

                            usuario = new ModelUser(usu.getId(), (dataSnapshot.child( "email" ).getValue()).toString() , usu.getNombre(), usu.getId() );
                            if (!title.isEmpty() && !description.isEmpty() && !distancia.isEmpty()
                                    && !desnivel.isEmpty() && !time.isEmpty() && !pais.isEmpty() ) {
                                ModelRuta ruta = new ModelRuta(title , usuario, description, distancia, desnivel, time, pais, city);
                                publicarRuta( ruta );

                                Toast.makeText( Crear_Ruta.this, "Ruta Publicada Correctamente", Toast.LENGTH_SHORT ).show();
                                startActivity( new Intent( getApplicationContext(), Principal.class ) );
                                finish();

                            } else {
                                Toast.makeText( Crear_Ruta.this, "campos sin llenar", Toast.LENGTH_SHORT ).show();
                            }

                        } else {
                            Toast.makeText( Crear_Ruta.this, "Hay un error, no se puede publicar", Toast.LENGTH_SHORT ).show();
                            startActivity( new Intent( getApplicationContext(), Crear_Ruta.class ) );
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                } );


            }

        });
    }

            public void publicarRuta(ModelRuta ruta) {

                String key = mData.getReference().child("rutas").push().getKey();
                mData.getReference().child( "rutas" ).child(key).setValue( ruta );

            }



    }


