package dev.drmn.vocabularioapp;

public class Palabra {
    private String palabra;
    private String significado;
    private String dificultad;
    private String idioma;

    public Palabra(String palabra, String dificultad) {
        this.palabra = palabra;
        this.dificultad = dificultad;
    }

    public Palabra(String palabra, String significado, String dificultad, String idioma) {
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

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    public String getSignificado() {
        return significado;
    }

    public void setSignificado(String significado) {
        this.significado = significado;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }
}
