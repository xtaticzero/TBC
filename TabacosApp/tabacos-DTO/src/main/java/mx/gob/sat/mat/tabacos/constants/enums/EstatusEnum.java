package mx.gob.sat.mat.tabacos.constants.enums;

/**
 *
 * @author Ing Salvador Pocteco Saldaña
 */
public enum EstatusEnum {

    ALTA(1L, "ALTA"),
    BAJA(2L, "BAJA"),
    SOLICITADO(3L, "SOLICITADO"),
    CANCELADO(4L, "CANCELADO");

    private Long key;
    private String value;

    private EstatusEnum(Long key, String value) {
        this.key = key;
        this.value = value;
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
