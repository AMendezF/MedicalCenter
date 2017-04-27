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
    private String idMedico;
    private String especialidad;

    /**
     * Constructor de cita.
     * @param fecha Formato fecha yyyy/mm/dd.
     * @param idPaciente Identificador de paciente.
     * @param idMedico Identificador médico.
     * @param especialidad Campo especialidad de la cita.
     */
    public Cita(String fecha, String idPaciente, String idMedico, String especialidad) {
        this.setFecha(fecha);
        this.setIdPaciente(idPaciente);
        this.setIdMedico(idMedico);
        this.setEspecialidad(especialidad);
    }
    
    /**
     * Devuelve la fecha de la cita.
     * @return Retorna la fecha en formato yyyy/mm/dd.
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * Devuelve el identificador del paciente de la cita.
     * @return Retorna el identificador.
     */
    public String getIdPaciente() {
        return idPaciente;
    }

    /**
     * Devuelve el identificador del médico de la cita.
     * @return Retorna el identificador.
     */
    public String getIdMedico() {
        return idMedico;
    }

    /**
     * Devuelve la especialidad para la que se ha pedido la cita.
     * @return Retorna la especialidad.
     */
    public String getEspecialidad() {
        return especialidad;
    }
    
    /**
     * Fija la fecha de la cita.
     * @param fecha Formato yyyy/mm/dd.
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * Fija el identificador del paciente.
     * @param idPaciente Identificador de paciente.
     */
    public void setIdPaciente(String idPaciente) {
        this.idPaciente = idPaciente;
    }

    /**
     * Fija el identificador del médico.
     * @param idMedico Identificador de médico.
     */
    public void setIdMedico(String idMedico) {
        this.idMedico = idMedico;
    }

    /**
     * Fija la especialidad de la cita.
     * @param especialidad Campo especialidad de la cita.
     */
    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
    
}
