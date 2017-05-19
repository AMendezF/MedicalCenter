package clases;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Alejandro
 */
public class Gestor {

//    private final int maxMedicosActivos = 3;
    private final Conexion conexion;

    /**
     * Constructor de gestor.
     *
     * @param conexion Llave para comunicar con la BD.
     */
    public Gestor(Conexion conexion) {
        this.conexion = conexion;
    }

    /**
     * Muestra las citas de un día completo organizadas por horario y salas de
     * especialidad.
     *
     * @param fecha Día del que quieres obtener la agenda.
     * @return Retorna todo un string identado con la agenda al completo.
     * @throws SQLException Devuelve error si no se pudo realizar la consulta
     * SQL.
     */
    public String mostrarAgenda(String fecha) throws SQLException {
        return mostrarAgendaMatinal(fecha) + mostrarAgendaVespertina(fecha);
    }

    /**
     * Muestra las citas matinales de un día organizadas por horario y salas de
     * especialidad.
     *
     * @param fecha Día del que quieres obtener la agenda.
     * @return Retorna todo un string identado con la agenda matinal.
     * @throws SQLException Devuelve error si no se pudo realizar la consulta
     * SQL.
     */
    public String mostrarAgendaMatinal(String fecha) throws SQLException {
        String agenda = "";
        String[] especialidades;
        String sql;
        ResultSet resultSet;

        boolean tieneEspecialidadSala; // Chapuzilla para asignar sala
        int numSala = 0; // Parte de la chapuzilla

        especialidades = getEspecialidades();
        for (String especialidad : especialidades) {
            sql = "SELECT c.hora, c.paciente, m.nombre, m.apellidos "
                    + "FROM centromedico.citas c, centromedico.medico m, centromedico.especialidad e "
                    + "WHERE e.nombre='" + especialidad + "' "
                    + "AND c.especialidad=e.cod_especialidad "
                    + "AND c.medico=m.n_colegiado "
                    + "AND c.hora<'13:00:00' "
                    + "AND c.dia='" + fecha + "' "
                    + "ORDER BY c.hora";
            resultSet = conexion.makeQuery(sql);

            tieneEspecialidadSala = false;
            while (resultSet.next()) {
                if (!tieneEspecialidadSala) {
                    numSala++;
                    agenda += "Sala " + numSala + ": " + especialidad + "\n";
                    tieneEspecialidadSala = true;
                }
                agenda += "    " + resultSet.getString("hora") + " "
                        + "DNI: " + resultSet.getString("paciente") + " "
                        + "con el médico: "
                        + resultSet.getString("nombre") + " "
                        + resultSet.getString("apellidos") + "\n";
            }
        }
        return agenda;
    }

    /**
     * Muestra las citas vespertinas de un día organizadas por horario y salas
     * de especialidad.
     *
     * @param fecha Día del que quieres obtener la agenda.
     * @return Retorna todo un string identado con la agenda vespertina.
     * @throws SQLException Devuelve error si no se pudo realizar la consulta
     * SQL.
     */
    public String mostrarAgendaVespertina(String fecha) throws SQLException {
        String agenda = "";
        String[] especialidades;
        String sql;
        ResultSet resultSet;

        boolean tieneEspecialidadSala; // Chapuzilla para asignar sala
        int numSala = 0; // Parte de la chapuzilla

        especialidades = getEspecialidades();
        for (String especialidad : especialidades) {
            sql = "SELECT c.hora, c.paciente, m.nombre, m.apellidos "
                    + "FROM centromedico.citas c, centromedico.medico m, centromedico.especialidad e "
                    + "WHERE e.nombre='" + especialidad + "' "
                    + "AND c.especialidad=e.cod_especialidad "
                    + "AND c.medico=m.n_colegiado "
                    + "AND c.hora>='13:00:00' "
                    + "AND c.dia='" + fecha + "' "
                    + "ORDER BY c.hora";
            resultSet = conexion.makeQuery(sql);

            tieneEspecialidadSala = false;
            while (resultSet.next()) {
                if (!tieneEspecialidadSala) {
                    numSala++;
                    agenda += "Sala " + numSala + ": " + especialidad + "\n";
                    tieneEspecialidadSala = true;
                }
                agenda += "    " + resultSet.getString("hora") + " "
                        + "DNI: " + resultSet.getString("paciente") + " "
                        + "con el médico: "
                        + resultSet.getString("nombre") + " "
                        + resultSet.getString("apellidos") + "\n";
            }
        }
        return agenda;
    }

