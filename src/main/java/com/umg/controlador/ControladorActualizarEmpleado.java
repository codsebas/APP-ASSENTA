/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.umg.controlador;

import com.umg.implementacion.EmpleadoImp;
import com.umg.implementacion.PuestoImp;
import com.umg.modelos.ModeloActualizarEmpleado;
import com.umg.modelos.ModeloEmpleado;
import com.umg.modelos.ModeloJefeInmediato;
import com.umg.modelos.ModeloPuesto;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author axels
 */
public class ControladorActualizarEmpleado implements MouseListener, ActionListener {
    ModeloActualizarEmpleado modelo;
    PuestoImp implementacionPuesto = new PuestoImp();
    private Map<String, String[]> municipiosPorDepartamento;


    public ControladorActualizarEmpleado(ModeloActualizarEmpleado modelo) {
        this.modelo = modelo;
        agregarMunicipios();
    }

    public void cargarJefes(JComboBox<String> comboBox) {

        List<ModeloJefeInmediato> jefes = implementacionPuesto.obtenerJefesInmediatos();
        comboBox.removeAllItems();

        comboBox.addItem("Elegir Jefe");
        for (ModeloJefeInmediato j : jefes) {
            comboBox.addItem(String.valueOf(j));
        }
    }

    public void cargarPuestos(JComboBox<String> comboBox) {
        List<ModeloPuesto> puestos = implementacionPuesto.obtenerPuestos();
        comboBox.removeAllItems();

        comboBox.addItem("Elegir Puesto");
        for (ModeloPuesto p : puestos) {
            comboBox.addItem(p.toString());
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {
    if (e.getComponent().equals(modelo.getvActualizarEmpleado().btnActualizarEmpleado)){
actualizarEmpleado();
    } else if (e.getComponent().equals(modelo.getvActualizarEmpleado().btnBuscarEmpleado)){
        mostrarEmpleado();
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(modelo.getvActualizarEmpleado().cbDepto.getActionCommand())) {
            actualizarMunicipios();
        }


    }

    public void mostrarEmpleado() { String dpi = modelo.getvActualizarEmpleado().txtDPI.getText();

        if (dpi == null || dpi.isEmpty()) {
            JOptionPane.showMessageDialog(null, "⚠️ Por favor, ingresa un DPI.");
            return;
        }

        EmpleadoImp imp = new EmpleadoImp();
        ModeloEmpleado empleado = imp.mostrarEmpleadoPorDpi(dpi);

        if (empleado == null) {
            JOptionPane.showMessageDialog(null, "❌ No se encontró un empleado con ese DPI.");
            return;
        }

        var vista = modelo.getvActualizarEmpleado();
        // Aquí obtenemos el valor de estado civil de la base de datos (que ya viene como 'C', 'S', etc.)
        String estadoCivilBD = empleado.getEstadoCivil();

        // Convertir el estado civil de la base de datos a la forma que el JComboBox espera
        String estadoCivil = "";
        switch (estadoCivilBD) {
            case "C":
                estadoCivil = "Casado/a"; break;
            case "S":
                estadoCivil = "Soltero/a"; break;
            case "D":
                estadoCivil = "Divorciado/a"; break;
            case "V":
                estadoCivil = "Viudo/a"; break;
            case "U":
                estadoCivil = "Unión de Hecho"; break;
            default:
                estadoCivil = "Desconocido"; break;
        }

        // Ahora seleccionamos el item correcto en el combo box
        vista.cbEstadoCivil.setSelectedItem(estadoCivil); // Aquí asignamos el estado civil al combo
        // Llenar campos con la información recuperada
        vista.cbSexo.setSelectedItem(empleado.getSexo());

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

        // Si estás usando JComboBox con los nombres visibles, seleccionamos por nombre:
        vista.cbPuesto.setSelectedItem(empleado.getNombrePuesto());
        vista.cbJefeInmediato.setSelectedItem(empleado.getNombreJefeInmediato());


        // Si tu vista también tiene los datos de dirección (esto dependerá de cómo llenes esos ComboBox)


        vista.cbDepto.setSelectedItem(empleado.getDepartamento());
        vista.cbMun.setSelectedItem(empleado.getMunicipio());
        vista.txtAldea.setText(empleado.getAldeaColonia());
        vista.txtDireccion.setText(empleado.getDireccionVivienda());

        JOptionPane.showMessageDialog(null, "✅ Datos del empleado cargados.");
    }

    public void validarEstadoCivil(){

    }

    public void actualizarEmpleado(){
        EmpleadoImp imp = new EmpleadoImp();
        ModeloEmpleado empleado = new ModeloEmpleado();

        try {
            var vista = modelo.getvActualizarEmpleado();

            empleado.setIdEmpleado(Integer.parseInt(vista.txtDPI.getText())); // Suponiendo que ID está en txtDPI (¿seguro? suele ser otro campo)
            empleado.setDpi(vista.txtDPI.getText());
            empleado.setSexo((String) vista.cbSexo.getSelectedItem());
            empleado.setEstadoCivil((String) vista.cbEstadoCivil.getSelectedItem());
            empleado.setPrimerNombre(vista.txtNom1.getText());
            empleado.setSegundoNombre(vista.txtNom2.getText());
            empleado.setTercerNombre(vista.txtNom3.getText());
            empleado.setPrimerApellido(vista.txtApe1.getText());
            empleado.setSegundoApellido(vista.txtApe2.getText());
            empleado.setApellidoCasada(vista.txtApeC.getText());
            empleado.setFechaNacimiento(vista.txtFecha.getText());
            // La edad deberías calcularla automáticamente por fecha de nacimiento, pero si está en un campo:
            // empleado.setEdad(Integer.parseInt(vista.txtEdad.getText()));  // Si tienes txtEdad
            empleado.setCorreoElectronico(vista.txtCorreo.getText());
            empleado.setNumeroTelefono1(vista.txtNum1.getText());
            empleado.setNumeroTelefono2(vista.txtNum2.getText());

            // Estos deberían venir de cbPuesto, cbJefeInmediato, etc.
            empleado.setIdPuesto(Integer.parseInt((String) vista.cbPuesto.getSelectedItem()));
            empleado.setHorarioEntrada(vista.txtHoraEntrada.getText());
            empleado.setHorarioSalida(vista.txtHoraSalida.getText());
            empleado.setIdJefeInmediato(Integer.parseInt((String) vista.cbJefeInmediato.getSelectedItem()));

            // Dirección
            empleado.setDepartamento((String) vista.cbDepto.getSelectedItem());
            empleado.setMunicipio((String) vista.cbMun.getSelectedItem());
            empleado.setAldeaColonia(vista.txtAldea.getText());
            empleado.setDireccionVivienda(vista.txtDireccion.getText());

            // Datos de huella (placeholder)
            empleado.setIdHuella(1);
            empleado.setHuella(new byte[0]);

            // El ID de dirección parece faltar: podrías obtenerlo desde algún campo oculto si lo tienes
            empleado.setIdDireccion(0); // cambiar si tienes un campo para esto

            boolean resultado = imp.actualizarEmpleado(empleado);

            if (resultado) {
                JOptionPane.showMessageDialog(null, "✅ Empleado actualizado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "❌ No se pudo actualizar el empleado.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "⚠️ Error de formato numérico: " + ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "⚠️ Error al actualizar empleado: " + ex.getMessage());
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
}


