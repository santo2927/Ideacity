package com.example.prueba2;


import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DB dbUser;
    EditText user, pass, confPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        pass = (EditText) findViewById(R.id.txtRegPass);
        SQLiteDatabase bd ;
        Cursor contenido;
        bd= dbUser.getReadableDatabase();
        contenido = bd.rawQuery("select * from Sesion where jugador='"+user.getText()+"' and contraseña='"+pass.getText()+"'", null);
        if (contenido.moveToNext()){
            Intent i = new Intent(MainActivity.this, GestorIdeas.class);
            startActivity(i);
        }else{
            Toast.makeText(this, "No existe ese usuario", Toast.LENGTH_SHORT).show();
        }
    }

    private void registro(){
        SQLiteDatabase bd ;
        user = (EditText) findViewById(R.id.txtRegUsername);
        pass = (EditText) findViewById(R.id.txtRegPass);
        confPass = (EditText) findViewById(R.id.txtConfPass);
        if (user.getText().toString() != "" && pass.getText().toString() != "" && pass.getText().toString().equals(confPass.getText().toString()) ){
            try {
                bd = dbUser.getWritableDatabase();
                bd.execSQL("INSERT INTO SESION VALUES ('" + user.getText().toString() + "','" + pass.getText().toString() + "',''); ");
                bd.close();
                Intent i = new Intent(this, GestorIdeas.class);
                startActivity(i);
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this, "Rellena los campos obligatorios", Toast.LENGTH_LONG).show();
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