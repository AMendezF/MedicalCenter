package clases;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Berta
 */
public class main {

	public static int[] especialidadesMa(Conexion con, int dia) throws SQLException {
		int[] res = {0, 0, 0, 0, 0};
		int j = 0;
		Especialidad especialidad;
		for (int i = 1; i < 8; i++) {
			especialidad = new Especialidad(i, con);
			int horarioDia = especialidad.getHorario(dia);
			if (horarioDia == 1 || horarioDia == 3) {
				res[j] = i;
				j++;
			}
		}
		return res;
	}

	public static int[] especialidadesTa(Conexion con, int dia) throws SQLException {
		int[] res = {0, 0, 0, 0, 0};
		int j = 0;
		Especialidad especialidad;
		for (int i = 1; i < 8; i++) {
			especialidad = new Especialidad(i, con);
			int horarioDia = especialidad.getHorario(dia);
			if (horarioDia == 2 || horarioDia == 3) {
				res[j] = i;
				j++;
			}
		}
		return res;
	}

	public static void pedirCita(Paciente paciente, Conexion con) throws SQLException {
		int codEspecialidad;
		int op = 0;
		int opTurno = -1;
		String turno = null;
		int codMedico;
		String fecha1;
		String fecha2;
		String fecha3;
		Calendar fecha = new GregorianCalendar();
		String fechaDia = null;
		int diaSemana = 0;

		System.out.println("Elija la especialidad: ");
		Scanner sc = new Scanner(System.in);
		PreparedStatement preparedStmt;
		Connection reg = con.getCon();
		String sql = "SELECT * FROM centromedico.especialidad ORDER BY cod_especialidad;";
		preparedStmt = reg.prepareStatement(sql);
		ResultSet rs = preparedStmt.executeQuery();
		List<String> listaNombres;
		listaNombres = new ArrayList();
		List<String> listaCodigos;
		listaCodigos = new ArrayList();
		List<String> listaHorarios;
		listaHorarios = new ArrayList();
		while (rs.next()) {
			listaNombres.add(rs.getString("nombre"));
			listaCodigos.add(Integer.toString(rs.getInt("cod_especialidad")));
			listaHorarios.add(rs.getString("horario"));
		}
		for (int i = 0; i < listaNombres.size(); i++) {
			System.out.println(listaCodigos.get(i) + ". " + listaNombres.get(i));
		}
		codEspecialidad = sc.nextInt();

		fecha1 = fecha.get(Calendar.YEAR) + "-"
				+ (fecha.get(Calendar.MONTH) + 1) + "-"
				+ fecha.get(Calendar.DAY_OF_MONTH);
		int diaSemana1 = fecha.get(Calendar.DAY_OF_WEEK);

		fecha.add(Calendar.DAY_OF_MONTH, 1);
		fecha2 = fecha.get(Calendar.YEAR) + "-"
				+ (fecha.get(Calendar.MONTH) + 1) + "-"
				+ fecha.get(Calendar.DAY_OF_MONTH);
		int diaSemana2 = fecha.get(Calendar.DAY_OF_WEEK);

		fecha.add(Calendar.DAY_OF_MONTH, 1);
		fecha3 = fecha.get(Calendar.YEAR) + "-"
				+ (fecha.get(Calendar.MONTH) + 1) + "-"
				+ fecha.get(Calendar.DAY_OF_MONTH);
		int diaSemana3 = fecha.get(Calendar.DAY_OF_WEEK);

		do {
			System.out.println("Elija una fecha: ");

			System.out.println("1. " + fecha1);
			System.out.println("2. " + fecha2);
			System.out.println("3. " + fecha3);
			op = sc.nextInt();

			if (op == 1) {
				fechaDia = fecha1;
				diaSemana = diaSemana1;
			}
			if (op == 2) {
				fechaDia = fecha2;
				diaSemana = diaSemana2;
			}
			if (op == 3) {
				fechaDia = fecha3;
				diaSemana = diaSemana3;
			}
			if (diaSemana == 1) {
				diaSemana = 7;
			} else {
				diaSemana--;
			}
			Especialidad especialidad = new Especialidad(codEspecialidad, con);
			int horarioDia = especialidad.getHorario(diaSemana);

			do {
				System.out.println("Elija el turno: ");
				System.out.println("0. Elija otro día");
				if (horarioDia == 1 || horarioDia == 3) {
					System.out.println("1. Mañana");
				}
				if (horarioDia == 2 || horarioDia == 3) {
					System.out.println("2. Tarde");
				}
				opTurno = sc.nextInt();
				if (opTurno == 1) {
					turno = "Mañana";
				}
				if (opTurno == 2) {
					turno = "Tarde";
				}
			} while (opTurno != 1 && opTurno != 2 && opTurno != 0);
		} while (opTurno == 0);
		sql = "SELECT * FROM centromedico.medico WHERE horario=? AND especialidad=?;";
		preparedStmt = reg.prepareStatement(sql);
		preparedStmt.setString(1, turno);
		preparedStmt.setInt(2, codEspecialidad);
		rs = preparedStmt.executeQuery();
		List<String> listaMedicos = new ArrayList();
		Map<Integer, Integer> mapa = new HashMap<Integer, Integer>();
		int j = 1;
		while (rs.next()) {
			listaMedicos.add(rs.getString("nombre") + " " + rs.getString("apellidos"));
			mapa.put(j, rs.getInt("n_colegiado"));
			j++;
		}
		sc.nextLine();
		System.out.println("¿Desea elegir el médico? (S/N) ");
		String opcionS = sc.nextLine().toUpperCase();
		if (opcionS.equals("S")) {
			System.out.println("Elija el médico: ");
			for (int i = 0; i < listaMedicos.size(); i++) {
				System.out.println((i + 1) + ". " + listaMedicos.get(i));
				op = sc.nextInt();
			}
		} else if (opcionS.equals("N")) {
			op = 1;
		}
		codMedico = mapa.get(op);

		Medico medico = new Medico(codMedico, con);

		pedirConsulta(medico, fechaDia, paciente);

	}

