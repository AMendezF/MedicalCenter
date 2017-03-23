
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 *
 * @author Berta
 */
class Paciente {

    private String DNI;
    private int contCitas;
    private Conexion con;
    private PreparedStatement preparedStmt = null;

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
}
