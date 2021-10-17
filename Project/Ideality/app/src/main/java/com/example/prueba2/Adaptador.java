package com.example.prueba2;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolderDatos> {

    ArrayList<Sistema.User.Idea> listaDatos;

    public Adaptador(ArrayList<Sistema.User.Idea> listaDatos) {
        this.listaDatos = listaDatos;
    }
    public class ViewHolderDatos extends RecyclerView.ViewHolder {

        private TextView nombre;
        private TextView descripcion;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            nombre = (TextView) itemView.findViewById(R.id.Titulo);
            descripcion = (TextView) itemView.findViewById(R.id.Descripcion);

        }
    }

    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_items,null,false);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        holder.nombre.setText(listaDatos.get(position).getNombre());
        holder.descripcion.setText(listaDatos.get(position).getDescripcion());
    }

    @Override
    public int getItemCount() {
        return listaDatos.size();
    }


}
