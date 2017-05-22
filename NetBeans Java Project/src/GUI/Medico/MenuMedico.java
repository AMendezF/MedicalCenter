package GUI.Medico;

import clases.Conexion;
import clases.Medico;
import java.awt.BorderLayout;
import java.awt.Component;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Pablo
 */
public class MenuMedico extends javax.swing.JPanel {

    /**
     * Creates new form MenuMedico
     */
    private mostrarPacientesMedico mostrarPacientes;
    private mostrarCitasMedico mostrarCitas;
    private Medico medico;
    private EscribirFichaPaciente ecribirFicha;
    private ModificarFichaPaciente modificarFicha;

    public MenuMedico(Medico medico) {
        initComponents();
        this.medico = medico;
        initMostrarPacientes();
        initMostrarCitas();
        initEscribirFichaPaciente();
        initModificarFichaPaciente();
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
        buttonMostrarPacientes = new javax.swing.JButton();
        buttonModifFicha = new javax.swing.JButton();
        buttonMostrarCitas = new javax.swing.JButton();
        labelMenuMedico = new javax.swing.JLabel();
        buttonEscribirFicha = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        mostrarDatos = new javax.swing.JPanel();

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 0)));

        menuOpciones.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));

        buttonMostrarPacientes.setText("Mostrar pacientes");
        buttonMostrarPacientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonMostrarPacientesActionPerformed(evt);
            }
        });

        buttonModifFicha.setText("Modificar Ficha");
        buttonModifFicha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonModifFichaActionPerformed(evt);
            }
        });

        buttonMostrarCitas.setText("Mostrar citas");
        buttonMostrarCitas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonMostrarCitasActionPerformed(evt);
            }
        });

        labelMenuMedico.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        labelMenuMedico.setText("Menu medico");

        buttonEscribirFicha.setText("Escribir ficha");
        buttonEscribirFicha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonEscribirFichaActionPerformed(evt);
            }
        });

        jButton1.setText("mostrar fichas por especialdiad (historial)");

        javax.swing.GroupLayout menuOpcionesLayout = new javax.swing.GroupLayout(menuOpciones);
        menuOpciones.setLayout(menuOpcionesLayout);
        menuOpcionesLayout.setHorizontalGroup(
            menuOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuOpcionesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(menuOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(menuOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(buttonMostrarPacientes, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                        .addComponent(buttonMostrarCitas, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                        .addComponent(labelMenuMedico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(buttonModifFicha, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonEscribirFicha, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addContainerGap(121, Short.MAX_VALUE))
        );
        menuOpcionesLayout.setVerticalGroup(
            menuOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuOpcionesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelMenuMedico)
                .addGap(18, 18, 18)
                .addComponent(buttonMostrarPacientes, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(buttonMostrarCitas, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(buttonEscribirFicha, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(buttonModifFicha, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mostrarDatos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        mostrarDatos.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                mostrarDatosComponentAdded(evt);
            }
            public void componentRemoved(java.awt.event.ContainerEvent evt) {
                mostrarDatosComponentRemoved(evt);
            }
        });

        javax.swing.GroupLayout mostrarDatosLayout = new javax.swing.GroupLayout(mostrarDatos);
        mostrarDatos.setLayout(mostrarDatosLayout);
        mostrarDatosLayout.setHorizontalGroup(
            mostrarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 329, Short.MAX_VALUE)
        );
        mostrarDatosLayout.setVerticalGroup(
            mostrarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 405, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(menuOpciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(mostrarDatos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mostrarDatos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(menuOpciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void buttonMostrarPacientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonMostrarPacientesActionPerformed
        mostrarDatos.removeAll();
        mostrarDatos.add(mostrarPacientes, BorderLayout.CENTER);
        mostrarDatos.revalidate();
        mostrarDatos.repaint();
    }//GEN-LAST:event_buttonMostrarPacientesActionPerformed

    private void buttonMostrarCitasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonMostrarCitasActionPerformed
        mostrarDatos.removeAll();
        mostrarDatos.add(mostrarCitas, BorderLayout.CENTER);
        mostrarDatos.revalidate();
        mostrarDatos.repaint();
    }//GEN-LAST:event_buttonMostrarCitasActionPerformed

    private void buttonModifFichaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonModifFichaActionPerformed
        mostrarDatos.removeAll();
        mostrarDatos.add(modificarFicha, BorderLayout.CENTER);
        mostrarDatos.revalidate();
        mostrarDatos.repaint();
    }//GEN-LAST:event_buttonModifFichaActionPerformed

    private void buttonEscribirFichaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonEscribirFichaActionPerformed
        mostrarDatos.removeAll();
        mostrarDatos.add(ecribirFicha, BorderLayout.CENTER);
        mostrarDatos.revalidate();
        mostrarDatos.repaint();
    }//GEN-LAST:event_buttonEscribirFichaActionPerformed

    private void mostrarDatosComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_mostrarDatosComponentAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_mostrarDatosComponentAdded

    private void mostrarDatosComponentRemoved(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_mostrarDatosComponentRemoved
        // TODO add your handling code here:
    }//GEN-LAST:event_mostrarDatosComponentRemoved
    
    private void initMostrarPacientes() {
        this.mostrarPacientes = new mostrarPacientesMedico(medico);
        this.mostrarPacientes.setSize(800, 800);
        this.mostrarPacientes.setLocation(2, 5);
    }

    private void initMostrarCitas() {
        this.mostrarCitas = new mostrarCitasMedico(medico);
        this.mostrarCitas.setSize(800, 800);
        this.mostrarCitas.setLocation(2, 5);
    }
    
    private void initModificarFichaPaciente(){
        this.modificarFicha = new ModificarFichaPaciente(medico);
        this.modificarFicha.setSize(800, 800);
        this.modificarFicha.setLocation(2, 5);
    }
    
    private void initEscribirFichaPaciente(){
        this.ecribirFicha = new EscribirFichaPaciente(medico);
        this.ecribirFicha.setSize(800, 800);
        this.ecribirFicha.setLocation(2, 5);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonEscribirFicha;
    private javax.swing.JButton buttonModifFicha;
    private javax.swing.JButton buttonMostrarCitas;
    private javax.swing.JButton buttonMostrarPacientes;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel labelMenuMedico;
    private javax.swing.JPanel menuOpciones;
    private javax.swing.JPanel mostrarDatos;
    // End of variables declaration//GEN-END:variables
}
