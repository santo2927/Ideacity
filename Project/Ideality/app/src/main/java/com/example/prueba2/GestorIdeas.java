package com.example.prueba2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.SortedList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GestorIdeas extends AppCompatActivity {

    RecyclerView listaIdea, listaCarpetas;
    List<Sistema.User.Carpeta> listaCarpetasUsable;
    List<Sistema.User.Idea> listaIdeaUsable;
    ItemsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gestor);
        Sistema s = Sistema.getSistema();
        //Barra de herramientas
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        //fin barra

        //RECYCLE VIEW
        listaIdea = findViewById(R.id.listIdeas);
        listaCarpetas = findViewById(R.id.listCarpetas);

        listaCarpetasUsable = s.getCarpetas();
        listaIdeaUsable = s.getIdeasFiltradas();

        listaIdea.setHasFixedSize(true);
        listaCarpetas.setHasFixedSize(true);

        adapter = new ItemsAdapter(listaIdeaUsable);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        listaIdea.setLayoutManager(layoutManager);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        listaCarpetas.setLayoutManager(layoutManager);

        ArrayList<String> cNombres = new ArrayList<>();
        for(Sistema.User.Carpeta c : this.listaCarpetasUsable){
            cNombres.add(c.toString());
        }


        listaIdea.setAdapter(adapter);
        listaCarpetas.setAdapter(new CustomAdapter(cNombres,getApplicationContext()));

        listaIdea.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), listaIdea, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        s.setSelectedIdea(listaIdeaUsable.get(position));
                        Intent intent = new Intent(GestorIdeas.this, verIdea.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        s.setSelectedIdea(listaIdeaUsable.get(position));
                        Intent intent = new Intent(GestorIdeas.this, editarIdea.class);
                        startActivity(intent);
                    }
                })
        );

        listaCarpetas.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), listaCarpetas, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        s.selectFolder(listaCarpetasUsable.get(position));
                        Intent i = new Intent(GestorIdeas.this, listaIdeas.class);
                        startActivity(i);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        Sistema.User.Carpeta c = listaCarpetasUsable.get(position);
                        s.selectFolder(c);
                        Intent i = new Intent(GestorIdeas.this, SelectAddIdea.class);
                        startActivity(i);
                    }
                })
        );
    }


    //MENU
    // @Override
    public boolean onCreateOptionsMenu(Menu mimenu) {
        getMenuInflater().inflate(R.menu.menu, mimenu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onOptionsItemSelected(MenuItem opcion_menu) {
        Sistema s = Sistema.getSistema();
        ArrayAdapter adaptadorIdea;
        ArrayList<Sistema.User.Idea> listaIdeas;
        int id = opcion_menu.getItemId();
        switch (id) {
            case R.id.BotonIdea:
                Intent i = new Intent(this, CrearIdea.class);
                startActivity(i);
                break;
            case R.id.BotonAlfabeticamente:
                listaIdeas = (ArrayList<Sistema.User.Idea>) s.ordenarIdeasNombre();
                listaIdea.setAdapter(new ItemsAdapter(listaIdeas));
                Toast.makeText(this, "Has ordenado tus ideas por nombre", Toast.LENGTH_SHORT).show();
                break;
            case R.id.BotonPrioridad:
                listaIdeas = (ArrayList<Sistema.User.Idea>) s.ordenarIdeasPrioridad();
                Collections.reverse(listaIdeas);
                listaIdea.setAdapter(new ItemsAdapter(listaIdeas));
                Toast.makeText(this, "Has ordenado tus ideas por prioridad", Toast.LENGTH_SHORT).show();
                break;
            case R.id.BotonFiltrar:
                Intent j = new Intent(this, Filtrar.class);
                startActivity(j);
                break;
            case R.id.BotonCarpeta:
                j = new Intent(this, CrearCarpeta.class);
                startActivity(j);
                break;
            case R.id.BotonEliminarCarpeta:
                Intent k = new Intent(this, DeleteFolder.class);
                startActivity(k);
                break;
            case R.id.BotonEliminarIdea:
                Intent x= new Intent(this, SelectDeleteIdeas.class);
                startActivity(x);
                break;
            case R.id.BotonAyuda:
                setContentView(R.layout.activity_ayuda);
                break;
            case R.id.BotonPerfil:
                x= new Intent(this, ActualizarInfo.class);
                startActivity(x);
                break;

        }
        return super.onOptionsItemSelected(opcion_menu);
    }
}