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

    private JPanel panelAjustes = new JPanel();
    private JLabel cambiarUsuarioLabel = new JLabel("Cambiar usuario:");
    private JLabel cambiarContrasenaLabel = new JLabel("Cambiar contraseña:");

    private RoundJTextField usuarioField = new RoundJTextField();
    private RoundJPasswordField contrasenaField = new RoundJPasswordField();

    private JButton botonCambiarUsuario = new JButton("Cambiar");
    private JButton botonCambiarContrasena = new JButton("Cambiar");

    private Dimension tamanoField = new Dimension(200, 30);
    private Dimension tamanoBoton = new Dimension(100, 30);

    private void setupLabel(JLabel lbl) {
        lbl.setFont(fuentePrincipal);
        lbl.setForeground(Color.white);
    }

    public Ajustes() {
        super();

        nombre1.setText("Ajustes");
        nombre1.setForeground(Color.white);
        nombre2.setText("");

        setupLabel(cambiarUsuarioLabel);
        setupLabel(cambiarContrasenaLabel);

        botonCambiarUsuario.addActionListener(evt -> botonCambiarUsuarioPerformed());
        botonCambiarContrasena.addActionListener(evt -> botonCambiarContrasenaPerformed());

        panelInformacion.setLayout(new BorderLayout());

        panelAjustes.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        panelAjustes.setOpaque(false);
        panelAjustes.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); 

        usuarioField.setPreferredSize(tamanoField);
        contrasenaField.setPreferredSize(tamanoField);

        botonCambiarUsuario.setPreferredSize(tamanoBoton);
        botonCambiarContrasena.setPreferredSize(tamanoBoton);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        panelAjustes.add(cambiarUsuarioLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        panelAjustes.add(usuarioField, gbc);

        gbc.gridx = 2;
        panelAjustes.add(botonCambiarUsuario, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        panelAjustes.add(cambiarContrasenaLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        panelAjustes.add(contrasenaField, gbc);

        gbc.gridx = 2;
        panelAjustes.add(botonCambiarContrasena, gbc);

        panelInformacion.add(panelAjustes, BorderLayout.CENTER);
    }

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
