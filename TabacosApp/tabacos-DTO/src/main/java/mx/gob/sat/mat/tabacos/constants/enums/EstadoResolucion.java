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
public enum EstadoResolucion {

    SOLICITADO("1", "SOLICITADO"),
    AUTORIZADO("2", "AUTORIZADO"),
    RECHAZADO("3", "RECHAZADO");

    private final String value;
    private final String descripcion;

    private EstadoResolucion(String value, String descripcion) {
        this.value = value;
        this.descripcion = descripcion;
    }

    public String getValue() {
        return value;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
