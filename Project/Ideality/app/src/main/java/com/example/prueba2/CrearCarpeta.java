package com.example.prueba2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CrearCarpeta extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_carpeta);
        Button b =(Button) findViewById(R.id.butonCreateFolder);


        Sistema s = Sistema.getSistema();
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombre = ((TextView)findViewById(R.id.textNameFolder)).getText().toString();
                s.addFolder(nombre);
                Sistema.guardarSistema();
                Intent i = new Intent(CrearCarpeta.this, GestorIdeas.class);
                startActivity(i);
            }
        });


    }
}