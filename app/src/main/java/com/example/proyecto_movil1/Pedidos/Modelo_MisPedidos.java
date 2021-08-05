package com.example.proyecto_movil1.Pedidos;

public class Modelo_MisPedidos {

    private String pedido;
    private String fecha;
    private String status;

    public Modelo_MisPedidos() {
    }

    public Modelo_MisPedidos(String pedido, String fecha, String status) {
        this.pedido = pedido;
        this.fecha = fecha;
        this.status = status;
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
}
