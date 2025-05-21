/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.umg.controlador;

import com.umg.modelos.ModeloLogin;
import com.umg.modelos.ModeloMenu;
import com.umg.vistas.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

/**
 *
 * @author axels
 */
public class ControladorMenu implements ActionListener, MouseListener {

    ModeloMenu modelo;
    VistaMenu vista;
    VistaPrincipal vistsaPrincipal;

    private boolean estado = true;

    public ControladorMenu() {
    }

    public ControladorMenu(ModeloMenu modelo, VistaMenu vista, VistaPrincipal vistsaPrincipal) {
        this.modelo = modelo;
        this.vista = vista;
        this.vistsaPrincipal = vistsaPrincipal;

        vista.btnMeReUser.addMouseListener(this);
        vista.btnExpandirMenu.addMouseListener(this);
        vista.panelMenu.addMouseListener(this);
        vista.contenedor.addMouseListener(this);
        vista.btnGestionUsuarios.addMouseListener(this);
        vista.btnMeMoEmple.addMouseListener(this);
        vista.btnActuEmpleado.addMouseListener(this);
        vista.btnRegresarLogin.addMouseListener(this);
        vista.btnMantePuestos.addMouseListener(this);
        vista.btnEliminarEmpleado.addMouseListener(this);
        vista.btnReportes.addMouseListener(this);
    }

    private void cambiarVista(JPanel panel) {
        panel.setSize(1230, 720);
        panel.setLocation(0, 0);

        vista.contenedor.removeAll();
        vista.contenedor.add(panel, BorderLayout.CENTER);
        vista.contenedor.revalidate();
        vista.contenedor.repaint();
    }

    public static void moverIzquierda(JComponent componente, int milisegundos, int saltos, int parar) {
        (new Thread() {
            public void run() {
                for (int i = componente.getWidth(); i >= parar; i -= saltos) {
                    try {
                        Thread.sleep(milisegundos);
                        componente.setPreferredSize(new Dimension(i, componente.getHeight()));
                        SwingUtilities.updateComponentTreeUI(componente);
                    } catch (InterruptedException e) {
                        System.out.println("Error Thread Interrumpido: " + e);
                    }
                }
            }
        }).start();
    }

