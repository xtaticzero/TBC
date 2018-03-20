/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.constants.enums;

/**
 *
 * @author Ing Salvador Pocteco Saldaña
 */
public enum EstatusTipoRepLegal {
    LEGAL(1L, "REPRESENTANTE LEGAL"),
    OPERATIVO(2L, "REPRESENTANTE OPERATIVO");
    
    private Long key;
    private String descripcion;

    private EstatusTipoRepLegal(Long key, String descripcion) {
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
