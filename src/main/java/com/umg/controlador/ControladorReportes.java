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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class ControladorReportes implements ActionListener, MouseListener {
    public ControladorReportes(ModeloVistaReportes modelo) {
    }

    ModeloVistaReportes modelo;

ReportesImp reportes = new ReportesImp();
    public void generarReportesDiarios(){
        reportes.generarReporteDiarioGeneral();
    }
    public void generarReportesTardios(){
        reportes.generarExcelTardios();
    }

    public void generarReporteFecha(){
        int id = Integer.parseInt(modelo.getvReportes().txtDpi.getText());
        LocalDate inicio = LocalDate.parse(modelo.getvReportes().txtFechaDesde.getText()); // Formato: yyyy-MM-dd
        LocalDate fin = LocalDate.parse(modelo.getvReportes().txtFechaHasta.getText());

        reportes.generarExcelPorEmpleadoYRango(id, inicio, fin);

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