    public static void moverDerecha(JComponent componente, int milisegundos, int saltos, int parar) {
        (new Thread() {
            public void run() {
                for (int i = componente.getWidth(); i <= parar; i += saltos) {
                    try {
                        Thread.sleep(milisegundos);
                        componente.setPreferredSize(new Dimension(i, componente.getHeight()));
                        SwingUtilities.updateComponentTreeUI(componente);
                    } catch (InterruptedException e) {
                        System.out.println("Error Thread Interrumpido: " + e);
                    }
                }
            }
        }).start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ModeloLogin modelo2 = new ModeloLogin();

        VistaLogin vista2 = new VistaLogin();
        new ControladorLogin(modelo2, vista2, vistsaPrincipal);
        vistsaPrincipal.cambiarPanel(vista2);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getComponent().equals(vista.btnMeReUser)) {
            VistaRegistrarEmpleado panel = new VistaRegistrarEmpleado();
            cambiarVista(panel);
        } else if (e.getComponent().equals(vista.btnGestionUsuarios)) {
            VistaMantenimientoUsuarios panel = new VistaMantenimientoUsuarios();
            cambiarVista(panel);
        } else if (e.getComponent().equals(vista.btnMeMoEmple)) {
            VistaMostrarEmpleados panel = new VistaMostrarEmpleados();
            cambiarVista(panel);
        } else if (e.getComponent().equals(vista.btnActuEmpleado)) {
            VistaActualizarEmpleado panel = new VistaActualizarEmpleado();
            cambiarVista(panel);
        } else if (e.getComponent().equals(vista.btnMantePuestos)) {
            VistaMantenimientoPuestos panel = new VistaMantenimientoPuestos();
            cambiarVista(panel);
        } else if (e.getComponent().equals(vista.btnEliminarEmpleado)) {
            VistaEliminarEmpleado panel = new VistaEliminarEmpleado();
            cambiarVista(panel);
        } else if (e.getComponent().equals(vista.btnReportes)) {
            VistaReportes panel = new VistaReportes();
            cambiarVista(panel);
        }  else if (e.getComponent().equals(vista.btnRegresarLogin)) {
            ModeloLogin modelo2 = new ModeloLogin();

            VistaLogin vista2 = new VistaLogin();
            new ControladorLogin(modelo2, vista2, vistsaPrincipal);
            vistsaPrincipal.cambiarPanel(vista2);
        } else if (e.getComponent().equals(vista.btnExpandirMenu)) {

            if (estado) {
                moverDerecha(vista.panelMenu, 1, 2, 200);
                estado = false;
            } else {
                moverIzquierda(vista.panelMenu, 1, 2, 50);
                estado = true;
            }
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
        if (e.getComponent().equals(vista.btnExpandirMenu)) {
            vista.btnExpandirMenu.setBackground(new Color(38, 163, 106));
        } else if (e.getComponent().equals(vista.contenedor)) {
            if (!estado) {
                moverIzquierda(vista.panelMenu, 1, 2, 50);
                estado = true;
            }
        } else if (e.getComponent().equals(vista.btnMeReUser)) {
            vista.btnMeReUser.setBackground(new Color(38, 163, 106));
        } else if (e.getComponent().equals(vista.btnActuEmpleado)) {
            vista.btnActuEmpleado.setBackground(new Color(38, 163, 106));
        } else if (e.getComponent().equals(vista.btnGestionUsuarios)) {
            vista.btnGestionUsuarios.setBackground(new Color(38, 163, 106));
        } else if (e.getComponent().equals(vista.btnRegresarLogin)) {
            vista.btnRegresarLogin.setBackground(new Color(38, 163, 106));
        } else if (e.getComponent().equals(vista.btnMantePuestos)) {
            vista.btnMantePuestos.setBackground(new Color(38, 163, 106));
        } else if (e.getComponent().equals(vista.btnMeMoEmple)) {
            vista.btnMeMoEmple.setBackground(new Color(38, 163, 106));
        } else if (e.getComponent().equals(vista.btnEliminarEmpleado)) {
            vista.btnEliminarEmpleado.setBackground(new Color(38, 163, 106));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {

        if (e.getComponent().equals(vista.btnExpandirMenu)) {
            vista.btnExpandirMenu.setBackground(new Color(0, 127, 75));
        } else if (e.getComponent().equals(vista.btnMeReUser)) {
            vista.btnMeReUser.setBackground(new Color(0, 127, 75));
        } else if (e.getComponent().equals(vista.btnActuEmpleado)) {
            vista.btnActuEmpleado.setBackground(new Color(0, 127, 75));
        } else if (e.getComponent().equals(vista.btnGestionUsuarios)) {
            vista.btnGestionUsuarios.setBackground(new Color(0, 127, 75));
        } else if (e.getComponent().equals(vista.btnRegresarLogin)) {
            vista.btnRegresarLogin.setBackground(new Color(0, 127, 75));
        } else if (e.getComponent().equals(vista.btnMantePuestos)) {
            vista.btnMantePuestos.setBackground(new Color(0, 127, 75));
        } else if (e.getComponent().equals(vista.btnMeMoEmple)) {
            vista.btnMeMoEmple.setBackground(new Color(0, 127, 75));
        } else if (e.getComponent().equals(vista.btnEliminarEmpleado)) {
            vista.btnEliminarEmpleado.setBackground(new Color(0, 127, 75));
        }

    }

    Timer timer;
    int pasosRealizados = 0;
    public void animacionLabel(JLabel label){

        int alturaInicial = 720;
        int alturaFinal = 150;
        int ancho = 1220;
        int duracion = 250;
        int pasos = 50;
        int retardo = duracion / pasos;

        timer = new Timer(retardo,(ActionEvent e) -> {
            pasosRealizados++;
            float fraccion = (float) pasosRealizados / pasos;
            int nuevaAltura = alturaInicial + Math.round((alturaFinal - alturaInicial) * fraccion);
            label.setBounds(50, 50, ancho, nuevaAltura);

            if (pasosRealizados >= pasos){
                timer.stop();
            }
        });

        timer.start();
    }

}