	public static void pedirConsulta(Medico med, String dia, Paciente paciente) throws SQLException {
		boolean[] consultas;
		consultas = new boolean[24];
		int horaI = 0;
		int minutos = -med.getTiempoMin();
		consultas = med.getConsultas(dia);
		if ("Mañana".equals(med.getTurno())) {
			horaI = 9;
		} else if ("Tarde".equals(med.getTurno())) {
			horaI = 15;
		}
		System.out.println("Horas disponibles:");
		int j = 1;
		Map<Integer, String> mapa = new HashMap<Integer, String>();
		for (int i = 0; i < consultas.length; i++) {
			String horaImp = "";
			if (minutos + med.getTiempoMin() < 60) {
				minutos = minutos + med.getTiempoMin();
			} else {
				horaI++;
				minutos = minutos + med.getTiempoMin() - 60;
			}
			if (minutos == 0) {
				horaImp = horaI + ":" + minutos + "0";
			} else {
				horaImp = horaI + ":" + minutos;
			}

			if (consultas[i] == false) {
				System.out.println(j + ". " + horaImp);
				String horaMapa = horaImp + ":00";
				mapa.put(j, horaMapa);
				j++;
			}
		}
		System.out.println("Escriba la opción deseada: ");
		Scanner sc = new Scanner(System.in);
		int cadena = sc.nextInt();
		System.out.println("Tiene cita el día " + dia + " a las " + mapa.get(cadena) + " con el medico " + med.getNombre());
		med.addCita(mapa.get(cadena), dia, paciente.getDNI(), paciente.getContCitas());
		paciente.addCita();
	}

