package com.aquasport.ApartadosMenu;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.aquasport.VentanasRegistros.GestionRegistros.*;
import com.aquasport.BaseDatos.VisualizarDatosInscripciones;
import com.aquasport.PlantillasVentanas.VentanaBase;

public class Inscripciones extends VentanaBase{
    // Elementos de la ventana
    private JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10)); 
    private JButton botonEditar = new JButton("Editar");
    private JButton botonEliminar = new JButton("Eliminar");

    public Inscripciones() {
        super("Inscripciones");

        panelInformacion.setLayout(new BorderLayout());
        panelInformacion.setBorder(new EmptyBorder(60, 30, 0, 30)); 

        VisualizarDatosInscripciones tablaInscripciones = new VisualizarDatosInscripciones();
        JScrollPane inscripcionesScroll = tablaInscripciones.crearTablaInscripciones();

        panelInformacion.add(inscripcionesScroll, BorderLayout.CENTER);

        SetupUIBotones(botonEditar, 120, 30);
        SetupUIBotones(botonEliminar, 120, 30);

        botonEditar.addActionListener(evt -> new EditarAlumno().setVisible(true));
        botonEliminar.addActionListener(evt -> new EliminarAlumno().setVisible(true));
        
        panelBotones.add(botonEditar);
        panelBotones.add(botonEliminar);
        panelBotones.setOpaque(false);
        panelInformacion.add(panelBotones, BorderLayout.SOUTH);
    }
}
