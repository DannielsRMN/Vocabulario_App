package dev.drmn.vocabularioapp;

import java.time.LocalDate;

public class HistorialSemanal {
    private String contexto;
    private LocalDate fecha;
    private String evento;

    public HistorialSemanal(String contexto, LocalDate fecha, String evento) {
        this.contexto = contexto;
        this.fecha = fecha;
        this.evento = evento;
    }

    public String getContexto() {
        return contexto;
    }

    public void setContexto(String contexto) {
        this.contexto = contexto;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }
}
