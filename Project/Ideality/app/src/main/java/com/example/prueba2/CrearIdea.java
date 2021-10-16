package com.example.prueba2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class CrearIdea extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_idea);

        //Barra de herramientas
        Toolbar myToolbar2 = (Toolbar) findViewById(R.id.my_toolbar2);
        setSupportActionBar(myToolbar2);
        //fin barra


    }
    //MENU
    @Override
    public boolean onCreateOptionsMenu (Menu mimenuidea) {
        getMenuInflater().inflate(R.menu.menu_idea, mimenuidea);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem opcion_menu_idea){
        int id = opcion_menu_idea.getItemId();
        if (id==R.id.guardarIdea){

            return true;
        }
        return super.onOptionsItemSelected(opcion_menu_idea);
    }
}