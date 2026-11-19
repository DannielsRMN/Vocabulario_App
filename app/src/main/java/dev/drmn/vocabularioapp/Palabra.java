package dev.drmn.vocabularioapp;

public class Palabra {
    private String palabra;
    private String significado;
    private int dificultad;
    private int idioma;

    public Palabra(String palabra, String significado) {
        this.palabra = palabra;
        this.significado = significado;
    }

    public Palabra(String palabra, String significado, int dificultad, int idioma) {
        this.palabra = palabra;
        this.significado = significado;
        this.dificultad = dificultad;
        this.idioma = idioma;
    }

    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    public String getSignificado() {
        return significado;
    }

    public void setSignificado(String significado) {
        this.significado = significado;
    }

    public int getDificultad() {
        return dificultad;
    }

    public void setDificultad(int dificultad) {
        this.dificultad = dificultad;
    }

    public int getIdioma() {
        return idioma;
    }

    public void setIdioma(int idioma) {
        this.idioma = idioma;
    }
}
