/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.modelo.dto.reportes;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
public class ReporteContribuyenteDTO implements Serializable {

    private static final long serialVersionUID = 7526442613115142376L;

    private String rfc;
    private Date fechaMovimiento;

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public Date getFechaMovimiento() {
        return (fechaMovimiento != null) ? (Date) fechaMovimiento.clone() : null;
    }

    public void setFechaMovimiento(Date fechaMovimiento) {
        this.fechaMovimiento = (fechaMovimiento != null) ? (Date) fechaMovimiento.clone() : null;
    }

}
