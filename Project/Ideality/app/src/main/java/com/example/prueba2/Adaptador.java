package com.example.prueba2;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolderDatos> {

    ArrayList<String> listaDatos;

    public Adaptador(ArrayList<String> listaDatos) {
        this.listaDatos = listaDatos;
    }


    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_items,null,false);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        holder.asignarDatos(listaDatos.get(position));
    }

    @Override
    public int getItemCount() {
        return listaDatos.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {

        TextView Dato;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            Dato = (TextView) itemView.findViewById(R.id.idDato);
        }

        public void asignarDatos(String Datos) {
            Dato.setText(Datos);
        }
    }
}
