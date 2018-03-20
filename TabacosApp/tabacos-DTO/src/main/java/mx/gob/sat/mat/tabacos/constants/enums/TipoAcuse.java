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
public enum TipoAcuse {
    SOLICITUD("S", "PREFIJO UTILIZADO PARA EL ACUSE DE UNA SOLICITUD DE CODIGOS"),
    DESCARGA("E", "PREFIJO PARA DESCARGA EL ACUSE DE DESCARGA DE ARCHIVO QUE CONTIENE CODIGOS"),
    PRODUCCION("P", "PREFIJO PARA LOS CODIGOS QUE SE RETROALIMENTAN COMO PRODUCCION"),
    INVALIDO("C", "PREFIJO PARA LOS CODIGOS QUE SE RETROALIMENTAN COMO FALSOS O INVALIDOS"),
    DESPERDICIO("D", "PREFIJO PARA LOS CODIGOS QUE SE RETROALIMENTAN COMO DESPERDICIO"),
    SOLICITUD_ALTA_MARCA("M","PREFIJO PARA SOLICITUD DE ALTA DE MARCA"),
    SOLICITUD_ALTA_PROVEEDOR("A","PREFIJO PARA SOLICITUD DE ALTA DE PROVEEDOR");
    private String key;
    private String descripcion;

    private TipoAcuse(String key, String descripcion) {
        this.key = key;
        this.descripcion = descripcion;
    }

    public static TipoAcuse parse(String key) {
        TipoAcuse tipoAcuse = null;

        for (TipoAcuse item : TipoAcuse.values()) {
            if (item.getKey().equals(key)) {
                tipoAcuse = item;
                break;
            }
        }

        if (tipoAcuse == null) {
            tipoAcuse = TipoAcuse.SOLICITUD;
        }

        return tipoAcuse;
    }

    /**
     * *** Getters & Setters ****
     * @return 
     */
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
