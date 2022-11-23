package com.example.pfghiking;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class Info_Ruta extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference rDataBase;
    private ModelRuta itemDetails;
    private TextView title;
    private TextView description;
    private TextView distance;
    private TextView altitude;
    private TextView time;
    private TextView country;
    private TextView city;
    private ImageView imageView;
    private String urlRuta;
    private Button editButton;
    private List<ModelRuta> elements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_info_ruta );

        //Para ocultar el actionBar
        getSupportActionBar().hide();


        mAuth = FirebaseAuth.getInstance();
        rDataBase = FirebaseDatabase.getInstance( "https://pfghiking-default-rtdb.europe-west1.firebasedatabase.app/" ).getReference();

        title = findViewById( R.id.TV_nombreRuta_IR );
        description = findViewById( R.id.TV_descRuta_IR );
        distance = findViewById( R.id.TV_distRuta_IR );
        altitude = findViewById( R.id.TV_desnRuta_IR );
        time = findViewById( R.id.TV_timeRuta_IR );
        country = findViewById( R.id.TV_paisRuta_IR );
        city = findViewById( R.id.TV_cityRuta_IR );
        imageView = findViewById( R.id.imgRuta_IR );


        itemDetails = (ModelRuta ) getIntent().getExtras().getSerializable( "itemDetails" );
        title.setText( itemDetails.getNombre_ruta() );
        description.setText( itemDetails.getDescripcion() );
        distance.setText( itemDetails.getDistancia() );
        altitude.setText( itemDetails.getDesnivel() );
        time.setText( itemDetails.getTiempo() );
        country.setText( itemDetails.getPais() );
        city.setText( itemDetails.getCiudad() );
        imageView.setImageResource( Integer.parseInt( itemDetails.getImagen().toString() ) );


        getInfoRuta(); //metodo para mostrar la información de la ruta

    }


    private void getInfoRuta(){
        itemDetails = ( ModelRuta ) getIntent().getExtras().getSerializable( "itemDetails" );
        String id = mAuth.getCurrentUser().getUid();
        rDataBase.child( "rutas" ).child( "id" ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if ( snapshot.exists()){
                    String nombre_ruta = snapshot.child( "rutas" ).getValue().toString();
                    title.setText( nombre_ruta );

                    String descripcion = snapshot.child( "rutas" ).getValue().toString();
                    description.setText( descripcion );

                    String distancia = snapshot.child( "rutas" ).getValue().toString();
                    distance.setText( distancia );

                    String desnivel = snapshot.child( "rutas" ).getValue().toString();
                    altitude.setText( desnivel );

                    String tiempo = snapshot.child( "rutas" ).getValue().toString();
                    time.setText( tiempo );

                    String pais = snapshot.child( "rutas" ).getValue().toString();
                    country.setText( pais );

                    String ciudad = snapshot.child( "rutas" ).getValue().toString();
                    city.setText( ciudad );


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        } );


    }
}