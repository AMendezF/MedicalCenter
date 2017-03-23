
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author Berta
 */
public class Medico {

    Integer n_colegiado;
    Conexion con;
    boolean[] dia1;
    boolean[] dia2;
    boolean[] dia3;
    PreparedStatement preparedStmt = null;
    String fecha1;
    String fecha2;
    String fecha3;
    Calendar fecha = new GregorianCalendar();
    int horaI = 0;

    public Medico(Integer n_colegiado, Conexion con) throws SQLException {
        this.n_colegiado = n_colegiado;
        this.con = con;
        dia1 = new boolean[(240 / this.getTiempoMin())];
        dia2 = new boolean[(240 / this.getTiempoMin())];
        dia3 = new boolean[(240 / this.getTiempoMin())];
        

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

        if ("Mañana".equals(this.getTurno())) {
            horaI = 9;
        } else if ("Tarde".equals(this.getTurno())) {
            horaI = 15;
        }
        Connection reg = con.getCon();
        String sql = "SELECT hora FROM citas WHERE medico=? AND dia =?;";
        preparedStmt = reg.prepareStatement(sql);
        preparedStmt.setInt(1, n_colegiado);
        preparedStmt.setDate(2, java.sql.Date.valueOf(fecha1));
        ResultSet rs = preparedStmt.executeQuery();

        List<String> horas;
        horas = new ArrayList<>();
        while (rs.next()) {
            horas.add(rs.getTime("hora").toString());
        }
        for (int i = 0; i < dia1.length; i++) {
            dia1[i] = false;
        }
        for (int i = 0; i < horas.size(); i++) {
            String hora[] = horas.get(i).split(":");
            int horadiv = Integer.parseInt(hora[0]);
            int minutos = Integer.parseInt(hora[1]);
            int index = ((horadiv - horaI) * 60 + minutos) / this.getTiempoMin();
            dia1[index] = true;
        }
        horas.clear();

        preparedStmt = reg.prepareStatement(sql);
        preparedStmt.setInt(1, n_colegiado);
        preparedStmt.setDate(2, java.sql.Date.valueOf(fecha2));
        rs = preparedStmt.executeQuery();

        while (rs.next()) {
            horas.add(rs.getTime("hora").toString());
        }
        for (int i = 0; i < dia2.length; i++) {
            dia2[i] = false;
        }
        for (int i = 0; i < horas.size(); i++) {
            String hora[] = horas.get(i).split(":");
            int horadiv = Integer.parseInt(hora[0]);
            int minutos = Integer.parseInt(hora[1]);
            int index = ((horadiv - horaI) * 60 + minutos) / this.getTiempoMin();
            dia2[index] = true;
        }
        horas.clear();

        preparedStmt = reg.prepareStatement(sql);
        preparedStmt.setInt(1, n_colegiado);
        preparedStmt.setDate(2, java.sql.Date.valueOf(fecha3));
        rs = preparedStmt.executeQuery();

        while (rs.next()) {
            horas.add(rs.getTime("hora").toString());
        }
        for (int i = 0; i < dia3.length; i++) {
            dia3[i] = false;
        }
        for (int i = 0; i < horas.size(); i++) {
            String hora[] = horas.get(i).split(":");
            int horadiv = Integer.parseInt(hora[0]);
            int minutos = Integer.parseInt(hora[1]);
            int index = ((horadiv - horaI) * 60 + minutos) / this.getTiempoMin();
            dia3[index] = true;
        }
        horas.clear();
     }

    public int getTiempoMin() throws SQLException {
        int result = 0;
        
        Connection reg = con.getCon();
        String sql = "SELECT tiempo_min FROM medico WHERE N_colegiado=?;";
        preparedStmt = reg.prepareStatement(sql);
        preparedStmt.setInt(1, n_colegiado);
        ResultSet rs = preparedStmt.executeQuery();
        
        if (rs.next()) {
            result = rs.getInt("tiempo_min");
        }
        return result;
    }

    public String getTurno() throws SQLException {
        String result = null;
        Connection reg = con.getCon();
        String sql = "SELECT horario FROM medico WHERE N_colegiado=?;";
        preparedStmt = reg.prepareStatement(sql);
        preparedStmt.setInt(1, this.n_colegiado);
        ResultSet rs = preparedStmt.executeQuery();
        if (rs.next()) {
            result = rs.getString("horario");
        }
        return result;
    }

    public String getNombre() throws SQLException{
        String result = null;
        Connection reg = con.getCon();
        String sql = "SELECT nombre FROM medico WHERE N_colegiado=?";
        preparedStmt = reg.prepareStatement(sql);
        preparedStmt.setInt(1, this.n_colegiado);
        ResultSet rs = preparedStmt.executeQuery();
        if (rs.next()) {
            result = rs.getString("nombre");
        }
        return result;
    }
    
    public String getEspecialidad() throws SQLException {
        String result = null;
        Connection reg = con.getCon();
        String sql = "SELECT especialidad.nombre, especialidad.cod_especialidad FROM especialidad, medico WHERE N_colegiado=? AND especialidad=cod_especialidad;";
        preparedStmt = reg.prepareStatement(sql);
        preparedStmt.setInt(1, n_colegiado);
        ResultSet rs = preparedStmt.executeQuery();
        if (rs.next()) {
            result = rs.getString("especialidad.nombre");
        }
        return result;
    }

    public void addCita(String hora, String fecha, String paciente, int contCitas) throws SQLException {
        String horas[] = hora.split(":");
        int codEsp=0;
        int horadiv = Integer.parseInt(horas[0]);
        int minutos = Integer.parseInt(horas[1]);
        Connection reg = con.getCon();
        String sql = "SELECT especialidad FROM medico WHERE N_colegiado=?;";
        preparedStmt = reg.prepareStatement(sql);
        preparedStmt.setInt(1, n_colegiado);
        ResultSet rs = preparedStmt.executeQuery();
        if (rs.next()) {
            codEsp = rs.getInt("especialidad");
        }
        sql = "INSERT INTO citas ( cod_cita, dia, hora, medico, especialidad, paciente)"
                + "VALUES (?,?,?,?,?,?)";
        preparedStmt = reg.prepareStatement(sql);
        String codcita=paciente+(contCitas+1);
        preparedStmt.setString(1, codcita);
        preparedStmt.setString(2, fecha);
        preparedStmt.setString(3, hora);
        preparedStmt.setInt(4, n_colegiado);
        preparedStmt.setInt(5, codEsp);
        preparedStmt.setString(6, paciente);
        preparedStmt.execute();
        
        int index = ((horadiv - horaI) * 60 + minutos) / this.getTiempoMin();
        boolean consultaFecha[] = getConsultas(fecha);
        consultaFecha[index]=false;
    }

    public void eliminarCita(String dia, String hora) throws SQLException{
        String horas[] = hora.split(":");
        int codEsp=0;
        boolean consultaFecha[] = getConsultas(dia);
        int horadiv = Integer.parseInt(horas[0]);
        int minutos = Integer.parseInt(horas[1]);
        
        int index = ((horadiv - horaI) * 60 + minutos) / this.getTiempoMin();
        consultaFecha[index]=false;
        
    }
    public boolean[] getConsultas(String dia) throws SQLException {
        if (dia.equals(fecha1)) {
            return dia1;
        } else if (dia.equals(fecha2)) {
            return dia2;
        } else if (dia.equals(fecha3)) {
            return dia3;
        }
        return null;
    }

}
