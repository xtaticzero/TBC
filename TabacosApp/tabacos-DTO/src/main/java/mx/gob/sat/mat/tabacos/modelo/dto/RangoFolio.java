package mx.gob.sat.mat.tabacos.modelo.dto;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import mx.gob.sat.mat.tabacos.constants.enums.EstatusEnum;

/**
 *
 * @author MMMF
 */
public class RangoFolio extends SolicitudDTO implements Comparator<RangoFolio>, Comparable<RangoFolio>, Serializable {

    private static final long serialVersionUID = 889743593068543L;

    private Long idRangoFolios;
    private Long folioInicial;
    private Long folioFinal;
    private Integer estado;
    private Long idProdCigarro;
    private Date fechaRetroalimentacion;
    private Long idResolucion;
    private Long idEstatus;

    public RangoFolio() {
    }

    public RangoFolio(Long idRangoFolios, Long folioInicial, Long folioFinal, Long idProdCigarro, Long idResolucion, Long idSolicitud) {
        this.idRangoFolios = idRangoFolios;
        this.folioInicial = folioInicial;
        this.folioFinal = folioFinal;
        this.idProdCigarro = idProdCigarro;
        this.idResolucion = idResolucion;
        setIdSolicitud(idSolicitud);
    }

    public Long getIdRangoFolios() {
        return idRangoFolios;
    }

    public void setIdRangoFolios(Long idRangoFolios) {
        this.idRangoFolios = idRangoFolios;
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

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    /**
     * Compara Rangos.
     *
     * @param rf1
     * @param rf2
     * @return int
     */
    @Override
    public int compare(final RangoFolio rf1, final RangoFolio rf2) {
        int startComparison = compare(rf1.getFolioInicial(), rf2.getFolioInicial());
        return startComparison == 0 ? startComparison : compare(rf1.getFolioFinal(), rf2.getFolioFinal());
    }

    /**
     * Compara RangoFolio.
     *
     * @param rf
     * @return int
     */
    @Override
    public int compareTo(final RangoFolio rf) {
        int startComparison = compare(folioInicial, rf.folioInicial);
        return startComparison == 0 ? startComparison : compare(folioFinal, rf.getFolioFinal());
    }

    /**
     * Compara rangos.
     *
     * @param a
     * @param b
     * @return int
     */
    private static int compare(final long a, final long b) {
        return a < b ? -1 : a > b ? 1 : 0;
    }

    /**
     * Valida si se intersectan los rangos de folios.
     *
     * @param rf1
     * @param rf2
     * @return boolean
     */
    public static boolean intersectan(final RangoFolio rf1, final RangoFolio rf2) {

        if (rf1.getFolioInicial() >= rf2.getFolioInicial() && rf1.getFolioInicial() <= rf2.getFolioFinal()) {
            return Boolean.TRUE;
        }
        if (rf1.getFolioFinal() <= rf2.getFolioFinal() && rf1.getFolioFinal() >= rf2.getFolioInicial()) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    @Override
    public String toString() {
        return "ID = " + idRangoFolios + ", folioInicial = " + folioInicial + ", folioFinal = " + folioFinal
                + ", estado = " + estado + ", idSolicitud = " + getIdSolicitud() + ", idResolucion = " + idResolucion;
    }

    public Long getIdProdCigarro() {
        return idProdCigarro;
    }

    public void setIdProdCigarro(Long idProdCigarro) {
        this.idProdCigarro = idProdCigarro;
    }

    public Date getFechaRetroalimentacion() {
        return (fechaRetroalimentacion != null) ? (Date) fechaRetroalimentacion.clone() : null;
    }

    public void setFechaRetroalimentacion(Date fechaRetroalimentacion) {
        this.fechaRetroalimentacion = (fechaRetroalimentacion != null) ? (Date) fechaRetroalimentacion.clone() : null;
    }

    public Long getIdResolucion() {
        return idResolucion;
    }

    public void setIdResolucion(Long idResolucion) {
        this.idResolucion = idResolucion;
    }

    @Override
    public boolean equals(Object object) {

        boolean resultado;
        if (this == object) {
            return Boolean.TRUE;
        }
        if (!(object instanceof RangoFolio)) {
            resultado = Boolean.FALSE;
        } else {

            final RangoFolio other = (RangoFolio) object;
            resultado = compararDatos(other);
        }
        return resultado;
    }

    private boolean compararDatos(RangoFolio other) {
        boolean resultado = Boolean.TRUE;

        if (!(idRangoFolios == null ? other.idRangoFolios == null : idRangoFolios.equals(other.idRangoFolios))) {
            resultado = Boolean.FALSE;
        }
        if (!(folioInicial == null ? other.folioInicial == null : folioInicial.equals(other.folioInicial))) {
            resultado = Boolean.FALSE;
        }
        if (!(folioFinal == null ? other.folioFinal == null : folioFinal.equals(other.folioFinal))) {
            resultado = Boolean.FALSE;
        }
        if (!(estado.compareTo(other.estado) == 0)) {
            resultado = Boolean.FALSE;
        }
        if (!(idProdCigarro == null ? other.idProdCigarro == null
                : idProdCigarro.equals(other.idProdCigarro))) {
            resultado = Boolean.FALSE;
        }

        return resultado;
    }

    @Override
    public int hashCode() {
        final int primo = 37;
        int result = 1;
        result = primo * result + ((idRangoFolios == null) ? 0 : idRangoFolios.hashCode());
        result = primo * result + ((folioInicial == null) ? 0 : folioInicial.hashCode());
        result = primo * result + ((folioFinal == null) ? 0 : folioFinal.hashCode());
        result = primo * result + estado;
        result = primo * result + ((idProdCigarro == null) ? 0 : idProdCigarro.hashCode());
        return result;
    }

    /**
     * @return the idEstatus
     */
    public Long getIdEstatus() {
        return idEstatus;
    }

    /**
     * @param estatusEnum
     */
    public void setIdEstatus(EstatusEnum estatusEnum) {
        this.idEstatus = estatusEnum.getKey();
    }

}
