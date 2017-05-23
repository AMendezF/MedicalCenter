package clases;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
    private static final Font categoryFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
    private static final Font smallBold = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
    private Document documento;
    private String title;
    private File file;

    public PdfConversor(JTable tabla, String titulo) {
        this.tabla = tabla;
        this.title = titulo;
        this.file = new File(title.replace(" ", "_") + ".pdf");
        initDocumento();
    }

    private void jTableToPdf() throws DocumentException {
        // Primera página
        Anchor anchor = new Anchor(title, categoryFont);
        anchor.setName(title);

        // El segundo parámetro es el número del capítulo
        Paragraph titulo = new Paragraph(anchor);

        // Creamos la tabla
        PdfPTable table = new PdfPTable(tabla.getColumnCount());

        // Ahora llenamos las filas de PdfPTable
        PdfPCell columnHeader;

        // Rellenamos las cabeceras de las columnas de la tabla.                
        for (int column = 0; column < tabla.getColumnCount(); column++) {
            columnHeader = new PdfPCell(new Phrase(tabla.getColumnName(column), smallBold));
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
        documento.add(titulo);
        documento.add(new Paragraph("\n"));
        documento.add(table);

        documento.close();

    }

    public void getPdf() throws DocumentException {
        jTableToPdf();

    }

    private void initDocumento() {
        try {
            this.documento = new Document();
            PdfWriter.getInstance(this.documento, new FileOutputStream(file));
            Image logo = Image.getInstance("./src/logo_redimensionado.png");
            
            documento.open();
            
            // Añadimos los metadatos del PDF
            documento.addTitle(this.title);
            documento.addAuthor("CentroMedicoUPM");
            documento.addCreator("Pablo");
            documento.addCreationDate();
            logo.scalePercent(15);
            logo.setAbsolutePosition(450f, 750f);
            documento.add(logo);
            documento.add(new Paragraph("\n"));

            
        } catch (FileNotFoundException | DocumentException ex) {
            Logger.getLogger(PdfConversor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PdfConversor.class.getName()).log(Level.SEVERE, null, ex);
        }
   
    }
}
