package com.example.pinclassroom.Aulas;

public class AulaObjetos {
    private String name;
    private String edificio;

    public AulaObjetos(String name, String edificio) {
        this.name = name;
        this.edificio = edificio;
    }

    public AulaObjetos() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEdificio() {
        return edificio;
    }

    public void setEdificio(String edificio) {
        this.edificio = edificio;
    }
}