	public static void eliminarCita(Paciente paciente, Conexion con) throws SQLException {
		int op = 0;

		System.out.println("Elija la cita que desea cancelar: ");
		Scanner sc = new Scanner(System.in);
		PreparedStatement preparedStmt;
		Connection reg = con.getCon();
		String sql = "SELECT medico.nombre,citas.medico, medico.n_colegiado, especialidad.nombre, citas.cod_cita, citas.hora, citas.dia"
				+ "  FROM centromedico.citas, centromedico.medico, centromedico.especialidad "
				+ "WHERE paciente=? AND medico.n_colegiado=citas.medico "
				+ "AND medico.especialidad= especialidad.cod_especialidad";
		preparedStmt = reg.prepareStatement(sql);
		preparedStmt.setString(1, paciente.getDNI());
		ResultSet rs = preparedStmt.executeQuery();
		List<String> medicos = new ArrayList();
		List<String> codMeds = new ArrayList();
		List<String> codigosCita = new ArrayList();
		List<String> horas = new ArrayList();
		List<String> fechas = new ArrayList();
		List<String> especialidades = new ArrayList();
		while (rs.next()) {
			medicos.add(rs.getString("medico.nombre"));
			codigosCita.add(rs.getString("citas.cod_cita"));
			horas.add(rs.getTime("citas.hora").toString());
			fechas.add(rs.getDate("citas.dia").toString());
			especialidades.add(rs.getString("especialidad.nombre"));
			codMeds.add(String.valueOf(rs.getInt("citas.medico")));
		}
		for (int i = 0; i < medicos.size(); i++) {
			System.out.println((i + 1) + ". " + fechas.get(i) + " " + horas.get(i) + " con " + medicos.get(i)
					+ ", " + especialidades.get(i));
		}

		op = sc.nextInt() - 1;
		sql = "DELETE FROM centromedico.citas WHERE cod_cita=?";
		preparedStmt = reg.prepareStatement(sql);
		preparedStmt.setString(1, codigosCita.get(op));
		preparedStmt.execute();
		int codMedico = Integer.parseInt(codMeds.get(op));
		Medico med = new Medico(codMedico, con);
		String[] fechasdiv = fechas.get(op).split("-");
		if (Integer.parseInt(fechasdiv[1]) < 10) {
			fechasdiv[1] = fechasdiv[1].substring(1);
		}
		String fechaCorrecta = fechasdiv[0] + "-" + fechasdiv[1] + "-" + fechasdiv[2];
		System.out.println("Se ha eliminado la cita: " + fechaCorrecta + " " + horas.get(op) + " con " + medicos.get(op)
				+ ", " + especialidades.get(op));
		med.eliminarCita(fechaCorrecta, horas.get(op));
	}

