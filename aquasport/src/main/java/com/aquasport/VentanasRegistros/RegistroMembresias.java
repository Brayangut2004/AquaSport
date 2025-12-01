package com.aquasport.VentanasRegistros;

import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Date;

import com.toedter.calendar.JDateChooser;

import com.aquasport.BaseDatos.ConexionBaseDatos;
import com.aquasport.PlantillasVentanas.RegistrosBase;
import com.aquasport.Elementos.*;
import com.aquasport.Entidades.Membresia;

public class RegistroMembresias extends RegistrosBase {
    private JLabel alumnoLabel = new JLabel("Alumno");
    private JLabel cantidadDiasLabel = new JLabel("Cantidad de Días");
    private JLabel detalleDiasLabel = new JLabel("Días de Asistencia");
    private JLabel profesorLabel = new JLabel("Profesor");
    private JLabel horarioLabel = new JLabel("Horario Disponible");
    private JLabel fechaInicioLabel = new JLabel("Fecha de Inicio");
    
    private JLabel montoLabel = new JLabel("Monto Base (Automático)");
    private JLabel cargoExtraLabel = new JLabel("Cargo Extra");
    private JLabel totalLabel = new JLabel("Total a Pagar");

    private JComboBox<AlumnoItem> alumnoComboBox;
    private JComboBox<String> cantidadDiasComboBox;
    private JComboBox<String> detalleDiasComboBox;
    private JComboBox<String> profesorComboBox;
    private JComboBox<String> horarioComboBox;
    private JComboBox<String> cargoExtraComboBox; 
    
    private JDateChooser fechaInicioChooser = new JDateChooser();
    private RoundJTextField montoField = new RoundJTextField();
    private RoundJTextField totalField = new RoundJTextField();

    private JButton registroBoton = new JButton("Registrar Membresía");

    private final double PRECIO_POR_CLASE = 60.00;

    private boolean modoEdicion = false;
    private int idMembresiasEditar = -1; 
    private boolean cargandoDatos = false; 

    public RegistroMembresias() {
        super("Nueva Membresía");
        initRegistroMembresiasContent();
        this.modoEdicion = false;
    }

    public RegistroMembresias(Membresia m) {
        super("Editar Membresía");
        initRegistroMembresiasContent();
        configurarModoEdicion(m);
    }

    private void configurarModoEdicion(Membresia m) {
        this.modoEdicion = true;
        this.idMembresiasEditar = m.getIdMembresias(); 
        this.registroBoton.setText("Guardar Cambios");
        llenarDatos(m);
    }

