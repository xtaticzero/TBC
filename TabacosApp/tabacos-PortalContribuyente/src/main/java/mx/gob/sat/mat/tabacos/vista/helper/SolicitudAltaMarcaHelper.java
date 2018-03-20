/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.vista.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dto.Tabacalera;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
public class SolicitudAltaMarcaHelper implements Serializable {

    private static final long serialVersionUID = 9045957717696822914L;

    private Tabacalera tabacalera;
    private transient UploadedFile archivoFolios;
    private String marca;
    private String confirmMarca;
    private String clavleMarca;
    private List<Tabacalera> lstContribuyente;

    public SolicitudAltaMarcaHelper() {
        this.tabacalera = new Tabacalera();
        this.lstContribuyente = new ArrayList<Tabacalera>();
    }

    public Tabacalera getTabacalera() {
        return tabacalera;
    }

    public void setTabacalera(Tabacalera tabacalera) {
        this.tabacalera = tabacalera;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getConfirmMarca() {
        return confirmMarca;
    }

    public void setConfirmMarca(String confirmMarca) {
        this.confirmMarca = confirmMarca;
    }

    public String getClavleMarca() {
        return clavleMarca;
    }

    public void setClavleMarca(String clavleMarca) {
        this.clavleMarca = clavleMarca;
    }

    public List<Tabacalera> getLstContribuyente() {
        return lstContribuyente;
    }

    public void setLstContribuyente(List<Tabacalera> lstContribuyente) {
        this.lstContribuyente = lstContribuyente;
    }

    public UploadedFile getArchivoFolios() {
        return archivoFolios;
    }

    public void setArchivoFolios(UploadedFile archivoFolios) {
        this.archivoFolios = archivoFolios;
    }

}
