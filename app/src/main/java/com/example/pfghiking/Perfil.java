package com.example.pfghiking;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Perfil extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDataBase;
    private ModelUser user;
    private TextView tvName;
    private Button crearRuta;
    private Button misRutas;

    //Agregar foto de perfil
    private Button btnAdd;
    private ImageView foto;
    private StorageReference mStorage;
    private ProgressDialog progressDialog;
    private String urlFoto;
    private static final int GALLERY_INTENT = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_perfil );

        //Para ocultar el actionBar
        getSupportActionBar().hide();


        mAuth = FirebaseAuth.getInstance();

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDataBase = FirebaseDatabase.getInstance( "https://pfghiking-default-rtdb.europe-west1.firebasedatabase.app/" );


        tvName = findViewById( R.id.TV_name_Profile );

        crearRuta = findViewById( R.id.button_CrearRuta_PE );
        crearRuta.setBackgroundColor( 0XFF6A5F4B );

        misRutas = findViewById( R.id.button_MisRutas_PE );
        misRutas.setBackgroundColor( 0XFF6A5F4B );


        //Agregar foto de perfil - instanciar onjeto
        btnAdd = findViewById( R.id.btn_AddFoto );
        btnAdd.setBackgroundColor( 0XFF6A5F4B );

        mStorage = FirebaseStorage.getInstance().getReference();


        btnAdd.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( Intent.ACTION_PICK );
                intent.setType( "image/*" );
                startActivityForResult( intent, GALLERY_INTENT );

            }
        } );


        foto = findViewById( R.id.img_PE );
        progressDialog = new ProgressDialog( this );
        //FIN Agregar foto de Perfil


        // Pasa información del usuario del registro
        String id = mAuth.getCurrentUser().getUid();
        mDataBase.getReference().child( "Users" ).child( mAuth.getCurrentUser().getUid() ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String name = snapshot.child( "usuario" ).getValue().toString();
                    String photo = snapshot.child( "foto" ).getValue().toString() ;
                    tvName.setText( name );

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

                Intent i = new Intent( Perfil.this, Crear_Ruta.class );
                i.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity( i );
                finish();

            }
        } );


        //Acción para cambiar de página de Perfil a Mis Rutas
        misRutas.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent( Perfil.this, MisRutas.class );
                i.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity( i );
                finish();

            }
        } );


    } //FIN DEL METODO ONCREATE


    //AGREGAR FOTO DE PERFIL - METODO ONACTIVITYRESULT
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

                    urlFoto = taskSnapshot.getStorage().getDownloadUrl().toString();

                    //OBTIENE EL URI DE LA FOTO
                    filePath.getDownloadUrl().addOnSuccessListener( new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Glide.with( Perfil.this )
                                    .load( uri ).into( foto );
                            Toast.makeText( Perfil.this, "Se subió correctamente la foto", Toast.LENGTH_SHORT ).show();
                        }
                    } );


                }
            } );

        }

    } // FIN DEL METODO OnActivityResult





}