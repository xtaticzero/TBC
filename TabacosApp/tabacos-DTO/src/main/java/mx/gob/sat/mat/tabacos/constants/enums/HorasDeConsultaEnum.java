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
public enum HorasDeConsultaEnum {
    INICIO_DE_DIA(1L, "00:00"),
    FIN_DE_DIA(2L, "23:59");
    
    private Long key;
    private String descripcion;

    private HorasDeConsultaEnum(Long key, String descripcion) {
        this.key = key;
        this.descripcion = descripcion;
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
