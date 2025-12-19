package com.aquasport.ApartadosMenu;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.aquasport.VentanasRegistros.GestionRegistros.*;
import com.aquasport.BaseDatos.VisualizarDatosHorarios;
import com.aquasport.PlantillasVentanas.VentanaBase;

public class Horarios extends VentanaBase {
    // Creacion e inicializacion 
    // de los elementos de la ventana
    private JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10)); 
    private JButton botonEditar = new JButton("Editar");
    private JButton botonEliminar = new JButton("Eliminar");

    public Horarios() {
        super("Horarios");

        panelInformacion.setLayout(new BorderLayout());
        panelInformacion.setBorder(new EmptyBorder(60, 30, 0, 30)); 

        VisualizarDatosHorarios tablaHorarios = new VisualizarDatosHorarios();     
        JScrollPane horariosScroll = tablaHorarios.crearTablaHorarios();

        panelInformacion.add(horariosScroll, BorderLayout.CENTER);

        SetupUIBotones(botonEditar, 120, 30);
        SetupUIBotones(botonEliminar, 120, 30);

        panelBotones.add(botonEditar);
        panelBotones.add(botonEliminar);
        panelBotones.setOpaque(false);

        panelInformacion.add(panelBotones, BorderLayout.SOUTH);

        botonEditar.addActionListener(evt -> new EditarHorarios().setVisible(true));
        botonEliminar.addActionListener(evt -> new EliminarHorario().setVisible(true));
    }
}
