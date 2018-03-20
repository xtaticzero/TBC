package mx.gob.sat.mat.tabacos.ws.dto;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "catEstado")
@XmlEnum
public enum CatEstado {

    @XmlEnumValue("Aceptado")
    ACEPTADO("Aceptado"),
    @XmlEnumValue("Aceptado")
    EN_PROCESO("En Proceso"),
    @XmlEnumValue("Rechazado")
    RECHAZADO("Rechazado");
    private final String value;

    CatEstado(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CatEstado fromValue(String v) {
        for (CatEstado c : CatEstado.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
