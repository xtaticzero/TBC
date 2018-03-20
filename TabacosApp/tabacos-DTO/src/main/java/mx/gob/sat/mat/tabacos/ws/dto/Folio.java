/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.ws.dto;

import java.util.ArrayList;
import java.util.List;
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
@XmlType(name = "Folio", namespace = "http://esquemas.clouda.sat.gob.mx/archivos/Tabacos/1/Consulta/Resp")
@XmlRootElement(name = "Folio", namespace = "http://esquemas.clouda.sat.gob.mx/archivos/Tabacos/1/Consulta/Resp")
public class Folio {

    @XmlElement(name = "Archivos")
    private List<Archivos> archivos;
    @XmlAttribute(name = "Folio", required = true)
    private String folio;
    @XmlAttribute(name = "RFC", required = true)
    private String rfc;
    @XmlAttribute(name = "Estado", required = true)
    private String estado;

    /**
     * Gets the value of the archivos property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the archivos property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getArchivos().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
         * {@link TBCRespConsulFolio.Folio.Archivos }
     *
     *
     * @return 
     */
    public List<Archivos> getArchivos() {
        if (archivos == null) {
            archivos = new ArrayList<Archivos>();
        }
        return this.archivos;
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
     * Gets the value of the estado property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Sets the value of the estado property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setEstado(String value) {
        this.estado = value;
    }
}
