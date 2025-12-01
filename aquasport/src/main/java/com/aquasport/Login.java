package com.aquasport;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

import com.aquasport.BaseDatos.ManejoDatosAdmin;
import com.aquasport.ApartadosMenu.Inicio;
import com.aquasport.Elementos.RoundJPasswordField;
import com.aquasport.Elementos.RoundJTextField;

public class Login extends JFrame {
    // Creacion e inicializacion
    // de los elementos de la ventana

    // Obtencion  e inicializacion del logo
    private ImageIcon icon = new ImageIcon(getClass().getResource("/Logo.png"));
    private JLabel icono = new JLabel(escalarImagen(icon, 70, 70));

    private ManejoDatosAdmin datosAdmin = new ManejoDatosAdmin();

    private JPanel panelPrincipal = new JPanel();
    private JLabel usuarioLabel = new JLabel("Usuario");
    private JLabel contrasenaLabel = new JLabel("Contraseña");
    private RoundJTextField usuarioField = new RoundJTextField();
    private RoundJPasswordField contrasenaField = new RoundJPasswordField();

    private JButton botonLogin = new JButton("Entrar");

    private Font fuentePrincipal = new Font("Arial", Font.BOLD, 14);
    private Color colorFondo = new Color(2, 44, 105);

    public Login() {
        initComponents();
    }

    private ImageIcon escalarImagen(ImageIcon iconoOriginal, int ancho, int alto) {
        Image icono = iconoOriginal.getImage();
        Image iconoEscalado = icono.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(iconoEscalado);
    }

    private void initComponents() {
        // Configuracion basica de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("AquaSport");
        setPreferredSize(new Dimension(800, 500));
        setResizable(false);
        getContentPane().setLayout(new GridBagLayout());
        getContentPane().setBackground(colorFondo);
        setIconImage(icon.getImage());

        // Configuracion del panel principal
        panelPrincipal.setBackground(colorFondo);
        panelPrincipal.setPreferredSize(new Dimension(250, 250));
        
        usuarioLabel.setFont(fuentePrincipal); 
        usuarioLabel.setForeground(new Color(255, 255, 255));
        usuarioField.addActionListener(evt -> contrasenaField.requestFocusInWindow());

        contrasenaLabel.setFont(fuentePrincipal); 
        contrasenaLabel.setForeground(new Color(255, 255, 255));
        contrasenaField.addActionListener(evt -> botonLoginActionPerformed());

        botonLogin.setFont(fuentePrincipal);
        botonLogin.setBackground(new Color(4, 1, 36));
        botonLogin.setForeground(Color.white);
        botonLogin.addActionListener(evt -> botonLoginActionPerformed());

        GroupLayout panelPrincipalLayout = new GroupLayout(panelPrincipal);
        
        panelPrincipal.setLayout(panelPrincipalLayout);
        panelPrincipalLayout.setHorizontalGroup(
            panelPrincipalLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
            .addComponent(icono)
            .addComponent(usuarioField)
            .addComponent(contrasenaField)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addGroup(panelPrincipalLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(usuarioLabel))
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(contrasenaLabel))
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addGap(89)
                        .addComponent(botonLogin)))
                .addContainerGap(89, Short.MAX_VALUE))
        );
        panelPrincipalLayout.setVerticalGroup(
            panelPrincipalLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addComponent(icono)
                .addGap(5)
                .addComponent(usuarioLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(usuarioField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(18)
                .addComponent(contrasenaLabel)
                .addGap(12)
                .addComponent(contrasenaField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(18)
                .addComponent(botonLogin)
                .addGap(0, 14, Short.MAX_VALUE))
        );

        getContentPane().add(panelPrincipal, new GridBagConstraints());

        pack();
        setLocationRelativeTo(null);
    }                       

    // Funcion que verifica si los datos ingresados
    // son correctos
    private void botonLoginActionPerformed() {   
        // Se obtienen los datos ingresados por el usuario                                        
        String usuario = usuarioField.getText();
        char[] contrasenaChars = contrasenaField.getPassword();

        String[] datos = datosAdmin.getDatos();

        boolean credencialesCorrectas = usuario.equals(datos[0]) && Arrays.equals(contrasenaChars, datos[1].toCharArray());

        // Se comparan los datos ingresados con los datos 
        // almacenados en la base de datos
        if (credencialesCorrectas) {
            JOptionPane.showMessageDialog(this, "Bienvenido");
            new Inicio().setVisible(true);
            
            dispose(); 
        } else {
            JOptionPane.showMessageDialog(this, 
                "Usuario o contraseña incorrectos", 
                "Error al iniciar sesión", 
                JOptionPane.ERROR_MESSAGE);

                usuarioField.setText("");
                contrasenaField.setText("");
                usuarioField.requestFocusInWindow();
        }
        Arrays.fill(contrasenaChars, ' ');
    }                                                     
}