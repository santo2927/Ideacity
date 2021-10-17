package com.example.prueba2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

public class GestorIdeas extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listaIdea,listaCarpetas;
    List<Sistema.User.Carpeta> listaCarpetasUsable;
    List<Sistema.User.Idea> listaIdeaUsable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestor);
        Sistema s=Sistema.getSistema();
       //Barra de herramientas
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        //fin barra

        //RECYCLE VIEW
        listaIdea=(ListView) findViewById(R.id.listIdeas);
        listaCarpetas=(ListView) findViewById(R.id.listCarpetas);

        /*Sistema.User u= s.getLogedUser();
        s.addEtiqueta("Universidad",1);
        s.getLogedUser().addCarpeta("La vida es dura");
        ArrayList<Integer> ej = new ArrayList<>();
        ej.add(1);
        s.addIdea("Hola","La vida es grande pero ala lo es mas",7,ej);

        Sistema.guardarSistema();*/

        listaCarpetasUsable=s.getCarpetas();
        listaIdeaUsable=s.getIdeasFiltradas();

        ArrayAdapter adaptadorIdea=new ArrayAdapter(this, android.R.layout.simple_list_item_1,listaIdeaUsable);
        ArrayAdapter adaptadorCarpeta=new ArrayAdapter(this, android.R.layout.simple_list_item_1,listaCarpetasUsable);

        listaIdea.setAdapter(adaptadorIdea);
        listaCarpetas.setAdapter(adaptadorCarpeta);

        listaIdea.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                s.setSelectedIdea(listaIdeaUsable.get(position));
                Intent intent = new Intent(GestorIdeas.this, verIdea.class);
                startActivity(intent);
            }
        });

        listaIdea.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                // TODO Auto-generated method stub
                Sistema.User.Idea i = listaIdeaUsable.get(pos);
                Log.v("long clicked idea","pos: " + i);
                return true;
            }
        });

        listaCarpetas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                s.selectFolder(listaCarpetasUsable.get(position));
                Intent i = new Intent(GestorIdeas.this, listaIdeas.class);
                startActivity(i);
            }
        });

        listaCarpetas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                // TODO Auto-generated method stub
                Sistema.User.Carpeta c = listaCarpetasUsable.get(pos);
                Log.v("long clicked carpeta","pos: " + c);
                s.selectFolder(c);
                Intent i = new Intent(GestorIdeas.this, SelectAddIdea.class);
                startActivity(i);
                return true;
            }
        });
    }


    //MENU
    // @Override
    public boolean onCreateOptionsMenu (Menu mimenu) {
        getMenuInflater().inflate(R.menu.menu, mimenu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onOptionsItemSelected (MenuItem opcion_menu){
        Sistema s = Sistema.getSistema();
        ArrayAdapter adaptadorIdea;
        ArrayList<Sistema.User.Idea> listaIdeas;
        int id = opcion_menu.getItemId();
        switch (id){
            case R.id.BotonIdea:
                Intent i = new Intent(this, CrearIdea.class);
                startActivity(i);
                break;
            case R.id.BotonAlfabeticamente:
                listaIdeas = (ArrayList<Sistema.User.Idea>) s.ordenarIdeasNombre();
                adaptadorIdea=new ArrayAdapter(this, android.R.layout.simple_list_item_1,listaIdeaUsable);
                listaIdea.setAdapter(adaptadorIdea);
                Toast.makeText(this, "Has ordenado tus ideas por nombre", Toast.LENGTH_SHORT).show();
                break;
            case R.id.BotonPrioridad:
                listaIdeas = (ArrayList<Sistema.User.Idea>) s.ordenarIdeasPrioridad();
                Collections.reverse(listaIdeas);
                adaptadorIdea=new ArrayAdapter(this, android.R.layout.simple_list_item_1,listaIdeaUsable);
                listaIdea.setAdapter(adaptadorIdea);
                Toast.makeText(this, "Has ordenado tus ideas por prioridad", Toast.LENGTH_SHORT).show();
                break;
            case R.id.BotonFiltrar:
                Intent j = new Intent(this, Filtrar.class);
                startActivity(j);
                return true;
        }
        return super.onOptionsItemSelected(opcion_menu);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}