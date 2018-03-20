/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.ws.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author root
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Archivos", namespace = "http://esquemas.clouda.sat.gob.mx/archivos/Tabacos/1/Consulta/Resp")
@XmlRootElement(name = "Archivos", namespace = "http://esquemas.clouda.sat.gob.mx/archivos/Tabacos/1/Consulta/Resp")
public class Archivos {

    @XmlAttribute(name = "NomArch", required = true)
    private String nomArch;
    @XmlAttribute(name = "CantidadCodigos", required = true)
    private Integer cantidadCodigos;
    @XmlAttribute(name = "Checksum", required = true)
    private String checksum;
    @XmlAttribute(name = "FechaHorCreacion", required = true)
    @XmlSchemaType(name = "dateTime")
    private XMLGregorianCalendar fechaHorCreacion;

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
     * Gets the value of the checksum property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getChecksum() {
        return checksum;
    }

    /**
     * Sets the value of the checksum property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setChecksum(String value) {
        this.checksum = value;
    }

    /**
     * Gets the value of the fechaHorCreación property.
     *
     * @return possible object is {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getFechaHorCreacion() {
        return fechaHorCreacion;
    }

    /**
     * Sets the value of the fechaHorCreación property.
     *
     * @param value allowed object is {@link XMLGregorianCalendar }
     *
     */
    public void setFechaHorCreacion(XMLGregorianCalendar value) {
        this.fechaHorCreacion = value;
    }
}
