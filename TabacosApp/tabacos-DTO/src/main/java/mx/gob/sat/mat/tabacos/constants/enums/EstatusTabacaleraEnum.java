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
public enum EstatusTabacaleraEnum {

    ACTIVO(1L, "ACTIVO"),
    BAJA(2L, "BAJA"),
    SOLICITADO(3L, "SOLICITADO"),
    CANCELADO(4L, "CANCELADO");
    
    private final Long key;
    private final String value;

    private EstatusTabacaleraEnum(Long key, String value) {
        this.key = key;
        this.value = value;
    }

    public Long getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
