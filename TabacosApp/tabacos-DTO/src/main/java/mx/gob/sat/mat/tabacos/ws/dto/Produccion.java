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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author root
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Produccion", namespace = "http://esquemas.clouda.sat.gob.mx/archivos/Tabacos/1/Retroalimentacion")
@XmlRootElement(name = "Produccion", namespace = "http://esquemas.clouda.sat.gob.mx/archivos/Tabacos/1/Retroalimentacion")
public class Produccion {
    
    @XmlElement(name = "Rangos", required = true)
    private List<RangoCodigosSeguridad> rangos;
    @XmlAttribute(name = "RFC", required = true)
    private String rfc;    
    @XmlAttribute(name = "Marca", required = true)
    private String marca;
    @XmlAttribute(name = "CantidadCigarros", required = true)
    private Integer cantidadCigarros;
    @XmlAttribute(name = "CantidadProduccion", required = true)
    private Integer cantidadProduccion;
    @XmlAttribute(name = "PlantaProduccion", required = true)
    private String plantaProduccion;
    @XmlAttribute(name = "MaquinaProduccion", required = true)
    private String maquinaProduccion;
    @XmlAttribute(name = "LoteProduccion", required = true)
    private String loteProduccion;
    @XmlAttribute(name = "LineaProd", required = true)
    private Integer lineaProd;
    @XmlAttribute(name = "FechaHoraProd", required = true)
    @XmlSchemaType(name = "dateTime")
    private XMLGregorianCalendar fechaHoraProd;
    @XmlAttribute(name = "Origen", required = true)
    private String origen;
    @XmlAttribute(name = "FechaImportacion", required = true)
    @XmlSchemaType(name = "date")
    private XMLGregorianCalendar fechaImportacion;

    /**
     * Gets the value of the rangos property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the rangos property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRangos().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
         * {@link RangoCodigosSeguridad }
     *
     *
     */
    public List<RangoCodigosSeguridad> getRangos() {
        if (rangos == null) {
            rangos = new ArrayList<RangoCodigosSeguridad>();
        }
        return this.rangos;
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
     * Gets the value of the marca property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getMarca() {
        return marca;
    }

    /**
     * Sets the value of the marca property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setMarca(String value) {
        this.marca = value;
    }

    /**
     * Gets the value of the cantidadCigarros property.
     *
     * @return possible object is {@link Integer }
     *
     */
    public Integer getCantidadCigarros() {
        return cantidadCigarros;
    }

    /**
     * Sets the value of the cantidadCigarros property.
     *
     * @param value allowed object is {@link Integer }
     *
     */
    public void setCantidadCigarros(Integer value) {
        this.cantidadCigarros = value;
    }

    /**
     * Gets the value of the plantaProduccion property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getPlantaProduccion() {
        return plantaProduccion;
    }

    /**
     * Sets the value of the plantaProduccion property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setPlantaProduccion(String value) {
        this.plantaProduccion = value;
    }

    /**
     * Gets the value of the maquinaProduccion property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getMaquinaProduccion() {
        return maquinaProduccion;
    }

    /**
     * Sets the value of the maquinaProduccion property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setMaquinaProduccion(String value) {
        this.maquinaProduccion = value;
    }

    /**
     * Gets the value of the loteProduccion property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getLoteProduccion() {
        return loteProduccion;
    }

    /**
     * Sets the value of the loteProduccion property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setLoteProduccion(String value) {
        this.loteProduccion = value;
    }

    /**
     * Gets the value of the lineaProd property.
     *
     * @return possible object is {@link Integer }
     *
     */
    public Integer getLineaProd() {
        return lineaProd;
    }

    /**
     * Sets the value of the lineaProd property.
     *
     * @param value allowed object is {@link Integer }
     *
     */
    public void setLineaProd(Integer value) {
        this.lineaProd = value;
    }

    /**
     * Gets the value of the fechaHoraProd property.
     *
     * @return possible object is {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getFechaHoraProd() {
        return fechaHoraProd;
    }

    /**
     * Sets the value of the fechaHoraProd property.
     *
     * @param value allowed object is {@link XMLGregorianCalendar }
     *
     */
    public void setFechaHoraProd(XMLGregorianCalendar value) {
        this.fechaHoraProd = value;
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
     * Gets the value of the fechaImportacion property.
     *
     * @return possible object is {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getFechaImportacion() {
        return fechaImportacion;
    }

    /**
     * Sets the value of the fechaImportacion property.
     *
     * @param value allowed object is {@link XMLGregorianCalendar }
     *
     */
    public void setFechaImportacion(XMLGregorianCalendar value) {
        this.fechaImportacion = value;
    }

    /**
     * @return the cantidadProduccion
     */
    public Integer getCantidadProduccion() {
        return cantidadProduccion;
    }

    /**
     * @param cantidadProduccion the cantidadProduccion to set
     */
    public void setCantidadProduccion(Integer cantidadProduccion) {
        this.cantidadProduccion = cantidadProduccion;
    }
}
