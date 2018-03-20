/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.vista.helper;

import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dto.RangoFolio;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
public class DesperdiciosHelper {
    
    private boolean deshabilitaMarca;
    private boolean deshabilitaPlanta;
    private boolean deshabilitaPais;
    private boolean deshabilitaOrigen;
    private boolean deshabilitaMaquina;
    private boolean deshabilitaLote;
    private boolean deshabilitaCantidad;
    private boolean deshabilitaFecImporta;
    private boolean deshabilitaFecProduccion;
    private boolean deshabilitaCargaArchivo;
    private boolean deshabilitaBtnCarga;
    private boolean deshabilitaBtnGuardar;
    private boolean deshabilitaBtnValidarProd;
    private boolean pnlAcuse;
    private boolean pnlPrincipal;

    private List<RangoFolio> rangoFoliosList;
    private transient UploadedFile archivoFolios;
    private List<RangoFolio> rangoFoliosListAux;
    private List<RangoFolio> rangoFoliosListTemp;

    private String idAcuseRecibo;
    private Long cantidadSolicitada;
    private String fechaAcuse;
    private String nombreArchivo;
    private String msgErrorArchivo;
    private String msgExitoArchivo;
    private String nombrePais;

    public boolean isDeshabilitaMarca() {
        return deshabilitaMarca;
    }

    public void setDeshabilitaMarca(boolean deshabilitaMarca) {
        this.deshabilitaMarca = deshabilitaMarca;
    }

    public boolean isDeshabilitaPlanta() {
        return deshabilitaPlanta;
    }

    public void setDeshabilitaPlanta(boolean deshabilitaPlanta) {
        this.deshabilitaPlanta = deshabilitaPlanta;
    }

    public boolean isDeshabilitaPais() {
        return deshabilitaPais;
    }

    public void setDeshabilitaPais(boolean deshabilitaPais) {
        this.deshabilitaPais = deshabilitaPais;
    }

    public boolean isDeshabilitaOrigen() {
        return deshabilitaOrigen;
    }

    public void setDeshabilitaOrigen(boolean deshabilitaOrigen) {
        this.deshabilitaOrigen = deshabilitaOrigen;
    }

    public boolean isDeshabilitaMaquina() {
        return deshabilitaMaquina;
    }

    public void setDeshabilitaMaquina(boolean deshabilitaMaquina) {
        this.deshabilitaMaquina = deshabilitaMaquina;
    }

    public boolean isDeshabilitaLote() {
        return deshabilitaLote;
    }

    public void setDeshabilitaLote(boolean deshabilitaLote) {
        this.deshabilitaLote = deshabilitaLote;
    }

    public boolean isDeshabilitaCantidad() {
        return deshabilitaCantidad;
    }

    public void setDeshabilitaCantidad(boolean deshabilitaCantidad) {
        this.deshabilitaCantidad = deshabilitaCantidad;
    }

    public boolean isDeshabilitaFecImporta() {
        return deshabilitaFecImporta;
    }

    public void setDeshabilitaFecImporta(boolean deshabilitaFecImporta) {
        this.deshabilitaFecImporta = deshabilitaFecImporta;
    }

    public boolean isDeshabilitaFecProduccion() {
        return deshabilitaFecProduccion;
    }

    public void setDeshabilitaFecProduccion(boolean deshabilitaFecProduccion) {
        this.deshabilitaFecProduccion = deshabilitaFecProduccion;
    }

    public boolean isDeshabilitaCargaArchivo() {
        return deshabilitaCargaArchivo;
    }

    public void setDeshabilitaCargaArchivo(boolean deshabilitaCargaArchivo) {
        this.deshabilitaCargaArchivo = deshabilitaCargaArchivo;
    }

    public boolean isDeshabilitaBtnCarga() {
        return deshabilitaBtnCarga;
    }

    public void setDeshabilitaBtnCarga(boolean deshabilitaBtnCarga) {
        this.deshabilitaBtnCarga = deshabilitaBtnCarga;
    }

    public boolean isDeshabilitaBtnGuardar() {
        return deshabilitaBtnGuardar;
    }

    public void setDeshabilitaBtnGuardar(boolean deshabilitaBtnGuardar) {
        this.deshabilitaBtnGuardar = deshabilitaBtnGuardar;
    }

    public boolean isDeshabilitaBtnValidarProd() {
        return deshabilitaBtnValidarProd;
    }

    public void setDeshabilitaBtnValidarProd(boolean deshabilitaBtnValidarProd) {
        this.deshabilitaBtnValidarProd = deshabilitaBtnValidarProd;
    }

    public boolean isPnlAcuse() {
        return pnlAcuse;
    }

    public void setPnlAcuse(boolean pnlAcuse) {
        this.pnlAcuse = pnlAcuse;
    }

    public boolean isPnlPrincipal() {
        return pnlPrincipal;
    }

    public void setPnlPrincipal(boolean pnlPrincipal) {
        this.pnlPrincipal = pnlPrincipal;
    }

    public List<RangoFolio> getRangoFoliosList() {
        return rangoFoliosList;
    }

    public void setRangoFoliosList(List<RangoFolio> rangoFoliosList) {
        this.rangoFoliosList = rangoFoliosList;
    }

    public UploadedFile getArchivoFolios() {
        return archivoFolios;
    }

    public void setArchivoFolios(UploadedFile archivoFolios) {
        this.archivoFolios = archivoFolios;
    }

    public List<RangoFolio> getRangoFoliosListAux() {
        return rangoFoliosListAux;
    }

    public void setRangoFoliosListAux(List<RangoFolio> rangoFoliosListAux) {
        this.rangoFoliosListAux = rangoFoliosListAux;
    }

    public List<RangoFolio> getRangoFoliosListTemp() {
        return rangoFoliosListTemp;
    }

    public void setRangoFoliosListTemp(List<RangoFolio> rangoFoliosListTemp) {
        this.rangoFoliosListTemp = rangoFoliosListTemp;
    }

    public String getIdAcuseRecibo() {
        return idAcuseRecibo;
    }

    public void setIdAcuseRecibo(String idAcuseRecibo) {
        this.idAcuseRecibo = idAcuseRecibo;
    }

    public Long getCantidadSolicitada() {
        return cantidadSolicitada;
    }

    public void setCantidadSolicitada(Long cantidadSolicitada) {
        this.cantidadSolicitada = cantidadSolicitada;
    }

    public String getFechaAcuse() {
        return fechaAcuse;
    }

    public void setFechaAcuse(String fechaAcuse) {
        this.fechaAcuse = fechaAcuse;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getMsgErrorArchivo() {
        return msgErrorArchivo;
    }

    public void setMsgErrorArchivo(String msgErrorArchivo) {
        this.msgErrorArchivo = msgErrorArchivo;
    }

    public String getMsgExitoArchivo() {
        return msgExitoArchivo;
    }

    public void setMsgExitoArchivo(String msgExitoArchivo) {
        this.msgExitoArchivo = msgExitoArchivo;
    }

    public String getNombrePais() {
        return nombrePais;
    }

    public void setNombrePais(String nombrePais) {
        this.nombrePais = nombrePais;
    }
}
