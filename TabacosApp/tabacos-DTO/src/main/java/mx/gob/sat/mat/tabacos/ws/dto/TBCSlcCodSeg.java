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
@XmlType(name = "TBCSlcCodSeg", propOrder = {
    "solicitud"
}, namespace = "http://esquemas.clouda.sat.gob.mx/archivos/Tabacos/1/Solicitud")
@XmlRootElement(name = "TBCSlcCodSeg", namespace = "http://esquemas.clouda.sat.gob.mx/archivos/Tabacos/1/Solicitud")
public class TBCSlcCodSeg {

    @XmlElement(name = "Solicitud", required = true)
    private Solicitud solicitud;
    @XmlAttribute(name = "Version", required = true)
    private String version;
    @XmlAttribute(name = "Fecha", required = true)
    @XmlSchemaType(name = "dateTime")
    private XMLGregorianCalendar fecha;
    @XmlAttribute(name = "RFCProveedorCertificado", required = true)
    private String rfcProveedorCertificado;

    /**
     * Gets the value of the solicitud property.
     *
     * @return possible object is {@link TBCSlcCodSeg.Solicitud }
     *
     */
    public Solicitud getSolicitud() {
        return solicitud;
    }

    /**
     * Sets the value of the solicitud property.
     *
     * @param value allowed object is {@link TBCSlcCodSeg.Solicitud }
     *
     */
    public void setSolicitud(Solicitud value) {
        this.solicitud = value;
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

    /**
     * Gets the value of the rfcProveedorCertificado property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getRFCProveedorCertificado() {
        return rfcProveedorCertificado;
    }

    /**
     * Sets the value of the rfcProveedorCertificado property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setRFCProveedorCertificado(String value) {
        this.rfcProveedorCertificado = value;
    }
}