	public static void mostrarSalas(Conexion con) throws SQLException {
		int dia = 0;
		String fecha1;
		String fecha2;
		String fecha3;
		Medico med;
		Calendar fecha = new GregorianCalendar();
		Scanner sc = new Scanner(System.in);
		Connection reg = con.getCon();
		PreparedStatement preparedStmt;
		String fechaDia = null;
		int op;

		fecha1 = fecha.get(Calendar.YEAR) + "-"
				+ (fecha.get(Calendar.MONTH) + 1) + "-"
				+ fecha.get(Calendar.DAY_OF_MONTH);
		int diaSemana1 = fecha.get(Calendar.DAY_OF_WEEK);

		fecha.add(Calendar.DAY_OF_MONTH, 1);
		fecha2 = fecha.get(Calendar.YEAR) + "-"
				+ (fecha.get(Calendar.MONTH) + 1) + "-"
				+ fecha.get(Calendar.DAY_OF_MONTH);
		int diaSemana2 = fecha.get(Calendar.DAY_OF_WEEK);

		fecha.add(Calendar.DAY_OF_MONTH, 1);
		fecha3 = fecha.get(Calendar.YEAR) + "-"
				+ (fecha.get(Calendar.MONTH) + 1) + "-"
				+ fecha.get(Calendar.DAY_OF_MONTH);

		int diaSemana3 = fecha.get(Calendar.DAY_OF_WEEK);

		do {
			System.out.println("Elija una fecha: ");

			System.out.println("1. " + fecha1);
			System.out.println("2. " + fecha2);
			System.out.println("3. " + fecha3);
			op = sc.nextInt();

			if (op == 1) {
				dia = diaSemana1;
				fechaDia = fecha1;
			}
			if (op == 2) {
				dia = diaSemana2;
				fechaDia = fecha2;
			}
			if (op == 3) {
				dia = diaSemana3;
				fechaDia = fecha3;
			}
		} while (op != 1 && op != 2 && op != 3);
		if (dia == 1) {
			dia = 7;
		} else {
			dia--;
		}
		int[] ma = especialidadesMa(con, dia);
		int[] ta = especialidadesTa(con, dia);
		Especialidad especialidad;

		System.out.println("Están ocupadas de 9 a 13 las salas: ");
		String[] fechasdiv = fechaDia.split("-");
		if (Integer.parseInt(fechasdiv[1]) < 10) {
			fechasdiv[1] = "0" + fechasdiv[1];
		}
		String fechaCorrecta = fechasdiv[0] + "-" + fechasdiv[1] + "-" + fechasdiv[2];
		for (int i = 0; i < ma.length; i++) {
			especialidad = new Especialidad(ma[i], con);
			String nombre = especialidad.getNombre();
			System.out.println("Sala " + (i + 1) + ": " + nombre);
			//PRINCIPIO GETCITAS
			String sql = "SELECT * "
					+ "FROM centromedico.citas "
					+ "WHERE  especialidad=? "
					+ "AND hora<='13:00:00' "
					+ "AND dia='" + fechaCorrecta
					+ "' ORDER BY hora;";
			preparedStmt = reg.prepareStatement(sql);
			preparedStmt.setInt(1, ma[i]);
			ResultSet rs = preparedStmt.executeQuery();
			rs = preparedStmt.executeQuery();
			int j = 1;
			while (rs.next()) {
				med = new Medico(rs.getInt("medico"), con);
				System.out.println("    " + j + ". " + rs.getString("hora") + " DNI: " + rs.getString("paciente")
						+ " con el médico: " + med.getNombre());
				j++;
			}
		}

		System.out.println("\nEstán ocupadas de 15 a 19 las salas: ");
		for (int i = 0; i < ma.length; i++) {
			especialidad = new Especialidad(ta[i], con);
			String nombre = especialidad.getNombre();
			System.out.println("Sala " + (i + 1) + ": " + nombre);
			//PRINCIPIO GETCITAS
			String sql = "SELECT * "
					+ "FROM centromedico.citas "
					+ "WHERE  especialidad=? "
					+ "AND hora>='13:00:00' "
					+ "AND dia='" + fechaCorrecta
					+ "' ORDER BY hora;";
			preparedStmt = reg.prepareStatement(sql);
			preparedStmt.setInt(1, ta[i]);
			ResultSet rs = preparedStmt.executeQuery();
			rs = preparedStmt.executeQuery();
			int j = 1;
			while (rs.next()) {
				med = new Medico(rs.getInt("medico"), con);
				System.out.println("    " + j + ". " + rs.getString("hora") + " DNI: " + rs.getString("paciente")
						+ " con el médico: " + med.getNombre());
				j++;
			}
		}
		System.out.print("\n");
	}

