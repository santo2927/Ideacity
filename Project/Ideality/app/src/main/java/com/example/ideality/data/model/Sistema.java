package com.example.ideality.data.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Sistema {
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

        public void moverACarpeta(int idea,int carpeta){
            this.carpetas.get(carpeta).addIdea(this.ideas.get(idea));
            this.ideas.remove(idea);
        }

    }

    private HashMap<String, User> usuarios;
    private HashMap<Integer,String> etiquetas;
    private User loggedUser;
    private static Sistema INSTANCE;

    public Sistema(){
        usuarios=new HashMap<>();
        etiquetas=new HashMap<>();
        loggedUser=null;
        INSTANCE=this;
    }


}
