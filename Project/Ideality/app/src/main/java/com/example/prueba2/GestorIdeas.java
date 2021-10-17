package com.example.prueba2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
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
        listaIdeaUsable=s.getIdeas();

        ArrayAdapter adaptadorIdea=new ArrayAdapter(this, android.R.layout.simple_list_item_1,listaIdeaUsable);
        ArrayAdapter adaptadorCarpeta=new ArrayAdapter(this, android.R.layout.simple_list_item_1,listaCarpetasUsable);

        listaIdea.setAdapter(adaptadorIdea);
        listaCarpetas.setAdapter(adaptadorCarpeta);

        listaIdea.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Sistema.User.Idea i = listaIdeaUsable.get(position);
                Log.i("Idea",i.toString());
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
                Sistema.User.Carpeta i = listaCarpetasUsable.get(position);
                Log.i("Carpeta",i.toString());
            }
        });

        listaCarpetas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                // TODO Auto-generated method stub
                Sistema.User.Carpeta i = listaCarpetasUsable.get(pos);
                Log.v("long clicked carpeta","pos: " + i);
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

    @Override
    public boolean onOptionsItemSelected (MenuItem opcion_menu){
        int id = opcion_menu.getItemId();
        if (id==R.id.BotonIdea){
            Intent i = new Intent(this, CrearIdea.class);
            startActivity(i);
            return true;
        }
        else if(id==R.id.BotonAlfabeticamente){
            Arrays.sort(listaIdeaUsable.toArray());
            ArrayList<Idea> listaIdeas = (ArrayList<Idea>) listaIdeaUsable;
            SortedList listaIdeaUsable;
             Toast.makeText(this, "No existe ese usuario", Toast.LENGTH_SHORT).show();
            return true;}
        else if(id==R.id.BotonAntiguedad){
              Toast.makeText(this, "No existe ", Toast.LENGTH_SHORT).show();
              return true;}


        return super.onOptionsItemSelected(opcion_menu);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}