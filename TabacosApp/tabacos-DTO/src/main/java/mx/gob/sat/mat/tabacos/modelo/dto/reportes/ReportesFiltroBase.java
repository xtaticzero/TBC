/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.modelo.dto.reportes;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author MTI Agustin Romero Mata
 */
public class ReportesFiltroBase implements Serializable {

    private static final long serialVersionUID = 4627791797598969102L;
    private String rfc;
    private Date fechaInicio;
    private Date fechaFin;
    private int movimiento;
    private String marca;
    private String subMarca;
    private int tipoCodigo;
    private String razonSocial;
    private static final int MOV1 = 1;
    private static final int MOV2 = 2;
    private static final int MOV3 = 3;
    private static final String MOV_ALTA = "ALTA";
    private static final String MOV_BAJA = "BAJA";
    private static final String MOV_CAMBIO = "CAMBIO";

    /**
     *
     */
    public ReportesFiltroBase() {
        this.rfc = "";
        this.fechaInicio = null;
        this.fechaFin = null;
        this.movimiento = 0;
        this.marca = "";
        this.subMarca = "";
        this.tipoCodigo = 0;
        this.razonSocial = "";
    }

    /**
     *
     * @param item
     */
    public ReportesFiltroBase(final ReportesFiltroBase item) {
        this.rfc = item.getRfc();
        this.fechaInicio = item.getFechaInicio();
        this.fechaFin = item.getFechaFin();
        this.movimiento = item.getMovimiento();
        this.marca = item.getMarca();
        this.subMarca = item.getSubMarca();
        this.tipoCodigo = item.getTipoCodigo();
    }

    /**
     *
     * @return
     */
    public String getRfc() {
        return this.rfc;
    }

    public void setRfc(final String rfc) {
        this.rfc = rfc.toUpperCase();
    }

    public Date getFechaInicio() {
        return (this.fechaInicio == null) ? null : (Date) this.fechaInicio.clone();
    }

    public void setFechaInicio(final Date fechaInicio) {
        this.fechaInicio = fechaInicio == null ? null : (Date) fechaInicio.clone();
    }

    public Date getFechaFin() {
        return (this.fechaFin == null) ? null : (Date) this.fechaFin.clone();
    }

    public void setFechaFin(final Date fechaFin) {
        this.fechaFin = (fechaFin == null) ? null : (Date) fechaFin.clone();
    }

    public int getMovimiento() {
        return this.movimiento;
    }

    public void setMovimiento(int movimiento) {
        this.movimiento = movimiento;
    }

    public final String getMarca() {
        return this.marca;
    }

    public void setMarca(final String marca) {
        this.marca = marca;
    }

    public final String getSubMarca() {
        return this.subMarca;
    }

    public void setSubMarca(final String subMarca) {
        this.subMarca = subMarca;
    }

    public final int getTipoCodigo() {
        return this.tipoCodigo;
    }

    public void setTipoCodigo(int tipoCodigo) {
        this.tipoCodigo = tipoCodigo;
    }

    public String convertDateToString(final Date fecha) {
        String retorno = "";

        if (fecha != null) {
            DateFormat convert = new SimpleDateFormat("dd/MM/yyyy");
            retorno = convert.format(fecha);
        } else {
            retorno = "";
        }

        return retorno;
    }

    public String opcionReporte(String tipoReporte) {
        String retorno = "";
        switch (this.movimiento) {
            case MOV1:
                retorno = MOV_ALTA + tipoReporte;
                break;
            case MOV2:
                retorno = MOV_CAMBIO + tipoReporte;
                break;
            case MOV3:
                retorno = MOV_BAJA + tipoReporte;
                break;
            default:
                break;
        }

        return retorno;
    }

    public final String getRazonSocial() {
        return this.razonSocial;
    }

    public void setRazonSocial(final String razonSocial) {
        this.razonSocial = razonSocial;
    }
}
