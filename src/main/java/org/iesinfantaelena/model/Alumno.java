package org.iesinfantaelena.model;

public class Alumno {
    private String nombre;
    private int id;
    private String apellidos;
    private int curso;
    private int titulacion;

    public Alumno() {
    }

    public Alumno(String nombre, int id, String apellidos, int curso, int titulacion) {
        this.nombre = nombre;
        this.id = id;
        this.apellidos = apellidos;
        this.curso = curso;
        this.titulacion = titulacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getCurso() {
        return curso;
    }

    public void setCurso(int curso) {
        this.curso = curso;
    }

    public int getTitulacion() {
        return titulacion;
    }

    public void setTitulacion(int titulacion) {
        this.titulacion = titulacion;
    }

    @Override
    public String toString() {
        return "Alumno{" +
                "nombre='" + nombre + '\'' +
                ", id=" + id +
                ", apellidos='" + apellidos + '\'' +
                ", curso=" + curso +
                ", titulacion=" + titulacion +
                '}';
    }
}
