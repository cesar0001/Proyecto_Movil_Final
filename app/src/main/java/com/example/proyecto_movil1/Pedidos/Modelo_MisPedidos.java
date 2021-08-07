package com.example.proyecto_movil1.Pedidos;

public class Modelo_MisPedidos {

    private String pedido;
    private String fecha;
    private String status;
    private String latitud;
    private String longitud;

    public Modelo_MisPedidos() {
    }


    public Modelo_MisPedidos(String pedido, String fecha, String status, String latitud, String longitud) {
        this.pedido = pedido;
        this.fecha = fecha;
        this.status = status;
        this.latitud = latitud;
        this.longitud = longitud;
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
}
