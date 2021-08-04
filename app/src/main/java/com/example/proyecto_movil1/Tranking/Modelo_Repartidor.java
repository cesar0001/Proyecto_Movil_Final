package com.example.proyecto_movil1.Tranking;

public class Modelo_Repartidor {

    private String nombre;
    private String fecha;
    private String status;

    public Modelo_Repartidor() {
    }


    public Modelo_Repartidor(String nombre, String fecha, String status) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.status = status;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
