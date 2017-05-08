package clases;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Berta
 */
class Paciente {

	String DNI;
	int contCitas;
	Conexion con;
	PreparedStatement preparedStmt = null;

	public Paciente(String DNI, Conexion con) throws SQLException {
		this.DNI = DNI;
		this.con = con;
		Connection reg = con.getCon();
		contCitas = getContCitas();
	}

	public String getDNI() {
		return DNI;
	}

	public int getContCitas() throws SQLException {
		Connection reg = con.getCon();
		String sql = "SELECT COUNT(*) AS cuenta FROM centromedico.citas WHERE paciente=? ;";
		preparedStmt = reg.prepareStatement(sql);
		preparedStmt.setString(1, DNI);
		ResultSet rs = preparedStmt.executeQuery();
		if (rs.next()) {
			contCitas = rs.getInt("cuenta");
		} else {
			contCitas = 0;
		}
		return contCitas;
	}

	public void addCita() {
		contCitas++;
	}

	/*
	public boolean estaBD() throws SQLException {
		boolean result = true;
		PreparedStatement preparedStmt;
		Connection reg = con.getCon();
		String dniBD = null;
		String sql = "SELECT dni FROM centromedico.paciente WHERE dni=? ;";
		preparedStmt = reg.prepareStatement(sql);
		preparedStmt.setString(1, DNI);
		ResultSet rs = preparedStmt.executeQuery();
		while (rs.next()) {
			dniBD = rs.getString("dni");
		}
		if (dniBD == null) {
			result = false;
		}
		return result;
	}*/

