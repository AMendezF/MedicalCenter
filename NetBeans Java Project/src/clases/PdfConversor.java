package clases;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author lafarga
 */
public class PdfConversor {

	private javax.swing.JTable tabla;
	private static final Font Title = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
	private static final Font subTitle = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
	private static final Font salas = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
	private static final Font coment = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.DARK_GRAY);
	private Document documento;
	private String title;
	private File file;
	private int NColegiado;
	private String codFicha;
	private String nombrePaciente;
	private String horaCita;
	private String diaCita;
	private String comentarioFicha;
	private String Agenda;

	public PdfConversor(JTable tabla, String titulo, String diaCita, int NColegiado, String directorio) {
		this.tabla = tabla;
		this.title = titulo + " " + diaCita;
		this.diaCita = diaCita;
		this.NColegiado = NColegiado;
		String fileTitle = titulo.replace(" ", "_") + "_" + NColegiado + "_" + this.diaCita + ".pdf";
		if (directorio.contains(".pdf")) {
			this.file = new File(directorio);
		} else {
			this.file = new File(directorio + ".pdf");
		}
		initDocumento();
	}

	public PdfConversor(String codFicha, String nombre, String hora, String dia, String comentario, int n_colegiado, String directorio) {
		this.title = "Ficha del dia " + dia;
		this.codFicha = codFicha;
		this.nombrePaciente = nombre;
		this.horaCita = hora;
		this.diaCita = dia;
		this.comentarioFicha = comentario;
		this.NColegiado = n_colegiado;
		String fileTitle = "Ficha_" + dia + ".pdf";
		if (directorio.contains(".pdf")) {
			this.file = new File(directorio);
		} else {
			this.file = new File(directorio + "\\" + fileTitle);
		}
		initDocumento();

	}

	public PdfConversor(String contenidoAgenda, String directorio) {
		this.title = "Agenda del dia " + getDia();
		this.Agenda = contenidoAgenda.replace("\t", "");
		String fileTitle = "Agenda_" + getDia() + ".pdf";
		if (directorio.contains(".pdf")) {
			this.file = new File(directorio);
		} else {
			this.file = new File(directorio + "\\" + fileTitle);
		}
		initDocumento();
	}

	private void jTableToPdf() throws DocumentException {
		Paragraph colegiado = new Paragraph("Colegiado Nº " + this.NColegiado, subTitle);
		documento.add(colegiado);
		Paragraph fecha = new Paragraph("Fecha: " + getDia(), subTitle);
		documento.add(fecha);
		// Creamos la tabla
		PdfPTable table = new PdfPTable(tabla.getColumnCount());

		// Ahora llenamos las filas de PdfPTable
		PdfPCell columnHeader;

		// Rellenamos las cabeceras de las columnas de la tabla.                
		for (int column = 0; column < tabla.getColumnCount(); column++) {
			columnHeader = new PdfPCell(new Phrase(tabla.getColumnName(column), subTitle));
			columnHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(columnHeader);
		}
		table.setHeaderRows(1);
		// Fill table rows (rellenamos las filas de la tabla).                
		for (int row = 0; row < tabla.getRowCount(); row++) {
			for (int column = 0; column < tabla.getColumnCount(); column++) {
				table.addCell(tabla.getValueAt(row, column).toString());
			}
		}

		//Imprimimos en el documento
		documento.add(new Paragraph("\n"));
		documento.add(table);

		documento.close();

	}

	private void fichaToPdf() throws DocumentException {
		Paragraph colegiado = new Paragraph("Colegiado Nº " + this.NColegiado, subTitle);
		documento.add(colegiado);
		Paragraph fecha = new Paragraph("Fecha: " + getDia(), subTitle);
		documento.add(fecha);
		Paragraph paciente = new Paragraph("\nPaciente: " + this.nombrePaciente, subTitle);
		documento.add(paciente);
		Paragraph codigo = new Paragraph("Cod. Ficha: " + this.codFicha, subTitle);
		documento.add(codigo);
		documento.add(new Paragraph("Fecha del diagnóstico: " + this.diaCita, subTitle));
		documento.add(new Paragraph("Comentarios:", subTitle));
		documento.add(new Paragraph("\n" + this.comentarioFicha, coment));

		documento.close();
	}

	public void getPdfFicha() throws DocumentException {
		fichaToPdf();
	}

	public void getPdfTablas() throws DocumentException {
		jTableToPdf();

	}

	private void initDocumento() {
		try {
			this.documento = new Document();
			PdfWriter.getInstance(this.documento, new FileOutputStream(file));
			Image logo = Image.getInstance("./src/logo_redimensionado.png");
			Image waterMark = Image.getInstance("./src/logo_watermark.png");
			documento.open();

			// Añadimos los metadatos del PDF
			documento.addTitle(this.title);
			documento.addAuthor("CentroMedicoUPM");
			documento.addCreator("Pablo");
			documento.addCreationDate();
			logo.scalePercent(15);
			logo.setAbsolutePosition(450f, 750f);
			documento.add(logo);
			waterMark.scalePercent(80);
			waterMark.setAbsolutePosition(90f, 100f);
			documento.add(waterMark);
			Anchor anchor = new Anchor(title, Title);
			anchor.setName(title);
			Paragraph titulo = new Paragraph(anchor);
			documento.add(titulo);

		} catch (FileNotFoundException | DocumentException ex) {
			Logger.getLogger(PdfConversor.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(PdfConversor.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	public String getDia() {
		Calendar fechaActual = new GregorianCalendar();
		return fechaActual.get(Calendar.YEAR) + "-"
				+ (fechaActual.get(Calendar.MONTH) + 1) + "-"
				+ fechaActual.get(Calendar.DAY_OF_MONTH);
	}

	public void getPdfAgenda() {
		agendaToPdf();
	}

	private void agendaToPdf() {
		try {
			Paragraph fecha = new Paragraph("Fecha: " + getDia(), subTitle);
			documento.add(fecha);
			documento.add(new Paragraph("\n"));
			addComentAgenda();
			documento.close();
		} catch (DocumentException ex) {
			Logger.getLogger(PdfConversor.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	private void addComentAgenda() throws DocumentException {
		String[] Agenda = this.Agenda.split("\n");
		for (int i = 0; i < Agenda.length; i++) {
			if (Agenda[i].contains("Sala")) {
				documento.add(new Phrase("\t" + Agenda[i] + "\n", salas));
			} else if (Agenda[i].contains("TARDE") || Agenda[i].contains("MAÑANA")) {
				documento.add(new Phrase("\n" + Agenda[i] + "\n", subTitle));
			} else if (Agenda[i].contains("_")) {

			} else {
				documento.add(new Phrase("\t" + Agenda[i] + "\n", coment));
			}
		}
	}
}
