package com.example.pfghiking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Crear_Cuenta extends AppCompatActivity {
    private Button myButtonReg;
    private EditText Textemail;
    private EditText Textusuario;
    private EditText Textcontraseña;

    private String email = "";
    private String usuario = "";
    private String contraseña = "";

    private FirebaseAuth mAuth;
    DatabaseReference rDatabase;
    //private FirebaseAuth.AuthStateListener mAuthListener;

    // @Override
    //protected void onStart(){
    //  super.onStart();
    //mAuth.addAuthStateListener( mAuthListener );
    //}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_crear_cuenta );

        mAuth = FirebaseAuth.getInstance();
        rDatabase = FirebaseDatabase.getInstance("https://pfghiking-default-rtdb.europe-west1.firebasedatabase.app/").getReference();

        Textemail = ( EditText ) findViewById( R.id.email_CC );
        Textusuario = ( EditText ) findViewById( R.id.usuario_CC );
        Textcontraseña = ( EditText ) findViewById( R.id.contraseña_CC );
        myButtonReg = ( Button ) findViewById( R.id.button_crearCuenta_CC );

        //---------------- CAMBIAR DE COLOR EL BOTÓN ---------------------
        Button myButton_cc = this.<Button>findViewById( R.id.button_crearCuenta_CC );
        myButtonReg.setBackgroundColor( 0XFF493B37 );

        myButtonReg.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = Textemail.getText().toString();
                usuario = Textusuario.getText().toString();
                contraseña = Textcontraseña.getText().toString();

                if ( !email.isEmpty() && !usuario.isEmpty() && !contraseña.isEmpty() && email.contains( "@" )) {

                    if (contraseña.length() >= 6) {
                        registrarUsuario();
                    } else {
                        Toast.makeText( Crear_Cuenta.this, "Contraseña debe tener al menos 6 caracteres",
                                Toast.LENGTH_SHORT ).show();
                    }
                }

            }
        } );

    }


    private void registrarUsuario() {

        mAuth.createUserWithEmailAndPassword( email, contraseña ).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put( "email", email );
                    map.put( "usuario", usuario );
                    map.put( "contraseña", contraseña );

                    String id = mAuth.getCurrentUser().getUid();
                    rDatabase.child( "Users" ).child( id ).setValue( map ).addOnCompleteListener( new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()) {
                                Toast.makeText( Crear_Cuenta.this, "Registro completado", Toast.LENGTH_SHORT ).show();
                                Intent cc_CC = new Intent( Crear_Cuenta.this, Iniciar_Sesion.class );
                                cc_CC.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                                startActivity( cc_CC );
                                finish();

                            } else {
                                Toast.makeText( Crear_Cuenta.this, "Error en el registro del usuario",
                                        Toast.LENGTH_SHORT ).show();
                            }
                        }
                    } );

                } else {
                    Toast.makeText( Crear_Cuenta.this, "Error en el registro del usuario",
                            Toast.LENGTH_SHORT ).show();
                }
            }
        } );

    }

}