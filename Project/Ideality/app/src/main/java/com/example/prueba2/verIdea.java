package com.example.prueba2;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class verIdea extends AppCompatActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState){


        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_idea);
        TextView titulo = findViewById(R.id.verTitulo);
        TextView descripcion = findViewById(R.id.verDescripcion);
        TextView prioridad = findViewById(R.id.verPrioridad);
        ListView listaEtiquetas = findViewById(R.id.verEtiquetas);
        Sistema s=Sistema.getSistema();
        //titulo.setText();
        Sistema.User.Idea idea = s.getSelectedIdea();

        titulo.setText("Nombre: "+idea.getNombre());
        descripcion.setText("Descripcion: " +idea.getDescripcion());
        prioridad.setText("Prioridad: "+idea.getPrioridad().toString());
        ArrayAdapter adaptadorEtiquetas=new ArrayAdapter(this, android.R.layout.simple_list_item_1,idea.getEtiquetas());
        listaEtiquetas.setAdapter(adaptadorEtiquetas);

    }
}