    /**
     * Obtiene todas las especialidades por nombre, que contiene la BD.
     *
     * @return String[] que contiene cada una de las especialidades.
     * @throws SQLException Devuelve error si no se pudo realizar la consulta
     * SQL.
     */
    public String[] getEspecialidades() throws SQLException {
        ArrayList<String> especialidades = new ArrayList<>();
        String sql;
        ResultSet resultSet;

        sql = "SELECT nombre "
                + "FROM centromedico.especialidad";
        resultSet = conexion.makeQuery(sql);

        while (resultSet.next()) {
            especialidades.add(resultSet.getString("nombre"));
        }
        return especialidades.toArray(new String[especialidades.size()]);
    }

    /**
     * Devuelve todos los médicos que contiene la base de datos.
     *
     * @return Retorna el valor de la consulta SQL.
     * @throws SQLException Devuelve error si no se pudo realizar la consulta
     * SQL.
     */
    public ResultSet mostrarMedicos() throws SQLException {
        String sql;
        ResultSet listaMedicos;

        sql = "SELECT * "
                + "FROM centromedico.medico";
        listaMedicos = this.conexion.makeQuery(sql);

        return listaMedicos;
    }

    /**
     * Comprueba si existe un médico en la BD con el número de colegiado
     * entregado.
     *
     * @param numColegiado Número de colegiado que se pretende comprobar en la
     * BD.
     * @return true = Número de colegiado encontrado, false = Número de
     * colegiado no encontrado
     * @throws SQLException Devuelve error si no se pudo realizar la consulta
     * SQL.
     */
    public boolean existeMedico(String numColegiado) throws SQLException {
        boolean result = false;
        String sql;
        ResultSet resultSet;

        sql = "SELECT n_colegiado "
                + "FROM centromedico.medico "
                + "WHERE n_colegiado='" + numColegiado + "'";
        resultSet = conexion.makeQuery(sql);

        while (resultSet.next()) {
            if (numColegiado.equals(resultSet.getString("n_colegiado"))) {
                result = true;
            }
        }

        return result;
    }

    /**
     * Inserta un nuevo médico en la base de datos.
     *
     * @param medico Recibe un array con los datos del médico. El formato con el
     * que se ha de rellenar el array es {n_colegiado, nombre, apellidos,
     * horario, tiempo_min, especialidad}.
     *
     * @return true = añadido a la BD, false = fallo al añadir
     */
    public boolean addMedico(String[] medico) {
        boolean added = true;
        String sql;
        String codMedico = medico[0];

        sql = "INSERT "
                + "INTO centromedico.medico "
                + "(n_colegiado, nombre, apellidos, horario, tiempo_min, especialidad)"
                + "VALUES "
                + "('" + codMedico + "', "
                + "'" + medico[1] + "', "
                + "'" + medico[2] + "', "
                + "'" + medico[3] + "', "
                + "'" + medico[4] + "', "
                + "'" + medico[5] + "')";
        try {
            conexion.makeUpdate(sql);
        } catch (SQLException e) {
            added = false;
        }

        // TODO
//        try {
//            if(!conexion.existeUser(codMedico)){
//                conexion.crearUserBD(codMedico);
//                conexion.setPermisosBD(codMedico);
//                
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(Gestor.class.getName()).log(Level.SEVERE, null, ex);
//        }
        return added;
    }

