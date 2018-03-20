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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author root
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Destruccion", propOrder = {}, namespace = "http://esquemas.clouda.sat.gob.mx/archivos/Tabacos/1/Retroalimentacion")
@XmlRootElement(name = "Destruccion", namespace = "http://esquemas.clouda.sat.gob.mx/archivos/Tabacos/1/Retroalimentacion")
public class Destruccion {

    @XmlElement(name = "Rangos", required = true)
    private RangoCodigosSeguridad rangos;
    @XmlAttribute(name = "RFC", required = true)
    private String rfc;
    @XmlAttribute(name = "Marca", required = true)
    private String marca;
    @XmlAttribute(name = "PlantaProduccion", required = true)
    private String plantaProduccion;
    @XmlAttribute(name = "MaquinaProduccion", required = true)
    private String maquinaProduccion;
    @XmlAttribute(name = "LoteProduccion", required = true)
    private String loteProduccion;
    @XmlAttribute(name = "CantidadProduccion", required = true)
    private Integer cantidadProduccion;
    @XmlAttribute(name = "TipoRetroalimentacion", required = true)
    private String tipoRetroalimentacion;
    @XmlAttribute(name = "Origen", required = true)
    private String origen;
    @XmlAttribute(name = "FechaHoraReg", required = true)
    @XmlSchemaType(name = "dateTime")
    private XMLGregorianCalendar fechaHoraReg;
    @XmlAttribute(name = "CantidadDestruccion", required = true)
    private Integer cantidadDestruccion;

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
     * Gets the value of the cantidadProduccion property.
     *
     * @return possible object is {@link Integer }
     *
     */
    public Integer getCantidadProduccion() {
        return cantidadProduccion;
    }

    /**
     * Sets the value of the cantidadProduccion property.
     *
     * @param value allowed object is {@link Integer }
     *
     */
    public void setCantidadProduccion(Integer value) {
        this.cantidadProduccion = value;
    }

    /**
     * Gets the value of the tipoRetroalimentacion property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getTipoRetroalimentacion() {
        return tipoRetroalimentacion;
    }

    /**
     * Sets the value of the tipoRetroalimentacion property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setTipoRetroalimentacion(String value) {
        this.tipoRetroalimentacion = value;
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
     * Gets the value of the fechaHoraReg property.
     *
     * @return possible object is {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getFechaHoraReg() {
        return fechaHoraReg;
    }

    /**
     * Sets the value of the fechaHoraReg property.
     *
     * @param value allowed object is {@link XMLGregorianCalendar }
     *
     */
    public void setFechaHoraReg(XMLGregorianCalendar value) {
        this.fechaHoraReg = value;
    }

    /**
     * Gets the value of the cantidadDestruccion property.
     *
     * @return possible object is {@link Integer }
     *
     */
    public Integer getCantidadDestruccion() {
        return cantidadDestruccion;
    }

    /**
     * Sets the value of the cantidadDestruccion property.
     *
     * @param value allowed object is {@link Integer }
     *
     */
    public void setCantidadDestruccion(Integer value) {
        this.cantidadDestruccion = value;
    }
}