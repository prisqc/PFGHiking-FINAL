package com.example.pfghiking;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Iniciar_Sesion extends AppCompatActivity {

    private Button myButton_is;
    private EditText editTextemail;
    private EditText editTextcontraseña;
    private TextView olvidoContraseña;
    private String email ="";
    private String contraseña ="";

    private FirebaseAuth isAuth;

    private ModelUser user; //SHAREPREFERENCES - MANTENER SESION INICIADA
    private SharedPreferences preferences; //SHAREPREFERENCES - MANTENER SESION INICIADA


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_iniciar_sesion );

        //Para ocultar el actionBar
        getSupportActionBar().hide();

        Button myButton_is = this.<Button>findViewById(R.id.button_iniciar_sesion_IS);
        myButton_is.setBackgroundColor(0XFF6A5F4B);


        isAuth = FirebaseAuth.getInstance();

        editTextemail = (EditText) findViewById( R.id.iniciar_sesion_user );
        editTextcontraseña = (EditText) findViewById( R.id.iniciar_sesion_pass );
        myButton_is = (Button) findViewById( R.id.button_iniciar_sesion_IS );
        olvidoContraseña = ( TextView ) findViewById( R.id.textView_ForgotPass_IS );


        user = new ModelUser(); //SHAREPREFERENCES - MANTENER SESION INICIADA
        preferences = getSharedPreferences( "Preferences", MODE_PRIVATE ); //SHAREPREFERENCES - MANTENER SESION INICIADA

        myButton_is.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = editTextemail.getText().toString();
                contraseña = editTextcontraseña.getText().toString();

                if (!email.isEmpty() && !contraseña.isEmpty()) {

                    loginUser();

                }else {
                    Toast.makeText( Iniciar_Sesion.this, "Complete los campos", Toast.LENGTH_SHORT ).show();
                }
            }
        } );

        olvidoContraseña.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent oc_IS = new Intent( Iniciar_Sesion.this, Olvido_Contrasena.class);
                oc_IS.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity( oc_IS );
                finish();

            }
        } );

    }

    private void loginUser(){
        isAuth.signInWithEmailAndPassword( email, contraseña ).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){

                    //SHAREPREFERENCES - VALIDAR USUARIO Y MANTENER SESION INICIADA
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt( "usuario_id", Integer.parseInt( FirebaseAuth.getInstance().getCurrentUser().getUid().toString() ) );
                    editor.putString( "usuario", user.getNombre() );
                    editor.commit(); //SHAREPREFERENCES - VALIDAR USUARIO Y MANTENER SESION INICIADA



                    Intent is_IS = new Intent( Iniciar_Sesion.this, Principal.class );
                    is_IS.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                    startActivity( is_IS );
                    finish();
                } else{
                    Toast.makeText( Iniciar_Sesion.this, "No se puede iniciar sesión, compruebe los datos", Toast.LENGTH_SHORT ).show();
                }


            }
        } );
    }



}