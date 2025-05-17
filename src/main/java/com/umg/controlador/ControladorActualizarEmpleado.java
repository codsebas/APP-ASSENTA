/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.umg.controlador;

import com.digitalpersona.uareu.*;
import com.umg.biometrics.Enrollment;
import com.umg.biometrics.ImagePanel;
import com.umg.biometrics.ResultadoCapturaHuella;
import com.umg.implementacion.EmpleadoImp;
import com.umg.implementacion.PuestoImp;
import com.umg.modelos.ModeloActualizarEmpleado;
import com.umg.modelos.ModeloEmpleado;
import com.umg.modelos.ModeloJefeInmediato;
import com.umg.modelos.ModeloPuesto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author axels
 */
public class ControladorActualizarEmpleado implements MouseListener, ActionListener {
    ModeloActualizarEmpleado modelo;
    PuestoImp implementacionPuesto = new PuestoImp();
    EmpleadoImp implementacion = new EmpleadoImp();
    private Map<String, String[]> municipiosPorDepartamento;
    Reader lector = obtenerReader();

    int id_emple;
    public int getId_emple() {
        return id_emple;
    }

    //Para las plantillas
    Fmd plantillaH1 = null;
    Fmd plantillaH2 = null;
    Fmd plantillaH3 = null;
    Fmd plantillaH4 = null;
    List<Fmd> listaPlantillas = new ArrayList<>();

    public void setId_emple(int id_emple) {
        this.id_emple = id_emple;
    }

    public ControladorActualizarEmpleado(ModeloActualizarEmpleado modelo) {
        this.modelo = modelo;
        listaPlantillas.add(null);
        listaPlantillas.add(null);
        listaPlantillas.add(null);
        listaPlantillas.add(null);
        agregarMunicipios();
    }

    public void cargarJefes(JComboBox<Object> comboBox) {

        List<ModeloJefeInmediato> jefes = implementacionPuesto.obtenerJefesInmediatos();
        comboBox.removeAllItems();

        comboBox.addItem("Elegir Jefe");
        for (ModeloJefeInmediato j : jefes) {
            comboBox.addItem(j);
        }
    }


    public void cargarPuestos(JComboBox<Object> comboBox) {
        List<ModeloPuesto> puestos = implementacionPuesto.obtenerPuestos();
        comboBox.removeAllItems();

        comboBox.addItem("Elegir Puesto");
        for (ModeloPuesto p : puestos) {
            comboBox.addItem(p);
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getComponent().equals(modelo.getvActualizarEmpleado().btnActualizarEmpleado)) {
            actualizarEmpleado();
        } else if (e.getComponent().equals(modelo.getvActualizarEmpleado().btnBuscarEmpleado)) {
            mostrarEmpleado();
        } else if (e.getComponent().equals(modelo.getvActualizarEmpleado().panelH1)) {
            capturarHuella(0, modelo.getvActualizarEmpleado().panelH1);
        } else if (e.getComponent().equals(modelo.getvActualizarEmpleado().panelH2)) {
            capturarHuella(1, modelo.getvActualizarEmpleado().panelH2);
        } else if (e.getComponent().equals(modelo.getvActualizarEmpleado().panelH3)) {
            capturarHuella(2, modelo.getvActualizarEmpleado().panelH3);
        } else if (e.getComponent().equals(modelo.getvActualizarEmpleado().panelH4)) {
            capturarHuella(3, modelo.getvActualizarEmpleado().panelH4);
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
        if (e.getComponent().equals(modelo.getvActualizarEmpleado().btnActualizarEmpleado)){
            modelo.getvActualizarEmpleado().btnActualizarEmpleado.setBackground(new Color(38, 163, 106));
        } else if (e.getComponent().equals(modelo.getvActualizarEmpleado().btnBuscarEmpleado)){
            modelo.getvActualizarEmpleado().btnBuscarEmpleado.setBackground(new Color(38, 163, 106));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getComponent().equals(modelo.getvActualizarEmpleado().btnActualizarEmpleado)){
            modelo.getvActualizarEmpleado().btnActualizarEmpleado.setBackground(new Color(0,127,75));
        } else if (e.getComponent().equals(modelo.getvActualizarEmpleado().btnBuscarEmpleado)){
            modelo.getvActualizarEmpleado().btnBuscarEmpleado.setBackground(new Color(0,127,75));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(modelo.getvActualizarEmpleado().cbDepto.getActionCommand())) {
            actualizarMunicipios();
        }


    }

    private boolean existeEnComboBox(JComboBox<Object> comboBox, String valor) {
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            if (comboBox.getItemAt(i).toString().equals(valor)) {
                return true;
            }
        }
        return false;
    }


