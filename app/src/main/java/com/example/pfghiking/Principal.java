package com.example.pfghiking;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Principal extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private RecyclerView rvLista;
    private RecyclerRutaAdapter adapter;
    private List<ModelRuta> elements;
    private FirebaseDatabase mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_principal );

        //Para ocultar el actionBar
        //getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();

        rvLista = findViewById(R.id.RV_Rutas_PP);
        rvLista.setLayoutManager(new LinearLayoutManager(this));

        elements = new ArrayList<>();

        mData = FirebaseDatabase.getInstance("https://pfghiking-default-rtdb.europe-west1.firebasedatabase.app/");

        adapter = new RecyclerRutaAdapter(elements);
        rvLista.setAdapter(adapter);





    }

    //PARA CREAR EL MENU EN EL PANTALLA PRINCIPAL
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.menu, menu );
        return true;
    }


    //PARA CREAR OPCIONES DEL MENU EN LA PANTALLA PRINCIPAL
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()) {
            case R.id.opcion1:
                Intent i = new Intent( Principal.this, Perfil.class );
                i.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity( i );
                finish();
                return true;
            case R.id.opcion2:
                Intent i2 = new Intent( Principal.this, MainActivity.class );
                i2.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity( i2 );
                finish();
                return true;

            default:
                return super.onOptionsItemSelected( item );
        }
    }



}