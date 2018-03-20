/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.modelo.dto.reportes;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author MTI Agustin Romero Mata
 */
public class ReportesRegistros implements Serializable {

    private static final long serialVersionUID = 5521612783478688712L;
    private String rfc;
    private String fechaInicio;
    private String fechaFin;
    private List<RepRegistro> registros;

    public ReportesRegistros() {
        this.rfc = "";
        this.fechaInicio = "";
        this.fechaFin = "";
        this.registros = null;
    }

    public ReportesRegistros(String rfc, String fechaInicio, String fechaFin,
            List<RepRegistro> registros) {
        this.rfc = rfc;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.registros = registros;
    }

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
    public List<RepRegistro> getRegistros() {
        return registros;
    }

    /**
     *
     * @param registros
     */
    public void setRegistros(List<RepRegistro> registros) {
        this.registros = registros;
    }
}
