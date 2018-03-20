/*
 * Todos los Derechos Reservados 2013 SAT.
 * Servicio de Administracion Tributaria (SAT).
 * Este software contiene informacion propiedad exclusiva del SAT considerada
 * Confidencial. Queda totalmente prohibido su uso o divulgacion en forma
 * parcial o total.
 */
package mx.gob.sat.mat.tabacos.util;

/**
 * Clase que facilita algunos metodos para el manejo de Strings
 * @author Rafael P&eacute;rez Rivera
 */
public final class StringUtil {
    // Se oculta constructor default
    private StringUtil() { }
    
    /**
     * M&eacute;todo que valida que un string sea vacio o nulo.
     * @param cadena a validar.
     * @return true si la cadena es nula o vacia, de lo contrario false.
     */
    public static boolean isEmpty(String cadena){
        return (cadena == null || cadena.isEmpty());
    }
}
