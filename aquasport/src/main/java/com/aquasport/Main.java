package com.aquasport;

import java.util.logging.*;
import java.awt.*;
import javax.swing.*;

import com.aquasport.BaseDatos.InicializarBaseDato;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String args[]) {
        
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | UnsupportedLookAndFeelException ex) {
            logger.log(Level.SEVERE, null, ex);
        }

        InicializarBaseDato baseDatos = new InicializarBaseDato();
        baseDatos.crearTablas();
        EventQueue.invokeLater(() -> new Login().setVisible(true));
    }
}