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

import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class GestorIdeas extends AppCompatActivity {

    ArrayList<String> listaDatos;
    RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestor);

       //Barra de herramientas
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        //fin barra

        //RECYCLE VIEW
        recycler = (RecyclerView) findViewById(R.id.RecycleId);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));

        listaDatos = new ArrayList<String>();
        for (int i=0; i<=50;i++){
            listaDatos.add("Dato # " +i+" ");
        }

        Adaptador adapter = new Adaptador(listaDatos);
        recycler.setAdapter(adapter);
        //FIN RECYCLE
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
}