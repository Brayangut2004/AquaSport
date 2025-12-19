package com.aquasport.Elementos;

public class AlumnoItem {
    private int id;
    private String nombre;

    public AlumnoItem(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre; 
    }
}