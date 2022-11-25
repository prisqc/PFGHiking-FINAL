package com.example.pfghiking;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

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


    //AGREGAR FOTO RUTA
    private String urlImagen ="";
    private ImageView picRuta ;
    private Button myButton_add;
    private StorageReference mStorage;
    private ProgressDialog progressDialog;
    private static final int GALLERY_INTENT = 1;


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



        //METODO PARA PUBLICAR RUTA CREADA

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

                            usuario = new ModelUser(usu.getId(), (dataSnapshot.child( "email" ).getValue()).toString() , usu.getNombre() );
                            if (!title.isEmpty() && !description.isEmpty() && !distancia.isEmpty()
                                    && !desnivel.isEmpty() && !time.isEmpty() && !pais.isEmpty() ) {
                                ModelRuta ruta = new ModelRuta(title , usuario, description, distancia, desnivel, time, pais, city, urlImagen);
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



        //METODO PARA AGREGAR FOTO A LA RUTA

        myButton_add  = this.<Button>findViewById(R.id.button_addPic_CR);
        myButton_add.setBackgroundColor(0XFF6A5F4B);

        mStorage = FirebaseStorage.getInstance().getReference();


        myButton_add.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( Intent.ACTION_PICK );
                intent.setType( "image/*" );
                startActivityForResult( intent, GALLERY_INTENT );

            }
        } );

        picRuta =  (ImageView) findViewById( R.id.img_PE );
        progressDialog = new ProgressDialog( this );  // Fin acciones para Agregar foto a Ruta




    }//FIN DEL METODO ONCREATE



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
            StorageReference filePath = mStorage.child( "fotos" ).child( uri.getLastPathSegment() ); //



            filePath.putFile( uri ).addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss(); //finaliza la barra de carga

                    //OBTIENE EL URI DE LA FOTO
                    filePath.getDownloadUrl().addOnSuccessListener( new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            Glide.with( Crear_Ruta.this )
                                    .load( uri ).into( (ImageView) findViewById( R.id.img_CR )); //.centerCrop().placeholder( R.id.img_CR ).error( R.id.img_CR )
                            Toast.makeText( Crear_Ruta.this, "Se subió correctamente la foto", Toast.LENGTH_SHORT ).show();

                            urlImagen = uri.toString();

                        }
                    } );


                }
            } );

        }

    } // FIN DEL METODO OnActivityResult




    // METODO PUBLICAR RUTA
    public void publicarRuta(ModelRuta ruta) {

        String key = mData.getReference().child("rutas").push().getKey();
        mData.getReference().child( "rutas" ).child(key).setValue( ruta );

    }




    } //FIN DE LA CLASE CREAR RUTA