	public void addPacienteBD() throws SQLException {
		Scanner sc = new Scanner(System.in);
		String nombre = " ";
		String apellidos = " ";
		String nomSeguro = " ";
		String sql;
		PreparedStatement preparedStmt;
		Connection reg = con.getCon();

		while (!comprobarDNI(DNI) || estaBD()) {
			System.out.println("Introduzca DNI del paciente: ");
			DNI = sc.nextLine();
			if (estaBD()) {
				System.out.println("Error: el DNI ya esta en uso.");
			}
			if (!comprobarDNI(DNI)) {
				System.out.println("Error: el DNI no es correcto.");
			}
		}

		System.out.println("Introduzca nombre del paciente: ");
		nombre = sc.nextLine();
		System.out.println("Introduzca apellido del paciente: ");
		apellidos = sc.nextLine();
		System.out.println("¿Tiene seguro medico? (S/N)");
		String opcionS = sc.nextLine();
		if (opcionS.equals("S") || opcionS.equals("s")) {
			System.out.println("Introduzca el nombre del seguro del paciente: ");
			nomSeguro = sc.nextLine();
		} else if (opcionS.equals("N") || opcionS.equals("n")) {
			nomSeguro = null;
		}
		int opE = 0;
		while (opE != 4) {
			System.out.println("Los datos del paciente con DNI " + DNI + " son: ");
			System.out.println("-Nombre: " + nombre + "\n" + "-Apellidos: " + apellidos + "\n" + "-Seguro medico: " + nomSeguro + "\n");
			System.out.println("¿Datos correctos? (elija una opcion) ");
			System.out.println("1. Nombre erroneo ");
			System.out.println("2. Apellidos erroneos ");
			System.out.println("3. Seguro erroneo");
			System.out.println("4. Todo correcto");
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
					System.out.println("Introduzca de nuevo el nombre del seguro: ");
					nomSeguro = sc.nextLine();
					break;
			}
		}
		sql = "INSERT INTO centromedico.paciente ( DNI, nombre, apellidos, CompSegur)"
				+ "VALUES (?,?,?,?)";
		preparedStmt = reg.prepareStatement(sql);
		preparedStmt.setString(1, DNI);
		preparedStmt.setString(2, nombre);
		preparedStmt.setString(3, apellidos);
		preparedStmt.setString(4, nomSeguro);
		preparedStmt.execute();
	}

	public boolean tieneCitas() throws SQLException {
		boolean tieneCitas = false;
		if (this.getContCitas() > 0) {
			tieneCitas = true;
		}
		return tieneCitas;
	}

	public void mostrarCitas() throws SQLException {
		PreparedStatement preparedStmt;
		Connection reg = con.getCon();
		String sql;

		sql = "SELECT medico.nombre,citas.medico, medico.n_colegiado, especialidad.nombre, citas.cod_cita, citas.hora, citas.dia"
				+ "  FROM centromedico.citas, centromedico.medico, centromedico.especialidad "
				+ "WHERE paciente=? AND medico.n_colegiado=citas.medico "
				+ "AND medico.especialidad= especialidad.cod_especialidad";
		preparedStmt = reg.prepareStatement(sql);
		preparedStmt.setString(1, getDNI());
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
	}

	public String tieneSeguro(String nombre) {
		String resul;
		if (nombre == null || nombre.equals("NULL")) {
			resul = "No tiene seguro";
		} else {
			resul = nombre;
		}
		return resul;
	}

	public String getNombre() throws SQLException {
		String resul = "";
		Connection reg = con.getCon();
		String sql = "SELECT nombre FROM centromedico.paciente WHERE  DNI=?";
		preparedStmt = reg.prepareStatement(sql);
		preparedStmt.setString(1, DNI);
		ResultSet rs = preparedStmt.executeQuery();
		if (rs.next()) {
			resul = rs.getString("nombre");
		}
		return resul;
	}

	public String getApellidos() throws SQLException {
		String resul = "";
		Connection reg = con.getCon();
		String sql = "SELECT apellido FROM centromedico.paciente WHERE  DNI=?";
		preparedStmt = reg.prepareStatement(sql);
		preparedStmt.setString(1, DNI);
		ResultSet rs = preparedStmt.executeQuery();
		if (rs.next()) {
			resul = rs.getString("apellidos");
		}
		return resul;
	}

	public String getSeguro() throws SQLException {
		String resul = "";
		Connection reg = con.getCon();
		String sql = "SELECT CompSegur FROM centromedico.paciente WHERE  DNI=?";
		preparedStmt = reg.prepareStatement(sql);
		preparedStmt.setString(1, DNI);
		ResultSet rs = preparedStmt.executeQuery();
		if (rs.next()) {
			resul = rs.getString("CompSegur");
		}
		return resul;
	}

	public void modificar() throws SQLException {
		Scanner sc = new Scanner(System.in);
		int op = 0;
		String dato;
		PreparedStatement preparedStmt;
		String valorActual = this.getNombre();

		System.out.println("Modificar este paciente:");

		while (op < 1 || op > 4) {
			System.out.println("¿Que datos del paciente quiere modificar");
			System.out.println("\t1- Nombre ");
			System.out.println("\t2- Apellidos ");
			System.out.println("\t3- Seguro Médico ");
			System.out.println("\nSeleccione una opcion:");
			op = sc.nextInt();
		}
		if (op == 1) {
			dato = "Nombre";
		} else if (op == 2) {
			dato = "Apellidos";
		} else {
			dato = "CompSegur";
		}
		sc.nextLine();
		System.out.println("Introduce el nuevo dato correspondiente a " + dato + " del paciente");
		String valorNuevo = sc.nextLine();

		Connection reg = con.getCon();
		String sql = "UPDATE centromedico.paciente SET " + dato + "=? WHERE nombre=?";
		preparedStmt = reg.prepareStatement(sql);
		preparedStmt.setString(1, valorNuevo);
		preparedStmt.setString(2, valorActual);
		preparedStmt.execute();
	}

	public String cogerPaciente(Conexion con) {
		// Pide el dni de un paciente, comprueba que el dni sea correcto
		// y que el paciente exista en la base de datos
		// Da la opcion de crear el paciente en caso de que no exista
		// Devuelve el dni
		Scanner sc = new Scanner(System.in);
		String dni = "";
		boolean result = false;
		boolean correcto = false;
		try {
			do {
				do {
					System.out.println("\n\nSeleccionar  paciente: ");
					System.out.println("Introduzca el DNI del paciente: ");
					dni = sc.nextLine().toUpperCase();
					if (comprobarDNI(dni)) {
						correcto = true;
					} else {
						System.out.println("ERROR: DNI no válido.");
					}
				} while (!correcto);

				this.DNI = dni;
				result = estaBD();

				if (!result) {

					String resp;
					System.out.println("El paciente no está registrado en el sistema.");
					System.out.println("¿Desea registrar el paciente? (S/N)");

					resp = sc.nextLine().toLowerCase();
					if (resp.equals("s")) {
						addPacienteBD();
						result = true;
					}
				}
			} while (!result);

		} catch (SQLException ex) {
			System.out.println(ex.fillInStackTrace());
		}
		return dni;
	}

	public boolean comprobarDNI(String DNI) {
		// Comprueba que el DNI tenga los caracteres correctos
		// y que la letra sea valida
		boolean resul = false;
		if (DNI.matches("[A-Z0-9][0-9]{7}[A-Z]")) {
			String letrasDNI = "TRWAGMYFPDXBNJZSQVHLCKE";
			String miNumero = DNI.substring(0, 8);

			int num = Integer.parseInt(miNumero);
			int mod = num % (letrasDNI.length());

			char miLetra = DNI.charAt(8);
			char nuevaLetra = letrasDNI.charAt(mod);

			if (nuevaLetra == miLetra) {
				resul = true;
			}
		}
		return resul;
	}
}
