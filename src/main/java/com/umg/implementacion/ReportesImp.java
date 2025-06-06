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
import java.sql.Time;
import java.text.DateFormat;
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
            String timestamp = formatter.format(new java.util.Date());
            String nombreArchivo = carpetaDescargas + File.separator + "ReporteAsistencia_" + timestamp + ".xlsx";
            FileOutputStream archivo = new FileOutputStream(nombreArchivo);

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Asistencia General");

            // Estilo para fecha
            CellStyle dateStyle = workbook.createCellStyle();
            CreationHelper createHelper = workbook.getCreationHelper();
            dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
            dateStyle.setBorderBottom(BorderStyle.THIN);
            dateStyle.setBorderTop(BorderStyle.THIN);
            dateStyle.setBorderLeft(BorderStyle.THIN);
            dateStyle.setBorderRight(BorderStyle.THIN);

            // Estilo para celdas normales
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);

            // Estilo para encabezados
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);

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
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

            // Encabezados
            Row headerRow = sheet.createRow(2);
            String[] headers = {"DPI Empleado", "Nombre", "Fecha Asistencia", "Hora Entrada", "Hora Salida", "Estatus"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // Obtener datos
            conector.conectar();
            ps = conector.preparar(sql.getReporteDiarioGeneral());
            rs = ps.executeQuery();

            DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            int rowNum = 3;
            while (rs.next()) {
                Row row = sheet.createRow(rowNum++);

                Cell cell0 = row.createCell(0);
                cell0.setCellValue(rs.getInt("dpi_empleado"));
                cell0.setCellStyle(cellStyle);

                Cell cell1 = row.createCell(1);
                cell1.setCellValue(rs.getString("nombre_completo"));
                cell1.setCellStyle(cellStyle);

                Cell fechaCell = row.createCell(2);
                java.sql.Date fecha = rs.getDate("fecha_asistencia");
                if (fecha != null) {
                    fechaCell.setCellValue(fecha);
                    fechaCell.setCellStyle(dateStyle);
                } else {
                    fechaCell.setCellValue("Sin fecha");
                    fechaCell.setCellStyle(cellStyle);
                }

                Time horaEntrada = rs.getTime("hora_entrada");
                Time horaSalida = rs.getTime("hora_salida");

                Cell cell3 = row.createCell(3);
                cell3.setCellValue(horaEntrada != null ? timeFormat.format(horaEntrada) : "");
                cell3.setCellStyle(cellStyle);

                Cell cell4 = row.createCell(4);
                cell4.setCellValue(horaSalida != null ? timeFormat.format(horaSalida) : "");
                cell4.setCellStyle(cellStyle);

                // Estatus
                String estatus;
                if (horaEntrada != null && horaSalida != null) {
                    estatus = "Asistencia";
                } else if (horaEntrada != null) {
                    estatus = "Salida no marcada";
                } else {
                    estatus = "Inasistencia";
                }

                Cell cell5 = row.createCell(5);
                cell5.setCellValue(estatus);
                cell5.setCellStyle(cellStyle);
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
            String timestamp = formatter.format(new java.util.Date());
            String nombreArchivo = carpetaDescargas + File.separator + "ReporteTardios_" + timestamp + ".xlsx";

            FileOutputStream archivo = new FileOutputStream(nombreArchivo);
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Tardíos o Sin Marca");

            // Estilo para fecha
            CellStyle dateStyle = workbook.createCellStyle();
            CreationHelper createHelper = workbook.getCreationHelper();
            dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
            dateStyle.setBorderBottom(BorderStyle.THIN);
            dateStyle.setBorderTop(BorderStyle.THIN);
            dateStyle.setBorderLeft(BorderStyle.THIN);
            dateStyle.setBorderRight(BorderStyle.THIN);

            // Estilo para celdas normales
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);

            // Estilo para encabezados
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 12);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);

            // Estilo para título
            Font tituloFont = workbook.createFont();
            tituloFont.setBold(true);
            tituloFont.setFontHeightInPoints((short) 16);
            CellStyle tituloStyle = workbook.createCellStyle();
            tituloStyle.setFont(tituloFont);
            tituloStyle.setAlignment(HorizontalAlignment.CENTER);

            // Título
            Row tituloRow = sheet.createRow(0);
            Cell tituloCell = tituloRow.createCell(0);
            tituloCell.setCellValue("Reporte de Empleados Tardíos o Sin Marca");
            tituloCell.setCellStyle(tituloStyle);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5)); // 6 columnas para incluir estatus

            // Encabezados
            Row headerRow = sheet.createRow(2);
            String[] headers = {"DPI Empleado", "Nombre", "Fecha Asistencia", "Hora Entrada", "Hora Salida", "Estatus"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            conector.conectar();
            ps = conector.preparar(sql.getGetReporteAsistenciaTardios());
            rs = ps.executeQuery();

            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            int rowNum = 3;

            while (rs.next()) {
                Row row = sheet.createRow(rowNum++);

                Cell cell0 = row.createCell(0);
                cell0.setCellValue(rs.getString("dpi_empleado"));
                cell0.setCellStyle(cellStyle);

                Cell cell1 = row.createCell(1);
                cell1.setCellValue(rs.getString("nombre_completo"));
                cell1.setCellStyle(cellStyle);

                Cell fechaCell = row.createCell(2);
                java.sql.Date fecha = rs.getDate("fecha_asistencia");
                if (fecha != null) {
                    fechaCell.setCellValue(fecha);
                    fechaCell.setCellStyle(dateStyle);
                } else {
                    fechaCell.setCellValue("Sin fecha");
                    fechaCell.setCellStyle(cellStyle);
                }

                Time horaEntrada = rs.getTime("hora_entrada");
                Time horaSalida = rs.getTime("hora_salida");

                Cell cell3 = row.createCell(3);
                cell3.setCellValue(horaEntrada != null ? timeFormat.format(horaEntrada) : "Sin registro");
                cell3.setCellStyle(cellStyle);

                Cell cell4 = row.createCell(4);
                cell4.setCellValue(horaSalida != null ? timeFormat.format(horaSalida) : "Sin registro");
                cell4.setCellStyle(cellStyle);

                // Estatus: Detectar si es tardío o sin marca de entrada
                String estatus;
                if (horaEntrada == null) {
                    estatus = "No marcó entrada";
                } else {
                    // Aquí puedes ajustar la hora límite para considerar tardío, ejemplo 08:00:00
                    java.sql.Time limite = java.sql.Time.valueOf("08:00:00");
                    if (horaEntrada.after(limite)) {
                        estatus = "Tardío";
                    } else {
                        estatus = "Dentro de tiempo";
                    }
                }

                Cell cell5 = row.createCell(5);
                cell5.setCellValue(estatus);
                cell5.setCellStyle(cellStyle);
            }

            conector.desconectar();

            // Autoajustar columnas
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(archivo);
            workbook.close();
            archivo.close();

            JOptionPane.showMessageDialog(null,
                    "Guardado en: " + nombreArchivo,
                    "Reporte generado exitosamente",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Ocurrió un error al generar el Excel",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }







    public void generarExcelPorEmpleadoYRango(String dpiEmpleado, LocalDate fechaInicio, LocalDate fechaFin) {
        try {
            Conector conector = new Conector();
            Sql sql = new Sql();
            conector.conectar();

            PreparedStatement ps = conector.preparar(sql.getReportePorRango());
            ps.setString(1, dpiEmpleado);
            ps.setDate(2, java.sql.Date.valueOf(fechaInicio));
            ps.setDate(3, java.sql.Date.valueOf(fechaFin));
            ResultSet rs = ps.executeQuery();

            String carpetaDescargas = System.getProperty("user.home") + File.separator + "Downloads";
            String timestamp = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
            String nombreArchivo = carpetaDescargas + File.separator + "ReporteEmpleado_" + timestamp + ".xlsx";

            FileOutputStream archivo = new FileOutputStream(nombreArchivo);
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Reporte por Empleado");

            CreationHelper createHelper = workbook.getCreationHelper();
            CellStyle dateStyle = workbook.createCellStyle();
            dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
            dateStyle.setBorderBottom(BorderStyle.THIN);
            dateStyle.setBorderTop(BorderStyle.THIN);
            dateStyle.setBorderLeft(BorderStyle.THIN);
            dateStyle.setBorderRight(BorderStyle.THIN);

            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);

            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 12);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);

            Font tituloFont = workbook.createFont();
            tituloFont.setBold(true);
            tituloFont.setFontHeightInPoints((short) 16);
            CellStyle tituloStyle = workbook.createCellStyle();
            tituloStyle.setFont(tituloFont);
            tituloStyle.setAlignment(HorizontalAlignment.CENTER);

            Row tituloRow = sheet.createRow(0);
            Cell tituloCell = tituloRow.createCell(0);
            tituloCell.setCellValue("Reporte de Asistencia por Empleado y Rango");
            tituloCell.setCellStyle(tituloStyle);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));

            Row headerRow = sheet.createRow(2);
            String[] headers = {"DPI Empleado", "Nombre", "Fecha Asistencia", "Hora Entrada", "Hora Salida", "Estatus"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            int rowNum = 3;
            while (rs.next()) {
                Row row = sheet.createRow(rowNum++);

                Cell cell0 = row.createCell(0);
                cell0.setCellValue(rs.getString("dpi_empleado"));
                cell0.setCellStyle(cellStyle);

                Cell cell1 = row.createCell(1);
                String nombreCompleto = rs.getString("nombre1_empleado") + " " + rs.getString("apellido1_empleado");
                cell1.setCellValue(nombreCompleto);
                cell1.setCellStyle(cellStyle);

                Cell fechaCell = row.createCell(2);
                java.sql.Date fecha = rs.getDate("fecha_asistencia");
                if (fecha != null) {
                    fechaCell.setCellValue(fecha);
                    fechaCell.setCellStyle(dateStyle);
                } else {
                    fechaCell.setCellValue("Sin fecha");
                    fechaCell.setCellStyle(cellStyle);
                }

                Time horaEntrada = rs.getTime("hora_entrada");
                Time horaSalida = rs.getTime("hora_salida");

                Cell cell3 = row.createCell(3);
                cell3.setCellValue(horaEntrada != null ? horaEntrada.toString() : "Sin registro");
                cell3.setCellStyle(cellStyle);

                Cell cell4 = row.createCell(4);
                cell4.setCellValue(horaSalida != null ? horaSalida.toString() : "Sin registro");
                cell4.setCellStyle(cellStyle);

                String estatus;
                if (horaEntrada == null) {
                    estatus = "No marcó entrada";
                } else {
                    Time limite = Time.valueOf("08:00:00");
                    estatus = horaEntrada.after(limite) ? "Tardío" : "Dentro de tiempo";
                }

                Cell cell5 = row.createCell(5);
                cell5.setCellValue(estatus);
                cell5.setCellStyle(cellStyle);
            }

            conector.desconectar();

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(archivo);
            workbook.close();
            archivo.close();

            JOptionPane.showMessageDialog(null,
                    "Guardado en: " + nombreArchivo,
                    "Reporte generado exitosamente",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Ocurrió un error al generar el Excel: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }


}
