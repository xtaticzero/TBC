/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.vista.reportes;

import java.util.Date;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
public class ReportesParametrosBase {
    private String rfc;
    private Date fechainicio;
    private Date fechafin; 
    private String seleccion;

    public ReportesParametrosBase() {
        this.rfc = "";
        this.fechainicio = null;
        this.fechafin = null;
        this.seleccion = "";
    }    
    
    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public Date getFechainicio() {
        return (this.fechainicio == null) ? null : 
                (Date)this.fechainicio.clone();
    }

    public void setFechainicio(Date fechainicio) {
        this.fechainicio = fechainicio == null ? null : 
                (Date)fechainicio.clone();
    }

    public Date getFechafin() {
        return (this.fechafin == null) ? null : 
                (Date) this.fechafin.clone();
    }

    public void setFechafin(Date fechafin) {
        this.fechafin = (fechafin == null) ? null : 
                (Date) fechafin.clone();
    }

    public String getSeleccion() {
        return seleccion;
    }

    public void setSeleccion(String seleccion) {
        this.seleccion = seleccion;
    }
}