	public static void addMedico(Conexion con) throws SQLException {
		Scanner sc = new Scanner(System.in);
		int codColegiado;
		String nombre;
		String apellidos;
		String horario;
		int tiempo;
		int codEspecialidad;
		String sql;
		PreparedStatement preparedStmt;
		Connection reg = con.getCon();
		System.out.println("Seleccione una especialidad para el medico: \n");
		codEspecialidad = elegirEspecialidad(con);
		int opE = 0;
		String nombreE = " ";
		while (opE != 2) {
			sql = "SELECT * FROM centromedico.especialidad WHERE  Cod_especialidad=?;";
			preparedStmt = reg.prepareStatement(sql);
			preparedStmt.setInt(1, codEspecialidad);
			ResultSet rs = preparedStmt.executeQuery();
			while (rs.next()) {
				nombreE = rs.getString("nombre");
			}
			System.out.println("- La especialidad es: " + nombreE);
			System.out.println("1. La especialidad es incorrecta");
			System.out.println("2. La especialidad es correcta");
			opE = sc.nextInt();
			if (opE == 1) {
				codEspecialidad = elegirEspecialidad(con);
			}
		}
		sql = "SELECT * FROM centromedico.medico WHERE  especialidad=?;";
		preparedStmt = reg.prepareStatement(sql);
		preparedStmt.setInt(1, codEspecialidad);
		ResultSet rs = preparedStmt.executeQuery();
		rs = preparedStmt.executeQuery();
		List<String> listaMedicos = new ArrayList();
		Map<Integer, Integer> mapa = new HashMap<Integer, Integer>();
		int j = 0;
		while (rs.next()) {
			listaMedicos.add(rs.getString("nombre") + " " + rs.getString("apellidos"));
			mapa.put(j, rs.getInt("n_colegiado"));
			j++;
		}
		if (j > 1) {
			System.out.println("El n�mero m�ximo de m�dicos en esta especialidad se ha superado");
		} else {

			sc.skip("\n");
			System.out.println("Introduzca nombre del medico: ");
			nombre = sc.nextLine();
			System.out.println("Introduzca apellidos del medico: ");
			apellidos = sc.nextLine();
			System.out.println("Introduzca horario del medico: ");
			horario = sc.nextLine();
			System.out.println("Introduzca tiempo del medico: ");
			tiempo = sc.nextInt();
			System.out.println("Introduzca n� colegiado del medico: ");
			codColegiado = sc.nextInt();
			opE = 0;
			while (opE != 6) {
				System.out.println("Los datos del medico con n� colegiado: " + codColegiado + " son: ");
				System.out.println("-Nombre: " + nombre + "\n" + "-Apellidos: " + apellidos
						+ "\n" + "-Horario: " + horario + "\n" + "-Tiempo de consulta: " + tiempo + "\n");
				System.out.println("�Datos correctos? (elija una opcion) ");
				System.out.println("1. Nombre erroneo ");
				System.out.println("2. Apellidos erroneos ");
				System.out.println("3. Horario erroneo");
				System.out.println("4. Tiempo de consulta erroneo");
				System.out.println("5. N� colegiado erroneo");
				System.out.println("6. Todo de correcto");
				opE = sc.nextInt();
				switch (opE) {
					case 1:
						sc.nextLine();
						System.out.println("Introduzca de nuevo el nombre: ");
						nombre = sc.nextLine();
						break;
					case 2:
						sc.nextLine();
						System.out.println("Introduzca de nuevo el apellido: ");
						apellidos = sc.nextLine();
						break;
					case 3:
						sc.nextLine();
						System.out.println("Introduzca de nuevo el horario: ");
						horario = sc.nextLine();
						break;
					case 4:
						sc.nextLine();
						System.out.println("Introduzca de nuevo el tiempo de consulta: ");
						tiempo = sc.nextInt();
						break;
					case 5:
						sc.nextLine();
						System.out.println("Introduzca de nuevo N� de colegiado: ");
						codColegiado = sc.nextInt();
						break;
				}
			}
			sql = "INSERT INTO centromedico.medico ( n_colegiado, nombre, apellidos, horario, tiempo_min, especialidad)"
					+ "VALUES (?, ?, ?, ?, ?, ?)";
			preparedStmt = reg.prepareStatement(sql);
			preparedStmt.setInt(1, codColegiado);
			preparedStmt.setString(2, nombre);
			preparedStmt.setString(3, apellidos);
			preparedStmt.setString(4, horario);
			preparedStmt.setInt(5, tiempo);
			preparedStmt.setInt(6, codEspecialidad);
			preparedStmt.execute();
			String codigo = String.valueOf(codColegiado);
			crearUserBD(con, codigo);
			setPermisosBD(con, codigo);
		}
	}

