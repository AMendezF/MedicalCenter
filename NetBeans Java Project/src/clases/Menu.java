package clases;

import java.sql.SQLException;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class Menu {

	private Paciente paciente;
	private Medico medico;

	public Menu() {

	}

	public void menuRoot(Conexion con) throws SQLException {
		Scanner sc = new Scanner(System.in);
		String dni = "";
		String op = "0";
		paciente = new Paciente(dni, con);

		do {
			System.out.println("\n\nEntrando en menu de gestor: ");
			System.out.println("  Selecciona la opción que desee realizar: ");
			System.out.println("	1. Mostrar pacientes.");
			System.out.println("	2. Añadir nuevo paciente.");
			System.out.println("	3. Gestionar paciente.");
			System.out.println("	4. Gestionar medicos.");
			System.out.println("	5. Mostrar salas.");
			System.out.println("	0. Salir");
			op = sc.nextLine();

			switch (op) {
				case "1":
					main.mostrarListaPacientes(con);
					break;
				case "2":
					paciente.addPacienteBD();
					break;
				case "3":
					main.mostrarListaPacientes(con);
					dni = paciente.cogerPaciente(con);
					paciente = new Paciente(dni, con);
					gestionarPacientes(paciente, con);
					break;
				case "4":
					main.mostrarListaMedicos(con);
					//medico = main.cogerMedico();
					gestionarMedicos(medico, con);
					break;
				case "5":
					main.mostrarSalas(con);
					break;
			}

		} while (!op.equals("0"));

		System.out.println("Saliendo de menu gestor\n");
		con.desconectar();
	}

	public void gestionarPacientes(Paciente paciente, Conexion con) throws SQLException {
		Scanner sc = new Scanner(System.in);
		String dni = "";
		String op = "0";

		do {
			System.out.println("\n\nMenu del paciente: ");
			main.mostrarPaciente(paciente.getDNI(), con);
			System.out.println("\n  Selecciona la opción que desee realizar: ");
			System.out.println("	1. Coger nuevo paciente");
			System.out.println("	2. Mostrar citas del paciente");
			System.out.println("	3. Pedir cita");
			System.out.println("	4. Cancelar cita");
			System.out.println("	5. Modificar paciente");
			System.out.println("	0. Salir");
			op = sc.nextLine();

			switch (op) {
				case "1":
					main.mostrarListaPacientes(con);
					dni = paciente.cogerPaciente(con);
					paciente = new Paciente(dni, con);
					gestionarPacientes(paciente, con);
					break;
				case "2":
					if (paciente.estaBD()) {
						if (paciente.tieneCitas()) {
							paciente.mostrarCitas();
						} else {
							System.out.println("No tiene citas el paciente con DNI: " + paciente.getDNI() + ".\n");
						}
					} else {
						System.out.println("No existe el paciente con DNI: " + paciente.getDNI() + ".\n");
					}
					break;
				case "3":
					if (paciente.estaBD()) {
						main.pedirCita(paciente, con);
					} else {
						System.out.println("No existe el paciente con DNI: " + paciente.getDNI() + ".\n");
					}
					break;

				case "4":
					if (paciente.estaBD()) {
						if (paciente.tieneCitas()) {
							main.eliminarCita(paciente, con);
						} else {
							System.out.println("No tiene citas el paciente con DNI: " + paciente.getDNI() + ".\n");
						}
					} else {
						System.out.println("No existe el paciente con DNI: " + paciente.getDNI() + ".\n");
					}
					break;
				case "5":
					if (paciente.estaBD()) {
						paciente.modificar();
					} else {
						System.out.println("No existe el paciente con DNI: " + paciente.getDNI());
					}
					break;
			}

		} while (!op.equals("0"));

		System.out.println("Saliendo de administrar paciente\n");
	}

	public void gestionarMedicos(Medico medico, Conexion con) throws SQLException {
		Scanner sc = new Scanner(System.in);
		String op = "0";

		do {
			System.out.println("\n\nMenu de medicos: ");
			System.out.println("  Selecciona la opción que desee realizar: ");
			System.out.println("	1. Mostrar medicos");
			System.out.println("	2. Añadir medicos");
			System.out.println("	3. Borrar medico");
			System.out.println("	0. Salir");
			op = sc.nextLine();

			switch (op) {
				case "1":
					main.mostrarListaMedicos(con);
					break;
				case "2":
					main.addMedico(con);
					break;
				case "3":
					main.eliminarMedico(con);
					break;

			}

		} while (!op.equals("0"));
		System.out.println("Saliendo de administrar medicos\n");
	}

	public void menuMedico(Conexion con) throws SQLException {
		Scanner sc = new Scanner(System.in);
		String op = "0";

		do {
			System.out.println("\nEntrando en menu de medico: ");
			System.out.println("  Selecciona la opcion que desee realizar: ");
			System.out.println("	1. Mostrar pacientes(por especialidad del medico)");
			System.out.println("	2. Coger nuevo paciente");
			System.out.println("	3. Mostrar datos paciente");
			System.out.println("	0. Salir");
			op = sc.nextLine();

			switch (op) {
				case "1":
					//paciente.mostrarListaPacientes();
					break;
				case "2":
					//paciente = main.cogerPaciente(con);
					break;
				case "3":
					//paciente.mostrarPaciente(paciente.getDNI());
					break;

			}
		} while (!op.equals("0"));
		System.out.println("Saliendo de menu medico\n");
		con.desconectar();
	}
}
