package clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
 * @author niels
 */
class Ficha {
    
	private int codHistorial;
	private int codCita;
	private String comentario;
	private Calendar fecha = new GregorianCalendar();
    private Conexion conexion;

	/**
	 * Constructor para insertar una ficha en la BD.
	 * @param codHistorial
	 * @param codCita
	 * @param comentario
	 * @throws SQLException 
	 */
	public Ficha(int codHistorial, int codCita, String comentario) throws SQLException{
		this.codHistorial = codHistorial;
		this.codCita = codCita;
		this.comentario = comentario;
		PreparedStatement preparedStmt;
        Connection reg = this.conexion.getCon();
		String fechaActual = fecha.get(Calendar.YEAR) + "-"
				+ (fecha.get(Calendar.MONTH) + 1) + "-"
				+ fecha.get(Calendar.DAY_OF_MONTH);
		String hora = fecha.get(Calendar.HOUR) + ":"
				+ fecha.get(Calendar.MINUTE);
        String sql = "INSERT INTO centromedico.ficha (Cod_historial, Cod_cita,"
				+ "comentario, Dia, Hora) VALUES (?, ?, ?, ?, ?)";
		preparedStmt = reg.prepareStatement(sql);
		preparedStmt.setInt(1, codHistorial);
		preparedStmt.setInt(2, codCita);
		preparedStmt.setString(3, comentario);
		preparedStmt.setDate(4, java.sql.Date.valueOf(fechaActual));
		preparedStmt.setTime(5, java.sql.Time.valueOf(hora));
	}
	
	public Ficha (int codCita){
		this.codCita = codCita;
		PreparedStatement preparedStmt;
        Connection reg = this.conexion.getCon();
		String sql = "SELECT * FROM centromedico.ficha WHERE Cod_cita="
				+ "";
	}
}
