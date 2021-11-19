package com.example.prueba2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ContactHolder> {

    // List to store all the contact details
    private ArrayList<String> elemList;
    private Context mContext;

    // Counstructor for the Class
    public CustomAdapter(ArrayList<String> contactsList, Context context) {
        this.elemList = contactsList;
        this.mContext = context;
    }

    // This method creates views for the RecyclerView by inflating the layout
    // Into the viewHolders which helps to display the items in the RecyclerView
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        // Inflate the layout view you have created for the list rows here
        View view = layoutInflater.inflate(R.layout.text_row_item, parent, false);
        return new ContactHolder(view);
    }

    @Override
    public int getItemCount() {
        return elemList == null? 0: elemList.size();
    }

    // This method is called when binding the data to the views being created in RecyclerView
    @Override
    public void onBindViewHolder(@NonNull ContactHolder holder, final int position) {
        final String elem = elemList.get(position);

        // Set the data to the views here
        holder.setContactName(elem);

        // You can set click listners to indvidual items in the viewholder here
        // make sure you pass down the listner or make the Data members of the viewHolder public

    }

    // This is your ViewHolder class that helps to populate data to the view
    public class ContactHolder extends RecyclerView.ViewHolder {

        private TextView txtName;

        public ContactHolder(View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txt_name);
        }

        public void setContactName(String name) {
            txtName.setText(name);
        }
    }
}