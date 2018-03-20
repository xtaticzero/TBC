package mx.gob.sat.mat.tabacos.ws.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TBCAcuRboSlc", namespace = "http://esquemas.clouda.sat.gob.mx/archivos/Tabacos/1/Solicitud/Acuse")
@XmlRootElement(name = "TBCAcuRboSlc", namespace = "http://esquemas.clouda.sat.gob.mx/archivos/Tabacos/1/Solicitud/Acuse")
public class TBCAcuRboSlc {

    @XmlElement(name = "Acuse", required = true)
    private Acuse acuse;
    @XmlAttribute(name = "Version", required = true)
    private String version;
    @XmlAttribute(name = "Fecha", required = true)
    @XmlSchemaType(name = "dateTime")
    private XMLGregorianCalendar fecha;

    /**
     * Gets the value of the acuse property.
     *
     * @return possible object is {@link TBCAcuRboSlc.Acuse }
     *
     */
    public Acuse getAcuse() {
        return acuse;
    }

    /**
     * Sets the value of the acuse property.
     *
     * @param value allowed object is {@link TBCAcuRboSlc.Acuse }
     *
     */
    public void setAcuse(Acuse value) {
        this.acuse = value;
    }

    /**
     * Gets the value of the version property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getVersion() {
        if (version == null) {
            return "1.0";
        } else {
            return version;
        }
    }

    /**
     * Sets the value of the version property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setVersion(Version v) {
        this.version = v.value();
    }

    /**
     * Gets the value of the fecha property.
     *
     * @return possible object is {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getFecha() {
        return fecha;
    }

    /**
     * Sets the value of the fecha property.
     *
     * @param value allowed object is {@link XMLGregorianCalendar }
     *
     */
    public void setFecha(XMLGregorianCalendar value) {
        this.fecha = value;
    }
}
