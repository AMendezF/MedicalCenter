CREATE  TABLE "Medico" 
	("N_colegiado" INTEGER PRIMARY KEY  NOT NULL, 
	"Nombre" VARCHAR NOT NULL , 
	"Apellidos" VARCHAR NOT NULL , 
	"Horario" VARCHAR NOT NULL , 	
	"Tiempo_min" INTEGER NOT NULL, 
	"Especialidad" INTEGER NOT NULL,
	FOREIGN KEY (Especialidad) REFERENCES especialidad(cod_especilidad));

CREATE  TABLE "Especialidad" 
	("Cod_especialidad" INTEGER PRIMARY KEY  NOT NULL, 
	"Nombre" VARCHAR NOT NULL);

CREATE  TABLE "Citas" (
	"Cod_cita" INTEGER PRIMARY KEY  NOT NULL, 
	"Dia_hora" DATETIME NOT NULL , 
	"Medico" INTEGER NOT NULL,
	"Especialidad" INTEGER NOT NULL,
	"Paciente" VARCHAR NOT NULL,
	FOREIGN KEY (Especialidad) REFERENCES especialidad(cod_especilidad),
	FOREIGN KEY (Medico) REFERENCES medico(n_colegiado),
	FOREIGN KEY (paciente) REFERENCES paciente(dni));


CREATE TABLE "paciente"(
	"DNI" VARCHAR PRIMARY KEY NOT NULL,
	"Nombre" VARCHAR NOT NULL , 
	"Apellidos" VARCHAR NOT NULL , 
	"CompSeguro" VARCHAR);

CREATE TABLE "historial"(
	"Cod_historial" INTEGER PRIMARY KEY  NOT NULL, 
	"Paciente" VARCHAR NOT NULL,
	"Especialidad" INTEGER NOT NULL,
	FOREIGN KEY (Especialidad) REFERENCES especialidad(cod_especilidad),
	FOREIGN KEY (paciente) REFERENCES paciente(dni));

CREATE TABLE "Ficha"(
	"Cod_historial" INTEGER PRIMARY KEY  NOT NULL, 
	"Cod_cita" INTEGER NOT NULL, 
	"comentario" VARCHAR,
	"Dia_hora" DATETIME NOT NULL , 
	FOREIGN KEY (Cod_historial) REFERENCES historial(cod_historial),
	FOREIGN KEY (Cod_cita) REFERENCES cita(cod_cita)
	FOREIGN KEY (Dia_hora) REFERENCES cita(Dia_hora));


	
	
	
	 