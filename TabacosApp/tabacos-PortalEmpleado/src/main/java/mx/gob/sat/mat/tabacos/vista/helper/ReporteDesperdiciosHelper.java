package mx.gob.sat.mat.tabacos.vista.helper;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Ing Salvador Pocteco Saldaña
 */
public class ReporteDesperdiciosHelper implements Serializable{
    private static final long serialVersionUID = 2577711478442394035L;
    
    private boolean chkProducto;
    private boolean chkPlantaProd;
    private boolean chkCantidadProd;
    private boolean chkOrigen;
    private boolean chkMaquinaProd;
    private boolean chkLoteProd;
    private boolean chkFechaHora;
    private boolean chkCantidadDestruccion;
    private boolean chkNumeroRegistro;

    private List<String> listTabacaleraXRfc;
    
    public boolean isChkProducto() {
        return chkProducto;
    }

    public void setChkProducto(boolean chkProducto) {
        this.chkProducto = chkProducto;
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

    public boolean isChkOrigen() {
        return chkOrigen;
    }

    public void setChkOrigen(boolean chkOrigen) {
        this.chkOrigen = chkOrigen;
    }

    public boolean isChkMaquinaProd() {
        return chkMaquinaProd;
    }

    public void setChkMaquinaProd(boolean chkMaquinaProd) {
        this.chkMaquinaProd = chkMaquinaProd;
    }

    public boolean isChkLoteProd() {
        return chkLoteProd;
    }

    public void setChkLoteProd(boolean chkLoteProd) {
        this.chkLoteProd = chkLoteProd;
    }

    public boolean isChkFechaHora() {
        return chkFechaHora;
    }

    public void setChkFechaHora(boolean chkFechaHora) {
        this.chkFechaHora = chkFechaHora;
    }

    public boolean isChkCantidadDestruccion() {
        return chkCantidadDestruccion;
    }

    public void setChkCantidadDestruccion(boolean chkCantidadDestruccion) {
        this.chkCantidadDestruccion = chkCantidadDestruccion;
    }

    public boolean isChkNumeroRegistro() {
        return chkNumeroRegistro;
    }

    public void setChkNumeroRegistro(boolean chkNumeroRegistro) {
        this.chkNumeroRegistro = chkNumeroRegistro;
    }

    public List<String> getListTabacaleraXRfc() {
        return listTabacaleraXRfc;
    }

    public void setListTabacaleraXRfc(List<String> listTabacaleraXRfc) {
        this.listTabacaleraXRfc = listTabacaleraXRfc;
    }

    @Override
    public String toString() {
        return "ReporteDesperdiciosHelper{" + "chkProducto=" + chkProducto + ", chkPlantaProd=" + chkPlantaProd + ", chkCantidadProd=" + chkCantidadProd + ", chkOrigen=" + chkOrigen + ", chkMaquinaProd=" + chkMaquinaProd + ", chkLoteProd=" + chkLoteProd + ", chkFechaHora=" + chkFechaHora + ", chkCantidadDestruccion=" + chkCantidadDestruccion + ", chkNumeroRegistro=" + chkNumeroRegistro + '}';
    }
    
}
