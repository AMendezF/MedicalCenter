package GUI.Gestor;

import clases.Gestor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class BorrarPaciente extends javax.swing.JPanel {

	private Gestor gestor;
	private TableRowSorter trsActivo;
	private TableRowSorter trsInactivo;
	private DefaultTableModel tabla;
	private String[] columnasActivos;
	private String[] columnasInactivos;

	public BorrarPaciente(Gestor gestor) {
		initComponents();
		this.gestor = gestor;
		this.setBackground(new java.awt.Color(150, 190, 230));
		panelPacienteActivo.setBackground(this.getBackground());
		panelPacienteInactivo.setBackground(this.getBackground());

		actualizarDatosPacientesActivos();
		actualizarDatosPacientesInactivos();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPacienteActivo = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaActivos = new javax.swing.JTable();
        textActivos = new javax.swing.JTextField();
        desplegableActivos = new javax.swing.JComboBox();
        labelActivos = new javax.swing.JLabel();
        actualizarActivos = new javax.swing.JButton();
        buttonExportar = new javax.swing.JButton();
        fieldBorrar = new javax.swing.JLabel();
        panelPacienteInactivo = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablaInactivos = new javax.swing.JTable();
        textInactivos = new javax.swing.JTextField();
        desplegableInactivos = new javax.swing.JComboBox();
        labelInactivos = new javax.swing.JLabel();
        actualizarInactivos = new javax.swing.JButton();
        buttonImportar = new javax.swing.JButton();
        fieldReinsertar = new javax.swing.JLabel();

        panelPacienteActivo.setMaximumSize(new java.awt.Dimension(650, 650));

        tablaActivos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tablaActivos.setColumnSelectionAllowed(true);
        tablaActivos.getTableHeader().setReorderingAllowed(false);
        tablaActivos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaActivosMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tablaActivos);
        tablaActivos.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        textActivos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textActivosKeyTyped(evt);
            }
        });

        desplegableActivos.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "DNI", "Nombre", "Apellidos", "Seguro" }));

        labelActivos.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        labelActivos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelActivos.setText("Pacientes activos");
        labelActivos.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        actualizarActivos.setText("Actualizar datos");
        actualizarActivos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actualizarActivosActionPerformed(evt);
            }
        });

        buttonExportar.setText("Exportar");
        buttonExportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonExportarActionPerformed(evt);
            }
        });

        fieldBorrar.setFont(new java.awt.Font("Century", 1, 14)); // NOI18N
        fieldBorrar.setText("Introduce DNI");

        javax.swing.GroupLayout panelPacienteActivoLayout = new javax.swing.GroupLayout(panelPacienteActivo);
        panelPacienteActivo.setLayout(panelPacienteActivoLayout);
        panelPacienteActivoLayout.setHorizontalGroup(
            panelPacienteActivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPacienteActivoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPacienteActivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPacienteActivoLayout.createSequentialGroup()
                        .addComponent(labelActivos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPacienteActivoLayout.createSequentialGroup()
                        .addGroup(panelPacienteActivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelPacienteActivoLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(panelPacienteActivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jScrollPane4)
                                    .addGroup(panelPacienteActivoLayout.createSequentialGroup()
                                        .addComponent(desplegableActivos, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(283, 283, 283)
                                        .addComponent(textActivos, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(panelPacienteActivoLayout.createSequentialGroup()
                                .addComponent(actualizarActivos, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(fieldBorrar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(buttonExportar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18))))
        );
        panelPacienteActivoLayout.setVerticalGroup(
            panelPacienteActivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPacienteActivoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelActivos, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelPacienteActivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(desplegableActivos, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textActivos, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
                .addGap(36, 36, 36)
                .addGroup(panelPacienteActivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(actualizarActivos, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonExportar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fieldBorrar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        panelPacienteInactivo.setMaximumSize(new java.awt.Dimension(650, 650));

        tablaInactivos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tablaInactivos.setColumnSelectionAllowed(true);
        tablaInactivos.getTableHeader().setReorderingAllowed(false);
        tablaInactivos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaInactivosMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tablaInactivos);
        tablaInactivos.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        textInactivos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textInactivosKeyTyped(evt);
            }
        });

        desplegableInactivos.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "DNI", "Nombre", "Apellidos", "Seguro" }));

        labelInactivos.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        labelInactivos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelInactivos.setText("Pacientes inactivos");
        labelInactivos.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        actualizarInactivos.setText("Actualizar datos");
        actualizarInactivos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actualizarInactivosActionPerformed(evt);
            }
        });

        buttonImportar.setText("Importar");
        buttonImportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonImportarActionPerformed(evt);
            }
        });

        fieldReinsertar.setFont(new java.awt.Font("Century", 1, 14)); // NOI18N
        fieldReinsertar.setText("Introduce DNI");

        javax.swing.GroupLayout panelPacienteInactivoLayout = new javax.swing.GroupLayout(panelPacienteInactivo);
        panelPacienteInactivo.setLayout(panelPacienteInactivoLayout);
        panelPacienteInactivoLayout.setHorizontalGroup(
            panelPacienteInactivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPacienteInactivoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPacienteInactivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 647, Short.MAX_VALUE)
                    .addComponent(labelInactivos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelPacienteInactivoLayout.createSequentialGroup()
                        .addComponent(buttonImportar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(fieldReinsertar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(actualizarInactivos, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPacienteInactivoLayout.createSequentialGroup()
                        .addComponent(desplegableInactivos, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(textInactivos, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panelPacienteInactivoLayout.setVerticalGroup(
            panelPacienteInactivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPacienteInactivoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelInactivos, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelPacienteInactivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(desplegableInactivos, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textInactivos, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE)
                .addGap(33, 33, 33)
                .addGroup(panelPacienteInactivoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(actualizarInactivos, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonImportar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fieldReinsertar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelPacienteActivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panelPacienteInactivo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelPacienteActivo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelPacienteInactivo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

	/**
	 * Carga un resulSet y lo muestra en la tabla Activos
	 */
	private void actualizarDatosPacientesActivos() {
		try {
			ResultSet rs = gestor.mostrarPacientes();
			TableAdaptor aux = new TableAdaptor(rs);
			setTabla(aux.getValue());
			DefaultTableModel tabla = getTabla();
			tablaActivos.setModel(tabla);
			cargarDesplegablesActivos();
		} catch (SQLException ex) {
			Logger.getLogger(AñadirPaciente.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Carga un resulSet y lo muestra en la tabla Inactivos
	 */
	private void actualizarDatosPacientesInactivos() {
		try {
			ResultSet rs = gestor.mostrarPacientesBorrados();
			TableAdaptor aux = new TableAdaptor(rs);
			setTabla(aux.getValue());
			DefaultTableModel tabla = getTabla();
			tablaInactivos.setModel(tabla);
			cargarDesplegablesInactivos();
		} catch (SQLException ex) {
			Logger.getLogger(AñadirPaciente.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Sirve para buscar en el jTable de pacientes activos
	 *
	 * @param evt
	 */
    private void textActivosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textActivosKeyTyped
		textActivos.addKeyListener(new KeyAdapter() {
			public void keyReleased(final KeyEvent e) {
				String cadena = (textActivos.getText());
				textActivos.setText(cadena);
				repaint();
				filtroActivo();
			}
		});
		trsActivo = new TableRowSorter(tablaActivos.getModel());
		tablaActivos.setRowSorter(trsActivo);
    }//GEN-LAST:event_textActivosKeyTyped

	/**
	 * Muestra los datos de pacientes activos
	 *
	 * @param evt
	 */
    private void actualizarActivosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actualizarActivosActionPerformed
		actualizarDatosPacientesActivos();
    }//GEN-LAST:event_actualizarActivosActionPerformed

	/**
	 * Exporta un paciente a la tabla borrado
	 *
	 * @param evt
	 */
    private void buttonExportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonExportarActionPerformed
		Object[] options = {"Si", "No"};
		int confirmar;

		String dni = fieldBorrar.getText();

		if (comprobarDNIActivo(dni)) {
			confirmar = JOptionPane.showOptionDialog(this, "Se va ha exportar el paciente, ¿desea confirmar la operacion?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			// Confirmar devuelve un 0 si quiere confirmarlo
			// Devuelve un 1 si no lo confirma
			if (confirmar == 0) {
				if (gestor.removePacienteBD(dni)) {
					JOptionPane.showMessageDialog(this, "Se ha borrado el paciente con DNI " + dni);
					fieldBorrar.setText("Introduce DNI");
					actualizarDatosPacientesActivos();
					actualizarDatosPacientesInactivos();
				} else {
					JOptionPane.showMessageDialog(this, "No se ha podido exportar", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		} else {
			JOptionPane.showMessageDialog(this, "El dni es incorrecto", "Error", JOptionPane.ERROR_MESSAGE);
		}
    }//GEN-LAST:event_buttonExportarActionPerformed

	/**
	 * Sirve para buscar en el jTable de pacientes inactivos
	 *
	 * @param evt
	 */
    private void textInactivosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textInactivosKeyTyped
		textInactivos.addKeyListener(new KeyAdapter() {
			public void keyReleased(final KeyEvent e) {
				String cadena = (textInactivos.getText());
				textInactivos.setText(cadena);
				repaint();
				filtroInactivo();
			}
		});
		trsInactivo = new TableRowSorter(tablaInactivos.getModel());
		tablaInactivos.setRowSorter(trsInactivo);
    }//GEN-LAST:event_textInactivosKeyTyped

	/**
	 * Muestra los pacientes inactivos
	 *
	 * @param evt
	 */
    private void actualizarInactivosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actualizarInactivosActionPerformed
		actualizarDatosPacientesInactivos();
    }//GEN-LAST:event_actualizarInactivosActionPerformed

	/**
	 * Importa un paciente de la tabla borrado
	 *
	 * @param evt
	 */
    private void buttonImportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonImportarActionPerformed
		Object[] options = {"Si", "No"};
		int confirmar;

		String dni = fieldReinsertar.getText();

		if (comprobarDNIInactivo(dni)) {
			confirmar = JOptionPane.showOptionDialog(this, "Se va ha importar el paciente, ¿desea confirmar la operacion?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			// Confirmar devuelve un 0 si quiere confirmarlo
			// Devuelve un 1 si no lo confirma
			if (confirmar == 0) {
				if (gestor.removePacienteBDBorrado(dni)) {
					JOptionPane.showMessageDialog(this, "Se ha importado el paciente con DNI " + dni);
					fieldReinsertar.setText("Introduce DNI");
					actualizarDatosPacientesActivos();
					actualizarDatosPacientesInactivos();
				} else {
					JOptionPane.showMessageDialog(this, "No se ha podido importar", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		} else {
			JOptionPane.showMessageDialog(this, "El dni es incorrecto", "Error", JOptionPane.ERROR_MESSAGE);
		}
    }//GEN-LAST:event_buttonImportarActionPerformed

	/**
	 * Coge el valor DNI al pinchar sobre la tabla
	 *
	 * @param evt
	 */
    private void tablaActivosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaActivosMouseClicked
		int row = tablaActivos.rowAtPoint(evt.getPoint());
		int col = tablaActivos.columnAtPoint(evt.getPoint());
		if (row >= 0 && col >= 0) {
			fieldBorrar.setText((String) tablaActivos.getValueAt(row, 0));
		}
    }//GEN-LAST:event_tablaActivosMouseClicked

	/**
	 * Coge el valor DNI al pinchar sobre la tabla
	 *
	 * @param evt
	 */
    private void tablaInactivosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaInactivosMouseClicked
		int row = tablaInactivos.rowAtPoint(evt.getPoint());
		int col = tablaInactivos.columnAtPoint(evt.getPoint());
		if (row >= 0 && col >= 0) {
			fieldReinsertar.setText((String) tablaInactivos.getValueAt(row, 0));
		}
    }//GEN-LAST:event_tablaInactivosMouseClicked

	/**
	 * Getters de la tabla
	 *
	 * @return
	 */
	public DefaultTableModel getTabla() {
		return this.tabla;
	}

	/**
	 * Setters de la tabla
	 *
	 * @param tabla
	 */
	public void setTabla(DefaultTableModel tabla) {
		this.tabla = tabla;
	}

	/**
	 * Carga el desplegable de paciente a partir de la tabla
	 */
	private void cargarDesplegablesActivos() {
		int numColums = tablaActivos.getColumnCount();
		this.columnasActivos = new String[numColums];
		for (int i = 0; i < numColums; i++) {
			this.columnasActivos[i] = tablaActivos.getColumnName(i);
		}
		desplegableActivos.setModel(new javax.swing.DefaultComboBoxModel(this.columnasActivos));
	}

	/**
	 * Carga el desplegable de paciente a partir de la tabla
	 */
	private void cargarDesplegablesInactivos() {
		int numColums = tablaInactivos.getColumnCount();
		this.columnasInactivos = new String[numColums];
		for (int i = 0; i < numColums; i++) {
			this.columnasInactivos[i] = tablaInactivos.getColumnName(i);
		}
		desplegableInactivos.setModel(new javax.swing.DefaultComboBoxModel(this.columnasInactivos));
	}

	/**
	 * Filtro en paciente inactivo necesario para manejar la informacion
	 */
	public void filtroInactivo() {
		int colum = 0;
		while (!(desplegableInactivos.getSelectedItem() == this.columnasInactivos[colum])) {
			colum++;
		}
		trsInactivo.setRowFilter(RowFilter.regexFilter(textInactivos.getText(), colum));
	}

	/**
	 * Filtro en paciente activo necesario para manejar la informacion
	 */
	public void filtroActivo() {
		int colum = 0;
		while (!(desplegableActivos.getSelectedItem() == this.columnasActivos[colum])) {
			colum++;
		}
		trsActivo.setRowFilter(RowFilter.regexFilter(textActivos.getText(), colum));
	}

	/**
	 * Devuelve true si un stirng esta vacio Funcion equals muy simple, pero
	 * ayuda a que el codigo sea mas sencillo y se vea mejor
	 *
	 * @param texto
	 * @return
	 */
	private boolean estaVacio(String texto) {
		boolean resul = false;
		if (texto.equals("")) {
			resul = true;
		}
		return resul;
	}

	/**
	 * Comprueba el campo DNI de campo Inactivo
	 *
	 * @return
	 */
	private boolean comprobarDNIInactivo(String dni) {
		boolean resul = false;
		try {
			if (!estaVacio(dni) && gestor.comprobarDNI(dni) && gestor.existePacienteBDBorrado(dni)) {
				resul = true;
			}
		} catch (SQLException ex) {
			Logger.getLogger(AñadirPaciente.class
					.getName()).log(Level.SEVERE, null, ex);
		}
		return resul;
	}

	/**
	 * Comprueba el campo DNI de campo Activo
	 *
	 * @return
	 */
	private boolean comprobarDNIActivo(String dni) {
		boolean resul = false;
		try {
			if (!estaVacio(dni) && gestor.comprobarDNI(dni) && gestor.existePacienteBD(dni)) {
				resul = true;
			}
		} catch (SQLException ex) {
			Logger.getLogger(AñadirPaciente.class
					.getName()).log(Level.SEVERE, null, ex);
		}
		return resul;
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton actualizarActivos;
    private javax.swing.JButton actualizarInactivos;
    private javax.swing.JButton buttonExportar;
    private javax.swing.JButton buttonImportar;
    private javax.swing.JComboBox desplegableActivos;
    private javax.swing.JComboBox desplegableInactivos;
    private javax.swing.JLabel fieldBorrar;
    private javax.swing.JLabel fieldReinsertar;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel labelActivos;
    private javax.swing.JLabel labelInactivos;
    private javax.swing.JPanel panelPacienteActivo;
    private javax.swing.JPanel panelPacienteInactivo;
    private javax.swing.JTable tablaActivos;
    private javax.swing.JTable tablaInactivos;
    private javax.swing.JTextField textActivos;
    private javax.swing.JTextField textInactivos;
    // End of variables declaration//GEN-END:variables
}
