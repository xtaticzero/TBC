/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.modelo.dto.reportes;

import java.io.Serializable;
import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dto.codigos.Codigo;

/**
 *
 * @author MTI Agustin Romero Mata
 */
public class ReportesCodigos implements Serializable {

    private static final long serialVersionUID = 3435886154300176189L;
    private String rfc;
    private String fechaInicio;
    private String fechaFin;
    private List<Codigo> codigos;

    /**
     *
     */
    public ReportesCodigos() {
        this.rfc = "";
        this.fechaInicio = "";
        this.fechaFin = "";
        this.codigos = null;
    }

    /**
     *
     * @param rfc
     * @param fechaInicio
     * @param fechaFin
     * @param codigos
     */
    public ReportesCodigos(String rfc, String fechaInicio, String fechaFin,
            List<Codigo> codigos) {
        this.rfc = rfc;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.codigos = codigos;
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
    public List<Codigo> getCodigos() {
        return codigos;
    }

    /**
     *
     * @param codigos
     */
    public void setCodigos(List<Codigo> codigos) {
        this.codigos = codigos;
    }
}
