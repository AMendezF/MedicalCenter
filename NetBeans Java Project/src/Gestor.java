/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alejandro
 */
public class Gestor {

    private Conexion conexion;

    /**
     * Constructor de gestor.
     *
     * @param conexion Llave para comunicar con la DB.
     */
    public Gestor(Conexion conexion) {
        this.conexion = conexion;
    }

    /**
     * Inserta un nuevo médico en la base de datos.
     * @param medico Médico que se desea añadir a la BD.
     * @return true = añadido a la DB, false = fallo al añadir
     */
    public boolean addMedico(Medico medico) {
        boolean added = false;

        return added;
    }

    /**
     * Inserta un nuevo paciente en la base de datos.
     * @param paciente Paciente que se desea añadir a la BD.
     * @return true = añadido a la DB, false = fallo al añadir
     */
    public boolean addPaciente(Paciente paciente) {
        boolean added = false;

        return added;
    }

    /**
     * Sustituye un médico por otro.
     * @param medico Médico al que se pretende reemplazar.
     * @param sustituto Médico que reemplaza al anterior.
     * @return true = eliminado, false = fallo al eliminar
     */
    public boolean sustituirMedico(Medico medico, Medico sustituto) {
        boolean eliminado = false;

        return eliminado;
    }

    /**
     * Muestra las citas de un día organizadas por horario y salas de
     * especialidad.
     *
     * @return Resumen de todas las citas del día por salas (especialidades).
     */
    public String mostrarItinerario() {
        String itinerario = "";

        return itinerario;
    }

    /**
     * Muestra todos los pacientes que contiene la base de datos.
     *
     * @return Devuelve todos los pacientes con DNI, nombre, apellidos...
     */
    public String mostrarPacientes() {
        String lista = "";

        return lista;
    }

    /**
     * Elimina una cita de un paciente.
     * @param paciente Paciente del que se desea borrar una cita.
     * @return true = eliminado, false = fallo al eliminar
     */
    public boolean eliminarCita(Paciente paciente) {
        boolean eliminado = false;

        return eliminado;
    }
}
