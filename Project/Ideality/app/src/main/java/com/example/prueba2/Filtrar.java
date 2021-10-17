package com.example.prueba2;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Filtrar extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Sistema s = Sistema.getSistema();
        setContentView(R.layout.activity_filtrar);
        Button b = findViewById(R.id.searchEtiquetas);
        ListView lv = findViewById(R.id.listCheckEtiquetas);
        ArrayList<String> ids = s.getEtiquetasString();
        String[] idArr=new String[ids.size()];
        int x=0;
        for(String string:ids){
            idArr[x]=string;
            x++;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_multiple_choice,ids);
        adapter.notifyDataSetChanged();
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lv.setAdapter(adapter);
        ArrayList<String> toadd=new ArrayList<>();
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SparseBooleanArray checked = lv.getCheckedItemPositions();
                for(int i=0;i<checked.size();i++){
                    toadd.add(idArr[checked.keyAt(i)]);
                }
                s.selectFilter(toadd);
                Intent i = new Intent(Filtrar.this, GestorIdeas.class);
                startActivity(i);
            }
        });
    }
}
