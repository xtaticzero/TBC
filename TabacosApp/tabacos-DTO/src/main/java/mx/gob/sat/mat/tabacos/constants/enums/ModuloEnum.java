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
public enum ModuloEnum {

    MODULO_TBC("Tabacos", "TBC", "044");
    
    private String descripcion;
    private String iniciales;
    private String claveDominio;

    private ModuloEnum(String descripcion, String iniciales, String claveDominio) {
        this.descripcion = descripcion;
        this.iniciales = iniciales;
        this.claveDominio = claveDominio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIniciales() {
        return iniciales;
    }

    public void setIniciales(String iniciales) {
        this.iniciales = iniciales;
    }

    public String getClaveDominio() {
        return claveDominio;
    }

    public void setClaveDominio(String claveDominio) {
        this.claveDominio = claveDominio;
    }

}
