package com.example.prueba2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class listaIdeas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_ideas);

        Sistema s=Sistema.getSistema();
        ArrayList<Sistema.User.Idea> ideas = s.getFolderIdeas();

        TextView titulo = findViewById(R.id.tituloCarpeta);
        ListView lv = findViewById(R.id.listaIdasCarpeta);

        titulo.setText(s.getFolderName());

        ArrayAdapter adaptadorEtiquetas=new ArrayAdapter(this, android.R.layout.simple_list_item_1,ideas);
        lv.setAdapter(adaptadorEtiquetas);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                s.setSelectedIdea(s.getFolderIdeas().get(position));
                Intent intent = new Intent(listaIdeas.this, verIdea.class);
                startActivity(intent);
            }
        });

    }
}