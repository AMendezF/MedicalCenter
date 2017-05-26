package GUI.Medico;

import clases.Selector;
import GUI.Gestor.TableAdaptor;
import clases.Medico;
import clases.PdfConversor;
import com.itextpdf.text.DocumentException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Pablo
 */
public class mostrarCitasMedico extends javax.swing.JPanel {

	/**
	 * Creates new form mostrarPacientesMedico
	 */
	private final Medico medico;
	private TableRowSorter trsFiltro;
	private DefaultTableModel tabla;
	private String[] columnas;

	public mostrarCitasMedico(Medico medico) {
		initComponents();
		this.medico = medico;
		this.setBackground(new java.awt.Color(150, 190, 230));

		jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(ArrayDias()));
		mostrarDatos((String) jComboBox1.getSelectedItem());
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        desplegableColumnas = new javax.swing.JComboBox();
        textFieldBuscar = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaInfo = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        BotonMostrar = new javax.swing.JButton();
        DescargarPdf = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();

        addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                formComponentAdded(evt);
            }
        });

        textFieldBuscar.setText("Buscar por...");
        textFieldBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                textFieldBuscarMouseClicked(evt);
            }
        });

        tablaInfo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(tablaInfo);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Citas Pendientes");

        BotonMostrar.setText("Actualizar");
        BotonMostrar.setActionCommand("MostrarPacientesMedico");
        BotonMostrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonMostrarActionPerformed(evt);
            }
        });

        DescargarPdf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pdf_icon.png"))); // NOI18N
        DescargarPdf.setText("Guardar en PDF");
        DescargarPdf.setActionCommand("MostrarPacientesMedico");
        DescargarPdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DescargarPdfActionPerformed(evt);
            }
        });

        jLabel2.setText("Dia:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BotonMostrar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(textFieldBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(desplegableColumnas, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(DescargarPdf)))
                        .addGap(0, 167, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BotonMostrar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textFieldBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(desplegableColumnas, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DescargarPdf))
                .addContainerGap(71, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void BotonMostrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonMostrarActionPerformed
		mostrarDatos((String) jComboBox1.getSelectedItem());
    }//GEN-LAST:event_BotonMostrarActionPerformed

	private void mostrarDatos(String dia) {
		try {
			ResultSet rs = medico.mostrarCitasDiaMedico(dia);
			TableAdaptor aux = new TableAdaptor(rs);
			setTabla(aux.getValue());
			DefaultTableModel tabla = getTabla();
			tablaInfo.setModel(tabla);
			int numColums = tabla.getColumnCount();
			this.columnas = new String[numColums];
			for (int i = 0; i < numColums; i++) {
				this.columnas[i] = tabla.getColumnName(i);
			}
			desplegableColumnas.setModel(new javax.swing.DefaultComboBoxModel(this.columnas));
		} catch (SQLException ex) {
			Logger.getLogger(mostrarCitasMedico.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

    private void formComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_formComponentAdded
		// TODO add your handling code here:
    }//GEN-LAST:event_formComponentAdded

    private void DescargarPdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DescargarPdfActionPerformed
		seleccionHubicacion();
    }//GEN-LAST:event_DescargarPdfActionPerformed

    private void textFieldBuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_textFieldBuscarMouseClicked
		textFieldBuscar.setText(null);
    }//GEN-LAST:event_textFieldBuscarMouseClicked

	private void textFieldBuscarKeyTyped(java.awt.event.KeyEvent evt) {
		// TODO add your handling code here:
		textFieldBuscar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(final KeyEvent e) {
				String cadena = (textFieldBuscar.getText());
				textFieldBuscar.setText(cadena);
				repaint();
				filtro();
			}
		});
		trsFiltro = new TableRowSorter(tablaInfo.getModel());
		tablaInfo.setRowSorter(trsFiltro);
	}

	public void filtro() {
		int colum = 0;
		while (!(desplegableColumnas.getSelectedItem().equals(this.columnas[colum]))) {
			colum++;
		}
		trsFiltro.setRowFilter(RowFilter.regexFilter(textFieldBuscar.getText(), colum));
	}

	public DefaultTableModel getTabla() {
		return this.tabla;
	}

	public void setTabla(DefaultTableModel tabla) {
		this.tabla = tabla;
	}

	private void seleccionHubicacion() {
		Selector explorador = new Selector();
		int seleccion = explorador.opcionMarcada();

		switch (seleccion) {
			case JFileChooser.APPROVE_OPTION:
				guardarPdf(explorador.retornarDirectorioElegido());
				break;

			case JFileChooser.CANCEL_OPTION:
				break;

			case JFileChooser.ERROR_OPTION:
				break;
		}
	}

	private void guardarPdf(String directorio) {
		try {
			PdfConversor conversor = new PdfConversor(tablaInfo, "Citas del dia", (String) jComboBox1.getSelectedItem(), medico.getN_colegiado(), directorio);
			conversor.getPdfTablas();
			JOptionPane.showMessageDialog(this, "¡Se ha generado tu hoja PDF!",
					"RESULTADO", JOptionPane.INFORMATION_MESSAGE);
		} catch (DocumentException ex) {
			Logger.getLogger(mostrarCitasMedico.class.getName()).log(Level.SEVERE, null, ex);
			JOptionPane.showMessageDialog(this, "Se ha producido un error al generar un documento: " + ex,
					"ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BotonMostrar;
    private javax.swing.JButton DescargarPdf;
    private javax.swing.JComboBox desplegableColumnas;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tablaInfo;
    private javax.swing.JTextField textFieldBuscar;
    // End of variables declaration//GEN-END:variables

	private Object[] ArrayDias() {
		String[] dias = new String[3];
		Calendar fecha = Calendar.getInstance();
		dias[0] = fecha.get(Calendar.YEAR) + "-"
				+ (fecha.get(Calendar.MONTH) + 1) + "-"
				+ (fecha.get(Calendar.DAY_OF_MONTH));

		dias[1] = fecha.get(Calendar.YEAR) + "-"
				+ (fecha.get(Calendar.MONTH) + 1) + "-"
				+ (fecha.get(Calendar.DAY_OF_MONTH) + 1);

		dias[2] = fecha.get(Calendar.YEAR) + "-"
				+ (fecha.get(Calendar.MONTH) + 1) + "-"
				+ (fecha.get(Calendar.DAY_OF_MONTH) + 2);

		return dias;
	}

}
