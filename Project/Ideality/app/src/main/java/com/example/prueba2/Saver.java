package com.example.prueba2;

import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Saver {

    SharedPreferences sp= null;

    public Saver(SharedPreferences sp){
        this.sp=sp;
    }

    public void getSistema(){
        Sistema s=Sistema.getSistema();
        Set<String> usuarios=sp.getStringSet("usuarios",null);
        Set<String> etiquetas=sp.getStringSet("etiquetas",null);
        if(etiquetas!=null){
            for(String e:etiquetas){
                Integer id=Integer.parseInt(e.split("\n")[0]);
                String nombre=e.split("\n")[1];
                s.addEtiqueta(nombre,id);
            }
        }
        if(usuarios!=null){
            for(String r:usuarios){
                String nombre=r.split("\n")[0];
                String contraseña=r.split("\n")[1];
                s.register(nombre,contraseña);
                s.logIn(nombre,contraseña);
                Set<String> ideas=sp.getStringSet(nombre+"ideas",null);
                Set<String> carpetas=sp.getStringSet(nombre+"carpetas",null);
                for(String idea:ideas){
                    String nombreIdea=idea.split("\n")[0];
                    String descripcion=idea.split("\n")[1];
                    String prioridad=idea.split("\n")[2];
                    ArrayList<Integer> etiq=new ArrayList<>();
                    for (int w=3;w<idea.split("\n").length;w++){
                        etiq.add(Integer.parseInt(idea.split("\n")[w]));
                    }
                    s.addIdea(nombreIdea,descripcion,Integer.parseInt(prioridad),etiq);
                }
                for(String carpeta:carpetas){
                    String nombreCarpeta=carpeta.split("\n")[0];
                    s.getLogedUser().addCarpeta(nombreCarpeta);
                    String nombreIdea=carpeta.split("\n")[1];
                    String descripcion=carpeta.split("\n")[2];
                    String prioridad=carpeta.split("\n")[3];
                    ArrayList<Integer> etiq=new ArrayList<>();
                    for (int w=4;w<carpeta.split("\n").length;w++){
                        etiq.add(Integer.parseInt(carpeta.split("\n")[w]));
                    }
                    s.getLogedUser().getCarpeta(nombreCarpeta).addIdea(nombreIdea,descripcion,Integer.parseInt(prioridad),etiq);
                }
            }
        }



    }

    public void guardarSistema(){
        SharedPreferences.Editor editor = sp.edit();
        Sistema s=Sistema.getSistema();
        Set<String> usuarios=s.getUsuarios();
        editor.putStringSet("usuarios", usuarios).commit();
        Set<String> etiquetas=s.getEtiquetas();
        editor.putStringSet("etiquetas",etiquetas).commit();
        for(String r:usuarios){
            String nombre=r.split("\n")[0];
            String contraseña=r.split("\n")[1];
            s.logIn(nombre,contraseña);
            Set<String> carpetas=s.getCarpetastoSave();
            Sistema.User u = s.getLogedUser();
            editor.putStringSet(nombre+"carpetas",carpetas).commit();
            Set<String> ideas = s.getIdeasSave();
            editor.putStringSet(nombre+"ideas",ideas).commit();
        }


    }





}
