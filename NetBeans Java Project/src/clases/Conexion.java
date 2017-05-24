package clases;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingConstants;

public class Conexion {

	private static Connection connection;
	private static final String driver = "com.mysql.jdbc.Driver";
	private static String user = "";
	private static String password = "";
	private static final String url = "jdbc:mysql://localhost:3306/";
	private static final String dbName = "centromedico";
	private Connection con;
	//private PreparedStatement preparedStmt;

	public Conexion(String usuario, char[] cPassword) throws SQLException, ClassNotFoundException {
		this.con = setConRoot();
		this.connection = null;
		this.user = usuario;
		setPassword(cPassword);

		Class.forName(driver);
		this.connection = DriverManager.getConnection(url, user, password);

		if (getCon().isValid(0)) {
			System.out.println("Conexion establecida");

//			if (getUser().equals("root") && !existeBD()) {
//				crearBD();
//			}
		}

	}

	public Connection getCon() {
		return this.connection;
	}

	public String getUser() {
		return this.user;
	}

	private Connection setConRoot() throws SQLException {
		return this.con = DriverManager.getConnection(url, "root", "");
	}

	/**
	 * Devuelve true si la conexion sigue siendo valida
	 *
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean esValida() throws SQLException {
		return this.connection.isValid(10);
	}

	/**
	 * Realiza una consulta SQL, que reciba por parámetro, a la BD.
	 *
	 * @param sql Sentencia SQL para la consulta.
	 * @return Retorna un objeto ResulSet con el resultado de la consulta.
	 * @throws SQLException Arroja un error si la consulta no se produjo
	 * correctamente.
	 */
	public ResultSet makeQuery(String sql) throws SQLException {
		PreparedStatement preparedStmt = connection.prepareStatement(sql);
		ResultSet resulSet = preparedStmt.executeQuery();
		return resulSet;
	}

	/**
	 * Realiza un update a la BBDD
	 *
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public int makeUpdate(String sql) throws SQLException {
		PreparedStatement preparedStmt = connection.prepareStatement(sql);
		int valor = preparedStmt.executeUpdate();
		return valor;
	}

	/**
	 * Realiza una consulta de tipo crear tabla en la BBDD
	 *
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public boolean makeExecute(String sql) throws SQLException {
		PreparedStatement preparedStmt = connection.prepareStatement(sql);
		boolean valor = preparedStmt.execute();
		return valor;
	}

	/**
	 * Cierra la conexion que se haya creado
	 *
	 * @throws SQLException
	 */
	public void desconectar() throws SQLException {
		this.connection.close();
		if (connection == null) {
			System.out.println("Conexion terminada");
		}
	}

	/**
	 * Comprueba que existe la database "centromedico"
	 *
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean existeBD() throws SQLException {
		boolean resul = false;
		String nombre = "";
		
		ResultSet rs;
		rs = con.getMetaData().getCatalogs();

		while (rs.next()) {
			nombre = rs.getString(1);
			if (dbName.equals(nombre)) {
				resul = true;
				System.out.println(nombre);
			}
		}
		return resul;
	}

	/**
	 * Convierte la contraseña a un String lo guarda en el atributo password
	 *
	 * @param cPassword
	 */
	private void setPassword(char[] cPassword) {
		this.password = "";
		for (int i = 0; i < cPassword.length; i++) {
			this.password += cPassword[i];
		}
	}

