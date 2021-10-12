package com.example.prueba2;


import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Activity act;
    EditText user, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        botones();
        Button conf = (Button) findViewById(R.id.btnConfDatos);
        conf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (comprobacionDatos()){
                    Intent i = new Intent(MainActivity.this, GestorIdeas.class);
                    startActivity(i);
                }else{
                    findViewById(R.id.txtError).setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private boolean comprobacionDatos() {
        user = (EditText) findViewById(R.id.txtRegUsername);
        pass = (EditText) findViewById(R.id.txtRegPass);
        if (user.getText().toString().equals("Jorge") && pass.getText().toString().equals("Perez")){
            return true;
        }

        return false;
    }

    public void botones(){
        findViewById(R.id.txtError).setVisibility(View.INVISIBLE);
        findViewById(R.id.txtConfPass).setVisibility(View.INVISIBLE);
        Button reg = (Button) findViewById(R.id.btnRegistrarse);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.txtConfPass).setVisibility(View.VISIBLE);
            }
        });

        Button ini = (Button) findViewById(R.id.btnIniciarSesion);
        ini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.txtConfPass).setVisibility(View.INVISIBLE);
            }
        });
    }
}