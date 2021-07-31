package com.example.proyecto_movil1.P_Recomendados;

public class Modelo_Recomendados {

    private String categoria;
    private String producto;
    private String RatingBar;
    private String url_photo;

    public Modelo_Recomendados() {
    }

    public Modelo_Recomendados(String categoria, String producto, String ratingBar, String url_photo) {
        this.categoria = categoria;
        this.producto = producto;
        RatingBar = ratingBar;
        this.url_photo = url_photo;
    }


    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getRatingBar() {
        return RatingBar;
    }

    public void setRatingBar(String ratingBar) {
        RatingBar = ratingBar;
    }

    public String getUrl_photo() {
        return url_photo;
    }

    public void setUrl_photo(String url_photo) {
        this.url_photo = url_photo;
    }
}