    private void initRegistroMembresiasContent() {
        SetupLabel(alumnoLabel);
        SetupLabel(cantidadDiasLabel);
        SetupLabel(detalleDiasLabel);
        SetupLabel(profesorLabel);
        SetupLabel(horarioLabel);
        SetupLabel(fechaInicioLabel);
        SetupLabel(montoLabel);
        SetupLabel(cargoExtraLabel);
        SetupLabel(totalLabel);

        alumnoComboBox = new JComboBox<>();
        alumnoComboBox.setBackground(Color.WHITE);
        cargarAlumnos();

        String[] listaCantidad = {
            "Seleccione cantidad...", 
            "1 Día por semana", 
            "2 Días por semana", 
            "3 Días por semana"
        };
        cantidadDiasComboBox = new JComboBox<>(listaCantidad);
        cantidadDiasComboBox.setBackground(Color.WHITE);
        cantidadDiasComboBox.addActionListener(e -> {
            if(!cargandoDatos) actualizarLogicaDiasYPrecio();
        });

        detalleDiasComboBox = new JComboBox<>();
        detalleDiasComboBox.setBackground(Color.WHITE);
        detalleDiasComboBox.addItem("Seleccione cantidad primero...");
        detalleDiasComboBox.setEnabled(false);

        profesorComboBox = new JComboBox<>();
        profesorComboBox.setBackground(Color.WHITE);
        cargarProfesores();
        profesorComboBox.addActionListener(evt -> {
            if(!cargandoDatos) actualizarHorariosPorProfesor();
        });

        horarioComboBox = new JComboBox<>();
        horarioComboBox.setBackground(Color.WHITE);
        horarioComboBox.addItem("Seleccione profesor primero...");
        horarioComboBox.setEnabled(false);

        fechaInicioChooser.setDate(new Date());

        montoField.setText("0.00");
        montoField.setEditable(false);
        montoField.setBackground(new Color(230, 230, 230));
        
        String[] cargoExtraLista = {
            "Ninguno ($0)", 
            "Gorro ($200)", 
            "Goggles ($250)", 
            "Tabla ($100)", 
            "Tapones ($50)" 
        };
        cargoExtraComboBox = new JComboBox<>(cargoExtraLista);
        cargoExtraComboBox.setBackground(Color.WHITE);
        cargoExtraComboBox.addActionListener(e -> {
            if(!cargandoDatos) calcularTotalFinal();
        });
        
        totalField.setEditable(false);
        totalField.setBackground(new Color(230, 230, 230));

        SetupBoton(registroBoton);
        registroBoton.addActionListener(evt -> botonRegistroActionPerfomed());

        GroupLayout layout = new GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap(20, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(alumnoLabel)
                        .addComponent(alumnoComboBox, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
                        .addComponent(cantidadDiasLabel)
                        .addComponent(cantidadDiasComboBox, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
                        .addComponent(montoLabel)
                        .addComponent(montoField, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
                        .addComponent(profesorLabel)
                        .addComponent(profesorComboBox, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
                        .addComponent(horarioLabel)
                        .addComponent(horarioComboBox, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
                    )
                    .addGap(40)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(fechaInicioLabel)
                        .addComponent(fechaInicioChooser, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
                        .addComponent(detalleDiasLabel)
                        .addComponent(detalleDiasComboBox, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
                        .addComponent(cargoExtraLabel)
                        .addComponent(cargoExtraComboBox, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
                        .addComponent(totalLabel)
                        .addComponent(totalField, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
                    )
                    .addContainerGap(20, Short.MAX_VALUE)
                )
                .addComponent(registroBoton)
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(alumnoLabel).addComponent(fechaInicioLabel))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(alumnoComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(fechaInicioChooser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(25)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(cantidadDiasLabel).addComponent(detalleDiasLabel))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(cantidadDiasComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(detalleDiasComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(25)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(montoLabel).addComponent(cargoExtraLabel))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(montoField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(cargoExtraComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(25)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(profesorLabel).addComponent(totalLabel))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(profesorComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(25)
                .addComponent(horarioLabel)
                .addComponent(horarioComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(35)
                .addComponent(registroBoton)
                .addContainerGap(20, Short.MAX_VALUE)
        );

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                confirmarCierre();
            }
        });
    }

    private void confirmarCierre() {
        if (!modoEdicion && alumnoComboBox.getSelectedIndex() == 0) {
            dispose();
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "<html>Los cambios no guardados se perderán <br/> ¿Está seguro que desea salir?</html>", 
            "Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if(confirmacion == JOptionPane.YES_OPTION) 
            dispose();
    }

    private void actualizarLogicaDiasYPrecio() {
        int indice = cantidadDiasComboBox.getSelectedIndex(); 
        detalleDiasComboBox.removeAllItems();
        
        if (indice <= 0) {
            detalleDiasComboBox.addItem("Seleccione cantidad primero...");
            detalleDiasComboBox.setEnabled(false);
            montoField.setText("0.00");
            totalField.setText("0.00");
            return;
        }

        detalleDiasComboBox.setEnabled(true);
        int diasPorSemana = indice; 
        double costoMensual = diasPorSemana * 4 * PRECIO_POR_CLASE;
        montoField.setText(String.format("%.2f", costoMensual));

        if (diasPorSemana == 1) {
            String[] dias = {
                "Lunes", 
                "Martes", 
                "Miercoles", 
                "Jueves", 
                "Viernes", 
                "Sabado"};
            for(String d : dias) detalleDiasComboBox.addItem(d);
        } else if (diasPorSemana == 2) {
            detalleDiasComboBox.addItem("Lunes y Miércoles");
            detalleDiasComboBox.addItem("Martes y Jueves");
            detalleDiasComboBox.addItem("Miércoles y Viernes");
            detalleDiasComboBox.addItem("Jueves y Sábado");
        } else if (diasPorSemana == 3) {
            detalleDiasComboBox.addItem("Lunes, Miércoles y Viernes");
            detalleDiasComboBox.addItem("Martes, Jueves y Sábado");
        }
        calcularTotalFinal();
    }

    private void cargarAlumnos() {
        alumnoComboBox.addItem(new AlumnoItem(-1, "Seleccione un alumno..."));
        String sql = "SELECT idAlumno, nombreCompleto FROM alumnos ORDER BY nombreCompleto ASC";
        try (Connection conn = ConexionBaseDatos.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                alumnoComboBox.addItem(new AlumnoItem(rs.getInt("idAlumno"), rs.getString("nombreCompleto")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void cargarProfesores() {
        profesorComboBox.addItem("Seleccione un profesor...");
        String sql = "SELECT DISTINCT maestroTitular FROM horarios ORDER BY maestroTitular ASC";
        try (Connection conn = ConexionBaseDatos.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) { 
                profesorComboBox.addItem(rs.getString("maestroTitular")); 
            }
        } catch (SQLException e) { JOptionPane.showMessageDialog(this, "Error: " + e.getMessage()); }
    }

    private void actualizarHorariosPorProfesor() {
        if (profesorComboBox.getSelectedIndex() <= 0) {
            horarioComboBox.removeAllItems();
            horarioComboBox.addItem("Seleccione profesor primero...");
            horarioComboBox.setEnabled(false);
            return;
        }
        String profesor = (String) profesorComboBox.getSelectedItem();
        horarioComboBox.removeAllItems();
        horarioComboBox.addItem("Seleccione un horario...");
        horarioComboBox.setEnabled(true);
        String sql = "SELECT horarioClase FROM horarios WHERE maestroTitular = ? ORDER BY horarioClase ASC";
        try (Connection conn = ConexionBaseDatos.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, profesor);
            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) { 
                    horarioComboBox.addItem(rs.getString("horarioClase")); 
                }
            }
        } catch (SQLException e) { JOptionPane.showMessageDialog(this, "Error: " + e.getMessage()); }
    }

    private double obtenerPrecioExtra() {
        String seleccion = (String) cargoExtraComboBox.getSelectedItem();
        if (seleccion == null || seleccion.contains("Ninguno")) return 0.0;
        try {
            int inicio = seleccion.lastIndexOf("$") + 1;
            int fin = seleccion.lastIndexOf(")");
            if (inicio > 0 && fin > inicio) {
                return Double.parseDouble(seleccion.substring(inicio, fin));
            }
        } catch (Exception e) { return 0.0; }
        return 0.0;
    }

    private void calcularTotalFinal() {
        try {
            double monto = montoField.getText().isEmpty() ? 0 : Double.parseDouble(montoField.getText().replace(",", "."));
            double extra = obtenerPrecioExtra(); 
            totalField.setText(String.format("%.2f", monto + extra));
        } catch (NumberFormatException ex) { }
    }

    private boolean validarHorarioProfesor(String profesor, String diasSeleccionados) {
        String sql = "SELECT dias FROM horarios WHERE maestroTitular = ?";
        try (Connection conn = ConexionBaseDatos.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, profesor);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    if (esHorarioCompatible(rs.getString("dias"), diasSeleccionados)) return true;
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    private boolean esHorarioCompatible(String diasProfesor, String diasAlumno) {
        if (diasProfesor == null || diasAlumno == null) return false;
        diasProfesor = diasProfesor.toLowerCase();
        diasAlumno = diasAlumno.toLowerCase();
        String[] arregloAlumno = diasAlumno.split("[-y,]");
        for (String diaA : arregloAlumno) {
            String diaLimpio = diaA.trim();
            if (diaLimpio.isEmpty()) continue;
            if (!diasProfesor.contains(diaLimpio)) return false; 
        }
        return true;
    }

    private void llenarDatos(Membresia m) {
        this.cargandoDatos = true; 

        for (int i = 0; i < alumnoComboBox.getItemCount(); i++) {
            AlumnoItem item = alumnoComboBox.getItemAt(i);
            if (item.getId() == m.getIdAlumno()) {
                alumnoComboBox.setSelectedIndex(i);
                break;
            }
        }
        
        cantidadDiasComboBox.setSelectedIndex(m.getNumDias());
        actualizarLogicaDiasYPrecio(); 

        detalleDiasComboBox.setSelectedItem(m.getDiasTexto());

        profesorComboBox.setSelectedItem(m.getProfesor());
        actualizarHorariosPorProfesor();

        horarioComboBox.setSelectedItem(m.getHorario());

        for (int i = 0; i < cargoExtraComboBox.getItemCount(); i++) {
            String item = cargoExtraComboBox.getItemAt(i);
            if (item.contains(String.valueOf((int)m.getCargoExtra()))) {
                cargoExtraComboBox.setSelectedIndex(i);
                break;
            }
        }

        montoField.setText(String.format("%.2f", m.getMonto()));
        totalField.setText(String.format("%.2f", m.getTotal()));
        
        fechaInicioChooser.setDate(m.getFechaInicio());

        this.cargandoDatos = false; 
    }

    private void botonRegistroActionPerfomed() {
        if (!validarCampos()) return;
        
        if (modoEdicion) {
            actualizarMembresia();
        } else {
            registrarNuevaMembresia();
        }
    }

    private boolean validarCampos() {
        AlumnoItem alumno = (AlumnoItem) alumnoComboBox.getSelectedItem();
        if (alumno == null || alumno.getId() == -1) {
            mostrarError("Seleccione un alumno."); 
            return false;
        }
        if (cantidadDiasComboBox.getSelectedIndex() == 0) { 
            mostrarError("Seleccione la cantidad de días."); 
            return false;
        }
        if (profesorComboBox.getSelectedIndex() <= 0) { 
            mostrarError("Seleccione un profesor."); 
            return false;
        }
        if (horarioComboBox.getSelectedIndex() <= 0) { 
            mostrarError("Seleccione un horario."); 
            return false;
        }
        if (fechaInicioChooser.getDate() == null) {
            mostrarError("Seleccione una fecha de inicio."); 
            return false;
        }
        
        String diasTexto = (String) detalleDiasComboBox.getSelectedItem();
        String profesor = (String) profesorComboBox.getSelectedItem();

        if (!validarHorarioProfesor(profesor, diasTexto)) {
            JOptionPane.showMessageDialog(this, 
                "El profesor " + profesor + " no tiene disponibles los días: " + diasTexto, 
                "Conflicto de Horario", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void registrarNuevaMembresia() {
        String sql = "INSERT INTO membresias (idAlumno, numDias, dias, profesor, horario, monto, cargoExtra, total, fechaInicio, fechaPago, estado) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        ejecutarSQL(sql, false);
    }

    private void actualizarMembresia() {
        String sql = "UPDATE membresias SET idAlumno=?, numDias=?, dias=?, profesor=?, horario=?, monto=?, cargoExtra=?, total=?, fechaInicio=?, fechaPago=?, estado=? WHERE idMembresias=?";
        ejecutarSQL(sql, true);
    }

    private void ejecutarSQL(String sql, boolean esUpdate) {
        AlumnoItem alumno = (AlumnoItem) alumnoComboBox.getSelectedItem();
        int numDias = cantidadDiasComboBox.getSelectedIndex();
        String diasTexto = (String) detalleDiasComboBox.getSelectedItem();
        String profesor = (String) profesorComboBox.getSelectedItem();
        String horario = (String) horarioComboBox.getSelectedItem();
        double monto = Double.parseDouble(montoField.getText().replace(",", "."));
        double cargoExtra = obtenerPrecioExtra();
        double total = Double.parseDouble(totalField.getText().replace(",", "."));
        java.sql.Date fechaInicio = new java.sql.Date(fechaInicioChooser.getDate().getTime());
        java.sql.Date fechaPago = new java.sql.Date(System.currentTimeMillis());

        try (Connection conn = ConexionBaseDatos.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, alumno.getId());
            pstmt.setInt(2, numDias);
            pstmt.setString(3, diasTexto);
            pstmt.setString(4, profesor);
            pstmt.setString(5, horario);
            pstmt.setDouble(6, monto);
            pstmt.setDouble(7, cargoExtra);
            pstmt.setDouble(8, total);
            pstmt.setDate(9, fechaInicio);
            pstmt.setDate(10, fechaPago);
            pstmt.setBoolean(11, true);

            if (esUpdate) {
                pstmt.setInt(12, this.idMembresiasEditar); 
            }

            if (pstmt.executeUpdate() > 0) {
                String msg = esUpdate ? "¡Membresía actualizada!" : "¡Membresía registrada exitosamente!";
                JOptionPane.showMessageDialog(this, 
                    msg, 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                if (esUpdate) dispose(); else limpiarCampos();
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
    
    private void limpiarCampos() {
        alumnoComboBox.setSelectedIndex(0);
        cantidadDiasComboBox.setSelectedIndex(0); 
        profesorComboBox.setSelectedIndex(0);
        horarioComboBox.removeAllItems();
        horarioComboBox.addItem("Seleccione profesor primero...");
        horarioComboBox.setEnabled(false);
        cargoExtraComboBox.setSelectedIndex(0); 
        fechaInicioChooser.setDate(new Date());
    }
}