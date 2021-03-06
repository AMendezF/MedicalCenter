package clases;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author OMG_DaNgErOuS_PablO_MLG
 */
public class Medico {

	private final int n_colegiado;
	private final Conexion con;
	private int n_especialidad;
	boolean[] dia1;
	boolean[] dia2;
	boolean[] dia3;
	boolean[] diaVacio;
	PreparedStatement preparedStmt = null;
	String fecha1;
	String fecha2;
	String fecha3;
	Calendar fecha = new GregorianCalendar();
	int horaI = 0;

	public Medico(int n_colegiado, Conexion con) throws SQLException {
		this.n_colegiado = n_colegiado;
		this.con = con;
		dia1 = new boolean[(240 / this.getTiempoMin())]; // 240 minutos de turno
		dia2 = new boolean[(240 / this.getTiempoMin())]; // entre el tiempo minimo
		dia3 = new boolean[(240 / this.getTiempoMin())]; // de atencion del medico
		diaVacio = new boolean[(240 / this.getTiempoMin())]; // Día libre sin 
		for (int i = 0; i < diaVacio.length; i++) {	// turnos ocupados
			diaVacio[i] = false;
		}

		fecha1 = fecha.get(Calendar.YEAR) + "-"
				+ (fecha.get(Calendar.MONTH) + 1) + "-"
				+ fecha.get(Calendar.DAY_OF_MONTH);

		fecha.add(Calendar.DAY_OF_MONTH, 1);
		fecha2 = fecha.get(Calendar.YEAR) + "-"
				+ (fecha.get(Calendar.MONTH) + 1) + "-"
				+ fecha.get(Calendar.DAY_OF_MONTH);

		fecha.add(Calendar.DAY_OF_MONTH, 1);
		fecha3 = fecha.get(Calendar.YEAR) + "-"
				+ (fecha.get(Calendar.MONTH) + 1) + "-"
				+ fecha.get(Calendar.DAY_OF_MONTH);

		if ("Mañana".equals(this.getTurno())) {
			horaI = 9;
		} else if ("Tarde".equals(this.getTurno())) {
			horaI = 15;
		}
		Connection reg = con.getCon();
		String sql = "SELECT hora FROM centromedico.citas WHERE medico=? AND dia =?;";
		preparedStmt = reg.prepareStatement(sql);
		preparedStmt.setInt(1, n_colegiado);
		preparedStmt.setDate(2, java.sql.Date.valueOf(fecha1));
		ResultSet rs = preparedStmt.executeQuery();

		setNumEspecialidad();
	}

	public int getTiempoMin() throws SQLException {
		int result = 0;
		Connection reg = con.getCon();
		String sql = "SELECT tiempo_min FROM centromedico.medico WHERE N_colegiado=?;";
		preparedStmt = reg.prepareStatement(sql);
		preparedStmt.setInt(1, n_colegiado);
		ResultSet rs = preparedStmt.executeQuery();
		if (rs.next()) {
			result = rs.getInt("tiempo_min");
		}
		return result;
	}

	public String getTurno() throws SQLException {
		String result = null;
		Connection reg = con.getCon();
		String sql = "SELECT horario FROM centromedico.medico WHERE N_colegiado=?;";
		preparedStmt = reg.prepareStatement(sql);
		preparedStmt.setInt(1, n_colegiado);
		ResultSet rs = preparedStmt.executeQuery();
		if (rs.next()) {
			result = rs.getString("horario");
		}
		return result;
	}

	public String getNombre() throws SQLException {
		String result = null;
		Connection reg = con.getCon();
		String sql = "SELECT nombre, apellidos FROM centromedico.medico WHERE N_colegiado=?";
		preparedStmt = reg.prepareStatement(sql);
		preparedStmt.setInt(1, n_colegiado);
		ResultSet rs = preparedStmt.executeQuery();
		if (rs.next()) {
			result = rs.getString("nombre") + " " + rs.getString("apellidos");
		}
		return result;
	}

	public String getEspecialidad() throws SQLException {
		String result = null;
		Connection reg = con.getCon();
		String sql = "SELECT especialidad.nombre FROM centromedico.especialidad WHERE cod_especialidad=?;";
		preparedStmt = reg.prepareStatement(sql);
		preparedStmt.setInt(1, getN_Especialidad());
		ResultSet rs = preparedStmt.executeQuery();
		if (rs.next()) {
			result = rs.getString("especialidad.nombre");
		}
		return result;
	}

