/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.vista.helper;

import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dto.PaisOrigen;
import mx.gob.sat.mat.tabacos.modelo.dto.Planta;
import mx.gob.sat.mat.tabacos.modelo.dto.ProduccionCigarros;
import mx.gob.sat.mat.tabacos.modelo.dto.Tabacalera;
import mx.gob.sat.mat.tabacos.modelo.dto.marca.Marcas;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
public class ProduccionHelper {

    private ProduccionCigarros produccion;
    private List<Marcas> marcasList;
    private List<Planta> plantasList;
    private List<PaisOrigen> paisList;
    private List<PaisOrigen> origenList;

    private ProduccionCigarrosHelper produccionCigarrosHelper;
    private FirmaFormHelper firmaFormHelper;
    private Tabacalera tabacalera;

    private Long opcionMarca;
    private Long opcionPlanta;
    private Long opcionPais;
    private Long opcionOrigen;
    
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

    public ProduccionCigarros getProduccion() {
        return produccion;
    }

    public void setProduccion(ProduccionCigarros produccion) {
        this.produccion = produccion;
    }

    public List<Marcas> getMarcasList() {
        return marcasList;
    }

    public void setMarcasList(List<Marcas> marcasList) {
        this.marcasList = marcasList;
    }

    public List<Planta> getPlantasList() {
        return plantasList;
    }

    public void setPlantasList(List<Planta> plantasList) {
        this.plantasList = plantasList;
    }

    public List<PaisOrigen> getPaisList() {
        return paisList;
    }

    public void setPaisList(List<PaisOrigen> paisList) {
        this.paisList = paisList;
    }

    public List<PaisOrigen> getOrigenList() {
        return origenList;
    }

    public void setOrigenList(List<PaisOrigen> origenList) {
        this.origenList = origenList;
    }

    public ProduccionCigarrosHelper getProduccionCigarrosHelper() {
        return produccionCigarrosHelper;
    }

    public void setProduccionCigarrosHelper(ProduccionCigarrosHelper produccionCigarrosHelper) {
        this.produccionCigarrosHelper = produccionCigarrosHelper;
    }

    public FirmaFormHelper getFirmaFormHelper() {
        return firmaFormHelper;
    }

    public void setFirmaFormHelper(FirmaFormHelper firmaFormHelper) {
        this.firmaFormHelper = firmaFormHelper;
    }

    public Tabacalera getTabacalera() {
        return tabacalera;
    }

    public void setTabacalera(Tabacalera tabacalera) {
        this.tabacalera = tabacalera;
    }

    public Long getOpcionMarca() {
        return opcionMarca;
    }

    public void setOpcionMarca(Long opcionMarca) {
        this.opcionMarca = opcionMarca;
    }

    public Long getOpcionPlanta() {
        return opcionPlanta;
    }

    public void setOpcionPlanta(Long opcionPlanta) {
        this.opcionPlanta = opcionPlanta;
    }

    public Long getOpcionPais() {
        return opcionPais;
    }

    public void setOpcionPais(Long opcionPais) {
        this.opcionPais = opcionPais;
    }

    public Long getOpcionOrigen() {
        return opcionOrigen;
    }

    public void setOpcionOrigen(Long opcionOrigen) {
        this.opcionOrigen = opcionOrigen;
    }

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
    
}
