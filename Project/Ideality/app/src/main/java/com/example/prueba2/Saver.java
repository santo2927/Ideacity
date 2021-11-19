package com.example.prueba2;

import android.content.SharedPreferences;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Saver {

    SharedPreferences sp = null;

    public Saver(SharedPreferences sp) {
        this.sp = sp;
        //sp.edit().clear().commit();


    }

    public void getSistema() {
        Sistema s = Sistema.getSistema();
        Set<String> usuarios = sp.getStringSet("usuarios", null);
        Set<String> etiquetas = sp.getStringSet("etiquetas", null);
        if (etiquetas != null) {
            for (String e : etiquetas) {
                Integer id = Integer.parseInt(e.split("\n")[0]);
                String nombre = e.split("\n")[1];
                s.addEtiqueta(nombre, id);
            }
        }
        if (usuarios != null) {
            for (String r : usuarios) {
                String nombre = r.split("\n")[0];
                String contraseña = r.split("\n")[1];
                s.register(nombre, contraseña);
                s.logIn(nombre, contraseña);
                Set<String> ideas = sp.getStringSet(nombre + "ideas", null);
                Set<String> carpetas = sp.getStringSet(nombre + "carpetas", null);
                if (ideas != null) {
                    for (String idea : ideas) {
                        String nombreIdea = idea.split("\n")[0];
                        String descripcion = idea.split("\n")[1];
                        String prioridad = idea.split("\n")[2];
                        Integer color;
                        try {
                            color = Integer.parseInt(idea.split("\n")[3]);
                        } catch (Exception e) {
                            color = Color.WHITE;
                        }
                        ArrayList<Integer> etiq = new ArrayList<>();
                        for (int w = 4; w < idea.split("\n").length; w++) {
                            try {
                                etiq.add(Integer.parseInt(idea.split("\n")[w]));
                            } catch (Exception e) {

                            }
                        }
                        s.addIdea(nombreIdea, descripcion, Integer.parseInt(prioridad), etiq, color);
                    }
                }
                if (carpetas != null) {
                    for (String carpeta : carpetas) {
                        String nombreCarpeta = carpeta.split("\n")[0];

                        s.getLogedUser().addCarpeta(nombreCarpeta);
                        if (carpeta.split("\n").length > 2) {
                            String nombreIdea = carpeta.split("\n")[1];
                            String descripcion = carpeta.split("\n")[2];
                            String prioridad = carpeta.split("\n")[3];
                            Integer color;
                            try {
                                color = Integer.parseInt(carpeta.split("\n")[4]);
                            } catch (Exception e) {
                                color = Color.WHITE;
                            }
                            ArrayList<Integer> etiq = new ArrayList<>();
                            for (int w = 5; w < carpeta.split("\n").length; w++) {
                                try {
                                    etiq.add(Integer.parseInt(carpeta.split("\n")[w]));
                                } catch (Exception e) {

                                }
                            }
                            Integer i = 0;
                            try {
                                i = Integer.parseInt(prioridad);
                            } catch (Exception e) {

                            }
                            s.getLogedUser().getCarpeta(nombreCarpeta).addIdea(nombreIdea, descripcion, i, etiq, color);
                        }
                    }
                }

            }
        }


    }

    public void guardarSistema() {
        SharedPreferences.Editor editor = sp.edit();
        Sistema s = Sistema.getSistema();
        Sistema.User u = s.getLogedUser();
        Set<String> usuarios = s.getUsuarios();
        editor.putStringSet("usuarios", usuarios).commit();
        Set<String> etiquetas = s.getEtiquetas();
        editor.putStringSet("etiquetas", etiquetas).commit();
        for (String r : usuarios) {
            String nombre = r.split("\n")[0];
            String contraseña = r.split("\n")[1];
            s.logIn(nombre, contraseña);
            Set<String> carpetas = s.getCarpetastoSave();
            editor.putStringSet(nombre + "carpetas", carpetas).commit();
            Set<String> ideas = s.getIdeasSave();
            editor.putStringSet(nombre + "ideas", ideas).commit();
        }
        if (u != null) {
            s.logIn(u.getName(), u.getPassword());
        } else {
            s.logOut();
        }


    }


}
