package com.example.prueba2;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class verIdea extends AppCompatActivity {
    TextView titulo = findViewById(R.id.verTitulo);
    TextView descripcion = findViewById(R.id.verDescripcion);
    TextView prioridad = findViewById(R.id.verPrioridad);
    ListView listaEtiquetas = findViewById(R.id.verEtiquetas);
    public verIdea (){

    }
    public void cambiarTexto (Sistema.User.Idea idea){
        titulo.setText(idea.getNombre());
        descripcion.setText(idea.getDescripcion());
        prioridad.setText(idea.getPrioridad());
        ArrayAdapter adaptadorEtiquetas=new ArrayAdapter(this, android.R.layout.simple_list_item_1,idea.getEtiquetas());
        listaEtiquetas.setAdapter(adaptadorEtiquetas);
    }
    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_idea);

        //titulo.setText();

    }
}
