/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.sat.mat.tabacos.modelo.dto.reportes;

import java.io.Serializable;
import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dto.Proveedor;

/**
 *
 * @author MTI Agustin Romero Mata
 */
public class ReportesProveedores implements Serializable {
    private static final long serialVersionUID = 2364888449620725556L;
    private String  rfc;
    private String  fechaInicio;
    private String  fechaFin;
    private List<Proveedor> proveedor;

    /**
     * 
     */
    public ReportesProveedores() {
        this.rfc = "";
        this.fechaInicio = "";
        this.fechaFin = "";
        this.proveedor = null;
    }

    /**
     * 
     * @param rfc
     * @param fechaInicio
     * @param fechaFin
     * @param proveedor 
     */
    public ReportesProveedores(String rfc, String fechaInicio, String fechaFin, 
            List<Proveedor> proveedor) {
        this.rfc = rfc;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.proveedor = proveedor;
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
    public List<Proveedor> getProveedor() {
        return proveedor;
    }

    /**
     * 
     * @param proveedor 
     */
    public void setProveedor(List<Proveedor> proveedor) {
        this.proveedor = proveedor;
    }       
}