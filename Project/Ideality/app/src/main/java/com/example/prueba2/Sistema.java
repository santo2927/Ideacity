package com.example.prueba2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class Sistema implements Serializable {
    class User{
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
            public int compareTo(Idea o) {
                return o.prioridad.compareTo(this.prioridad);
            }
        }

        class Carpeta{
            private String nombre;
            private ArrayList<Idea> ideas;

            public Carpeta(String nombre) {
                this.nombre = nombre;
                ideas=new ArrayList<>();
            }

            public void addIdea(Idea i){
                this.ideas.add(i);
            }
        }

        private String nombre;
        private String correo;
        private String contraseña;
        private ArrayList<Carpeta> carpetas;
        private ArrayList<Idea> ideas;

        public User(String nombre,String correo, String contraseña){
            this.contraseña=contraseña;
            this.correo=correo;
            this.nombre=nombre;
            this.carpetas=new ArrayList<>();
            this.ideas=new ArrayList<>();
        }

        private void moverACarpeta(int idea,int carpeta){
            this.carpetas.get(carpeta).addIdea(this.ideas.get(idea));
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

    private static final long serialVersionUID = 1L;
    private static Sistema instancia = null;
    private static final String DATABASE = "database.obj";
    private HashMap<String, User> usuarios;
    private HashMap<Integer,String> etiquetas;
    private User loggedUser;

    public Sistema(){
        usuarios=new HashMap<>();
        etiquetas=new HashMap<>();
        loggedUser=null;
    }

    public static Sistema cargarSistema() {
        try {
            FileInputStream file = new FileInputStream(DATABASE);
            ObjectInputStream object = new ObjectInputStream(file);
            Sistema instancia2 = (Sistema) object.readObject();
            object.close();
            file.close();
            return instancia2;
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        return null;

    }

    public Boolean guardarSistema() {
        try {
            FileOutputStream file = new FileOutputStream(DATABASE);
            ObjectOutputStream object = new ObjectOutputStream(file);
            object.writeObject(this);
            object.close();
            file.close();
            return true;

        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    public static Sistema getSistema() {
        if (instancia == null) {
            File f = new File(DATABASE);
            if (f.exists()) {
                instancia = cargarSistema();
            } else {
                instancia = new Sistema();
            }
        }
        return instancia;
    }

    private boolean isRegistered(String name){
        return usuarios.containsKey(name);
    }

    public boolean register(String nombre, String correo, String contraseña){
        if(!isRegistered(nombre)) {
            usuarios.put(nombre, new User(nombre, correo, contraseña));
            return true;
        }
        return false;
    }

    public boolean logIn(String nombre, String contraseña){
        if(isRegistered(nombre)){
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

    public Integer createEtiqueta(String s){
        return getEtiqueta(s);
    }

    public void moverACarpeta(String idea, String carpeta){
        assert isLoged();
        this.loggedUser.moverACarpeta(idea,carpeta);
    }


}