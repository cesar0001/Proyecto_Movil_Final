package com.example.proyecto_movil1.Pedidos;

public class Modelo_informacion_Pedidos_Productos {

    private String nombre;
    private String cantidad;
    private String precio;
    private String Url;


    public Modelo_informacion_Pedidos_Productos() {
    }

    public Modelo_informacion_Pedidos_Productos(String nombre, String cantidad, String precio, String url) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        Url = url;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
