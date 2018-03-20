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
public enum EstadoResolucionEnum {
    SOLICITADO(1L, "SOLICITADO"),
    AUTORIZADO(2L, "AUTORIZADO"),
    RECHAZADO(3L, "RECHAZADO"),
    PROCESADO(4L, "PROCESADO"),
    GENERADO(5L,"GENERADO");

    private Long key;
    private String value;

    private EstadoResolucionEnum(Long key, String value) {
        this.key = key;
        this.value = value;
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    
}
