/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.modelo.dto.reportes;

import java.util.List;

/**
 *
 * @author MTI Agustin Romero Mata
 */
public class ReportesDesperdicios {

    private String rfc;
    private String fechaInicio;
    private String fechaFin;
    private List<RepDesperdicio> desperdicios;

    /**
     *
     */
    public ReportesDesperdicios() {
        this.rfc = "";
        this.fechaInicio = "";
        this.fechaFin = "";
        this.desperdicios = null;
    }

    /**
     *
     * @param rfc
     * @param fechaInicio
     * @param fechaFin
     * @param desperdicios
     */
    public ReportesDesperdicios(String rfc, String fechaInicio, String fechaFin,
            List<RepDesperdicio> desperdicios) {
        this.rfc = rfc;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.desperdicios = desperdicios;
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
    public List<RepDesperdicio> getDesperdicios() {
        return desperdicios;
    }

    /**
     *
     * @param desperdicios
     */
    public void setDesperdicios(List<RepDesperdicio> desperdicios) {
        this.desperdicios = desperdicios;
    }
}
