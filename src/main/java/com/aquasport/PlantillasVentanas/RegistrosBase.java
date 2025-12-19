package com.aquasport.PlantillasVentanas;

import javax.swing.*;
import java.awt.*;

public class RegistrosBase extends JFrame {
    private ImageIcon icon = new ImageIcon(getClass().getResource("/Logo.png"));
    private GridLayout miLayout = new GridLayout();
    protected JPanel panelPrincipal = new JPanel();

    private Font fuentePrincipal = new Font("Arial", Font.BOLD, 14);

    public RegistrosBase(String nombre) {
        initComponents();
        setLocationRelativeTo(null);
        setTitle(nombre);
    }

    protected void SetupLabel(JLabel lbl) {
        lbl.setFont(fuentePrincipal);
        lbl.setForeground(Color.white);
    }

    protected void SetupBoton(JButton btn) {
        btn.setFont(fuentePrincipal);
        btn.setForeground(Color.white);
        btn.setBackground(new Color(4, 1, 36));
    }

    private void initComponents() {
        // Configuracion de la ventana
        setPreferredSize(new Dimension(700, 500));
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setIconImage(icon.getImage());
        panelPrincipal.setBackground(new Color(2, 44, 105));

        add(panelPrincipal);
        panelPrincipal.setLayout(miLayout);
        pack();
    }
}
