package mx.gob.sat.mat.tabacos.ws.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TBCConsulFolio", propOrder = {
    "folios"
}, namespace = "http://esquemas.clouda.sat.gob.mx/archivos/Tabacos/1/Consulta")
@XmlRootElement(name = "TBCConsulFolio", namespace = "http://esquemas.clouda.sat.gob.mx/archivos/Tabacos/1/Consulta")
public class TBCConsulFolio {

    @XmlElement(name = "Folios", required = true)
    private Folios folios;
    @XmlAttribute(name = "Version", required = true)
    private String version;
    @XmlAttribute(name = "RFCProveedorCertificado", required = true)
    private String rfcProveedorCertificado;

    /**
     * Gets the value of the folios property.
     *
     * @return possible object is {@link TBCConsulFolio.Folios }
     *
     */
    public Folios getFolios() {
        return folios;
    }

    /**
     * Sets the value of the folios property.
     *
     * @param value allowed object is {@link TBCConsulFolio.Folios }
     *
     */
    public void setFolios(Folios value) {
        this.folios = value;
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
