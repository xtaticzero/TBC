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
@XmlType(name = "Solicitud", namespace = "http://esquemas.clouda.sat.gob.mx/archivos/Tabacos/1/Solicitud")
@XmlRootElement(name = "Solicitud", namespace = "http://esquemas.clouda.sat.gob.mx/archivos/Tabacos/1/Solicitud")
public class Solicitud {

    @XmlAttribute(name = "RFC", required = true)
    private String rfc;
    @XmlAttribute(name = "TipoContribuyente", required = true)
    private String tipoContribuyente;
    @XmlAttribute(name = "CantidadCodigos", required = true)
    private Integer cantidadCodigos;
    @XmlAttribute(name = "Origen", required = true)
    private String origen;
    @XmlAttribute(name = "Firma", required = true)
    private String firma;

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
     * Gets the value of the tipoContribuyente property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getTipoContribuyente() {
        return tipoContribuyente;
    }

    /**
     * Sets the value of the tipoContribuyente property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setTipoContribuyente(String value) {
        this.tipoContribuyente = value;
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
     * Gets the value of the origen property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getOrigen() {
        return origen;
    }

    /**
     * Sets the value of the origen property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setOrigen(String value) {
        this.origen = value;
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
