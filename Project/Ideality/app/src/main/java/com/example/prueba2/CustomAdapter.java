package com.example.prueba2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    ArrayList<String> arrayList;

    public ItemAdapter(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
    }
    }
}
RecyclerView