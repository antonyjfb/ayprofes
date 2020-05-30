package com.example.ayprofes;

import androidx.appcompat.widget.Toolbar;

public class Usuario {
    public Usuario(String nombre) {
        this.nombre = nombre;
    }

    private String nombre;
    private String contraseña;
    private String pregunta;
    private String respuesta;

    public Usuario(String nombre, String contraseña, String pregunta, String respuesta) {
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.pregunta = pregunta;
        this.respuesta = respuesta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }



}