	public static void crearUserBD(Conexion con, String cod) throws SQLException {
		PreparedStatement preparedStmt;
		Connection reg = con.getCon();
		String sql = "create user ?@localhost;";
		preparedStmt = reg.prepareStatement(sql);
		preparedStmt.setString(1, cod);
		preparedStmt.execute();
	}

	public static void setPermisosBD(Conexion con, String cod) throws SQLException {
		PreparedStatement preparedStmt;
		Connection reg = con.getCon();
		String sql = "grant all on centromedico.* to ?@localhost;";
		preparedStmt = reg.prepareStatement(sql);
		preparedStmt.setString(1, cod);
		preparedStmt.execute();
	}

	public static void eliminarMedico(Conexion con) throws SQLException {
		int numColegiadoViejo;
		Medico medico;
		int codColegiadoSustituto;

		System.out.println("Introduza el numero de Colegiado");
		Scanner sc = new Scanner(System.in);
		numColegiadoViejo = sc.nextInt();
		medico = new Medico(numColegiadoViejo, con);
		System.out.println("Se procedera al borrado de " + medico.getNombre());

		int codEspecialidad = medico.getCod_Especialidad();
		String horario = medico.getTurno();
		eliminarMedicoVacio(medico, con);
		System.out.println("\nAñada Medico sustituto.");
		System.out.println("Numero de Colegiado: \n");
		codColegiadoSustituto = sc.nextInt();
		addMedicoSustituto(codEspecialidad, codColegiadoSustituto, horario, con);

		if (medico.getNumCitas() > 0) {
			trasladarCitas(con, numColegiadoViejo, codColegiadoSustituto);
		}
	}

	/*private static void eliminarMedicoVacio(Medico medico, Conexion con) throws SQLException {
		Connection reg = con.getCon();
		PreparedStatement preparedStmt;
		String sql = "DELETE FROM centromedico.medico WHERE N_colegiado =? ";
		preparedStmt = reg.prepareStatement(sql);
		preparedStmt.setInt(1, medico.n_colegiado);
		preparedStmt.execute();

	}*/

