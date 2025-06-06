/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.umg.vistas;

import com.umg.controlador.ControladorReportes;
import com.umg.modelos.ModeloVistaReportes;

/**
 *
 * @author Keily Orellana
 */
public class VistaReportes extends javax.swing.JPanel {

    /**
     * Creates new form VistaReportes
     */
    public VistaReportes() {
        initComponents();
        ModeloVistaReportes modelo = new ModeloVistaReportes(this);
        ControladorReportes controlador = new ControladorReportes(modelo);
        setControlador(controlador);
    }



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtFechaDesde = new javax.swing.JTextField();
        txtDpi = new javax.swing.JTextField();
        txtFechaHasta = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        btnReporteDiario = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        btnSinMarca = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        btnReporteEmpleado = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(1230, 720));

        jPanel1.setBackground(new java.awt.Color(240, 253, 244));
        jPanel1.setMinimumSize(new java.awt.Dimension(1220, 720));
        jPanel1.setPreferredSize(new java.awt.Dimension(1220, 720));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(368, 24, -1, -1));

        jPanel3.setBackground(new java.awt.Color(240, 253, 244));
        jPanel3.setMinimumSize(new java.awt.Dimension(970, 400));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 127, 75));
        jLabel2.setText("Fecha desde");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 70, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 127, 75));
        jLabel3.setText("Fecha hasta");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 70, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 127, 75));
        jLabel4.setText("DPI");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, -1));

        txtFechaDesde.setBackground(new java.awt.Color(255, 255, 255));
        txtFechaDesde.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtFechaDesde.setForeground(new java.awt.Color(26, 75, 54));
        txtFechaDesde.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtFechaDesde.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txtFechaDesde.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFechaDesdeActionPerformed(evt);
            }
        });
        jPanel3.add(txtFechaDesde, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 90, 270, -1));

        txtDpi.setBackground(new java.awt.Color(255, 255, 255));
        txtDpi.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtDpi.setForeground(new java.awt.Color(26, 75, 54));
        txtDpi.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtDpi.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txtDpi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDpiActionPerformed(evt);
            }
        });
        jPanel3.add(txtDpi, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, 270, -1));

        txtFechaHasta.setBackground(new java.awt.Color(255, 255, 255));
        txtFechaHasta.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtFechaHasta.setForeground(new java.awt.Color(26, 75, 54));
        txtFechaHasta.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtFechaHasta.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txtFechaHasta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFechaHastaActionPerformed(evt);
            }
        });
        jPanel3.add(txtFechaHasta, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 90, 270, -1));

        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(816, 143, -1, -1));

        btnReporteDiario.setBackground(new java.awt.Color(0, 127, 75));
        btnReporteDiario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReporteDiario.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Reporte Diario");
        btnReporteDiario.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 120, 30));

        jPanel3.add(btnReporteDiario, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 220, 120, 30));

        btnSinMarca.setBackground(new java.awt.Color(0, 127, 75));
        btnSinMarca.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSinMarca.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Reporte de entrada tarde/sin marcar");
        btnSinMarca.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 270, 30));

        jPanel3.add(btnSinMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 220, 270, 30));

        btnReporteEmpleado.setBackground(new java.awt.Color(0, 127, 75));
        btnReporteEmpleado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReporteEmpleado.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Reporte de Empleado");
        btnReporteEmpleado.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 170, 30));

        jPanel3.add(btnReporteEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 130, 170, 30));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 127, 75));
        jLabel19.setText("DATOS DE REPORTE POR EMPLEADO");
        jPanel3.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, -1, -1));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 127, 75));
        jLabel20.setText("REPORTES VARIOS");
        jPanel3.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 190, 120, -1));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/umg/imagenes/img-tabla-usuarios.png"))); // NOI18N
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 970, 390));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 120, 970, 430));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 127, 75));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Reportes");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 0, 400, 60));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1230, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtFechaHastaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaHastaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaHastaActionPerformed

    private void txtFechaDesdeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaDesdeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaDesdeActionPerformed

    private void txtDpiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDpiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDpiActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JPanel btnReporteDiario;
    public javax.swing.JPanel btnReporteEmpleado;
    public javax.swing.JPanel btnSinMarca;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    public javax.swing.JTextField txtDpi;
    public javax.swing.JTextField txtFechaDesde;
    public javax.swing.JTextField txtFechaHasta;
    // End of variables declaration//GEN-END:variables

    private void setControlador(ControladorReportes controlador) {

        btnReporteDiario.addMouseListener(controlador);
        btnReporteEmpleado.addMouseListener(controlador);
        btnSinMarca.addMouseListener(controlador);
        txtFechaHasta.getDocument().addDocumentListener(controlador);
        txtFechaDesde.getDocument().addDocumentListener(controlador);
        txtDpi.addKeyListener(controlador);
        txtFechaDesde.addKeyListener(controlador);
        txtFechaHasta.addKeyListener(controlador);
    }
}
