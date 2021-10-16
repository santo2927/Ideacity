package com.example.prueba2;


import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DB dbUser;
    EditText user, pass, confPass;
    Sistema s=null;
    Saver sv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sv=new Saver(getSharedPreferences("Ideality",Context.MODE_PRIVATE));
        sv.getSistema();
        s=Sistema.getSistema();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbUser = new DB(this);
        botones();
        Button conf = (Button) findViewById(R.id.btnConfDatos);
        conf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(findViewById(R.id.txtConfPass).getVisibility() ==  View.INVISIBLE){
                    comprobacionDatos();
                }else{
                    registro();
                }
            }
        });
    }

    private void comprobacionDatos() {

        user = (EditText) findViewById(R.id.txtRegUsername);
        dbUser.getUser(user.toString());
        pass = (EditText) findViewById(R.id.txtRegPass);
        /*SQLiteDatabase bd ;
        Cursor contenido;
        bd= dbUser.getReadableDatabase();
        contenido = bd.rawQuery("select * from Sesion where jugador='"+user.getText()+"' and contraseña='"+pass.getText()+"'", null);
        if (contenido.moveToNext()){
            Sistema s=Sistema.getSistema();
            s.logIn((String)user,(String)pass);
            Intent i = new Intent(MainActivity.this, GestorIdeas.class);
            startActivity(i);
        }else{
            Toast.makeText(this, "No existe ese usuario", Toast.LENGTH_SHORT).show();
        }*/
        user = (EditText) findViewById(R.id.txtRegUsername);
        pass = (EditText) findViewById(R.id.txtRegPass);
        String nombre=user.getText().toString();
        String contraseña= pass.getText().toString();
        Boolean b =s.logIn(nombre,contraseña);
        sv.guardarSistema();
        if(b){
            Intent i = new Intent(MainActivity.this, GestorIdeas.class);
            startActivity(i);
        }else{
            Toast.makeText(this, "No existe ese usuario", Toast.LENGTH_SHORT).show();
        }
    }

    private void registro(){

        user = (EditText) findViewById(R.id.txtRegUsername);
        pass = (EditText) findViewById(R.id.txtRegPass);
        String nombre=user.getText().toString();
        String contraseña= pass.getText().toString();
        Boolean b=s.register(nombre,contraseña);
        sv.guardarSistema();
        if(b){
            Intent i = new Intent(MainActivity.this, MainActivity.class);
            startActivity(i);
        } else{
            Toast.makeText(this, "Ya existe un usuario con esas caracteristicas", Toast.LENGTH_SHORT).show();
        }

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