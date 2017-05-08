package clases;

import java.sql.ResultSet;
import java.sql.SQLException;

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
	 * Muestra las citas de un día organizadas por horario y salas de
	 * especialidad.
	 */
	public void mostrarAgenda() {

	}

	/**
	 * Devuelve todos los pacientes que contiene la base de datos.
	 *
	 * @return Retorna el valor de la consulta SQL.
	 * @throws SQLException Devuelve error si no see pudo realizar la consulta
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
	 * @throws SQLException Devuelve error si no see pudo realizar la consulta
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
	 * @throws SQLException Devuelve error si no see pudo realizar la consulta
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
	 * @param paciente Recibe un paciente relleno con los datos actualizados.
	 * @return true = actualizado, false = fallo al actualizar
	 */
	public boolean updatePaciente(Paciente paciente) {
		boolean updated = false;
		
		//TODO
		return updated;
	}
	
	
	public Paciente getPaciente(String DNI) {
		Paciente paciente = new Paciente(DNI, conexion);
		ResultSet resultSet;
		
		try {
			String sql;
			sql = "SELECT DNI, nombre, apellidos, compsegur "
					+ "FROM centromedico.paciente "
					+ "WHERE DNI =" + DNI;
		}catch (Exception e) {
			
		}
		
		while (resultSet.next()) {
			paciente.setNombre(resultSet.getString("paciente.nombre"));
			paciente.setApellidos(resultSet.getString("paciente.apellidos"));
			paciente.setCampoSegur(resultSet.getString("paciente.compsegur"));
		}
		
		
		
		return paciente;
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

			/*
			 Se comprueba si es un DNI nacional o extranjero.
			 */
			if (DNI.substring(0, 1).matches("[0-9]")) {
				miNumero = DNI.substring(0, 8);
			} else {
				miNumero = DNI.substring(1, 8);
			}

			int num = Integer.parseInt(miNumero);
			int mod = num % (letrasDNI.length());

			char miLetra = DNI.charAt(8);
			char nuevaLetra = letrasDNI.charAt(mod);

			if (nuevaLetra == miLetra) {
				verdadero = true;
			}
		}

		return verdadero;
	}

	public boolean estaBD(String DNI) throws SQLException {
		boolean result = false;
		String sql;
		ResultSet resultSet;

		sql = "SELECT dni "
				+ "FROM centromedico.paciente "
				+ "WHERE dni=" + DNI;
		resultSet = conexion.makeQuery(sql);

		String dniBD = null;
		while (resultSet.next()) {
			dniBD = resultSet.getString("dni");
		}

		if (dniBD.equals(DNI)) {
			result = true;
		}
		return result;
	}

	public boolean estaBDBorrado(String DNI) throws SQLException {
		boolean result = false;
		String sql;
		ResultSet resultSet;

		sql = "SELECT dni "
				+ "FROM centromedico.paciente_borrado "
				+ "WHERE dni=" + DNI;
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
	 * @throws SQLException
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
	 * @throws SQLException Devuelve error si no see pudo realizar la consulta
	 * SQL.
	 */
	public void eliminarMedico(int codMedico) throws SQLException {
		boolean eliminado = false;
		String sql;

		//sql = "DELETE FROM centromedico.medico WHERE N_colegiado =? "; // tested
		sql = "DELETE FROM centromedico.medico "
				+ "WHERE N_colegiado=" + Integer.toString(codMedico) + ";";
		this.conexion.makeQuery(sql);
	}

	/**
	 * Traslada las citas que posee un medico a un médico sustituto.
	 *
	 * @param codMedico Número identificativo del médico sustituido.
	 * @param codMedicoSustituto Número identificativo del médico sustituto.
	 * @throws SQLException SQLException Devuelve error si no see pudo realizar
	 * la consulta SQL.
	 */
	public void trasladarCitas(int codMedico, int codMedicoSustituto) throws SQLException {
		String sql;

		//sql = "UPDATE centromedico.citas SET medico=? WHERE medico=?;"; // tested
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
	 }*/ /**
	 * Devuleve todas las especialidades médicas existentes en la BD.
	 *
	 * @return Retorna una lista de especialidades médicas.
	 * @throws SQLException Devuelve error si no see pudo realizar la consulta
	 * SQL.
	 */
	/*public ArrayList<String> mostrarEspecialidades() throws SQLException {
	 ArrayList<String> listaEspecialidades = null;
	 String sql;
	 ResultSet resultSet;
        
	 sql = "SELECT * FROM centromedico.especialidad "
	 "ORDER BY cod_especialidad;";
	 resultSet = this.conexion.makeQuery(sql);
        
	 while (resultSet.next()) {
	 listaEspecialidades.add(resultSet.getString("nombre"));
	 }
        
	 return listaEspecialidades;
	 }*/
}
