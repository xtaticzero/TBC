package mx.gob.sat.mat.tabacos.constants.enums;

/**
 *
 * @author Salvador Pocteco Saldaña
 */
public enum EstatusProveedor {

    ACTIVO(1L, "ACTIVO"),
    BAJA(2L, "BAJA");

    private Long key;
    private String descripcion;

    private EstatusProveedor(Long key, String value) {
        this.key = key;
        this.descripcion = value;
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
