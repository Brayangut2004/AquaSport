package com.aquasport.VentanasRegistros;

import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.awt.Color;
import java.awt.event.*;

import com.aquasport.PlantillasVentanas.RegistrosBase;
import com.aquasport.BaseDatos.ConexionBaseDatos;
import com.aquasport.Elementos.RoundJTextField;
import com.aquasport.Elementos.AlumnoItem;

public class RegistroPagos extends RegistrosBase {
    private JLabel alumnoLabel = new JLabel("Alumno Suscrito");
    private JLabel tipoPagoLabel = new JLabel("Tipo de Pago");
    private JLabel conceptoLabel = new JLabel("Concepto");
    private JLabel montoLabel = new JLabel("Monto a Pagar ($)");
    private JLabel metodoLabel = new JLabel("Método de Pago");

    private JComboBox<AlumnoItem> alumnoComboBox;
    private JComboBox<String> tipoPagoComboBox;
    private JComboBox<String> metodoPagoComboBox;

    private RoundJTextField conceptoField = new RoundJTextField();
    private RoundJTextField montoField = new RoundJTextField();

    private JButton pagarBoton = new JButton("Registrar Cobro");

    public RegistroPagos() {
        super("Caja - Pago de Membresía");
        initRegistroPagosContent();
    }

    private void initRegistroPagosContent() {
        SetupLabel(alumnoLabel);
        SetupLabel(tipoPagoLabel);
        SetupLabel(conceptoLabel);
        SetupLabel(montoLabel);
        SetupLabel(metodoLabel);

        alumnoComboBox = new JComboBox<>();
        alumnoComboBox.setBackground(Color.WHITE);
        cargarAlumnos();

        String[] tipos = {
            "Mensualidad", 
            "Inscripción", 
            "Reinscripción", 
            "Clase de Prueba"
        };
        tipoPagoComboBox = new JComboBox<>(tipos);
        tipoPagoComboBox.setBackground(Color.WHITE);
        tipoPagoComboBox.addActionListener(e -> actualizarConceptoAutomatico());

        conceptoField.setText("Mensualidad"); 
        actualizarConceptoAutomatico(); 

        String[] metodos = {
            "Efectivo", 
            "Tarjeta de Débito", 
            "Tarjeta de Crédito", 
            "Transferencia",
        };
        metodoPagoComboBox = new JComboBox<>(metodos);
        metodoPagoComboBox.setBackground(Color.WHITE);

        SetupBoton(pagarBoton);
        pagarBoton.setText("Procesar Pago");
        pagarBoton.addActionListener(e -> registrarPago());

        GroupLayout layout = new GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap(30, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(alumnoLabel)
                        .addComponent(alumnoComboBox, GroupLayout.PREFERRED_SIZE, 460, GroupLayout.PREFERRED_SIZE)
                        
                        .addGap(20)

                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(tipoPagoLabel)
                                .addComponent(tipoPagoComboBox, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
                            )
                            .addGap(20)
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(metodoLabel)
                                .addComponent(metodoPagoComboBox, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
                            )
                        )

                        .addGap(20)

                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(conceptoLabel)
                                .addComponent(conceptoField, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                            )
                            .addGap(20)
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(montoLabel)
                                .addComponent(montoField, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
                            )
                        )
                    )
                    .addContainerGap(30, Short.MAX_VALUE)
                )
                .addComponent(pagarBoton)
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                
                .addComponent(alumnoLabel)
                .addComponent(alumnoComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)

                .addGap(20)

                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(tipoPagoLabel)
                    .addComponent(metodoLabel)
                )
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(tipoPagoComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(metodoPagoComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                )

                .addGap(20)

                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(conceptoLabel)
                    .addComponent(montoLabel)
                )
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(conceptoField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(montoField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                )

                .addGap(30)

                .addComponent(pagarBoton)
                .addContainerGap(20, Short.MAX_VALUE)
        );

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                String concepto = conceptoField.getText().trim();
                String monto = montoField.getText().trim();

                boolean alumno = alumnoComboBox.getSelectedIndex() > 0;
                boolean tipoPago = tipoPagoComboBox.getSelectedIndex() > 0;
                boolean metodoPago = metodoPagoComboBox.getSelectedIndex() > 0;

                boolean camposVacios = concepto.isEmpty() && monto.isEmpty()
                                    && !alumno && !tipoPago && !metodoPago;

                if(camposVacios) {
                    dispose();
                    return;
                }

                int confirmacion = JOptionPane.showConfirmDialog(
                    RegistroPagos.this, 
                    "<html>Los cambios no guardados se perderan <br/> ¿Esta seguro que desea salir?</html>", 
                    "Advertencia de cierre", 
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
                );

                if(confirmacion == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        });
    }

    private void actualizarConceptoAutomatico() {
        String tipo = (String) tipoPagoComboBox.getSelectedItem();
        Locale locale = new Locale.Builder()
            .setLanguage("es")
            .setRegion("ES")
            .setVariant("POSIX")
            .build();
        
        if (tipo.equals("Mensualidad")) {
            String mes = LocalDate.now().getMonth()
                .getDisplayName(TextStyle.FULL, locale);

            mes = mes.substring(0, 1).toUpperCase() + mes.substring(1);
            
            conceptoField.setText(tipo + " - " + mes);
        } else {
            conceptoField.setText(tipo);
        }
    }

    private void cargarAlumnos() {
        alumnoComboBox.addItem(new AlumnoItem(-1, "Seleccione un alumno..."));
        
        String sql = "SELECT idAlumno, nombreCompleto FROM alumnos ORDER BY nombreCompleto ASC";

        try (Connection conn = ConexionBaseDatos.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                alumnoComboBox.addItem(new AlumnoItem(
                    rs.getInt("idAlumno"),
                    rs.getString("nombreCompleto")
                ));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error cargando alumnos: " + e.getMessage());
        }
    }

    private void registrarPago() {
        AlumnoItem alumno = (AlumnoItem) alumnoComboBox.getSelectedItem();
        if (alumno == null || alumno.getId() == -1) {
            JOptionPane.showMessageDialog(this, 
                "Debe seleccionar un alumno.", 
                "Error", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        String concepto = conceptoField.getText().trim();
        if (concepto.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "El concepto no puede estar vacío.", 
                "Error", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        double monto = 0;
        try {
            monto = Double.parseDouble(montoField.getText().trim());
            if (monto <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Ingrese un monto válido mayor a 0.", 
                "Error", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        String referencia = (String) metodoPagoComboBox.getSelectedItem();

        String sql = "INSERT INTO pagos (idAlumno, Concepto, monto, referencia) "
                    + "VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionBaseDatos.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, alumno.getId());
            pstmt.setString(2, concepto);
            pstmt.setDouble(3, monto);
            pstmt.setString(4, referencia);

            int filas = pstmt.executeUpdate();

            if (filas > 0) {
                JOptionPane.showMessageDialog(this, 
                    "Pago registrado correctamente.\n" +
                    "Alumno: " + alumno.toString() + "\n" +
                    "Monto: $" + monto, 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Error de Base de Datos:\n" + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarFormulario() {
        alumnoComboBox.setSelectedIndex(0);
        tipoPagoComboBox.setSelectedIndex(0);
        actualizarConceptoAutomatico();
        montoField.setText("");
        metodoPagoComboBox.setSelectedIndex(0);
    }
}