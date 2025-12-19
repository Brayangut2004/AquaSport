package com.aquasport.ApartadosMenu;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.aquasport.VentanasRegistros.GestionRegistros.*;
import com.aquasport.BaseDatos.VisualizarDatosMembresia;
import com.aquasport.PlantillasVentanas.VentanaBase;

public class Membresias extends VentanaBase{
    // Elementos de la ventana
    private JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10)); 
    private JButton botonEditar = new JButton("Editar");
    private JButton botonEliminar = new JButton("Eliminar");

    public Membresias() {
        super("Membresias");

        panelInformacion.setLayout(new BorderLayout());
        panelInformacion.setBorder(new EmptyBorder(60, 30, 0, 30));

        VisualizarDatosMembresia tablaPagos = new VisualizarDatosMembresia();
        JScrollPane pagosScroll = tablaPagos.crearTablaMembresias();

        panelInformacion.add(pagosScroll, BorderLayout.CENTER);

        SetupUIBotones(botonEditar, 120, 30);
        SetupUIBotones(botonEliminar, 120, 30);

        botonEditar.addActionListener(evt -> new EditarMembresia().setVisible(true));
        botonEliminar.addActionListener(evt -> new EliminarMembresia().setVisible(true));
        
        panelBotones.add(botonEditar);
        panelBotones.add(botonEliminar);
        panelBotones.setOpaque(false);
        panelInformacion.add(panelBotones, BorderLayout.SOUTH);
    }
}
