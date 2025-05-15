package com.umg.implementacion;

import com.digitalpersona.uareu.Fmd;
import com.umg.interfaces.IEmpleados;
import com.umg.modelos.ModeloEmpleado;
import com.umg.modelos.ModeloHuella;
import com.umg.modelos.ModeloJefeInmediato;
import com.umg.modelos.ModeloPuesto;
import com.umg.sql.Conector;
import com.umg.sql.Sql;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoImp implements IEmpleados {

    Conector conector = new Conector();
    Sql sql = new Sql();
    PreparedStatement ps;
    ResultSet rs;


    private void setNullableString(PreparedStatement ps, int index, String valor) throws SQLException {
        if (valor == null || valor.trim().isEmpty()) {
            ps.setNull(index, Types.VARCHAR);
        } else {
            ps.setString(index, valor);
        }
    }
    private void setNullableTime(PreparedStatement ps, int index, String timeStr) throws SQLException {
        if (timeStr == null || timeStr.trim().isEmpty()) {
            ps.setNull(index, java.sql.Types.TIME);
        } else {
            ps.setTime(index, Time.valueOf(timeStr));
        }
    }

    @Override
    public boolean insertarEmpleado(ModeloEmpleado modelo, List<Fmd> listaFmd) {
        boolean resultado = false;
        conector.conectar();
        int id_empleado = -1;

        try {
            // Insertar empleado
            ps = conector.preparar(sql.getINSERTAR_EMPLEADO());
            ps.setString(1, modelo.getDpi());
            ps.setString(2, modelo.getSexo());
            ps.setString(3, modelo.getEstadoCivil());
            ps.setString(4, modelo.getPrimerNombre());
            setNullableString(ps, 5, modelo.getSegundoNombre());
            setNullableString(ps, 6, modelo.getTercerNombre());
            ps.setString(7, modelo.getPrimerApellido());
            ps.setString(8, modelo.getSegundoApellido());
            setNullableString(ps, 9, modelo.getApellidoCasada());
            ps.setDate(10, Date.valueOf(modelo.getFechaNacimiento()));
            ps.setInt(11, modelo.getEdad());
            ps.setInt(12, modelo.getIdPuesto());
            setNullableString(ps, 13, modelo.getCorreoElectronico());
            ps.setString(14, modelo.getNumeroTelefono1());
            setNullableString(ps, 15, modelo.getNumeroTelefono2());
            setNullableTime(ps, 16, modelo.getHorarioEntrada());
            setNullableTime(ps, 17, modelo.getHorarioSalida());
            ps.setInt(18, modelo.getIdJefeInmediato());

            rs = ps.executeQuery();
            if (rs.next()) {
                id_empleado = rs.getInt("id_empleado");
            } else {
                throw new SQLException("No se pudo obtener el ID del empleado insertado.");
            }

            // Insertar dirección
            ps = conector.preparar(sql.getINSERTAR_DIRECCION());
            ps.setInt(1, id_empleado);
            ps.setString(2, modelo.getDepartamento());
            ps.setString(3, modelo.getMunicipio());
            ps.setString(4, modelo.getAldeaColonia());
            ps.setString(5, modelo.getDireccionVivienda());
            ps.executeUpdate();

            // Insertar huellas
            resultado = guardarHuella(armarModelo(id_empleado, listaFmd));

        } catch (SQLException ex) {
            conector.mensaje("Error: " + ex.getMessage(), "Error al ingresar", 0);
            resultado = false;
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                conector.desconectar();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }

        return resultado;
    }

    @Override
    public boolean eliminarEmpleado(String dpi_empleado) {
        boolean resultado = true;
        conector.conectar();
        try {
            ps = conector.preparar(sql.getELIMINAR_EMPLEADO());
            ps.setInt(1, Integer.parseInt(dpi_empleado));
            return ps.execute();

        } catch (SQLException ex) {
            conector.mensaje("No se pudo eliminar el empleado", "Error al eliminar", 0);
            return resultado;
        }

    }

    @Override
    public DefaultTableModel modeloEmpleado() {
        DefaultTableModel tabla = new DefaultTableModel();

        try {
            conector.conectar(); //
            ps = conector.preparar(sql.getCONSULTA_TODOS_EMPLEADO());
            rs = ps.executeQuery();


            tabla.setColumnIdentifiers(new Object[]{"DPI", "Sexo", "Estado Civil", "1er.Nombre", "2do.Nombre", "3er.Nombre", "1er.Apellido",
                    "2do.Apellido", "Apellido de Casada", "Edad", "Puesto", "Email", "telefono", "Hor-Entrada", "Hor-Salida", "Jefe"});

            while (rs.next()) {
                tabla.addRow(new Object[]{
                        rs.getString("dpi_empleado"),
                        rs.getString("sexo_empleado"),
                        rs.getString("estado_civil"),
                        rs.getString("nombre1_empleado"),
                        rs.getString("nombre2_empleado"),
                        rs.getString("nombre3_empleado"),
                        rs.getString("apellido1_empleado"),
                        rs.getString("apellido2_empleado"),
                        rs.getString("apellidocasada_empleado"),
                        rs.getInt("edad_empleado"),
                        rs.getString("nombre_puesto"),
                        rs.getString("email_empleado"),
                        rs.getString("telefono1_empleado"),
                        rs.getString("horario_entrada"),
                        rs.getString("horario_salida"),
                        rs.getString("jefe_inmediato_nombre")
                });

            }


        } catch (SQLException ex) {
        }
        return tabla;
}

    @Override
    public DefaultTableModel modeloEmpleado(int dpi_empleado) {
        return null;
    }

    public boolean eliminarEmpleadoPorDPI(String dpi) {
        boolean eliminado = false;
        conector.conectar();

        try {
            // 1. Obtener id_empleado
            ps = conector.preparar(sql.getOBTENER_ID_EMPLEADO_POR_DPI_ELIMINAR());
            ps.setString(1, dpi);
            rs = ps.executeQuery();

            if (rs.next()) {
                int idEmpleado = rs.getInt("id_empleado");

                // 2. Eliminar de huella
                ps = conector.preparar(sql.getELIMINAR_HUELLA_ELIMINAR());
                ps.setInt(1, idEmpleado);
                ps.executeUpdate();

                // 3. Eliminar de direccion_empleado
                ps = conector.preparar(sql.getELIMINAR_DIRECCION_ELIMINAR());
                ps.setInt(1, idEmpleado);
                ps.executeUpdate();

                // 4. Eliminar de usuarios
                ps = conector.preparar(sql.getELIMINAR_USUARIO_ELIMINAR());
                ps.setString(1, dpi);
                ps.executeUpdate();

                // 5. Eliminar de empleado
                ps = conector.preparar(sql.getELIMINAR_EMPLEADO_ELIMINAR());
                ps.setString(1, dpi);
                ps.executeUpdate();

                eliminado = true;
            } else {
                conector.mensaje("No se encontró un empleado con ese DPI.", "Error", 0);
            }

            conector.desconectar();
        } catch (SQLException ex) {
            conector.mensaje("Error al eliminar empleado: " + ex.getMessage(), "Error SQL", 0);
            conector.desconectar();
        }

        return eliminado;
    }

    @Override
    public ModeloEmpleado mostrarEmpleado(String dpi_empleado) {
        conector.conectar();
        ModeloEmpleado modelo = null; // <- Aquí importante: empieza como null

        try {
            ps = conector.preparar(sql.getCONSULTA_EMPLEADO_DPI()); // SELECT * FROM empleado WHERE dpi_empleado = ?
            ps.setString(1, dpi_empleado);
            rs = ps.executeQuery();

            if (rs.next()) {
                modelo = new ModeloEmpleado(); // Solo si encuentra resultado

                modelo.setDpi(rs.getString(2));                           // dpi_empleado
                modelo.setSexo(rs.getString(3));                          // sexo_empleado
                modelo.setEstadoCivil(rs.getString(4));                   // estado_civil
                modelo.setPrimerNombre(rs.getString(5));                  // nombre1_empleado
                modelo.setSegundoNombre(rs.getString(6));                 // nombre2_empleado
                modelo.setTercerNombre(rs.getString(7));                  // nombre3_empleado
                modelo.setPrimerApellido(rs.getString(8));                // apellido1_empleado
                modelo.setSegundoApellido(rs.getString(9));               // apellido2_empleado
                modelo.setApellidoCasada(rs.getString(10));               // apellidocasada_empleado
                modelo.setFechaNacimiento(rs.getString(11));              // fec_nacimiento
                modelo.setEdad(rs.getInt(12));                            // edad_empleado
                modelo.setNombrePuesto(rs.getString(13));                 // nombre_puesto (viene del JOIN)
                modelo.setCorreoElectronico(rs.getString(14));            // email_empleado
                modelo.setNumeroTelefono1(rs.getString(15));              // telefono1_empleado
                modelo.setNumeroTelefono2(rs.getString(16));              // telefono2_empleado
                modelo.setHorarioEntrada(rs.getString(17));               // horario_entrada
                modelo.setHorarioSalida(rs.getString(18));                // horario_salida
                modelo.setNombreJefeInmediato(rs.getString(19));          // nombre del jefe (concat del JOIN)
     // jefe_inmediato_id

            }

            conector.desconectar();
        } catch (SQLException ex) {
            conector.mensaje(ex.getMessage(), "Error al mostrar", 0);
            conector.desconectar();
        }

        return modelo;
    }

    public String obtenerNombrePuestoDesdeBD(int idPuesto) {
        String nombrePuesto = "No encontrado"; // Para detectar si no devuelve datos
        conector.conectar();

        try {
            String sql = "SELECT nombre_puesto FROM puesto WHERE id_puesto = ?";
            PreparedStatement stmt = conector.preparar(sql);
            stmt.setInt(1, idPuesto);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                nombrePuesto = rs.getString("nombre_puesto");
            } else {
                System.out.println(" No se encontró el puesto para ID: " + idPuesto);
            }
        } catch (SQLException ex) {
            System.out.println("Error al obtener el nombre del puesto: " + ex.getMessage());
        } finally {
            conector.desconectar();
        }

        return nombrePuesto;
    }

    public String obtenerNombreJefeDesdeBD(int idJefe) {
        String nombreJefe = "No encontrado"; // Valor predeterminado en caso de que no exista
        conector.conectar();

        try {
            String sql = "SELECT CONCAT(nombre1_empleado, ' ', apellido1_empleado) AS nombre_jefe " +
                    "FROM empleado WHERE id_empleado = ?";
            PreparedStatement stmt = conector.preparar(sql);
            stmt.setInt(1, idJefe);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                nombreJefe = rs.getString("nombre_jefe");
            } else {
                System.out.println(" No se encontró un jefe con ID: " + idJefe);
            }
        } catch (SQLException ex) {
            System.out.println("Error al obtener el nombre del jefe: " + ex.getMessage());
        } finally {
            conector.desconectar();
        }

        return nombreJefe;
    }



    @Override
    public ModeloEmpleado mostrarEmpleadoPorDpi(String dpi_empleado) {
        conector.conectar();
        ModeloEmpleado modelo = null; // Empezamos como null

        try {
            // Usamos la consulta SQL definida en tu clase SQL
            ps = conector.preparar(sql.getCONSULTA_EMPLEADO_DPIUPD() ); // Selecciona el empleado con el DPI dado
            ps.setString(1, dpi_empleado);
            rs = ps.executeQuery();

            if (rs.next()) {
                modelo = new ModeloEmpleado(); // Solo si encuentra un resultado

                // Llenamos el objeto modelo con los datos recuperados
                modelo.setIdEmpleado(rs.getInt("id_empleado"));
                modelo.setDpi(rs.getString("dpi_empleado"));              // dpi_empleado
                modelo.setSexo(rs.getString("sexo_empleado"));            // sexo_empleado
                modelo.setEstadoCivil(rs.getString("estado_civil"));     // estado_civil
                modelo.setPrimerNombre(rs.getString("nombre1_empleado")); // nombre1_empleado
                modelo.setSegundoNombre(rs.getString("nombre2_empleado")); // nombre2_empleado
                modelo.setTercerNombre(rs.getString("nombre3_empleado")); // nombre3_empleado
                modelo.setPrimerApellido(rs.getString("apellido1_empleado")); // apellido1_empleado
                modelo.setSegundoApellido(rs.getString("apellido2_empleado")); // apellido2_empleado
                modelo.setApellidoCasada(rs.getString("apellidocasada_empleado")); // apellidocasada_empleado
                modelo.setFechaNacimiento(rs.getString("fec_nacimiento")); // fec_nacimiento
                modelo.setEdad(rs.getInt("edad_empleado"));
                modelo.setDepartamento(rs.getString( "departamento" ));
                modelo.setMunicipio(rs.getString("municipio"));
                modelo.setAldeaColonia(rs.getString("aldea"));
                modelo.setDireccionVivienda(rs.getString("direccion"));
                modelo.setIdPuesto(rs.getInt("id_puesto"));
                modelo.setIdJefeInmediato(rs.getInt("id_jefe_inmediato")); // posible error aqui
                modelo.setNombrePuesto(rs.getString("nombre_puesto"));   // nombre_puesto (viene del JOIN)
                modelo.setCorreoElectronico(rs.getString("email_empleado")); // email_empleado
                modelo.setNumeroTelefono1(rs.getString("telefono1_empleado")); // telefono1_empleado
                modelo.setNumeroTelefono2(rs.getString("telefono2_empleado")); // telefono2_empleado
                modelo.setHorarioEntrada(rs.getString("horario_entrada")); // horario_entrada
                modelo.setHorarioSalida(rs.getString("horario_salida")); // horario_salida
                modelo.setNombreJefeInmediato(rs.getString("nombre_jefe_inmediato")); // nombre_jefe_inmediato
                System.out.println("Nombre del puesto desde BD: " + rs.getString("nombre_puesto"));
                System.out.println("Nombre del jefe inmediato desde BD: " + rs.getString("nombre_jefe_inmediato"));
            }

            conector.desconectar();
        } catch (SQLException ex) {
            conector.mensaje(ex.getMessage(), "Error al mostrar empleado", 0);
            conector.desconectar();
        }

        return modelo;
    }

    private void setNullableInt(PreparedStatement ps, int index, int valor) throws SQLException {
        if (valor == 0) {
            ps.setNull(index, java.sql.Types.INTEGER);
        } else {
            ps.setInt(index, valor);
        }
    }




    @Override
    public boolean actualizarEmpleado(ModeloEmpleado modelo, List<Fmd> listaFmd) {
        boolean resultado = false;
        conector.conectar();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Actualizar datos del empleado
            ps = conector.preparar(sql.getACTUALIZAR_EMPLEADO());

            ps.setString(1, modelo.getSexo());
            ps.setString(2, modelo.getEstadoCivil());
            ps.setString(3, modelo.getPrimerNombre());
            setNullableString(ps, 4, modelo.getSegundoNombre());
            setNullableString(ps, 5, modelo.getTercerNombre());
            ps.setString(6, modelo.getPrimerApellido());
            ps.setString(7, modelo.getSegundoApellido());
            setNullableString(ps, 8, modelo.getApellidoCasada());

            try {
                Date fecha = Date.valueOf(modelo.getFechaNacimiento().trim());
                ps.setDate(9, fecha);
            } catch (IllegalArgumentException e) {
                conector.mensaje("Fecha inválida. Usa el formato yyyy-MM-dd", "Error de Fecha", 0);
                return false;
            }

            ps.setInt(10, modelo.getEdad());
            setNullableInt(ps, 11, modelo.getIdPuesto());
            setNullableString(ps, 12, modelo.getCorreoElectronico());
            ps.setString(13, modelo.getNumeroTelefono1());
            setNullableString(ps, 14, modelo.getNumeroTelefono2());
            setNullableTime(ps, 15, modelo.getHorarioEntrada());
            setNullableTime(ps, 16, modelo.getHorarioSalida());
            setNullableInt(ps, 17, modelo.getIdJefeInmediato());
            ps.setString(18, modelo.getDpi());

            int filasAfectadas = ps.executeUpdate();
            ps.close(); // <-- cierro después de usarlo

            if (filasAfectadas > 0) {
                int id_empleado = modelo.getIdEmpleado();
                if (id_empleado == 0) {
                    ps = conector.preparar("SELECT id_empleado FROM empleado WHERE dpi_empleado = ?");
                    ps.setString(1, modelo.getDpi());
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        id_empleado = rs.getInt("id_empleado");
                    }
                    rs.close();
                    ps.close();
                }

                // Actualizar dirección
                ps = conector.preparar(sql.getACTUALIZAR_DIRECCION());
                ps.setString(1, modelo.getDepartamento());
                ps.setString(2, modelo.getMunicipio());
                setNullableString(ps, 3, modelo.getAldeaColonia());
                setNullableString(ps, 4, modelo.getDireccionVivienda());
                ps.setInt(5, id_empleado);
                ps.executeUpdate();
                ps.close();

                // Eliminar huellas anteriores
                ps = conector.preparar(sql.getELIMINAR_HUELLA_ELIMINAR());
                ps.setInt(1, id_empleado);
                ps.executeUpdate();
                ps.close();

                // Guardar nuevas huellas
                resultado = guardarHuella(armarModelo(id_empleado, listaFmd));
            } else {
                conector.mensaje("No se encontró el empleado para actualizar.", "Error", 0);
            }
        } catch (SQLException ex) {
            conector.mensaje("Error al actualizar empleado: " + ex.getMessage(), "Error SQL", 0);
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException e) {
                System.err.println("Error cerrando recursos: " + e.getMessage());
            }
            conector.desconectar();
        }
        return resultado;
    }


    @Override
    public boolean eliminarEmpleado(ModeloEmpleado modelo) {
        return false;
    }

    public ModeloEmpleado mostrarNombreApellidoPuestoPorDPI(String dpi_empleado) {
        conector.conectar();
        ModeloEmpleado modelo = null;

        try {
            ps = conector.preparar(sql.getCONSULTA_EMPLEADO_DPI_ELIMINAR());
            ps.setString(1, dpi_empleado);
            rs = ps.executeQuery();

            if (rs.next()) {
                modelo = new ModeloEmpleado();
                modelo.setPrimerNombre(rs.getString("nombre1_empleado"));
                modelo.setPrimerApellido(rs.getString("apellido1_empleado"));
                modelo.setNombrePuesto(rs.getString("nombre_puesto"));
            }

        } catch (SQLException ex) {
            conector.mensaje(ex.getMessage(), "Error al mostrar empleado", 0);
        } finally {
            conector.desconectar();
        }

        return modelo; // Devuelve el modelo con los datos del empleado
    }

    public List<ModeloPuesto> obtenerPuestos() {
        List<ModeloPuesto> lista = new ArrayList<>();
        conector.conectar();
        try {
            PreparedStatement stmt = conector.preparar(sql.getCONSULTA_TODOS_PUESTOS());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ModeloPuesto puesto = new ModeloPuesto(
                        rs.getInt("id_puesto"),
                        rs.getString("nombre_puesto")
                );
                lista.add(puesto);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            conector.mensaje("Error al obtener puestos: " + e.getMessage(), "Error", 0);
        } finally {
            conector.desconectar();
        }
        return lista;
    }

    public List<ModeloJefeInmediato> obtenerJefesInmediatos() {
        List<ModeloJefeInmediato> lista = new ArrayList<>();
        conector.conectar();
        try {
            PreparedStatement stmt = conector.preparar(sql.getCONSULTAR_TODOS_JEFES_INMEDIATOS());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ModeloJefeInmediato jefe = new ModeloJefeInmediato(
                        rs.getInt("id_empleado"),
                        rs.getString("nombre_completo")
                );
                lista.add(jefe);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            conector.mensaje("Error al obtener jefes inmediatos: " + e.getMessage(), "Error", 0);
        } finally {
            conector.desconectar();
        }
        return lista;

    }
    @Override
    public DefaultTableModel modeloEmpleado(String dpi) {
        return null;
    }

    @Override
    public DefaultTableModel mostrarTodosLosEmpleados() {
        return null;
    }

    @Override
    public boolean guardarHuella(List<ModeloHuella> modelo) {
        conector.conectar();
        boolean resultado = false;
        try{
            for(ModeloHuella huella : modelo){
                ps = conector.preparar(sql.getINSERTAR_HUELLA_EMPLEADO());
                ps.setInt(1, huella.getId());
                ps.setBytes(2,huella.getFmd().getData());
                int filas = ps.executeUpdate();
                if(filas > 0){
                    resultado = true;
                } else {
                    resultado = false;
                    break;
                }
            }
        } catch (SQLException e) {
            conector.mensaje("Error al guardar huella: " + e.getMessage(), "Error", 0);
            resultado = false;
        } finally {
            conector.desconectar();
        }
        return resultado;
    }

    public List<ModeloHuella> armarModelo(int id_empleado, List<Fmd> huellas) {
        List<ModeloHuella> listaModelo = new ArrayList<>();

        for (Fmd huella : huellas) {
            if(huella != null){
                ModeloHuella modelo = new ModeloHuella(id_empleado, huella);
                listaModelo.add(modelo);
            }
        }

        return listaModelo;
    }
}