	private static void addMedicoSustituto(int codEspecialidad, int codColegiado, String horario, Conexion con) throws SQLException {
		Scanner sc = new Scanner(System.in);
		String nombre;
		String apellidos;
		int tiempo;
		String sql;
		PreparedStatement preparedStmt;
		Connection reg = con.getCon();

		int opE = 0;

		sql = "SELECT * FROM centromedico.medico WHERE  especialidad=?;";
		preparedStmt = reg.prepareStatement(sql);
		preparedStmt.setInt(1, codEspecialidad);
		ResultSet rs = preparedStmt.executeQuery();
		rs = preparedStmt.executeQuery();
		List<String> listaMedicos = new ArrayList();
		Map<Integer, Integer> mapa = new HashMap<Integer, Integer>();
		int j = 0;
		while (rs.next()) {
			listaMedicos.add(rs.getString("nombre") + " " + rs.getString("apellidos"));
			mapa.put(j, rs.getInt("n_colegiado"));
			j++;
		}
		if (j > 1) {
			System.out.println("El n�mero m�ximo de m�dicos en esta especialidad se ha superado");
		} else {

			System.out.println("Introduzca nombre del medico: ");
			nombre = sc.nextLine();
			System.out.println("Introduzca apellidos del medico: ");
			apellidos = sc.nextLine();
			System.out.println("Introduzca tiempo del medico: ");
			tiempo = sc.nextInt();
			opE = 0;
			while (opE != 6) {
				System.out.println("Los datos del medico con n� colegiado: " + codColegiado + " son: ");
				System.out.println("-Nombre: " + nombre + "\n" + "-Apellidos: " + apellidos
						+ "\n" + "-Horario: " + horario + "\n" + "-Tiempo de consulta: " + tiempo + "\n");
				System.out.println("�Datos correctos? (elija una opcion) ");
				System.out.println("1. Nombre erroneo ");
				System.out.println("2. Apellidos erroneos ");
				System.out.println("3. Horario erroneo");
				System.out.println("4. Tiempo de consulta erroneo");
				System.out.println("5. N� colegiado erroneo");
				System.out.println("6. Todo de correcto");
				opE = sc.nextInt();
				switch (opE) {
					case 1:
						sc.nextLine();
						System.out.println("Introduzca de nuevo el nombre: ");
						nombre = sc.nextLine();
						break;
					case 2:
						sc.nextLine();
						System.out.println("Introduzca de nuevo el apellido: ");
						apellidos = sc.nextLine();
						break;
					case 3:
						sc.nextLine();
						System.out.println("No se puede modificar el horario del médico sustituto: ");
						break;
					case 4:
						sc.nextLine();
						System.out.println("Introduzca de nuevo el tiempo de consulta: ");
						tiempo = sc.nextInt();
						break;
					case 5:
						sc.nextLine();
						System.out.println("Introduzca de nuevo N� de colegiado: ");
						codColegiado = sc.nextInt();
						break;
				}
			}
			sql = "INSERT INTO centromedico.medico ( n_colegiado, nombre, apellidos, horario, tiempo_min, especialidad)"
					+ "VALUES (?, ?, ?, ?, ?, ?)";
			preparedStmt = reg.prepareStatement(sql);
			preparedStmt.setInt(1, codColegiado);
			preparedStmt.setString(2, nombre);
			preparedStmt.setString(3, apellidos);
			preparedStmt.setString(4, horario);
			preparedStmt.setInt(5, tiempo);
			preparedStmt.setInt(6, codEspecialidad);
			preparedStmt.execute();
			String codigo = String.valueOf(codColegiado);
			crearUserBD(con, codigo);
			setPermisosBD(con, codigo);
		}
	}

	/*private static void trasladarCitas(Conexion con, int codMedicoViejo, int codMedicoSustituto) throws SQLException {
		Connection reg = con.getCon();
		PreparedStatement preparedStmt;
		String sql = "UPDATE centromedico.citas SET medico=? WHERE medico=?";
		preparedStmt = reg.prepareStatement(sql);
		preparedStmt.setInt(1, codMedicoSustituto);
		preparedStmt.setInt(2, codMedicoViejo);
		preparedStmt.execute();
	}*/

