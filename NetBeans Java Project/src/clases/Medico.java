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

public class Medico {

	private final int n_colegiado;
	private final Conexion con;
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
		dia1 = new boolean[(240 / this.getTiempoMin())];
		dia2 = new boolean[(240 / this.getTiempoMin())];
		dia3 = new boolean[(240 / this.getTiempoMin())];
		diaVacio = new boolean[(240 / this.getTiempoMin())];
		for (int i = 0; i < diaVacio.length; i++) {
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

		List<String> horas;
		horas = new ArrayList<>();
		while (rs.next()) {
			horas.add(rs.getTime("hora").toString());
		}
		for (int i = 0; i < dia1.length; i++) {
			dia1[i] = false;
		}
		for (int i = 0; i < horas.size(); i++) {
			String hora[] = horas.get(i).split(":");
			int horadiv = Integer.parseInt(hora[0]);
			int minutos = Integer.parseInt(hora[1]);
			int index = ((horadiv - horaI) * 60 + minutos) / this.getTiempoMin();
			dia1[index] = true;
		}
		horas.clear();

		preparedStmt = reg.prepareStatement(sql);
		preparedStmt.setInt(1, n_colegiado);
		preparedStmt.setDate(2, java.sql.Date.valueOf(fecha2));
		rs = preparedStmt.executeQuery();

		while (rs.next()) {
			horas.add(rs.getTime("hora").toString());
		}
		for (int i = 0; i < dia2.length; i++) {
			dia2[i] = false;
		}
		for (int i = 0; i < horas.size(); i++) {
			String hora[] = horas.get(i).split(":");
			int horadiv = Integer.parseInt(hora[0]);
			int minutos = Integer.parseInt(hora[1]);
			int index = ((horadiv - horaI) * 60 + minutos) / this.getTiempoMin();
			dia2[index] = true;
		}
		horas.clear();

		preparedStmt = reg.prepareStatement(sql);
		preparedStmt.setInt(1, n_colegiado);
		preparedStmt.setDate(2, java.sql.Date.valueOf(fecha3));
		rs = preparedStmt.executeQuery();

		while (rs.next()) {
			horas.add(rs.getTime("hora").toString());
		}
		for (int i = 0; i < dia3.length; i++) {
			dia3[i] = false;
		}
		for (int i = 0; i < horas.size(); i++) {
			String hora[] = horas.get(i).split(":");
			int horadiv = Integer.parseInt(hora[0]);
			int minutos = Integer.parseInt(hora[1]);
			int index = ((horadiv - horaI) * 60 + minutos) / this.getTiempoMin();
			dia3[index] = true;
		}
		horas.clear();
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
		String sql = "SELECT especialidad.nombre, especialidad.cod_especialidad FROM centromedico.especialidad, centromedico.medico WHERE N_colegiado=? AND especialidad=cod_especialidad;";
		preparedStmt = reg.prepareStatement(sql);
		preparedStmt.setInt(1, n_colegiado);
		ResultSet rs = preparedStmt.executeQuery();
		if (rs.next()) {
			result = rs.getString("especialidad.nombre");
		}
		return result;
	}

	public boolean codCitaEnBD(String codCita) throws SQLException {
		boolean result = true;
		PreparedStatement preparedStmt;
		Connection reg = con.getCon();
		String codCitaDB = null;
		String sql = "SELECT cod_cita FROM centromedico.citas WHERE cod_cita=? ;";
		preparedStmt = reg.prepareStatement(sql);
		preparedStmt.setString(1, codCita);
		ResultSet rs = preparedStmt.executeQuery();
		while (rs.next()) {
			codCitaDB = rs.getString("cod_cita");
		}
		if (codCitaDB == null) {
			result = false;
		}
		return result;
	}

	public void addCita(String hora, String fecha, String paciente, int contCitas) throws SQLException {
		String horas[] = hora.split(":");
		int codEsp = 0;
		int horadiv = Integer.parseInt(horas[0]);
		int minutos = Integer.parseInt(horas[1]);
		Connection reg = con.getCon();
		String sql = "SELECT especialidad FROM centromedico.medico WHERE N_colegiado=?;";
		preparedStmt = reg.prepareStatement(sql);
		preparedStmt.setInt(1, n_colegiado);
		ResultSet rs = preparedStmt.executeQuery();
		if (rs.next()) {
			codEsp = rs.getInt("especialidad");
		}
		sql = "INSERT INTO centromedico.citas ( cod_cita, dia, hora, medico, especialidad, paciente)"
				+ "VALUES (?,?,?,?,?,?)";
		preparedStmt = reg.prepareStatement(sql);
		String codcita = paciente + (contCitas + 1);
		while (codCitaEnBD(codcita)) {
			contCitas++;
			codcita = paciente + (contCitas + 1);
		}
		preparedStmt.setString(1, codcita);
		preparedStmt.setString(2, fecha);
		preparedStmt.setString(3, hora);
		preparedStmt.setInt(4, n_colegiado);
		preparedStmt.setInt(5, codEsp);
		preparedStmt.setString(6, paciente);
		preparedStmt.execute();

		int index = ((horadiv - horaI) * 60 + minutos) / this.getTiempoMin();
		boolean consultaFecha[] = getConsultas(fecha);
		consultaFecha[index] = false;
	}

