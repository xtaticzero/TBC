/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.vista.helper;

import java.util.Date;
import java.util.List;
import mx.gob.sat.mat.tabacos.constants.enums.ABCEnum;
import mx.gob.sat.mat.tabacos.modelo.dto.Proveedor;

/**
 *
 * @author MMMF
 */
public class ReporteProveedorHelper {
    private String rfc;
    private String razonSocial;
    private Date fechaInicio;
    private Date fechaFin; 
    private String tipoReporte;
    private List<Proveedor> proveedores;
    
    private final ABCEnum tipoAlta;
    private final ABCEnum tipoBaja;
    private final ABCEnum tipoCambio;

    public ReporteProveedorHelper() {
        this.rfc = "";
        this.fechaInicio = null;
        this.fechaFin = null;
        tipoAlta = ABCEnum.ALTA;
        tipoBaja = ABCEnum.BAJA;
        tipoCambio = ABCEnum.CAMBIO;
    }    
    
    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }
    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public Date getFechaInicio() {
        return (this.fechaInicio == null) ? null : 
                (Date)this.fechaInicio.clone();
    }

    public void setFechaInicio(Date fechainicio) {
        this.fechaInicio = fechainicio == null ? null : 
                (Date)fechainicio.clone();
    }

    public Date getFechaFin() {
        return (this.fechaFin == null) ? null : 
                (Date) this.fechaFin.clone();
    }

    public void setFechaFin(Date fechafin) {
        this.fechaFin = (fechafin == null) ? null : 
                (Date) fechafin.clone();
    }
    
    public String getTipoReporte() {
        return tipoReporte;
    }

    public void setTipoReporte(String tipoReporte) {
        this.tipoReporte = tipoReporte;
    }

    public List<Proveedor> getProveedores() {
        return proveedores;
    }

    public void setProveedores(List<Proveedor> proveedores) {
        this.proveedores = proveedores;
    }
    
    public ABCEnum getTipoAlta() {
        return tipoAlta;
    }

    public ABCEnum getTipoBaja() {
        return tipoBaja;
    }

    public ABCEnum getTipoCambio() {
        return tipoCambio;
    }
    
    
    
}