	/**
	 * Crea un backup de la base de datos especificada
	 *
	 * @param nombre
	 * @throws SQLException
	 * @throws IOException
	 */
	public void crearBackup(String nombre) throws SQLException, IOException {
		try {
			Process p = Runtime
					.getRuntime()
					.exec("C:\\xampp\\mysql\\mysqldump -u root " + nombre);

			Process d = Runtime
					.getRuntime()
					.exec("C:\\xampp\\xampp_shell.bat\\mysqldump -u root " + nombre);

			InputStream is = p.getInputStream();
			FileOutputStream fos = new FileOutputStream(nombre + "_backup.sql");
			byte[] buffer = new byte[1000];

			int leido = is.read(buffer);
			while (leido > 0) {
				fos.write(buffer, 0, leido);
				leido = is.read(buffer);
			}

			fos.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Carga un fichero.sql en una base de datos del sistema
	 *
	 * @param fichero
	 */
	public void cargarBackup(String fichero) {

	}

	/**
	 * Devuelve true si existe el medico determinado por el String "nColegiado".
	 * Sirve para crear los usuarios en la BD
	 *
	 * @param nColegiado
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean existeUser(String user) throws SQLException {
		PreparedStatement preparedStmt;
		boolean resul = false;

		String sql = "SELECT user from mysql.user where user = ?;";
		preparedStmt = con.prepareStatement(sql);
		preparedStmt.setString(1, user);
		ResultSet rs = preparedStmt.executeQuery();
		while (rs.next()) {
			resul = true;
		}
		return resul;
	}

	/**
	 * Busca en medico si existe el medico con nColegiado. Sirve para conectarse
	 * como medico a la BD.
	 *
	 * @param nColegiado
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean existeMedico(String nColegiado) throws SQLException {
		PreparedStatement preparedStmt;
		boolean resul = false;

		System.out.println("existeMedico");
		String sql = "SELECT n_colegiado from centromedico.medico where n_colegiado = ?;";
		preparedStmt = con.prepareStatement(sql);
		preparedStmt.setString(1, nColegiado);
		ResultSet rs = preparedStmt.executeQuery();
		while (rs.next()) {
			resul = true;
		}
		System.out.println("noexisteMedico");
		return resul;
	}

	/**
	 * Destruye la base de datos
	 */
	public void destruirBD() throws SQLException {
		String sql = "DROP database centromedico;";
		makeExecute(sql);   // CREAR LA BD
		System.out.println("Base de datos destruida correctamente");
	}

	/**
	 * Crea la base de datos Usa otras funciones para rellenarla y añadirle
	 * nuevas utilidades
	 *
	 * @throws SQLException
	 */
	public void crearBD() throws SQLException {
		Connection reg = connection;
		System.out.println("Cargando los datos ");

		String sql = "CREATE DATABASE IF NOT EXISTS centromedico;";
		makeExecute(sql);   // CREAR LA BD
		System.out.println("Base de datos creada correctamente");

		crearTablas();
		crearProdecimientos();
		insertarDatos();
		probarFuncionamiento(reg);
		crearUsuarios(reg);
	}

	/**
	 * Sentencias SQL que crear la estructura de tablas de la BD
	 *
	 * @param reg
	 * @throws SQLException
	 */
	private void crearTablas() throws SQLException {
		String crearCitas = "CREATE TABLE IF NOT EXISTS centromedico.`citas` (\n"
				+ "  `Cod_cita` varchar(20) NOT NULL,\n"
				+ "  `Dia` date NOT NULL,\n"
				+ "  `Hora` time NOT NULL,\n"
				+ "  `Medico` int(11) NOT NULL,\n"
				+ "  `Especialidad` int(11) NOT NULL,\n"
				+ "  `Paciente` varchar(15) NOT NULL,\n"
				+ "   PRIMARY KEY (`Cod_cita`), "
				+ "   KEY `citas01` (`Especialidad`),\n"
				+ "   KEY `citas02` (`Medico`),\n"
				+ "   KEY `citas03` (`Paciente`)"
				+ ")";
		makeExecute(crearCitas);

		String drop = "DROP TABLE IF EXISTS centromedico.`especialidad`";
		makeExecute(drop);

		String crearEspecialidades = "CREATE TABLE IF NOT EXISTS centromedico.`especialidad` (\n"
				+ "  `Cod_especialidad` int(11) NOT NULL,\n"
				+ "  `Nombre` varchar(50) NOT NULL,\n"
				+ "  `Horario` varchar(50) NOT NULL, \n"
				+ "  PRIMARY KEY (`Cod_especialidad`))";
		makeExecute(crearEspecialidades);

		String crearFicha = "CREATE TABLE IF NOT EXISTS centromedico.`ficha` (\n"
				+ "  `Cod_historial` int(11) NOT NULL,\n"
				+ "  `Cod_cita` varchar(20) NOT NULL,\n"
				+ "  `comentario` varchar(5000) DEFAULT NULL,\n"
				+ "  `Dia` date NOT NULL,\n"
				+ "  `Hora` time NOT NULL,\n"
				+ "   PRIMARY KEY (`Cod_historial`,`Cod_cita`),\n"
				+ "   KEY `ficha02` (`Cod_cita`))";
		makeExecute(crearFicha);

		String crearHistorial = "CREATE TABLE IF NOT EXISTS centromedico.`historial` (\n"
				+ "  `Cod_historial` int(11) NOT NULL AUTO_INCREMENT,\n"
				+ "  `Paciente` varchar(15) NOT NULL,\n"
				+ "  `Especialidad` int(11) NOT NULL,\n"
				+ "  PRIMARY KEY (`Cod_historial`),\n"
				+ "  KEY `historial01` (`Paciente`),\n"
				+ "  KEY `historial02` (`Especialidad`))";
		makeExecute(crearHistorial);

		String crearHistorialBorrado = "CREATE TABLE IF NOT EXISTS centromedico.`historial_borrado` (\n"
				+ "  `Cod_historial` int(11) NOT NULL,\n"
				+ "  `Paciente` varchar(15) NOT NULL,\n"
				+ "  `Especialidad` int(11) NOT NULL,\n"
				+ "  PRIMARY KEY (`Cod_historial`),\n"
				+ "  KEY `historial01` (`Paciente`),\n"
				+ "  KEY `historial02` (`Especialidad`))";
		makeExecute(crearHistorialBorrado);

		String crearMedico = "CREATE TABLE IF NOT EXISTS centromedico.`medico` (\n"
				+ "  `N_colegiado` int(11) NOT NULL,\n"
				+ "  `Nombre` varchar(50) NOT NULL,\n"
				+ "  `Apellidos` varchar(50) NOT NULL,\n"
				+ "  `Horario` varchar(50) NOT NULL,\n"
				+ "  `Tiempo_min` int(11) NOT NULL,\n"
				+ "  `Especialidad` int(11) NOT NULL,\n"
				+ "  PRIMARY KEY (`N_colegiado`),\n"
				+ "  KEY `medico01` (`Especialidad`))";
		makeExecute(crearMedico);

		String crearPaciente = "CREATE TABLE IF NOT EXISTS centromedico.`paciente` (\n"
				+ "  `DNI` varchar(15) NOT NULL,\n"
				+ "  `Nombre` varchar(50) NOT NULL,\n"
				+ "  `Apellidos` varchar(50) NOT NULL,\n"
				+ "  `CompSegur` varchar(50) DEFAULT NULL,\n"
				+ "  `Telefono`  varchar(15) DEFAULT NULL, \n"
				+ "  `Direccion` varchar(100) DEFAULT NULL, \n"
				+ "  PRIMARY KEY (`DNI`))";
		makeExecute(crearPaciente);

		String crearPacienteBorrado = "CREATE TABLE IF NOT EXISTS centromedico.`paciente_borrado` (\n"
				+ "  `DNI` varchar(15) NOT NULL,\n"
				+ "  `Nombre` varchar(50) NOT NULL,\n"
				+ "  `Apellidos` varchar(50) NOT NULL,\n"
				+ "  `CompSegur` varchar(50) DEFAULT NULL,\n"
				+ "  `Telefono`  varchar(15) DEFAULT NULL, \n"
				+ "  `Direccion` varchar(100) DEFAULT NULL, \n"
				+ "  PRIMARY KEY (`DNI`))";
		makeExecute(crearPacienteBorrado);

		String crearHorarioLunes = "CREATE TABLE IF NOT EXISTS centromedico.`horario_lunes` ("
				+ "`Cod_especialidad` int(11) NOT NULL, "
				+ "`Horario` varchar(50) NOT NULL, "
				+ "KEY `horario01` (`Cod_especialidad`))";
		makeExecute(crearHorarioLunes);

		String crearHorarioMartes = "CREATE TABLE IF NOT EXISTS centromedico.`horario_martes` ("
				+ "`Cod_especialidad` int(11) NOT NULL, "
				+ "`Horario` varchar(50) NOT NULL,"
				+ "KEY `horario01` (`Cod_especialidad`))";
		makeExecute(crearHorarioMartes);

		String crearHorarioMiercoles = "CREATE TABLE IF NOT EXISTS centromedico.`horario_miercoles` ("
				+ "`Cod_especialidad` int(11) NOT NULL, "
				+ "`Horario` varchar(50) NOT NULL,"
				+ "KEY `horario01` (`Cod_especialidad`))";
		makeExecute(crearHorarioMiercoles);

		String crearHorarioJueves = "CREATE TABLE IF NOT EXISTS centromedico.`horario_jueves` ("
				+ "`Cod_especialidad` int(11) NOT NULL, "
				+ "`Horario` varchar(50) NOT NULL,"
				+ "KEY `horario01` (`Cod_especialidad`))";
		makeExecute(crearHorarioJueves);

		String crearHorarioViernes = "CREATE TABLE IF NOT EXISTS centromedico.`horario_viernes` ("
				+ "`Cod_especialidad` int(11) NOT NULL, "
				+ "`Horario` varchar(50) NOT NULL,"
				+ "KEY `horario01` (`Cod_especialidad`))";
		makeExecute(crearHorarioViernes);

		String crearHorarioSabado = "CREATE TABLE IF NOT EXISTS centromedico.`horario_sabado` ("
				+ "`Cod_especialidad` int(11) NOT NULL, "
				+ "`Horario` varchar(50) NOT NULL,"
				+ "KEY `horario01` (`Cod_especialidad`))";
		makeExecute(crearHorarioSabado);

		String crearHorarioDomingo = "CREATE TABLE IF NOT EXISTS centromedico.`horario_domingo` ("
				+ "`Cod_especialidad` int(11) NOT NULL, "
				+ "`Horario` varchar(50) NOT NULL,"
				+ "KEY `horario01` (`Cod_especialidad`))";
		makeExecute(crearHorarioDomingo);

		System.out.println("Tablas creadas correctamente");
	}

	/**
	 * Se crean los distintos procedimientos y triggers en la base de datos para
	 * simplificar el codigo en java. Estas funcionalidades nuevas, borrar y
	 * reinsertar pacientes e historiales, se ocupa la BD de gestionarlas
	 * correctamente
	 *
	 * @param reg
	 * @throws SQLException
	 */
	private void crearProdecimientos() throws SQLException {
		// Antes de borrar un paciente activo, lo inserta en la tabla paciente_borrado
		String insertarEnPacienteBorrado = "CREATE TRIGGER centromedico.insertar_en_paciente_borrado BEFORE DELETE ON centromedico.paciente FOR EACH ROW "
				+ " INSERT INTO centromedico.paciente_borrado (DNI, Nombre, Apellidos, CompSegur, Telefono, Direccion)\n"
				+ " values (OLD.DNI, OLD.Nombre, OLD.Apellidos, OLD.CompSegur, OLD.Telefono, OLD.Direccion);";
		makeUpdate(insertarEnPacienteBorrado);

		// Antes de borrar un paciente inactivo, lo inserta en la tabla paciente
		String reinsertarEnPaciente = "CREATE TRIGGER centromedico.reinsertar_en_paciente BEFORE DELETE ON centromedico.paciente_borrado FOR EACH ROW "
				+ " INSERT INTO paciente (DNI, Nombre, Apellidos, CompSegur, Telefono, Direccion)\n"
				+ " values (OLD.DNI, OLD.Nombre, OLD.Apellidos, OLD.CompSegur, OLD.Telefono, OLD.Direccion);";
		makeUpdate(reinsertarEnPaciente);

		// Despues de borrar un paciente activo, borra el historial de dicho paciente
		String borrarDeHistorial = "CREATE TRIGGER centromedico.borrar_de_historial AFTER DELETE ON centromedico.paciente FOR EACH ROW "
				+ " DELETE FROM centromedico.historial WHERE Paciente = OLD.DNI;";
		makeUpdate(borrarDeHistorial);

		// Antes de borrar el historial del paciente activo, lo inserta en historial_borrado
		String insertarEnHistorialBorrado = "CREATE TRIGGER centromedico.insertar_en_historial_borrado BEFORE DELETE ON centromedico.historial FOR EACH ROW "
				+ " INSERT INTO centromedico.historial_borrado (Cod_historial, Paciente, Especialidad)\n"
				+ " values (OLD.Cod_historial, OLD.Paciente, OLD.Especialidad);";
		makeUpdate(insertarEnHistorialBorrado);

		// Despues de reinsertar un paciente inactivo, borra el historial de historial_borrado
		String borrarDeHistorialBorrado = "CREATE TRIGGER centromedico.borrar_de_historial_borrado AFTER DELETE ON centromedico.paciente_borrado FOR EACH ROW "
				+ " DELETE FROM centromedico.historial_borrado WHERE Paciente = OLD.DNI;";
		makeUpdate(borrarDeHistorialBorrado);

		// Antes de borrar de historial_borrado, lo reinserta en historial
		String reinsertarEnHistorial = "CREATE TRIGGER centromedico.reinsertar_en_historial BEFORE DELETE ON centromedico.historial_borrado FOR EACH ROW "
				+ " INSERT INTO centromedico.historial (Cod_historial, Paciente, Especialidad)\n"
				+ " values (OLD.Cod_historial, OLD.Paciente, OLD.Especialidad);";
		makeUpdate(reinsertarEnHistorial);

		System.out.println("Procedimientos creados correctamente");
	}

	/**
	 * Sentencias SQL que rellenan las tablas de la BD
	 *
	 * @param reg
	 * @throws SQLException
	 */
	private void insertarDatos() throws SQLException {
		//1= mañana, 2= tarde, 3= mañana y tarde
		String iEspecialidades = "REPLACE INTO centromedico.`especialidad` (`Cod_especialidad`, `Nombre`, `Horario`) VALUES\n"
				+ "(1, 'Cardiología',   '1,2,3,1,2,3,3'),\n"
				+ "(2, 'Neurología',    '1,2,3,3,3,1,2'),\n"
				+ "(3, 'Traumatología', '2,3,1,3,1,3,2'),\n"
				+ "(4, 'Ginecología',   '2,3,3,1,3,1,3'),\n"
				+ "(5, 'Urología',      '3,3,1,2,1,3,3'),"
				+ "(6, 'Oftalmología',  '3,1,2,3,2,2,1'),"
				+ "(7, 'M. Familia',    '3,1,2,2,3,2,1')";
		makeUpdate(iEspecialidades);

		String iMedicos = "REPLACE INTO centromedico.`medico` (`N_colegiado`, `Nombre`, `Apellidos`, `Horario`, `Tiempo_min`, `Especialidad`) VALUES\n"
				+ "(103456, 'Juana', 'Hermoso', 'Tarde', 10, 7),\n"
				+ "(120056, 'Laura', 'Rodriguez', 'Mañana', 10, 3),\n"
				+ "(120356, 'Victor', 'Toro', 'Mañana', 10, 7),\n"
				+ "(123456, 'Alfonso', 'Garcia', 'Mañana', 15, 1),\n"
				+ "(123458, 'Maria', 'Garcia', 'Mañana', 10, 4),\n"
				+ "(123656, 'Alfonsa', 'Lopez', 'Tarde', 20, 2),\n"
				+ "(123786, 'Roberto', 'Ramirez', 'Tarde', 15, 1),\n"
				+ "(126156, 'Carla', 'Izquierdo', 'Tarde', 10, 4),\n"
				+ "(129656, 'Julian', 'Garcia', 'Mañana', 20, 2),\n"
				+ "(256975, 'Alfonso', 'Sanchez', 'Tarde', 10, 3),\n"
				+ "(129777, 'Pablo', 'Hernanz', 'Mañana', 15, 5),\n"
				+ "(129999, 'Julian', 'Perez', 'Tarde', 15, 5),\n"
				+ "(129435, 'Rosa', 'Sánchez', 'Mañana', 15, 6),\n"
				+ "(129589, 'Román', 'Perez', 'Tarde', 15, 6)";
		makeUpdate(iMedicos);

		String iPacientes = "REPLACE INTO centromedico.paciente (DNI, Nombre, Apellidos, CompSegur, Telefono, Direccion) VALUES\n"
				+ "('57211499B', 'Juan Antonio', 'Huesa Aranda', 'Seguros Ocaso', '6292743542', 'Calle Augusto N 20, Guadalajara'),\n"
				+ "('15326776J', 'Felipe', 'Solano Carrillo', 'NULL', '657575757', 'Bulevar de Amancio N 4, 3ºB, Madrid'),\n"
				+ "('26352431C', 'Mariano', 'Jimenez Poleo', 'Mafre', '987523547', 'Calle del generalisimo Nº80, Bloque 3, 2ºD, Berrueco, Madrid'),\n"
				+ "('52323400X', 'Lucia', 'Aranda Huesa', 'NULL', '635894125', 'Calle Alcala N 228, 9ºB, Madrid'),\n"
				+ "('67511200J', 'Berta', 'Garcia Blas', 'Mutua', '648512359', 'Paseo de Paquito embalsador Nº52, 3ºB, Alcorcón, Madrid'),\n"
				+ "('23456123X', 'Leonardo', 'Matamoros Sanz', 'Adeslas', '695423157', 'Calle Augusto N 10, Guadalajara'),\n"
				+ "('34126666W', 'Alejandro', 'Mendes Guela', 'Adeslas', '614879604', 'Avenida constitución Nº94, escalera 2, 5ºB, Vallecas, Madrid'),\n"
				+ "('19951996W', 'Pablo', 'Meson De Toro', 'NULL', '621999999', 'Calle Guadalajara N 7, Toledo'),\n"
				+ "('23488209B', 'Ismael', 'Garcia Garcia', 'Sanitas', '622135678', 'Calle de Sonsoles de las neveras Nº32, 3ºE, Escorial, Madrid'),\n"
				+ "('00675833R', 'Jose Maria', 'Gimeno De Lucas', 'Sanitas', '612345674', 'Calle de Abraham Mateo Nº21, Escalera izquierda, 4ºD, Madrid')";
		makeUpdate(iPacientes);

		String horarioLunes = "INSERT INTO centromedico.horario_lunes (Cod_especialidad, Horario) VALUES "
				+ "(1, 'Mañana'), "
				+ "(2, 'Mañana'), "
				+ "(3, 'Tarde'), "
				+ "(4, 'Tarde'), "
				+ "(5, 'Mañana'), "
				+ "(5, 'Tarde'), "
				+ "(6, 'Mañana'), "
				+ "(6, 'Tarde'), "
				+ "(7, 'Mañana'), "
				+ "(7, 'Tarde')";
		makeUpdate(horarioLunes);

		String horarioMartes = "INSERT INTO centromedico.horario_martes (Cod_especialidad, Horario) VALUES "
				+ "(1, 'Tarde'), "
				+ "(2, 'Tarde'), "
				+ "(3, 'Mañana'), "
				+ "(3, 'Tarde'), "
				+ "(4, 'Mañana'), "
				+ "(4, 'Tarde'), "
				+ "(5, 'Mañana'), "
				+ "(5, 'Tarde'), "
				+ "(6, 'Mañana'), "
				+ "(7, 'Mañana')";
		makeUpdate(horarioMartes);

		String horarioMiercoles = "INSERT INTO centromedico.horario_miercoles (Cod_especialidad, Horario) VALUES "
				+ "(1, 'Mañana'), "
				+ "(1, 'Tarde'), "
				+ "(2, 'Mañana'), "
				+ "(2, 'Tarde'), "
				+ "(3, 'Mañana'), "
				+ "(4, 'Mañana'), "
				+ "(4, 'Tarde'), "
				+ "(5, 'Mañana'), "
				+ "(6, 'Tarde'), "
				+ "(7, 'Tarde')";
		makeUpdate(horarioMiercoles);

		String horarioJueves = "INSERT INTO centromedico.horario_jueves (Cod_especialidad, Horario) VALUES "
				+ "(1, 'Mañana'), "
				+ "(2, 'Mañana'), "
				+ "(2, 'Tarde'), "
				+ "(3, 'Mañana'), "
				+ "(3, 'Tarde'), "
				+ "(4, 'Mañana'), "
				+ "(5, 'Tarde'), "
				+ "(6, 'Mañana'), "
				+ "(6, 'Tarde'), "
				+ "(7, 'Tarde')";
		makeUpdate(horarioJueves);

		String horarioViernes = "INSERT INTO centromedico.horario_viernes (Cod_especialidad, Horario) VALUES "
				+ "(1, 'Tarde'), "
				+ "(2, 'Mañana'), "
				+ "(2, 'Tarde'), "
				+ "(3, 'Mañana'), "
				+ "(4, 'Mañana'), "
				+ "(4, 'Tarde'), "
				+ "(5, 'Mañana'), "
				+ "(6, 'Tarde'), "
				+ "(7, 'Mañana'), "
				+ "(7, 'Tarde')";
		makeUpdate(horarioViernes);

		String horarioSabado = "INSERT INTO centromedico.horario_sabado (Cod_especialidad, Horario) VALUES "
				+ "(1, 'Mañana'), "
				+ "(1, 'Tarde'), "
				+ "(2, 'Mañana'), "
				+ "(3, 'Mañana'), "
				+ "(3, 'Tarde'), "
				+ "(4, 'Mañana'), "
				+ "(5, 'Mañana'), "
				+ "(5, 'Tarde'), "
				+ "(6, 'Tarde'), "
				+ "(7, 'Tarde')";
		makeUpdate(horarioSabado);

		String horarioDomingo = "INSERT INTO centromedico.horario_domingo (Cod_especialidad, Horario) VALUES "
				+ "(1, 'Mañana'), "
				+ "(1, 'Tarde'), "
				+ "(2, 'Tarde'), "
				+ "(3, 'Tarde'), "
				+ "(4, 'Mañana'), "
				+ "(4, 'Tarde'), "
				+ "(5, 'Mañana'), "
				+ "(5, 'Tarde'), "
				+ "(6, 'Mañana'), "
				+ "(7, 'Mañana')";
		makeUpdate(horarioDomingo);

		String iCitasPasadas = "INSERT INTO centromedico.citas (Cod_cita, Dia, Hora, Medico, Especialidad, Paciente ) VALUES "
				+ "('1', '2017-05-20', '10:00', 123456, 1, '00675833R'), "
				+ "('2', '2017-05-21', '15:20', 123656, 2, '19951996W'), "
				+ "('3', '2017-05-21', '16:10', 126156, 4, '23456123X'), "
				+ "('4', '2017-05-21', '16:20', 126156, 4, '26352431C'), "
				+ "('5', '2017-05-21', '16:50', 126156, 4, '34126666W'), "
				+ "('6', '2017-05-21', '16:50', 126156, 4, '52323400X'), "
				+ "('7', '2017-05-21', '10:15', 129777, 5, '57211499B'), "
				+ "('8', '2017-05-21', '10:30', 129777, 5, '67511200J');";
		makeUpdate(iCitasPasadas);

		String iHistorial = "INSERT INTO centromedico.historial (Especialidad, Paciente) VALUES "
				+ "(1, '00675833R'), "
				+ "(2, '19951996W'), "
				+ "(4, '23456123X'), "
				+ "(4, '26352431C'), "
				+ "(4, '34126666W'), "
				+ "(4, '52323400X'), "
				+ "(5, '57211499B'), "
				+ "(5, '67511200J')";
		makeUpdate(iHistorial);

		String iFichas = "INSERT INTO centromedico.ficha (Cod_historial, Cod_cita, comentario, Dia, Hora) VALUES "
				+ "(1, 1, '[2017-05-20, 10:00:00, 123456] Tensión alta por consumo frecuente de MontainDews y Doritos. Se recomienda abstinencia de dicha dieta.', "
				+ " '2017-05-20', '10:00:00'), "
				+ "(2, 2, '[2017-05-21, 15:20:00, 123656] El alto consumo de cannabis está reduciendo su sinapsis causando episodios chungos de epilepsia.', "
				+ " '2017-05-21', '15:20:00'), "
				+ "(3, 3, '[2017-05-21, 16:10:00, 126156] Se ha confundido de consulta... No me pagan lo suficiente para esto.', "
				+ " '2017-05-21', '16:10:00'), "
				+ "(4, 4, '', '2017-05-21', '16:20:00'), "
				+ "(5, 5, '[2017-05-21, 16:50:00, 126156] Se encuentra mal.', '2017-05-21', '16:50:00'), "
				+ "(6, 6, '[2017-05-21, 16:50:00, 126156] Revisión por molestia dolorosa. Se recetan unas píldoras.\n[2017-05-25, 19:30:00, 126156] Se descubre un error en la receta.', '2017-05-21', '16:50:00'), "
				+ "(7, 7, '[2017-05-21, 10:15:00, 129777] Revisión rutinaria sin novedades.', '2017-05-21', '10:15:00'), "
				+ "(8, 8, '[2017-05-21, 10:30:00, 129777] Nada nuevo.', '2017-05-21', '10:30:00');";

		makeUpdate(iFichas);

		System.out.println("Datos insertados correctamente");
	}

	/**
	 * Borra algunos pacientes elegidos en la base de datos para comprobar el
	 * funcionamiento de los triggers y que la base de datos mantenga la
	 * coherencia de los datos
	 *
	 * @param reg
	 * @throws SQLException
	 */
	private void probarFuncionamiento(Connection reg) throws SQLException {
		PreparedStatement preparedStmt;
		String primerPaciente = "23488209B";
		String segundoPaciente = "34126666W";
		String tercerPaciente = "15326776J";
		String borrarPaciente = "delete from centromedico.paciente where DNI = ?";

		preparedStmt = reg.prepareStatement(borrarPaciente);
		preparedStmt.setString(1, primerPaciente);
		preparedStmt.executeUpdate();

		preparedStmt = reg.prepareStatement(borrarPaciente);
		preparedStmt.setString(1, segundoPaciente);
		preparedStmt.executeUpdate();

		preparedStmt = reg.prepareStatement(borrarPaciente);
		preparedStmt.setString(1, tercerPaciente);
		preparedStmt.executeUpdate();

		String reinsertarPaciente = "delete from centromedico.paciente_borrado where DNI = ?";

		preparedStmt = reg.prepareStatement(reinsertarPaciente);
		preparedStmt.setString(1, segundoPaciente);
		preparedStmt.executeUpdate();

		System.out.println("Pruebas realizadas correctamente");

	}

	/**
	 * Sentencias SQL que crean usuarios y asignan permisos en la BD
	 *
	 * @param reg
	 * @throws SQLException
	 */
	private void crearUsuarios(Connection reg) throws SQLException {
		if (!existeUser("Gestor")) {
			String crearGestor = "create user Gestor@localhost identified by 'gestor';";
			makeUpdate(crearGestor);
			String permisosGestor = "grant all privileges on *.* to Gestor@localhost;";
			makeUpdate(permisosGestor);
			String masPermisos = "grant grant option on *.* to Gestor@localhost;";
			makeUpdate(masPermisos);
		}

		String iUsuarios = "SELECT medico.n_colegiado FROM centromedico.medico";
		ResultSet rs = makeQuery(iUsuarios);
		List<String> nColegiado = new ArrayList();

		while (rs.next()) {
			nColegiado.add(rs.getString("medico.n_colegiado"));
		}

		String createUser;
		String privilegiosUser;
		PreparedStatement preparedStmt;
		for (int i = 0; i < nColegiado.size(); i++) {
			if (!existeUser(nColegiado.get(i))) {
				createUser = "create user ?@localhost identified by ?;";
				preparedStmt = reg.prepareStatement(createUser);
				preparedStmt.setString(1, nColegiado.get(i));
				preparedStmt.setString(2, nColegiado.get(i));
				preparedStmt.executeUpdate();
				privilegiosUser = "grant all on centromedico.* to ?@localhost;";
				preparedStmt = reg.prepareStatement(privilegiosUser);
				preparedStmt.setString(1, nColegiado.get(i));
				preparedStmt.executeUpdate();
			}
		}
		System.out.println("Usuarios añadidos correctamente\n");
	}

}

/*
 select * from centromedico.paciente;
 select * from centromedico.paciente_borrado;
 select * from centromedico.historial;
 select * from centromedico.historial_borrado;
 */
