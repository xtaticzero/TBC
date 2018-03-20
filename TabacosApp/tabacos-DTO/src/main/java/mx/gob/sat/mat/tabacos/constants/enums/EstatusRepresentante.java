package mx.gob.sat.mat.tabacos.constants.enums;

/**
 *
 * @author Ing Salvador Pocteco Saldaña
 */
public enum EstatusRepresentante {

    ACTIVO(1, "ACTIVO"),
    ELIMINADO(2, "BAJA"),
    SOLICITADO(3, "SOLICITADO");

    private Integer key;
    private String descripcion;

    private EstatusRepresentante(Integer key, String descripcion) {
        this.key = key;
        this.descripcion = descripcion;
    }

    public static EstatusRepresentante parse(Integer key) {
        EstatusRepresentante estatusRepresentante = null;

        for (EstatusRepresentante item : EstatusRepresentante.values()) {
            if (item.getKey().equals(key)) {
                estatusRepresentante = item;
                break;
            }
        }

        if (estatusRepresentante == null) {
            estatusRepresentante = EstatusRepresentante.ACTIVO;
        }

        return estatusRepresentante;
    }

    /**
     * *** Getters & Setters ****
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
