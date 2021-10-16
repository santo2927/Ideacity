package com.example.prueba2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
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

        listaCarpetasUsable=s.getCarpetas();
        listaIdeaUsable=s.getIdeas();

        ArrayAdapter adaptadorIdea=new ArrayAdapter(this, android.R.layout.simple_list_item_1,listaIdeaUsable);
        ArrayAdapter adaptadorCarpeta=new ArrayAdapter(this, android.R.layout.simple_list_item_1,listaCarpetasUsable);

        listaIdea.setAdapter(adaptadorIdea);
        listaCarpetas.setAdapter(adaptadorCarpeta);
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
        return super.onOptionsItemSelected(opcion_menu);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}