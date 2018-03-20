/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.vista.helper;

import org.primefaces.model.UploadedFile;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
public class AutorizarSolicitudHelper{
    private String nombreArchivoRes;
    private String rutaArchivoRes;
    private UploadedFile archivoResolucion;

    public String getNombreArchivoRes() {
        return nombreArchivoRes;
    }

    public void setNombreArchivoRes(String nombreArchivoRes) {
        this.nombreArchivoRes = nombreArchivoRes;
    }

    public String getRutaArchivoRes() {
        return rutaArchivoRes;
    }

    public void setRutaArchivoRes(String rutaArchivoRes) {
        this.rutaArchivoRes = rutaArchivoRes;
    }

    public UploadedFile getArchivoResolucion() {
        return archivoResolucion;
    }

    public void setArchivoResolucion(UploadedFile archivoResolucion) {
        this.archivoResolucion = archivoResolucion;
    }
}
