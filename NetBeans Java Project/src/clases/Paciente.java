package clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author xXx_niels420_xXx
 */

/*
TODO: 1- IMPLEMENTAR METODOS DE INTERFAZ "MenuDePaciente" orientado a interfaz
      2- mostrarCitasPasadas() devuelve rs de citas pasadas de fecha
 */
public class Paciente {

    private String DNI;
    private String Nombre;
    private String Apellidos;
    private String Seguro;
    private Conexion con;
    PreparedStatement preparedStmt = null;

    /**
     * Este guapísimo constructor inicializa el contador de citas e iguala sus
     * atributos a los marcados en la BD.
     *
     * @param DNI
     * @param con
     * @throws SQLException
     */
    public Paciente(String DNI, Conexion con) throws SQLException {
        this.DNI = DNI;
        this.con = con;
        Connection reg = con.getCon();
        String sql = "SELECT nombre, apellidos, compsegur "
                + "FROM centromedico.paciente WHERE dni =?;";

        PreparedStatement preparedStmt = reg.prepareStatement(sql);
        preparedStmt.setString(1, DNI);
        ResultSet rs = preparedStmt.executeQuery();

        rs.next();
        this.Nombre = rs.getString("paciente.nombre");
        this.Apellidos = rs.getString("paciente.apellidos");
        this.Seguro = rs.getString("paciente.compsegur");
    }

    /**
     * Devuelve las citas pendientes del paciente
     *
     * @return
     * @throws SQLException
     */
    public ResultSet mostrarCitasPendientes() throws SQLException {
        Connection reg = this.con.getCon();
        String sql = "SELECT * FROM centromedico.citas WHERE (Dia>"
                + this.getDia() + " OR (Dia=" + this.getDia() + " AND Hora>"
                + this.getHora() + ")) AND Paciente='" + this.DNI + "';";
        PreparedStatement preparedStmt = reg.prepareStatement(sql);
        ResultSet rs = preparedStmt.executeQuery();

        return rs;
    }

    /**
     * Retorna el día actual formato AAAA-MM-DD.
     *
     * @return
     */
    private String getDia() {
        Calendar fecha = new GregorianCalendar();
        return "'" + fecha.get(Calendar.YEAR) + "-"
                + (fecha.get(Calendar.MONTH) + 1) + "-"
                + fecha.get(Calendar.DAY_OF_MONTH) + "'";
    }

    /**
     * Retorna la hora actual formato HH-MM.
     *
     * @return
     */
    private String getHora() {
        Calendar fecha = new GregorianCalendar();
        return "'" + fecha.get(Calendar.HOUR) + ":"
                + fecha.get(Calendar.MINUTE) + "'";
    }

    private String tieneSeguro() {
        if ("NULL".equals(this.Seguro) || "null".equals(this.Seguro)) {
            return "No tiene seguro";
        } else {
            return this.Seguro;
        }
    }

    @Override
    public String toString() {
        return this.DNI + " - " + this.Nombre + " - " + this.Apellidos + "; " + tieneSeguro() + ".";
    }

    public String getDNI() {
        return DNI;
    }

    public String getNombre() {
        return this.Nombre;
    }

    public String getApellidos() {
        return this.Apellidos;
    }

    public String getSeguro() {
        return this.Seguro;
    }
}
