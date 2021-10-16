package com.example.prueba2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DB extends SQLiteOpenHelper {
    //Nombre de la base de datos
    public static final String NOMBREBD = "Usuarios.sdb";
    //Versión de la base de datos
    public static final int VERSION = 1;

    public DB(Context context) {
        super(context, NOMBREBD, null, VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        // Tabla que enlaza usuario y contraseña
        db.execSQL("create table if not exists sesion ( jugador varchar2(15)  unique not null,contraseña varchar(10));");
        //Crear una tabla que enlaza IdEtiquetas con Etiqueta
        db.execSQL("create table if not exists labels (id integer unique not null,nombre varchar2(15) unique not null );");
        //Crear una tabla que enlaza usuarios con IdCarpetas
        db.execSQL("create table if not exists carpetas (jugador varchar2(15) not null,carpeta int unique not null );");
        //Crear una tabla que enlaza IdCarpetas con Nombre
        db.execSQL("create table if not exists carpetasIdNombre (idCarpeta int unique not null,nombreCarpeta varchar2(15) not null );");
        //Crear una tabla que enlaza IdCarpeta con IdIdeas
        db.execSQL("create table if not exists ideasCarpeta (idCarpeta int unique not null,idIdea int not null );");
        //Crear una tabla que enlaza IdIdeas con Nombre,Descripcion,Prioridad
        db.execSQL("create table if not exists ideas (idIdeas int unique not null, nombre varchar(15) not null,descripcion varchar(255) not null, prioridad int not null );");
        //Crear una tabla que enlaza IdIdeas con IdEtiquetas
        db.execSQL("create table if not exists ideasEtiquetas (idIdeas int unique not null, idLabel int not null);");

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean isRegistered(String user, String password){
        return this.getReadableDatabase().rawQuery("select * from Sesion where jugador='"+user+"' and contraseña='"+password+"'", null).moveToNext();
    }

    public void Register(String user, String password){
        this.getWritableDatabase().execSQL("INSER INTO SESION VALUES('"+user+"','"+password+"',);");
    }

    public void getUser(String user){
        String s=this.getReadableDatabase().rawQuery("select * from Sesion where jugador='"+user+"'",null).toString();
        System.out.println();
        //return new Sistema.User();
    }

    /*public ArrayList<Sistema.User.Idea> getIdeas(String user){
        //String s= this.getReadableDatabase("")
    }*/
}
