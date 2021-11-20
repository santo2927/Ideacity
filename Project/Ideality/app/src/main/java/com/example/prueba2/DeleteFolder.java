package com.example.prueba2;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DeleteFolder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Sistema s = Sistema.getSistema();
        setContentView(R.layout.activity_select_delete_folder);
        Button b = findViewById(R.id.searchFolderToDelete);
        ListView lv = findViewById(R.id.listDeleteFolderCheck);
        ArrayList<Sistema.User.Carpeta> ids = (ArrayList<Sistema.User.Carpeta>) s.getCarpetas();
        Sistema.User.Carpeta[] idArr=new Sistema.User.Carpeta[ids.size()];
        for(int x=0;x<ids.size();x++){
            idArr[x]=ids.get(x);
        }
        String carpetas[]= new String[ids.size()];
        for(int x=0;x<ids.size();x++){
            carpetas[x]=ids.get(x).toString();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_multiple_choice,carpetas);
        adapter.notifyDataSetChanged();
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lv.setAdapter(adapter);
        ArrayList<Sistema.User.Carpeta> toadd=new ArrayList<>();
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SparseBooleanArray checked = lv.getCheckedItemPositions();
                for(int i=0;i<checked.size();i++){
                    toadd.add(idArr[checked.keyAt(i)]);
                }
                for(Sistema.User.Carpeta x:toadd){
                    for(Sistema.User.Idea y:x.getIdeas()){
                        s.addIdea(y.getNombre(),y.getDescripcion(),y.getPrioridad(),y.getEtiquetas(),y.getColor());
                    }
                    s.deleteFolder(x);
                }
                Intent i = new Intent(DeleteFolder.this, GestorIdeas.class);
                startActivity(i);
                s.deleteSelectedFolder();
                Sistema.guardarSistema();
            }
        });
    }
}