    /**
     * Actualiza el médico, en parte o en su totalidad, en la base de datos.
     *
     * @param numColegiado Recibe el dni del paciente del cual se quiere
     * modificar los datos.
     * @param medico Recibe un array de 'tuplas' (arrays de 2 elementos) con los
     * datos que se desean actualizar del médico. La estructura de cada 'tupla'
     * debe ser {campo, valor}. Los campos disponibles actualmente para
     * actualizar son: [n_colegiado, nombre, apellidos, horario, tiempo_min,
     * especialidad].
     * @return true = actualizado, false = fallo al actualizar
     */
    public boolean updateMedico(String numColegiado, String[][] medico) {
        boolean updated = true;
        String sql;

        sql = "UPDATE centromedico.medico "
                + "SET ";
        for (String[] tuplas : medico) {
            sql += tuplas[0] + "='" + tuplas[1] + "' ";
        }
        sql += "WHERE DNI='" + numColegiado + "'";
        try {
            conexion.makeUpdate(sql);
        } catch (SQLException e) {
            updated = false;
        }

        return updated;
    }

    /**
     * Devuelve un médico completo presente en la BD.
     *
     * @param numColegiado Número identificativo del médico que se desea
     * obtener.
     * @return Retorna el Paciente con toda su información.
     * @throws SQLException Devuelve error si no se pudo recuperar el paciente
     * de la BD.
     */
    /*
    public Medico getMedico(String numColegiado) throws SQLException {
        return new Medico(Integer.parseInt(numColegiado), conexion);
    }
     */
    /**
     * Sustituye un médico por otro.
     *
     * @param numColegiado Número identificativo del médico que se desea
     * sustituir.
     * @param medicoSustito Número identificativo del médico que sustituye al
     * otro médico.
     * @return true = añadido a la BD, false = fallo al añadir
     */
    public Boolean sustituirMedico(String numColegiado, String[] medicoSustito) {
        Boolean sustituido = true;
        //TODO

        return sustituido;
    }

    /**
     * Elimina, de la BD, el médico especificado por parámetro.
     *
     * @param numColegiado Número identificativo del médico que se desea borrar.
     * @return true = borrado de la BD, false = fallo al borrar
     */
    public Boolean removeMedico(String numColegiado) {
        Boolean removed = true;
        String sql;

        sql = "DELETE FROM centromedico.medico "
                + "WHERE N_colegiado='" + numColegiado + "'";

        try {
            conexion.makeQuery(sql);
        } catch (SQLException e) {
            removed = false;
        }

        return removed;
    }

    /**
     * Traslada las citas que posee un medico a un médico sustituto.
     *
     * @param numColegiado Número identificativo del médico que posee
     * actualmente las citas.
     * @param numColegiadoSustituto Número identificativo del médico sustituto
     * que recibe las citas.
     * @return true = trasladadas con éxito, false = fallo al trasladar
     */
    public Boolean trasladarCitas(String numColegiado, String numColegiadoSustituto) {
        Boolean trasladado = true;
        String sql;

        sql = "UPDATE centromedico.citas"
                + "SET medico='" + numColegiado + "' "
                + "WHERE medico='" + numColegiadoSustituto + "'";
        try {
            this.conexion.makeQuery(sql);
        } catch (SQLException e) {
            trasladado = false;
        }

        return trasladado;
    }

    /**
     * Devuelve todos los pacientes que contiene la base de datos.
     *
     * @return Retorna el valor de la consulta SQL.
     * @throws SQLException Devuelve error si no se pudo realizar la consulta
     * SQL.
     */
    public ResultSet mostrarPacientes() throws SQLException {
        String sql;
        ResultSet listaPacientes;

        sql = "SELECT * "
                + "FROM centromedico.paciente "
                + "ORDER BY DNI";
        listaPacientes = this.conexion.makeQuery(sql);

        return listaPacientes;
    }

    /**
     * Devuelve todos los pacientes borrados que contiene la base de datos.
     *
     * @return Retorna el valor de la consulta SQL.
     * @throws SQLException Devuelve error si no se pudo realizar la consulta
     * SQL.
     */
    public ResultSet mostrarPacientesBorrados() throws SQLException {
        String sql;
        ResultSet listaPacientes;

        sql = "SELECT * "
                + "FROM centromedico.paciente_borrado "
                + "ORDER BY DNI";
        listaPacientes = this.conexion.makeQuery(sql);

        return listaPacientes;
    }

