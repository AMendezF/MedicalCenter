/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author lafarga
 */
public class Selector {

    private JFileChooser explorador;

    public Selector() {
        this.explorador = new JFileChooser();
        this.explorador.setCurrentDirectory(new java.io.File(System.getProperty("user.home") + "\\Desktop"));
        this.explorador.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        this.explorador.setDialogTitle("Guardar documento...");
        this.explorador.setFileFilter(new FileNameExtensionFilter("PDF - Adobe Portable Document Format", "pdf"));
    }

    public int opcionMarcada() {
        return this.explorador.showDialog(null, "Guardar");
    }

    public String retornarDirectorioElegido() {
        return this.explorador.getSelectedFile().getAbsolutePath();
    }

}
