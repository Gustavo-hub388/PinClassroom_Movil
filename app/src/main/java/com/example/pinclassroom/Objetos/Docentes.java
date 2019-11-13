package com.example.pinclassroom.Objetos;

public class Docentes {

    String contacto;
    String facultad;
    String historial;
    String nombre;

    public Docentes() {
    }

    public Docentes(String contacto, String facultad, String historial, String nombre) {
        this.contacto = contacto;
        this.facultad = facultad;
        this.historial = historial;
        this.nombre = nombre;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getFacultad() {
        return facultad;
    }

    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }

    public String getHistorial() {
        return historial;
    }

    public void setHistorial(String historial) {
        this.historial = historial;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