    /**
     * Devuelve todos los pacientes que contiene la base de datos.
     *
     * @return Retorna el valor de la consulta SQL.
     * @throws SQLException Devuelve error si no se pudo realizar la consulta
     * SQL.
     */
    public ResultSet mostrarPacientesTodos() throws SQLException {
        String sql;
        ResultSet listaPacientes;

        sql = "SELECT * FROM centromedico.paciente "
                + "UNION "
                + "SELECT * FROM centromedico.paciente_borrado";
        listaPacientes = this.conexion.makeQuery(sql);

        return listaPacientes;
    }

    public boolean existePaciente(String DNI) throws SQLException {
        return existePacienteBD(DNI) || existePacienteBDBorrado(DNI);
    }

    /**
     * Comprueba si existe un paciente en la BD con el DNI entregado.
     *
     * @param DNI DNI que se pretende comprobar en la BD.
     * @return true = DNI encontrado, false = DNI no encontrado
     * @throws SQLException Devuelve error si no se pudo realizar la consulta
     * SQL.
     */
    public boolean existePacienteBD(String DNI) throws SQLException {
        boolean result = false;
        String sql;
        ResultSet resultSet;

        sql = "SELECT dni "
                + "FROM centromedico.paciente "
                + "WHERE dni='" + DNI + "'";
        resultSet = conexion.makeQuery(sql);

        while (resultSet.next()) {
            if (DNI.equals(resultSet.getString("dni"))) {
                result = true;
            }
        }

        return result;
    }

    /**
     * Comprueba si existe un paciente en la BD de pacientes borrados, con el
     * DNI entregado.
     *
     * @param DNI DNI que se pretende comprobar en la BD de pacientes borrados.
     * @return true = DNI encontrado, false = DNI no encontrado
     * @throws SQLException Devuelve error si no se pudo realizar la consulta
     * SQL.
     */
    public boolean existePacienteBDBorrado(String DNI) throws SQLException {
        boolean result = false;
        String sql;
        ResultSet resultSet;

        sql = "SELECT dni "
                + "FROM centromedico.paciente_borrado "
                + "WHERE dni='" + DNI + "'";
        resultSet = conexion.makeQuery(sql);

        while (resultSet.next()) {
            if (DNI.equals(resultSet.getString("dni"))) {
                result = true;
            }
        }

        return result;
    }

    /**
     * Inserta un nuevo paciente en la base de datos.
     *
     * @param paciente Recibe un array con los datos del paciente. El formato
     * con el que se ha de rellenar el array es {dni, nombre, apellidos,
     * seguro}.
     *
     * @return true = añadido a la BD, false = fallo al añadir
     */
    public boolean addPaciente(String[] paciente) {
        boolean added = true;
        String sql;

        sql = "INSERT "
                + "INTO centromedico.paciente "
                + "(DNI, nombre, apellidos, CompSegur)"
                + "VALUES "
                + "('" + paciente[0] + "', "
                + "'" + paciente[1] + "', "
                + "'" + paciente[2] + "', "
                + "'" + paciente[3] + "')";
        try {
            conexion.makeUpdate(sql);
        } catch (SQLException e) {
            added = false;
        }

        return added;
    }

    /**
     * Actualiza el paciente, en parte o en su totalidad, en la base de datos.
     *
     * @param DNI Recibe el dni del paciente del cual se quiere modificar los
     * datos.
     * @param paciente Recibe un array de 'tuplas' (arrays de 2 elementos) con
     * los datos que se desean actualizar del paciente. La estructura de cada
     * 'tupla' debe ser {campo, valor}. Los campos disponibles actualmente para
     * actualizar son: [dni, nombre, apellidos, compsegur].
     * @return true = actualizado, false = fallo al actualizar
     */
    public boolean updatePaciente(String DNI, String[][] paciente) {
        boolean updated = true;
        String sql;

        sql = "UPDATE centromedico.paciente "
                + "SET ";
        for (String[] tuplas : paciente) {
            sql += tuplas[0] + "='" + tuplas[1] + "' ";
        }
        sql += "WHERE DNI='" + DNI + "'";
        try {
            conexion.makeUpdate(sql);
        } catch (SQLException e) {
            updated = false;
        }

        return updated;
    }

