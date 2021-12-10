package org.iesinfantaelena.model;

public class Asignatura {
    private int identificador;
    private String nombre;
    private String tipo;
    private float creditos;

    public Asignatura() {
    }

    public Asignatura(int identificador, String nombre, String tipo, float creditos) {
        this.identificador = identificador;
        this.nombre = nombre;
        this.tipo = tipo;
        this.creditos = creditos;
    }

    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public float getCreditos() {
        return creditos;
    }

    public void setCreditos(float creditos) {
        this.creditos = creditos;
    }

    @Override
    public String toString() {
        return "Asignatura{" +
                "identificador=" + identificador +
                ", nombre='" + nombre + '\'' +
                ", tipo='" + tipo + '\'' +
                ", creditos=" + creditos +
                '}';
    }
}
