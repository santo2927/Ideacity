package com.example.prueba2;

import android.content.Context;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class Sistema implements Serializable {



    public List<User.Carpeta> getCarpetas() {
        assert isLoged();
        return this.loggedUser.carpetas;
    }

    public List<User.Idea> getIdeas() {
        assert isLoged();
        return this.loggedUser.ideas;
    }

    public Set<String> getIdeasSave(){
        HashSet<String> s=new HashSet<>();
        for(User.Idea i:this.getIdeas()){
            s.add(i.getString());
        }
        return s;
    }

    public Set<String> getCarpetastoSave() {
        HashSet<String> s=new HashSet<>();
        for(User.Carpeta c:this.getCarpetas()){
            for(User.Idea i:c.ideas){
                s.add(c.nombre+" "+i.getString());
            }
            if(c.ideas.isEmpty()){
                s.add(c.nombre+"\n");
            }
        }
        return s;
    }

    public User getLogedUser() {
        return this.loggedUser;
    }

    public void logOut() {
        this.loggedUser=null;
    }

    public void addToCarpeta(User.Idea i) {
        this.selectedFolder.addIdea(i.nombre,i.descripcion,i.prioridad,i.etiquetas);
        ArrayList<User.Idea> ideas = (ArrayList<User.Idea>)this.getIdeas();
        int aux=0;
        for(int r=0;r<ideas.size();r++){
            if(ideas.get(r).nombre.equals(i.nombre)){
                aux=r;
            }
        }
        ideas.remove(aux);
        this.getLogedUser().ideas=ideas;
    }

    public void deleteSelectedFolder() {
        this.selectedFolder=null;
    }
    private User.Idea selectedIdea=null;

    public User.Idea getSelectedIdea() {
        return this.selectedIdea;
    }

    public void setSelectedIdea(User.Idea i){
        this.selectedIdea=i;
    }

    public ArrayList<User.Idea> getFolderIdeas() {
        return this.selectedFolder.ideas;
    }

    public String getFolderName() {
        return this.selectedFolder.nombre;
    }

    class User{
        public String getName() {
            return this.nombre;
        }

        public String getPassword() {
            return this.contraseña;
        }

        public void addCarpeta(String nombreCarpeta) {
            this.carpetas.add(new Carpeta(nombreCarpeta));
        }

        public Carpeta getCarpeta(String nombreCarpeta) {
            for(Carpeta c:this.carpetas){
                if(c.nombre.equalsIgnoreCase(nombreCarpeta)){
                    return c;
                }
            }
            return null;
        }

        class Idea implements Comparable<Idea>{
            private String nombre;
            private String descripcion;
            private Integer prioridad;
            private ArrayList<Integer> etiquetas;

            public Idea(String nombre, String descripcion, int prioridad, ArrayList<Integer> etiquetas) {
                this.nombre = nombre;
                this.descripcion=descripcion;
                this.prioridad=prioridad;
                this.etiquetas=etiquetas;
            }
            public Idea(Idea other){
                this.nombre = other.nombre;
                this.descripcion=other.descripcion;
                this.prioridad=other.prioridad;
                this.etiquetas=other.etiquetas;
            }

            public ArrayList<Integer> getEtiquetas() {
                return etiquetas;
            }

            public void setEtiquetas(ArrayList<Integer> etiquetas) {
                this.etiquetas = etiquetas;
            }

            public Integer getPrioridad() {
                return prioridad;
            }

            public void setPrioridad(int prioridad) {
                this.prioridad = prioridad;
            }

            public String getDescripcion() {
                return descripcion;
            }

            public void setDescripcion(String descripcion) {
                this.descripcion = descripcion;
            }

            public String getNombre() {
                return nombre;
            }

            public void setNombre(String nombre) {
                this.nombre = nombre;
            }

            @Override
            public int compareTo(@NonNull Idea o) {
                return o.prioridad.compareTo(this.prioridad);
            }

            @Override
            public String toString(){
                return this.nombre;
            }


            public String getString(){
                String s="";
                for(Integer i:etiquetas){
                    s+=i+"\n";
                }
                return String.format("%s\n%s\n%d\n%s",nombre,descripcion,prioridad,s);
            }
        }



        class Carpeta{
            private String nombre;
            private ArrayList<Idea> ideas;

            public Carpeta(String nombre) {
                this.nombre = nombre;
                ideas=new ArrayList<>();
            }

            public String toString(){
                return nombre;
            }

            public void addIdea(String nombre, String descripcion, Integer prioridad, ArrayList<Integer> etiquetas){
                this.ideas.add(new Idea(nombre,descripcion,prioridad,etiquetas));
            }


        }

        private String nombre;
        private String contraseña;
        private ArrayList<Carpeta> carpetas;
        private ArrayList<Idea> ideas;

        public User(String nombre, String contraseña){
            this.contraseña=contraseña;
            this.nombre=nombre;
            this.carpetas=new ArrayList<>();
            this.ideas=new ArrayList<>();
        }

        private void moverACarpeta(int idea,int carpeta){
            Idea i = this.ideas.get(idea);
            this.carpetas.get(carpeta).addIdea(i.nombre,i.descripcion,i.prioridad,i.etiquetas);
            this.ideas.remove(idea);
        }

        public void moverACarpeta(String idea, String carpeta){
            int i=0;
            int n=0;
            for(Carpeta c:carpetas){
                if(c.nombre.equalsIgnoreCase(carpeta)){
                    break;
                }
                i++;
            }
            for(Idea c:ideas){
                if(c.nombre.equalsIgnoreCase(idea)){
                    break;
                }
                n++;
            }
            moverACarpeta(n,i);
        }

        public void addIdea(String nombre, String descripcion, Integer prioridad, ArrayList<Integer> etiquetas){
            this.ideas.add(new Idea(nombre,descripcion,prioridad,etiquetas));
        }



    }

    private static Sistema instancia = null;

    private User.Carpeta selectedFolder=null;
    private HashMap<String, User> usuarios;
    private static Saver sb=null;
    private HashMap<Integer,String> etiquetas;

    private User loggedUser;

    public Set<String> getUsuarios(){
        HashSet<String> s = new HashSet<>();
        for (String u:this.usuarios.keySet()){
            s.add(u+"\n"+this.usuarios.get(u).contraseña);
        }
        return s;
    }

    public void selectFolder(User.Carpeta c){
        this.selectedFolder=c;
    }

    public Set<String> getEtiquetas(){
        HashSet<String> etiquetas=new HashSet<>();
        for(Integer i:this.etiquetas.keySet()){
            etiquetas.add(i+"\n"+this.etiquetas.get(i));
        }
        return etiquetas;
    }

    public Sistema(){
        usuarios=new HashMap<>();
        etiquetas=new HashMap<>();
        loggedUser=null;
        instancia=this;
    }

    public static Sistema setSaver(Saver sb){
        instancia=new Sistema();
        Sistema.sb=sb;
        sb.getSistema();
        return instancia;
    }


    public static Sistema getSistema() {
        return instancia;
    }

    public static void guardarSistema(){
        sb.guardarSistema();
    }

    private boolean isRegistered(String name){
        return usuarios.containsKey(name);
    }

    public boolean register(String nombre, String contraseña){
        if(!isRegistered(nombre)) {
            usuarios.put(nombre, new User(nombre, contraseña));
            return true;
        }
        return false;
    }

    public boolean logIn(String nombre, String contraseña){
        if(isRegistered(nombre) && this.usuarios.get(nombre).contraseña.equals(contraseña)){
            this.loggedUser=usuarios.get(nombre);
            return true;
        }
        return false;
    }

    private Integer getEtiqueta(String s){
        Integer a=0;
        for(Integer i:this.etiquetas.keySet()){
            if(this.etiquetas.get(i).equalsIgnoreCase(s)){
                return i;
            }
            a=i;
        }
        a++;
        this.etiquetas.put(a,s);
        return a;
    }

    private boolean isLoged(){
        return this.loggedUser!=null;
    }

    public void createIdea(String nombre, String descripcion, int prioridad, ArrayList<String> etiquetas){
        assert isLoged();
        ArrayList<Integer> aux =new ArrayList<>();
        for (String i:etiquetas){
            aux.add(getEtiqueta(i));
        }
        this.loggedUser.addIdea(nombre, descripcion, prioridad, aux);
    }

    public void addIdea(String nombre, String descripcion, int prioridad, ArrayList<Integer> etiquetas){
        assert isLoged();
        this.loggedUser.addIdea(nombre, descripcion, prioridad, etiquetas);
    }

    public Integer createEtiqueta(String s){
        return getEtiqueta(s);
    }

    public void moverACarpeta(String idea, String carpeta){
        assert isLoged();
        this.loggedUser.moverACarpeta(idea,carpeta);
    }

    public void addEtiqueta(String s, Integer i){
        this.etiquetas.put(i,s);
    }


}