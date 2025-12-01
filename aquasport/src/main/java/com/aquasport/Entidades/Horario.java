package com.aquasport.Entidades;

public class Horario {
    private int idHorario;
    private String maestroTitular;
    private String nivel;
    private int cuposDisponibles;
    private String horarioClase;
    private String dias;

    public Horario(int idHorario, String maestroTitular, String nivel, int cuposDisponibles, String horarioClase, String dias) {
        this.idHorario = idHorario;
        this.maestroTitular = maestroTitular;
        this.nivel = nivel;
        this.cuposDisponibles = cuposDisponibles;
        this.horarioClase = horarioClase;
        this.dias = dias;
    }

    public int getIdHorario() { return idHorario; }
    public String getMaestroTitular() { return maestroTitular; }
    public String getNivel() { return nivel; }
    public int getCuposDisponibles() { return cuposDisponibles; }
    public String getHorarioClase() { return horarioClase; }
    public String getDias() { return dias; }

    @Override
    public String toString() {
        return dias + " | " + horarioClase + " (" + nivel + ")";
    }
}