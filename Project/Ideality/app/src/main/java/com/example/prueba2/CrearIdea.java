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

public class CrearIdea extends AppCompatActivity {
        private Sistema s = null;
        private TextView eTitulo, eDescripcion, ePrioridad;
        private ChipGroup chipGroup;
        private Button aEtiqueta;
        private EditText textInputEditText;
        private ArrayList<String> listaEtiquetas = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_idea);
        s = Sistema.getSistema();
        eTitulo = findViewById(R.id.eTitulo);
        eDescripcion = findViewById(R.id.eDescripcion);
        ePrioridad = findViewById(R.id.ePrioridad);

        //View
        aEtiqueta = (Button) findViewById(R.id.aEtiqueta);
        chipGroup = (ChipGroup) findViewById(R.id.chip);
        textInputEditText = (TextInputEditText) findViewById(R.id.textInputEditText);

        //Barra de herramientas
        Toolbar myToolbar2 = (Toolbar) findViewById(R.id.my_toolbar2);
        setSupportActionBar(myToolbar2);
        //fin barra
    }

    public void btnClick(View v) {
        Chip chip = new Chip(this);
        ChipDrawable drawable = ChipDrawable.createFromAttributes(this, null, 0, R.style.Widget_MaterialComponents_Chip_Entry);
        chip.setChipDrawable(drawable);
        chip.setCheckable(false);
        chip.setClickable(false);
        chip.setCheckedIconResource(R.drawable.ic_baseline_add_24);
        chip.setIconStartPadding(3f);
        chip.setPadding(60, 10, 60, 10);
        chip.setText(textInputEditText.getText().toString());
        chip.setOnCloseIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chipGroup.removeView(chip);
            }
        });
        chipGroup.addView(chip);
        //añadir lo escrito a la idea
        listaEtiquetas.add(textInputEditText.getText().toString());
        textInputEditText.setText("");

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
            Integer r =0;
            try{
                r=Integer.parseInt(ePrioridad.getText().toString());
            }catch(Exception e){

            }
            s.createIdea( eTitulo.getText().toString(),  eDescripcion.getText().toString(),r, listaEtiquetas);
            Intent i = new Intent(CrearIdea.this, GestorIdeas.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(opcion_menu_idea);
    }
}