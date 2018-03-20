/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.vista.helper;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import mx.gob.sat.mat.tabacos.constants.enums.ABCEnum;
import mx.gob.sat.mat.tabacos.modelo.dto.Tabacalera;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
public class ReporteTabacaleraHelper implements Serializable {

    private static final long serialVersionUID = -7203018593461719438L;

    private String rfcContribuyente;
    private Date fechaInicio;
    private Date fechaFin;
    private Date fechaMaxima;
    private ABCEnum tipoReporte;
    private List<Tabacalera> lstTabacaleras;

    public static final ABCEnum TIPO_ALTA = ABCEnum.ALTA;
    public static final ABCEnum TIPO_BAJA = ABCEnum.BAJA;;
    public static final ABCEnum TIPO_CAMBIO = ABCEnum.CAMBIO;

    public String getRfcContribuyente() {
        return rfcContribuyente;
    }

    public void setRfcContribuyente(String rfcContribuyente) {
        this.rfcContribuyente = rfcContribuyente;
    }

    public Date getFechaInicio() {
        return (fechaInicio != null) ? (Date) fechaInicio.clone() : null;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = (fechaInicio != null) ? (Date) fechaInicio.clone() : null;
    }

    public Date getFechaFin() {
        return (fechaFin != null) ? (Date) fechaFin.clone() : null;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = (fechaFin != null) ? (Date) fechaFin.clone() : null;
    }

    public Date getFechaMaxima() {
        return (fechaMaxima != null) ? (Date) fechaMaxima.clone() : null;
    }

    public void setFechaMaxima(Date fechaMaxima) {
        this.fechaMaxima = (fechaMaxima != null) ? (Date) fechaMaxima.clone() : null;
    }

    public List<Tabacalera> getLstTabacaleras() {
        return lstTabacaleras;
    }

    public void setLstTabacaleras(List<Tabacalera> lstTabacaleras) {
        this.lstTabacaleras = lstTabacaleras;
    }

    public ABCEnum getTipoReporte() {
        return tipoReporte;
    }

    public void setTipoReporte(ABCEnum tipoReporte) {
        this.tipoReporte = tipoReporte;
    }

    public ABCEnum getTipoAlta() {
        return TIPO_ALTA;
    }

    public ABCEnum getTipoBaja() {
        return TIPO_BAJA;
    }

    public ABCEnum getTipoCambio() {
        return TIPO_CAMBIO;
    }
}
