/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.vista.helper;

import java.util.List;
import mx.gob.sat.mat.tabacos.constants.enums.EstadoResolucion;
import mx.gob.sat.mat.tabacos.modelo.dto.SelectItem;
import mx.gob.sat.mat.tabacos.modelo.dto.solicitud.SolicitudesRegistradas;
import mx.gob.sat.mat.tabacos.modelo.dto.solicitud.SolitudAutorizada;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
public class SolicitudHelper {
    private List<SolicitudesRegistradas> solicitudes;
    private List<SelectItem> filtros;
    private List<SelectItem> resoluciones;
    private List<SelectItem> autorizantes;
    private String filtroSel;
    private String buscSel;
    private String paramBusq;
    private SolitudAutorizada solicitud;
    private String razonSocial;
    private String oficio;
    private String autorizante;
    private String tipoResolucionSelect;
    private SolicitudesRegistradas registro;
    private boolean ctrlUploader;
    private boolean btnBorrar;
    private boolean btnGuardar;
    private boolean btnImprimir;
    private boolean captFiltro;
    private boolean cantAutCapt;
    private boolean noFolioCapt;
    private boolean autCapt;
    private boolean fechaCapt;
    private boolean resCapt;
    private EstadoResolucion estadoRes;
    private List<SelectItem> lstTipoResolucion;

    public List<SolicitudesRegistradas> getSolicitudes() {
        return solicitudes;
    }

    public void setSolicitudes(List<SolicitudesRegistradas> solicitudes) {
        this.solicitudes = solicitudes;
    }

    public List<SelectItem> getFiltros() {
        return filtros;
    }

    public void setFiltros(List<SelectItem> filtros) {
        this.filtros = filtros;
    }

    public List<SelectItem> getResoluciones() {
        return resoluciones;
    }

    public void setResoluciones(List<SelectItem> resoluciones) {
        this.resoluciones = resoluciones;
    }

    public List<SelectItem> getAutorizantes() {
        return autorizantes;
    }

    public void setAutorizantes(List<SelectItem> autorizantes) {
        this.autorizantes = autorizantes;
    }

    public String getFiltroSel() {
        return filtroSel;
    }

    public void setFiltroSel(String filtroSel) {
        this.filtroSel = filtroSel;
    }

    public String getBuscSel() {
        return buscSel;
    }

    public void setBuscSel(String buscSel) {
        this.buscSel = buscSel;
    }

    public String getParamBusq() {
        return paramBusq;
    }

    public void setParamBusq(String paramBusq) {
        this.paramBusq = paramBusq;
    }

    public SolitudAutorizada getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(SolitudAutorizada solicitud) {
        this.solicitud = solicitud;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getOficio() {
        return oficio;
    }

    public void setOficio(String oficio) {
        this.oficio = oficio;
    }

    public String getAutorizante() {
        return autorizante;
    }

    public void setAutorizante(String autorizante) {
        this.autorizante = autorizante;
    }

    public String getTipoResolucionSelect() {
        return tipoResolucionSelect;
    }

    public void setTipoResolucionSelect(String tipoResolucionSelect) {
        this.tipoResolucionSelect = tipoResolucionSelect;
    }

    public SolicitudesRegistradas getRegistro() {
        return registro;
    }

    public void setRegistro(SolicitudesRegistradas registro) {
        this.registro = registro;
    }

    public boolean isCtrlUploader() {
        return ctrlUploader;
    }

    public void setCtrlUploader(boolean ctrlUploader) {
        this.ctrlUploader = ctrlUploader;
    }

    public boolean isBtnBorrar() {
        return btnBorrar;
    }

    public void setBtnBorrar(boolean btnBorrar) {
        this.btnBorrar = btnBorrar;
    }

    public boolean isBtnGuardar() {
        return btnGuardar;
    }

    public void setBtnGuardar(boolean btnGuardar) {
        this.btnGuardar = btnGuardar;
    }

    public boolean isBtnImprimir() {
        return btnImprimir;
    }

    public void setBtnImprimir(boolean btnImprimir) {
        this.btnImprimir = btnImprimir;
    }

    public boolean isCaptFiltro() {
        return captFiltro;
    }

    public void setCaptFiltro(boolean captFiltro) {
        this.captFiltro = captFiltro;
    }

    public boolean isCantAutCapt() {
        return cantAutCapt;
    }

    public void setCantAutCapt(boolean cantAutCapt) {
        this.cantAutCapt = cantAutCapt;
    }

    public boolean isNoFolioCapt() {
        return noFolioCapt;
    }

    public void setNoFolioCapt(boolean noFolioCapt) {
        this.noFolioCapt = noFolioCapt;
    }

    public boolean isAutCapt() {
        return autCapt;
    }

    public void setAutCapt(boolean autCapt) {
        this.autCapt = autCapt;
    }

    public boolean isFechaCapt() {
        return fechaCapt;
    }

    public void setFechaCapt(boolean fechaCapt) {
        this.fechaCapt = fechaCapt;
    }

    public boolean isResCapt() {
        return resCapt;
    }

    public void setResCapt(boolean resCapt) {
        this.resCapt = resCapt;
    }

    public EstadoResolucion getEstadoRes() {
        return estadoRes;
    }

    public void setEstadoRes(EstadoResolucion estadoRes) {
        this.estadoRes = estadoRes;
    }

    public List<SelectItem> getLstTipoResolucion() {
        return lstTipoResolucion;
    }

    public void setLstTipoResolucion(List<SelectItem> lstTipoResolucion) {
        this.lstTipoResolucion = lstTipoResolucion;
    }
    
    
}
