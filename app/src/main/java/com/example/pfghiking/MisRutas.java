package com.example.pfghiking;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MisRutas extends AppCompatActivity implements     //PARA REMOVER ITEM RV
        RecyclerRutaTouchHelper.RecyclerRutaTouchHelperListener {    //PARA REMOVER ITEM RV

    private FirebaseAuth mAuth;
    private RecyclerView rvLista;
    private RecyclerRutaAdapter adapter;
    private List<ModelRuta> elements2 = new ArrayList<>();
    private List<ModelRuta> dataOriginal;
    private FirebaseDatabase mData;
    private RecyclerRutaAdapter.OnItemClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_mis_rutas );

        //Para ocultar el actionBar
        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();

        rvLista = findViewById( R.id.RV_Rutas_PP );
        rvLista.setLayoutManager( new LinearLayoutManager( this ) );

        elements2 = new ArrayList<>();

        mData = FirebaseDatabase.getInstance( "https://pfghiking-default-rtdb.europe-west1.firebasedatabase.app/" );

        adapter = new RecyclerRutaAdapter( elements2, getApplicationContext(), listener);
        rvLista.setAdapter( adapter );

        //PARA REMOVER ITEM RV
        ItemTouchHelper.SimpleCallback simpleCallback =
                new RecyclerRutaTouchHelper( 0, ItemTouchHelper.LEFT, MisRutas.this  );

        //PARA REMOVER ITEM RV
        new ItemTouchHelper( simpleCallback ).attachToRecyclerView( rvLista );



        ValueEventListener elements = mData.getReference().child( "rutas" ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                MisRutas.this.elements2.removeAll( MisRutas.this.elements2 );

                for ( DataSnapshot snapshot :
                dataSnapshot.getChildren()){
                    ModelRuta ruta1 = snapshot.getValue(ModelRuta.class);
                    MisRutas.this.elements2.add( ruta1 );
                }
                adapter.notifyDataSetChanged();
                buscar( Objects.requireNonNull( mAuth.getCurrentUser().getEmail() ) );

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        } );
    }

    // esto busca las rutas del usuario que ha ingresado al app
    public void  buscar (final String txtBuscar){
        int longitud = txtBuscar.length();
        if (longitud == 0){
            elements2.clear();
            elements2.addAll( dataOriginal );
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                List<ModelRuta> colection = elements2.stream()
                        .filter( i -> i.getUsers().getEmail().toLowerCase().contains( txtBuscar.toLowerCase() ) )
                        .collect( Collectors.toList());
                elements2.clear();
                elements2.addAll( colection );
            } else{
                for (ModelRuta a : dataOriginal){
                    if (a.getNombre_ruta().toLowerCase().contains( txtBuscar.toLowerCase() ));
                    dataOriginal.add( a );
                }
            }
        }

        adapter.notifyDataSetChanged();
    }


    //PARA REMOVER ITEM RV
    @Override
    public void onSwipe(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof RecyclerRutaAdapter.RecyclerHolder){
            String nombre = elements2.get( viewHolder.getAdapterPosition() ).getNombre_ruta();
            final ModelRuta rutaBorrada = elements2.get( viewHolder.getAdapterPosition() );
            final int deleteIntex = viewHolder.getAdapterPosition();

            adapter.removeItem( viewHolder.getAdapterPosition() );

            recuperarRutaBorrada( viewHolder, nombre, rutaBorrada, deleteIntex); //recuperarRutaBorrada( viewHolder, nombre,  rutaBorrada, deleteIntex);

        }
    }

    private void recuperarRutaBorrada(RecyclerView.ViewHolder viewHolder, String nombre,
                                      final ModelRuta rutaBorrada, final int deleteIntex){ // String nombre

        Snackbar snackbar = Snackbar.make( ((RecyclerRutaAdapter.RecyclerHolder)viewHolder).layoutDelete,
                "ELIMINADA", Snackbar.LENGTH_SHORT);
        snackbar.setAction( "DESHACER", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.restoreItem( rutaBorrada, deleteIntex );
            }
        } );

        snackbar.setActionTextColor( Color.GREEN );
        snackbar.show();

    }


}