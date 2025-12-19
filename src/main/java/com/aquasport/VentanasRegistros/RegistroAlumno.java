package com.aquasport.VentanasRegistros;

import com.aquasport.Entidades.Alumno;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

import com.aquasport.Elementos.RoundJTextField;
import com.aquasport.PlantillasVentanas.RegistrosBase;
import com.aquasport.BaseDatos.ConexionBaseDatos; 

public class RegistroAlumno extends RegistrosBase {

    private JLabel nombreLabel = new JLabel("Nombre Completo");
    private JLabel alergiasLabel = new JLabel("Alergias (si no tiene, dejar vacío)");
    private JLabel nacimientoLabel = new JLabel("Fecha de Nacimiento (DD-MM-YYYY)");
    private JLabel telefonoLabel = new JLabel("Teléfono"); 
    private JLabel direccionLabel = new JLabel("Dirección");
    private JLabel telefonoEmergenciaLabel = new JLabel("Teléfono de emergencia");

    private RoundJTextField nombreField = new RoundJTextField();
    private RoundJTextField alergiasField = new RoundJTextField();
    private RoundJTextField telefonoField = new RoundJTextField();
    private RoundJTextField direccionField = new RoundJTextField();
    private RoundJTextField telefonoEmergenciaField = new RoundJTextField();

    private JDateChooser nacimientoChooser = new JDateChooser();

    private JButton registroBoton = new JButton("Registrar");

    private boolean modoEdicion = false;
    private int idAlumnoEditar = -1;

    public RegistroAlumno() {
        super("Registrar Alumno");
        initRegistroAlumnoContent();
        configurarModoRegistro();
    }

    public RegistroAlumno(Alumno alumno) {
        super("Editar Alumno"); 
        initRegistroAlumnoContent();
        configurarModoEdicion(alumno);
    }

    private void configurarModoRegistro() {
        modoEdicion = false;
        registroBoton.setText("Registrar Alumno");
    }

    private void configurarModoEdicion(Alumno alumno) {
        modoEdicion = true;
        idAlumnoEditar = alumno.getIdAlumno();
        registroBoton.setText("Guardar Cambios");
        nombreField.setText(alumno.getNombreCompleto());
        alergiasField.setText(alumno.getAlergias());
        telefonoField.setText(alumno.getTelefono());
        telefonoEmergenciaField.setText(alumno.getTelefonoEmergencia());
        direccionField.setText(alumno.getDireccion());
        nacimientoChooser.setDate(alumno.getFechaNacimiento());
    }

