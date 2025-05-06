package com.umg.controlador;

import com.umg.implementacion.EmpleadoImp;
import com.umg.modelos.ModeloEmpleado;
import com.umg.modelos.ModeloVistaRegistrarEmpleado;

import javax.swing.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

public class ControladorRegistrarEmpleado implements MouseListener, ActionListener {
    EmpleadoImp implementacion = new EmpleadoImp();
    ModeloVistaRegistrarEmpleado modelo;
    private Map<String, String[]> municipiosPorDepartamento;

    
    public ControladorRegistrarEmpleado(ModeloVistaRegistrarEmpleado modelo) {
        this.modelo = modelo;
        agregarMunicipios();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals(modelo.getvRegistraEmpleado().cbDepto.getActionCommand())){
            actualizarMunicipios();
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getComponent().equals(modelo.getvRegistraEmpleado().btnRegistrarEmpleado)) {
            unirNombre();
            ModeloEmpleado modeloEmpleado = new ModeloEmpleado();
           boolean resultado =  implementacion.insertarEmpleado(modeloEmpleado);
           if (!resultado) {
               JOptionPane.showMessageDialog(null, "empleado registrado con exito", "Exito", 1);
           }else {
               JOptionPane.showMessageDialog(null, "empleado no registrado axel jodete","ERROR PUTO",0);
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
    
}