	private void setNumEspecialidad() throws SQLException {
		int result = 0;
		Connection reg = con.getCon();
		String sql = "SELECT medico.especialidad FROM centromedico.medico WHERE N_colegiado=? ;";
		preparedStmt = reg.prepareStatement(sql);
		preparedStmt.setInt(1, n_colegiado);
		ResultSet rs = preparedStmt.executeQuery();
		if (rs.next()) {
			result = rs.getInt("medico.especialidad");
		}
		this.n_especialidad = result;
	}

	public int getNumCitas() throws SQLException {
		int resul = 0;

		Connection reg = con.getCon();
		String sql = "SELECT COUNT(Cod_cita) FROM centromedico.citas WHERE medico=? ";
		preparedStmt = reg.prepareStatement(sql);
		preparedStmt.setInt(1, n_colegiado);
		ResultSet rs = preparedStmt.executeQuery();

		if (rs.next()) {
			resul = rs.getInt("COUNT(Cod_cita)");
		}
		return resul;
	}

	public int getN_Especialidad() throws SQLException {
		return this.n_especialidad;
	}

	/**
	 * Este método retorna un ResultSet de SQL de los pacientes asociados a ese
	 * médico Si no hay pacientes asociados, no retorna nada.
	 *
	 * @return ResultSet
	 * @throws SQLException
	 */
	public ResultSet mostrarPacientesAsociados() throws SQLException {
		Connection reg = con.getCon();
		String sql;
		sql = "SELECT paciente.* FROM centromedico.paciente, centromedico.historial "
				+ "WHERE historial.especialidad = ?  AND historial.paciente = paciente.DNI "
				+ "GROUP BY historial.paciente";

		preparedStmt = reg.prepareStatement(sql);
		preparedStmt.setInt(1, getN_Especialidad());
		ResultSet rs = preparedStmt.executeQuery();

		return rs;
	}

	/**
	 * HOLA ALEEEX! Devuelve el horario libre de citas del médico para la fecha,
	 * hora y especialidad proporcionada.
	 *
	 * @param fecha
	 * @param especialidad
	 * @param horario
	 * @return String[]
	 * @throws SQLException
	 */
	public String[] getConsultasDisponibles(String fecha, String especialidad,
			String horario) throws SQLException {
		int inicioHorario;
		// Preparamos consulta
		Connection reg = this.con.getCon();
		String sql;
		sql = "SELECT Hora "
				+ "FROM centromedico.citas "
				+ "WHERE Dia='" + fecha + "' ";	// Queremos los horarios de las citas del médico de ese dia
		if (horario.equals("Mañana")) { // Solo horarios de turno mañana
			sql += "AND Hora<'13:00:00' ";
			inicioHorario = 9;
		} else {                        // Solo horarios de turno tarde
			sql += "AND Hora>='13:00:00' ";
			inicioHorario = 13;
		}
		sql += "AND Especialidad=(SELECT Cod_especialidad "
				+ // y de esta 
				"FROM centromedico.especialidad WHERE Nombre=?) "
				+ "ORDER BY TIME(hora) ASC;"; // especialidad

		preparedStmt = reg.prepareStatement(sql);
		preparedStmt.setString(1, especialidad);
		ResultSet rs = preparedStmt.executeQuery();

		return this.construirHorario(rs, inicioHorario);
	}

	/**
	 * Con el ResultSet obtenido devuelve un array de strings de horarios libres
	 * del médico menos los ya ocupados señalados en el ResultSet.
	 *
	 * @param rs
	 * @return String[]
	 */
	private String[] construirHorario(ResultSet rs, int horaInicio)
			throws SQLException {
		ArrayList<String> horariosOcupados = new ArrayList<>();
		String horaReserv, minutoReservado;
		while (rs.next()) { // cargamos en el arraylist los horarios de citas ya 
			horaReserv = rs.getString("Hora"); // reservadas y guardamos en 
			// formato HH:MM
			horariosOcupados.add(horaReserv.substring(0, horaReserv.length() - 3));
		}

		ArrayList<String> horariosDisponibles = new ArrayList<>();

		int hora = horaInicio;
		int minutos = 0;
		int horaMax = hora + 4;
		String time;
		do {
			if (hora < 10) {
				time = "0" + hora;
			} else {
				time = Integer.toString(hora);
			}
			if (minutos < 10) {
				time += ":0" + minutos;
			} else {
				time += ":" + minutos;
			}

			if (horariosOcupados.size() > 0 && time.equals(horariosOcupados.get(0))) {
				horariosOcupados.remove(0);
			} else {
				horariosDisponibles.add(time);
			}

			minutos += this.getTiempoMin();
			if (minutos > 59) {
				minutos -= 60;
				hora++;
			}
		} while (hora < horaMax);

		return horariosDisponibles.toArray(new String[horariosDisponibles.size()]);
	}