	public static void main(String[] args) {
		Conexion con = null;
		Menu menu = new Menu();

		try {
			//con = new Conexion();
			
			// No muestra las opciones si no hay conexion
			if (con.getCon() != null) {

				// Solo crea la base de datos si eres root.
				if (con.getUser().equals("root")) {
					con.crearBD();
				}

				String usuario = con.getUser();

				// Muestra menu segun el tipo de usuario
				if (usuario.equals("root")) {
					menu.menuRoot(con);
				} else {
					menu.menuMedico(con);
				}
			}
		} catch (SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
	}

	public static void mostrarPacientesMedico(String nColegiado, Conexion con){
		//EN construnccion
	}
	
	/*public static void mostrarListaPacientes(Conexion con) throws SQLException {
		// Mismo funcionamiento que mostrarMedico
		// Selecciona todos los DNI de los pacientes y los muestra individualmente
		// mediante la funcion mostrarPaciente
		Connection reg = con.getCon();
		List<String> dni = new ArrayList();
		String sql;
		sql = "SELECT DNI FROM centromedico.paciente;";

		PreparedStatement preparedStmt = reg.prepareStatement(sql);
		ResultSet rs = preparedStmt.executeQuery();

		while (rs.next()) {
			dni.add(rs.getString("paciente.DNI"));
		}

		System.out.println("Lista de pacientes:");
		for (int i = 0; i < dni.size(); i++) {
			System.out.print("" + (i + 1) + ": ");
			mostrarPaciente(dni.get(i), con);
		}
	}*/

	public static void mostrarPaciente(String DNI, Conexion con) throws SQLException {
		// Selecciona y muestra por pantalla los datos del paciente con el parametro DNI
		Connection reg = con.getCon();
		String sql;
		String nombre = "";
		String apellidos = "";
		String compsegur = "";
		sql = "SELECT DNI, nombre, apellidos, compsegur FROM centromedico.paciente WHERE DNI =?;";

		PreparedStatement preparedStmt = reg.prepareStatement(sql);
		preparedStmt.setString(1, DNI);
		ResultSet rs = preparedStmt.executeQuery();

		while (rs.next()) {
			DNI = rs.getString("paciente.DNI");
			nombre = rs.getString("paciente.nombre");
			apellidos = rs.getString("paciente.apellidos");
			compsegur = rs.getString("paciente.compsegur");
		}
		System.out.println(DNI + " - " + nombre + " " + apellidos + "; " + tieneSeguro(compsegur) + ".");
	}

	public static String tieneSeguro(String nombre) {
		// Si el seguro esta vacio devuelve que no tiene, para mostrarlo por pantalla
		String resul;
		if (nombre == null || nombre.equals("NULL")) {
			resul = "No tiene seguro";
		} else {
			resul = nombre;
		}
		return resul;
	}

	/*public static void mostrarListaMedicos(Conexion con) throws SQLException {
		// Mismo funcionamiento que mostrar pacientes
		// Selecciona todos los n_colegiado de los medicos y los muestra individualmente
		// mediante la funcion mostrarMedico
		Connection reg = con.getCon();
		List<String> nColegiado = new ArrayList();
		String sql;
		sql = "SELECT medico.n_colegiado FROM centromedico.medico;";

		PreparedStatement preparedStmt = reg.prepareStatement(sql);
		ResultSet rs = preparedStmt.executeQuery();

		while (rs.next()) {
			nColegiado.add(rs.getString("medico.n_colegiado"));
		}

		System.out.println("Lista de medicos:");
		for (int i = 0; i < nColegiado.size(); i++) {
			System.out.print("" + (i + 1) + ": ");
			mostrarMedico(nColegiado.get(i), con);
		}
	}*/

	public static void mostrarMedico(String nColegiado, Conexion con) throws SQLException {
		// Selecciona y muestra por pantalla los datos del medico con el parametro n_colegiado
		Connection reg = con.getCon();
		String sql;
		String nombre = "";
		String apellidos = "";
		String horario = "";
		String tiempo = "";
		String especialidades = "";
		sql = "SELECT medico.n_colegiado, medico.nombre, medico.apellidos, medico.horario, medico.tiempo_min, medico.especialidad, especialidad.nombre "
				+ "FROM centromedico.medico, centromedico.especialidad "
				+ "WHERE medico.especialidad = especialidad.cod_especialidad "
				+ "and medico.n_colegiado = ?;";

		PreparedStatement preparedStmt = reg.prepareStatement(sql);
		preparedStmt.setString(1, nColegiado);
		ResultSet rs = preparedStmt.executeQuery();

		while (rs.next()) {
			nColegiado = rs.getString("medico.n_colegiado");
			nombre = rs.getString("medico.nombre");
			apellidos = rs.getString("medico.apellidos");
			horario = rs.getString("medico.horario").toString();
			tiempo = rs.getString("medico.tiempo_min").toString();
			especialidades = rs.getString("especialidad.nombre");
		}
		System.out.println(nColegiado + " - " + nombre + " " + apellidos + "; " + tiempo + ", " + especialidades);
	}
	
	
}
