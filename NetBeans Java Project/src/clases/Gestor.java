package clases;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alejandro
 */
public class Gestor {

    int especialidadMaxMedicos = 2;

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
        String agenda = "--------------------------MAÑANA--------------------------\n";
        String[] especialidades;
        String sql;
        ResultSet resultSet;

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

            if (resultSet.next()) {
                numSala++;
                agenda += "Sala " + numSala + ": " + especialidad + "\n";

                do {
                    agenda += "    " + resultSet.getString("hora") + " "
                            + "DNI: "
                            + resultSet.getString("paciente") + " "
                            + "Médico: "
                            + resultSet.getString("nombre") + " "
                            + resultSet.getString("apellidos") + "\n";
                } while (resultSet.next());
            }
        }

        if (numSala == 0) {
            agenda += "\n"
                    + "                          "
                    + "(NADA)"
                    + "                          "
                    + "\n\n";
        }
        agenda += "--------------------------______--------------------------\n";
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
        String agenda = "--------------------------TARDE---------------------------\n";
        String[] especialidades;
        String sql;
        ResultSet resultSet;

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

            if (resultSet.next()) {
                numSala++;
                agenda += "Sala " + numSala + ": " + especialidad + "\n";

                do {
                    agenda += "    " + resultSet.getString("hora") + " "
                            + "DNI: "
                            + resultSet.getString("paciente") + " "
                            + "Médico: "
                            + resultSet.getString("nombre") + " "
                            + resultSet.getString("apellidos") + "\n";
                } while (resultSet.next());
            }
        }

        if (numSala == 0) {
            agenda += "\n"
                    + "                          "
                    + "(NADA)"
                    + "                          "
                    + "\n\n";
        }
        agenda += "--------------------------______--------------------------\n";
        return agenda;
    }

    /**
     * Muestra todas las especialidades por nombre, que contiene la BD.
     *
     * @return String[] que contiene cada una de las especialidades.
     * @throws SQLException Devuelve error si no se pudo realizar la consulta
     * SQL.
     */
    public String[] getEspecialidades() throws SQLException {
        ArrayList<String> especialidades = new ArrayList();
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

    public boolean isEspecialidadMaxMedicos(String especialidad) throws SQLException {
        boolean maxMedicos = false;
        String sql;
        ResultSet resultSet;

        sql = "SELECT COUNT(m.n_colegiado) "
                + "FROM centromedico.medico m, centromedico.especialidad e "
                + "WHERE e.cod_especialidad=m.especialidad "
                + "AND e.nombre='" + especialidad + "'";
        resultSet = conexion.makeQuery(sql);

        while (resultSet.next()) {
            if (resultSet.getInt(1) >= this.especialidadMaxMedicos) {
                maxMedicos = true;
            }
        }

        return maxMedicos;
    }

    /**
     * Devuelve los turnos del horario de una especialidad en un día de la
     * semana.
     *
     * @param especialidad Nombre de la especialidad de la que se desea obtener
     * su horario.
     * @param diaDeLaSemana Día de la semana de la que se desea obtener su
     * horario. Ej. "Lunes".
     * @return Retorna los turnos para ese día.
     * @throws SQLException Devuelve error si no se pudo obtener el horario de
     * la especialidad de la BD.
     */
    public String[] getHorarioEspecialidadByDay(String especialidad, String diaDeLaSemana) throws SQLException {
        List<String> horario = new ArrayList();
        String sql;
        ResultSet resultSet;

        sql = "SELECT d.horario "
                + "FROM centromedico.horario_" + diaDeLaSemana + " d, centromedico.especialidad e "
                + "WHERE d.cod_especialidad=e.cod_especialidad "
                + "AND e.nombre='" + especialidad + "' "
                + "ORDER BY d.horario";
        resultSet = conexion.makeQuery(sql);

        while (resultSet.next()) {
            horario.add(resultSet.getString(1));
        }

        return horario.toArray(new String[horario.size()]);
    }

    /**
     * Devuelve todos los médicos que contiene la base de datos.
     *
     * @return Retorna un objeto ResultSet con el valor de la consulta SQL.
     * @throws SQLException Devuelve error si no se pudo realizar la consulta
     * SQL.
     */
    public ResultSet mostrarMedicos() throws SQLException {
        String sql;
        ResultSet listaMedicos;

        sql = "SELECT m.n_colegiado, m.nombre, m.apellidos, m.horario, e.nombre "
                + "FROM centromedico.medico m, centromedico.especialidad e "
                + "WHERE e.cod_especialidad=m.especialidad ";
        listaMedicos = this.conexion.makeQuery(sql);

        return listaMedicos;
    }

    /**
     * Devuelve los médicos de una especialidad en un turno.
     *
     * @param especialidad Especialidad de la que se quiere obtener los médicos.
     * @param horario Turno del que se quiere obtener los médicos.
     * @return Retorna un objeto ResultSet con el valor de la consulta SQL.
     * @throws SQLException Devuelve error si no se puderon obtener los médicos,
     * de la especialidad y en el horario deseados, de la BD.
     */
    public ResultSet mostrarMedicosByHorarioEspecialidad(String especialidad, String horario) throws SQLException {
        String sql;
        ResultSet resultSet;

        sql = "SELECT m.n_colegiado, m.nombre, m.apellidos "
                + "FROM centromedico.medico m, centromedico.especialidad e "
                + "WHERE e.cod_especialidad=m.especialidad "
                + "AND e.nombre='" + especialidad + "' "
                + "AND m.horario='" + horario + "'";
        resultSet = conexion.makeQuery(sql);

        return resultSet;
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
     * Inserta un nuevo médico en la base de datos, si no ha sobrepasado el
     * límite de médicos por especialidad.
     *
     * @param medico Recibe un array con los datos del médico. El formato con el
     * que se ha de rellenar el array es {n_colegiado, nombre, apellidos,
     * horario, tiempo_min, especialidad}.
     *
     * @return true = añadido a la BD, false = fallo al añadir
     */
    public boolean addMedico(String[] medico) {
        boolean added = true;
        String especialidad = medico[5];

        try {
            if (!isEspecialidadMaxMedicos(especialidad)) {
                String sql;
                String codMedico = medico[0];

                sql = "INSERT"
                        + "INTO centromedico.medico "
                        + "(n_colegiado, nombre, apellidos, horario, tiempo_min, especialidad)"
                        + "VALUES"
                        + "("
                        + "'" + codMedico + ", "
                        + "'" + medico[1] + ", "
                        + "'" + medico[2] + ", "
                        + "'" + medico[3] + ", "
                        + "'" + medico[4] + ", "
                        + "(SELECT e.cod_especialidad "
                        + "FROM centromedico.especialidad e "
                        + "WHERE e.nombre='" + especialidad + "')"
                        + ")";
                conexion.makeUpdate(sql);

                // TODO
//                if (!conexion.existeUser(codMedico)) {
//                    conexion.crearUserBD(codMedico);
//                    conexion.setPermisosBD(codMedico);
//                }
            } else {
                added = false;
            }
        } catch (SQLException e) {
            added = false;
        }

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
    public Medico getMedico(String numColegiado) throws SQLException {
        return new Medico(Integer.parseInt(numColegiado), conexion);
    }

    /**
     * Devuelve todas las horas que tiene disponibles, de consulta, un médico en
     * una fecha y para una especialidad en concreto.
     *
     * @param fecha Formato de fecha: "yy-mm-dd"
     * @param especialidad Especialidad de la que se desea obtener las consultas
     * disponibles.
     * @param horario Turno en el que se desea conocer las consultas disponibles
     * del médico.
     * @param numColegiado Número identificativo del médico del que se desea
     * conocer las consultas disponibles.
     * @return Retorna las horas de las consultas disponibles, dentro del turno
     * indicado, de un médico perteneciente a una especialidad.
     * @throws SQLException Devuelve error si no se pudo realizar la consulta
     * SQL.
     */
    public String[] getConsultasDisponiblesMedico(String fecha, String especialidad, String horario, String numColegiado) throws SQLException {
        Medico medico = getMedico(numColegiado);

        return medico.getConsultasDisponibles(fecha, especialidad, horario);
    }

    /**
     * Sustituye un médico por otro.
     *
     * @param numColegiado Número identificativo del médico que se desea
     * sustituir.
     * @param medicoSustituto Recibe un array con los datos del médico
     * sustituto. El formato con el que se ha de rellenar el array es
     * {n_colegiado, nombre, apellidos, horario, tiempo_min, especialidad}
     * @return true = añadido a la BD, false = fallo al añadir
     */
    public Boolean sustituirMedico(String numColegiado, String[] medicoSustituto) {
        Boolean sustituido = false;

        if (removeMedico(numColegiado)) {
            if (addPaciente(medicoSustituto)) {
                if (trasladarCitas(numColegiado, medicoSustituto[0])) {
                    sustituido = true;
                }
            }
        }

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
     * @return Retorna un objeto ResultSet con el valor de la consulta SQL.
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
     * @return Retorna un objeto ResultSet con el valor de la consulta SQL.
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
     * @return Retorna un objeto ResultSet con el valor de la consulta SQL.
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
     * Comprueba si existe un historial, en la BD, perteneciente a un paciente y
     * a una especialidad.
     *
     * @param DNI Código identificativo de un paciente.
     * @param especialidad Nombre de la especialidad.
     * @return true = existe el historial, false = no existe el historial
     * @throws SQLException Devuelve error si no se pudo realizar la consulta
     * SQL.
     */
    public Boolean existeHistorial(String DNI, String especialidad) throws SQLException {
        Boolean existe = false;
        String sql;
        ResultSet resultSet;

        sql = "SELECT COUNT(*)"
                + "FROM centromedico.historial h, centromedico.especialidad e "
                + "WHERE e.cod_especialidad=h.especialidad "
                + "AND e.nombre='" + especialidad + "'"
                + "AND h.paciente='" + DNI + "'";
        resultSet = conexion.makeQuery(sql);

        while (resultSet.next()) {
            if (resultSet.getInt(1) > 0) {
                existe = true;
            }
        }

        return existe;
    }

    /**
     * Añade un historial de una especialidad perteneciente a un paciente, a la
     * BD.
     *
     * @param DNI Código identificativo de un paciente.
     * @param especialidad Nombre de la especialidad.
     * @return true = se ha añadido, false = no se ha añadido
     */
    public Boolean addHistorial(String DNI, String especialidad) {
        Boolean added;
        String sql;

        sql = "INSERT "
                + "INTO centromedico.historial "
                + "(paciente, especialidad)"
                + "VALUES "
                + "("
                + "'" + DNI + "', "
                + "(SELECT e.cod_especialidad "
                + "FROM centromedico.especialidad e "
                + "WHERE e.nombre='" + especialidad + "')"
                + ")";
        try {
            added = conexion.makeExecute(sql);

        } catch (SQLException e) {
            added = false;
        }

        return added;
    }

    /**
     * Añade una cita de un paciente en una fecha, para una hora con un médico
     * en concreto.
     *
     * @param cita Colección de valores con el siguiente formato: El formato a
     * recibir es {fecha, hora, codMedico, especialidad, DNI}.
     * @return true = se ha añadido, false = no se ha añadido
     */
    public Boolean addCita(String[] cita) {
        Boolean added;
        String sql;
        String especialidad = cita[3];
        String DNI = cita[4];

        sql = "INSERT "
                + "INTO centromedico.citas "
                + "(dia, hora, medico, especialidad, paciente)"
                + "VALUES "
                + "("
                + "'" + cita[0] + "', "
                + "'" + cita[1] + "', "
                + "'" + cita[2] + "', "
                + "(SELECT e.cod_especialidad "
                + "FROM centromedico.especialidad e "
                + "WHERE e.nombre='" + especialidad + "')"
                + "'" + DNI + "')";
        try {
            added = conexion.makeExecute(sql);
            if (!existeHistorial(DNI, especialidad)) {
                addHistorial(DNI, especialidad);
            }
        } catch (SQLException e) {
            added = false;
        }

        return added;
    }

    /**
     * Elimina una cita de un paciente.
     *
     * @param codCita 
     * @return true = eliminado, false = fallo al eliminar
     */
    public boolean removeCita(String codCita) {
        boolean removed;
        String sql;
        
        sql = "DELETE FROM centromedico.citas "
                + "WHERE cod_cita='" + codCita + "'";
        try {
        removed = conexion.makeExecute(sql);
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

    /**
     * Método para discernir si un String contiene o no únicamente carácteres
     * numéricos, es decir, no contiene letras, símbolos...
     *
     * @param campoNumerico
     * @return true = solo contiene carácteres numéricos, false = contiene
     * carácteres no numéricos
     */
    public boolean esNumerico(String campoNumerico) {
        boolean esNumerico = false;

        if (campoNumerico.matches("[^\\D.,<>_´`+¿?!¡@#$%&=\\\\|\"#·¬/();:-{}*]{1,}")) {
            esNumerico = true;
        }

        return esNumerico;
    }
}
