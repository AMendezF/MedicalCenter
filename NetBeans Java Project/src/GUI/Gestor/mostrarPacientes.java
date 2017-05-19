package GUI.Gestor;

import clases.Gestor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author bm0275
 */
public class mostrarPacientes extends javax.swing.JPanel {

	/**
	 * Creates new form mostrarPacientes
	 */
	private TableRowSorter trsFiltro;
	private Gestor gestor;

	public mostrarPacientes(Gestor gestor) {
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaInfo = new javax.swing.JTable();
        mostrar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        desplegableColumnas = new javax.swing.JComboBox();
        textFieldBuscar = new javax.swing.JTextField();

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
        jScrollPane1.setViewportView(tablaInfo);
        tablaInfo.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        mostrar.setText("Mostrar datos");
        mostrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mostrarActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Mostrar pacientes");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        desplegableColumnas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "DNI", "Nombre", "Apellidos", "Seguro" }));

        textFieldBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textFieldBuscarKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(desplegableColumnas, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(mostrar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 208, Short.MAX_VALUE)
                        .addComponent(textFieldBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(desplegableColumnas, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mostrar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textFieldBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

	/**
	 * Sirve para filtrar en el jTable
	 *
	 * @param evt
	 */
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
	 * La tabla se puede ordenar
	 * @return 
	 */
/*
	private JTable getJTable() {
		if (table == null) {
			table = new JTable();
			// Creamos el modelo, la parte que contenrá los datos de la tabla
			DefaultTableModel model = new DefaultTableModel();
			// Creamos un ordenador de filas para el modelo
			TableRowSorter sorter = new TableRowSorter(
					model);
			// Añadimos al modelo los datos que queremos que contenga la tabla
			model.setDataVector(data, columnNames);
			// Le decimos a la tabla que use el modelo de datos que hemos creado
			table.setModel(model);
		// Le decimos a la tabla que use la ordenación de filas que hemos
			// creado
			table.setRowSorter(sorter);
		}
		return table;
	}
	

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
	 * Muestra los datos en el jTable
	 *
	 * @param evt
	 */
    private void mostrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mostrarActionPerformed
		try {
			ResultSet rs = gestor.mostrarPacientes();
			//TableRowSorter tablaOrdenada = new TableRowSorter (buildTableModel(rs));
			tablaInfo.setModel(buildTableModel(rs));
			//tablaInfo.setRowSorter(tablaOrdenada);

		} catch (SQLException ex) {
			Logger.getLogger(mostrarPacientes.class.getName()).log(Level.SEVERE, null, ex);
		}
    }//GEN-LAST:event_mostrarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox desplegableColumnas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton mostrar;
    private javax.swing.JTable tablaInfo;
    private javax.swing.JTextField textFieldBuscar;
    // End of variables declaration//GEN-END:variables
}
