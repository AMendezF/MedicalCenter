package clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author @author niels_hat_master_fucker
 */
public class Historial extends ArrayList<Ficha>{
    
    private int codigoHistorial;
    private String DNIPaciente;
    private int especialidad;
    private Conexion conexion;
    
    /**
     * @param DNIPaciente
     * @param especialidad
     * @param conexion
     * @throws SQLException 
     * Comprobamos presencia en la BD de un historial con el DNI y la 
     * especialidad suministrada. En tal caso se obtiene su codigo de historial.
     * En caso contrario se genera otro código.
     * Se igualan el valor del codigo y acto seguido se registra la row en la 
     * tabla Historial.
     */
    public Historial(String DNIPaciente, int especialidad, Conexion conexion) throws SQLException{
        this.conexion = conexion;
        this.codigoHistorial = comprobarPresenciaBD(DNIPaciente, especialidad);
        if(codigoHistorial == 0)//Si no esta en la BD se genera un código.
            obtenerCodigo();
        this.DNIPaciente = DNIPaciente;
        this.especialidad = especialidad;
        //TODO:Insertar row a BD
    }
    /**
     * 
     * @param DNIPaciente
     * @param especialidad
     * @return
     * @throws SQLException 
     * Descripción: Se busca un row en la tabla Historial donde coincidan 
     * DNIPaciente con la especialidad.
     */
    private int comprobarPresenciaBD(String DNIPaciente, int especialidad) throws SQLException{
        PreparedStatement preparedStmt;
        Connection reg = conexion.getCon();
        String sql = "SELECT Cod_historial FROM centromedico.historial WHERE paciente=? AND especialidad=? ;";
        preparedStmt = reg.prepareStatement(sql);
        preparedStmt.setString(1, DNIPaciente);
        preparedStmt.setInt(2, especialidad);
        ResultSet rs = preparedStmt.executeQuery();
        rs.next();

        return rs.getInt("Cod_historial");
    }

    /**
     * @return
     * @throws SQLException 
     * Se busca el código con el valor más alto en la tabla Historial para
     * incrementarlo en uno y después adoptarlo a este historial.
     */
    private int obtenerCodigo() throws SQLException{
        PreparedStatement preparedStmt;
        Connection reg = conexion.getCon();
        String sql = "SELECT max(Cod_historial) FROM historial";
        preparedStmt = reg.prepareStatement(sql);
        ResultSet rs = preparedStmt.executeQuery();
        rs.next();
        codigoHistorial = rs.getInt("max(Cod_historial)") + 1;
        return codigoHistorial;
    }
    
    public String mostrarFichas(){
        //Se accede a la tabla Fichas para 
        return "";
    }
    
    public void modificarFicha(){
        
    }
    
}
