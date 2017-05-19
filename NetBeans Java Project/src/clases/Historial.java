package clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author niels_420_BlazeIt
 */
public class Historial {

    private int codigoHistorial;
    private String DNIPaciente;
    private int especialidad;
    private Ficha ficha;
    private Conexion conexion;

    /**
     * ¡La clase medico debe proporcionar un DNI y un código de cita válido!
     * Comprobamos presencia en la BD de un historial con el DNI y la
     * especialidad suministrada. Si ya existen se obtiene su codigo de
     * historial. En caso contrario se genera otro código. Se igualan el valor
     * del codigo y acto seguido se registra la row en la tabla historial y
     * ficha.
     *
     * @param DNIPaciente
     * @param especialidad
     * @param conexion
     * @param codCita
     * @param comentario
     * @throws SQLException
     */
    public Historial(String DNIPaciente, int especialidad, Conexion conexion,
            String codCita, String comentario) throws SQLException {
        this.conexion = conexion;   // Igualamos atributos
        // Se busca el codigo en la BD para igualarlo 
        this.codigoHistorial = getCodigoHistorialBD(DNIPaciente, especialidad);
        // Preparamos conexión
        PreparedStatement preparedStmt;
        Connection reg = this.conexion.getCon();
        if (this.codigoHistorial == 0) {//Si no esta en la BD se genera un código.
            String sql = "INSERT INTO centromedico.historial "
                    + "(Paciente, Especialidad) VALUES (?, ?)";
            preparedStmt = reg.prepareStatement(sql);
            preparedStmt.setInt(1, this.codigoHistorial);
            preparedStmt.setString(2, DNIPaciente);
            preparedStmt.setInt(3, especialidad);
            preparedStmt.execute();
            this.codigoHistorial = getCodigoHistorialBD(DNIPaciente, especialidad);
        }// Se adoptan los atributos
        this.DNIPaciente = DNIPaciente;
        this.especialidad = especialidad;
        // Y se introduce la ficha en la BD
        this.ficha = new Ficha(this.codigoHistorial, codCita, comentario, conexion);
    }

    /**
     * El codigo de historial recibido debe haberse comprobado que ya existe en
     * la BD.
     *
     * El constructor busca en la BD el historial cuyo codigo coincida para
     * retirar sus datos e igualarlos con los atributos
     *
     * @param codigoHistorial
     */
    public Historial(int codigoHistorial, Conexion con) throws SQLException {
        this.codigoHistorial = codigoHistorial;
        this.conexion = con;
        PreparedStatement preparedStmt;
        Connection reg = this.conexion.getCon();
        String sql = "SELECT Paciente, Especialidad FROM centromedico.historial"
                + " WHERE Cod_historial=" + codigoHistorial + ";";
        preparedStmt = reg.prepareStatement(sql);
        ResultSet rs = preparedStmt.executeQuery();
        rs.next();
        this.DNIPaciente = rs.getString("Paciente");
        this.especialidad = rs.getInt("Especialidad");
    }

    /**
     * Se busca un row en la tabla Historial donde coincidan DNIPaciente con la
     * especialidad. Si ya existe retorna su clave. Sino retorna 0.
     *
     * @param DNIPaciente
     * @param especialidad
     * @return integer
     * @throws SQLException
     */
    private int getCodigoHistorialBD(String DNIPaciente, int especialidad) throws SQLException {
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
     * Se recogen las fichas donde coincide el codigo de historial para así dar
     * acceso al médico de solo las citas y especialidades que el maneja para
     * despues retornarlo a un resulset.
     *
     * @return ResulSet
     * @throws SQLException
     */
    public ResultSet mostrarFichas() throws SQLException {
        PreparedStatement preparedStmt;
        Connection reg = conexion.getCon();
        String sql = "SELECT * FROM centromedico.ficha WHERE Cod_historial="
                + this.codigoHistorial + ";";
        preparedStmt = reg.prepareStatement(sql);
        ResultSet rs = preparedStmt.executeQuery();
        return rs;
    }

    /**
     * La interfaz debe presentar un formulario solicitando el codigo de cita
     * que se desea modificar, seguido de el comentario a modificar. Deben
     * comprobarse que el codigo de cita y el codigo de historial coinciden y
     * existen en la tabla Ficha de la BD.
     *
     * @param codCita
     * @param codHistorial
     * @param comentario
     * @param actualizarHora
     */
    public void modificarFicha(String codCita, String comentario
    ) throws SQLException {
        Ficha fichaAModificar = new Ficha(codCita, this.codigoHistorial,
                this.conexion);
        fichaAModificar.updateFicha(comentario);
    }

    /**
     * Retorna el codigo del historial.
     *
     * @return
     */
    public int getCodigoHistorial() {
        return this.codigoHistorial;
    }

}
