/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.vista.helper;

import java.io.Serializable;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
public class FirmaFormHelper implements Serializable{
    private String rfcSession;
    private String passwordSession;
    private String selloDigital;
    private String cadenaOriginal;
    
    public static final String CADENA_ORIGINA = "cadenaOriginal";
    public static final String FIRMA_DIGITAL = "firmaDigital";

    public String getRfcSession() {
        return rfcSession;
    }

    public void setRfcSession(String rfcSession) {
        this.rfcSession = rfcSession;
    }

    public String getPasswordSession() {
        return passwordSession;
    }

    public void setPasswordSession(String passwordSession) {
        this.passwordSession = passwordSession;
    }

    public String getSelloDigital() {
        return selloDigital;
    }

    public void setSelloDigital(String selloDigital) {
        this.selloDigital = selloDigital;
    }

    public String getCadenaOriginal() {
        return cadenaOriginal;
    }

    public void setCadenaOriginal(String cadenaOriginal) {
        this.cadenaOriginal = cadenaOriginal;
    }
    
}