	public void eliminarCita(String dia, String hora) throws SQLException {
		String horas[] = hora.split(":");
		int codEsp = 0;
		boolean consultaFecha[] = getConsultas(dia);
		int horadiv = Integer.parseInt(horas[0]);
		int minutos = Integer.parseInt(horas[1]);

		int index = ((horadiv - horaI) * 60 + minutos) / this.getTiempoMin();
		consultaFecha[index] = true;
	}

	public boolean[] getConsultas(String dia) throws SQLException {
		String fechas[] = dia.split("-");
		Date fechacita = new Date(Integer.parseInt(fechas[0]),
				Integer.parseInt(fechas[1]) - 1, Integer.parseInt(fechas[2]));
		String fechas1[] = fecha1.split("-");
		Date dia01 = new Date(Integer.parseInt(fechas1[0]),
				Integer.parseInt(fechas1[1]) - 1, Integer.parseInt(fechas1[2]));
		String fechas3[] = fecha3.split("-");
		Date dia03 = new Date(Integer.parseInt(fechas3[0]),
				Integer.parseInt(fechas3[1]) - 1, Integer.parseInt(fechas3[2]));
		if (fechacita.compareTo(dia01) < 0) {
			return diaVacio;
		} else if (dia.equals(fecha1)) {
			return dia1;
		} else if (dia.equals(fecha2)) {
			return dia2;
		} else if (dia.equals(fecha3)) {
			return dia3;
		} else if (fechacita.compareTo(dia03) > 0) {
			return diaVacio;
		}
		return null;
	}

	public int getNumCitasLibres(String dia) throws SQLException {
		int result = 0;
		boolean consultaFecha[] = getConsultas(dia);
		for (int i = 0; i < consultaFecha.length; i++) {
			if (consultaFecha[i] == true) {
				result++;
			}
		}
		return result;
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

	public int getCod_Especialidad() throws SQLException {
		int result = 0;
		Connection reg = con.getCon();
		String sql = "SELECT medico.especialidad FROM centromedico.medico WHERE N_colegiado=?;";
		preparedStmt = reg.prepareStatement(sql);
		preparedStmt.setInt(1, n_colegiado);
		ResultSet rs = preparedStmt.executeQuery();
		if (rs.next()) {
			result = rs.getInt("medico.especialidad");
		}
		return result;
	}
        
//        public String mostrarMedico() throws SQLException {
//        // Selecciona y muestra por pantalla los datos del medico con el parametro n_colegiado
//        Connection reg = con.getCon();
//        String sql;
//        int nColegiado = 0;
//        String nombre = "";
//        String apellidos = "";
//        String horario = "";
//        String tiempo = "";
//        String especialidades = "";
//        sql = "SELECT medico.n_colegiado, medico.nombre, medico.apellidos, medico.horario, medico.tiempo_min, medico.especialidad, especialidad.nombre "
//                + "FROM centromedico.medico, centromedico.especialidad "
//                + "WHERE medico.especialidad = especialidad.cod_especialidad "
//                + "and medico.n_colegiado = ?;";
//
//        preparedStmt = reg.prepareStatement(sql);
//        preparedStmt.setInt(1, this.getN_colegiado());
//        ResultSet rs = preparedStmt.executeQuery();
//
//       if (rs.next()) {
//            nColegiado = rs.getInt("medico.n_colegiado");
//            nombre = rs.getString("medico.nombre");
//            apellidos = rs.getString("medico.apellidos");
//            horario = rs.getString("medico.horario");
//            tiempo = rs.getString("medico.tiempo_min");
//            especialidades = rs.getString("especialidad.nombre");
//        }
//        return (nColegiado + " - " + nombre + " " + apellidos + "; " + tiempo + ", " + especialidades);
//    }

        /**
         * Este método retorna un ResultSet de SQL de los pacientes asociados a ese médico
         * Si no hay pacientes asociados, no retorna nada.
         * @return ResultSet
         * @throws SQLException 
         */
    public ResultSet mostrarPacientesAsociados() throws SQLException {
        Connection reg = con.getCon();
        String sql;
        sql = "SELECT paciente.* FROM centromedico.paciente, centromedico.citas "+
              "WHERE citas.medico = ?  AND citas.paciente = paciente.DNI "+
              "GROUP BY citas.paciente";

        preparedStmt = reg.prepareStatement(sql);
        preparedStmt.setInt(1, this.getN_colegiado());
        ResultSet rs = preparedStmt.executeQuery();
        return rs;
    }

    public int getN_colegiado() {
        return n_colegiado;
    }
}
