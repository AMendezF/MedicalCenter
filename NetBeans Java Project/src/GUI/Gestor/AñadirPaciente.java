package GUI.Gestor;

import clases.Conexion;
import clases.Gestor;
import clases.Paciente;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Juan
 */
public class AñadirPaciente extends javax.swing.JPanel {

	/**
	 * Creates new form AñadirPaciente
	 */
	private Gestor gestor;
	private Paciente paciente;
	private TableRowSorter trsFiltro;

	public AñadirPaciente(Gestor gestor) {
		initComponents();
		this.gestor = gestor;
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        tituloPaciente = new javax.swing.JLabel();
        labelDNI = new javax.swing.JLabel();
        DNI = new javax.swing.JTextField();
        labelNombre = new javax.swing.JLabel();
        nombre = new javax.swing.JTextField();
        labelApellidos = new javax.swing.JLabel();
        apellidos = new javax.swing.JTextField();
        labelSeguro = new javax.swing.JLabel();
        compSeguro = new javax.swing.JTextField();
        buttonAñadirPaciente = new javax.swing.JButton();
        DNIOK = new javax.swing.JLabel();
        nombreOK = new javax.swing.JLabel();
        apellidoOK = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaInfo = new javax.swing.JTable();
        textFieldBuscar = new javax.swing.JTextField();
        desplegableColumnas = new javax.swing.JComboBox();
        labelPacientesExistentes = new javax.swing.JLabel();
        mostrar = new javax.swing.JButton();

        tituloPaciente.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        tituloPaciente.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tituloPaciente.setText("Añadir nuevo paciente");
        tituloPaciente.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        labelDNI.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        labelDNI.setText("DNI");

        DNI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                DNIKeyReleased(evt);
            }
        });

        labelNombre.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        labelNombre.setText("Nombre");

        nombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                nombreKeyReleased(evt);
            }
        });

        labelApellidos.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        labelApellidos.setText("Apellidos");

        apellidos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                apellidosKeyReleased(evt);
            }
        });

        labelSeguro.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        labelSeguro.setText("Compañia de seguro");

        buttonAñadirPaciente.setText("Añadir");
        buttonAñadirPaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAñadirPacienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tituloPaciente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(labelApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(apellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(labelSeguro, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(compSeguro, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addComponent(apellidoOK))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(labelDNI, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(DNI, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(DNIOK))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(labelNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(nombreOK)))
                        .addGap(0, 83, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(buttonAñadirPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tituloPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelDNI, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DNI, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DNIOK))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nombreOK))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(apellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(apellidoOK)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelSeguro, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(compSeguro, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(buttonAñadirPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(237, Short.MAX_VALUE))
        );

        tablaInfo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "DNI", "Nombre", "Apellidos", "Seguro"
            }
        ));
        tablaInfo.setColumnSelectionAllowed(true);
        jScrollPane4.setViewportView(tablaInfo);

        textFieldBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textFieldBuscarKeyTyped(evt);
            }
        });

        desplegableColumnas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "DNI", "Nombre", "Apellidos", "Seguro" }));

        labelPacientesExistentes.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        labelPacientesExistentes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelPacientesExistentes.setText("Pacientes existentes");
        labelPacientesExistentes.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        mostrar.setText("Mostrar datos");
        mostrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mostrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelPacientesExistentes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(desplegableColumnas, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(mostrar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                                .addComponent(textFieldBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelPacientesExistentes, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(textFieldBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(mostrar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(desplegableColumnas, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

	/**
	 * Recoge la informacion del formulario y lo añade a la base de datos
	 * @param evt 
	 */
    private void buttonAñadirPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAñadirPacienteActionPerformed
		Object[] options = {"Si", "No"};
		int confirmar;
		/*
		 if (comprobarDNI(dni.getText())  {
		 if (nombre.getText() = "" || apellidos.getText() = "") {
		 if (noExistePaciente()  {
		 confirmar = JOptionPane.showOptionDialog(this,"Se va ha crear el paciente, ¿desea confirmar la operacion?","Confirmar",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,  options,  options[0]);
		 // Confirmar devuelve un 0 si quiere confirmarlo
		 //devuelve un 1 si no lo confirma
		 if (confirmar == 0){
		 paciente = new Paciente(dni.getText(), nombre.getText(), apellidos.getText(), compSeguro.getText());
		 addPaciente(paciente);
		 JOptionPane.showMessageDialog(this, "Se ha hañadido el paciente con DNI " + dni.getText());
		 }
		 } else {
		 JOptionPane.showMessageDialog(this, "El dni ya esta en uso", "Error", JOptionPane.ERROR_MESSAGE);
		 }
		 } else {
		 JOptionPane.showMessageDialog(this, "Algun campo esta vacio", "Cuidado!", JOptionPane.WARNING_MESSAGE);
		 }
		 } else {
		 JOptionPane.showMessageDialog(this, "El dni no es correcto", "Error", JOptionPane.ERROR_MESSAGE);
		 }
		 System.out.println(dni.getText() + "" + nombre.getText() + "" + apellidos.getText() + "" + compSeguro.getText());
		 */
    }//GEN-LAST:event_buttonAñadirPacienteActionPerformed

    private void textFieldBuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textFieldBuscarKeyTyped
		textFieldBuscar.addKeyListener(new KeyAdapter() {
			public void keyReleased(final KeyEvent e) {
				String cadena = (textFieldBuscar.getText());
				textFieldBuscar.setText(cadena);
				repaint();
				filtro();
			}
		});
		trsFiltro = new TableRowSorter(tablaInfo.getModel());
		tablaInfo.setRowSorter(trsFiltro);
    }//GEN-LAST:event_textFieldBuscarKeyTyped

	/**
	 * Muestra los pacientes en la jTable para el usuario
	 * @param evt 
	 */
    private void mostrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mostrarActionPerformed
		try {
			ResultSet rs = gestor.mostrarPacientes();
			tablaInfo.setModel(buildTableModel(rs));
		} catch (SQLException ex) {
			Logger.getLogger(mostrarPacientes.class.getName()).log(Level.SEVERE, null, ex);
		}
    }//GEN-LAST:event_mostrarActionPerformed

	/**
	 * Muestra por pantalla si el campo DNI es correcto
	 *
	 * @param evt
	 */
    private void DNIKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DNIKeyReleased
		/*
		 if (gestor.comprobarDNI(DNI.getText())){
			DNIOK.setForeground(Color.green);
			DNIOK.setText("OK!!");
		 }
		else if (gestor.existePaciente(DNI.getText()){
			DNIOK.setForeground(Color.red);
			DNIOK.setText("Ya existe");
		}
		else {
		DNIOK.setForeground(Color.red);
		DNIOK.setText("KO!!");
		}	
		 */
    }//GEN-LAST:event_DNIKeyReleased

	/**
	 * Muestra por pantalla si el campo esta o no vacio
	 *
	 * @param evt
	 */
    private void nombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nombreKeyReleased
		if (nombre.getText().equals("")) {
			nombreOK.setForeground(Color.red);
			nombreOK.setText("Vacio!!");
		} else //if (!nombreOK.contieneNumero())
		{
			nombreOK.setForeground(Color.black);
			nombreOK.setText("OK!!");
		}
    }//GEN-LAST:event_nombreKeyReleased

	/**
	 * Muestra por pantalla si el campo esta o no vacio
	 *
	 * @param evt
	 */
    private void apellidosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_apellidosKeyReleased
		if (apellidos.getText().equals("")) {
			apellidoOK.setForeground(Color.red);
			apellidoOK.setText("Vacio!!");
		} else //if (!apellidoOK.contieneNumero())
		{
			apellidoOK.setForeground(Color.black);
			apellidoOK.setText("OK!!");
		}
    }//GEN-LAST:event_apellidosKeyReleased

	/**
	 * Filtro necesario para manejar la informacion
	 */
	public void filtro() {
		int columnaABuscar = 0;
		if (desplegableColumnas.getSelectedItem().equals("DNI")) {
			columnaABuscar = 0;
		}
		if (desplegableColumnas.getSelectedItem().equals("Nombre")) {
			columnaABuscar = 1;
		}
		if (desplegableColumnas.getSelectedItem().equals("Apellidos")) {
			columnaABuscar = 2;
		}
		if (desplegableColumnas.getSelectedItem().equals("Seguro")) {
			columnaABuscar = 3;
		}
		trsFiltro.setRowFilter(RowFilter.regexFilter(textFieldBuscar.getText(), columnaABuscar));
	}

	/**
	 * Crea el modelo de la tabla para insertarlo
	 * Podriamos sacarlo a otra clase y organizarlo, se repite mucho este codigo
	 * @param rs
	 * @return
	 * @throws SQLException 
	 */
	public DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
		java.sql.ResultSetMetaData metaData = rs.getMetaData();

		// names of columns
		Vector<String> columnNames = new Vector<String>();
		int columnCount = metaData.getColumnCount();
		for (int column = 1; column <= columnCount; column++) {
			columnNames.add(metaData.getColumnName(column));
		}

		// data of the table
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		while (rs.next()) {
			Vector<Object> vector = new Vector<Object>();
			for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
				vector.add(rs.getObject(columnIndex));
			}
			data.add(vector);
		}
		return new DefaultTableModel(data, columnNames);
	}


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField DNI;
    private javax.swing.JLabel DNIOK;
    private javax.swing.JLabel apellidoOK;
    private javax.swing.JTextField apellidos;
    private javax.swing.JButton buttonAñadirPaciente;
    private javax.swing.JTextField compSeguro;
    private javax.swing.JComboBox desplegableColumnas;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel labelApellidos;
    private javax.swing.JLabel labelDNI;
    private javax.swing.JLabel labelNombre;
    private javax.swing.JLabel labelPacientesExistentes;
    private javax.swing.JLabel labelSeguro;
    private javax.swing.JButton mostrar;
    private javax.swing.JTextField nombre;
    private javax.swing.JLabel nombreOK;
    private javax.swing.JTable tablaInfo;
    private javax.swing.JTextField textFieldBuscar;
    private javax.swing.JLabel tituloPaciente;
    // End of variables declaration//GEN-END:variables
}
