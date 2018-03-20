/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.constants.enums;

/**
 *
 * @author Ing. Gregorio Serapio Ramirez
 */
public enum EstatusCargador {
    ESPERA(1, "ESPERA"),
    OBTENIENDO_TICKET(10, "OBTENIENDO TICKET"),
    ERROR_OBTENIENDO_TICKET(11, "ERROR OBTENIENDO TICKET"),
    INSERTANDO_ARCHIVOS(20, "INSERTANDO ARCHIVOS"),
    ERROR_INSERTANDO_ARCHIVOS(21, "ERROR INSERTANDO ARCHIVOS"),
    CREANDO_DIRECTORIOS(30, "CREANDO DIRECTORIOS"),
    ERROR_CREANDO_DIRECTORIOS(31, "ERROR CREANDO DIRECTORIOS"),
    PREPARANDO_SQL_EXPORT(40, "PREPARANDO SQL EXPORT"),
    ERROR_PREPARANDO_SQL_EXPORT(41, "ERROR PREPARANDO SQL EXPORT"),
    EXPORTANDO_CODIGOS(50, "EXPORTANDO CODIGOS"),
    ERROR_EXPORTANDO_CODIGOS(51, "ERROR EXPORTANDO CODIGOS"),
    COMPRIMIENDO_Y_MOVIENDO_CODIGOS(60, "COMPRIMIENDO Y MOVIENDO CODIGOS"),
    ERROR_COMPRIMIENDO_Y_MOVIENDO_CODIGOS(61, "ERROR COMPRIMIENDO Y MOVIENDO CODIGOS"),
    ASOCIANDO_SOLICITUD(70, "ASOCIANDO SOLICITUD"),
    ERROR_ASOCIANDO_SOLICITUD(71, "ERROR ASOCIANDO SOLICITUD"),
    LISTO(80, "LISTO");
    private Integer key;
    private String value;

    private EstatusCargador(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public static EstatusCargador parse(Integer key) {
        EstatusCargador estatusCargador = null;

        for (EstatusCargador item : EstatusCargador.values()) {
            if (item.getKey().equals(key)) {
                estatusCargador = item;
                break;
            }
        }

        if (estatusCargador == null) {
            estatusCargador = EstatusCargador.ESPERA;
        }

        return estatusCargador;
    }

    /**
     * *** Getters & Setters ****
     * @return 
     */
    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
