package com.example.prueba2;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

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
import java.util.Arrays;
import java.util.Comparator;
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
    
    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<User.Idea> ordenarIdeasNombre(){
        List<User.Idea> ordenadas = getIdeasFiltradas();
        ordenadas.sort(new Comparator<User.Idea>() {
            @Override
            public int compare(User.Idea o1, User.Idea o2) {
                return o1.nombre.compareToIgnoreCase(o2.nombre);
            }
        });
        return ordenadas;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<User.Idea> ordenarIdeasPrioridad(){
        List<User.Idea> ordenadas = getIdeasFiltradas();

        ordenadas.sort(new Comparator<User.Idea>() {
            @Override
            public int compare(User.Idea o1, User.Idea o2) {
                return o1.prioridad.compareTo(o2.prioridad);
            }
        });
        return ordenadas;
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
                s.add(c.nombre+"\n"+i.getString());
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
        this.selectedFolder.addIdea(i.nombre,i.descripcion,i.prioridad,i.etiquetas,i.color);
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


    public void deleteFolder(User.Carpeta c) {
        ArrayList<User.Carpeta> carpetas = (ArrayList<User.Carpeta>) this.getCarpetas();
        int aux = 0;
        for (int r = 0; r < carpetas.size(); r++) {
            if (carpetas.get(r).nombre.equals(c.nombre)) {
                aux = r;
            }
        }
        carpetas.remove(aux);
        this.getLogedUser().carpetas = carpetas;
    }

    public void deleteIdea(User.Idea i){
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

    private ArrayList<Integer> filter;

    public void selectFilter(ArrayList<String> toadd) {
        filter=new ArrayList<>();
        for(String s:toadd){
            filter.add(this.getEtiqueta(s));
        }
    }

    public List<User.Idea> getIdeasFiltradas() {
        if(this.filter==null || this.filter.size()<1){
            return this.getIdeas();
        }
        ArrayList<User.Idea> fin=new ArrayList<>();
        for(User.Idea i:this.getIdeas()){
            if(i.containsEtiqueta(filter)){
                fin.add(i);
            }
        }
        return fin;
    }

    public ArrayList<String> getEtiquetasString() {
        ArrayList<String> s = new ArrayList<>();
        for(Integer i:this.etiquetas.keySet()){
            s.add(this.etiquetas.get(i));
        }
        return s;
    }

    public ArrayList<String> getEtiquetasIdea(User.Idea idea) {
        ArrayList<String> s = new ArrayList<>();
        for(Integer i:idea.etiquetas){
            s.add(this.etiquetas.get(i));
        }
        return s;
    }

    public void addFolder(String nombre) {
        this.loggedUser.addCarpeta(nombre);
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
            private Integer color;
            private ArrayList<Integer> etiquetas;

            public Idea(String nombre, String descripcion, int prioridad, ArrayList<Integer> etiquetas,Integer color) {
                this.nombre = nombre;
                this.descripcion=descripcion;
                this.prioridad=prioridad;
                this.etiquetas=etiquetas;
                this.color=color;
            }
            public Idea(Idea other){
                this.nombre = other.nombre;
                this.descripcion=other.descripcion;
                this.prioridad=other.prioridad;
                this.etiquetas=other.etiquetas;
                this.color=other.color;
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

            public Integer getColor() {
                return color;
            }

            public void setColor(Integer color) {
                this.color = color;
            }

            @Override
            public int compareTo(Idea o) {
                if (prioridad < o.prioridad) {
                    return -1;
                }
                if (prioridad > o.prioridad) {
                    return 1;
                }
                return 0;
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
                return String.format("%s\n%s\n%d\n%d\n%s",nombre,descripcion,prioridad,color,s);
            }

            public boolean containsEtiqueta(ArrayList<Integer> filter) {
                Boolean aux=false;
                for(Integer s:filter){
                    aux|=this.etiquetas.contains(s);
                }
                return aux;
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

            public void addIdea(String nombre, String descripcion, Integer prioridad, ArrayList<Integer> etiquetas,Integer color){
                this.ideas.add(new Idea(nombre,descripcion,prioridad,etiquetas,color));
            }

            public ArrayList<Idea> getIdeas(){
                return ideas;
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
            this.carpetas.get(carpeta).addIdea(i.nombre,i.descripcion,i.prioridad,i.etiquetas,i.color);
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

        public void addIdea(String nombre, String descripcion, Integer prioridad, ArrayList<Integer> etiquetas,Integer color){
            this.ideas.add(new Idea(nombre,descripcion,prioridad,etiquetas,color));
        }



    }

    private static Sistema instancia = null;

    private User.Carpeta selectedFolder=null;
    public HashMap<String, User> usuarios;
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

    public void updateNombreUsuario(String nombre){
        String aux= this.loggedUser.getName();
        if(!nombre.equals("")){

            usuarios.get(aux).nombre=nombre;
            User u = usuarios.remove(aux);
            usuarios.put(nombre,u);

        }
    }

    public void updateContraseñaUsuario(String contraseña){
        if(!contraseña.equals("")){
            usuarios.get(this.loggedUser.nombre).contraseña=contraseña;
        }
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

    public void createIdea(String nombre, String descripcion, int prioridad, ArrayList<String> etiquetas,Integer color){
        assert isLoged();
        ArrayList<Integer> aux =new ArrayList<>();
        for (String i:etiquetas){
            aux.add(getEtiqueta(i));
        }
        this.loggedUser.addIdea(nombre, descripcion, prioridad, aux,color);
    }

    public void editarIdea(String nombre, String descripcion, int prioridad, ArrayList<String> etiquetas, Sistema.User.Idea idea){
        assert isLoged();
        ArrayList<Integer> aux =new ArrayList<>();
        for (String i:etiquetas){
            aux.add(getEtiqueta(i));
        }
        idea.setDescripcion(descripcion);
        idea.setNombre(nombre);
        idea.setPrioridad(prioridad);
        idea.setEtiquetas(aux);
    }

    public void addIdea(String nombre, String descripcion, int prioridad, ArrayList<Integer> etiquetas,Integer color){
        assert isLoged();
        this.loggedUser.addIdea(nombre, descripcion, prioridad, etiquetas,color);
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