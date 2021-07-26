package com.example.proyecto_movil1.categorias;

public class Modelo_Categoria {

    private String id;
    private String descripcion;
    private String url;

    public Modelo_Categoria() {
    }

    public Modelo_Categoria(String id, String descripcion, String url) {
        this.id = id;
        this.descripcion = descripcion;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
