package mx.gob.sat.mat.tabacos.ws.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RangoCodigosSeguridad", namespace = "http://esquemas.clouda.sat.gob.mx/archivos/Tabacos/1/Catalogos")
@XmlRootElement(name = "RangoCodigosSeguridad", namespace = "http://esquemas.clouda.sat.gob.mx/archivos/Tabacos/1/Catalogos")
public class RangoCodigosSeguridad {

    @XmlAttribute(name = "LimSup", required = true)
    private String limSup;
    @XmlAttribute(name = "LimInif", required = true)
    private String limInif;

    /**
     * Gets the value of the limSup property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getLimSup() {
        return limSup;
    }

    /**
     * Sets the value of the limSup property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setLimSup(String value) {
        this.limSup = value;
    }

    /**
     * Gets the value of the limInif property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getLimInif() {
        return limInif;
    }

    /**
     * Sets the value of the limInif property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setLimInif(String value) {
        this.limInif = value;
    }
}
