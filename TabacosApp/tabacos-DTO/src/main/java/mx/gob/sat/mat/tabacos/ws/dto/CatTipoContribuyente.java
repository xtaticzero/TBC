/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.ws.dto;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author root
 */
@XmlType(name = "catTipoContribuyente")
@XmlEnum
public enum CatTipoContribuyente {

    @XmlEnumValue("1")
    UNO("1"),
    @XmlEnumValue("2")
    DOS("2"),
    @XmlEnumValue("3")
    TRES("3");
    private final String value;

    CatTipoContribuyente(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CatTipoContribuyente fromValue(String v) {
        for (CatTipoContribuyente c : CatTipoContribuyente.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
