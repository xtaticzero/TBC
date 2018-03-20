package mx.gob.sat.mat.tabacos.vista.helper;

import java.io.Serializable;
import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dto.Proveedor;
import mx.gob.sat.mat.tabacos.modelo.dto.Tabacalera;

/**
 *
 * @author Ing Salvador Pocteco Saldaña
 */
public class ReporteProduccionHelper implements Serializable{
    private static final long serialVersionUID = -761543640483706804L;
    
    private boolean chkLoteProd;
    private boolean chkPlantaProd;
    private boolean chkCantidadProd;
    private boolean chkMaquinaProd;
    private boolean chkOrigen;
    private boolean chkLineaProd;

    private List<Tabacalera> listaTabacaleras;
    private List<Proveedor> listaProveedores;

    private String variableReporte;
    
    public boolean isChkLoteProd() {
        return chkLoteProd;
    }

    public void setChkLoteProd(boolean chkLoteProd) {
        this.chkLoteProd = chkLoteProd;
    }

    public boolean isChkPlantaProd() {
        return chkPlantaProd;
    }

    public void setChkPlantaProd(boolean chkPlantaProd) {
        this.chkPlantaProd = chkPlantaProd;
    }

    public boolean isChkCantidadProd() {
        return chkCantidadProd;
    }

    public void setChkCantidadProd(boolean chkCantidadProd) {
        this.chkCantidadProd = chkCantidadProd;
    }

    public boolean isChkMaquinaProd() {
        return chkMaquinaProd;
    }

    public void setChkMaquinaProd(boolean chkMaquinaProd) {
        this.chkMaquinaProd = chkMaquinaProd;
    }

    public boolean isChkOrigen() {
        return chkOrigen;
    }

    public void setChkOrigen(boolean chkOrigen) {
        this.chkOrigen = chkOrigen;
    }

    public boolean isChkLineaProd() {
        return chkLineaProd;
    }

    public void setChkLineaProd(boolean chkLineaProd) {
        this.chkLineaProd = chkLineaProd;
    }

    public List<Tabacalera> getListaTabacaleras() {
        return listaTabacaleras;
    }

    public void setListaTabacaleras(List<Tabacalera> listaTabacaleras) {
        this.listaTabacaleras = listaTabacaleras;
    }

    public List<Proveedor> getListaProveedores() {
        return listaProveedores;
    }

    public void setListaProveedores(List<Proveedor> listaProveedores) {
        this.listaProveedores = listaProveedores;
    }

    public String getVariableReporte() {
        return variableReporte;
    }

    public void setVariableReporte(String variableReporte) {
        this.variableReporte = variableReporte;
    }

    @Override
    public String toString() {
        return "ReporteProduccionHelper{" + "chkLoteProd=" + chkLoteProd + ", chkPlantaProd=" + chkPlantaProd + ", chkCantidadProd=" + chkCantidadProd + ", chkMaquinaProd=" + chkMaquinaProd + ", chkOrigen=" + chkOrigen + ", chkLineaProd=" + chkLineaProd + ", listaTabacaleras=" + listaTabacaleras + ", listaProveedores=" + listaProveedores + '}';
    }

   
}
