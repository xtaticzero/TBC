/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.modelo.dto.reportes;

import java.io.Serializable;
import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dto.marca.Marcas;

/**
 *
 * @author MTI Agustin Romero Mata
 */
public class ReportesMarcas implements Serializable {

    private static final long serialVersionUID = 3236441886058613599L;

    private String rfc;
    private String fechaInicio;
    private String fechaFin;
    private List<Marcas> marcas;

    /**
     *
     */
    public ReportesMarcas() {
        this.rfc = "";
        this.fechaInicio = "";
        this.fechaFin = "";
        this.marcas = null;
    }

    /**
     *
     * @param rfc
     * @param fechaInicio
     * @param fechaFin
     * @param marcas
     */
    public ReportesMarcas(String rfc, String fechaInicio, String fechaFin,
            List<Marcas> marcas) {
        this.rfc = rfc;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.marcas = marcas;
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
    public List<Marcas> getMarcas() {
        return marcas;
    }

    /**
     *
     * @param marcas
     */
    public void setMarcas(List<Marcas> marcas) {
        this.marcas = marcas;
    }
}
