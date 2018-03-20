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
public enum DatosSATEnum {

    DATOS_FISCALES("SAT970701NN3", "SERVICIO DE ADMINISTRACIÓN TRIBUTARIA", "AV. HIDALGO 77. COLONIA GUERRERO, DEL. CUAUHTEMOC, C.P. 06300, MEXICO, DF");

    private String rfc;
    private String razonSocial;
    private String domicilio;

    private DatosSATEnum(String rfc, String razonSocial, String domicilio) {
        this.rfc = rfc;
        this.razonSocial = razonSocial;
        this.domicilio = domicilio;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    
}
