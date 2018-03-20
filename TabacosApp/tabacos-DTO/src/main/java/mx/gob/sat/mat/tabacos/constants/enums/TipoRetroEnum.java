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
public enum TipoRetroEnum {

    PRODUCCION(null, "PRODUCCION"),
    DESPERDICIO(1, "DESPERDICIO"),
    DESTRUCCION(2, "DESTRUCCION"),
    RETIRADA_DEL_MERCADO(3, "RETIRADA DEL MERCADO"),
    DESTRUCCION_EN_DEPOSITO(4, "DESTRUCCION EN DEPOSITO");

    private Integer key;
    private String descripcion;

    private TipoRetroEnum(Integer key, String descripcion) {
        this.key = key;
        this.descripcion = descripcion;
    }

    public static TipoRetroEnum parse(Integer key) {
        TipoRetroEnum tipo = null;

        for (TipoRetroEnum item : TipoRetroEnum.values()) {
            if (item.getKey().equals(key)) {
                tipo = item;
                break;
            }
        }

        if (tipo == null) {
            tipo = TipoRetroEnum.PRODUCCION;
        }

        return tipo;
    }

    /**
     * *** Getters & Setters
     *
     * @return  ****
     */
    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