    /**
     * Devuelve un paciente completo presente en la BD.
     *
     * @param DNI Identificador del paciente que se desea obtener.
     * @return Retorna el Paciente con toda su información.
     * @throws SQLException Devuelve error si no se pudo recuperar el paciente
     * de la BD.
     */
    public Paciente getPaciente(String DNI) throws SQLException {
        return new Paciente(DNI, conexion);
    }

    /**
     * Elimina, de la BD, el paciente especificado por parámetro.
     *
     * @param DNI Identificador del paciente que se desea borrar.
     * @return true = borrado de la BD, false = fallo al borrar
     */
    public Boolean removePacienteBD(String DNI) {
        Boolean removed = true;
        String sql;

        sql = "DELETE FROM centromedico.paciente "
                + "WHERE dni='" + DNI + "'";
        try {
            conexion.makeExecute(sql);
        } catch (SQLException e) {
            removed = false;
        }

        return removed;
    }

    /**
     * Elimina, de la BD de pacientes borrados, el paciente especificado por
     * parámetro.
     *
     * @param DNI Identificador del paciente que se desea borrar.
     * @return true = borrado de la BD, false = fallo al borrar
     */
    public Boolean removePacienteBDBorrado(String DNI) {
        Boolean removed = true;
        String sql;

        sql = "DELETE FROM centromedico.paciente_borrado "
                + "WHERE dni='" + DNI + "'";
        try {
            conexion.makeExecute(sql);
        } catch (SQLException e) {
            removed = false;
        }

        return removed;
    }

    /**
     * Comprueba que el DNI es verdadero, ya sea nacional o extranjero.
     *
     * @param DNI DNI que se desea comprobar en formato texto.
     * @return true = DNI verdadero, false = DNI falso
     */
    public boolean comprobarDNI(String DNI) {
        boolean verdadero = false;

        if (DNI.matches("[A-Z0-9][0-9]{7}[A-Z]")) {
            String letrasDNI = "TRWAGMYFPDXBNJZSQVHLCKE";
            String miNumero;

            /**
             * Se comprueba si es un DNI nacional o extranjero.
             */
            if (DNI.substring(0, 1).matches("[0-9]")) {
                miNumero = DNI.substring(0, 8);
            } else {
                miNumero = DNI.substring(1, 8);
            }

            int moduloLetra = Integer.parseInt(miNumero) % letrasDNI.length();

            char letraDNI = DNI.charAt(8);
            char letraObtenida = letrasDNI.charAt(moduloLetra);

            if (letraObtenida == letraDNI) {
                verdadero = true;
            }
        }

        return verdadero;
    }

    /**
     * Método para discernir si un String contiene o no únicamente carácteres de
     * texto, es decir, no contiene números, símbolos...
     *
     * @param campoTexto
     * @return true = solo contiene carácteres alfabéticos, false = contiene
     * carácteres no alfebéticos
     */
    public boolean esTexto(String campoTexto) {
        boolean esTexto = false;

        if (campoTexto.matches("[^\\d.,<>_´`+¿?!¡@#$%&=\\s]{1,}")) {
            esTexto = true;
        }

        return esTexto;
    }

//    public String[] getHorarioEspecialidadByDay(String diaDeLaSemana) {
//        throw new UnsupportedOperationException("TODO");
//    }
//
//    public ResultSet getMedicosByHorarioEspecialidad(String diaDeLaSemana, String especialidad, String horario) {
//        throw new UnsupportedOperationException("TODO");
//    }
//
//    public String[] getConsultasMedico(String fecha, String especialidad, String horario, String codMedico) throws SQLException {
//        Medico medico = getMedico(codMedico);
//        throw new UnsupportedOperationException("TODO");
//        return medico.getConsultasDisponibles(String fecha, String especialidad, String horario);
//    }
//    /**
//     * Elimina una cita de un paciente.
//     *
//     * @param paciente Paciente del que se desea borrar una cita.
//     * @return true = eliminado, false = fallo al eliminar
//     */
//    public boolean anularCita(String codCita) {
//	 boolean eliminado = false;
//       // TODO
//	 return eliminado;
//	 }
}
