package GUI.Medico;

import GUI.Gestor.TableAdaptor;
import clases.Historial;
import clases.Medico;
import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class ModificarFichaPaciente extends javax.swing.JPanel {

    /**
     * Creates new form mostrarPacientesMedico
     */
    private final Medico medico;
    private TableRowSorter trsFiltro;
    private DefaultTableModel tabla;
    private int codHistoria = 0;
    private String[] columnas;
    private int controlTablas = 1;
    private ModificarFichaSeleccionada modificador;
    private String nombrePaciente;

    public ModificarFichaPaciente(Medico medico) {
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
        BotonMostrar = new javax.swing.JButton();
        BotonModificarHisto = new javax.swing.JButton();
        mostrarDatos = new javax.swing.JPanel();
        DNI1 = new javax.swing.JLabel();
        campoNombre = new javax.swing.JLabel();

        addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                formComponentAdded(evt);
            }
        });

        desplegableColumnas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                desplegableColumnasActionPerformed(evt);
            }
        });

        textFieldBuscar.setText("Buscar por...");
        textFieldBuscar.setToolTipText("");
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
        tablaInfo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tablaInfoMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tablaInfo);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Modificar Ficha de Paciente");

        BotonMostrar.setText("Mostrar Historial");
        BotonMostrar.setActionCommand("MostrarPacientesMedico");
        BotonMostrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonMostrarActionPerformed(evt);
            }
        });

        BotonModificarHisto.setText("Ver Fichas");
        BotonModificarHisto.setActionCommand("MostrarPacientesMedico");
        BotonModificarHisto.setEnabled(false);
        BotonModificarHisto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonModificarHistoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mostrarDatosLayout = new javax.swing.GroupLayout(mostrarDatos);
        mostrarDatos.setLayout(mostrarDatosLayout);
        mostrarDatosLayout.setHorizontalGroup(
            mostrarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        mostrarDatosLayout.setVerticalGroup(
            mostrarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        DNI1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        DNI1.setText("Paciente");

        campoNombre.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        campoNombre.setForeground(new java.awt.Color(0, 51, 102));
        campoNombre.setText("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(BotonMostrar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(BotonModificarHisto, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(mostrarDatos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(90, 90, 90)
                                .addComponent(textFieldBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(desplegableColumnas, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(DNI1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(campoNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(desplegableColumnas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textFieldBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BotonMostrar)
                            .addComponent(BotonModificarHisto))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(DNI1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(campoNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(mostrarDatos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void desplegableColumnasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_desplegableColumnasActionPerformed

    }//GEN-LAST:event_desplegableColumnasActionPerformed

    private void BotonMostrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonMostrarActionPerformed
        controlTablas = 1;
        BotonModificarHisto.setEnabled(false);
        mostrarDatos();
    }//GEN-LAST:event_BotonMostrarActionPerformed

    private void mostrarDatos() {
        try {
            ResultSet rs = medico.mostrarHistoriales();
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
            Logger.getLogger(ModificarFichaPaciente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void mostrarDatosFichas() {
        try {
            ResultSet rs = medico.mostrarFichas(this.codHistoria);
            TableAdaptor aux = new TableAdaptor(rs);
            tablaInfo.setModel(aux.getValue());
            int numColums = aux.getValue().getColumnCount();
            this.columnas = new String[numColums];
            for (int i = 0; i < numColums; i++) {
                this.columnas[i] = aux.getValue().getColumnName(i);
            }
            desplegableColumnas.setModel(new javax.swing.DefaultComboBoxModel(this.columnas));
        } catch (SQLException ex) {
            Logger.getLogger(ModificarFichaPaciente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void formComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_formComponentAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_formComponentAdded

    private void BotonModificarHistoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonModificarHistoActionPerformed
        controlTablas = 2;
        mostrarDatosFichas();
    }//GEN-LAST:event_BotonModificarHistoActionPerformed

    private void tablaInfoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaInfoMousePressed
        int row = tablaInfo.rowAtPoint(evt.getPoint());
        int col = tablaInfo.columnAtPoint(evt.getPoint());
        if (row >= 0 && col >= 0) {
            if (controlTablas == 1) {
                this.nombrePaciente = (String) tablaInfo.getValueAt(row, 3) + ", "
                        + (String) tablaInfo.getValueAt(row, 2);
                campoNombre.setText(nombrePaciente);
                codHistoria = (int) tablaInfo.getValueAt(row, 0);
                BotonModificarHisto.setEnabled(true);
            } else if (controlTablas == 2) {
                String codFicha = (String) tablaInfo.getValueAt(row, 1);
                String hora = tablaInfo.getValueAt(row, 4).toString();
                String dia = (String) tablaInfo.getValueAt(row, 3).toString();
                String comentario = (String) tablaInfo.getValueAt(row, 2);

                createModificarFicha(codFicha, hora, dia, comentario);

            }
        }
    }//GEN-LAST:event_tablaInfoMousePressed

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
        while (!(desplegableColumnas.getSelectedItem() == this.columnas[colum])) {
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

    private void createModificarFicha(String codFicha, String hora, String dia, String comentario) {
        this.modificador = new ModificarFichaSeleccionada(this.medico, this.nombrePaciente, codFicha, hora, dia, comentario);
        this.modificador.setSize(800, 800);
        this.modificador.setLocation(2, 5);

        mostrarDatos.removeAll();
        mostrarDatos.add(this.modificador, BorderLayout.CENTER);
        mostrarDatos.revalidate();
        mostrarDatos.repaint();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BotonModificarHisto;
    private javax.swing.JButton BotonMostrar;
    private javax.swing.JLabel DNI1;
    private javax.swing.JLabel campoNombre;
    private javax.swing.JComboBox desplegableColumnas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel mostrarDatos;
    private javax.swing.JTable tablaInfo;
    private javax.swing.JTextField textFieldBuscar;
    // End of variables declaration//GEN-END:variables

}
