package dev.drmn.vocabularioapp;

public class Racha {
    private int id;
    private int cantDias;

    public Racha(int id, int cantDias) {
        this.id = id;
        this.cantDias = cantDias;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantDias() {
        return cantDias;
    }

    public void setCantDias(int cantDias) {
        this.cantDias = cantDias;
    }
}