    public void initRegistroAlumnoContent() {
        SetupLabel(nombreLabel);
        SetupLabel(alergiasLabel);
        SetupLabel(nacimientoLabel);
        SetupLabel(telefonoLabel);
        SetupLabel(direccionLabel);
        SetupLabel(telefonoEmergenciaLabel);

        SetupBoton(registroBoton);
        registroBoton.addActionListener(evt -> botonRegistroActionPerfomed());

        GroupLayout layout = new GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap(10, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(nombreLabel)
                        .addComponent(nombreField, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
                        .addComponent(nacimientoLabel)
                        .addComponent(nacimientoChooser, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
                        .addComponent(telefonoLabel)
                        .addComponent(telefonoField, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
                    )

                    .addGap(40)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(alergiasLabel)
                        .addComponent(alergiasField, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
                        .addComponent(direccionLabel)
                        .addComponent(direccionField, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
                        .addComponent(telefonoEmergenciaLabel)
                        .addComponent(telefonoEmergenciaField, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
                    )
                    .addContainerGap(10, Short.MAX_VALUE)
            )
            .addComponent(registroBoton)
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(nombreLabel)
                    .addComponent(alergiasLabel)
                )
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(nombreField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(alergiasField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                )

                .addGap(25)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(nacimientoLabel)
                    .addComponent(direccionLabel)
                )
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(nacimientoChooser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(direccionField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                )

                .addGap(25)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(telefonoLabel)
                    .addComponent(telefonoEmergenciaLabel)
                )
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(telefonoField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE)
                    .addComponent(telefonoEmergenciaField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                )

                .addGap(35)
                .addComponent(registroBoton)
                .addContainerGap(10, Short.MAX_VALUE)
        );

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                confirmarSalida();
            }
        });
    }

    // Verifica si hay datos ingresados 
    // antes de salir de la ventana
    private void confirmarSalida() {
        String nombre = nombreField.getText().trim();
        String direccion = direccionField.getText().trim();
        String telefono = telefonoField.getText().trim();
        String telefonoEmergencia = telefonoEmergenciaField.getText().trim();

        if (nombre.isEmpty() && direccion.isEmpty() && telefono.isEmpty() &&
        telefonoEmergencia.isEmpty() && nacimientoChooser.getDate() == null ) {
            dispose();
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
            RegistroAlumno.this, 
            "<html>Los cambios no guardados se perderan <br/> ¿Esta seguro que desea salir?</html>", 
            "Advertencia de cierre", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if(confirmacion == JOptionPane.YES_OPTION) {
            dispose();
        }
    }

    private void botonRegistroActionPerfomed() {
        String nombre = nombreField.getText().trim();
        String alergias = alergiasField.getText().trim();
        String telefono = telefonoField.getText().trim();
        String telefonoEmergencia = telefonoEmergenciaField.getText().trim();
        String direccion = direccionField.getText().trim();

        if (nombre.isEmpty()) { 
            mostrarError("El campo nombre es obligatorio."); 
            return; 
        }

        if (!nombre.matches("^[a-zA-ZáéíóúüÁÉÍÓÚÜñÑ\\s]+$")) { 
            mostrarError("El nombre contiene caracteres no validos."); 
            return; 
        }

        if (nacimientoChooser.getDate() == null) {
            mostrarError("El campo Fecha de Nacimiento es obligatorio."); 
            return; 
        }

        if (telefono.isEmpty() || telefono.replaceAll("[^\\d]", "").length() < 10) { 
            mostrarError("El teléfono debe tener al menos 10 dígitos."); 
            return; 
        }

        if (telefonoEmergencia.isEmpty() || telefonoEmergencia.replaceAll("[^\\d]", "").length() < 10) { 
            mostrarError("El teléfono de emergencia debe tener al menos 10 dígitos."); 
            return; 
        }

        if (direccion.isEmpty()) { 
            mostrarError("El campo direccion es obligatorio."); 
            return; 
        }

        Date fechaNacimiento = new Date(nacimientoChooser.getDate().getTime());

        if (modoEdicion) {
            actualizarAlumno(nombre, alergias, fechaNacimiento, telefono, telefonoEmergencia, direccion);
        } else {
            registrarAlumno(nombre, alergias, fechaNacimiento, telefono, telefonoEmergencia, direccion);
        }
    }

    private void registrarAlumno(String nombre, String alergias, java.sql.Date fecha, String tel, String telEm, String dir) {
        String sqlCheck = "SELECT idAlumno FROM alumnos WHERE nombreCompleto = ? LIMIT 1";
        String sqlInsert = "INSERT INTO alumnos (nombreCompleto, alergias, fechaNacimiento, telefono, telefonoEmergencia, direccion) "
                        +  "VALUES (?, ?, ?, ?, ?, ?)";

        // Verifica si existen alumnos con el mismo nombre
        // si no realiza el registro con normalidad
        try (Connection conn = ConexionBaseDatos.getConnection()) {
            try (PreparedStatement pstCheck = conn.prepareStatement(sqlCheck)) {
                pstCheck.setString(1, nombre);
                if (pstCheck.executeQuery().next()) {
                    mostrarAlerta("Ya existe un alumno registrado con ese nombre.");
                    return;
                }
            }

            try (PreparedStatement pstmt = conn.prepareStatement(sqlInsert)) {
                pstmt.setString(1, nombre);
                pstmt.setString(2, alergias);
                pstmt.setDate(3, fecha);
                pstmt.setString(4, tel);
                pstmt.setString(5, telEm);
                pstmt.setString(6, dir);

                if (pstmt.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(this, "¡Alumno registrado exitosamente!");
                    limpiarCampos();
                }
            }
        } catch (SQLException e) {
            mostrarError("Error SQL: " + e.getMessage());
        }
    }

    // Funcion para editar alumnos
    private void actualizarAlumno(String nombre, String alergias, java.sql.Date fecha, String tel, String telEm, String dir) {
        String sqlUpdate = "UPDATE alumnos SET nombreCompleto=?, alergias=?, fechaNacimiento=?, telefono=?, telefonoEmergencia=?, direccion=? WHERE idAlumno=?";

        try (Connection conn = ConexionBaseDatos.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sqlUpdate)) {
            
            pstmt.setString(1, nombre);
            pstmt.setString(2, alergias);
            pstmt.setDate(3, fecha);
            pstmt.setString(4, tel);
            pstmt.setString(5, telEm);
            pstmt.setString(6, dir);
            pstmt.setInt(7, idAlumnoEditar);

            if (pstmt.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(this, "¡Datos del alumno actualizados!");
                dispose();
            } else {
                mostrarError("No se encontró el registro para actualizar.");
            }
        } catch (SQLException e) {
            mostrarError("Error SQL: " + e.getMessage());
        }
    }

    private void mostrarError(String msg) {
        JOptionPane.showMessageDialog(this, 
            msg, 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
    }
    
    private void mostrarAlerta(String msg) {
        JOptionPane.showMessageDialog(this, 
            msg, 
            "Advertencia", 
            JOptionPane.WARNING_MESSAGE);
    }

    private void limpiarCampos() {
        nombreField.setText("");
        alergiasField.setText("");
        nacimientoChooser.setDate(null);
        telefonoField.setText("");
        direccionField.setText("");
        telefonoEmergenciaField.setText("");
    }
}