	public ResultSet mostrarCitasMedico() throws SQLException {
		Connection reg = con.getCon();
		String sql;
		sql = "SELECT citas.cod_cita, citas.dia, citas.hora,"
				+ "paciente.nombre, paciente.apellidos, citas.paciente "
				+ "FROM centromedico.citas, centromedico.paciente "
				+ "WHERE citas.medico = ? AND paciente.DNI = citas.paciente "
				+ "ORDER BY citas.hora";

		preparedStmt = reg.prepareStatement(sql);
		preparedStmt.setInt(1, getN_colegiado());
		ResultSet rs = preparedStmt.executeQuery();

		return rs;
	}

	/**
	 * Retorna el ResultSet de solo citas que tiene hoy el médico para poder
	 * abrir una ficha de historial a un paciente de los presentes.
	 *
	 * @return
	 * @throws SQLException
	 */
	public ResultSet mostrarCitasDiaMedico(String dia) throws SQLException {
		Connection reg = con.getCon();
		String sql;
		if(dia == null){
			dia = getDia();
		}
		sql = "SELECT citas.cod_cita, citas.dia, citas.hora,"
				+ "paciente.nombre, paciente.apellidos, citas.paciente "
				+ "FROM centromedico.citas, centromedico.paciente "
				+ "WHERE citas.medico = ? AND paciente.DNI = citas.paciente "
				+ "AND citas.dia = ?"
				+ "ORDER BY citas.hora";

		preparedStmt = reg.prepareStatement(sql);
		preparedStmt.setInt(1, getN_colegiado());
		preparedStmt.setDate(2, java.sql.Date.valueOf(dia));
		ResultSet rs = preparedStmt.executeQuery();

		return rs;
	}

	/**
	 * Método para invocar un historial y ficha de un paciente. DNIPACIENTE Y
	 * CODIGO DE CITA DEBEN COINCIDIR!
	 *
	 * @param DNIPaciente
	 * @param codCita
	 * @param comentario
	 * @throws SQLException
	 */
	public void escribirFichaPaciente(String DNIPaciente, String codCita,
			String comentario) throws SQLException {
		int codHistoria = getCodigoHistorialBD(DNIPaciente, getN_Especialidad());
		addFichaNueva(codCita, codHistoria, comentario);

	}

	/**
	 * Retorna un ResultSet con los historiales cuya especialidad coincide con
	 * la del médico en cuestión.
	 *
	 * @return
	 * @throws SQLException
	 */
	public ResultSet mostrarHistoriales() throws SQLException {
		Connection reg = con.getCon();
		String sql;
		sql = "SELECT historial.cod_historial, historial.paciente, "
				+ "paciente.nombre, paciente.apellidos FROM "
				+ "centromedico.historial, centromedico.paciente WHERE "
				+ "especialidad=?  AND historial.paciente = paciente.DNI";

		preparedStmt = reg.prepareStatement(sql);
		preparedStmt.setInt(1, this.n_especialidad);
		ResultSet rs = preparedStmt.executeQuery();

		return rs;
	}

	/**
	 * Retorna un ResultSet para enseñar las fichas cuyo codigo de historial
	 * coincide con el proporcionado, DEBE COMPROBARSE QUE ESTE CÓDIGO PERTENECE
	 * EN EL SELECT PROPORCIONADO.
	 *
	 * @param codigoHistorial
	 * @return
	 * @throws SQLException
	 */
	public ResultSet mostrarFichas(int codigoHistorial) throws SQLException {
		Historial historial = new Historial(codigoHistorial, this.con);
		return historial.mostrarFichas(); //Que guapada eso de la POO eh?
	}

