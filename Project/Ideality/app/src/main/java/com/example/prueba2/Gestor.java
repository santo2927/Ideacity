package com.example.prueba2;

import android.app.Activity;


public class Gestor {

    Activity activity;

    public Gestor(Activity activity){
        this.activity =activity;
    }

    public void start(){
        activity.setContentView(R.layout.success);
    }
}