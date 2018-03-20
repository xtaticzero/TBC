/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.vista.helper;

import java.io.Serializable;
import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dto.codigos.Codigo;
import mx.gob.sat.mat.tabacos.modelo.dto.marca.Marcas;
import mx.gob.sat.mat.tabacos.modelo.dto.Tabacalera;
import mx.gob.sat.mat.tabacos.modelo.dto.tipo.TipoCodigo;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author MMMF
 */
public class IngresaCodigosHelper implements Serializable {
    private static final long serialVersionUID = 325507574L;
    private Codigo codigo;
    private List<TipoCodigo> tiposCodigo;
    private List<Marcas> marcasList;
    private int opcionTipoCodigo;
    private Long opcionMarca;
    private List<Tabacalera> tabacaleras;
    private Long opcionRfcTabacalera;
    private transient UploadedFile archivoFolios;
    private String errorSeleccionRfc;
    private String errorSeleccionTipoCod;
    private String errorNumeroCodigo;
    private String rfcTabacalera;
    private String razonSocial;
    private String errorGeneral;
    private boolean visiblePanelCodigos;
    private boolean visiblePanelFirma;
    private boolean visiblePanelAcuse;
    private boolean visiblePanelExito;

    public Codigo getCodigo() {
        return codigo;
    }

    public void setCodigo(Codigo codigo) {
        this.codigo = codigo;
    }

    public List<TipoCodigo> getTiposCodigo() {
        return tiposCodigo;
    }

    public void setTiposCodigo(List<TipoCodigo> tiposCodigo) {
        this.tiposCodigo = tiposCodigo;
    }

    public List<Marcas> getMarcasList() {
        return marcasList;
    }

    public void setMarcasList(List<Marcas> marcasList) {
        this.marcasList = marcasList;
    }

    public int getOpcionTipoCodigo() {
        return opcionTipoCodigo;
    }

    public void setOpcionTipoCodigo(int opcionTipoCodigo) {
        this.opcionTipoCodigo = opcionTipoCodigo;
    }

    public Long getOpcionMarca() {
        return opcionMarca;
    }

    public void setOpcionMarca(Long opcionMarca) {
        this.opcionMarca = opcionMarca;
    }

    public UploadedFile getArchivoFolios() {
        return archivoFolios;
    }

    public void setArchivoFolios(UploadedFile archivoFolios) {
        this.archivoFolios = archivoFolios;
    }

    public List<Tabacalera> getTabacaleras() {
        return tabacaleras;
    }

    public void setTabacaleras(List<Tabacalera> tabacaleras) {
        this.tabacaleras = tabacaleras;
    }

    public Long getOpcionRfcTabacalera() {
        return opcionRfcTabacalera;
    }

    public void setOpcionRfcTabacalera(Long opcionRfcTabacalera) {
        this.opcionRfcTabacalera = opcionRfcTabacalera;
    }

    public String getErrorSeleccionRfc() {
        return errorSeleccionRfc;
    }

    public void setErrorSeleccionRfc(String errorSeleccionRfc) {
        this.errorSeleccionRfc = errorSeleccionRfc;
    }

    public String getErrorSeleccionTipoCod() {
        return errorSeleccionTipoCod;
    }

    public void setErrorSeleccionTipoCod(String errorSeleccionTipoCod) {
        this.errorSeleccionTipoCod = errorSeleccionTipoCod;
    }

    public String getErrorNumeroCodigo() {
        return errorNumeroCodigo;
    }

    public void setErrorNumeroCodigo(String errorNumeroCodigo) {
        this.errorNumeroCodigo = errorNumeroCodigo;
    }

    public String getRfcTabacalera() {
        return rfcTabacalera;
    }

    public void setRfcTabacalera(String rfcTabacalera) {
        this.rfcTabacalera = rfcTabacalera;
    }
    
    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }
    
    public String getErrorGeneral() {
        return errorGeneral;
    }

    public void setErrorGeneral(String errorGeneral) {
        this.errorGeneral = errorGeneral;
    }

    public boolean isVisiblePanelCodigos() {
        return visiblePanelCodigos;
    }

    public void setVisiblePanelCodigos(boolean visiblePanelCodigos) {
        this.visiblePanelCodigos = visiblePanelCodigos;
    }

    public boolean isVisiblePanelFirma() {
        return visiblePanelFirma;
    }

    public void setVisiblePanelFirma(boolean visiblePanelFirma) {
        this.visiblePanelFirma = visiblePanelFirma;
    }

    public boolean isVisiblePanelAcuse() {
        return visiblePanelAcuse;
    }

    public void setVisiblePanelAcuse(boolean visiblePanelAcuse) {
        this.visiblePanelAcuse = visiblePanelAcuse;
    }

    public boolean isVisiblePanelExito() {
        return visiblePanelExito;
    }

    public void setVisiblePanelExito(boolean visiblePanelExito) {
        this.visiblePanelExito = visiblePanelExito;
    }
}
