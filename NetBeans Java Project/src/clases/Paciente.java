package clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

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
    private int contCitas;
    private Conexion con;
    PreparedStatement preparedStmt = null;

    /**
     * Este guapísimo constructor inicializa el contador de citas e iguala
	 * sus atributos a los marcados en la BD.
     *
     * @param DNI
     * @param con
     * @throws SQLException
     */
    public Paciente(String DNI, Conexion con) throws SQLException {
        this.DNI = DNI;
        this.con = con;
        Connection reg = con.getCon();
        contCitas = getContCitas();
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

    public void addCita() {
        contCitas++;
    }

    //TODO: Devolver resultset que muestre todas las citas pendiente del paciente
    public void mostrarCitas() throws SQLException {
        PreparedStatement preparedStmt;
        Connection reg = con.getCon();
        String sql;

        sql = "SELECT medico.nombre,citas.medico, medico.n_colegiado, especialidad.nombre, citas.cod_cita, citas.hora, citas.dia"
                + "  FROM centromedico.citas, centromedico.medico, centromedico.especialidad "
                + "WHERE paciente=? AND medico.n_colegiado=citas.medico "
                + "AND medico.especialidad= especialidad.cod_especialidad";
        preparedStmt = reg.prepareStatement(sql);
        preparedStmt.setString(1, getDNI());
        ResultSet rs = preparedStmt.executeQuery();
        List<String> medicos = new ArrayList();
        List<String> codMeds = new ArrayList();
        List<String> codigosCita = new ArrayList();
        List<String> horas = new ArrayList();
        List<String> fechas = new ArrayList();
        List<String> especialidades = new ArrayList();
        while (rs.next()) {
            medicos.add(rs.getString("medico.nombre"));
            codigosCita.add(rs.getString("citas.cod_cita"));
            horas.add(rs.getTime("citas.hora").toString());
            fechas.add(rs.getDate("citas.dia").toString());
            especialidades.add(rs.getString("especialidad.nombre"));
            codMeds.add(String.valueOf(rs.getInt("citas.medico")));
        }
        for (int i = 0; i < medicos.size(); i++) {
            System.out.println((i + 1) + ". " + fechas.get(i) + " " + horas.get(i) + " con " + medicos.get(i)
                    + ", " + especialidades.get(i));
        }
    }

    public void pedirCita(Conexion con) throws SQLException {
        int codEspecialidad;
        int op = 0;
        int opTurno = -1;
        String turno = null;
        int codMedico;
        String fecha1, fecha2, fecha3;

        Calendar fecha = new GregorianCalendar();
        String fechaDia = null;
        int diaSemana = 0;

//        System.out.println("Elija la especialidad: ");
       Scanner sc = new Scanner(System.in);
//        PreparedStatement preparedStmt;
//        Connection reg = con.getCon();
//        String sql = "SELECT * FROM centromedico.especialidad ORDER BY cod_especialidad;";
//        preparedStmt = reg.prepareStatement(sql);
//        ResultSet rs = preparedStmt.executeQuery();
//        List<String> listaNombres;
//        listaNombres = new ArrayList();
//        List<String> listaCodigos;
//        listaCodigos = new ArrayList();
//        List<String> listaHorarios; // BIEEEEEEEEEN
//        listaHorarios = new ArrayList(); // BIEEEEEEEEEN
//        while (rs.next()) {
//            listaNombres.add(rs.getString("nombre"));
//            listaCodigos.add(Integer.toString(rs.getInt("cod_especialidad")));
//            listaHorarios.add(rs.getString("horario")); // BIEEEEEEEEEN
//        }
//        for (int i = 0; i < listaNombres.size(); i++) {
//            System.out.println(listaCodigos.get(i) + ". " + listaNombres.get(i));
//        }
//        codEspecialidad = sc.nextInt();

        fecha1 = fecha.get(Calendar.YEAR) + "-"
                + (fecha.get(Calendar.MONTH) + 1) + "-"
                + fecha.get(Calendar.DAY_OF_MONTH);
        int diaSemana1 = fecha.get(Calendar.DAY_OF_WEEK);

        fecha.add(Calendar.DAY_OF_MONTH, 1);
        fecha2 = fecha.get(Calendar.YEAR) + "-"
                + (fecha.get(Calendar.MONTH) + 1) + "-"
                + fecha.get(Calendar.DAY_OF_MONTH);
        int diaSemana2 = fecha.get(Calendar.DAY_OF_WEEK);

        fecha.add(Calendar.DAY_OF_MONTH, 1);
        fecha3 = fecha.get(Calendar.YEAR) + "-"
                + (fecha.get(Calendar.MONTH) + 1) + "-"
                + fecha.get(Calendar.DAY_OF_MONTH);
        int diaSemana3 = fecha.get(Calendar.DAY_OF_WEEK);

        do {
            System.out.println("Elija una fecha: ");

            System.out.println("1. " + fecha1);
            System.out.println("2. " + fecha2);
            System.out.println("3. " + fecha3);
            op = sc.nextInt();

            if (op == 1) {
                fechaDia = fecha1;
                diaSemana = diaSemana1;
            }
            if (op == 2) {
                fechaDia = fecha2;
                diaSemana = diaSemana2;
            }
            if (op == 3) {
                fechaDia = fecha3;
                diaSemana = diaSemana3;
            }
            if (diaSemana == 1) {
                diaSemana = 7;
            } else {
                diaSemana--;
            }
            Especialidad especialidad = new Especialidad(codEspecialidad, con);
            int horarioDia = especialidad.getHorario(diaSemana);

            do {
                System.out.println("Elija el turno: ");
                System.out.println("0. Elija otro día");
                if (horarioDia == 1 || horarioDia == 3) {
                    System.out.println("1. Mañana");
                }
                if (horarioDia == 2 || horarioDia == 3) {
                    System.out.println("2. Tarde");
                }
                opTurno = sc.nextInt();
                if (opTurno == 1) {
                    turno = "Mañana";
                }
                if (opTurno == 2) {
                    turno = "Tarde";
                }
            } while (opTurno != 1 && opTurno != 2 && opTurno != 0);
        } while (opTurno == 0);
        sql = "SELECT * FROM centromedico.medico WHERE horario=? AND especialidad=?;";
        preparedStmt = reg.prepareStatement(sql);
        preparedStmt.setString(1, turno);
        preparedStmt.setInt(2, codEspecialidad);
        rs = preparedStmt.executeQuery();
        List<String> listaMedicos = new ArrayList();
        Map<Integer, Integer> mapa = new HashMap<Integer, Integer>();
        int j = 1;
        while (rs.next()) {
            listaMedicos.add(rs.getString("nombre") + " " + rs.getString("apellidos"));
            mapa.put(j, rs.getInt("n_colegiado"));
            j++;
        }
        sc.nextLine();
        System.out.println("¿Desea elegir el médico? (S/N) ");
        String opcionS = sc.nextLine().toUpperCase();
        if (opcionS.equals("S")) {
            System.out.println("Elija el médico: ");
            for (int i = 0; i < listaMedicos.size(); i++) {
                System.out.println((i + 1) + ". " + listaMedicos.get(i));
                op = sc.nextInt();
            }
        } else if (opcionS.equals("N")) {
            op = 1;
        }
        codMedico = mapa.get(op);

        Medico medico = new Medico(codMedico, con);

        this.pedirConsulta(medico, fechaDia);

    }

    private void pedirConsulta(Medico med, String dia) throws SQLException {
        boolean[] consultas;
        //consultas = new boolean[24]; 
        int horaI = 0;
        int minutos = -med.getTiempoMin();
        consultas = med.getConsultas(dia);
        if ("Mañana".equals(med.getTurno())) {
            horaI = 9;
        } else if ("Tarde".equals(med.getTurno())) {
            horaI = 15;
        }
        System.out.println("Horas disponibles:");
        int j = 1;
        Map<Integer, String> mapa = new HashMap<Integer, String>();
        for (int i = 0; i < consultas.length; i++) { //Voy mostrando las horas
            String horaImp = "";                    // disponibles
            if (minutos + med.getTiempoMin() < 60) {
                minutos = minutos + med.getTiempoMin();
            } else {
                horaI++;
                minutos = minutos + med.getTiempoMin() - 60;
            }
            if (minutos == 0) {
                horaImp = horaI + ":" + minutos + "0";
            } else {
                horaImp = horaI + ":" + minutos;
            }

            if (consultas[i] == false) { //Consulta libre
                System.out.println(j + ". " + horaImp);
                String horaMapa = horaImp + ":00";
                mapa.put(j, horaMapa);
                j++;
            }
        }
        System.out.println("Escriba la opción deseada: ");
        Scanner sc = new Scanner(System.in);
        int cadena = sc.nextInt();
        System.out.println("Tiene cita el día " + dia + " a las " + mapa.get(cadena) + " con el medico " + med.getNombre());
        med.addCita(mapa.get(cadena), dia, getDNI(), getContCitas()); // 
        addCita();
    }
	
	/**
	 * Devuelve el número de citas donde aparece el Paciente.
	 * TODO: QUE SEAN CITAS PENDIENTEEEES
	 * 
	 * @return
	 * @throws SQLException 
	 */
    public int getContCitas() throws SQLException {
        Connection reg = con.getCon();
        String sql = "SELECT COUNT(*) AS cuenta FROM centromedico.citas WHERE paciente=? ;";
        preparedStmt = reg.prepareStatement(sql);
        preparedStmt.setString(1, DNI);
        ResultSet rs = preparedStmt.executeQuery();
        if (rs.next()) {
            contCitas = rs.getInt("cuenta");
        } else {
            contCitas = 0;
        }
        return contCitas;
    }

	/**
	 * Devuelve si el paciente tiene citas pendientes o no.
	 * WWOOOOOOOOOOWWWW!!!     ;)    
	 * 
	 * @return
	 * @throws SQLException 
	 */
    public boolean tieneCitas() throws SQLException {
        boolean tieneCitas = false;
        if (this.getContCitas() > 0) {
            tieneCitas = true;
        }
        return tieneCitas;
    }
	
    private String tieneSeguro() {
        if (this.Seguro == "NULL" || this.Seguro == "null") {
            return "No tiene seguro";
        } else {
            return this.Seguro;
        }
    }

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
