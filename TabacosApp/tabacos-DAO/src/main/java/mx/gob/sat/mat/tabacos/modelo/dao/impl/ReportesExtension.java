/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.modelo.dao.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import mx.gob.sat.mat.tabacos.modelo.dto.reportes.ReportesFiltroBase;
import mx.gob.sat.mat.tabacos.modelo.dto.reportes.ReportesRegistrosYDespedicios;

/**
 *
 * @author MTI Agustin Romero Mata
 */
public class ReportesExtension {
    protected static final int ACTIVO = 1;
    protected static final String VACIO = "";    
    protected static final int VAL0 = 0;
    protected static final int VAL1 = 1;
    protected static final int VAL2 = 2;
    protected static final int VAL3 = 3;
    protected static final int VAL4 = 4;
    protected static final int VAL5 = 5;
    protected static final int VAL6 = 6;
    protected static final int VAL7 = 7;
    protected static final int VAL8 = 8;
    protected static final int VAL9 = 9;
        
    /**
     * 
     */
    protected static enum OPCIONES {
        OPCION0, OPCION1, OPCION2, OPCION3, OPCION4, 
        OPCION5, OPCION6, OPCION7, OPCION8, OPCION9, 
        OPCION10, OPCION11, OPCION12
    }
    
    /**
     * 
     * @param filtro
     * @return 
     */
    protected OPCIONES selectOpciones(ReportesFiltroBase filtro) {
        OPCIONES retorno = OPCIONES.OPCION0;
        
        if ((!filtro.getRfc().isEmpty()) && 
                (filtro.getFechaInicio()!=null) && 
                (filtro.getFechaFin()!=null)) {
            retorno = OPCIONES.OPCION1;        
        } else if ((filtro.getFechaInicio()!=null) && 
                (filtro.getFechaFin()!=null)) {
            retorno = OPCIONES.OPCION2;
        } else if (!filtro.getRfc().isEmpty()) {
            retorno = OPCIONES.OPCION3;
        }
        
        return retorno;
    }
    
    /**
     * 
     * @param filtro
     * @return 
     */
    protected int selectOpc(ReportesFiltroBase filtro) {
        int retorno = 0;
        
        if ((!filtro.getRfc().isEmpty()) && 
                (filtro.getFechaInicio()!=null) && 
                (filtro.getFechaFin()!=null)) {
            retorno = VAL1;        
        } else if ((filtro.getFechaInicio()!=null) && 
                (filtro.getFechaFin()!=null)) {
            retorno = VAL2;
        } else if (!filtro.getRfc().isEmpty()) {
            retorno = VAL3;
        }
        
        return retorno;
    }
    
    /**
     * 
     * @param filtro
     * @return 
     */
    protected Object[] parametrosContProv(ReportesFiltroBase filtro) {
        Object[] retorno = null;
        
        switch(selectOpc(filtro)) {
            case VAL1: retorno = new Object[]{filtro.getRfc(),
                filtro.getFechaInicio(), filtro.getFechaFin()};
                break;
            case VAL2: retorno = new Object[]{filtro.getFechaInicio(), 
                        filtro.getFechaFin()};
                break;
            case VAL3: retorno = new Object[]{filtro.getRfc()};
                break;
            default: break;
        }
        
        return retorno;
    }
            
    protected String fechaInit(Date fecha) {
        if (fecha != null)  {
            return convertDateToString(fecha);
        } else {
            return VACIO;
        }
    }
    
    protected String convertDateToString(final Date fecha) {
        if (fecha != null) {
            DateFormat convert = new SimpleDateFormat("dd/MM/yyyy");
            return convert.format(fecha);
        } else {
            return "";
        }
    }
    
    /**
     * 
     * @param finicial
     * @param ffinal
     * @return 
     */
    protected String rangoFechas(BigDecimal finicial, BigDecimal ffinal) {
        
        if ((finicial == null) || (ffinal == null)) {
            return " No existe rango valido ";
        } else {
            return finicial.toString() + " - " + ffinal.toString();
        }
    }
    
