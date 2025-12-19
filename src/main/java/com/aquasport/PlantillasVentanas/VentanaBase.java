package com.aquasport.PlantillasVentanas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import com.aquasport.ApartadosMenu.*;
import com.aquasport.Elementos.*;
import com.aquasport.Login;

public abstract class VentanaBase extends JFrame {
    // Logo e iconos
    private ImageIcon icon = new ImageIcon(getClass().getResource("/Logo.png"));
    private JLabel icono = new JLabel(escalarImagen(icon, 25, 25));

    private ImageIcon iconoHome = obtenerImagen("/home_2.png", 20);
    private ImageIcon iconoHomeResaltado = obtenerImagen("/home_1.png", 20);

    private ImageIcon iconoHorarios = obtenerImagen("/calendar_month_2.png", 20);
    private ImageIcon iconoHorariosResaltado = obtenerImagen("/calendar_month_1.png", 20);

    private ImageIcon iconoInscripciones = obtenerImagen("/group_2.png", 20);
    private ImageIcon iconoInscripcionesResaltado = obtenerImagen("/group_1.png", 20);

    private ImageIcon iconoPagos = obtenerImagen("/paid_2.png", 20);
    private ImageIcon iconoPagosResaltado = obtenerImagen("/paid_1.png", 20);
    
    private ImageIcon iconoAjustes = obtenerImagen("/setting_2.png", 20);
    private ImageIcon iconoAjustesResaltado = obtenerImagen("/setting_1.png", 20);

    private ImageIcon iconoMembresias = obtenerImagen("/patient_list_2.png", 20);
    private ImageIcon iconoMembresiasResaltado = obtenerImagen("/patient_list_1.png", 20);

    // Botones
    private JButton botonAjustes = new JButton("Ajustes");
    private JButton botonCerrarSesion = new JButton("Cerrar sesión");
    private JButton botonHorario = new JButton("Horarios");
    private JButton botonInicio = new JButton("Inicio");
    private JButton botonInscripciones = new JButton("Inscripciones");
    private JButton botonPagos = new JButton("Pagos");
    private JButton botonMembresias = new JButton("Membresias");

    private JPanel fondoPanel = new JPanel();
    private JPanel jPanel2 = new JPanel();
    private JPanel menuBar = new JPanel();

    protected JLabel nombre1 = new JLabel("Aqua");
    protected JLabel nombre2 = new JLabel("Sport");

    protected CRoundJPanel cabeceraPanel = new CRoundJPanel(20);
    protected RoundJPanel panelInformacion = new RoundJPanel(30);

    protected Font fuentePrincipal = new Font("Arial", Font.BOLD, 14);
    protected Color colorNormal = new Color(2, 44, 105);
    protected Color colorResaltado = new Color(62, 171, 230);

    public VentanaBase() {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("AquaSport");
    }

    public VentanaBase(String nombre) {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("AquaSport");
        nombre1.setText(nombre);
        nombre1.setForeground(Color.white);
        nombre2.setText("");
    }

    private ImageIcon escalarImagen(ImageIcon iconoOriginal, int ancho, int alto) {
        Image icono = iconoOriginal.getImage();
        Image iconoEscalado = icono.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(iconoEscalado);
    }

    private ImageIcon obtenerImagen(String ruta, int tamano) {
        ImageIcon icon = new ImageIcon(getClass().getResource(ruta));
        ImageIcon icono = escalarImagen(icon, tamano, tamano);
        return icono;
    }

