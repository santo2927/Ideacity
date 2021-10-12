package com.example.prueba2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB extends SQLiteOpenHelper {
    //Nombre de la base de datos
    public static final String NOMBREBD = "Usuarios.sdb";
    //Versión de la base de datos
    public static final int VERSION = 1;

    public DB(Context context) {
        super(context, NOMBREBD, null, VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        // db.execSQL("create table " + NOMBRE_TABLA + "(" + REF + " integer primary key autoincrement not null, " + NOMBRE + "text);");
        db.execSQL("create table if not exists sesion ( jugador varchar2(15)  unique not null,contraseña varchar(10), ideas varchar2(50));");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
