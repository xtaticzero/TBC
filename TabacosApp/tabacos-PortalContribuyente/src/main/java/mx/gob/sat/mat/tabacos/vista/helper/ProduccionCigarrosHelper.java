package mx.gob.sat.mat.tabacos.vista.helper;

import java.io.Serializable;
import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dto.PaisOrigen;
import mx.gob.sat.mat.tabacos.modelo.dto.ProduccionCigarros;
import mx.gob.sat.mat.tabacos.modelo.dto.Proveedor;
import mx.gob.sat.mat.tabacos.modelo.dto.Tabacalera;
import mx.gob.sat.mat.tabacos.modelo.dto.tipo.TipoContribuyente;

/**
 *
 * @author Ing Salvador Pocteco Saldaña
 */
public class ProduccionCigarrosHelper implements Serializable{
    private static final long serialVersionUID = -3788690158274044663L;
    
    private Long     tipoContribuyente;
    private Long     cantidadCodigos;
    private PaisOrigen  origen;
    private Tabacalera  tabacalera;
    private Proveedor   proveedor;
    private ProduccionCigarros produccion;
    
    private List<Tabacalera> lstContribuyente;
    private List<Proveedor> lstProveedores;
    private List<PaisOrigen> lstPais;
    private List<TipoContribuyente> lstTipoContrib;
    private List<ProduccionCigarros> lstProduccion;

    public ProduccionCigarrosHelper() {
        this.origen = new PaisOrigen();
        this.tabacalera = new Tabacalera();
        this.proveedor = new Proveedor();
        this.produccion = new ProduccionCigarros();
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

    public ProduccionCigarros getProduccion() {
        return produccion;
    }

    public void setProduccion(ProduccionCigarros produccion) {
        this.produccion = produccion;
    }

    public List<ProduccionCigarros> getLstProduccion() {
        return lstProduccion;
    }

    public void setLstProduccion(List<ProduccionCigarros> lstProduccion) {
        this.lstProduccion = lstProduccion;
    }
    
    
}
