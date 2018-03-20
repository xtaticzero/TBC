/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.constants.enums;

/**
 *
 * @author emmanuel
 */
public enum OpcionReportesEnum {

    ALTA(1, "Alta"),
    CAMBIO(2, "Cambio"),
    BAJA(3, "Baja");

    private Integer idOpcion;
    private String opcion;

    private OpcionReportesEnum(Integer idOpcion, String opcion) {
        this.idOpcion = idOpcion;
        this.opcion = opcion;
    }

    public Integer getIdOpcion() {
        return idOpcion;
    }

    public String getOpcion() {
        return opcion;
    }

}
