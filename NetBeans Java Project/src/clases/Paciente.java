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

    private String dni;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String direccion;
    private String seguro;
    private Conexion con;
    PreparedStatement preparedStmt = null;

    /**
     * Este guapísimo constructor retira de la BD el paciente con el DNI 
     * proporcionado para cargar los atributos con los datos de la BD
     *
     * @param DNI
     * @param con
     * @throws SQLException
     */
    public Paciente(String DNI, Conexion con) throws SQLException {
        this.dni = DNI;
        this.con = con;
        Connection reg = con.getCon();
        String sql = "SELECT nombre, apellidos, compsegur, telefono, direccion "
                + "FROM centromedico.paciente WHERE dni =?;";

        PreparedStatement preparedStmt = reg.prepareStatement(sql);
        preparedStmt.setString(1, DNI);
        ResultSet rs = preparedStmt.executeQuery();

        rs.next();
        this.nombre = rs.getString("paciente.nombre");
        this.apellidos = rs.getString("paciente.apellidos");
        this.telefono = rs.getString("paciente.telefono");
        this.direccion = rs.getString("paciente.direccion");
        this.seguro = rs.getString("paciente.compsegur");
    }

    /**
     * Devuelve las citas pendientes del paciente
     *
     * @return
     * @throws SQLException
     */
    public ResultSet mostrarCitasPendientes() throws SQLException {
        Connection reg = this.con.getCon();
        String sql = "SELECT c.Dia, c.Hora, c.Cod_cita, c.Medico, "
				+ "e.Nombre FROM centromedico.citas c, " 
				+ "centromedico.especialidad e WHERE (c.Dia>"
                + this.getDia() + " OR (c.Dia=" + this.getDia() + " AND c.Hora>"
                + this.getHora() + ")) AND c.Paciente='" + this.dni + "' "
				+ "AND c.especialidad=e.cod_especialidad "
				+ "ORDER BY DATE(c.Dia)";
        ResultSet rs = reg.prepareStatement(sql).executeQuery();

        return rs;
    }

    /**
     * Devuelve las citas pasadas de fecha del paciente.
     *
     * @return
     * @throws SQLException
     */
    public ResultSet mostrarCitasPasadas() throws SQLException {
        Connection reg = this.con.getCon();
        String sql = "SELECT * FROM centromedico.citas WHERE (Dia<"
                + this.getDia() + " OR (Dia=" + this.getDia() + " AND Hora<"
                + this.getHora() + ")) AND Paciente='" + this.dni + "';";
        ResultSet rs = reg.prepareStatement(sql).executeQuery();

        return rs;
    }

    /**
     * Retorna el día actual formato AAAA-MM-DD.
     *
     * @return
     */
    private String getDia() {
        Calendar fecha = new GregorianCalendar();
		String date;
		date = "'" + fecha.get(Calendar.YEAR) + "-"
                + (fecha.get(Calendar.MONTH) + 1) + "-"
                + fecha.get(Calendar.DAY_OF_MONTH) + "'";
				System.out.println(date);
        return date;
    }

    /**
     * Retorna la hora actual formato HH-MM.
     *
     * @return
     */
    private String getHora() {
        Calendar fecha = new GregorianCalendar();
		String hora;
		hora = "'" + fecha.get(Calendar.HOUR_OF_DAY) + ":"
                + fecha.get(Calendar.MINUTE) + "'";
		System.out.println(hora);
        return hora;
    }

    private String tieneSeguro() {
        if ("NULL".equals(this.seguro) || "null".equals(this.seguro)) {
            return "No tiene seguro";
        } else {
            return this.seguro;
        }
    }

    @Override
    public String toString() {
        return this.dni + " - " + this.nombre + " - " + this.apellidos + "; " + tieneSeguro() + ".";
    }

    public String getDNI() {
        return dni;
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getApellidos() {
        return this.apellidos;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public String getSeguro() {
        return this.seguro;
    }
}
