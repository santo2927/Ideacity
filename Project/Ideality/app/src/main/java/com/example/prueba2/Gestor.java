package com.example.prueba2;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.example.prueba2.R;

public class Gestor {

    Activity activity;

    public Gestor(Activity activity){
        this.activity =activity;
    }

    public void start(){
        activity.setContentView(R.layout.success);
    }
}