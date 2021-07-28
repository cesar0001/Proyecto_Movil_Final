package com.example.proyecto_movil1.Carrito;

public class CarritoCompra {

    private String nombre;
    private String precio;
    private String cantidad;
    private String url;
    private String id_Producto;

    public CarritoCompra() {
    }

    public CarritoCompra(String nombre, String precio, String cantidad, String id_Producto) {
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.id_Producto = id_Producto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId_Producto() {
        return id_Producto;
    }

    public void setId_Producto(String id_Producto) {
        this.id_Producto = id_Producto;
    }
}
