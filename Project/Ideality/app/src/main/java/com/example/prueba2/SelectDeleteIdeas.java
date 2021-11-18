package com.example.prueba2;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SelectDeleteIdeas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Sistema s = Sistema.getSistema();
        setContentView(R.layout.activity_select_delete_ideas);
        Button b = findViewById(R.id.searchDeleteIdeas);
        ListView lv = findViewById(R.id.listDeleteIdeasCheck);
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
        adapter.notifyDataSetChanged();
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lv.setAdapter(adapter);
        ArrayList<Sistema.User.Idea> toadd=new ArrayList<>();
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SparseBooleanArray checked = lv.getCheckedItemPositions();
                for(int i=0;i<checked.size();i++){
                    toadd.add(idArr[checked.keyAt(i)]);
                }
                for(Sistema.User.Idea x:toadd){
                    s.deleteIdea(x);
                }
                Intent i = new Intent(SelectDeleteIdeas.this, GestorIdeas.class);
                startActivity(i);
                Sistema.guardarSistema();
            }
        });
    }


}