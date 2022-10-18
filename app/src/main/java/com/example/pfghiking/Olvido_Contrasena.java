package com.example.pfghiking;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Olvido_Contrasena extends AppCompatActivity {
    Button rcButton;
    EditText rcEmailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_olvido_contrasena );

        //Para ocultar el actionBar
        getSupportActionBar().hide();

        rcButton = findViewById( R.id.button_forgot_pass_OC );
        rcEmailEditText = findViewById( R.id.email_user_OC );

        rcButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        } );

    }



    private void validate() {

        String email = rcEmailEditText.getText().toString().trim();

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher( email ).matches()){
            rcEmailEditText.setError( "Correo Inválido" );
            return;
        }
        sendEmail(email);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Olvido_Contrasena.this, Iniciar_Sesion.class);
        startActivity( i );
        finish();
    }


    public void sendEmail(String email){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAdress = email;

        auth.sendPasswordResetEmail( emailAdress ).addOnCompleteListener( new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText( Olvido_Contrasena.this, "Correo enviado", Toast.LENGTH_SHORT).show();
                    Intent i2 = new Intent(Olvido_Contrasena.this, Iniciar_Sesion.class);
                    startActivity( i2 );
                    finish();
                }else{
                    Toast.makeText( Olvido_Contrasena.this, "Correo Inválido", Toast.LENGTH_SHORT).show();
                }
            }
        } );

    }
}