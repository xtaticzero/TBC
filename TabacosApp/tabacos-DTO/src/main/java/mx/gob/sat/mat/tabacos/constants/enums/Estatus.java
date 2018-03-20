/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.constants.enums;

/**
 *
 * @author root
 */
public enum Estatus {

    ALTA(1, "Aun No Calificado"),
    BAJA(2, "Autorizado"),
    OTRO(3, "Rechazado");
    private Integer key;
    private String value;

    private Estatus(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public static Estatus parse(Integer key) {
        Estatus estatusProveedor = null;

        for (Estatus item : Estatus.values()) {
            if (item.getKey().equals(key)) {
                estatusProveedor = item;
                break;
            }
        }

        if (estatusProveedor == null) {
            estatusProveedor = Estatus.ALTA;
        }

        return estatusProveedor;
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
