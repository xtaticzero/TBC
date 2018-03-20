/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.vista.util;

import java.util.Date;

/**
 *
 * @author MTI Agustin Romero Mata
 */
public final class ValidaRango {
    private static final long MES = 2592000000L;
    //Milisegundos al día
    private static final long MILLSECS_POR_DAY = 24 * 60 * 60 * 1000;
    private static final long DIAS_PERIODO = 30;

    private ValidaRango() {
    }
    
    public static boolean rangoFecha(Date fechaInicial, Date fechaFinal) {               
       Date calcFecha = new Date(fechaInicial.getTime()+MES);                    
       return (calcFecha.getTime() < fechaFinal.getTime());
    }
    
    public static boolean isRangoDiasValido(Date fechaInicio, Date fechaFin) {
        Long diferencia = ((fechaFin.getTime() - fechaInicio.getTime()) / MILLSECS_POR_DAY);
        if(diferencia>0){
            return diferencia < DIAS_PERIODO;
        }else {
            return false;
        }
        
    }
    
    
}
