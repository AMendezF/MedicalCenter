package clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author @author niels
 */
public class Historial{
    
    private int codigoHistorial;
    private String DNIPaciente;
    private int especialidad;
	private Ficha ficha;
    private Conexion conexion;
    
    /**
	 * ¡La clase medico debe proporcionar un DNI y un código de cita válido!
	 * Comprobamos presencia en la BD de un historial con el DNI y la 
     * especialidad suministrada. Si ya existen se obtiene su codigo de historial.
     * En caso contrario se genera otro código.
     * Se igualan el valor del codigo y acto seguido se registra la row en la 
     * tabla historial y ficha.
     * @param DNIPaciente
     * @param especialidad
     * @param conexion
	 * @param codCita
	 * @param comentario
     * @throws SQLException 
     */
    public Historial(String DNIPaciente, int especialidad, Conexion conexion, 
			int codCita, String comentario) throws SQLException{
        this.conexion = conexion;
        this.codigoHistorial = comprobarPresenciaBD(DNIPaciente, especialidad);
        PreparedStatement preparedStmt;
        Connection reg = this.conexion.getCon();
		if(this.codigoHistorial == 0){//Si no esta en la BD se genera un código.
            this.codigoHistorial = obtenerCodigo();
			String sql = "INSERT INTO centromedico.historial  (Cod_historial, "
					+ "Paciente, Especialista) VALUES (?, ?, ?)";
			preparedStmt = reg.prepareStatement(sql);
			preparedStmt.setInt(1, this.codigoHistorial);
			preparedStmt.setString(2, DNIPaciente);
			preparedStmt.setInt(3, especialidad);
		}// Se adoptan los atributos
        this.DNIPaciente = DNIPaciente;
        this.especialidad = especialidad;
		// Y se introduce la ficha en la BD
		this.ficha = new Ficha(this.codigoHistorial, codCita, comentario);
    }
	
    /**
     * Se busca un row en la tabla Historial donde coincidan 
     * DNIPaciente con la especialidad. Si ya existe retorna su clave.
	 * Sino retorna 0.
     * @param DNIPaciente
     * @param especialidad
     * @return integer
     * @throws SQLException 
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
	 * Se busca el código con el valor más alto en la tabla Historial para
     * incrementarlo en uno y después adoptarlo a este historial.
     * @return	integer
     * @throws SQLException 
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

	/**
	 * Se recogen las fichas donde coincide el codigo de historial
	 * para así dar acceso al médico de solo las citas y especialidades
	 * que el maneja para despues retornarlo a un resulset.
	 * @return ResulSet
	 * @throws SQLException 
	 */
    public ResultSet mostrarFichas() throws SQLException{
		PreparedStatement preparedStmt;
        Connection reg = conexion.getCon();
        String sql = "SELECT * FROM centromedico.ficha WHERE Cod_historial="
				+ Integer.toString(codigoHistorial) + ";";
        preparedStmt = reg.prepareStatement(sql);
        ResultSet rs = preparedStmt.executeQuery();		
        return rs;
    }
    
    public void modificarFicha(){
        
    }
    
}
