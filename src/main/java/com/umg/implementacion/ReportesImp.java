package com.umg.implementacion;

import com.umg.sql.Conector;
import com.umg.sql.Sql;
import org.apache.poi.hpsf.Date;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class ReportesImp {

    public void generarReporteDiarioGeneral() {
        try {
            Conector conector = new Conector();
            PreparedStatement ps;
            Sql sql = new Sql();
            ResultSet rs;

            String carpetaDescargas = System.getProperty("user.home") + File.separator + "Downloads";
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String timestamp = formatter.format(new Date());
            String nombreArchivo = carpetaDescargas + File.separator + "ReporteAsistencia_" + timestamp + ".xlsx";
            FileOutputStream archivo = new FileOutputStream(nombreArchivo);

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Asistencia General");

            // Estilo para fecha
            CellStyle dateStyle = workbook.createCellStyle();
            CreationHelper createHelper = workbook.getCreationHelper();
            dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));

            // Título
            Font tituloFont = workbook.createFont();
            tituloFont.setBold(true);
            tituloFont.setFontHeightInPoints((short) 16);
            CellStyle tituloStyle = workbook.createCellStyle();
            tituloStyle.setFont(tituloFont);
            tituloStyle.setAlignment(HorizontalAlignment.CENTER);

            Row tituloRow = sheet.createRow(0);
            Cell tituloCell = tituloRow.createCell(0);
            tituloCell.setCellValue("Reporte General de Asistencia");
            tituloCell.setCellStyle(tituloStyle);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));

            // Encabezados
            Row headerRow = sheet.createRow(2);
            String[] headers = {"ID Empleado", "Nombre", "Fecha Asistencia", "Hora Entrada", "Hora Salida"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            // Obtener datos
            conector.conectar();
            ps = conector.preparar(sql.getReporteDiarioGeneral());
            rs = ps.executeQuery();

            int rowNum = 3;
            while (rs.next()) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(rs.getInt("id_empleado"));
                row.createCell(1).setCellValue(rs.getString("nombre"));

                Cell fechaCell = row.createCell(2);
                fechaCell.setCellValue(rs.getDate("fecha_asistencia"));
                fechaCell.setCellStyle(dateStyle);

                row.createCell(3).setCellValue(rs.getString("hora_entrada"));
                row.createCell(4).setCellValue(rs.getString("hora_salida"));
            }

            conector.desconectar();

            // Autoajuste de columnas
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(archivo);
            workbook.close();
            archivo.close();

            JOptionPane.showMessageDialog(null, "Guardado en: " + nombreArchivo, "Reporte generado exitosamente", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ocurrió un error al generar el Excel", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    public void generarExcelTardios() {
        try {
            Conector conector = new Conector();
            PreparedStatement ps;
            Sql sql = new Sql();
            ResultSet rs;

            String carpetaDescargas = System.getProperty("user.home") + File.separator + "Downloads";
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String timestamp = formatter.format(new Date());
            String nombreArchivo = carpetaDescargas + File.separator + "ReporteTardios_" + timestamp + ".xlsx";
            FileOutputStream archivo = new FileOutputStream(nombreArchivo);

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Tardíos o Sin Marca");

            // Estilo para fecha
            CellStyle dateStyle = workbook.createCellStyle();
            CreationHelper createHelper = workbook.getCreationHelper();
            dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));

            // Título
            Font tituloFont = workbook.createFont();
            tituloFont.setBold(true);
            tituloFont.setFontHeightInPoints((short) 16);
            CellStyle tituloStyle = workbook.createCellStyle();
            tituloStyle.setFont(tituloFont);
            tituloStyle.setAlignment(HorizontalAlignment.CENTER);

            Row tituloRow = sheet.createRow(0);
            Cell tituloCell = tituloRow.createCell(0);
            tituloCell.setCellValue("Reporte de Empleados Tardíos o Sin Marca");
            tituloCell.setCellStyle(tituloStyle);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));

            // Encabezados
            Row headerRow = sheet.createRow(2);
            String[] headers = {"ID Empleado", "Nombre", "Fecha Asistencia", "Hora Entrada", "Hora Salida"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            // Obtener datos
            conector.conectar();
            ps = conector.preparar(sql.getGetReporteAsistenciaTardios()); // <- Aquí usamos el nuevo query
            rs = ps.executeQuery();

            int rowNum = 3;
            while (rs.next()) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(rs.getInt("id_empleado"));
                row.createCell(1).setCellValue(rs.getString("nombre"));

                Cell fechaCell = row.createCell(2);
                fechaCell.setCellValue(rs.getDate("fecha_asistencia"));
                fechaCell.setCellStyle(dateStyle);

                row.createCell(3).setCellValue(rs.getString("hora_entrada"));
                row.createCell(4).setCellValue(rs.getString("hora_salida"));
            }

            conector.desconectar();

            // Autoajuste de columnas
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(archivo);
            workbook.close();
            archivo.close();

            JOptionPane.showMessageDialog(null, "Guardado en: " + nombreArchivo, "Reporte generado exitosamente", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ocurrió un error al generar el Excel", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void generarExcelPorEmpleadoYRango(int idEmpleado, LocalDate fechaInicio, LocalDate fechaFin) {
        try {
            Conector conector = new Conector();
            PreparedStatement ps;
            Sql sql = new Sql();
            ResultSet rs;

            String carpetaDescargas = System.getProperty("user.home") + File.separator + "Downloads";
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String timestamp = formatter.format(new Date());
            String nombreArchivo = carpetaDescargas + File.separator + "ReporteEmpleado_" + idEmpleado + "_" + timestamp + ".xlsx";
            FileOutputStream archivo = new FileOutputStream(nombreArchivo);

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Asistencia por Empleado");

            // Estilo para fecha
            CellStyle dateStyle = workbook.createCellStyle();
            CreationHelper createHelper = workbook.getCreationHelper();
            dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));

            // Título
            Font tituloFont = workbook.createFont();
            tituloFont.setBold(true);
            tituloFont.setFontHeightInPoints((short) 16);
            CellStyle tituloStyle = workbook.createCellStyle();
            tituloStyle.setFont(tituloFont);
            tituloStyle.setAlignment(HorizontalAlignment.CENTER);

            Row tituloRow = sheet.createRow(0);
            Cell tituloCell = tituloRow.createCell(0);
            tituloCell.setCellValue("Reporte de Asistencia del Empleado ID: " + idEmpleado);
            tituloCell.setCellStyle(tituloStyle);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));

            // Encabezados
            Row headerRow = sheet.createRow(2);
            String[] headers = {"ID Empleado", "Nombre", "Fecha Asistencia", "Hora Entrada", "Hora Salida"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            // Conectar y consultar
            conector.conectar();
            String query = "SELECT e.id_empleado, e.nombre, a.fecha_asistencia, a.hora_entrada, a.hora_salida " +
                    "FROM asistencia_diaria a " +
                    "JOIN empleado e ON e.id_empleado = a.empleado_id " +
                    "WHERE e.id_empleado = ? AND a.fecha_asistencia BETWEEN ? AND ? " +
                    "ORDER BY a.fecha_asistencia DESC;";
            ps = conector.preparar(query);
            ps.setInt(1, idEmpleado);
            ps.setDate(2, java.sql.Date.valueOf(fechaInicio));
            ps.setDate(3, java.sql.Date.valueOf(fechaFin));
            rs = ps.executeQuery();

            int rowNum = 3;
            while (rs.next()) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(rs.getInt("id_empleado"));
                row.createCell(1).setCellValue(rs.getString("nombre"));

                Cell fechaCell = row.createCell(2);
                fechaCell.setCellValue(rs.getDate("fecha_asistencia"));
                fechaCell.setCellStyle(dateStyle);

                row.createCell(3).setCellValue(rs.getString("hora_entrada"));
                row.createCell(4).setCellValue(rs.getString("hora_salida"));
            }

            conector.desconectar();

            // Ajustar columnas
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(archivo);
            workbook.close();
            archivo.close();

            JOptionPane.showMessageDialog(null, "Guardado en: " + nombreArchivo, "Reporte generado exitosamente", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ocurrió un error al generar el Excel", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


}
