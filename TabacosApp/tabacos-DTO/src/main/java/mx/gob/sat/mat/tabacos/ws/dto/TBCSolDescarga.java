package mx.gob.sat.mat.tabacos.ws.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TBCSolDescarga", namespace = "http://esquemas.clouda.sat.gob.mx/archivos/Tabacos/1/Descarga")
@XmlRootElement(name = "TBCSolDescarga", namespace = "http://esquemas.clouda.sat.gob.mx/archivos/Tabacos/1/Descarga")
public class TBCSolDescarga {

    @XmlAttribute(name = "Version", required = true)
    private String version;
    @XmlAttribute(name = "RFC", required = true)
    private String rfc;
    @XmlAttribute(name = "Folio", required = true)
    private String folio;
    @XmlAttribute(name = "NomArch", required = true)
    private String nomArch;
    @XmlAttribute(name = "Firma", required = true)
    private String firma;

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
     *
     * @param v
     */
    public void setVersion(Version v) {
        this.version = v.value();
    }

    /**
     * Gets the value of the rfc property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getRFC() {
        return rfc;
    }

    /**
     * Sets the value of the rfc property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setRFC(String value) {
        this.rfc = value;
    }

    /**
     * Gets the value of the folio property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getFolio() {
        return folio;
    }

    /**
     * Sets the value of the folio property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setFolio(String value) {
        this.folio = value;
    }

    /**
     * Gets the value of the nomArch property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getNomArch() {
        return nomArch;
    }

    /**
     * Sets the value of the nomArch property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setNomArch(String value) {
        this.nomArch = value;
    }

    /**
     * Gets the value of the firma property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getFirma() {
        return firma;
    }

    /**
     * Sets the value of the firma property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setFirma(String value) {
        this.firma = value;
    }
}
