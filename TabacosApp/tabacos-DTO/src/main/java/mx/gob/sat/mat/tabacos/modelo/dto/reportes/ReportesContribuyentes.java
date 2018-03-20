/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.modelo.dto.reportes;

import java.io.Serializable;
import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dto.Tabacalera;

/**
 *
 * @author MTI Agustin Romero Mata
 */
public class ReportesContribuyentes implements Serializable {

    private static final long serialVersionUID = 1337556691832855108L;
    private String rfc;
    private String fechaInicio;
    private String fechaFin;
    private String tipoReporte;
    private List<Tabacalera> contribuyentes;

    /**
     *
     */
    public ReportesContribuyentes() {
        this.rfc = "";
        this.fechaInicio = "";
        this.fechaFin = "";
        this.contribuyentes = null;
    }

    /**
     *
     * @param rfc
     * @param fechaInicio
     * @param fechaFin
     * @param contribuyentes
     */
    public ReportesContribuyentes(String rfc, String fechaInicio,
            String fechaFin, List<Tabacalera> contribuyentes) {
        this.rfc = rfc;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.contribuyentes = contribuyentes;
    }

    /**
     *
     * @return
     */
    public String getRfc() {
        return rfc;
    }

    /**
     *
     * @param rfc
     */
    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    /**
     *
     * @return
     */
    public String getFechaInicio() {
        return fechaInicio;
    }

    /**
     *
     * @param fechaInicio
     */
    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     *
     * @return
     */
    public String getFechaFin() {
        return fechaFin;
    }

    /**
     *
     * @param fechaFin
     */
    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     *
     * @return
     */
    public String getTipoReporte() {
        return tipoReporte;
    }

    /**
     *
     * @param tipoReporte
     */
    public void setTipoReporte(String tipoReporte) {
        this.tipoReporte = tipoReporte;
    }

    /**
     *
     * @return
     */
    public List<Tabacalera> getContribuyentes() {
        return contribuyentes;
    }

    /**
     *
     * @param contribuyentes
     */
    public void setContribuyentes(List<Tabacalera> contribuyentes) {
        this.contribuyentes = contribuyentes;
    }
}
