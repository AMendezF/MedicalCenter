/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Gestor.Paciente;

import clases.Gestor;
import clases.Paciente;
import java.awt.Color;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author bm0275
 */
public class modificarPaciente extends javax.swing.JPanel {

	private Gestor gestor;
	private Paciente paciente;

	public modificarPaciente(Gestor gestor, Paciente paciente) {
		initComponents();
		this.gestor = gestor;
		this.paciente = paciente;
		this.setBackground(new java.awt.Color(150, 190, 230));
		mostrarDatos.setBackground(this.getBackground());
		fieldNombre.setDocument(new JTextFieldLimit(50));
		fieldApellidos.setDocument(new JTextFieldLimit(50));
		fieldSeguro.setDocument(new JTextFieldLimit(50));
		fieldTelefono.setDocument(new JTextFieldLimit(15));
		fieldDireccion.setDocument(new JTextFieldLimit(100));
		mostrarValores();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mostrarDatos = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        labelDNI = new javax.swing.JLabel();
        fieldNombre = new javax.swing.JTextField();
        fieldSeguro = new javax.swing.JTextField();
        fieldApellidos = new javax.swing.JTextField();
        buttonActualizarDatos = new javax.swing.JButton();
        nombreOK = new javax.swing.JLabel();
        apellidosOK = new javax.swing.JLabel();
        seguroOK = new javax.swing.JLabel();
        fieldTelefono = new javax.swing.JTextField();
        fieldDireccion = new javax.swing.JTextField();
        telefonoOK = new javax.swing.JLabel();
        direccionOK = new javax.swing.JLabel();
        campoDNI = new javax.swing.JLabel();
        campoNombre = new javax.swing.JLabel();
        campoApellidos = new javax.swing.JLabel();
        campoSeguro = new javax.swing.JLabel();
        campoTelefono = new javax.swing.JLabel();
        campoDireccion = new javax.swing.JLabel();

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Modificar Paciente");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        labelDNI.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        labelDNI.setText("DNI");

        fieldNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                fieldNombreKeyReleased(evt);
            }
        });

        fieldSeguro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                fieldSeguroKeyReleased(evt);
            }
        });

        fieldApellidos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                fieldApellidosKeyReleased(evt);
            }
        });

        buttonActualizarDatos.setText("Actualizar datos");
        buttonActualizarDatos.setEnabled(false);
        buttonActualizarDatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonActualizarDatosActionPerformed(evt);
            }
        });

        nombreOK.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        nombreOK.setText("Modifica el nombre");

        apellidosOK.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        apellidosOK.setText("Modifica el apellido");

        seguroOK.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        seguroOK.setText("Modifica el seguro");

        fieldTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                fieldTelefonoKeyReleased(evt);
            }
        });

        fieldDireccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                fieldDireccionKeyReleased(evt);
            }
        });

        telefonoOK.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        telefonoOK.setText("Modifica el teléfono");

        direccionOK.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        direccionOK.setText("Modifica la dirección");

        campoDNI.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        campoDNI.setText("DNI:");

        campoNombre.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        campoNombre.setText("Nombre:");

        campoApellidos.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        campoApellidos.setText("Apellidos:");

        campoSeguro.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        campoSeguro.setText("Seguro:");

        campoTelefono.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        campoTelefono.setText("Teléfono:");

        campoDireccion.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        campoDireccion.setText("Dirección:");

        javax.swing.GroupLayout mostrarDatosLayout = new javax.swing.GroupLayout(mostrarDatos);
        mostrarDatos.setLayout(mostrarDatosLayout);
        mostrarDatosLayout.setHorizontalGroup(
            mostrarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mostrarDatosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mostrarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(mostrarDatosLayout.createSequentialGroup()
                        .addGroup(mostrarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mostrarDatosLayout.createSequentialGroup()
                                .addGroup(mostrarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(campoNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(campoApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(campoTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(campoDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(campoSeguro, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(mostrarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(mostrarDatosLayout.createSequentialGroup()
                                        .addGap(272, 272, 272)
                                        .addGroup(mostrarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(mostrarDatosLayout.createSequentialGroup()
                                                .addComponent(fieldTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(telefonoOK, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(mostrarDatosLayout.createSequentialGroup()
                                                .addComponent(fieldSeguro, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(seguroOK, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(mostrarDatosLayout.createSequentialGroup()
                                                .addComponent(fieldDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(direccionOK, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(buttonActualizarDatos, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(mostrarDatosLayout.createSequentialGroup()
                                        .addGap(272, 272, 272)
                                        .addGroup(mostrarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(fieldApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(fieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(mostrarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(nombreOK, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(apellidosOK, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(mostrarDatosLayout.createSequentialGroup()
                                .addComponent(campoDNI, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(labelDNI, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 46, Short.MAX_VALUE)))
                .addContainerGap())
        );
        mostrarDatosLayout.setVerticalGroup(
            mostrarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mostrarDatosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(mostrarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoDNI, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelDNI, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addGroup(mostrarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nombreOK, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(mostrarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fieldApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(apellidosOK, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(mostrarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoSeguro, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fieldSeguro, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(seguroOK, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(mostrarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fieldTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(telefonoOK, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(mostrarDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fieldDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(direccionOK, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(buttonActualizarDatos, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(75, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mostrarDatos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mostrarDatos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

	/**
	 * Carga los datos del paciente en la pantalla
	 */
	private void mostrarValores() {
		labelDNI.setText(paciente.getDNI());
		/*
		 labelNombre.setText(paciente.getNombre());
		 labelApellidos.setText(paciente.getApellidos());
		 labelSeguro.setText(paciente.getSeguro());
		 labelTelefono.setText(paciente.getTelefono());
		 labelDireccion.setText(paciente.getDireccion());

		 */

		fieldNombre.setText(paciente.getNombre());
		fieldApellidos.setText(paciente.getApellidos());
		fieldSeguro.setText(paciente.getSeguro());
		fieldTelefono.setText(paciente.getTelefono());
		fieldDireccion.setText(paciente.getDireccion());
	}

	/**
	 * Comprueba los datos y ejecuta modificar paciente
	 *
	 * @param evt
	 */
    private void buttonActualizarDatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonActualizarDatosActionPerformed
		List<String> datos = new ArrayList<String>();

		datos.add(fieldNombre.getText().trim());
		datos.add(fieldApellidos.getText().trim());
		datos.add(fieldSeguro.getText().trim());
		datos.add(fieldTelefono.getText().trim());
		datos.add(fieldDireccion.getText().trim());

		ArrayList<String[]> modificarPaciente = new ArrayList<String[]>();

		if (comprobarNombre(datos.get(0))) {
			if (comprobarApellido(datos.get(1))) {
				if (comprobarTelefono(datos.get(3))) {
					if (comprobarDireccion(datos.get(4))) {

						if (!datos.get(0).equals(paciente.getNombre())) {
							modificarPaciente.add(new String[]{"nombre", datos.get(0)});
						}
						if (!datos.get(1).equals(paciente.getApellidos())) {
							modificarPaciente.add(new String[]{"apellidos", datos.get(1)});
						}
						if (!datos.get(2).equals(paciente.getSeguro())) {
							if (estaVacio(datos.get(2))) {
								datos.set(2, "NULL");
							}
							modificarPaciente.add(new String[]{"CompSegur", datos.get(2)});
						}
						if (!datos.get(3).equals(paciente.getTelefono())) {
							modificarPaciente.add(new String[]{"telefono", datos.get(3)});
						}
						if (!datos.get(4).equals(paciente.getDireccion())) {
							modificarPaciente.add(new String[]{"direccion", datos.get(4)});
						}

						String[][] datosModificar = modificarPaciente.toArray(new String[modificarPaciente.size()][2]);

						if (gestor.updatePaciente(paciente.getDNI(), datosModificar)) {
							JOptionPane.showMessageDialog(this, "Se modificado el paciente con DNI " + paciente.getDNI());
							try {
								paciente = gestor.getPaciente(paciente.getDNI());
							} catch (SQLException ex) {
								Logger.getLogger(modificarPaciente.class.getName()).log(Level.SEVERE, null, ex);
							}
							buttonActualizarDatos.setEnabled(false);
							mostrarValores();
						} else {
							JOptionPane.showMessageDialog(this, "Error al modificar paciente");
						}
					} else {
						JOptionPane.showMessageDialog(this, "Error con el dirección", "Cuidado!", JOptionPane.WARNING_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(this, "Error con el teléfono", "Cuidado!", JOptionPane.WARNING_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(this, "Error con el apellido", "Cuidado!", JOptionPane.WARNING_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(this, "Error con el nombre", "Cuidado!", JOptionPane.WARNING_MESSAGE);
		}
    }//GEN-LAST:event_buttonActualizarDatosActionPerformed

	/**
	 * Comprueba el valor del nombre y muestra por las labeles el resultado
	 *
	 * @param evt
	 */
    private void fieldNombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fieldNombreKeyReleased
		if (comprobarNombre(fieldNombre.getText())) {
			actualizarBoton();
			if (paciente.getNombre().equals(fieldNombre.getText())) {
				nombreOK.setForeground(Color.black);
				nombreOK.setText("No se modificará este campo");
			} else {
				nombreOK.setForeground(Color.green);
				nombreOK.setText("Se actualizara este campo");
			}
		}
    }//GEN-LAST:event_fieldNombreKeyReleased

	/**
	 * Comprueba y modifica los valores de la label
	 *
	 * @param evt
	 */
    private void fieldApellidosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fieldApellidosKeyReleased
		if (comprobarApellido(fieldApellidos.getText())) {
			actualizarBoton();
			if (paciente.getApellidos().equals(fieldApellidos.getText())) {
				apellidosOK.setForeground(Color.black);
				apellidosOK.setText("No se modificará este campo");
			} else {
				apellidosOK.setForeground(Color.green);
				apellidosOK.setText("Se actualizara este campo");
			}
		}
    }//GEN-LAST:event_fieldApellidosKeyReleased

	/**
	 * Modifica el valor del titulo seguro
	 *
	 * @param evt
	 */
    private void fieldSeguroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fieldSeguroKeyReleased
		actualizarBoton();
		if (paciente.getSeguro().equals(fieldSeguro.getText())) {
			seguroOK.setForeground(Color.black);
			seguroOK.setText("No se modificará este campo");
		} else {
			seguroOK.setForeground(Color.green);
			seguroOK.setText("Se actualizara este campo");
		}
    }//GEN-LAST:event_fieldSeguroKeyReleased

	/**
	 * Modifica el valor del titulo telefono
	 *
	 * @param evt
	 */
    private void fieldTelefonoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fieldTelefonoKeyReleased
		if (comprobarTelefono(fieldTelefono.getText())) {
			actualizarBoton();
			if (paciente.getTelefono().equals(fieldTelefono.getText())) {
				telefonoOK.setForeground(Color.black);
				telefonoOK.setText("No se modificará este campo");
			} else {
				telefonoOK.setForeground(Color.green);
				telefonoOK.setText("Se actualizara este campo");
			}
		}
    }//GEN-LAST:event_fieldTelefonoKeyReleased

	/**
	 * Modifica el valor del titulo direccion
	 *
	 * @param evt
	 */
    private void fieldDireccionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fieldDireccionKeyReleased
		if (comprobarDireccion(fieldDireccion.getText())) {
			actualizarBoton();
			if (paciente.getDireccion().equals(fieldDireccion.getText())) {
				direccionOK.setForeground(Color.black);
				direccionOK.setText("No se modificará este campo");
			} else {
				direccionOK.setForeground(Color.green);
				direccionOK.setText("Se actualizara este campo");
			}
		}
    }//GEN-LAST:event_fieldDireccionKeyReleased

	/**
	 * Habitila o desabilita el boton en funcion de los valores cambiados
	 */
	private boolean actualizarBoton() {
		boolean resul = false;
		if (paciente.getNombre().equals(fieldNombre.getText())
				&& paciente.getApellidos().equals(fieldApellidos.getText())
				&& paciente.getSeguro().equals(fieldSeguro.getText())
				&& paciente.getTelefono().equals(fieldTelefono.getText())
				&& paciente.getDireccion().equals(fieldDireccion.getText())) {
			resul = false;
			buttonActualizarDatos.setEnabled(false);
		} else {
			resul = true;
			buttonActualizarDatos.setEnabled(true);
		}
		return resul;
	}

	/**
	 * Devuelve true si un string esta vacio Funcion equals muy simple, pero
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
	 * Comprueba si el valor apellido es correcto
	 *
	 * @param campo
	 * @return
	 */
	private boolean comprobarApellido(String apellido) {
		boolean resul = false;
		if (estaVacio(apellido)) {
			apellidosOK.setForeground(Color.red);
			apellidosOK.setText("Vacio!!");
		} else if (!gestor.esTexto(apellido)) {
			apellidosOK.setForeground(Color.red);
			apellidosOK.setText("Incorrecto!!");
		} else {
			resul = true;
		}
		return resul;
	}

	/**
	 * Comprueba si el valor apellido es correcto
	 *
	 * @param campo
	 * @return
	 */
	private boolean comprobarNombre(String nombre) {
		boolean resul = false;
		if (estaVacio(nombre)) {
			nombreOK.setForeground(Color.red);
			nombreOK.setText("Vacio!!");
		} else if (!gestor.esTexto(nombre)) {
			nombreOK.setForeground(Color.red);
			nombreOK.setText("Incorrecto!!");
		} else {
			resul = true;
		}
		return resul;
	}

	/**
	 * Comprueba el telefono
	 *
	 * @return
	 */
	private boolean comprobarTelefono(String telefono) {
		boolean resul = false;
		if (estaVacio(telefono)) {
			telefonoOK.setForeground(Color.red);
			telefonoOK.setText("Vacio!!");
		} else if (!gestor.esNumerico(telefono)) {
			telefonoOK.setForeground(Color.red);
			telefonoOK.setText("Incorrecto!!");
		} else {
			resul = true;
		}
		return resul;
	}

	/**
	 * Comprueba la direccion
	 *
	 * @return
	 */
	private boolean comprobarDireccion(String direccion) {
		boolean resul = false;
		if (estaVacio(direccion)) {
			direccionOK.setForeground(Color.red);
			direccionOK.setText("Vacio!!");
		} else {
			resul = true;
		}
		return resul;
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel apellidosOK;
    private javax.swing.JButton buttonActualizarDatos;
    private javax.swing.JLabel campoApellidos;
    private javax.swing.JLabel campoDNI;
    private javax.swing.JLabel campoDireccion;
    private javax.swing.JLabel campoNombre;
    private javax.swing.JLabel campoSeguro;
    private javax.swing.JLabel campoTelefono;
    private javax.swing.JLabel direccionOK;
    private javax.swing.JTextField fieldApellidos;
    private javax.swing.JTextField fieldDireccion;
    private javax.swing.JTextField fieldNombre;
    private javax.swing.JTextField fieldSeguro;
    private javax.swing.JTextField fieldTelefono;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel labelDNI;
    private javax.swing.JPanel mostrarDatos;
    private javax.swing.JLabel nombreOK;
    private javax.swing.JLabel seguroOK;
    private javax.swing.JLabel telefonoOK;
    // End of variables declaration//GEN-END:variables
}

class JTextFieldLimit extends PlainDocument {

	private int limit;

	JTextFieldLimit(int limit) {
		super();
		this.limit = limit;
	}

	JTextFieldLimit(int limit, boolean upper) {
		super();
		this.limit = limit;
	}

	public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		if (str == null) {
			return;
		}

		if ((getLength() + str.length()) <= limit) {
			super.insertString(offset, str, attr);
		}
	}
}
