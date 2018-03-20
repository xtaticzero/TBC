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

@XmlType(name = "version")
@XmlEnum
public enum Version {
    @XmlEnumValue("1.0")
    UNO("1.0");
    
    private final String value;

    Version(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Version fromValue(String v) {
        for (Version c: Version.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
