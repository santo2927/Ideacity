package com.example.prueba2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder> {

    ArrayList<Sistema.User.Idea> listaItems;
    private OnItemClickListener itemListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        itemListener = listener;
    }

    public ItemsAdapter(List<Sistema.User.Idea> listaItems) {
        this.listaItems = (ArrayList<Sistema.User.Idea>) listaItems;
    }

    @Override
    public ItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_row_item,null,false);
        return new ItemsViewHolder(view, itemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewHolder holder, int position) {
        holder.txt_nombre.setText(listaItems.get(position).toString());
        holder.txt_nombre.setTextColor(listaItems.get(position).getColor());
    }

    @Override
    public int getItemCount() {
        return listaItems.size();
    }

    public static class ItemsViewHolder extends RecyclerView.ViewHolder{

        TextView txt_nombre;

        public ItemsViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            txt_nombre = (TextView) itemView.findViewById(R.id.txt_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}