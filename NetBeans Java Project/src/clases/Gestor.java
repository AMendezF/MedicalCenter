package clases;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Alejandro
 */
public class Gestor {

    private final int maxMedicosActivos = 3;
    private Conexion conexion;

    /**
     * Constructor de gestor.
     *
     * @param conexion Llave para comunicar con la BD.
     */
    public Gestor(Conexion conexion) {
        this.conexion = conexion;
    }

    /**
     * Genera el menú de gestor en pantalla.
     */
    public void Menu() {
        //TODO
    }

    /**
     * Muestra las citas de un día organizadas por horario y salas de
     * especialidad.
     */
    public void mostrarItinerario() {
        //TODO
    }

    /**
     * Genera el menú de gestionar pacientes en pantalla.
     */
    public void gestionPacientesMenu() {
        //TODO
    }

    /**
     * Devuelve todos los pacientes que contiene la base de datos.
     *
     * @return Retorna el valor de la consulta SQL.
     * @throws SQLException Devuelve error si no see pudo realizar la consulta
     * SQL.
     */
    public ResultSet mostrarPacientes() throws SQLException {
        ResultSet listaPacientes = null;
        String sql;

        sql = "SELECT * "
                + "FROM centromedico.paciente "
                + "ORDER BY DNI" + ";";
        listaPacientes = this.conexion.makeQuery(sql);

        return listaPacientes;
    }

    /**
     * Inserta un nuevo paciente en la base de datos.
     *
     * @return true = añadido a la BD, false = fallo al añadir
     */
    public boolean addPaciente() {
        boolean added = false;
        //TODO
        return added;
    }

    /**
     * Accede al menú de un paciente.
     *
     * @param paciente Paciente que deseas gestionar.
     */
    public void gestionarPaciente(Paciente paciente) {
        //TODO paciente.menu();
    }

    /**
     * Genera el menú de gestionar medicos en pantalla.
     */
    public void gestionMedicosMenu() {
        //TODO
    }

    /**
     * Devuelve todos los medicos que contiene la base de datos.
     *
     * @return Retorna el valor de la consulta SQL.
     * @throws SQLException
     */
    public ResultSet mostrarMedicos() throws SQLException {
        ResultSet listaMedicos = null;
        String sql;

        sql = "SELECT * "
                + "FROM centromedico.medico" + ";";
        listaMedicos = this.conexion.makeQuery(sql);

        return listaMedicos;
    }

    /**
     * Inserta un nuevo médico en la base de datos.
     *
     * @return true = añadido a la BD, false = fallo al añadir
     */
    public boolean addMedico() {
        boolean added = false;

        /*try {
            if (actualMedicosActivos() < maxMedicosActivos) {

            } else {
                if (this.sustituirMedico(medico, sustituto)) {
                    added = true;
                }
            }

        } catch (Exception e) {

        }*/
        //TODO
        return added;
    }

    /**
     * Sustituye un médico por otro.
     *
     * @param medico Médico al que se pretende reemplazar.
     * @param sustituto Médico que reemplaza al anterior.
     * @return true = añadido a la BD, false = fallo al añadir
     */
    public void sustituirMedico(Medico medico, Medico sustituto) throws SQLException {
        //TODO
    }

    /**
     * Elimina el médico de la BD, especificado por parámetro.
     *
     * @param codMedico Número identificativo de un médico.
     * @throws SQLException Devuelve error si no see pudo realizar la consulta
     * SQL.
     */
    public void eliminarMedico(int codMedico) throws SQLException {
        boolean eliminado = false;
        String sql;

        //sql = "DELETE FROM centromedico.medico WHERE N_colegiado =? "; // tested
        sql = "DELETE FROM centromedico.medico "
                + "WHERE N_colegiado=" + Integer.toString(codMedico) + ";";
        this.conexion.makeQuery(sql);
    }

    /**
     * Traslada las citas que posee un medico a un médico sustituto.
     *
     * @param codMedico Número identificativo del médico sustituido.
     * @param codMedicoSustituto Número identificativo del médico sustituto.
     * @throws SQLException SQLException Devuelve error si no see pudo realizar
     * la consulta SQL.
     */
    public void trasladarCitas(int codMedico, int codMedicoSustituto) throws SQLException {
        String sql;

        //sql = "UPDATE centromedico.citas SET medico=? WHERE medico=?;"; // tested
        sql = "UPDATE centromedico.citas"
                + "SET medico=" + Integer.toString(codMedico) + " "
                + "WHERE medico=" + Integer.toString(codMedicoSustituto) + ";";
        this.conexion.makeQuery(sql);
    }

    /**
     * Elimina una cita de un paciente.
     *
     * @param paciente Paciente del que se desea borrar una cita.
     * @return true = eliminado, false = fallo al eliminar
     */
    /*public boolean anularCita(Cita cita) {
        boolean eliminado = false;

        return eliminado;
    }*/ /**
     * Devuleve todas las especialidades médicas existentes en la BD.
     *
     * @return Retorna una lista de especialidades médicas.
     * @throws SQLException Devuelve error si no see pudo realizar la consulta
     * SQL.
     */
    /*public ArrayList<String> mostrarEspecialidades() throws SQLException {
        ArrayList<String> listaEspecialidades = null;
        String sql;
        ResultSet resultSet;
        
        sql = "SELECT * FROM centromedico.especialidad "
                "ORDER BY cod_especialidad;";
        resultSet = this.conexion.makeQuery(sql);
        
        while (resultSet.next()) {
            listaEspecialidades.add(resultSet.getString("nombre"));
	}
        
        return listaEspecialidades;
    }*/
}
