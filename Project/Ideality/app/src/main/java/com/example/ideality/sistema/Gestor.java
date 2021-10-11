package com.example.procesossoftware.sistema;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.example.procesossoftware.R;

public class Gestor {

    Activity activity;

    public Gestor(Activity activity){
        this.activity =activity;
    }

    public void start(){
        activity.setContentView(R.layout.success);
    }
}
