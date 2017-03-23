
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
        String sql = "SELECT COUNT(*) AS cuenta FROM citas WHERE paciente=? ;";
        preparedStmt = reg.prepareStatement(sql);
        preparedStmt.setString(1, DNI);
        ResultSet rs = preparedStmt.executeQuery();
        while (rs.next()) {
            contCitas = rs.getInt("cuenta");
        }
    }

    public String getDNI() {
        return DNI;
    }

    public int getContCitas() {
        return contCitas;
    }

    public void addCita() {
        contCitas++;
    }

    public boolean estaBD() throws SQLException {
        boolean result = true;
        PreparedStatement preparedStmt;
        Connection reg = con.getCon();
        String dniBD = null;
        String sql = "SELECT dni FROM paciente WHERE dni=? ;";
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
    }

    public void addPacienteBD() throws SQLException {
        Scanner sc = new Scanner(System.in);
        String nombre = " ";
        String apellidos = " ";
        String nomSeguro = " ";
        String sql;
        PreparedStatement preparedStmt;
        Connection reg = con.getCon();
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
        sql = "INSERT INTO paciente ( DNI, nombre, apellidos, CompSegur)"
                + "VALUES (?,?,?,?)";
        preparedStmt = reg.prepareStatement(sql);
        preparedStmt.setString(1, DNI);
        preparedStmt.setString(2, nombre);
        preparedStmt.setString(3, apellidos);
        preparedStmt.setString(4, nomSeguro);
        preparedStmt.execute();
    }

    public boolean tieneCitas() {
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
                + "  FROM citas, medico, especialidad "
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

}
