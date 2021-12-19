package com.example.prueba2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class ActualizarInfo extends AppCompatActivity{

    private Sistema s=null;
    private EditText nombre, contraseña;
    private Button aceptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_perfil);
        s = Sistema.getSistema();
        nombre =  (EditText) findViewById(R.id.txtNombre);
        contraseña= (EditText) findViewById(R.id.txtContraseña);
        aceptar= (Button) findViewById(R.id.btnAceptar);

        nombre.setText(s.getLogedUser().getName());

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i;
                if(contraseña.getText().toString().equals("") && nombre.getText().toString().equals(s.getLogedUser().getName())){
                    i = new Intent(ActualizarInfo.this, GestorIdeas.class);
                    startActivity(i);
                }else{
                    s.updateContraseñaUsuario(contraseña.getText().toString());
                    s.updateNombreUsuario(nombre.getText().toString());
                    s.logOut();
                    s.guardarSistema();

                    i = new Intent(ActualizarInfo.this, MainActivity.class);
                    startActivity(i);
                }

            }
        });

    }


}
