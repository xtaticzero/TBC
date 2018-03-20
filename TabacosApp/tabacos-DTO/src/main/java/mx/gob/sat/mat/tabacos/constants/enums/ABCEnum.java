/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.constants.enums;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
public enum ABCEnum {

    ALTA(1, "Alta"),
    BAJA(2, "Baja"),
    CAMBIO(3, "Cambio");

    /**
     * Identificador
     */
    private final int id;

    /**
     * Descripci&oacute;n
     */
    private final String descripcion;

    /**
     * constructor ABCEnum
     *
     * @param descripcion del elemento
     * @param id identificador del elemento
     */
    ABCEnum(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    /**
     * Regresa el identificador del enum
     *
     * @return identificador.
     */
    public int getId() {
        return id;
    }

    /**
     * Regresa la descipcion del enum
     *
     * @return
     */
    public String getDescripcion() {
        return descripcion;
    }
}
