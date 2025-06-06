package com.umg.controlador;

import com.umg.implementacion.ReportesImp;
import com.umg.implementacion.ReportesImpPDF; // <--- Agregado para PDF
import com.umg.modelos.ModeloVistaReportes;
import com.umg.sql.Conector;
import com.umg.sql.Sql;
import org.apache.poi.hpsf.Date;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.Color;
import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ControladorReportes implements ActionListener, MouseListener, DocumentListener, KeyListener {
    public ControladorReportes(ModeloVistaReportes modelo) {
        this.modelo = modelo;
    }

    ModeloVistaReportes modelo;

    ReportesImp reportes = new ReportesImp();
    ReportesImpPDF reportesPDF = new ReportesImpPDF(); // <--- Instancia para PDF

    public void generarReportesDiarios() {
        reportes.generarReporteDiarioGeneral();
        reportesPDF.generarReporteDiarioGeneralPDF(); // <--- Genera también PDF
    }

    public void generarReportesTardios() {
        reportes.generarExcelTardios();
        reportesPDF.generarReporteTardiosPDF(); // <--- Genera también PDF
    }

    public void generarReporteFecha() {
        try {
            String dpiTexto = modelo.getvReportes().txtDpi.getText().trim();
            String fechaInicioTexto = modelo.getvReportes().txtFechaDesde.getText().trim();
            String fechaFinTexto = modelo.getvReportes().txtFechaHasta.getText().trim();

            // Validar que el DPI contiene solo 13 dígitos
            if (!dpiTexto.matches("\\d{13}")) {
                throw new NumberFormatException("El DPI debe tener 13 dígitos numéricos.");
            }

            String dpi = dpiTexto;

            // Formateador para dd/MM/yyyy
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            // Parsear fechas con el nuevo formato
            LocalDate inicio = LocalDate.parse(fechaInicioTexto, formatter);
            LocalDate fin = LocalDate.parse(fechaFinTexto, formatter);

            if (inicio.isAfter(fin)) {
                JOptionPane.showMessageDialog(null,
                        "La fecha de inicio no puede ser posterior a la fecha de fin.",
                        "Rango de fechas inválido",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            reportes.generarExcelPorEmpleadoYRango(dpi, inicio, fin);
            reportesPDF.generarReportePorEmpleadoYRangoPDF(dpi, inicio, fin);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El DPI debe ser un número de 13 dígitos.", "Formato inválido", JOptionPane.ERROR_MESSAGE);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(null, "Formato de fecha inválido. Usa: dd/MM/yyyy", "Error de fecha", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocurrió un error inesperado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }


    private void fechas(DocumentEvent e) {
        try {
            SwingUtilities.invokeLater(() -> {
                // Fecha Desde
                String fechaDesde = modelo.getvReportes().txtFechaDesde.getText();
                if (fechaDesde.length() > 10) {
                    fechaDesde = fechaDesde.substring(0, 10);
                    modelo.getvReportes().txtFechaDesde.setText(fechaDesde);
                }
                if (fechaDesde.length() == 2 && !fechaDesde.endsWith("/")) {
                    modelo.getvReportes().txtFechaDesde.setText(fechaDesde + "/");
                }
                if (fechaDesde.length() == 5 && fechaDesde.charAt(4) != '/') {
                    modelo.getvReportes().txtFechaDesde.setText(fechaDesde + "/");
                }

                // Fecha Hasta
                String fechaHasta = modelo.getvReportes().txtFechaHasta.getText();
                if (fechaHasta.length() > 10) {
                    fechaHasta = fechaHasta.substring(0, 10);
                    modelo.getvReportes().txtFechaHasta.setText(fechaHasta);
                }
                if (fechaHasta.length() == 2 && !fechaHasta.endsWith("/")) {
                    modelo.getvReportes().txtFechaHasta.setText(fechaHasta + "/");
                }
                if (fechaHasta.length() == 5 && fechaHasta.charAt(4) != '/') {
                    modelo.getvReportes().txtFechaHasta.setText(fechaHasta + "/");
                }
            });
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error fecha: " + ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }



    @Override
    public void insertUpdate(DocumentEvent e) {

        fechas(e);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {

    }

    @Override
    public void changedUpdate(DocumentEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // (Vacío, como tu original)
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getComponent().equals(modelo.getvReportes().btnReporteDiario)) {
            generarReportesDiarios();
        } else if (e.getComponent().equals(modelo.getvReportes().btnSinMarca)) {
            generarReportesTardios();
        } else if (e.getComponent().equals(modelo.getvReportes().btnReporteEmpleado)) {
            generarReporteFecha();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getComponent().equals(modelo.getvReportes().btnReporteDiario)) {
            modelo.getvReportes().btnReporteDiario.setBackground(new Color(38, 163, 106));
        } else if (e.getComponent().equals(modelo.getvReportes().btnReporteEmpleado)) {
            modelo.getvReportes().btnReporteEmpleado.setBackground(new Color(38, 163, 106));
        } else if (e.getComponent().equals(modelo.getvReportes().btnSinMarca)) {
            modelo.getvReportes().btnSinMarca.setBackground(new Color(38, 163, 106));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getComponent().equals(modelo.getvReportes().btnReporteDiario)) {
            modelo.getvReportes().btnReporteDiario.setBackground(new Color(0, 127, 75));
        } else if (e.getComponent().equals(modelo.getvReportes().btnReporteEmpleado)) {
            modelo.getvReportes().btnReporteEmpleado.setBackground(new Color(0, 127, 75));
        } else if (e.getComponent().equals(modelo.getvReportes().btnSinMarca)) {
            modelo.getvReportes().btnSinMarca.setBackground(new Color(0, 127, 75));
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // DPI: solo números
        if (e.getComponent().equals(modelo.getvReportes().txtDpi)) {
            char key = e.getKeyChar();
            if (!Character.isDigit(key)) {
                e.consume();
            }
        }

        // Fechas: solo números y '/'
        else if (e.getComponent().equals(modelo.getvReportes().txtFechaDesde) ||
                e.getComponent().equals(modelo.getvReportes().txtFechaHasta)) {

            char key = e.getKeyChar();
            boolean esNumero = Character.isDigit(key);
            boolean esSlash = key == '/';

            if (!esNumero && !esSlash) {
                e.consume();
            }
        }
    }


    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
