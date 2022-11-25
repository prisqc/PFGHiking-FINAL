package com.example.pfghiking;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

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
    private Button addPic;
    private Button updRuta;
    private ImageView imageView = null;
    private String imagen = "";
    private List<ModelRuta> elements;
    private ModelUser usuario ;

    private StorageReference mStorage;
    private ProgressDialog progressDialog;
    private static final int GALLERY_INTENT = 1;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_ruta_update );
        
    //Para ocultar el actionBar
    getSupportActionBar().hide();


    mAuth = FirebaseAuth.getInstance();
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    rDataBase = FirebaseDatabase.getInstance( "https://pfghiking-default-rtdb.europe-west1.firebasedatabase.app/" ).getReference();

    title = findViewById( R.id.TV_nombreRuta_UR );
    description = findViewById( R.id.TV_descRuta_UR );
    distance = findViewById( R.id.TV_distRuta_UR );
    altitude = findViewById( R.id.TV_desnRuta_UR );
    time = findViewById( R.id.TV_timeRuta_UR );
    country = findViewById( R.id.TV_paisRuta_UR );
    city = findViewById( R.id.TV_cityRuta_UR );
    imageView = findViewById( R.id.imgRuta_UR );

     progressDialog = new ProgressDialog( this );

        itemDetails = (ModelRuta ) getIntent().getExtras().getSerializable( "itemDetails" );

        title.setText( itemDetails.getNombre_ruta()); ;
        description.setText( itemDetails.getDescripcion() );
        distance.setText( itemDetails.getDistancia() );
        altitude.setText( itemDetails.getDesnivel() );
        time.setText( itemDetails.getTiempo() );
        country.setText( itemDetails.getPais() );
        city.setText( itemDetails.getCiudad() );


        //Agregar foto de perfil - instanciar onjeto
        addPic = findViewById( R.id.button_addPic_UR );
        addPic.setBackgroundColor( 0XFF6A5F4B );

        updRuta = findViewById( R.id.button_update_UR );
        updRuta.setBackgroundColor( 0XFF6A5F4B );

        mStorage = FirebaseStorage.getInstance().getReference();


        addPic.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( Intent.ACTION_PICK );
                intent.setType( "image/*" );
                startActivityForResult( intent, GALLERY_INTENT );

            }
        } );



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

                    imagen = snapshot.child( "imagen" ).toString();
                    getFotoPp( snapshot.child( "imagen" ).getValue().toString());


                }

                updRuta.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rutaActualizada();
                    }
                } );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        } );


    }




    //METODO PARA LLAMAR A LA URL DE LA FOTO QUE CONTIENE EL RV
    private void getFotoPp( String imagen) {

        rDataBase.child( "Users" ).child( mAuth.getCurrentUser().getUid() ).get().addOnSuccessListener( new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {

                String url = dataSnapshot.getKey().toString();

                try {
                    if (!url.equals( "" ) ){
                        Toast toast = Toast.makeText( getApplicationContext(), "Cargando Foto", Toast.LENGTH_SHORT);
                        toast.show();
                        Glide.with( Ruta_update.this ).load( imagen )
                                .into( imageView );

                    }
                }catch (Exception e){
                    Log.v( "Error", "e: "+ e );
                }
            }
        } );
    }





    //AGREGAR FOTO DE PERFIL DE LA RUTA - METODO ONACTIVITYRESULT
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {

            progressDialog.setTitle( "Subiendo..." );
            progressDialog.setMessage( "Subiendo foto a Firebase" );
            progressDialog.setCancelable( false );
            progressDialog.show();


            Uri uri = data.getData();
            StorageReference filePath = mStorage.child( "fotos" ).child( uri.getLastPathSegment() );



            filePath.putFile( uri ).addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss(); //finaliza la barra de carga

                    //OBTIENE EL URI DE LA FOTO
                    filePath.getDownloadUrl().addOnSuccessListener( new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            Glide.with( Ruta_update.this)
                                    .load(uri).into((ImageView) findViewById( R.id.imgRuta_UR ));
                            Toast.makeText( Ruta_update.this, "Se subió correctamente la foto", Toast.LENGTH_SHORT ).show();

                            //recupera la Url y asignala en el campo determinado "imagen"
                            imagen = uri.toString();
                            rDataBase.child( "rutas" ).child( "id" ).child( "foto" ).setValue( imagen );

                        }
                    } );


                }
            } );

        }

    } // FIN DEL METODO OnActivityResult




    // METODO QUE DEVUELVE LA URL DE LA IMAGEN DE LA RUTA QUE SE SELECCIONADO
    private String getUrl(String urlRuta) {

        rDataBase.child( "rutas" ).child(  "id"  ).get().addOnSuccessListener( new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {

                String url = dataSnapshot.getKey().toString();

                if (!url.equals( "" ) ) {
                    Toast toast = Toast.makeText( getApplicationContext(), "Cargando Foto", Toast.LENGTH_SHORT );
                    toast.show();
                    Glide.with( Ruta_update.this ).load( imagen )
                            .into( ( ImageView ) findViewById( R.id.imageView ) );
                }
            }
        } );
        return urlRuta;
    }




    //METODO QUE ACTUALIZA LA LISTA
    private void rutaActualizada(){

        ModelRuta r = new ModelRuta();

        itemDetails.setNombre_ruta( title.getText().toString().trim() );
        itemDetails.setDescripcion( description.getText().toString().trim() );
        itemDetails.setDistancia( distance.getText().toString().trim() );
        itemDetails.setDesnivel( altitude.getText().toString().trim() );
        itemDetails.setTiempo( time.getText().toString().trim() );
        itemDetails.setPais( country.getText().toString().trim() );
        itemDetails.setCiudad( city.getText().toString().trim() );


        rDataBase.child( "Users" ).child( mAuth.getCurrentUser().getUid() ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                rDataBase.child( "Usuario" ).child( mAuth.getCurrentUser().getUid() ).removeEventListener( this );
                if(dataSnapshot.exists()){
                    ModelUser usu = dataSnapshot.getValue( ModelUser.class);

                    usuario = new ModelUser(usu.getId(), (dataSnapshot.child( "email" ).getValue()).toString() , usu.getNombre() );
                    if (!itemDetails.nombre_ruta.isEmpty() && !itemDetails.descripcion.isEmpty() && !itemDetails.distancia.isEmpty()) {
                        ModelRuta ruta = new ModelRuta();
                        publicarRuta( ruta );

                        Toast.makeText( Ruta_update.this, "Ruta actualizada Correctamente", Toast.LENGTH_SHORT ).show();
                        startActivity( new Intent( getApplicationContext(), Principal.class ) );
                        finish();

                    } else {
                        Toast.makeText( Ruta_update.this, "campos sin llenar", Toast.LENGTH_SHORT ).show();
                    }

                } else {
                    Toast.makeText( Ruta_update.this, "Hay un error, no se pudo actualizarzar", Toast.LENGTH_SHORT ).show();
                    startActivity( new Intent( getApplicationContext(), Crear_Ruta.class ) );
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        } );


    }


    // METODO PUBLICAR RUTA
    public void publicarRuta(ModelRuta ruta) {

        String key = rDataBase.child("rutas").push().getKey();
        rDataBase.child( "rutas" ).child(key).setValue( ruta );

    }





} //FIN DE LA CLASE ACTUALIZAR RUTA


