package com.example.pinclassroom.Docentes;

public class DocenteObjetos {
    private String nombre;
    private String facultad;

    public DocenteObjetos(String nombre, String facultad) {
        this.nombre = nombre;
        this.facultad = facultad;
    }

    public DocenteObjetos() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFacultad() {
        return facultad;
    }

    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }
}
