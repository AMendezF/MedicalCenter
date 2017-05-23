package GUI.Medico;

import GUI.Gestor.TableAdaptor;
import clases.Medico;
import clases.PdfConversor;
import com.itextpdf.text.DocumentException;
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Pablo
 */
public class mostrarPacientesMedico extends javax.swing.JPanel {

    /**
     * Creates new form mostrarPacientesMedico
     */
    private final Medico medico;
    private TableRowSorter trsFiltro;
    private DefaultTableModel tabla;
    private String[] columnas;

    public mostrarPacientesMedico(Medico medico) {
        initComponents();
        this.medico = medico;
        mostrarDatos();
        
        
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
        mostrar = new javax.swing.JButton();
        buttonPDF = new javax.swing.JButton();

        desplegableColumnas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                desplegableColumnasActionPerformed(evt);
            }
        });

        textFieldBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textFieldBuscarKeyTyped(evt);
            }
        });

        tablaInfo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {}
            },
            new String [] {

            }
        ));
        tablaInfo.setColumnSelectionAllowed(true);
        jScrollPane2.setViewportView(tablaInfo);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Mostrar pacientes");

        mostrar.setText("Mostrar");
        mostrar.setActionCommand("MostrarPacientesMedico");
        mostrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mostrarActionPerformed(evt);
            }
        });

        buttonPDF.setText("PDF");
        buttonPDF.setActionCommand("MostrarPacientesMedico");
        buttonPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPDFActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(desplegableColumnas, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(textFieldBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonPDF))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(175, 175, 175)
                        .addComponent(mostrar)))
                .addContainerGap(291, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(desplegableColumnas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textFieldBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonPDF))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(mostrar)
                .addContainerGap(43, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void desplegableColumnasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_desplegableColumnasActionPerformed

    }//GEN-LAST:event_desplegableColumnasActionPerformed

    private void mostrarDatos(){
        try {
            ResultSet rs = medico.mostrarPacientesAsociados();
            TableAdaptor aux = new TableAdaptor(rs);
            setTabla(aux.getValue());
            DefaultTableModel tabla = getTabla();
            tablaInfo.setModel(tabla);
            int numColums = tabla.getColumnCount();
            this.columnas = new String[numColums];
            for (int i = 0 ; i < numColums ; i++){
                this.columnas[i] = tabla.getColumnName(i);
            }
            desplegableColumnas.setModel(new javax.swing.DefaultComboBoxModel(this.columnas));
        } catch (SQLException ex) {
            Logger.getLogger(mostrarPacientesMedico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void mostrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mostrarActionPerformed
        mostrarDatos();
    }//GEN-LAST:event_mostrarActionPerformed

    private void textFieldBuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textFieldBuscarKeyTyped
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
    }//GEN-LAST:event_textFieldBuscarKeyTyped

    private void buttonPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonPDFActionPerformed
        try {
            PdfConversor conversor = new PdfConversor(tablaInfo, "Paciente de colegiado " + medico.getN_colegiado() +"  " + medico.getDia());
            conversor.getPdf();
            JOptionPane.showMessageDialog(this, "¡Se ha generado tu hoja PDF!",
                "RESULTADO", JOptionPane.INFORMATION_MESSAGE);
        } catch (DocumentException ex) {
            Logger.getLogger(mostrarCitasMedico.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Se ha producido un error al generar un documento: " + ex,
                "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_buttonPDFActionPerformed

    public void filtro() {
        int colum = 0;
        while (!(desplegableColumnas.getSelectedItem() == this.columnas[colum])){
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonPDF;
    private javax.swing.JComboBox desplegableColumnas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton mostrar;
    private javax.swing.JTable tablaInfo;
    private javax.swing.JTextField textFieldBuscar;
    // End of variables declaration//GEN-END:variables
}
