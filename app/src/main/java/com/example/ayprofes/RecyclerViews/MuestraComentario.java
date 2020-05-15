package com.example.ayprofes.RecyclerViews;

public class MuestraComentario {
    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    private String comentario;

    public MuestraComentario(String comentario) {
        this.comentario = comentario;
    }
}
