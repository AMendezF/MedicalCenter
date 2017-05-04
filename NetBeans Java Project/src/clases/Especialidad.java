package clases;


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
 * @author Berta
 */
public class Especialidad {

	int idEsp;
	Conexion con;
	boolean ocupada;
	PreparedStatement preparedStmt;
	int lunes;
	int martes;
	int miercoles;
	int jueves;
	int viernes;
	int sabado;
	int domingo;
	Connection reg;

	public Especialidad(int idEsp, Conexion con) throws SQLException {
		this.idEsp = idEsp;
		this.con = con;

		reg = con.getCon();
		String sql = "SELECT * FROM centromedico.especialidad WHERE Cod_especialidad=? ;";
		preparedStmt = reg.prepareStatement(sql);
		preparedStmt.setInt(1, idEsp);
		ResultSet rs = preparedStmt.executeQuery();
		String horario = null;
		while (rs.next()) {
			horario = rs.getString("horario");
		}
		String horarios[] = horario.split(",");

		lunes = Integer.parseInt(horarios[0]);
		martes = Integer.parseInt(horarios[1]);
		miercoles = Integer.parseInt(horarios[2]);
		jueves = Integer.parseInt(horarios[3]);
		viernes = Integer.parseInt(horarios[4]);
		sabado = Integer.parseInt(horarios[5]);
		domingo = Integer.parseInt(horarios[6]);
	}

	public String getNombre() throws SQLException {
		reg = con.getCon();
		String res = null;
		String sql = "SELECT * FROM centromedico.especialidad WHERE Cod_especialidad=? ;";
		preparedStmt = reg.prepareStatement(sql);
		preparedStmt.setInt(1, idEsp);
		ResultSet rs = preparedStmt.executeQuery();
		while (rs.next()) {
			res = rs.getString("nombre");
		}
		return res;
	}

	int getHorario(int diaSemana) {
		int res = 0;
		switch (diaSemana) {
			case 1:
				res = lunes;
				break;
			case 2:
				res = martes;
				break;
			case 3:
				res = miercoles;
				break;
			case 4:
				res = jueves;
				break;
			case 5:
				res = viernes;
				break;
			case 6:
				res = sabado;
				break;
			case 7:
				res = domingo;
				break;
		}
		return res;
	}
}
