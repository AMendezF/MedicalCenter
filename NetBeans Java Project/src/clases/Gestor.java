package clases;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author Alejandro
 */
public class Gestor {

    private final int maxMedicosActivos = 3;
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
                    + "FROM citas c, medico m, especialidad e "
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
                    + "FROM citas c, medico m, especialidad e "
                    + "WHERE e.nombre='" + especialidad + "' "
                    + "AND c.especialidad=e.cod_especialidad "
                    + "AND c.medico=m.n_colegiado "
                    + "AND c.hora>='13:00:00' "
                    + "AND c.dia='" + fecha + "' "
                    + "ORDER BY c.hora" + ";";
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
                + "FROM centromedico.especialidad" + ";";
        resultSet = conexion.makeQuery(sql);

        while (resultSet.next()) {
            especialidades.add(resultSet.getString("nombre"));
        }
        return (String[]) especialidades.toArray();
    }

    /**
     * Devuelve todos los pacientes que contiene la base de datos.
     *
     * @return Retorna el valor de la consulta SQL.
     * @throws SQLException Devuelve error si no se pudo realizar la consulta
     * SQL.
     */
    public ResultSet mostrarPacientes() throws SQLException {
        ResultSet listaPacientes;
        String sql;

        sql = "SELECT * "
                + "FROM centromedico.paciente "
                + "ORDER BY DNI" + ";";
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
        ResultSet listaPacientes;
        String sql;

        sql = "SELECT * "
                + "FROM centromedico.paciente_borrado "
                + "ORDER BY DNI" + ";";
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
        ResultSet listaPacientes;
        String sql;

        sql = "SELECT * "
                + "FROM centromedico.paciente, centromedico.paciente_borrado"
                + "ORDER BY DNI" + ";";
        listaPacientes = this.conexion.makeQuery(sql);

        return listaPacientes;
    }

    /**
     * Inserta un nuevo paciente en la base de datos.
     *
     * @return true = añadido a la BD, false = fallo al añadir
     */
    public boolean addPaciente(Paciente paciente) {
        boolean added = false;

        //TODO
        return added;
    }

    /**
     * Actualiza el paciente, que recibe, en la base de datos.
     *
     * @param paciente Recibe un paciente relleno con los datos actualizados.
     * @return true = actualizado, false = fallo al actualizar
     */
    public boolean updatePaciente(Paciente paciente) {
        boolean updated = false;

        //TODO
        return updated;
    }

    /**
     * Devuelve un paciente completo de presente en la BD.
     *
     * @param DNI Identificador del paciente que se desea conseguir.
     * @return Retorna el Paciente con toda su información.
     * @throws SQLException Devuelve error si no se pudo recuperar el paciente
     * de la BD.
     */
    public Paciente getPaciente(String DNI) throws SQLException {
        return new Paciente(DNI, conexion);
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
	
	public boolean esTexto(String campoTexto) {
		boolean esTexto = false;
		
		if(campoTexto.matches("[^\\d.,<>_´`+¿?!¡@#$%&=\\s]{1,}")) {
			esTexto = true;
		}
		
		return esTexto;
	}

    /**
     * Comprueba si existe un paciente en la BD con el DNI entregado.
     *
     * @param DNI DNI que se pretende comprobar en la BD.
     * @return true = DNI encontrado, false = DNI no encontrado
     * @throws SQLException Devuelve error si no se pudo realizar la consulta
     * SQL.
     */
    public boolean estaBD(String DNI) throws SQLException {
        boolean result = false;
        String sql;
        ResultSet resultSet;
        String dniBD;

        sql = "SELECT dni "
                + "FROM centromedico.paciente "
                + "WHERE dni=" + DNI + ";";
        resultSet = conexion.makeQuery(sql);

        dniBD = null;
        while (resultSet.next()) {
            dniBD = resultSet.getString("dni");
        }

        if (dniBD.equals(DNI)) {
            result = true;
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
    public boolean estaBDBorrado(String DNI) throws SQLException {
        boolean result = false;
        String sql;
        ResultSet resultSet;

        sql = "SELECT dni "
                + "FROM centromedico.paciente_borrado "
                + "WHERE dni=" + DNI + ";";
        resultSet = conexion.makeQuery(sql);

        String dniBD = null;
        while (resultSet.next()) {
            dniBD = resultSet.getString("dni");
        }

        if (DNI.equals(dniBD)) {
            result = true;
        }
        return result;
    }

    /**
     * Devuelve todos los medicos que contiene la base de datos.
     *
     * @return Retorna el valor de la consulta SQL.
     * @throws SQLException Devuelve error si no se pudo realizar la consulta
     * SQL.
     */
    public ResultSet mostrarMedicos() throws SQLException {
        ResultSet listaMedicos = null;
        String sql;

        sql = "SELECT * "
                + "FROM centromedico.medico" + ";";
        listaMedicos = this.conexion.makeQuery(sql);

        return listaMedicos;
    }

    /**
     * Inserta un nuevo médico en la base de datos.
     *
     * @return true = añadido a la BD, false = fallo al añadir
     */
    public boolean addMedico() {
        boolean added = false;

        /*try {
		 if (actualMedicosActivos() < maxMedicosActivos) {

		 } else {
		 if (this.sustituirMedico(medico, sustituto)) {
		 added = true;
		 }
		 }

		 } catch (Exception e) {

		 }*/
        //TODO
        return added;
    }

    /**
     * Sustituye un médico por otro.
     *
     * @param medico Médico al que se pretende reemplazar.
     * @param sustituto Médico que reemplaza al anterior.
     * @return true = añadido a la BD, false = fallo al añadir
     */
    public void sustituirMedico(Medico medico, Medico sustituto) throws SQLException {
        //TODO
    }

    /**
     * Elimina el médico de la BD, especificado por parámetro.
     *
     * @param codMedico Número identificativo de un médico.
     * @throws SQLException Devuelve error si no se pudo realizar la consulta
     * SQL.
     */
    public void eliminarMedico(int codMedico) throws SQLException {
        String sql;

        sql = "DELETE FROM centromedico.medico "
                + "WHERE N_colegiado=" + Integer.toString(codMedico) + ";";
        this.conexion.makeQuery(sql);
    }

    /**
     * Traslada las citas que posee un medico a un médico sustituto.
     *
     * @param codMedico Número identificativo del médico sustituido.
     * @param codMedicoSustituto Número identificativo del médico sustituto.
     * @throws SQLException Devuelve error si no se pudo realizar la consulta
     * SQL.
     */
    public void trasladarCitas(int codMedico, int codMedicoSustituto) throws SQLException {
        String sql;

        sql = "UPDATE centromedico.citas"
                + "SET medico=" + Integer.toString(codMedico) + " "
                + "WHERE medico=" + Integer.toString(codMedicoSustituto) + ";";
        this.conexion.makeQuery(sql);
    }

    /**
     * Elimina una cita de un paciente.
     *
     * @param paciente Paciente del que se desea borrar una cita.
     * @return true = eliminado, false = fallo al eliminar
     */
    /*public boolean anularCita(Cita cita) {
	 boolean eliminado = false;

	 return eliminado;
	 }*/ 
}
