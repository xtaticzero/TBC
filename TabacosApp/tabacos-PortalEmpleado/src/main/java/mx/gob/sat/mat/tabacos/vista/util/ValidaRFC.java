/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.vista.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
public final class ValidaRFC {

    public static final String ERROR = "Error";
    public static final String INFO = "Información";
    public static final String WARN = "Atención";
    public static final String FATAL = "Error grave";

    private static final String MSG_ERROR_INFO = "La información no es correcta";

    private ValidaRFC() {
    }

    /**
     * Valida que el RFC sea valido y exista en la PCBA.
     *
     * @param sRFC
     * @return boolean
     */
    public static boolean validaRFC(String sRFC) {
        final int longRfcMin = 12;
        final int longRfcMax = 13;

        if (sRFC.length() < longRfcMin || sRFC.length() > longRfcMax) {

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ERROR,
                            MSG_ERROR_INFO));
            return false;
        } else {
            if (!verificaExistenciaRFC(sRFC)) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, ERROR,
                                "No existe rfc en el sistema"));
                return false;
            }
        }
        return true;
    }

    public static boolean validaRFCFisica(String sRFC) {

        Pattern pattern;
        Matcher matcher;

        pattern = Pattern.compile(Expresiones.RFC_PATTERN_1BLOQUE);
        matcher = pattern.matcher(sRFC);
        if (!matcher.matches()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ERROR,
                            "Para persona fisica: Las primeras 4 posiciones deben ser letras"));
            return false;
        } else {
            pattern = Pattern.compile(Expresiones.RFC_PATTERN_2BLOQUE);
            matcher = pattern.matcher(sRFC);
            if (!matcher.matches()) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, ERROR,
                                "Para persona fisica: Año[yy]-Mes[mm]-Dia[dd] deben ser válidos y numéricos"));
                return false;
            } else {
                pattern = Pattern.compile(Expresiones.RFC_PATTERN_3BLOQUE);
                matcher = pattern.matcher(sRFC);
                if (!matcher.matches()) {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, ERROR,
                                    "Para persona fisica: El RFC no debe contener caracteres especiales"));
                    return false;
                } else {
                    pattern = Pattern.compile(Expresiones.RFC_PATTERN_4BLOQUE);
                    matcher = pattern.matcher(sRFC);
                    if (!matcher.matches()) {
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR, ERROR,
                                        "Para persona fisica: El último digito debe ser numérico o la letra A"));
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static boolean validaRFCMoral(String sRFC) {

        Pattern pattern;
        Matcher matcher;

        pattern = Pattern.compile(Expresiones.RFCM_PATTERN_1BLOQUE);
        matcher = pattern.matcher(sRFC);
        if (!matcher.matches()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ERROR,
                            "Para persona moral: Las primeras 3 posiciones deben ser letras"));
            return false;
        } else {
            pattern = Pattern.compile(Expresiones.RFCM_PATTERN_2BLOQUE);
            matcher = pattern.matcher(sRFC);
            if (!matcher.matches()) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, ERROR,
                                "Para persona moral: Año-Mes-Dia deben ser válidos y numéricos"));
                return false;
            } else {
                pattern = Pattern.compile(Expresiones.RFCM_PATTERN_3BLOQUE);
                matcher = pattern.matcher(sRFC);
                if (!matcher.matches()) {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, ERROR,
                                    "Para persona moral: El RFC no debe contener caracteres especiales"));
                    return false;
                } else {
                    pattern = Pattern.compile(Expresiones.RFCM_PATTERN_4BLOQUE);
                    matcher = pattern.matcher(sRFC);
                    if (!matcher.matches()) {
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR, ERROR,
                                        "Para persona moral: El último digito debe ser numérico o la letra A"));
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static boolean verificaExistenciaRFC(String rfc) {
        return true;
    }
}
