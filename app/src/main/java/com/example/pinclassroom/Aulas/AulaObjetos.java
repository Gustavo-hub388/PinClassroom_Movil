package com.example.pinclassroom.Aulas;

public class AulaObjetos {
    private String name;
    private String edificio;
    private String images;

    public AulaObjetos(String name, String edificio, String images) {
        this.name = name;
        this.edificio = edificio;
        this.images = images;
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

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
}
