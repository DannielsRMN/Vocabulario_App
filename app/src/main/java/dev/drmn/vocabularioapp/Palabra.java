package dev.drmn.vocabularioapp;

public class Palabra {
    private String palabra;
    private String dificultad;

    public Palabra(String palabra, String dificultad) {
        this.palabra = palabra;
        this.dificultad = dificultad;
    }

    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }
}
