package com.example.proyecto_movil1.Pedidos;

public class Modelo_MisPedidos {

    private String pedido;
    private String fecha;
    private String status;
    private String latitud;
    private String longitud;
    private String calificacion;
    private String nombre;
    private String correo;
    private String telefono;
    private String direccion;
    private String url_foto;

    public Modelo_MisPedidos() {
    }

    public Modelo_MisPedidos(String pedido, String fecha, String status, String latitud, String longitud, String calificacion, String nombre, String correo, String telefono, String direccion, String url_foto) {
        this.pedido = pedido;
        this.fecha = fecha;
        this.status = status;
        this.latitud = latitud;
        this.longitud = longitud;
        this.calificacion = calificacion;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.direccion = direccion;
        this.url_foto = url_foto;
    }

    public String getPedido() {
        return pedido;
    }

    public void setPedido(String pedido) {
        this.pedido = pedido;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(String calificacion) {
        this.calificacion = calificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getUrl_foto() {
        return url_foto;
    }

    public void setUrl_foto(String url_foto) {
        this.url_foto = url_foto;
    }
}
