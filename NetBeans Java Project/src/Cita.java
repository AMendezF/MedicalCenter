
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Alejandro
 */
public class Cita {

    private String fecha;
    private String idPaciente;
    private String especialidad;
    private String idMedico;

    /**
     * Constructor de cita.
     *
     * @param fecha Formato fecha yyyy/mm/dd.
     * @param idPaciente Identificador de paciente.
     * @param especialidad Campo especialidad de la cita.
     * @param idMedico Identificador médico.
     */
    public Cita(String fecha, String idPaciente, String idMedico, String especialidad) {
        this.setFecha(fecha);
        this.setIdPaciente(idPaciente);
        this.setEspecialidad(especialidad);
        this.setIdMedico(idMedico);
    }

    /**
     * Muestra las especialidades disponibles para solicitar en la cita.
     * @param connection Objeto para poder realizar consulta a la base de datos.
     * @return Retorna un string con todas las especialidades.
     * @throws SQLException 
     */
    public String mostrarEspecialidades(Connection connection) throws SQLException {
        String resultado = "";
        String sql;
        ResultSet resultSet;
        
        sql = "SELECT * FROM centromedico.especialidad ORDER BY cod_especialidad;";
        resultSet = makeQuery(connection, sql);

        while (resultSet.next()) {
            resultado += Integer.toString(resultSet.getInt("cod_especialidad"))
                    + ". " + resultSet.getString("nombre") + "\n";
        }
        return resultado;
    }

    public ResultSet makeQuery(Connection connection, String sql) throws SQLException {
        PreparedStatement preparedStmt = connection.prepareStatement(sql);
        ResultSet resulSet = preparedStmt.executeQuery();
        return resulSet;
    }

    /**
     * Devuelve la fecha de la cita.
     *
     * @return Retorna la fecha en formato yyyy/mm/dd.
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * Devuelve el identificador del paciente de la cita.
     *
     * @return Retorna el identificador.
     */
    public String getIdPaciente() {
        return idPaciente;
    }

    /**
     * Devuelve la especialidad para la que se ha pedido la cita.
     *
     * @return Retorna la especialidad.
     */
    public String getEspecialidad() {
        return especialidad;
    }

    /**
     * Devuelve el identificador del médico de la cita.
     *
     * @return Retorna el identificador.
     */
    public String getIdMedico() {
        return idMedico;
    }

    /**
     * Fija la fecha de la cita.
     *
     * @param fecha Formato yyyy/mm/dd.
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * Fija el identificador del paciente.
     *
     * @param idPaciente Identificador de paciente.
     */
    public void setIdPaciente(String idPaciente) {
        this.idPaciente = idPaciente;
    }

    /**
     * Fija la especialidad de la cita.
     *
     * @param especialidad Campo especialidad de la cita.
     */
    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    /**
     * Fija el identificador del médico.
     *
     * @param idMedico Identificador de médico.
     */
    public void setIdMedico(String idMedico) {
        this.idMedico = idMedico;
    }

    @Override
    public String toString() {
        return "Cita{" + "fecha=" + fecha
                + ", idPaciente=" + idPaciente
                + ", especialidad=" + especialidad
                + ", idMedico=" + idMedico + '}';
    }

}
