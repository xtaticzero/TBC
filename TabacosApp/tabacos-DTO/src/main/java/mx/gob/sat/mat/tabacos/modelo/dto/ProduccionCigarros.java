package mx.gob.sat.mat.tabacos.modelo.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Ing Salvador Pocteco Saldaña
 */
public class ProduccionCigarros implements Serializable{
    private static final long serialVersionUID = -2168465604902650570L;
    
    private Long idProdCigarro;
    private Long idMarca;
    private Long idPaisOrigen;
    private String descPaisOrigen;
    private Long idPlantaProd;
    private Long idTipoRetro;
    private Integer cantidadCigarros;
    private Integer cantidadProd;
    private Integer lineaProd;
    private String descMaquinaProd;
    private String loteProduccion;
    private Date fechImportacion;
    private Date fechProduccion;
    
    private String numLote;

    public Long getIdProdCigarro() {
        return idProdCigarro;
    }

    public void setIdProdCigarro(Long idProdCigarro) {
        this.idProdCigarro = idProdCigarro;
    }

    public Long getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(Long idMarca) {
        this.idMarca = idMarca;
    }

    public Long getIdPaisOrigen() {
        return idPaisOrigen;
    }

    public void setIdPaisOrigen(Long idPaisOrigen) {
        this.idPaisOrigen = idPaisOrigen;
    }

    public String getDescPaisOrigen() {
        return descPaisOrigen;
    }

    public void setDescPaisOrigen(String descPaisOrigen) {
        this.descPaisOrigen = descPaisOrigen;
    }

    public Long getIdPlantaProd() {
        return idPlantaProd;
    }

    public void setIdPlantaProd(Long idPlantaProd) {
        this.idPlantaProd = idPlantaProd;
    }

    public Long getIdTipoRetro() {
        return idTipoRetro;
    }

    public void setIdTipoRetro(Long idTipoRetro) {
        this.idTipoRetro = idTipoRetro;
    }

    public Integer getCantidadCigarros() {
        return cantidadCigarros;
    }

    public void setCantidadCigarros(Integer cantidadCigarros) {
        this.cantidadCigarros = cantidadCigarros;
    }

    public Integer getCantidadProd() {
        return cantidadProd;
    }

    public void setCantidadProd(Integer cantidadProd) {
        this.cantidadProd = cantidadProd;
    }

    public Integer getLineaProd() {
        return lineaProd;
    }

    public void setLineaProd(Integer lineaProd) {
        this.lineaProd = lineaProd;
    }

    public String getDescMaquinaProd() {
        return descMaquinaProd;
    }

    public void setDescMaquinaProd(String descMaquinaProd) {
        this.descMaquinaProd = descMaquinaProd;
    }

    public String getLoteProduccion() {
        return loteProduccion;
    }

    public void setLoteProduccion(String loteProduccion) {
        this.loteProduccion = loteProduccion;
    }

    public Date getFechImportacion() {
        return (fechImportacion != null) ? (Date)fechImportacion.clone() : null;
    }

    public void setFechImportacion(Date fechImportacion) {
        this.fechImportacion = (fechImportacion != null) ? (Date)fechImportacion.clone() : null;
    }

    public Date getFechProduccion() {
        return (fechProduccion != null) ? (Date)fechProduccion.clone() : null;
    }

    public void setFechProduccion(Date fechProduccion) {
        this.fechProduccion = (fechProduccion != null) ? (Date)fechProduccion.clone() : null;
    }

    /**
     * @return the numLote
     */
    public String getNumLote() {
        return numLote;
    }

    /**
     * @param numLote the numLote to set
     */
    public void setNumLote(String numLote) {
        this.numLote = numLote;
    }
    
    
    
}
