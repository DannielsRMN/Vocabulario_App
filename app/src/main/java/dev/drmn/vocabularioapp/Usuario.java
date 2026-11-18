package dev.drmn.vocabularioapp;

public class Usuario {
    private int id;
    private String nombres;
    private String apellidos;
    private String fechaNacimiento;
    private String correoElectronico;
    private String contrasena;
    private String rol;
    private String idioma;
    private int id_idioma;
    private String difficultad;
    private int id_dificultad;

    public Usuario(int id, String correoElectronico, String contrasena) {
        this.id = id;
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
    }

    public Usuario(int id, String correoElectronico, String contrasena, String idioma, String dificultad) {
        this.id = id;
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
        this.idioma = idioma;
        this.difficultad = dificultad;
    }

    public Usuario(String nombres, String apellidos, String fechaNacimiento, String correoElectronico, String contrasena, String rol) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
        this.rol = rol;
    }

    public Usuario(String nombres, String apellidos, String fechaNacimiento, String correoElectronico, String contrasena, String rol, String idioma, int id_idioma, String difficultad, int id_dificultad) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
        this.rol = rol;
        this.idioma = idioma;
        this.id_idioma = id_idioma;
        this.difficultad = difficultad;
        this.id_dificultad = id_dificultad;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getDifficultad() {
        return difficultad;
    }

    public void setDifficultad(String difficultad) {
        this.difficultad = difficultad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
