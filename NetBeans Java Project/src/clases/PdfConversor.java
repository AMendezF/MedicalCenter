package clases;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author lafarga
 */
public class PdfConversor {

    private javax.swing.JTable tabla;
    private static final Font categoryFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
    private static final Font subCategoryFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
    private static final Font redFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.RED);
    private static final Font smallBold = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);

    public PdfConversor(JTable tabla) {
        this.tabla = tabla;
    }

    private void utilJTableToPdf(File pdfNewFile, String title) {
        try {       
            // Creamos el documento e indicamos el nombre del fichero.
            Document document = new Document();
            try {
                PdfWriter.getInstance(document, new FileOutputStream(pdfNewFile));
            } catch (FileNotFoundException fileNotFoundException) {
                System.out.println("No such file was found to generate the PDF (No se encontró el fichero para generar el pdf)" + fileNotFoundException);
            }
            document.open();
            // Añadimos los metadatos del PDF
            document.addTitle(title);
//            document.addSubject("Using iText (usando iText)");
//            document.addAuthor("Código Xules");
//            document.addCreator("Código Xules");

            // Primera página
            Anchor anchor = new Anchor(title, categoryFont);
            anchor.setName(title);

//            // El segundo parámetro es el número del capítulo
            Paragraph titulo = new Paragraph(anchor);


            // Creamos la tabla
            PdfPTable table = new PdfPTable(tabla.getColumnCount());

            // Ahora llenamos las filas de PdfPTable
            PdfPCell columnHeader;
            // Fill table columns header 
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
            document.add(titulo);
            document.add(new Paragraph("\n"));
            document.add(table);
            document.addCreationDate();
            document.close();
        } catch (DocumentException documentException) {
            System.out.println("The file not exists (Se ha producido un error al generar un documento): " + documentException);
        }

    }

    public void getPdf(File NameFile, String titulo) {
        utilJTableToPdf(NameFile, titulo);
        
    }
}
