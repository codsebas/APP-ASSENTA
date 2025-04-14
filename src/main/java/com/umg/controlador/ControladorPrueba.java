/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.umg.controlador;

import com.umg.modelos.ModeloLogin;
import com.umg.modelos.ModeloPrueba;
import com.umg.vistas.VistaLogin;
import com.umg.vistas.VistaPrincipal;
import com.umg.vistas.VistaPrueba;
import com.umg.vistas.VistaRegistrarEmpleado;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author axels
 */
public class ControladorPrueba implements ActionListener, MouseListener {

    ModeloPrueba modelo;
    VistaPrueba vista;
    VistaPrincipal vistsaPrincipal;

    private boolean estado = true;

    public ControladorPrueba(ModeloPrueba modelo, VistaPrueba vista, VistaPrincipal vistsaPrincipal) {
        this.modelo = modelo;
        this.vista = vista;
        this.vistsaPrincipal = vistsaPrincipal;

        vista.btnMeReUser.addMouseListener(this);
        vista.btnExpandirMenu.addMouseListener(this);
        vista.panelMenu.addMouseListener(this);
        vista.contenedor.addMouseListener(this);
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
            vista.btnExpandirMenu.setBackground(new Color(0, 153, 153));
        } else if(e.getComponent().equals(vista.contenedor)){
            if (!estado){
                moverIzquierda(vista.panelMenu, 1, 2, 50);
                estado = true;
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {

        if (e.getComponent().equals(vista.btnExpandirMenu)) {
            vista.btnExpandirMenu.setBackground(new Color(153, 255, 255));
        }

    }

}
