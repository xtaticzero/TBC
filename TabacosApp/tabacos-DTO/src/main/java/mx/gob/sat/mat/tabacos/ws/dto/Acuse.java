/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.ws.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author root
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Acuse", namespace = "http://esquemas.clouda.sat.gob.mx/archivos/Tabacos/1/Solicitud/Acuse")
@XmlRootElement(name = "Acuse", namespace = "http://esquemas.clouda.sat.gob.mx/archivos/Tabacos/1/Solicitud/Acuse")
public class Acuse {

    @XmlAttribute(name = "RFC", required = true)
    private String rfc;
    @XmlAttribute(name = "Folio", required = true)
    private String folio;
    @XmlAttribute(name = "CantidadCodigos", required = true)
    private Integer cantidadCodigos;
    @XmlAttribute(name = "FolioAcuse", required = true)
    private String folioAcuse;

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