    public void mostrarEmpleado() {
        String dpi = modelo.getvActualizarEmpleado().txtDPI.getText();

        if (dpi == null || dpi.isEmpty()) {
            JOptionPane.showMessageDialog(null, "⚠️ Por favor, ingresa un DPI.");
            return;
        }

        EmpleadoImp imp = new EmpleadoImp();
        ModeloEmpleado empleado = imp.mostrarEmpleadoPorDpi(dpi);

        if (empleado == null) {
            JOptionPane.showMessageDialog(null, "El dpi no esite BURRO.");
            return;
        }

        var vista = modelo.getvActualizarEmpleado();
        String estadoCivilBD = empleado.getEstadoCivil();
        String estadoCivil = "";
        switch (estadoCivilBD) {
            case "C":
                estadoCivil = "Casado/a";
                break;
            case "S":
                estadoCivil = "Soltero/a";
                break;
            case "D":
                estadoCivil = "Divorciado/a";
                break;
            case "V":
                estadoCivil = "Viudo/a";
                break;
            case "U":
                estadoCivil = "Unión de Hecho";
                break;
            default:
                estadoCivil = "Desconocido";
                break;
        }


        vista.cbEstadoCivil.setSelectedItem(estadoCivil);

        vista.cbSexo.setSelectedItem(empleado.getSexo());

        String nombrePuesto = imp.obtenerNombrePuestoDesdeBD(empleado.getIdPuesto());

        for (int i = 0; i < vista.cbPuesto.getItemCount(); i++) {
            if (vista.cbPuesto.getItemAt(i).toString().equals(nombrePuesto)) {
                vista.cbPuesto.setSelectedIndex(i); // Fuerza la selección
                break;
            }
        }

        String nombreJefe = imp.obtenerNombreJefeDesdeBD(empleado.getIdJefeInmediato());

// Recorrer el ComboBox para forzar la selección
        for (int i = 0; i < vista.cbJefeInmediato.getItemCount(); i++) {
            if (vista.cbJefeInmediato.getItemAt(i).toString().equals(nombreJefe)) {
                vista.cbJefeInmediato.setSelectedIndex(i);
                break;
            }
        }


        setId_emple(empleado.getIdEmpleado());
        System.out.println("id empleado desde mostrar: " + empleado.getIdEmpleado());
        System.out.println("Estado Civil: " + empleado.getEstadoCivil());
        vista.cbEstadoCivil.setSelectedItem(empleado.getEstadoCivil());
        vista.txtNom1.setText(empleado.getPrimerNombre());
        vista.txtNom2.setText(empleado.getSegundoNombre());
        vista.txtNom3.setText(empleado.getTercerNombre());
        vista.txtApe1.setText(empleado.getPrimerApellido());
        vista.txtApe2.setText(empleado.getSegundoApellido());
        vista.txtApeC.setText(empleado.getApellidoCasada());
        vista.txtFecha.setText(empleado.getFechaNacimiento());
        // Si quieres mostrar la edad
        // vista.txtEdad.setText(String.valueOf(empleado.getEdad()));
        vista.txtCorreo.setText(empleado.getCorreoElectronico());
        vista.txtNum1.setText(empleado.getNumeroTelefono1());
        vista.txtNum2.setText(empleado.getNumeroTelefono2());
        vista.txtHoraEntrada.setText(empleado.getHorarioEntrada());
        vista.txtHoraSalida.setText(empleado.getHorarioSalida());
        //vista.cbPuesto.setSelectedItem(empleado.getNombrePuesto());//posible error
        //vista.cbJefeInmediato.setSelectedItem(empleado.getNombreJefeInmediato());


        vista.cbDepto.setSelectedItem(empleado.getDepartamento());
        vista.cbMun.setSelectedItem(empleado.getMunicipio());
        vista.txtAldea.setText(empleado.getAldeaColonia());
        vista.txtDireccion.setText(empleado.getDireccionVivienda());

        JOptionPane.showMessageDialog(null, "Datos cargados por completo MAQUINA.");
    }


