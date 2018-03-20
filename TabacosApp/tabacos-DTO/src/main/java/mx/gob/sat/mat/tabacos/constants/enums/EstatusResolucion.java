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
public enum EstatusResolucion {
    SOLICITADO(1, "SOLICITADO"),
    AUTORIZADO(2, "AUTORIZADO"),
    RECHAZADO(3, "RECHAZADO"),
    PROCESADO(4, "PROCESADO"),
    GENERADO(5, "GENERADO");
    private Integer key;
    private String value;

    private EstatusResolucion(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public static EstatusResolucion parse(Integer key) {
        EstatusResolucion estatusResolucion = null;

        for (EstatusResolucion item : EstatusResolucion.values()) {
            if (item.getKey().equals(key)) {
                estatusResolucion = item;
                break;
            }
        }

        if (estatusResolucion == null) {
            estatusResolucion = EstatusResolucion.SOLICITADO;
        }

        return estatusResolucion;
    }

    /**
     * *** Getters & Setters ****
     * @return 
     */
    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
