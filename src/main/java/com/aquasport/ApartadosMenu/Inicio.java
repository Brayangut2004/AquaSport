package com.aquasport.ApartadosMenu;

import javax.swing.*;
import java.awt.*;

import com.aquasport.VentanasRegistros.*;
import com.aquasport.PlantillasVentanas.VentanaBase;

public class Inicio extends VentanaBase {
    // Elementos de la ventana
    private JButton registroAlumnoBoton = new JButton("Registrar nuevo alumno");
    private JButton registroPagoBoton = new JButton("Registrar nuevo pago");
    private JButton registroHorarioBoton = new JButton("Registro de horarios");
    private JButton membresiasBoton = new JButton("Registrar membresias");
    private JLabel bievenidaLabel = new JLabel("¡Bienvenid@!");

    public Inicio() {
        super(); 
        initInicioContent();
    }

    private void initInicioContent() {
        SetupUIBotones(registroAlumnoBoton, 75, 20);
        registroAlumnoBoton.addActionListener(evt -> new RegistroAlumno().setVisible(true));

        SetupUIBotones(registroPagoBoton, 75,20);
        registroPagoBoton.addActionListener(evt -> new RegistroPagos().setVisible(true));

        SetupUIBotones(registroHorarioBoton, 75, 20);
        registroHorarioBoton.addActionListener(evt -> new RegistroHorarios().setVisible(true));

        bievenidaLabel.setFont(new Font("Arial", 1, 28));
        bievenidaLabel.setForeground(Color.white);

        membresiasBoton.setBackground(new Color(3, 61, 102));
        membresiasBoton.setFont(new Font("Arial", Font.BOLD, 20));
        membresiasBoton.setForeground(Color.white);
        membresiasBoton.setBorder(null);
        membresiasBoton.setBorderPainted(false);
        membresiasBoton.addActionListener(evt -> new RegistroMembresias().setVisible(true));

        GroupLayout panelInformacionLayout = new GroupLayout(panelInformacion);
        panelInformacion.setLayout(panelInformacionLayout);
        
        panelInformacionLayout.setHorizontalGroup(
            panelInformacionLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panelInformacionLayout.createSequentialGroup()
                .addGap(10) 
                .addGroup(panelInformacionLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(cabeceraPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelInformacionLayout.createSequentialGroup()
                        .addGap(50) 
                        .addGroup(panelInformacionLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(bievenidaLabel, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelInformacionLayout.createSequentialGroup())
                            .addComponent(membresiasBoton, GroupLayout.PREFERRED_SIZE, 270, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE) 
                        .addGroup(panelInformacionLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(registroAlumnoBoton, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 230, GroupLayout.PREFERRED_SIZE)
                            .addComponent(registroPagoBoton, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 230, GroupLayout.PREFERRED_SIZE)
                            .addComponent(registroHorarioBoton, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 230, GroupLayout.PREFERRED_SIZE))
                        .addGap(60))) 
                .addGap(10)) 
        );
        panelInformacionLayout.setVerticalGroup(
            panelInformacionLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panelInformacionLayout.createSequentialGroup()
                .addGap(10) 
                .addComponent(cabeceraPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(50) 
                
                .addGroup(panelInformacionLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(registroAlumnoBoton, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                    .addComponent(bievenidaLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18)
                .addGroup(panelInformacionLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(panelInformacionLayout.createSequentialGroup()
                        .addComponent(registroPagoBoton, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                        .addGap(18)
                        .addComponent(registroHorarioBoton, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelInformacionLayout.createSequentialGroup()
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(membresiasBoton, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)))
                
                .addContainerGap(34, Short.MAX_VALUE)) 
        );
    }
}