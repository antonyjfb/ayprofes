package com.example.ayprofes.RecyclerViews;

public class MuestraProfesor {

    private String nombre;
    private String materia;
    private double calificacion;
    private String comentario;
    private float facilidad;
    private float claridad;
    private float ayuda;
    private float carga;
    private float recomendacion;
    private double promedio;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }

    public float getFacilidad() {
        return facilidad;
    }

    public void setFacilidad(float facilidad) {
        this.facilidad = facilidad;
    }

    public float getClaridad() {
        return claridad;
    }

    public void setClaridad(float claridad) {
        this.claridad = claridad;
    }

    public float getAyuda() {
        return ayuda;
    }

    public void setAyuda(float ayuda) {
        this.ayuda = ayuda;
    }

    public float getCarga() {
        return carga;
    }

    public void setCarga(float carga) {
        this.carga = carga;
    }

    public float getRecomendacion() {
        return recomendacion;
    }

    public void setRecomendacion(float recomendacion) {
        this.recomendacion = recomendacion;
    }


    public MuestraProfesor(String comentario, float claridad, float facilidad, float ayuda, float carga, float recomendacion) {
        this.claridad = claridad;
        this.facilidad = facilidad;
        this.ayuda = ayuda;
        this.carga = carga;
        this.recomendacion = recomendacion;
        this.comentario = comentario;
        double promedioPreliminar = (claridad+facilidad+ayuda+carga+recomendacion)/5;
        this.promedio =  Math.floor(promedioPreliminar * 10) / 10;
    }

    public MuestraProfesor(String nombre, String materia, double calificacion) {
        this.nombre = nombre;
        this.materia = materia;
        this.calificacion = calificacion;
    }

    public MuestraProfesor(String nombre, String materia) {
        this.nombre = nombre;
        this.materia = materia;
        this.calificacion = calificacion;
    }

    public MuestraProfesor(){

    }


    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public double getPromedio() {
        return promedio;
    }

    public void setPromedio(double promedio) {
        this.promedio = promedio;
    }
}
