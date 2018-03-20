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
public enum IdentificadorProcesoEnum {
    // Procesos de Contribuyente
    //RETROALIMENTACION_MARBETES("MYP00002"),

    CONTRIBUYENTE_TRAMITES("TBC00011"),
    PE_AUTORIZAR_SOLICITUD("TBC00006"),
    PE_AUTORIZAR_MARCAS("TBC00007"),
    PE_AUTORIZAR_REGISTRO("TBC00008"),
    PE_AUTORIZAR_REPORTES("TBC00009"),
    PE_AUTORIZAR_BUSQUEDAS("TBC00010");

    /**
     * String con el identificador
     */
    private final String descripcion;

    /**
     * Constructor IDENTIFICADOR_PROCESO
     *
     * @param descripcion del elemento
     */
    IdentificadorProcesoEnum(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene la descripcion del catalogo
     *
     * @return descripci&oacute;n
     */
    public String getDescripcion() {
        return descripcion;
    }
}
