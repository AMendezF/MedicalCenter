package clases;

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
	private PreparedStatement preparedStmt;

	public Conexion(String usuario, char[] cPassword) {
		this.connection = null;
		this.user = usuario;
		setPassword(cPassword);

		try {
			Class.forName(driver);
			this.connection = DriverManager.getConnection(url, user, password);

			if (getCon() != null) {
				System.out.println("Conexion establecida");

				if (getUser().equals("root") && !existeBD()) {
					crearBD();
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Error al conectar " + e);
		}
	}

	public Connection getCon() {
		return this.connection;
	}

	public String getUser() {
		return this.user;
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
		rs = connection.getMetaData().getCatalogs();

		while (rs.next()) {
			nombre = rs.getString(1);
			if (dbName.equals(nombre)) {
				resul = true;
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
	 * Devuelve true si existe el medico determinado por el String "nColegiado".
	 * Sirve para crear los usuarios en la BD
	 *
	 * @param nColegiado
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean existeUser(String nColegiado) throws SQLException {
		PreparedStatement preparedStmt;
		boolean resul = false;

		String sql = "SELECT user from mysql.user where user = ?;";
		preparedStmt = connection.prepareStatement(sql);
		preparedStmt.setString(1, nColegiado);
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

		String sql = "SELECT n_colegiado from centromedico.medico where n_colegiado = ?;";
		preparedStmt = connection.prepareStatement(sql);
		preparedStmt.setString(1, nColegiado);
		ResultSet rs = preparedStmt.executeQuery();
		while (rs.next()) {
			resul = true;
		}
		return resul;
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
		preparedStmt = reg.prepareStatement(sql);
		preparedStmt.execute(); //CREAR LA BD
		System.out.println("Base de datos creada correctamente");
		crearTablas(reg);
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
	private void crearTablas(Connection reg) throws SQLException {
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
		preparedStmt = reg.prepareStatement(crearCitas);
		preparedStmt.execute();

		String drop = "DROP TABLE IF EXISTS centromedico.`especialidad`";
		preparedStmt = reg.prepareStatement(drop);
		preparedStmt.execute();

		String crearEspecialidades = "CREATE TABLE IF NOT EXISTS centromedico.`especialidad` (\n"
				+ "  `Cod_especialidad` int(11) NOT NULL,\n"
				+ "  `Nombre` varchar(25) NOT NULL,\n"
				+ "  `Horario` varchar(14) NOT NULL, \n"
				+ "  PRIMARY KEY (`Cod_especialidad`))";
		makeExecute(crearEspecialidades);

		String crearFicha = "CREATE TABLE IF NOT EXISTS centromedico.`ficha` (\n"
				+ "  `Cod_historial` int(11) NOT NULL,\n"
				+ "  `Cod_cita` varchar(20) NOT NULL,\n"
				+ "  `comentario` varchar(500) DEFAULT NULL,\n"
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
				+ "  `Nombre` varchar(25) NOT NULL,\n"
				+ "  `Apellidos` varchar(50) NOT NULL,\n"
				+ "  `Horario` varchar(10) NOT NULL,\n"
				+ "  `Tiempo_min` int(11) NOT NULL,\n"
				+ "  `Especialidad` int(11) NOT NULL,\n"
				+ "  PRIMARY KEY (`N_colegiado`),\n"
				+ "  KEY `medico01` (`Especialidad`))";
		makeExecute(crearMedico);

		String crearPaciente = "CREATE TABLE IF NOT EXISTS centromedico.`paciente` (\n"
				+ "  `DNI` varchar(15) NOT NULL,\n"
				+ "  `Nombre` varchar(25) NOT NULL,\n"
				+ "  `Apellidos` varchar(50) NOT NULL,\n"
				+ "  `CompSegur` varchar(50) DEFAULT NULL,\n"
				+ "  PRIMARY KEY (`DNI`))";
		makeExecute(crearPaciente);

		String crearPacienteBorrado = "CREATE TABLE IF NOT EXISTS centromedico.`paciente_borrado` (\n"
				+ "  `DNI` varchar(15) NOT NULL,\n"
				+ "  `Nombre` varchar(25) NOT NULL,\n"
				+ "  `Apellidos` varchar(50) NOT NULL,\n"
				+ "  `CompSegur` varchar(50) DEFAULT NULL,\n"
				+ "  PRIMARY KEY (`DNI`))";
		makeExecute(crearPacienteBorrado);
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
				+ " INSERT INTO centromedico.paciente_borrado (DNI, Nombre, Apellidos, CompSegur)\n"
				+ " values (OLD.DNI, OLD.Nombre, OLD.Apellidos, OLD.CompSegur);";
		makeUpdate(insertarEnPacienteBorrado);

		// Antes de borrar un paciente inactivo, lo inserta en la tabla paciente
		String reinsertarEnPaciente = "CREATE TRIGGER centromedico.reinsertar_en_paciente BEFORE DELETE ON centromedico.paciente_borrado FOR EACH ROW "
				+ " INSERT INTO paciente (DNI, Nombre, Apellidos, CompSegur)\n"
				+ " values (OLD.DNI, OLD.Nombre, OLD.Apellidos, OLD.CompSegur);";
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

		String iPacientes = "REPLACE INTO centromedico.paciente (DNI, Nombre, Apellidos, CompSegur) VALUES\n"
				+ "('57211499B', 'Juan Antonio', 'Huesa Aranda', 'Seguros Ocaso'),\n"
				+ "('15326776J', 'Felipe', 'Solano Carrillo', 'NULL'),\n"
				+ "('26352431C', 'Mariano', 'Jimenez Poleo', 'Mafre'),\n"
				+ "('52323400X', 'Lucia', 'Aranda Huesa', 'NULL'),\n"
				+ "('67511200J', 'Berta', 'Garcia Blas', 'Mutua'),\n"
				+ "('23456123X', 'Leonardo', 'Matamoros Sanz', 'Adeslas'),\n"
				+ "('34126666W', 'Alejandro', 'Mendes Guela', 'Adeslas'),\n"
				+ "('19951996W', 'Pablo', 'Meson De Toro', 'NULL'),\n"
				+ "('23488209B', 'Ismael', 'Garcia Garcia', 'Sanitas'),\n"
				+ "('00675833R', 'Jose Maria', 'Gimeno De Lucas', 'Sanitas')";
		makeUpdate(iPacientes);

		String iHistorial = "INSERT INTO centromedico.historial (Paciente, Especialidad) VALUES \n"
				+ "('57211499B', 1),\n"
				+ "('15326776J', 2),\n"
				+ "('57211499B', 2),\n"
				+ "('15326776J', 3),\n"
				+ "('57211499B', 3),\n"
				+ "('15326776J', 4),\n"
				+ "('34126666W', 1),\n"
				+ "('34126666W', 3),\n"
				+ "('19951996W', 4),\n"
				+ "('34126666W', 4)";
		makeUpdate(iHistorial);

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
