package mx.gob.sat.mat.tabacos.modelo.dto.solicitud;

import mx.gob.sat.mat.tabacos.modelo.dto.archivo.Archivo;
import java.util.Date;
import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dto.SolicitudDTO;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
public class AutorizacionResol extends SolicitudDTO {

    private static final long serialVersionUID = 41507893884333575L;

    private String rfcTavacalera;
    private String razonSocial;
    private Date fechaResolucion;
    private Long codigosAutorizados;
    private Long folioInicial;
    private Long folioFinal;
    private List<Archivo> lstArchivos;

    public String getRfcTavacalera() {
        return rfcTavacalera;
    }

    public void setRfcTavacalera(String rfcTavacalera) {
        this.rfcTavacalera = rfcTavacalera.toUpperCase();
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public Date getFechaResolucion() {
        return (fechaResolucion != null) ? (Date) fechaResolucion.clone() : null;
    }

    public void setFechaResolucion(Date fechaResolucion) {
        this.fechaResolucion = (fechaResolucion != null) ? (Date) fechaResolucion.clone() : null;
    }

    public Long getCodigosAutorizados() {
        return codigosAutorizados;
    }

    public void setCodigosAutorizados(Long codigosAutorizados) {
        this.codigosAutorizados = codigosAutorizados;
    }

    public Long getFolioInicial() {
        return folioInicial;
    }

    public void setFolioInicial(Long folioInicial) {
        this.folioInicial = folioInicial;
    }

    public Long getFolioFinal() {
        return folioFinal;
    }

    public void setFolioFinal(Long folioFinal) {
        this.folioFinal = folioFinal;
    }

    public List<Archivo> getLstArchivos() {
        return lstArchivos;
    }

    public void setLstArchivos(List<Archivo> lstArchivos) {
        this.lstArchivos = lstArchivos;
    }

}
