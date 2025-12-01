package com.aquasport.ApartadosMenu;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.aquasport.BaseDatos.VisualizarDatosPagos;
import com.aquasport.PlantillasVentanas.VentanaBase;

public class Pagos extends VentanaBase{
    public Pagos() {
        super();
        
        nombre1.setText("Pagos");
        nombre1.setForeground(Color.white);
        nombre2.setText("");
        panelInformacion.setLayout(new BorderLayout());
        panelInformacion.setBorder(new EmptyBorder(60, 30, 0, 30));

        VisualizarDatosPagos tablaPagos = new VisualizarDatosPagos();
        JScrollPane pagosScroll = tablaPagos.crearTablaPagos();

        panelInformacion.add(pagosScroll, BorderLayout.CENTER);
    }
}
