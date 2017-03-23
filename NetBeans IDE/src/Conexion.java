
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static Connection con;
    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String user= "root";
    private static final String password = "";
    private static final String url= "jdbc:mysql://localhost:3306/centromedico";

    public Conexion() {
        con=null;
        try{
            Class.forName(driver);
            con=DriverManager.getConnection(url, user, password);
            if(con!=null){
                System.out.println("Conexion establecida");
            }
        }catch (ClassNotFoundException | SQLException e){
            System.out.println("Error al conectar "+e);            
        }
    }
    
    public Connection getCon(){
        return con;
    }
    
    public void desconectar(){
        con=null;
        if(con==null){
            System.out.println("Conexion terminada");
        }
    }
    
}
