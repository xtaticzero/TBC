package mx.gob.sat.mat.tabacos.ws.dto;

import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TBCRespDescarga", namespace = "http://esquemas.clouda.sat.gob.mx/archivos/Tabacos/1/Descarga/Resp")
@XmlRootElement(name = "TBCRespDescarga", namespace = "http://esquemas.clouda.sat.gob.mx/archivos/Tabacos/1/Descarga/Resp")
public class TBCRespDescarga{

    @XmlElement(name = "Contenido", required = true)
    @XmlMimeType("application/octet-stream")
    private DataHandler contenido;
    @XmlAttribute(name = "Version", required = true)
    private String version;
    @XmlAttribute(name = "Folio", required = true)
    private String folio;
    @XmlAttribute(name = "NomArch", required = true)
    private String nomArch;
    @XmlAttribute(name = "FechaDescarga", required = true)
    @XmlSchemaType(name = "dateTime")
    private XMLGregorianCalendar fechaDescarga;
    @XmlAttribute(name = "CantidadCodigos", required = true)
    private Integer cantidadCodigos;
    @XmlAttribute(name = "FolioAcuse", required = true)
    private String folioAcuse;

    /**
     * Gets the value of the contenido property.
     *
     * @return possible object is {@link Object }
     *
     */
    public DataHandler getContenido() {
        return contenido;
    }

    /**
     * Sets the value of the contenido property.
     *
     * @param value allowed object is {@link Object }
     *
     */
    public void setContenido(DataHandler value) {
        this.contenido = value;
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
     *
     * @param v
     */
    public void setVersion(Version v) {
        this.version = v.value();
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
     * Gets the value of the fechaDescarga property.
     *
     * @return possible object is {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getFechaDescarga() {
        return fechaDescarga;
    }

    /**
     * Sets the value of the fechaDescarga property.
     *
     * @param value allowed object is {@link XMLGregorianCalendar }
     *
     */
    public void setFechaDescarga(XMLGregorianCalendar value) {
        this.fechaDescarga = value;
    }

    /**
     * Gets the value of the cantidadCodigos property.
     *
     * @return possible object is {@link Integer }
     *
     */
    public Integer getCantidadCodigos() {
        return cantidadCodigos;
    }

    /**
     * Sets the value of the cantidadCodigos property.
     *
     * @param value allowed object is {@link Integer }
     *
     */
    public void setCantidadCodigos(Integer value) {
        this.cantidadCodigos = value;
    }

    /**
     * Gets the value of the folioAcuse property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getFolioAcuse() {
        return folioAcuse;
    }

    /**
     * Sets the value of the folioAcuse property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setFolioAcuse(String value) {
        this.folioAcuse = value;
    }
}
