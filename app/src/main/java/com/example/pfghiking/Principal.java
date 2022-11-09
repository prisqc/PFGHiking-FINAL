package com.example.pfghiking;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Principal extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private FirebaseAuth mAuth;
    private RecyclerView rvLista;
    private RecyclerRutaAdapter adapter;
    private SearchView txtBuscar2;
    private List<ModelRuta> elements;
    private FirebaseDatabase mData;
    private RecyclerRutaAdapter.RecyclerViewClickListener listener; // para generar eventos en el RV

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_principal );

        //Para ocultar el actionBar
        //getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();

        rvLista = findViewById(R.id.RV_Rutas_PP);
        txtBuscar2 = findViewById( R.id.searchView_PP );

        setUserInfo(); //para generar eventos en el RV
        setAdapter(); //para generar eventos en el RV


        rvLista.setLayoutManager(new LinearLayoutManager(this));

        elements = new ArrayList<>();

        mData = FirebaseDatabase.getInstance("https://pfghiking-default-rtdb.europe-west1.firebasedatabase.app/");
        
        // Mostrar rutas creadas
        ValueEventListener elements = mData.getReference().child("rutas").addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Principal.this.elements.removeAll(Principal.this.elements);
                for (DataSnapshot snapshot :
                        dataSnapshot.getChildren()) {
                    ModelRuta rut = snapshot.getValue(ModelRuta.class);
                    Principal.this.elements.add(rut);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        txtBuscar2.setOnQueryTextListener( this );


    }



    //para generar eventos en el RV
    private void setAdapter() {
        setOnClickListener();
        RecyclerRutaAdapter adapter = new RecyclerRutaAdapter( elements, listener);
        RecyclerView.LayoutManager rl = new LinearLayoutManager( getApplicationContext() );
        rvLista.setLayoutManager( rl );
        rvLista.setItemAnimator( new DefaultItemAnimator() );
        rvLista.setAdapter( adapter );

    }

    // para generar eventos en el RV, pasar datos a Info_Ruta
    private void setOnClickListener() {
        listener = new RecyclerRutaAdapter.RecyclerViewClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent i = new Intent(getApplicationContext(), Info_Ruta.class);
                i.putExtra( "title" , elements.get( position ).getNombre_ruta() );
                i.putExtra( "description" , elements.get( position ).getDescripcion() );
                i.putExtra( "distance" , elements.get( position ).getDistancia() );
                i.putExtra( "altitude" , elements.get( position ).getDesnivel() );
                i.putExtra( "time" , elements.get( position ).getTiempo() );
                i.putExtra( "country" , elements.get( position ).getPais() );
                i.putExtra( "city" , elements.get( position ).getCiudad() );
                startActivity( i );
            }
        };
    }

    // para generar eventos en el RV
    private void setUserInfo(){
        List<ModelRuta> elements = new ArrayList<ModelRuta>();
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
               // finish();
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
    } // fin del menú



    //metodo para SEARCH VIEW con querys para buscar una ruta por nombre con SearchView

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.filtrado( newText );
        return false;
    }




} // fin de la clase principal