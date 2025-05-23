package com.umg.controlador;

import com.umg.implementacion.ReportesImp;
import com.umg.modelos.ModeloVistaReportes;
import com.umg.sql.Conector;
import com.umg.sql.Sql;
import org.apache.poi.hpsf.Date;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;


public class ControladorReportes implements ActionListener, MouseListener {
    public ControladorReportes(ModeloVistaReportes modelo) {

        this.modelo = modelo;
    }

    ModeloVistaReportes modelo;

ReportesImp reportes = new ReportesImp();
    public void generarReportesDiarios(){
        reportes.generarReporteDiarioGeneral();
    }
    public void generarReportesTardios(){
        reportes.generarExcelTardios();
    }

    public void generarReporteFecha() {
        try {
            String dpiTexto = modelo.getvReportes().txtDpi.getText().trim();

            // Validar que solo contiene dígitos
            if (!dpiTexto.matches("\\d{13}")) {
                throw new NumberFormatException("El DPI debe tener 13 dígitos numéricos.");
            }

            // No convertir a número, se pasa como String
            String dpi = dpiTexto;

            LocalDate inicio = LocalDate.parse(modelo.getvReportes().txtFechaDesde.getText());
            LocalDate fin = LocalDate.parse(modelo.getvReportes().txtFechaHasta.getText());

            // Validar que la fecha de inicio no sea posterior a la de fin
            if (inicio.isAfter(fin)) {
                JOptionPane.showMessageDialog(null,
                        "La fecha de inicio no puede ser posterior a la fecha de fin.",
                        "Rango de fechas inválido",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            reportes.generarExcelPorEmpleadoYRango(dpi, inicio, fin);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El DPI debe ser un número de 13 dígitos.", "Formato inválido", JOptionPane.ERROR_MESSAGE);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(null, "Formato de fecha inválido. Usa: yyyy-MM-dd", "Error de fecha", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocurrió un error inesperado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }





    @Override
    public void actionPerformed(ActionEvent e) {
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getComponent().equals(modelo.getvReportes().btnReporteDiario)) {
           generarReportesDiarios();

        }else if (e.getComponent().equals(modelo.getvReportes().btnSinMarca)) {
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

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
