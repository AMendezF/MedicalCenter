
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        this.con=con;
        Connection reg = con.getCon();
        String sql = "SELECT COUNT(*) AS cuenta FROM citas WHERE paciente=? ;";
        preparedStmt = reg.prepareStatement(sql);
        preparedStmt.setString(1, DNI);
        ResultSet rs = preparedStmt.executeQuery();
       while (rs.next()) {
            contCitas=rs.getInt("cuenta");
        }         
    }    

    public String getDNI() {
        return DNI;
    }

    public int getContCitas() {
        return contCitas;
    }
    
    public void addCita(){
        contCitas++;
    }    
    
    public boolean estaBD() throws SQLException{
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
    
     public void addPacienteBD() throws SQLException{
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
            System.out.println("Introduzca nmbre del seguro del paciente: ");
            nomSeguro = sc.nextLine();
            sql = "INSERT INTO paciente ( DNI, nombre, apellidos, CompSegur)"
                    + "VALUES (?,?,?,?)";
            preparedStmt = reg.prepareStatement(sql);
            preparedStmt.setString(1, DNI);
            preparedStmt.setString(2, nombre);
            preparedStmt.setString(3, apellidos);
            preparedStmt.setString(4, nomSeguro);
            preparedStmt.execute();
        }
        else if (opcionS.equals("N") || opcionS.equals("n")){
            sql = "INSERT INTO paciente ( DNI, nombre, apellidos, CompSegur)"
                    + "VALUES (?,?,?,NULL)";
            preparedStmt = reg.prepareStatement(sql);
            preparedStmt.setString(1, DNI);
            preparedStmt.setString(2, nombre);
            preparedStmt.setString(3, apellidos);
            preparedStmt.execute();
        }

     }
}
