package com.aquasport.ApartadosMenu;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

import com.aquasport.Elementos.*;
import com.aquasport.BaseDatos.ManejoDatosAdmin;
import com.aquasport.PlantillasVentanas.VentanaBase;

public class Ajustes extends VentanaBase {
    // Creacion e inicializacion de los elementos
    // de la ventana
    ManejoDatosAdmin consulta = new ManejoDatosAdmin();

    private JPanel panelPrincipal = new JPanel();
    private JPanel panelAjustes = new JPanel();

    private JLabel cambiarUsuarioLabel = new JLabel("Cambiar usuario:");
    private JLabel cambiarContrasenaLabel = new JLabel("Cambiar contraseña:");

    private RoundJTextField usuarioField = new RoundJTextField();
    private RoundJPasswordField contrasenaField = new RoundJPasswordField();

    private JButton botonCambiarUsuario = new JButton("Cambiar");
    private JButton botonCambiarContrasena = new JButton("Cambiar");

    public Ajustes() {
        super("Ajustes");
        initComponents();
    }

    // Formato de Etiquetas
    private void setupLabel(JLabel lbl) {
        lbl.setFont(fuentePrincipal);
        lbl.setForeground(Color.white);
    }

    private void initComponents() {
        setupLabel(cambiarUsuarioLabel);
        setupLabel(cambiarContrasenaLabel);

        // Acciones botones
        botonCambiarUsuario.addActionListener(evt -> botonCambiarUsuarioPerformed());
        botonCambiarContrasena.addActionListener(evt -> botonCambiarContrasenaPerformed());

        panelPrincipal.setLayout(new GridBagLayout());
        panelPrincipal.setOpaque(false);
        panelAjustes.setOpaque(false);

        GroupLayout panelAjustesLayout = new GroupLayout(panelAjustes);
        panelAjustes.setLayout(panelAjustesLayout);

        panelAjustesLayout.setAutoCreateGaps(true);
        panelAjustesLayout.setAutoCreateContainerGaps(true);

        panelAjustesLayout.setHorizontalGroup(
            panelAjustesLayout.createSequentialGroup()
                .addGroup(panelAjustesLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(cambiarUsuarioLabel)
                    .addComponent(cambiarContrasenaLabel))
                .addGroup(panelAjustesLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(usuarioField, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(contrasenaField, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                .addGroup(panelAjustesLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(botonCambiarUsuario)
                    .addComponent(botonCambiarContrasena))
        );
        panelAjustesLayout.setVerticalGroup(
            panelAjustesLayout.createSequentialGroup()
                .addGroup(panelAjustesLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(cambiarUsuarioLabel)
                    .addComponent(usuarioField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonCambiarUsuario))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelAjustesLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(cambiarContrasenaLabel)
                    .addComponent(contrasenaField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonCambiarContrasena))
        );

        panelPrincipal.add(panelAjustes);
        panelInformacion.setLayout(new BorderLayout());
        panelInformacion.add(panelPrincipal, BorderLayout.CENTER);
    }

    // Funcion para cambiar Usuario 
    // de la base de la base de datos
    private void botonCambiarUsuarioPerformed() {
        String nuevoUsuario = usuarioField.getText();

        if(nuevoUsuario.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "No se puede ejecutar la accion",
                "Campo vacio",
                JOptionPane.ERROR_MESSAGE);
        } else {
            consulta.setUsuario(nuevoUsuario);

            JOptionPane.showMessageDialog(this,
                "Se cambio el usuario con exito",
                "Accion exitosa",
                JOptionPane.INFORMATION_MESSAGE);

            usuarioField.setText("");
        }
    }

    // Funcion para cambiar la contraseña
    // de la base de datos
    private void botonCambiarContrasenaPerformed() {
        char[] contrasenaChar = contrasenaField.getPassword();
        String contrasena = new String(contrasenaChar);

        if(contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "No se puede ejecutar la accion", 
                "Campo vacio", 
                JOptionPane.ERROR_MESSAGE);
        } else {
            consulta.setContrasena(contrasena);

            JOptionPane.showMessageDialog(this,
                "Se cambio la contraseña con exito",
                "Accion exitosa",
                JOptionPane.INFORMATION_MESSAGE);

            contrasenaField.setText("");
            Arrays.fill(contrasenaChar, '0');
        }
    }
}
