package com.example.pfghiking;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class Ruta_update extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference rDataBase;
    private ModelRuta itemDetails;
    private EditText title;
    private EditText description;
    private EditText distance;
    private EditText altitude;
    private EditText time;
    private EditText country;
    private EditText city;
    private ImageView imageView = null;
    private String imagen = "";
    private List<ModelRuta> elements;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_ruta_update );
        
    //Para ocultar el actionBar
    getSupportActionBar().hide();


    mAuth = FirebaseAuth.getInstance();
    rDataBase = FirebaseDatabase.getInstance( "https://pfghiking-default-rtdb.europe-west1.firebasedatabase.app/" ).getReference();

    title = findViewById( R.id.TV_nombreRuta_UR );
    description = findViewById( R.id.TV_descRuta_UR );
    distance = findViewById( R.id.TV_distRuta_UR );
    altitude = findViewById( R.id.TV_desnRuta_UR );
    time = findViewById( R.id.TV_timeRuta_UR );
    country = findViewById( R.id.TV_paisRuta_UR );
    city = findViewById( R.id.TV_cityRuta_UR );
    imageView = findViewById( R.id.imgRuta_UR );


    itemDetails = (ModelRuta ) getIntent().getExtras().getSerializable( "itemDetails" );

        title.setText( itemDetails.getNombre_ruta()); ;
        description.setText( itemDetails.getDescripcion() );
        distance.setText( itemDetails.getDistancia() );
        altitude.setText( itemDetails.getDesnivel() );
        time.setText( itemDetails.getTiempo() );
        country.setText( itemDetails.getPais() );
        city.setText( itemDetails.getCiudad() );


    getInfoRuta(); //metodo para mostrar la información de la ruta

}


    private void getInfoRuta(){
        itemDetails = ( ModelRuta ) getIntent().getExtras().getSerializable( "itemDetails" );
        String id = mAuth.getCurrentUser().getUid();
        rDataBase.child( "rutas" ).child( rDataBase.child( "rutas" ).getKey() ).addValueEventListener( new ValueEventListener() {
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

                    imagen = getUrl(snapshot.child( "imagen" ).getValue().toString());
                    Glide.with( imageView.getContext() ).load( imagen ).into( imageView );

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        } );


    }



    private String getUrl(String urlRuta) {

        rDataBase.child( "rutas" ).child(  "id"  ).get().addOnSuccessListener( new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {

                String url = dataSnapshot.getKey().toString();

                //try {
                if (!url.equals( "" ) ){
                    Toast toast = Toast.makeText( getApplicationContext(), "Cargando Foto", Toast.LENGTH_SHORT);
                    //toast.setGravity( Gravity.TOP, 0, 1 );
                    toast.show();
                    Glide.with( Ruta_update.this ).load( imagen )
                            .into( (ImageView) findViewById( R.id.imageView ) );
                }
                // }catch (Exception e){
                //   Log.v( "Error", "e: "+ e );
                //}

            }
        } );
        return urlRuta;
    }

    
} //FIN DE LA CLASE ACTUALIZAR RUTA


