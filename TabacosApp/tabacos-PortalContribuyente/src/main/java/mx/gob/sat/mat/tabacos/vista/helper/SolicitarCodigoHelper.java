/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.vista.helper;

import java.io.Serializable;
import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dto.PaisOrigen;
import mx.gob.sat.mat.tabacos.modelo.dto.Proveedor;
import mx.gob.sat.mat.tabacos.modelo.dto.Tabacalera;
import mx.gob.sat.mat.tabacos.modelo.dto.tipo.TipoContribuyente;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
public class SolicitarCodigoHelper implements Serializable {
    private Long     tipoContribuyente;
    private Long     cantidadCodigos;
    private PaisOrigen  origen;
    private Tabacalera  tabacalera;
    private Proveedor   proveedor;
    
    private List<Tabacalera> lstContribuyente;
    private List<Proveedor> lstProveedores;
    private List<PaisOrigen> lstPais;
    private List<TipoContribuyente> lstTipoContrib;

    public SolicitarCodigoHelper() {
        this.origen = new PaisOrigen();
        this.tabacalera = new Tabacalera();
        this.proveedor = new Proveedor();
    }
    
    public Long getTipoContribuyente() {
        return tipoContribuyente;
    }

    public void setTipoContribuyente(Long tipoContribuyente) {
        this.tipoContribuyente = tipoContribuyente;
    }

    public Long getCantidadCodigos() {
        return cantidadCodigos;
    }

    public void setCantidadCodigos(Long cantidadCodigos) {
        this.cantidadCodigos = cantidadCodigos;
    }

    public List<Tabacalera> getLstContribuyente() {
        return lstContribuyente;
    }

    public void setLstContribuyente(List<Tabacalera> lstContribuyente) {
        this.lstContribuyente = lstContribuyente;
    }

    public List<Proveedor> getLstProveedores() {
        return lstProveedores;
    }

    public void setLstProveedores(List<Proveedor> lstProveedores) {
        this.lstProveedores = lstProveedores;
    }

    public PaisOrigen getOrigen() {
        return origen;
    }

    public void setOrigen(PaisOrigen origen) {
        this.origen = origen;
    }

    public List<PaisOrigen> getLstPais() {
        return lstPais;
    }

    public void setLstPais(List<PaisOrigen> lstPais) {
        this.lstPais = lstPais;
    }

    public List<TipoContribuyente> getLstTipoContrib() {
        return lstTipoContrib;
    }

    public void setLstTipoContrib(List<TipoContribuyente> lstTipoContrib) {
        this.lstTipoContrib = lstTipoContrib;
    }

    public Tabacalera getTabacalera() {
        return tabacalera;
    }

    public void setTabacalera(Tabacalera tabacalera) {
        this.tabacalera = tabacalera;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }
    
    
}
