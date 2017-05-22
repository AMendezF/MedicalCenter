package GUI.Gestor.Paciente;

import GUI.Gestor.Paciente.mostrarCitas;
import clases.Gestor;
import clases.Paciente;
import java.awt.BorderLayout;
import java.sql.SQLException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Juan
 */
public class MenuDePaciente extends javax.swing.JPanel {

	/**
	 * Creates new form MenuDePaciente
	 */
	private Paciente paciente;
	private Gestor gestor;
	private mostrarCitas mostrarCitas;
	private PedirCita pedirCita;
	private modificarPaciente modificarPaciente;

	public MenuDePaciente(Gestor gestor, Paciente paciente) {
		initComponents();
		this.gestor = gestor;
		this.paciente = paciente;
		this.mostrarCitas = new mostrarCitas(gestor, paciente);
		this.pedirCita = new PedirCita(gestor, paciente);
		this.modificarPaciente = new modificarPaciente(gestor,paciente);
		this.labelMenuDelPaciente.setText(paciente.getNombre() +":   " + paciente.getDNI());
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
        buttonMostrarCitas = new javax.swing.JButton();
        labelMenuDelPaciente = new javax.swing.JLabel();
        buttonPedirCita = new javax.swing.JButton();
        buttonModificarPaciente = new javax.swing.JButton();
        mostrarDatos = new javax.swing.JPanel();

        buttonMostrarCitas.setText("Mostrar citas");
        buttonMostrarCitas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonMostrarCitasActionPerformed(evt);
            }
        });

        labelMenuDelPaciente.setFont(new java.awt.Font("Cambria Math", 1, 18)); // NOI18N
        labelMenuDelPaciente.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelMenuDelPaciente.setText("Menu del paciente");
        labelMenuDelPaciente.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        buttonPedirCita.setText("Pedir cita");
        buttonPedirCita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPedirCitaActionPerformed(evt);
            }
        });

        buttonModificarPaciente.setText("Modificar paciente");
        buttonModificarPaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonModificarPacienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout menuOpcionesLayout = new javax.swing.GroupLayout(menuOpciones);
        menuOpciones.setLayout(menuOpcionesLayout);
        menuOpcionesLayout.setHorizontalGroup(
            menuOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuOpcionesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(menuOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelMenuDelPaciente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(menuOpcionesLayout.createSequentialGroup()
                        .addGroup(menuOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(buttonMostrarCitas, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonPedirCita, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonModificarPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 27, Short.MAX_VALUE)))
                .addContainerGap())
        );
        menuOpcionesLayout.setVerticalGroup(
            menuOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuOpcionesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelMenuDelPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(buttonMostrarCitas, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(buttonPedirCita, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(buttonModificarPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(214, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout mostrarDatosLayout = new javax.swing.GroupLayout(mostrarDatos);
        mostrarDatos.setLayout(mostrarDatosLayout);
        mostrarDatosLayout.setHorizontalGroup(
            mostrarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 522, Short.MAX_VALUE)
        );
        mostrarDatosLayout.setVerticalGroup(
            mostrarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(mostrarDatos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(menuOpciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

	/**
	 * Crea y muestra el panel mostrarCitas
	 *
	 * @param evt
	 */
    private void buttonMostrarCitasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonMostrarCitasActionPerformed
		mostrarCitas.setSize(mostrarDatos.getWidth(), mostrarDatos.getHeight());
		mostrarCitas.setLocation(0, 0);

		mostrarDatos.removeAll();
		mostrarDatos.add(mostrarCitas, BorderLayout.CENTER);
		mostrarDatos.revalidate();
		mostrarDatos.repaint();
    }//GEN-LAST:event_buttonMostrarCitasActionPerformed

    private void buttonPedirCitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonPedirCitaActionPerformed
        pedirCita.setSize(mostrarDatos.getWidth(), mostrarDatos.getHeight());
		pedirCita.setLocation(0, 0);

		mostrarDatos.removeAll();
		mostrarDatos.add(pedirCita, BorderLayout.CENTER);
		mostrarDatos.revalidate();
		mostrarDatos.repaint();
    }//GEN-LAST:event_buttonPedirCitaActionPerformed

    private void buttonModificarPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonModificarPacienteActionPerformed
        modificarPaciente.setSize(mostrarDatos.getWidth(), mostrarDatos.getHeight());
		modificarPaciente.setLocation(0, 0);

		mostrarDatos.removeAll();
		mostrarDatos.add(modificarPaciente, BorderLayout.CENTER);
		mostrarDatos.revalidate();
		mostrarDatos.repaint();
    }//GEN-LAST:event_buttonModificarPacienteActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonModificarPaciente;
    private javax.swing.JButton buttonMostrarCitas;
    private javax.swing.JButton buttonPedirCita;
    private javax.swing.JLabel labelMenuDelPaciente;
    private javax.swing.JPanel menuOpciones;
    private javax.swing.JPanel mostrarDatos;
    // End of variables declaration//GEN-END:variables
}
