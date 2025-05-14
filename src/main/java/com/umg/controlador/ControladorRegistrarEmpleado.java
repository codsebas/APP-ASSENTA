package com.umg.controlador;

import com.digitalpersona.uareu.*;
import com.umg.biometrics.Enrollment;
import com.umg.biometrics.ImagePanel;
import com.umg.biometrics.ResultadoCapturaHuella;
import com.umg.implementacion.EmpleadoImp;
import com.umg.implementacion.PuestoImp;
import com.umg.modelos.ModeloEmpleado;
import com.umg.modelos.ModeloJefeInmediato;
import com.umg.modelos.ModeloPuesto;
import com.umg.modelos.ModeloVistaRegistrarEmpleado;
import com.umg.modelos.ModeloHuella;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class ControladorRegistrarEmpleado implements MouseListener, ActionListener {
    EmpleadoImp implementacion = new EmpleadoImp();
    ModeloVistaRegistrarEmpleado modelo;
    PuestoImp implementacionPuesto = new PuestoImp();
    Reader lector = obtenerReader();

    //Para las plantillas
    Fmd plantillaH1 = null;
    Fmd plantillaH2 = null;
    Fmd plantillaH3 = null;
    Fmd plantillaH4 = null;
    List<Fmd> listaPlantillas = new ArrayList<>();

    private Map<String, String[]> municipiosPorDepartamento;

    public ControladorRegistrarEmpleado(ModeloVistaRegistrarEmpleado modelo) {
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
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(modelo.getvRegistraEmpleado().cbDepto.getActionCommand())) {
            actualizarMunicipios();
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getComponent().equals(modelo.getvRegistraEmpleado().btnRegistrarEmpleado)) {
            validarCampos();
        } else if (e.getComponent().equals(modelo.getvRegistraEmpleado().panelH1)) {
            capturarHuella(0, modelo.getvRegistraEmpleado().panelH1);
        } else if (e.getComponent().equals(modelo.getvRegistraEmpleado().panelH2)) {
            capturarHuella(1, modelo.getvRegistraEmpleado().panelH2);
        } else if (e.getComponent().equals(modelo.getvRegistraEmpleado().panelH3)) {
            capturarHuella(2, modelo.getvRegistraEmpleado().panelH3);
        } else if (e.getComponent().equals(modelo.getvRegistraEmpleado().panelH4)) {
            capturarHuella(3, modelo.getvRegistraEmpleado().panelH4);
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
        if (e.getComponent().equals(modelo.getvRegistraEmpleado().btnRegistrarEmpleado)) {
            modelo.getvRegistraEmpleado().btnRegistrarEmpleado.setBackground(new Color(38, 163, 106));
        }
    }
    
    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getComponent().equals(modelo.getvRegistraEmpleado().btnRegistrarEmpleado)) {
            modelo.getvRegistraEmpleado().btnRegistrarEmpleado.setBackground(new Color(0,127,75));
        }
    }
    
    private void unirNombre() {
        String texto;
        texto = modelo.getvRegistraEmpleado().txtNom1.getText() + " " + modelo.getvRegistraEmpleado().txtNom2.getText();
        System.out.println("El nombre unido es: " + texto);

    }
    
    public void llenarDepartamentos(){
        String[] departamentos = {
                "Elegir Departamento",
                "Alta Verapaz", "Baja Verapaz", "Chimaltenango", "Chiquimula", "El Progreso",
                "Escuintla", "Guatemala", "Huehuetenango", "Izabal", "Jalapa", "Jutiapa",
                "Petén", "Quetzaltenango", "Quiché", "Retalhuleu", "Sacatepéquez", "San Marcos",
                "Santa Rosa", "Sololá", "Suchitepéquez", "Totonicapán", "Zacapa"
        };

        modelo.getvRegistraEmpleado().cbDepto = new JComboBox<>(departamentos);
    }

    private void insertarEmpleado(){
        String departamento = String.valueOf(modelo.getvRegistraEmpleado().cbDepto.getSelectedItem());
        String municipio = String.valueOf(modelo.getvRegistraEmpleado().cbMun.getSelectedItem());

        // === Conversión de estado civil ===
        String estadoSeleccionado = String.valueOf(modelo.getvRegistraEmpleado().cbEstadoCivil.getSelectedItem());
        String estadoCivil = "";

        switch (estadoSeleccionado) {
            case "Soltero/a": estadoCivil = "S"; break;
            case "Casado/a": estadoCivil = "C"; break;
            case "Divorciado/a": estadoCivil = "D"; break;
            case "Viudo/a": estadoCivil = "V"; break;
            case "Unión de Hecho": estadoCivil = "U"; break;
            default: estadoCivil = ""; break;
        }

        Object selectedItem = modelo.getvRegistraEmpleado().cbJefeInmediato.getSelectedItem();
        int idJefe;

        if (selectedItem instanceof ModeloJefeInmediato) {
            ModeloJefeInmediato jefe = (ModeloJefeInmediato) selectedItem;
            idJefe = jefe.getIdEmpleado();
        } else {
            // Si seleccionaron "Elegir Jefe"
            idJefe = 0;
        }
        Object selectedItem2 = modelo.getvRegistraEmpleado().cbPuesto.getSelectedItem();
        int idPuesto;

        if (selectedItem2 instanceof ModeloPuesto) {
            ModeloPuesto puesto = (ModeloPuesto) selectedItem2;
            idPuesto = puesto.getIdPuesto();
        } else {
            // Si seleccionaron "Elegir Puesto"
            idPuesto = 0;
        }

        String sexo = String.valueOf(modelo.getvRegistraEmpleado().cbSexo.getSelectedItem());
        ModeloEmpleado modeloEmpleado = new ModeloEmpleado();

        modeloEmpleado.setPrimerNombre(modelo.getvRegistraEmpleado().txtNom1.getText());
        modeloEmpleado.setSegundoNombre(modelo.getvRegistraEmpleado().txtNom2.getText());
        modeloEmpleado.setTercerNombre(modelo.getvRegistraEmpleado().txtNom3.getText());
        modeloEmpleado.setPrimerApellido(modelo.getvRegistraEmpleado().txtApe1.getText());
        modeloEmpleado.setSegundoApellido(modelo.getvRegistraEmpleado().txtApe2.getText());
        modeloEmpleado.setApellidoCasada(modelo.getvRegistraEmpleado().txtApeC.getText());
        modeloEmpleado.setDepartamento(departamento);
        modeloEmpleado.setMunicipio(municipio);
        modeloEmpleado.setFechaNacimiento(modelo.getvRegistraEmpleado().txtFecha.getText());
        String fechaNacimientoTexto = modelo.getvRegistraEmpleado().txtFecha.getText().trim();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        modeloEmpleado.setIdJefeInmediato(idJefe);
        System.out.println("El Puesto es: " + idPuesto);
        modeloEmpleado.setIdPuesto(idPuesto);

        try {
            LocalDate fechaNacimiento = LocalDate.parse(fechaNacimientoTexto, formatter);
            LocalDate hoy = LocalDate.now();
            int edad = Period.between(fechaNacimiento, hoy).getYears();

            modeloEmpleado.setFechaNacimiento(fechaNacimientoTexto);
            modeloEmpleado.setEdad(edad);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "La fecha debe tener el formato yyyy-MM-dd (ej: 2000-12-25)");
            return;
        }

        modeloEmpleado.setEstadoCivil(estadoCivil);
        modeloEmpleado.setSexo(sexo);
        modeloEmpleado.setCorreoElectronico(modelo.getvRegistraEmpleado().txtCorreo.getText());
        modeloEmpleado.setNumeroTelefono1(modelo.getvRegistraEmpleado().txtNum1.getText());
        modeloEmpleado.setNumeroTelefono2(modelo.getvRegistraEmpleado().txtNum2.getText()); // <-- Estabas repitiendo Num1
        modeloEmpleado.setDpi(modelo.getvRegistraEmpleado().txtDPI.getText());
        modeloEmpleado.setHorarioEntrada(modelo.getvRegistraEmpleado().txtHoraEntrada.getText());
        modeloEmpleado.setHorarioSalida(modelo.getvRegistraEmpleado().txtHoraSalida.getText());
        modeloEmpleado.setAldeaColonia(modelo.getvRegistraEmpleado().txtAldea.getText());
        modeloEmpleado.setDireccionVivienda(modelo.getvRegistraEmpleado().txtDireccion.getText());

        boolean resultado = implementacion.insertarEmpleado(modeloEmpleado, listaPlantillas);
        if (resultado) {
            limpiarCampos();
            JOptionPane.showMessageDialog(null, "Empleado registrado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Empleado no registrado", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void validarCampos() {
        if(modelo.getvRegistraEmpleado().txtNom1.getText().trim().isEmpty() ||
                modelo.getvRegistraEmpleado().txtApe1.getText().trim().isEmpty() ||
                modelo.getvRegistraEmpleado().txtApe2.getText().trim().isEmpty() ||
                modelo.getvRegistraEmpleado().txtFecha.getText().trim().isEmpty() ||
                modelo.getvRegistraEmpleado().cbEstadoCivil.getSelectedIndex() == 0 ||
                modelo.getvRegistraEmpleado().cbSexo.getSelectedIndex() == 0 ||
                modelo.getvRegistraEmpleado().txtNum1.getText().trim().isEmpty() ||
                modelo.getvRegistraEmpleado().txtDPI.getText().trim().isEmpty()||
                modelo.getvRegistraEmpleado().cbDepto.getSelectedIndex() == 0||
                modelo.getvRegistraEmpleado().cbMun.getSelectedIndex() == 0||
                modelo.getvRegistraEmpleado().cbPuesto.getSelectedIndex() == 0||
                modelo.getvRegistraEmpleado().txtHoraEntrada.getText().trim().isEmpty()||
                modelo.getvRegistraEmpleado().txtHoraSalida.getText().trim().isEmpty()) {

            if (modelo.getvRegistraEmpleado().txtNom1.getText().trim().isEmpty()) {
                modelo.getvRegistraEmpleado().lblErrorPrimerNombre.setText("*Este campo es obligatorio");
            }

            if (modelo.getvRegistraEmpleado().txtApe1.getText().trim().isEmpty()) {
                modelo.getvRegistraEmpleado().lblError1erApellido.setText("*Este campo es obligatorio");
            }
            if (modelo.getvRegistraEmpleado().txtApe2.getText().trim().isEmpty()) {
                modelo.getvRegistraEmpleado().lblError2doApellido.setText("*Este campo es obligatorio");
            }
            if (modelo.getvRegistraEmpleado().txtFecha.getText().trim().isEmpty()) {
                modelo.getvRegistraEmpleado().lblErrorFechaNacimiento.setText("*Este campo es obligatorio");
            }
            if (modelo.getvRegistraEmpleado().cbEstadoCivil.getSelectedIndex() == 0) {
                modelo.getvRegistraEmpleado().lblErrorEstado.setText("*Este campo es obligatorio");
            }
            if (modelo.getvRegistraEmpleado().cbSexo.getSelectedIndex() == 0) {
                modelo.getvRegistraEmpleado().lblErrorSexo.setText("*Este campo es obligatorio");
            }


            if (modelo.getvRegistraEmpleado().txtNum1.getText().trim().isEmpty()) {
                modelo.getvRegistraEmpleado().lblError1erTel.setText("*Este campo es obligatorio");
            }

            if (modelo.getvRegistraEmpleado().txtDPI.getText().trim().length() != 13) {
                modelo.getvRegistraEmpleado().lblErrorDpi.setText("*Este campo es obligatorio");
            }
            if (modelo.getvRegistraEmpleado().cbDepto.getSelectedIndex() == 0) {
                modelo.getvRegistraEmpleado().lblErrorDepartamento.setText("*Este campo es obligatorio");
            }
            if (modelo.getvRegistraEmpleado().cbMun.getSelectedIndex() == 0) {
                modelo.getvRegistraEmpleado().lblErrorMunicipio.setText("*Este campo es obligatorio");;
            }

            if (modelo.getvRegistraEmpleado().cbPuesto.getSelectedIndex() == 0) {
                modelo.getvRegistraEmpleado().lblErrorpuesto.setText("*Este campo es obligatorio");
            }
            if (modelo.getvRegistraEmpleado().txtHoraEntrada.getText().trim().isEmpty()) {
                modelo.getvRegistraEmpleado().lblErrorHorarioEntrada.setText("*Este campo es obligatorio");
            }
            if (modelo.getvRegistraEmpleado().txtHoraSalida.getText().trim().isEmpty()) {
                modelo.getvRegistraEmpleado().lblErrorHorarioSalida.setText("*Este campo es obligatorio");
            }
        } else if(listaPlantillas.isEmpty()){
            JOptionPane.showMessageDialog(
                    null,
                    "Debe registrar mínimo una huella para poder ingresar un cliente.",
                    "No se registraron huellas",
                    JOptionPane.ERROR_MESSAGE
            );
        } else {
            insertarEmpleado();
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
        String seleccionado = (String) modelo.getvRegistraEmpleado().cbDepto.getSelectedItem();
        modelo.getvRegistraEmpleado().cbMun.removeAllItems();
        modelo.getvRegistraEmpleado().cbMun.addItem("Elegir Municipio");

        if (municipiosPorDepartamento.containsKey(seleccionado)) {
            for (String municipio : municipiosPorDepartamento.get(seleccionado)) {
                modelo.getvRegistraEmpleado().cbMun.addItem(municipio);
            }
        }
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

    public boolean confirmarSiHuella(){
        int opcion = JOptionPane.showConfirmDialog(
                null,
                "¿Desea volver a capturar la huella?",
                "Confirmar nueva captura",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        return (opcion == JOptionPane.YES_OPTION);
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

        limpiarPanelHuella(modelo.getvRegistraEmpleado().panelH1);
        limpiarPanelHuella(modelo.getvRegistraEmpleado().panelH2);
        limpiarPanelHuella(modelo.getvRegistraEmpleado().panelH3);
        limpiarPanelHuella(modelo.getvRegistraEmpleado().panelH4);

        modelo.getvRegistraEmpleado().txtDPI.setText("");
        modelo.getvRegistraEmpleado().txtNom1.setText("");
        modelo.getvRegistraEmpleado().txtNom2.setText("");
        modelo.getvRegistraEmpleado().txtNom3.setText("");
        modelo.getvRegistraEmpleado().txtApe1.setText("");
        modelo.getvRegistraEmpleado().txtApe2.setText("");
        modelo.getvRegistraEmpleado().txtApeC.setText("");
        modelo.getvRegistraEmpleado().txtFecha.setText("");
        modelo.getvRegistraEmpleado().txtCorreo.setText("");
        modelo.getvRegistraEmpleado().txtNum1.setText("");
        modelo.getvRegistraEmpleado().txtNum2.setText("");
        modelo.getvRegistraEmpleado().txtDPI.setText("");
        modelo.getvRegistraEmpleado().txtHoraEntrada.setText("");
        modelo.getvRegistraEmpleado().txtHoraSalida.setText("");
        modelo.getvRegistraEmpleado().txtAldea.setText("");
        modelo.getvRegistraEmpleado().txtDireccion.setText("");

        modelo.getvRegistraEmpleado().cbDepto.setSelectedIndex(0);
        modelo.getvRegistraEmpleado().cbMun.setSelectedIndex(0);
        modelo.getvRegistraEmpleado().cbEstadoCivil.setSelectedIndex(0);
        modelo.getvRegistraEmpleado().cbSexo.setSelectedIndex(0);
        modelo.getvRegistraEmpleado().cbJefeInmediato.setSelectedIndex(0);
        modelo.getvRegistraEmpleado().cbPuesto.setSelectedIndex(0);

    }


    private void limpiarPanelHuella(JPanel panel) {
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
    }

}
