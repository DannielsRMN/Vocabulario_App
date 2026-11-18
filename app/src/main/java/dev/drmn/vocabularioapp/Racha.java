package dev.drmn.vocabularioapp;

public class Racha {
    private int id;
    private int cantDias;
    private int usuario_id;

    public Racha(int id, int cantDias, int usuario_id){
        this.id = id;
        this.cantDias = cantDias;
        this.usuario_id = usuario_id;
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

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }
}