    /**
     * 
     * @param marca
     * @return 
     */
    protected boolean existeMarca(final String marca) {
        if ((marca != null) && (!marca.equals(VACIO))) {
            return true;            
        } 
        return false;
    }
    
    /**
     * 
     * @param filtro
     * @param opcion
     * @return 
     */
    protected Object[] parametrosMarcas(ReportesFiltroBase filtro, OPCIONES opcion) {
        Object[] retorno = null;
        
        switch(opcion) {
            case OPCION1: retorno = new Object[]{filtro.getFechaInicio(),
                filtro.getFechaFin(),filtro.getRfc(),  
                filtro.getMarca()};
            break;
            case OPCION2: retorno = new Object[]{filtro.getFechaInicio(),
                filtro.getFechaFin(),filtro.getRfc()}; 
            break;
            case OPCION3: retorno = new Object[]{filtro.getFechaInicio(),
                filtro.getFechaFin(), filtro.getMarca()}; 
            break;
            case OPCION4: retorno = new Object[]{filtro.getFechaInicio(),
                filtro.getFechaFin()};
            break;
            case OPCION5: retorno = new Object[]{filtro.getRfc(), filtro.getMarca()};
            break;
            case OPCION6: retorno = new Object[]{filtro.getRfc()};  
            break;
            default: break;
        }
        return retorno;
    }
    
    /**
     * 
     * @param filtro
     * @return 
     */
    protected OPCIONES selectOpcionesMarcas(ReportesFiltroBase filtro) {
        OPCIONES retorno = OPCIONES.OPCION0;
        
        if ((!filtro.getRfc().isEmpty()) && 
                (filtro.getFechaInicio()!=null) && 
                (filtro.getFechaFin()!=null)) {            
            retorno = (existeMarca(filtro.getMarca()))?
                    OPCIONES.OPCION1:OPCIONES.OPCION2;
        } else if ((filtro.getFechaInicio()!=null) && 
                (filtro.getFechaFin()!=null)) {            
            retorno = (existeMarca(filtro.getMarca()))?
                    OPCIONES.OPCION3:OPCIONES.OPCION4;
        } else if (!filtro.getRfc().isEmpty()) {
            retorno = (existeMarca(filtro.getMarca()))?
                    OPCIONES.OPCION5:OPCIONES.OPCION6;
        }
        
        return retorno;
    }
    
    /**
     * 
     * @param activo
     * @param valor
     * @return 
     */
    protected String filtroActivo(int activo, String valor) {        
        if (activo == this.ACTIVO) {
            return valor;
        }
        
        return null;
    }
    
    /**
     * 
     * @param activo
     * @param valor
     * @return 
     */
    protected String filtroActivo(long activo, String valor) {        
        if (activo == this.ACTIVO) {
            return valor;
        }
        
        return null;
    }    
    
    /**
     * 
     * @param filtro
     * @return 
     */
    protected OPCIONES selectOpcionesRegistro(final ReportesRegistrosYDespedicios filtro) {
        OPCIONES retorno = OPCIONES.OPCION0;
     
        if ((filtro.getContribuyenteCB()!=null) && 
            (filtro.getProveedorCB()!=null)) {
           if (!filtro.getContribuyenteCB().equals(VACIO) && 
                !filtro.getProveedorCB().equals(VACIO)) {
               retorno = OPCIONES.OPCION1;
           }
        } else if (filtro.getContribuyenteCB()!=null) {
           if (!filtro.getContribuyenteCB().equals(VACIO)) {
               retorno = OPCIONES.OPCION2;
           }
        } else if (filtro.getProveedorCB()!=null) {
           if (!filtro.getProveedorCB().equals(VACIO)) {
               retorno = OPCIONES.OPCION3;
           }
        } else {
           retorno = OPCIONES.OPCION4;
        }
        
        return retorno;
    }        
}
