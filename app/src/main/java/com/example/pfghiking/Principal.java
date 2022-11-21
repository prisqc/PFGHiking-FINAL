package com.example.pfghiking;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Principal extends AppCompatActivity  implements SearchView.OnQueryTextListener {

    private FirebaseAuth mAuth;
    private RecyclerView rvLista;
    private RecyclerRutaAdapter adapter;
    private SearchView buscarSV;
    private List<ModelRuta> elements;
    private FirebaseDatabase mData;

    private SharedPreferences preferences; //SHAREPREFERENCES - MANTENER SESION INICIADA


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_principal );

        //Para ocultar el actionBar
        //getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();

        rvLista = findViewById( R.id.RV_Rutas_PP );
        buscarSV = findViewById( R.id.searchView_PP );


        rvLista.setLayoutManager( new LinearLayoutManager( this ) );

        elements = new ArrayList<ModelRuta>();

        mData = FirebaseDatabase.getInstance( "https://pfghiking-default-rtdb.europe-west1.firebasedatabase.app/" );


        adapter = new RecyclerRutaAdapter( elements , getApplicationContext());
        rvLista.setAdapter( adapter );

        //SHAREPREFERENCES - MANTENER SESION INICIADA
        init();
       // validarSesion();
        int usuario_id = preferences.getInt( "usuario_id", 0 );
        String usuario = preferences.getString( "usuario", null );

        if(usuario_id > 0 && usuario != null){

        }         //SHAREPREFERENCES - MANTENER SESION INICIADA


        // Mostrar rutas creadas
        mData.getReference().child( "rutas" ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                elements.removeAll( elements );
                for (DataSnapshot snapshot :
                        dataSnapshot.getChildren()) {
                    ModelRuta rut = snapshot.getValue( ModelRuta.class );
                    elements.add( rut );
                }
                adapter.notifyDataSetChanged( );
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        } );

        buscarSV.setOnQueryTextListener( this ); //para buscar en SV

    } // FIN METODO ONCREATE




    //SHAREPREFERENCES - MANTENER SESION INICIADA
    private void init(){
        preferences = getSharedPreferences( "Preferences", MODE_PRIVATE );
    }

    private void cerrarSesion(){
        preferences.edit().clear().apply();
        Intent i2 = new Intent( Principal.this, MainActivity.class );
        i2.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
        startActivity( i2 );
    }

    //private void validarSesion() {

    //}
    //fin SHAREPREFERENCES


    //**********************************************************************************************
    //PARA CREAR EL MENU EN EL PANTALLA PRINCIPAL
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.menu, menu );
        return true;
    }



    //**********************************************************************************************
    //PARA CREAR OPCIONES DEL MENU EN LA PANTALLA PRINCIPAL
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.opcion1:
                Intent i = new Intent( Principal.this, Perfil.class );
                i.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity( i );
                // finish();
                return true;
            case R.id.opcion2:
              //  Intent i2 = new Intent( Principal.this, MainActivity.class );
             //   i2.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
               // startActivity( i2 );
                finish();
                cerrarSesion(); // SHAREPREFERENCES - MANTENER LA SESIÓN INCIADA
                return true;

            default:
                return super.onOptionsItemSelected( item );
        }
    } // fin del menú





    //**********************************************************************************************
    //metodo para Buscar en SV
    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.filtrado( newText );
        return false;
    }





    //**********************************************************************************************
    //metodo para SEARCH VIEW con querys para buscar una ruta por nombre con SearchView





} // fin de la clase principal