package mx.gob.sat.mat.tabacos.modelo.dto.codigos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import mx.gob.sat.mat.tabacos.modelo.dto.SolicitudDTO;

public class Codigo extends SolicitudDTO implements Serializable {

    private static final long serialVersionUID = -8757998403377471104L;

    private Long idCodigoFalso;
    private Long idMarca;

    private Date fecMovimiento;
    private String numeroCodigo;
    private String tipoCodigo;
    private String rfc;
    private String folioInicial;
    private String folioFinal;
    private String justificacion;
    private Long idRangoFolio;
    private Date fecRegistro;
    private Date fecCaptura;

    public Codigo() {
        this.fecMovimiento = null;
        this.numeroCodigo = "";
        this.tipoCodigo = "";
    }

    public Date getFecMovimiento() {
        return (fecMovimiento != null) ? (Date) fecMovimiento.clone() : null;
    }

    public void setFecMovimiento(Date fecMovimiento) {
        this.fecMovimiento = (fecMovimiento != null) ? (Date) fecMovimiento.clone() : null;
    }

    public String getNumeroCodigo() {
        return numeroCodigo;
    }

    public void setNumeroCodigo(String numeroCodigo) {
        this.numeroCodigo = numeroCodigo;
    }

    public void setNumeroCodigo() {
        BigDecimal temp = new BigDecimal(folioFinal);
        temp = temp.subtract(new BigDecimal(folioInicial));
        this.numeroCodigo = String.valueOf(temp);
    }

    public String getTipoCodigo() {
        return tipoCodigo;
    }

    public void setTipoCodigo(String tipoCodigo) {
        this.tipoCodigo = tipoCodigo;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getFolioInicial() {
        return folioInicial;
    }

    public void setFolioInicial(String folioInicial) {
        this.folioInicial = folioInicial;
    }

    public String getFolioFinal() {
        return folioFinal;
    }

    public void setFolioFinal(String folioFinal) {
        this.folioFinal = folioFinal;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }

    public Long getIdCodigoFalso() {
        return idCodigoFalso;
    }

    public void setIdCodigoFalso(Long idCodigoFalso) {
        this.idCodigoFalso = idCodigoFalso;
    }

    public Long getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(Long idMarca) {
        this.idMarca = idMarca;
    }

    public Long getIdRangoFolio() {
        return idRangoFolio;
    }

    public void setIdRangoFolio(Long idRangoFolio) {
        this.idRangoFolio = idRangoFolio;
    }

    public Date getFecRegistro() {
        return (fecRegistro != null) ? (Date) fecRegistro.clone() : null;
    }

    public void setFecRegistro(Date fecRegistro) {
        this.fecRegistro = (fecRegistro != null) ? (Date) fecRegistro.clone() : null;
    }

    public Date getFecCaptura() {
        return (fecCaptura != null) ? (Date) fecCaptura.clone() : null;
    }

    public void setFecCaptura(Date fecCaptura) {
        this.fecCaptura = (fecCaptura != null) ? (Date) fecCaptura.clone() : null;
    }

    @Override
    public String toString() {
        return "IDCODIGO =" + idCodigoFalso + ", idMarca = " + idMarca
                + ", fecMovimiento = " + fecMovimiento + ", numeroCodigo = " + numeroCodigo
                + ", tipoCodigo = " + tipoCodigo + ", rfc = " + rfc + ", folioInicial = " + folioInicial
                + ", folioFinal = " + folioFinal + ", justificacion = " + justificacion
                + ", idRangoFolio = " + idRangoFolio + ", fecRegistro = " + fecRegistro + ", fecCaptura = " + fecCaptura;
    }
}
