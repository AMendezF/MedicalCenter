
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
 * @author Berta
 */
public class main {

    public static void pedirCita(Paciente paciente, Conexion con) throws SQLException {
        int codEspecialidad;
        int op = 0;
        String turno = null;
        int codMedico;
        String fecha1;
        String fecha2;
        String fecha3;
        Calendar fecha = new GregorianCalendar();
        String fechaDia = null;

        System.out.println("Elija la especialidad: ");
        Scanner sc = new Scanner(System.in);
        PreparedStatement preparedStmt;
        Connection reg = con.getCon();
        String sql = "SELECT * FROM especialidad ORDER BY cod_especialidad;";
        preparedStmt = reg.prepareStatement(sql);
        ResultSet rs = preparedStmt.executeQuery();
        List<String> listaNombres;
        listaNombres = new ArrayList();
        List<String> listaCodigos;
        listaCodigos = new ArrayList();
        while (rs.next()) {
            listaNombres.add(rs.getString("nombre"));
            listaCodigos.add(Integer.toString(rs.getInt("cod_especialidad")));
        }
        for (int i = 0; i < listaNombres.size(); i++) {
            System.out.println(listaCodigos.get(i) + ". " + listaNombres.get(i));
        }
        codEspecialidad = sc.nextInt();

        while (op != 1 && op != 2) {
            System.out.println("Elija el turno: ");

            System.out.println("1. Mañana");
            System.out.println("2. Tarde");
            op = sc.nextInt();
            if (op == 1) {
                turno = "Mañana";
            }
            if (op == 2) {
                turno = "Tarde";
            }
        }
        sql = "SELECT * FROM medico WHERE horario=? AND especialidad=?;";
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
        String opcionS = sc.nextLine();
        if (opcionS.equals("S")) {
             System.out.println("Elija el médico: ");
             for (int i = 0; i < listaMedicos.size(); i++) {
            System.out.println((i + 1) + ". " + listaMedicos.get(i));
             op = sc.nextInt();            
        }
        }
        else if (opcionS.equals("N")){
            op=1;
        }
        codMedico = mapa.get(op);

        System.out.println("Elija una fecha: ");
        fecha1 = fecha.get(Calendar.YEAR) + "-"
                + (fecha.get(Calendar.MONTH) + 1) + "-"
                + fecha.get(Calendar.DAY_OF_MONTH);

        fecha.add(Calendar.DAY_OF_MONTH, 1);
        fecha2 = fecha.get(Calendar.YEAR) + "-"
                + (fecha.get(Calendar.MONTH) + 1) + "-"
                + fecha.get(Calendar.DAY_OF_MONTH);

        fecha.add(Calendar.DAY_OF_MONTH, 1);
        fecha3 = fecha.get(Calendar.YEAR) + "-"
                + (fecha.get(Calendar.MONTH) + 1) + "-"
                + fecha.get(Calendar.DAY_OF_MONTH);
        System.out.println("1. " + fecha1);
        System.out.println("2. " + fecha2);
        System.out.println("3. " + fecha3);
        op = sc.nextInt();

        if (op == 1) {
            fechaDia = fecha1;
        }
        if (op == 2) {
            fechaDia = fecha2;
        }
        if (op == 3) {
            fechaDia = fecha3;
        }
        Medico medico = new Medico(codMedico, con);

        pedirConsulta(medico, fechaDia, paciente);

    }
    
    public static void pedirConsulta(Medico med, String dia, Paciente paciente) throws SQLException {
        boolean[] consultas;
        consultas = new boolean[24];
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
        for (int i = 0; i < consultas.length; i++) {
            String horaImp = "";
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

            if (consultas[i] == false) {
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
        med.addCita(mapa.get(cadena), dia, paciente.getDNI(), paciente.getContCitas());
        paciente.addCita();
    }
    
    public static void eliminarCita(Paciente paciente, Conexion con) throws SQLException {
        int op = 0;

        System.out.println("Elija la cita que desea cancelar: ");
        Scanner sc = new Scanner(System.in);
        PreparedStatement preparedStmt;
        Connection reg = con.getCon();
        String sql = "SELECT medico.nombre,citas.medico, medico.n_colegiado, especialidad.nombre, citas.cod_cita, citas.hora, citas.dia"
                + "  FROM citas, medico, especialidad "
                + "WHERE paciente=? AND medico.n_colegiado=citas.medico "
                + "AND medico.especialidad= especialidad.cod_especialidad";
        preparedStmt = reg.prepareStatement(sql);
        preparedStmt.setString(1, paciente.getDNI());
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
        
        op = sc.nextInt()-1;
        sql = "DELETE FROM citas WHERE cod_cita=?";
        preparedStmt = reg.prepareStatement(sql);
        preparedStmt.setString(1, codigosCita.get(op));
        preparedStmt.execute();
        int codMedico=Integer.parseInt(codMeds.get(op));
        Medico med=new Medico(codMedico,con);
        String[]fechasdiv=fechas.get(op).split("-");
        if(Integer.parseInt(fechasdiv[1])<10)  fechasdiv[1]=fechasdiv[1].substring(1);
        String fechaCorrecta=fechasdiv[0]+"-"+ fechasdiv[1]+"-"+fechasdiv[2];
        System.out.println("Se ha eliminado la cita: " + fechaCorrecta + " " + horas.get(op) + " con " + medicos.get(op)
                    + ", " + especialidades.get(op));
        med.eliminarCita(fechaCorrecta, horas.get(op));        
    }

    public static void main(String[] args) {
        Conexion con = null;
        Paciente paciente;

        try {
            con = new Conexion();
            int op = 0;
            String DNI;
            Scanner sc = new Scanner(System.in);
            boolean result = false;
            
            do {
                boolean correcto = false;
                do {
                    System.out.println("Introduzca el DNI del paciente: ");
                    DNI = sc.nextLine().toUpperCase();
                    if (DNI.matches("[A-Z0-9]?[0-9]{7}[A-Z]")) {
                        correcto = true;
                    } else {
                        System.out.println("ERROR: DNI no válido.");
                    }
                } while (!correcto);
                
                paciente = new Paciente(DNI, con);

                result = paciente.estaBD();
                if (!result) {
                    String resp;

                    System.out.println("El paciente no está registrado en el sistema.");
                    System.out.println("¿Desea registrar el paciente? (S/N)");

                    resp = sc.nextLine().toLowerCase();
                    if (resp.equals("s")) {
                        paciente.addPacienteBD();
                        result = true;
                    }
                }
            } while (!result);

            while (op != 3) {
                System.out.println("Selecciona la opción que desea realizar: ");
                System.out.println("1. Pedir cita ");
                System.out.println("2. Cancelar una cita ");
                System.out.println("3. Salir");
                op = sc.nextInt();

                switch (op) {
                    case 1:
                        pedirCita(paciente, con);
                        break;
                    case 2:
                        eliminarCita(paciente, con);
                        break;
                }
            }
            con.desconectar();
        } catch (SQLException ex) {
            System.out.println(ex.fillInStackTrace());
        }

    }

}
