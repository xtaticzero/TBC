/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.vista.helper;

import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dto.marca.Marcas;
import mx.gob.sat.mat.tabacos.modelo.dto.SelectItem;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
public class AbcMarcaHelper {

    public static final int MAX_LENGTH_MARCA = 6;

    private boolean acuseBaja;
    private boolean acuseCambio;
    private boolean formBaja;
    private boolean flgFile;
    private boolean flgBtnGuarda;
    private boolean flgBtnGuardaBaja;
    private boolean flgBtnGuardaCambio;

    private String nombreArchivo;
    private String rutaArchivo;
    private String confirmacion;

    private String destination;
    private List<SelectItem> marcas;
    private List<SelectItem> tabacaleras;
    private Marcas marcaBaja;
    private Marcas marcaCambio;
    private UploadedFile archivo;

    public boolean isAcuseBaja() {
        return acuseBaja;
    }

    public void setAcuseBaja(boolean acuseBaja) {
        this.acuseBaja = acuseBaja;
    }

    public boolean isAcuseCambio() {
        return acuseCambio;
    }

    public void setAcuseCambio(boolean acuseCambio) {
        this.acuseCambio = acuseCambio;
    }

    public boolean isFormBaja() {
        return formBaja;
    }

    public void setFormBaja(boolean formBaja) {
        this.formBaja = formBaja;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public List<SelectItem> getMarcas() {
        return marcas;
    }

    public void setMarcas(List<SelectItem> marcas) {
        this.marcas = marcas;
    }

    public List<SelectItem> getTabacaleras() {
        return tabacaleras;
    }

    public void setTabacaleras(List<SelectItem> tabacaleras) {
        this.tabacaleras = tabacaleras;
    }

    public Marcas getMarcaBaja() {
        return marcaBaja;
    }

    public void setMarcaBaja(Marcas marcaBaja) {
        this.marcaBaja = marcaBaja;
    }

    public boolean isFlgFile() {
        return flgFile;
    }

    public void setFlgFile(boolean flgFile) {
        this.flgFile = flgFile;
    }

    public UploadedFile getArchivo() {
        return archivo;
    }

    public void setArchivo(UploadedFile archivo) {
        this.archivo = archivo;
    }

    public boolean isFlgBtnGuarda() {
        return flgBtnGuarda;
    }

    public void setFlgBtnGuarda(boolean flgBtnGuarda) {
        this.flgBtnGuarda = flgBtnGuarda;
    }

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    public String getConfirmacion() {
        return confirmacion;
    }

    public void setConfirmacion(String confirmacion) {
        this.confirmacion = confirmacion;
    }

    public boolean isFlgBtnGuardaBaja() {
        return flgBtnGuardaBaja;
    }

    public void setFlgBtnGuardaBaja(boolean flgBtnGuardaBaja) {
        this.flgBtnGuardaBaja = flgBtnGuardaBaja;
    }

    public boolean isFlgBtnGuardaCambio() {
        return flgBtnGuardaCambio;
    }

    public void setFlgBtnGuardaCambio(boolean flgBtnGuardaCambio) {
        this.flgBtnGuardaCambio = flgBtnGuardaCambio;
    }

    public Marcas getMarcaCambio() {
        return marcaCambio;
    }

    public void setMarcaCambio(Marcas marcaCambio) {
        this.marcaCambio = marcaCambio;
    }
}