    // Plantilla de los botones del BarMenu
    private void setupButtonUI(JButton btn, ImageIcon icono, ImageIcon iconoResaltado) {
        btn.setIcon(icono);
        btn.setFont(fuentePrincipal);
        btn.setForeground(colorNormal);
        btn.setBorder(null);
        btn.setBorderPainted(false);
        btn.setMargin(new Insets(10, 20, 10, 20));
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setForeground(colorResaltado);
                btn.setIcon(iconoResaltado);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setForeground(colorNormal);
                btn.setIcon(icono);
            }
        });
    }

    private void setupButtonCerrarSesion(JButton btn) {
        btn.setFont(fuentePrincipal);
        btn.setForeground(new Color(110, 2, 2));
        btn.setBorder(null);
        btn.setBorderPainted(false);
        btn.setMargin(new Insets(10, 20, 10, 20));
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setForeground(new Color(225, 13, 13));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setForeground(new Color(110, 2,2));
            }
        });
    }

    protected void SetupUIBotones(JButton btn, int anchura, int altura) {
        btn.setFont(fuentePrincipal); 
        btn.setForeground(new Color(49, 116, 173));
        btn.setBorder(null);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(anchura, altura));
    }

    private void cambiarVentana(JFrame ventana) {
        ventana.setVisible(true);
        dispose();
    }

    private void initComponents() {
        // Configuración de ventana y botones de menú 
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1000, 600));
        setIconImage(icon.getImage());

        // Configuracion de botones e iconos del MenuBar
        setupButtonUI(botonInicio, iconoHome, iconoHomeResaltado);
        botonInicio.addActionListener(evt -> cambiarVentana(new Inicio()));

        setupButtonUI(botonHorario, iconoHorarios, iconoHorariosResaltado);
        botonHorario.addActionListener(evt -> cambiarVentana(new Horarios()));

        setupButtonUI(botonPagos, iconoPagos, iconoPagosResaltado);
        botonPagos.addActionListener(evt -> cambiarVentana(new Pagos()));

        setupButtonUI(botonInscripciones, iconoInscripciones, iconoInscripcionesResaltado);
        botonInscripciones.addActionListener(evt -> cambiarVentana(new Inscripciones()));

        setupButtonUI(botonMembresias, iconoMembresias, iconoMembresiasResaltado);
        botonMembresias.addActionListener(evt -> cambiarVentana(new Membresias()));

        setupButtonUI(botonAjustes, iconoAjustes, iconoAjustesResaltado);
        botonAjustes.addActionListener(evt -> cambiarVentana(new Ajustes()));

        setupButtonCerrarSesion(botonCerrarSesion);
        botonCerrarSesion.addActionListener(evt -> botonCerrarSesionActionPerformed());

        GroupLayout menuBarLayout = new GroupLayout(menuBar);
        menuBar.setLayout(menuBarLayout);
        menuBarLayout.setHorizontalGroup(
            menuBarLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(menuBarLayout.createSequentialGroup()
                .addGap(20)
                .addGroup(menuBarLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(botonInicio)
                    .addComponent(botonInscripciones)
                    .addComponent(botonMembresias)
                    .addComponent(botonHorario)
                    .addComponent(botonPagos)
                    .addComponent(botonAjustes)
                    .addComponent(botonCerrarSesion))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        menuBarLayout.setVerticalGroup(
            menuBarLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(menuBarLayout.createSequentialGroup()
                .addGap(24)
                .addGap(18)
                .addComponent(botonInicio)
                .addGap(20)
                .addComponent(botonInscripciones)
                .addGap(20)
                .addComponent(botonMembresias)
                .addGap(20)
                .addComponent(botonHorario)
                .addGap(20)
                .addComponent(botonPagos)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(botonAjustes)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(botonCerrarSesion)
                .addGap(20))
        );

        jPanel2.setBackground(new Color(0, 22, 54));
        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
        );

        fondoPanel.setBackground(new Color(129, 174, 247));
        panelInformacion.setBackground(new Color(49, 116, 173));
        cabeceraPanel.setBackground(new Color(3, 61, 102));

        nombre1.setFont(fuentePrincipal);
        nombre1.setForeground(colorResaltado);
        nombre1.setText("Aqua");
        nombre2.setFont(fuentePrincipal);
        nombre2.setForeground(Color.white);
        nombre2.setText("Sport");

        GroupLayout cabeceraPanelLayout = new GroupLayout(cabeceraPanel);
        cabeceraPanel.setLayout(cabeceraPanelLayout);
        cabeceraPanelLayout.setHorizontalGroup(
            cabeceraPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(cabeceraPanelLayout.createSequentialGroup()
                .addGap(15)
                .addComponent(icono)
                .addGap(10)
                .addComponent(nombre1)
                .addGap(3)
                .addComponent(nombre2)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        cabeceraPanelLayout.setVerticalGroup(
            cabeceraPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, cabeceraPanelLayout.createSequentialGroup()
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addGroup(cabeceraPanelLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(icono)
                    .addComponent(nombre1)
                    .addComponent(nombre2))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE))
        );
        
        GroupLayout panelInformacionLayout = new GroupLayout(panelInformacion);
        panelInformacion.setLayout(panelInformacionLayout);
        panelInformacionLayout.setHorizontalGroup(
            panelInformacionLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panelInformacionLayout.createSequentialGroup()
                .addGap(10)
                .addComponent(cabeceraPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10))
        );
        panelInformacionLayout.setVerticalGroup(
            panelInformacionLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panelInformacionLayout.createSequentialGroup()
                .addGap(10)
                .addComponent(cabeceraPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(485, Short.MAX_VALUE)) 
        );

        GroupLayout fondoPanelLayout = new GroupLayout(fondoPanel);
        fondoPanel.setLayout(fondoPanelLayout);
        fondoPanelLayout.setHorizontalGroup(
            fondoPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(fondoPanelLayout.createSequentialGroup()
                .addGap(50)
                .addComponent(panelInformacion, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(50))
        );
        fondoPanelLayout.setVerticalGroup(
            fondoPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(fondoPanelLayout.createSequentialGroup()
                .addGap(50)
                .addComponent(panelInformacion, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(menuBar, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(fondoPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(menuBar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fondoPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pack();
    }

    private void botonCerrarSesionActionPerformed() {
        int respuesta = JOptionPane.showConfirmDialog(
            null,
            "¿Esta seguro que desea salir?",
            "Confirmacion",
            JOptionPane.YES_NO_OPTION
        );

        if(respuesta == JOptionPane.YES_OPTION) {
            cambiarVentana(new Login());
        }
    }
}