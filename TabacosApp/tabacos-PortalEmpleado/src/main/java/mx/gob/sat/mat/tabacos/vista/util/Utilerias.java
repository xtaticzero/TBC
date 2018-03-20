/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.vista.util;

import java.util.Date;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
public final class Utilerias {
    private Utilerias() {
    }
    
    /**
     * Metodo para validar periode de 30 dias entre dos fechas
     * @param fechaInicio
     * @param fechaFin
     * @return 
     */
    public static boolean isPeriodoValidoMax30dias(Date fechaInicio, Date fechaFin) {
        if (fechaInicio != null && fechaFin!= null) {
            
            if(fechaInicio.compareTo(fechaFin) == 0){
                return true;
            }
            
            if (fechaInicio.compareTo(fechaFin) < 0) {
                if (!ValidaRango.isRangoDiasValido(fechaInicio, fechaFin)) {
                     return false;
                }
                return true;
            }
        }
        return false;        
    }
}
