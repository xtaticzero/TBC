/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.ws.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author root
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CodigosNoValidos", propOrder = {}, namespace = "http://esquemas.clouda.sat.gob.mx/archivos/Tabacos/1/Retroalimentacion")
@XmlRootElement(name = "CodigosNoValidos", namespace = "http://esquemas.clouda.sat.gob.mx/archivos/Tabacos/1/Retroalimentacion")
public class CodigosNoValidos {

    @XmlElement(name = "Rangos", required = true)
    private RangoCodigosSeguridad rangos;
    @XmlAttribute(name = "RFC", required = true)
    private String rfc;    
    @XmlAttribute(name = "Justificacion", required = true)
    private String justificacion;

    /**
     * Gets the value of the rangos property.
     *
     * @return possible object is {@link RangoCodigosSeguridad }
     *
     */
    public RangoCodigosSeguridad getRangos() {
        return rangos;
    }

    /**
     * Sets the value of the rangos property.
     *
     * @param value allowed object is {@link RangoCodigosSeguridad }
     *
     */
    public void setRangos(RangoCodigosSeguridad value) {
        this.rangos = value;
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
     * Gets the value of the justificacion property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getJustificacion() {
        return justificacion;
    }

    /**
     * Sets the value of the justificacion property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setJustificacion(String value) {
        this.justificacion = value;
    }
}