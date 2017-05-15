package clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author niels_420_BlazeIt
 */
class Ficha {

    private int codHistorial;
    private String codCita;
    private String comentario;
    private Calendar fecha = new GregorianCalendar();
    private Conexion conexion;

    /**
     * Constructor para insertar una ficha en la BD.
     *
     * @param codHistorial
     * @param codCita
     * @param comentario
     * @throws SQLException
     */
    public Ficha(int codHistorial, String codCita, String comentario) 
            throws SQLException {
        this.codHistorial = codHistorial;
        this.codCita = codCita;
        this.comentario = comentario;
        PreparedStatement preparedStmt;
        Connection reg = this.conexion.getCon();
        String sql = "INSERT INTO centromedico.ficha (Cod_historial, Cod_cita,"
                + "comentario, Dia, Hora) VALUES (?, ?, ?, ?, ?)";
        preparedStmt = reg.prepareStatement(sql);
        preparedStmt.setInt(1, codHistorial);
        preparedStmt.setString(2, codCita);
        preparedStmt.setString(3, comentario);
        preparedStmt.setDate(4, java.sql.Date.valueOf(getDia()));
        preparedStmt.setTime(5, java.sql.Time.valueOf(getHora()));
        preparedStmt.execute();
    }

    /**
     * ¡Importante! Es necesario recibir un código de citas e historial válido
     * es decir se necesita pre-comprobar su presencia en la BD antes de llamar
     * a este método. 
     * 
     * Este constructor busca una row en la tabla Ficha de la BD donde 
     * coincidan el codCita y el codigo del historial para cargar los datos.
     * @param codCita
     * @param codHistorial
     * @param con
     * @throws SQLException 
     */
    public Ficha(String codCita, int codHistorial, Conexion con) throws SQLException {
        this.codCita = codCita;
        this.codHistorial = codHistorial;
        this.conexion = con;
        PreparedStatement preparedStmt;
        Connection reg = this.conexion.getCon();
        String sql = "SELECT comentario, Dia, Hora FROM centromedico.ficha "
                + "WHERE Cod_cita=? AND  Cod_historial=?;";
        preparedStmt = reg.prepareStatement(sql);
        preparedStmt.setString(1, codCita);
        preparedStmt.setInt(2, codHistorial);
        ResultSet rs = preparedStmt.executeQuery();
        rs.next();
        this.comentario = rs.getString("comentario");
        // capturamos el día
        String day = rs.getString("Dia");
        String[] dayArray = day.split("-");
        String hour = rs.getString("Hora");
        String[] hourArray = hour.split(":");
        this.fecha.set(Integer.parseInt(dayArray[0]), Integer.parseInt(dayArray[1]),
                Integer.parseInt(dayArray[2]), Integer.parseInt(hourArray[0]), 
                Integer.parseInt(hourArray[1]));
    }
    
    /**
     * Actualiza la ficha tanto en la clase como en la BD con los datos 
     * proporcionados. Si el string está vacío ' "" ' no se actualizará el 
     * comentario. Si el parámetro actualizarFecha es True se insertará en la
     * BD la fecha actual, en caso contrario no se actualizará.
     * @param comentario
     * @param actualizarFecha
     * @throws SQLException 
     */
    public void updateFicha(String comentario, boolean actualizarFecha) 
            throws SQLException{
        PreparedStatement preparedStmt;
        Connection reg = this.conexion.getCon();
        if(!comentario.equals("")){
            this.comentario = comentario;
            String sql = "UPDATE centromedico.Ficha SET comentario=? WHERE" + 
                    " Cod_cita=? AND Cod_historial=?;";
            preparedStmt = reg.prepareStatement(sql);
            preparedStmt.setString(1, comentario);
            preparedStmt.setString(2, this.codCita);
            preparedStmt.setInt(3, this.codHistorial);
            preparedStmt.execute();
        }
        if(actualizarFecha){
            String sql = "UPDATE centromedico.Ficha SET Dia=?, Hora=? WHERE" + 
                    " Cod_cita=? AND Cod_historial=?;";
            preparedStmt = reg.prepareStatement(sql);
            preparedStmt.setDate(1, java.sql.Date.valueOf(getDia()));
            preparedStmt.setTime(2, java.sql.Time.valueOf(getHora()));
            preparedStmt.setString(3, this.codCita);
            preparedStmt.setInt(4, this.codHistorial);
        }
    }
    
    /**
     * Retorna el día actual formato AAAA-MM-DD.
     * @return 
     */
    private String getDia(){
        return fecha.get(Calendar.YEAR) + "-"
                + (fecha.get(Calendar.MONTH) + 1) + "-"
                + fecha.get(Calendar.DAY_OF_MONTH);
    }
    
    /**
     * Retorna la hora actual formato HH-MM.
     * @return 
     */
    private String getHora(){
        return fecha.get(Calendar.HOUR) + ":" + fecha.get(Calendar.MINUTE);
    }
}
