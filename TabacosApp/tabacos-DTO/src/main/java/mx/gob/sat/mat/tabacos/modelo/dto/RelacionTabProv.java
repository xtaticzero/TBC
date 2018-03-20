package mx.gob.sat.mat.tabacos.modelo.dto;

import java.io.Serializable;
import java.util.Date;
import mx.gob.sat.mat.tabacos.constants.enums.EstatusEnum;

/**
 *
 * @author Ing. Gregorio Serapio Ramirez
 */
public class RelacionTabProv implements Serializable {

    private static final long serialVersionUID = -4235328932635274479L;
    private Long idTbcProv;
    private Long idTabacalera;
    private Long idProveedor;
    private Date fecRegistro;
    private Date fecBaja;
    private Long idEstatus;

    private EstatusEnum estatusEnum = EstatusEnum.ALTA;

    /**
     * @return the idTbcProv
     */
    public Long getIdTbcProv() {
        return idTbcProv;
    }

    /**
     * @param idTbcProv the idTbcProv to set
     */
    public void setIdTbcProv(Long idTbcProv) {
        this.idTbcProv = idTbcProv;
    }

    /**
     * @return the idTabacalera
     */
    public Long getIdTabacalera() {
        return idTabacalera;
    }

    /**
     * @param idTabacalera the idTabacalera to set
     */
    public void setIdTabacalera(Long idTabacalera) {
        this.idTabacalera = idTabacalera;
    }

    /**
     * @return the idProveedor
     */
    public Long getIdProveedor() {
        return idProveedor;
    }

    /**
     * @param idProveedor the idProveedor to set
     */
    public void setIdProveedor(Long idProveedor) {
        this.idProveedor = idProveedor;
    }

    /**
     * @return the fecRegistro
     */
    public Date getFecRegistro() {
        return (fecRegistro != null) ? (Date) fecRegistro.clone() : null;
    }

    /**
     * @param fecRegistro the fecRegistro to set
     */
    public void setFecRegistro(Date fecRegistro) {
        this.fecRegistro = (fecRegistro != null) ? (Date) fecRegistro.clone() : null;
    }

    /**
     * @return the fecBaja
     */
    public Date getFecBaja() {
        return (fecBaja != null) ? (Date) fecBaja.clone() : null;
    }

    /**
     * @param fecBaja the fecBaja to set
     */
    public void setFecBaja(Date fecBaja) {
        this.fecBaja = (fecBaja != null) ? (Date) fecBaja.clone() : null;
    }

    /**
     * @return the idEstatus
     */
    public Long getIdEstatus() {
        return idEstatus;
    }

    /**
     * @param idEstatus the idEstatus to set
     */
    public void setIdEstatus(Long idEstatus) {
        this.idEstatus = idEstatus;
    }

    public EstatusEnum getEstatusEnum() {
        return estatusEnum;
    }

    public void setEstatusEnum(EstatusEnum estatusEnum) {
        this.estatusEnum = estatusEnum;
    }

}
