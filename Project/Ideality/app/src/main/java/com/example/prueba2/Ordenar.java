package com.example.prueba2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class Ordenar extends AppCompatActivity{
    private Sistema s = null;
    private TextView gestor_ordenar;
    private Button Alfafabetica;
    private Button Antiguedad;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        s = Sistema.getSistema();
    }
    //MENU
    @Override
    public boolean onCreateOptionsMenu (Menu mimenuordenar) {
        //getMenuInflater().inflate(R.menu.menu_ordenar, mimenuordenar);
        return true;
    }
    public boolean onCreateOrderMenu (Menu mimenuo) {
        //getMenuInflater().inflate(R.menu.menu_ordenar, mimenuo);
        return true;
    }
}
