package com.aquasport.VentanasRegistros;

import com.aquasport.Entidades.Horario;
import com.aquasport.BaseDatos.ConexionBaseDatos;
import com.aquasport.Elementos.RoundJTextField;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class RegistroHorarios extends JFrame {
    private JPanel panelPrincipal = new JPanel();
    private JLabel maestroLabel = new JLabel("Maestro titular");
    private JLabel nivelLabel = new JLabel("Nivel");
    private JLabel cuposDisponiblesLabel = new JLabel("Cupos (Max 8)");
    private JLabel horariosLabel = new JLabel("Horarios");
    private JLabel diasLabel = new JLabel("Días de Clase");

    private RoundJTextField maestroField = new RoundJTextField();
    private RoundJTextField cuposdisponiblesField = new RoundJTextField();

    private JComboBox<String> nivelComboBox;
    private JComboBox<String> horariosComboBox;
    private JComboBox<String> diasComboBox;

    private JButton accionBoton = new JButton("Acción");

    private boolean modoEdicion = false; 
    private int idHorarioEditar = -1;    

    public RegistroHorarios() {
        initComponents();
        configurarModoRegistro();
    }

    public RegistroHorarios(Horario datosHorario) {
        initComponents();
        configurarModoEdicion(datosHorario);
    }

    private void configurarModoRegistro() {
        this.modoEdicion = false;
        setTitle("Registrar Horario");
        accionBoton.setText("Registrar Horario");
        limpiarCampos();
    }

    private void configurarModoEdicion(Horario h) {
        this.modoEdicion = true;
        this.idHorarioEditar = h.getIdHorario();
        setTitle("Editar Horario");
        accionBoton.setText("Guardar Cambios");
        llenarDatos(h);
    }

    private void initComponents() {
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        
        Color colorFondo = new Color(2, 44, 105);
        panelPrincipal.setBackground(colorFondo);
        getContentPane().add(panelPrincipal);

        setupLabelStyle(maestroLabel);
        setupLabelStyle(nivelLabel);
        setupLabelStyle(cuposDisponiblesLabel);
        setupLabelStyle(horariosLabel);
        setupLabelStyle(diasLabel);

        String[] listaNivel = { 
            "Seleccione una opcion...", 
            "Basico", 
            "Intermedio", 
            "Avanzado" 
        };
        nivelComboBox = new JComboBox<>(listaNivel);
        nivelComboBox.setBackground(Color.white);

        String[] listaHorarios = { 
            "Seleccione una opcion...", 
            "10:00 - 11:00 AM", 
            "11:00 - 12:00 PM", 
            "12:00 - 01:00 PM", 
            "04:00 - 05:00 PM", 
            "05:00 - 06:00 PM", 
            "06:00 - 07:00 PM", 
            "07:00 - 08:00 PM" 
        };
        horariosComboBox = new JComboBox<>(listaHorarios);
        horariosComboBox.setBackground(Color.white);

        String[] diasLista = { 
            "Seleccione una opcion...", 
            "Lunes - Miercoles - Viernes", 
            "Martes - Jueves - Sabado" 
        };
        diasComboBox = new JComboBox<>(diasLista);
        diasComboBox.setBackground(Color.white);

        accionBoton.setFont(new Font("Arial", Font.BOLD, 14));
        accionBoton.setBackground(new Color(4, 1, 36));
        accionBoton.setForeground(Color.white);

        accionBoton.addActionListener(evt -> ejecutarAccion());

        GroupLayout layout = new GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.CENTER) 
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap(20, Short.MAX_VALUE) 
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(maestroLabel)
                        .addComponent(maestroField, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
                        .addComponent(cuposDisponiblesLabel)
                        .addComponent(cuposdisponiblesField, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
                        .addComponent(diasLabel)
                        .addComponent(diasComboBox, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE))
                    .addGap(40) 
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(nivelLabel)
                        .addComponent(nivelComboBox, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
                        .addComponent(horariosLabel)
                        .addComponent(horariosComboBox, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(20, Short.MAX_VALUE)) 
                .addComponent(accionBoton, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE) 
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE) 
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(maestroLabel)
                    .addComponent(nivelLabel))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED) 
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(maestroField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(nivelComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(25) 
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(cuposDisponiblesLabel)
                    .addComponent(horariosLabel))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(cuposdisponiblesField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(horariosComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(25)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(diasLabel))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(diasComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(35) 
                .addComponent(accionBoton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE) 
        );
    }

    private void setupLabelStyle(JLabel label) {
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(Color.WHITE);
    }

    private void ejecutarAccion() {
        if (!validarCampos()) 
            return;

        if (modoEdicion) {
            actualizarDatosEnBD();
        } else {
            registrarNuevoEnBD();
        }
    }

    private boolean validarCampos() {
        String nombreMaestro = maestroField.getText().trim();

        if(!nombreMaestro.matches("^[a-zA-ZáéíóúüÁÉÍÓÚÜñÑ\\s]+$")) {
            JOptionPane.showMessageDialog(this, 
                "El campo Maestro Titular tiene caracteres no validos", 
                "Caracteres invalidos", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (nivelComboBox.getSelectedIndex() == 0 || horariosComboBox.getSelectedIndex() == 0 || diasComboBox.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, 
                "Seleccione Nivel, Horario y Días válidos.", 
                "Error", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (maestroField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "El campo Maestro es obligatorio.", 
                "Error", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }

        try {
            int cupos = Integer.parseInt(cuposdisponiblesField.getText().trim());
            if (cupos < 0 || cupos > 8) {
                JOptionPane.showMessageDialog(this, 
                    "Los cupos deben ser entre 0 y 8.", 
                    "Error", 
                    JOptionPane.WARNING_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Cupos debe ser un número entero.", 
                "Error", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void registrarNuevoEnBD() {
        String sql = "INSERT INTO horarios (maestroTitular, nivel, cuposDisponibles, horarioClase, dias) "
                    + "VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexionBaseDatos.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            setParametrosSQL(pstmt);
            
            if (pstmt.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(this, "¡Horario registrado exitosamente!");
                limpiarCampos();
            }
        } catch (SQLException e) {
            manejarErrorSQL(e);
        }
    }

    private void actualizarDatosEnBD() {
        String sql = "UPDATE horarios SET maestroTitular=?, nivel=?, cuposDisponibles=?, horarioClase=?, dias=? WHERE idHorario=?";
        
        try (Connection conn = ConexionBaseDatos.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            setParametrosSQL(pstmt);
            pstmt.setInt(6, this.idHorarioEditar); // Parámetro extra para el WHERE
            
            if (pstmt.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(this, "¡Horario actualizado exitosamente!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "No se encontró el registro.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            manejarErrorSQL(e);
        }
    }

    private void setParametrosSQL(PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, maestroField.getText().trim());
        pstmt.setString(2, (String) nivelComboBox.getSelectedItem());
        pstmt.setInt(3, Integer.parseInt(cuposdisponiblesField.getText().trim()));
        pstmt.setString(4, (String) horariosComboBox.getSelectedItem());
        pstmt.setString(5, (String) diasComboBox.getSelectedItem());
    }

    private void manejarErrorSQL(SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, 
            "Error de base de datos: " + e.getMessage(), 
            "Error SQL", 
            JOptionPane.ERROR_MESSAGE);
    }

    private void llenarDatos(Horario h) {
        maestroField.setText(h.getMaestroTitular());
        cuposdisponiblesField.setText(String.valueOf(h.getCuposDisponibles()));
        nivelComboBox.setSelectedItem(h.getNivel());
        horariosComboBox.setSelectedItem(h.getHorarioClase());
        diasComboBox.setSelectedItem(h.getDias());
    }

    private void limpiarCampos() {
        maestroField.setText("");
        cuposdisponiblesField.setText("");
        nivelComboBox.setSelectedIndex(0);
        horariosComboBox.setSelectedIndex(0);
        diasComboBox.setSelectedIndex(0);
    }
}