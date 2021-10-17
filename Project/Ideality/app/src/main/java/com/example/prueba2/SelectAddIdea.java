package com.example.prueba2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import java.util.ArrayList;

public class SelectAddIdea extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Sistema s = Sistema.getSistema();
        setContentView(R.layout.activity_select_add_idea);
        Button b = findViewById(R.id.addIdea);
        ListView lv = findViewById(R.id.listCheck);
        ArrayList<Sistema.User.Idea> ids = (ArrayList<Sistema.User.Idea>) s.getIdeas();
        Sistema.User.Idea[] idArr=new Sistema.User.Idea[ids.size()];
        for(int x=0;x<ids.size();x++){
            idArr[x]=ids.get(x);
        }
        String ideas[]= new String[ids.size()];
        for(int x=0;x<ids.size();x++){
            ideas[x]=ids.get(x).toString();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_multiple_choice,ideas);
        lv.setAdapter(adapter);
        ArrayList<Sistema.User.Idea> toadd=new ArrayList<>();
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<lv.getCount();i++){
                    if(lv.isItemChecked(i)){
                        toadd.add(idArr[i]);
                    }
                }
                for(Sistema.User.Idea x:toadd){
                    s.addToCarpeta(x);
                }
                Intent i = new Intent(SelectAddIdea.this, GestorIdeas.class);
                startActivity(i);
                s.deleteSelectedFolder();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        int id=item.getItemId();
        if(id==R.id.item_done){
            String
        }
    }
}