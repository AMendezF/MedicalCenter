
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Conexion {

    private static Connection connection;
    private static final String driver = "com.mysql.jdbc.Driver";
    private static String user = "root";
    private static String password = "";
    private static final String url = "jdbc:mysql://localhost:3306/";

    public Conexion() {
        connection = null;
        Scanner sc = new Scanner(System.in);

        // Tienen que estar creados los distintos tipos de usuario en la BD
        // con los permisos correctos para que estas lineas tengan sentido
        // solo root funciona seguro
        System.out.print("\nUsuario (root/medico(codColegiado)): \n");
        user = sc.nextLine();

        System.out.print("\nPassword (vacia por defecto): \n");
        password = sc.nextLine();

        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
            if (connection != null) {
                System.out.println("Conexion establecida");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error al conectar " + e);
        }
    }

    /**
     *
     * @param connection 
     * @param sql Sentencia SQL para la consulta.
     * @return Retorna un objeto ResulSet con el resultado de la consulta.
     * @throws SQLException Arroja un error si la consulta
     * no se produjo correctamente.
     */
    public ResultSet makeQuery(String sql) throws SQLException {
        PreparedStatement preparedStmt = connection.prepareStatement(sql);
        ResultSet resulSet = preparedStmt.executeQuery();
        return resulSet;
    }

    public Connection getCon() {
        return connection;
    }

    public String getUser() {
        return user;
    }

    public void desconectar() {
        connection = null;
        if (connection == null) {
            System.out.println("Conexion terminada");
        }
    }

    public void crearBD() throws SQLException {
        PreparedStatement preparedStmt;
        Connection reg = connection;
        System.out.println("Cargando los datos ");
        String sql = "CREATE DATABASE IF NOT EXISTS centromedico;";
        preparedStmt = reg.prepareStatement(sql);
        preparedStmt.execute(); //CREAR LA BD
        System.out.print(".");

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
        System.out.print(".");

        String drop = "DROP TABLE IF EXISTS centromedico.`especialidad`";
        preparedStmt = reg.prepareStatement(drop);
        preparedStmt.execute();
        System.out.print(".");

        String crearEspecialidades = "CREATE TABLE IF NOT EXISTS centromedico.`especialidad` (\n"
                + "  `Cod_especialidad` int(11) NOT NULL,\n"
                + "  `Nombre` varchar(25) NOT NULL,\n"
                + "  `Horario` varchar(14) NOT NULL, \n"
                + "  PRIMARY KEY (`Cod_especialidad`))";
        preparedStmt = reg.prepareStatement(crearEspecialidades);
        preparedStmt.execute();
        System.out.print(".");

        String crearFicha = "CREATE TABLE IF NOT EXISTS centromedico.`ficha` (\n"
                + "  `Cod_historial` int(11) NOT NULL,\n"
                + "  `Cod_cita` varchar(20) NOT NULL,\n"
                + "  `comentario` varchar(100) DEFAULT NULL,\n"
                + "  `Dia` date NOT NULL,\n"
                + "  `Hora` time NOT NULL,\n"
                + "   PRIMARY KEY (`Cod_historial`,`Cod_cita`),\n"
                + "   KEY `ficha02` (`Cod_cita`))";
        preparedStmt = reg.prepareStatement(crearFicha);
        preparedStmt.execute();
        System.out.print(".");

        String crearHistorial = "CREATE TABLE IF NOT EXISTS centromedico.`historial` (\n"
                + "  `Cod_historial` int(11) NOT NULL,\n"
                + "  `Paciente` varchar(15) NOT NULL,\n"
                + "  `Especialidad` int(11) NOT NULL,\n"
                + "  PRIMARY KEY (`Cod_historial`),\n"
                + "  KEY `historial01` (`Paciente`),\n"
                + "  KEY `historial02` (`Especialidad`))";
        preparedStmt = reg.prepareStatement(crearHistorial);
        preparedStmt.execute();
        System.out.print(".");

        String crearMedico = "CREATE TABLE IF NOT EXISTS centromedico.`medico` (\n"
                + "  `N_colegiado` int(11) NOT NULL,\n"
                + "  `Nombre` varchar(25) NOT NULL,\n"
                + "  `Apellidos` varchar(50) NOT NULL,\n"
                + "  `Horario` varchar(10) NOT NULL,\n"
                + "  `Tiempo_min` int(11) NOT NULL,\n"
                + "  `Especialidad` int(11) NOT NULL,\n"
                + "  PRIMARY KEY (`N_colegiado`),\n"
                + "  KEY `medico01` (`Especialidad`))";
        preparedStmt = reg.prepareStatement(crearMedico);
        preparedStmt.execute();
        System.out.print(".");

        String crearPaciente = "CREATE TABLE IF NOT EXISTS centromedico.`paciente` (\n"
                + "  `DNI` varchar(15) NOT NULL,\n"
                + "  `Nombre` varchar(25) NOT NULL,\n"
                + "  `Apellidos` varchar(50) NOT NULL,\n"
                + "  `CompSegur` varchar(50) DEFAULT NULL,\n"
                + "  PRIMARY KEY (`DNI`))";
        preparedStmt = reg.prepareStatement(crearPaciente);
        preparedStmt.execute();
        System.out.print(".");

//1= mañana, 2= tarde, 3= mañana y tarde
        String iEspecialidades = "REPLACE INTO centromedico.`especialidad` (`Cod_especialidad`, `Nombre`, `Horario`) VALUES\n"
                + "(1, 'Cardiología',   '1,2,3,1,2,3,3'),\n"
                + "(2, 'Neurología',    '1,2,3,3,3,1,2'),\n"
                + "(3, 'Traumatología', '2,3,1,3,1,3,2'),\n"
                + "(4, 'Ginecología',   '2,3,3,1,3,1,3'),\n"
                + "(5, 'Urología',      '3,3,1,2,1,3,3'),"
                + "(6, 'Oftalmología',  '3,1,2,3,2,2,1'),"
                + "(7, 'M. Familia',    '3,1,2,2,3,2,1')";
        preparedStmt = reg.prepareStatement(iEspecialidades);
        preparedStmt.execute();
        System.out.print(".");

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
        preparedStmt = reg.prepareStatement(iMedicos);
        preparedStmt.execute();
        System.out.print(".");

        String iPacientes = "REPLACE INTO centromedico.paciente (DNI, Nombre, Apellidos, CompSegur) VALUES\n"
                + "('57211499B', 'Juan Antonio', 'Huesa Aranda', 'Seguros Ocaso'),\n"
                + "('15326776J', 'Felipe', 'Solano Carrillo', 'NULL'),\n"
                + "('26352431C', 'Mariano', 'Jimenez Poleo', 'Mafre'),\n"
                + "('52323400X', 'Lucia', 'Aranda Huesa', 'NULL'),\n"
                + "('67511200J', 'Berta', 'Garcia Blas', 'Mutua'),\n"
                + "('23456123X', 'Leonardo', 'Matamoros Sanz', 'Adeslas'),\n"
                + "('00675833R', 'Jose Maria', 'Gimeno De Lucas', 'Sanitas')\n";
        preparedStmt = reg.prepareStatement(iPacientes);
        preparedStmt.execute();

        String iUsuarios = "SELECT medico.n_colegiado FROM centromedico.medico";
        preparedStmt = reg.prepareStatement(iUsuarios);
        ResultSet rs = preparedStmt.executeQuery();
        List<String> nColegiado = new ArrayList();

        while (rs.next()) {
            nColegiado.add(rs.getString("medico.n_colegiado"));
        }

        String cUser;
        String pUser;
        for (int i = 0; i < nColegiado.size(); i++) {
            if (!existeUser(nColegiado.get(i))) {
                cUser = "create user ?@localhost identified by ?;";
                preparedStmt = reg.prepareStatement(cUser);
                preparedStmt.setString(1, nColegiado.get(i));
                preparedStmt.setString(2, nColegiado.get(i));
                preparedStmt.executeUpdate();
                pUser = "grant all on centromedico.* to ?@localhost;";
                preparedStmt = reg.prepareStatement(pUser);
                preparedStmt.setString(1, nColegiado.get(i));
                preparedStmt.executeUpdate();
            }
        }
    }

    private boolean existeUser(String nColegiado) throws SQLException {
        Connection reg = connection;
        PreparedStatement preparedStmt;
        boolean resul = false;

        String sql = "SELECT user from mysql.user where user = ?;";
        preparedStmt = reg.prepareStatement(sql);
        preparedStmt.setString(1, nColegiado);
        ResultSet rs = preparedStmt.executeQuery();
        while (rs.next()) {
            resul = true;
        }
        return resul;
    }
}
