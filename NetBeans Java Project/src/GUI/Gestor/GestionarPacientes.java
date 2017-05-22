package GUI.Gestor;

import GUI.Gestor.Paciente.MenuDePaciente;
import clases.Gestor;
import clases.Paciente;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author bm0275
 */
public class GestionarPacientes extends javax.swing.JPanel {

	/**
	 * Creates new form GestionarPacientes
	 */
	private Gestor gestor;
	private Paciente paciente;
	private MenuDePaciente menuDePaciente;
	private TableRowSorter trsFiltro;

	public GestionarPacientes(Gestor gestor) {
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

        menuOpciones = new javax.swing.JPanel();
        buttonCogerPaciente = new javax.swing.JButton();
        fieldDNI = new javax.swing.JLabel();
        labelTitulo = new javax.swing.JLabel();
        mostrarDatos = new javax.swing.JPanel();
        jScrollPane14 = new javax.swing.JScrollPane();
        tablaInfo = new javax.swing.JTable();
        buttonMostrar = new javax.swing.JButton();
        labelMostrar = new javax.swing.JLabel();
        desplegableColumnas = new javax.swing.JComboBox();
        textFieldBuscar = new javax.swing.JTextField();

        buttonCogerPaciente.setText("Coger paciente");
        buttonCogerPaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCogerPacienteActionPerformed(evt);
            }
        });

        fieldDNI.setFont(new java.awt.Font("Century", 1, 14)); // NOI18N
        fieldDNI.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fieldDNI.setText("DNI PACIENTE");

        labelTitulo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        labelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelTitulo.setText("Menu de paciente");
        labelTitulo.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout menuOpcionesLayout = new javax.swing.GroupLayout(menuOpciones);
        menuOpciones.setLayout(menuOpcionesLayout);
        menuOpcionesLayout.setHorizontalGroup(
            menuOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuOpcionesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(menuOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
                    .addGroup(menuOpcionesLayout.createSequentialGroup()
                        .addGroup(menuOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(buttonCogerPaciente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(fieldDNI, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        menuOpcionesLayout.setVerticalGroup(
            menuOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuOpcionesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(buttonCogerPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(fieldDNI)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaInfo.setColumnSelectionAllowed(true);
        tablaInfo.getTableHeader().setReorderingAllowed(false);
        tablaInfo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaInfoMouseClicked(evt);
            }
        });
        jScrollPane14.setViewportView(tablaInfo);
        tablaInfo.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        buttonMostrar.setText("Mostrar datos");
        buttonMostrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonMostrarActionPerformed(evt);
            }
        });

        labelMostrar.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        labelMostrar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelMostrar.setText("Mostrar pacientes");
        labelMostrar.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        desplegableColumnas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "DNI", "Nombre", "Apellidos", "Seguro" }));

        textFieldBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textFieldBuscarKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout mostrarDatosLayout = new javax.swing.GroupLayout(mostrarDatos);
        mostrarDatos.setLayout(mostrarDatosLayout);
        mostrarDatosLayout.setHorizontalGroup(
            mostrarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mostrarDatosLayout.createSequentialGroup()
                .addGroup(mostrarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelMostrar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(mostrarDatosLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(mostrarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 889, Short.MAX_VALUE)
                            .addGroup(mostrarDatosLayout.createSequentialGroup()
                                .addComponent(desplegableColumnas, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(textFieldBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(buttonMostrar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        mostrarDatosLayout.setVerticalGroup(
            mostrarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mostrarDatosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelMostrar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mostrarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(desplegableColumnas, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonMostrar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textFieldBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(menuOpciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addComponent(mostrarDatos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(21, 21, 21))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(menuOpciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(mostrarDatos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

	/**
	 * Coge un paciente que exista en la base de datos y monta menuPaciente
	 *
	 * @param evt
	 */
    private void buttonCogerPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCogerPacienteActionPerformed
		try {
			if (gestor.existePacienteBD(fieldDNI.getText())) {
				this.paciente = gestor.getPaciente(fieldDNI.getText());
				crearMenu();
			} else {
				JOptionPane.showMessageDialog(this, "El dni no es correcto", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} catch (SQLException ex) {
			Logger.getLogger(GestionarPacientes.class.getName()).log(Level.SEVERE, null, ex);
		}
    }//GEN-LAST:event_buttonCogerPacienteActionPerformed


    private void textFieldBuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textFieldBuscarKeyTyped
		// TODO add your handling code here:
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

    private void buttonMostrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonMostrarActionPerformed
		try {
			ResultSet rs = gestor.mostrarPacientes();
			tablaInfo.setModel(buildTableModel(rs));

		} catch (SQLException ex) {
			Logger.getLogger(GestionarPacientes.class.getName()).log(Level.SEVERE, null, ex);
		}
    }//GEN-LAST:event_buttonMostrarActionPerformed

    private void tablaInfoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaInfoMouseClicked
		int row = tablaInfo.rowAtPoint(evt.getPoint());
		int col = tablaInfo.columnAtPoint(evt.getPoint());
		if (row >= 0 && col >= 0) {
			fieldDNI.setText((String) tablaInfo.getValueAt(row, 0));
		}
    }//GEN-LAST:event_tablaInfoMouseClicked

	/**
	 * Crea el modelo para el jTable
	 *
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

	/**
	 * Sirve para filtar en el jTable
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
	 * Crea un menu de paciente
	 */
	private void crearMenu() {
		menuDePaciente = new MenuDePaciente(gestor, paciente);

		mostrarDatos.setSize(mostrarDatos.getWidth() + menuOpciones.getWidth(), mostrarDatos.getHeight());
		menuDePaciente.setSize(mostrarDatos.getWidth(), mostrarDatos.getHeight());
		menuDePaciente.setLocation(0, 0);

		mostrarDatos.removeAll();
		menuOpciones.setVisible(false);
		mostrarDatos.add(menuDePaciente, BorderLayout.WEST);
		mostrarDatos.revalidate();
		mostrarDatos.repaint();
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonCogerPaciente;
    private javax.swing.JButton buttonMostrar;
    private javax.swing.JComboBox desplegableColumnas;
    private javax.swing.JLabel fieldDNI;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JLabel labelMostrar;
    private javax.swing.JLabel labelTitulo;
    private javax.swing.JPanel menuOpciones;
    private javax.swing.JPanel mostrarDatos;
    private javax.swing.JTable tablaInfo;
    private javax.swing.JTextField textFieldBuscar;
    // End of variables declaration//GEN-END:variables
}
