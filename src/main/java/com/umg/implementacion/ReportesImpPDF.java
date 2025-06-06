package com.umg.implementacion;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.umg.sql.Conector;
import com.umg.sql.Sql;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class ReportesImpPDF {

    // Método para encabezado profesional
    private void agregarEncabezado(Document document, String tituloReporte) throws Exception {
        try {
            Image logo = Image.getInstance("/com/umg/imagenes/logo.jpg");
            logo.scaleAbsolute(60, 60);
            logo.setAlignment(Image.ALIGN_LEFT);
            document.add(logo);
        } catch (Exception e) {
            // Si no hay logo, sigue sin error
        }
        Font fontEmpresa = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, new BaseColor(40, 70, 130));
        Paragraph empresa = new Paragraph("ASSENTA", fontEmpresa);
        empresa.setAlignment(Element.ALIGN_LEFT);
        document.add(empresa);

        Font fontTitulo = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
        Paragraph titulo = new Paragraph(tituloReporte, fontTitulo);
        titulo.setAlignment(Element.ALIGN_CENTER);
        titulo.setSpacingAfter(10);
        document.add(titulo);

        Font fontFecha = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.DARK_GRAY);
        Paragraph fechaGen = new Paragraph("Fecha de generación: " +
                new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date()), fontFecha);
        fechaGen.setAlignment(Element.ALIGN_RIGHT);
        fechaGen.setSpacingAfter(10);
        document.add(fechaGen);

        LineSeparator ls = new LineSeparator();
        ls.setOffset(-5);
        document.add(new Chunk(ls));
    }

    // Método para pie profesional
    private void agregarPie(Document document) throws Exception {
        LineSeparator ls = new LineSeparator();
        document.add(new Chunk(ls));
        Font fontPie = new Font(Font.FontFamily.HELVETICA, 9, Font.ITALIC, BaseColor.DARK_GRAY);
        Paragraph pie = new Paragraph("Reporte confidencial. Generado automáticamente por el sistema.", fontPie);
        pie.setAlignment(Element.ALIGN_CENTER);
        document.add(pie);
    }

    // ---------- REPORTE GENERAL ----------
    public void generarReporteDiarioGeneralPDF() {
        try {
            Conector conector = new Conector();
            Sql sql = new Sql();
            conector.conectar();

            PreparedStatement ps = conector.preparar(sql.getReporteDiarioGeneral());
            ResultSet rs = ps.executeQuery();

            String carpetaDescargas = System.getProperty("user.home") + File.separator + "Downloads";
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
            String nombreArchivo = carpetaDescargas + File.separator + "ReporteAsistencia_" + timestamp + ".pdf";

            Document document = new Document(PageSize.A4.rotate(), 40, 40, 70, 40);
            PdfWriter.getInstance(document, new FileOutputStream(nombreArchivo));
            document.open();

            agregarEncabezado(document, "Reporte General de Asistencia");

            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);
            table.setSpacingBefore(15f);
            table.setSpacingAfter(10f);
            table.setWidths(new float[]{1.2f, 3.8f, 2.2f, 2.2f, 2.2f, 2.5f});

            Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);
            BaseColor azul = new BaseColor(40, 70, 130);
            String[] headers = {"DPI Empleado", "Nombre", "Fecha Asistencia", "Hora Entrada", "Hora Salida", "Estatus"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
                cell.setBackgroundColor(azul);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPadding(8);
                table.addCell(cell);
            }

            SimpleDateFormat fechaFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat horaFormat = new SimpleDateFormat("HH:mm:ss");
            Font cellFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);

            while (rs.next()) {
                table.addCell(new PdfPCell(new Phrase(rs.getString("dpi_empleado"), cellFont)));
                table.addCell(new PdfPCell(new Phrase(rs.getString("nombre_completo"), cellFont)));

                java.sql.Date fecha = rs.getDate("fecha_asistencia");
                table.addCell(new PdfPCell(new Phrase(fecha != null ? fechaFormat.format(fecha) : "Sin fecha", cellFont)));

                Time horaEntrada = rs.getTime("hora_entrada");
                table.addCell(new PdfPCell(new Phrase(horaEntrada != null ? horaFormat.format(horaEntrada) : "", cellFont)));
                Time horaSalida = rs.getTime("hora_salida");
                table.addCell(new PdfPCell(new Phrase(horaSalida != null ? horaFormat.format(horaSalida) : "", cellFont)));

                String estatus;
                if (horaEntrada != null && horaSalida != null) {
                    estatus = "Asistencia";
                } else if (horaEntrada != null) {
                    estatus = "Salida no marcada";
                } else {
                    estatus = "Inasistencia";
                }
                table.addCell(new PdfPCell(new Phrase(estatus, cellFont)));
            }
            document.add(table);

            agregarPie(document);

            document.close();
            conector.desconectar();

            JOptionPane.showMessageDialog(null, "Guardado en: " + nombreArchivo, "Reporte PDF generado exitosamente", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ocurrió un error al generar el PDF: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ---------- REPORTE TARDÍOS ----------
    public void generarReporteTardiosPDF() {
        try {
            Conector conector = new Conector();
            Sql sql = new Sql();
            conector.conectar();

            PreparedStatement ps = conector.preparar(sql.getGetReporteAsistenciaTardios());
            ResultSet rs = ps.executeQuery();

            String carpetaDescargas = System.getProperty("user.home") + File.separator + "Downloads";
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
            String nombreArchivo = carpetaDescargas + File.separator + "ReporteTardios_" + timestamp + ".pdf";

            Document document = new Document(PageSize.A4.rotate(), 40, 40, 70, 40);
            PdfWriter.getInstance(document, new FileOutputStream(nombreArchivo));
            document.open();

            agregarEncabezado(document, "Reporte de Empleados Tardíos o Sin Marca");

            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);
            table.setSpacingBefore(15f);
            table.setSpacingAfter(10f);
            table.setWidths(new float[]{1.2f, 3.8f, 2.2f, 2.2f, 2.2f, 2.5f});

            Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);
            BaseColor azul = new BaseColor(40, 70, 130);
            String[] headers = {"DPI Empleado", "Nombre", "Fecha Asistencia", "Hora Entrada", "Hora Salida", "Estatus"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
                cell.setBackgroundColor(azul);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPadding(8);
                table.addCell(cell);
            }

            SimpleDateFormat fechaFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat horaFormat = new SimpleDateFormat("HH:mm:ss");
            Font cellFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);

            while (rs.next()) {
                table.addCell(new PdfPCell(new Phrase(rs.getString("dpi_empleado"), cellFont)));
                table.addCell(new PdfPCell(new Phrase(rs.getString("nombre_completo"), cellFont)));

                java.sql.Date fecha = rs.getDate("fecha_asistencia");
                table.addCell(new PdfPCell(new Phrase(fecha != null ? fechaFormat.format(fecha) : "Sin fecha", cellFont)));

                Time horaEntrada = rs.getTime("hora_entrada");
                Time horaSalida = rs.getTime("hora_salida");

                table.addCell(new PdfPCell(new Phrase(horaEntrada != null ? horaFormat.format(horaEntrada) : "Sin registro", cellFont)));
                table.addCell(new PdfPCell(new Phrase(horaSalida != null ? horaFormat.format(horaSalida) : "Sin registro", cellFont)));

                String estatus;
                if (horaEntrada == null) {
                    estatus = "No marcó entrada";
                } else {
                    Time limite = Time.valueOf("08:00:00");
                    if (horaEntrada.after(limite)) {
                        estatus = "Tardío";
                    } else {
                        estatus = "Dentro de tiempo";
                    }
                }
                table.addCell(new PdfPCell(new Phrase(estatus, cellFont)));
            }
            document.add(table);

            agregarPie(document);

            document.close();
            conector.desconectar();

            JOptionPane.showMessageDialog(null, "Guardado en: " + nombreArchivo, "Reporte PDF generado exitosamente", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ocurrió un error al generar el PDF: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ---------- REPORTE POR RANGO ----------
    public void generarReportePorEmpleadoYRangoPDF(String dpiEmpleado, LocalDate fechaInicio, LocalDate fechaFin) {
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
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
            String nombreArchivo = carpetaDescargas + File.separator + "ReporteEmpleado_" + timestamp + ".pdf";

            Document document = new Document(PageSize.A4.rotate(), 40, 40, 70, 40);
            PdfWriter.getInstance(document, new FileOutputStream(nombreArchivo));
            document.open();

            agregarEncabezado(document, "Reporte de Asistencia por Empleado y Rango");

            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);
            table.setSpacingBefore(15f);
            table.setSpacingAfter(10f);
            table.setWidths(new float[]{1.2f, 3.8f, 2.2f, 2.2f, 2.2f, 2.5f});

            Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);
            BaseColor azul = new BaseColor(40, 70, 130);
            String[] headers = {"ID Empleado", "Nombre", "Fecha Asistencia", "Hora Entrada", "Hora Salida", "Estatus"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
                cell.setBackgroundColor(azul);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPadding(8);
                table.addCell(cell);
            }

            SimpleDateFormat fechaFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat horaFormat = new SimpleDateFormat("HH:mm:ss");
            Font cellFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);

            while (rs.next()) {
                table.addCell(new PdfPCell(new Phrase(rs.getString("id_empleado"), cellFont)));
                String nombreCompleto = rs.getString("nombre1_empleado") + " " + rs.getString("apellido1_empleado");
                table.addCell(new PdfPCell(new Phrase(nombreCompleto, cellFont)));

                java.sql.Date fecha = rs.getDate("fecha_asistencia");
                table.addCell(new PdfPCell(new Phrase(fecha != null ? fechaFormat.format(fecha) : "Sin fecha", cellFont)));

                Time horaEntrada = rs.getTime("hora_entrada");
                Time horaSalida = rs.getTime("hora_salida");

                table.addCell(new PdfPCell(new Phrase(horaEntrada != null ? horaFormat.format(horaEntrada) : "Sin registro", cellFont)));
                table.addCell(new PdfPCell(new Phrase(horaSalida != null ? horaFormat.format(horaSalida) : "Sin registro", cellFont)));

                String estatus;
                if (horaEntrada == null) {
                    estatus = "No marcó entrada";
                } else {
                    Time limite = Time.valueOf("08:00:00");
                    estatus = horaEntrada.after(limite) ? "Tardío" : "Dentro de tiempo";
                }
                table.addCell(new PdfPCell(new Phrase(estatus, cellFont)));
            }
            document.add(table);

            agregarPie(document);

            document.close();
            conector.desconectar();

            JOptionPane.showMessageDialog(null, "Guardado en: " + nombreArchivo, "Reporte PDF generado exitosamente", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ocurrió un error al generar el PDF: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
