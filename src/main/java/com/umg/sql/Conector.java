package com.umg.sql;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.lang.reflect.InvocationTargetException;

public class Conector {


    private static String CLASE = "com.postgres.jdbc.Driver";
    private final String HOST = ".host";
    private final String USER = ".username";
    private final String PASS = ".password";
    private final String DATABASE = ".database";
    private final String URL;

    private Connection link;
    private PreparedStatement ps;

    public Conector() {
        this.URL = "jdbc:postgres://" + this.HOST + "/" + this.DATABASE;
    }


    public void conectar() {
        try {
            Class.forName(CLASE).getDeclaredConstructor().newInstance();
            this.link = DriverManager.getConnection(this.URL, this.USER, this.PASS);
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }


    public void desconectar() {
        try {
            this.link.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

        public PreparedStatement preparar(String sql) {
            try {
                ps = link.prepareStatement(sql);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            return ps;
        }

        public void mensaje(String mensaje, String titulo, int tipoMensaje){
            JOptionPane.showMessageDialog(null, mensaje, titulo, tipoMensaje);
        }
    }