    private void actualizarEmpleado() {
        String departamento = String.valueOf(modelo.getvActualizarEmpleado().cbDepto.getSelectedItem());
        String municipio = String.valueOf(modelo.getvActualizarEmpleado().cbMun.getSelectedItem());

        String estadoSeleccionado = String.valueOf(modelo.getvActualizarEmpleado().cbEstadoCivil.getSelectedItem());
        String estadoCivil = switch (estadoSeleccionado) {
            case "Soltero/a" -> "S";
            case "Casado/a" -> "C";
            case "Divorciado/a" -> "D";
            case "Viudo/a" -> "V";
            case "Unión de Hecho" -> "U";
            default -> "";
        };

        Object selectedItem = modelo.getvActualizarEmpleado().cbJefeInmediato.getSelectedItem();
        int idJefe = (selectedItem instanceof ModeloJefeInmediato jefe) ? jefe.getIdEmpleado() : 0;

        Object selectedItem2 = modelo.getvActualizarEmpleado().cbPuesto.getSelectedItem();
        int idPuesto = (selectedItem2 instanceof ModeloPuesto puesto) ? puesto.getIdPuesto() : 0;

        String sexo = String.valueOf(modelo.getvActualizarEmpleado().cbSexo.getSelectedItem());

        ModeloEmpleado modeloEmpleado = new ModeloEmpleado();

        // Aquí cambiamos a buscar por DPI
        String dpi = modelo.getvActualizarEmpleado().txtDPI.getText().trim();
        if (dpi.isEmpty()) {
            JOptionPane.showMessageDialog(null, "El DPI no puede estar vacío", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        modeloEmpleado.setDpi(dpi); // Establecemos el DPI

        modeloEmpleado.setPrimerNombre(modelo.getvActualizarEmpleado().txtNom1.getText());
        modeloEmpleado.setSegundoNombre(modelo.getvActualizarEmpleado().txtNom2.getText());
        modeloEmpleado.setTercerNombre(modelo.getvActualizarEmpleado().txtNom3.getText());
        modeloEmpleado.setPrimerApellido(modelo.getvActualizarEmpleado().txtApe1.getText());
        modeloEmpleado.setSegundoApellido(modelo.getvActualizarEmpleado().txtApe2.getText());
        modeloEmpleado.setApellidoCasada(modelo.getvActualizarEmpleado().txtApeC.getText());
        modeloEmpleado.setDepartamento(departamento);
        modeloEmpleado.setMunicipio(municipio);
        modeloEmpleado.setIdJefeInmediato(idJefe);
        modeloEmpleado.setIdPuesto(idPuesto);
        modeloEmpleado.setEstadoCivil(estadoCivil);
        modeloEmpleado.setSexo(sexo);
        modeloEmpleado.setCorreoElectronico(modelo.getvActualizarEmpleado().txtCorreo.getText());
        modeloEmpleado.setNumeroTelefono1(modelo.getvActualizarEmpleado().txtNum1.getText());
        modeloEmpleado.setNumeroTelefono2(modelo.getvActualizarEmpleado().txtNum2.getText());
        modeloEmpleado.setHorarioEntrada(modelo.getvActualizarEmpleado().txtHoraEntrada.getText());
        modeloEmpleado.setHorarioSalida(modelo.getvActualizarEmpleado().txtHoraSalida.getText());
        modeloEmpleado.setAldeaColonia(modelo.getvActualizarEmpleado().txtAldea.getText());
        modeloEmpleado.setDireccionVivienda(modelo.getvActualizarEmpleado().txtDireccion.getText());

        // 🟢 Conversión de fecha
        String fechaTexto = modelo.getvActualizarEmpleado().txtFecha.getText().trim();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate fechaNacimiento = LocalDate.parse(fechaTexto, formatter);

            modeloEmpleado.setFechaNacimiento(fechaNacimiento.toString());
            modeloEmpleado.setEdad(Period.between(fechaNacimiento, LocalDate.now()).getYears());
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(null, "La fecha debe tener el formato yyyy-MM-dd (ej: 2000-12-25)");
            return;
        }

        //  Llamada al DAO para actualizar por DPI
        boolean resultado = implementacion.actualizarEmpleado(modeloEmpleado, listaPlantillas);
        if (resultado) {
            limpiarCampos();
            JOptionPane.showMessageDialog(null, "Empleado actualizado con éxito", "Actualización", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo actualizar el empleado", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void agregarMunicipios() {
        municipiosPorDepartamento = new HashMap<>();

        municipiosPorDepartamento.put("Alta Verapaz", new String[]{
                "Cobán", "Santa Cruz Verapaz", "San Cristóbal Verapaz", "Tactic", "Tamahú", "Tucurú", "Panzós", "Senahú",
                "San Pedro Carchá", "Lanquín", "Chisec", "Chahal", "Fray Bartolomé de las Casas", "Santa Catalina La Tinta",
                "Raxruhá"
        });

        municipiosPorDepartamento.put("Baja Verapaz", new String[]{
                "Salamá", "San Miguel Chicaj", "Rabinal", "Cubulco", "Granados", "Santa Cruz El Chol", "San Jerónimo", "Purulhá"
        });

        municipiosPorDepartamento.put("Chimaltenango", new String[]{
                "Chimaltenango", "San José Poaquil", "San Martín Jilotepeque", "Comalapa", "Santa Apolonia",
                "Tecpán Guatemala", "Patzún", "Pochuta", "Patzicía", "Santa Cruz Balanyá", "Acatenango", "Yepocapa",
                "San Andrés Itzapa", "Parramos", "Zaragoza", "El Tejar"
        });

        municipiosPorDepartamento.put("Chiquimula", new String[]{
                "Chiquimula", "San José La Arada", "San Juan Ermita", "Jocotán", "Camotán", "Olopa", "Esquipulas", "Concepción Las Minas", "Quetzaltepeque"
        });

        municipiosPorDepartamento.put("El Progreso", new String[]{
                "Guastatoya", "Morazán", "San Agustín Acasaguastlán", "San Cristóbal Acasaguastlán", "El Jícaro",
                "Sansare", "Sanarate", "San Antonio La Paz"
        });

        municipiosPorDepartamento.put("Escuintla", new String[]{
                "Escuintla", "Santa Lucía Cotzumalguapa", "La Democracia", "Siquinalá", "Masagua", "Tiquisate",
                "La Gomera", "Guanagazapa", "San José", "Iztapa", "Palín", "San Vicente Pacaya", "Nueva Concepción"
        });

        municipiosPorDepartamento.put("Guatemala", new String[]{
                "Guatemala", "Santa Catarina Pinula", "San José Pinula", "San José del Golfo", "Palencia", "Chinautla",
                "San Pedro Ayampuc", "Mixco", "San Pedro Sacatepéquez", "San Juan Sacatepéquez", "San Raymundo",
                "Chuarrancho", "Fraijanes", "Amatitlán", "Villa Nueva", "Villa Canales", "Petapa"
        });

        municipiosPorDepartamento.put("Huehuetenango", new String[]{
                "Huehuetenango", "Chiantla", "Malacatancito", "Cuilco", "Nentón", "San Pedro Necta", "Jacaltenango",
                "San Pedro Soloma", "San Ildefonso Ixtahuacán", "Santa Bárbara", "La Libertad", "La Democracia",
                "San Miguel Acatán", "San Rafael La Independencia", "Todos Santos Cuchumatán", "San Juan Atitán",
                "Santa Eulalia", "San Mateo Ixtatán", "Colotenango", "San Sebastián Huehuetenango",
                "Tectitán", "Concepción Huista", "San Juan Ixcoy", "San Antonio Huista", "Santa Cruz Barillas",
                "Aguacatán", "San Rafael Petzal", "San Gaspar Ixchil", "Santiago Chimaltenango", "Santa Ana Huista"
        });

        municipiosPorDepartamento.put("Izabal", new String[]{
                "Puerto Barrios", "Livingston", "El Estor", "Morales", "Los Amates"
        });

        municipiosPorDepartamento.put("Jalapa", new String[]{
                "Jalapa", "San Pedro Pinula", "San Luis Jilotepeque", "San Manuel Chaparrón", "San Carlos Alzatate",
                "Monjas", "Mataquescuintla"
        });

        municipiosPorDepartamento.put("Jutiapa", new String[]{
                "Jutiapa", "El Progreso", "Santa Catarina Mita", "Agua Blanca", "Asunción Mita", "Yupiltepeque", "Atescatempa",
                "Jerez", "El Adelanto", "Zapotitlán", "Comapa", "Jalpatagua", "Conguaco", "Moyuta", "Pasaco", "Quesada"
        });

        municipiosPorDepartamento.put("Petén", new String[]{
                "Flores", "San Benito", "San Andrés", "La Libertad", "San Francisco", "Santa Ana", "Dolores",
                "San Luis", "Sayaxché", "Melchor de Mencos", "Poptún", "Las Cruces", "El Chal"
        });

        municipiosPorDepartamento.put("Quetzaltenango", new String[]{
                "Quetzaltenango", "Salcajá", "Olintepeque", "San Carlos Sija", "Sibilia", "Cabricán", "Cajolá",
                "San Miguel Sigüilá", "Ostuncalco", "San Mateo", "Concepción Chiquirichapa", "San Martín Sacatepéquez",
                "Almolonga", "Cantel", "Huitán", "Zunil", "Colomba", "San Francisco La Unión", "El Palmar", "Coatepeque",
                "Génova", "Flores Costa Cuca", "La Esperanza", "Palestina de Los Altos"
        });

        municipiosPorDepartamento.put("Quiché", new String[]{
                "Santa Cruz del Quiché", "Chiché", "Chinique", "Zacualpa", "Chajul", "Chichicastenango", "Patzité",
                "San Antonio Ilotenango", "San Pedro Jocopilas", "Cunén", "Uspantán", "Sacapulas", "San Bartolomé Jocotenango",
                "Canillá", "Chicamán", "Ixcán", "Pachalum", "Joyabaj", "Nebaj"
        });

        municipiosPorDepartamento.put("Retalhuleu", new String[]{
                "Retalhuleu", "San Sebastián", "Santa Cruz Muluá", "San Martín Zapotitlán", "San Felipe", "San Andrés Villa Seca",
                "Champerico", "Nuevo San Carlos", "El Asintal"
        });

        municipiosPorDepartamento.put("Sacatepéquez", new String[]{
                "Antigua Guatemala", "Jocotenango", "Pastores", "Sumpango", "Santo Domingo Xenacoj", "Santiago Sacatepéquez",
                "San Bartolomé Milpas Altas", "San Lucas Sacatepéquez", "Santa Lucía Milpas Altas", "Magdalena Milpas Altas",
                "Santa María de Jesús", "Ciudad Vieja", "San Miguel Dueñas", "Alotenango", "Santa Catarina Barahona"
        });

        municipiosPorDepartamento.put("San Marcos", new String[]{
                "San Marcos", "San Pedro Sacatepéquez", "San Antonio Sacatepéquez", "Comitancillo", "San Miguel Ixtahuacán",
                "Concepción Tutuapa", "Tacaná", "Sibinal", "Tajumulco", "Tejutla", "San Rafael Pie de la Cuesta",
                "Nuevo Progreso", "El Tumbador", "San José El Rodeo", "Malacatán", "Catarina", "Ayutla", "Ocos",
                "San Pablo", "El Quetzal", "La Reforma", "Pajapita", "Ixchiguán", "San Cristóbal Cucho",
                "Sipacapa", "Esquipulas Palo Gordo", "Río Blanco", "San Lorenzo"
        });

        municipiosPorDepartamento.put("Santa Rosa", new String[]{
                "Cuilapa", "Barberena", "Santa Rosa de Lima", "Casillas", "San Rafael Las Flores", "Oratorio", "San Juan Tecuaco",
                "Chiquimulilla", "Taxisco", "Santa María Ixhuatán", "Guazacapán", "Santa Cruz Naranjo", "Pueblo Nuevo Viñas",
                "Nueva Santa Rosa"
        });

        municipiosPorDepartamento.put("Sololá", new String[]{
                "Sololá", "San José Chacayá", "Santa María Visitación", "Santa Lucía Utatlán", "Nahualá", "Santa Catarina Ixtahuacán",
                "Santa Clara La Laguna", "Concepción", "San Andrés Semetabaj", "Panajachel", "Santa Catarina Palopó",
                "San Antonio Palopó", "San Lucas Tolimán", "Santa Cruz La Laguna", "San Pablo La Laguna", "San Marcos La Laguna",
                "San Juan La Laguna", "San Pedro La Laguna", "Santiago Atitlán"
        });

        municipiosPorDepartamento.put("Suchitepéquez", new String[]{
                "Mazatenango", "Cuyotenango", "San Francisco Zapotitlán", "San Bernardino", "San José El Ídolo", "Santo Domingo Suchitepéquez",
                "San Lorenzo", "Samayac", "San Pablo Jocopilas", "San Antonio Suchitepéquez", "San Miguel Panán",
                "San Gabriel", "Chicacao", "Patulul", "Santa Bárbara", "San Juan Bautista", "Santo Tomás La Unión", "Zunilito"
        });

        municipiosPorDepartamento.put("Totonicapán", new String[]{
                "Totonicapán", "San Cristóbal Totonicapán", "San Francisco El Alto", "San Andrés Xecul", "Momostenango",
                "Santa María Chiquimula", "Santa Lucía La Reforma", "San Bartolo"
        });

        municipiosPorDepartamento.put("Zacapa", new String[]{
                "Zacapa", "Estanzuela", "Río Hondo", "Gualán", "Teculután", "Usumatlán", "Cabañas", "San Diego", "La Unión", "Huité"
        });
    }

    private void actualizarMunicipios() {
        String seleccionado = (String) modelo.getvActualizarEmpleado().cbDepto.getSelectedItem();
        modelo.getvActualizarEmpleado().cbMun.removeAllItems();
        modelo.getvActualizarEmpleado().cbMun.addItem("Elegir Municipio");

        if (municipiosPorDepartamento.containsKey(seleccionado)) {
            for (String municipio : municipiosPorDepartamento.get(seleccionado)) {
                modelo.getvActualizarEmpleado().cbMun.addItem(municipio);
            }
        }
    }

    private void limpiarCampos(){
        plantillaH1 = null;
        plantillaH2 = null;
        plantillaH3 = null;
        plantillaH4 = null;

        if (listaPlantillas != null) {
            listaPlantillas.clear();
            listaPlantillas = new ArrayList<>();
            listaPlantillas.add(null);
            listaPlantillas.add(null);
            listaPlantillas.add(null);
            listaPlantillas.add(null);
        }

        limpiarPanelHuella(modelo.getvActualizarEmpleado().panelH1);
        limpiarPanelHuella(modelo.getvActualizarEmpleado().panelH2);
        limpiarPanelHuella(modelo.getvActualizarEmpleado().panelH3);
        limpiarPanelHuella(modelo.getvActualizarEmpleado().panelH4);

        modelo.getvActualizarEmpleado().txtDPI.setText("");
        modelo.getvActualizarEmpleado().txtNom1.setText("");
        modelo.getvActualizarEmpleado().txtNom2.setText("");
        modelo.getvActualizarEmpleado().txtNom3.setText("");
        modelo.getvActualizarEmpleado().txtApe1.setText("");
        modelo.getvActualizarEmpleado().txtApe2.setText("");
        modelo.getvActualizarEmpleado().txtApeC.setText("");
        modelo.getvActualizarEmpleado().txtFecha.setText("");
        modelo.getvActualizarEmpleado().txtCorreo.setText("");
        modelo.getvActualizarEmpleado().txtNum1.setText("");
        modelo.getvActualizarEmpleado().txtNum2.setText("");
        modelo.getvActualizarEmpleado().txtDPI.setText("");
        modelo.getvActualizarEmpleado().txtHoraEntrada.setText("");
        modelo.getvActualizarEmpleado().txtHoraSalida.setText("");
        modelo.getvActualizarEmpleado().txtAldea.setText("");
        modelo.getvActualizarEmpleado().txtDireccion.setText("");

        modelo.getvActualizarEmpleado().cbDepto.setSelectedIndex(0);
        modelo.getvActualizarEmpleado().cbMun.setSelectedIndex(0);
        modelo.getvActualizarEmpleado().cbEstadoCivil.setSelectedIndex(0);
        modelo.getvActualizarEmpleado().cbSexo.setSelectedIndex(0);
        modelo.getvActualizarEmpleado().cbJefeInmediato.setSelectedIndex(0);
        modelo.getvActualizarEmpleado().cbPuesto.setSelectedIndex(0);

    }

    private void limpiarPanelHuella(JPanel panel) {
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
    }

    public Reader obtenerReader() {
        Reader reader = null;
        try {
            ReaderCollection readers = UareUGlobal.GetReaderCollection();
            readers.GetReaders(); // Refrescar la lista de dispositivos conectados
            if (readers.size() == 0) {
                System.out.println("❌ No se detectaron lectores de huella.");
                return null;
            }

            reader = readers.get(0); // Selecciona automáticamente el primer lector encontrado
            System.out.println("✅ Lector seleccionado automáticamente: " + reader.GetDescription().name);

        } catch (UareUException e) {
            System.err.println("❌ Error inicializando el lector: " + e.getMessage());
        }
        return reader;
    }

    private void capturarHuella(int indice, JPanel panel) {
        ResultadoCapturaHuella resultado = Enrollment.Run(lector);

        if (resultado != null && resultado.getPlantilla() != null) {
            listaPlantillas.set(indice, resultado.getPlantilla());

            // Mostrar la imagen de la huella en el panel
            Graphics g = panel.getGraphics();
            if (g != null) {
                ImagePanel imagePanel = new ImagePanel();
                imagePanel.showImage(resultado.getImagen());
                imagePanel.setSize(panel.getWidth(), panel.getHeight());
                panel.removeAll();
                panel.add(imagePanel);
                panel.revalidate();
                panel.repaint();
            }
        }
    }
}


