package com.example.prueba2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CrearIdea extends AppCompatActivity {
    Sistema s=null;
    TextView nombre, descripcion,prioridad;
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        Sistema s=Sistema.cargarSistema();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_idea);
        b=findViewById(R.id.button);
        nombre=findViewById(R.id.editTextTextPersonName);
        descripcion=findViewById(R.id.editTextTextMultiLine);
        prioridad=findViewById(R.id.textView2);

        //Barra de herramientas
        Toolbar myToolbar2 = (Toolbar) findViewById(R.id.my_toolbar2);
        setSupportActionBar(myToolbar2);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s.createIdea((String) nombre.getText(),(String) descripcion.getText(),toInt((String) prioridad.getText()));
            }
        });
        //fin barra


    }
    //MENU
    @Override
    public boolean onCreateOptionsMenu (Menu mimenuidea) {
        getMenuInflater().inflate(R.menu.menu_idea, mimenuidea);
        return true;
    }

    private Integer toInt(String s){
        Integer i;
        try{
            i=Integer.parseInt(s);
        }catch (Exception e){
            i=0;
        }
        return i;
    }
}