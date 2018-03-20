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
@XmlType(name = "Acuse", namespace = "http://esquemas.clouda.sat.gob.mx/archivos/Tabacos/1/Retroalimentacion/Acuse")
@XmlRootElement(name = "Acuse", namespace = "http://esquemas.clouda.sat.gob.mx/archivos/Tabacos/1/Retroalimentacion/Acuse")
public class AcuseRetro {

    @XmlAttribute(name = "RFC", required = true)
    private String rfc;
    @XmlAttribute(name = "Incidencia", required = true)
    private String incidencia;
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
     * Gets the value of the incidencia property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getIncidencia() {
        return incidencia;
    }

    /**
     * Sets the value of the incidencia property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setIncidencia(String value) {
        this.incidencia = value;
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
