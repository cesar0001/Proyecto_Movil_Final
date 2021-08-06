package com.example.proyecto_movil1.Tranking;

public class Modelo_Repartidor {

    private String pedidos;
    private String fecha;
    private String status;

    public Modelo_Repartidor() {
    }

    public Modelo_Repartidor(String pedidos, String fecha, String status) {
        this.pedidos = pedidos;
        this.fecha = fecha;
        this.status = status;
    }

    public String getPedidos() {
        return pedidos;
    }

    public void setPedidos(String pedidos) {
        this.pedidos = pedidos;
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