	/**
	 * Con el codigo del historial y el de cita se procede a modificar el
	 * comentario en la BD con el proporcionado. Además se actualizará la hora.
	 *
	 * @param codigoHistorial
	 * @param codigoCita
	 * @param comentario
	 * @throws SQLException
	 */
	public void modificarFicha(int codigoHistorial, String codigoCita,
			String comentario) throws SQLException {
		Historial historial = new Historial(codigoHistorial, this.con);
		historial.modificarFicha(codigoCita, comentario);
	}

	/**
	 * Se busca un row en la tabla Historial donde coincidan DNIPaciente con la
	 * especialidad. Si ya existe retorna su clave. Sino retorna 0.
	 *
	 * @param DNIPaciente
	 * @param especialidad
	 * @return integer
	 * @throws SQLException
	 */
	private int getCodigoHistorialBD(String DNIPaciente, int especialidad) throws SQLException {
		Connection reg = con.getCon();
		String sql = "SELECT Cod_historial FROM centromedico.historial WHERE "
				+ "paciente=? AND especialidad=? ;";
		preparedStmt = reg.prepareStatement(sql);
		preparedStmt.setString(1, DNIPaciente);
		preparedStmt.setInt(2, especialidad);
		ResultSet rs = preparedStmt.executeQuery();
		if (rs.next()) {
			return rs.getInt("Cod_historial");
		}
		return 0;
	}

	public int getN_colegiado() {
		return n_colegiado;
	}

	public String getDia() {
		Calendar fechaActual = new GregorianCalendar();
		return fechaActual.get(Calendar.YEAR) + "-"
				+ (fechaActual.get(Calendar.MONTH) + 1) + "-"
				+ fechaActual.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Retorna la hora actual formato HH:MM.
	 *
	 * @return
	 */
	private String getHora() {
		Calendar fechaActual = new GregorianCalendar();
		return fechaActual.get(Calendar.HOUR_OF_DAY) + ":" + fecha.get(Calendar.MINUTE);
	}

	private void addFichaNueva(String codCita, int codHistoria,
			String comentario) throws SQLException {
		comentario = "[" + getDia() + ", " + getHora() + ", "
				+ getN_colegiado() + "] " + comentario;
		Connection reg = this.con.getCon();
		String sql = "INSERT INTO centromedico.ficha (Cod_historial, Cod_cita,"
				+ "comentario, Dia, Hora) VALUES (?, ?, ?, '" + getDia()
				+ "', '" + getHora() + "')";
		preparedStmt = reg.prepareStatement(sql);
		preparedStmt.setInt(1, codHistoria);
		preparedStmt.setString(2, codCita);
		preparedStmt.setString(3, comentario);
		preparedStmt.execute();
	}

	/**
	 * Actualiza la ficha tanto en la clase como en la BD con los datos
	 * proporcionados. Si el string está vacío ' "" ' no se actualizará el
	 * comentario. Si el parámetro actualizarFecha es True se insertará en la BD
	 * la fecha actual, en caso contrario no se actualizará.
	 *
	 * @param comentarioNuevo
	 * @param actualizarFecha
	 * @throws SQLException
	 */
	public void updateFicha(String comentarioViejo, String comentarioNuevo, String codCita) throws SQLException {
		Connection reg = this.con.getCon();
		comentarioViejo += "\n[" + getDia() + ", " + getHora() + ", "
				+ getN_colegiado() + "] " + comentarioNuevo;

		String sql = "UPDATE centromedico.Ficha SET ficha.comentario=? "
				+ "WHERE ficha.Cod_cita=?;";
		preparedStmt = reg.prepareStatement(sql);
		preparedStmt.setString(1, comentarioViejo);
		preparedStmt.setString(2, codCita);
		preparedStmt.execute();
	}

	public ArrayList<String> cargarFicha(String codCita) throws SQLException {
		ArrayList<String> resul = new ArrayList<>();
		Connection reg = con.getCon();
		String sql;
		sql = "SELECT ficha.* FROM centromedico.ficha WHERE cod_Cita=? ";

		preparedStmt = reg.prepareStatement(sql);
		preparedStmt.setString(1, codCita);
		ResultSet rs = preparedStmt.executeQuery();
		if (rs.next()) {
			resul.add(rs.getString(2));
			resul.add(rs.getString(3));
			resul.add(rs.getDate(4).toString());
			resul.add(rs.getTime(5).toString());
		}
		return resul;
	}

}
