/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.constants.enums;

/**
 *
 * @author Ing. Gregorio Serapio Ramirez
 */
public enum RetroFalsoNoValido {
    FALSO(1, "Falso"),
    NO_VALIDO(2, "No válido");

    private Integer key;
    private String descripcion;

    private RetroFalsoNoValido(Integer key, String descripcion) {
        this.key = key;
        this.descripcion = descripcion;
    }

    public static RetroFalsoNoValido parse(Integer key) {
        RetroFalsoNoValido tipo = null;

        for (RetroFalsoNoValido item : RetroFalsoNoValido.values()) {
            if (item.getKey().equals(key)) {
                tipo = item;
                break;
            }
        }

        if (tipo == null) {
            tipo = RetroFalsoNoValido.FALSO;
        }

        return tipo;
    }

    /**
     * *** Getters & Setters
     *
     * @return ****
     */
    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
