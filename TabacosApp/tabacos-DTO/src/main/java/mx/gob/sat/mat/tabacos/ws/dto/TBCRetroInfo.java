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

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TBCRetroInfo", propOrder = {
    "produccion",
    "destruccion",
    "codigosNoValidos"
}, namespace = "http://esquemas.clouda.sat.gob.mx/archivos/Tabacos/1/Retroalimentacion")
@XmlRootElement(name = "TBCRetroInfo", namespace = "http://esquemas.clouda.sat.gob.mx/archivos/Tabacos/1/Retroalimentacion")
public class TBCRetroInfo {

    @XmlElement(name = "Produccion")
    private List<Produccion> produccion;   
    @XmlElement(name = "Destruccion")
    private List<Destruccion> destruccion;
    @XmlElement(name = "CodigosNoValidos")
    private List<CodigosNoValidos> codigosNoValidos;
    @XmlAttribute(name = "Version", required = true)
    private String version;
    @XmlAttribute(name = "Fecha", required = true)
    @XmlSchemaType(name = "dateTime")
    private XMLGregorianCalendar fecha;
    @XmlAttribute(name = "RFCProveedorCertificado", required = true)
    private String rfcProveedorCertificado;

    /**
     * Gets the value of the infoAsociada property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the infoAsociada property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInfoAsociada().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TBCRetroInfo.Produccion }
     *
     *
     * @return 
     */
    public List<Produccion> getProduccion() {
        if (produccion == null) {
            produccion = new ArrayList<Produccion>();
        }
        return this.produccion;
    }

    /**
     * Gets the value of the destruccion property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the destruccion property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDestruccion().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TBCRetroInfo.Destruccion }
     *
     *
     * @return 
     */
    public List<Destruccion> getDestruccion() {
        if (destruccion == null) {
            destruccion = new ArrayList<Destruccion>();
        }
        return this.destruccion;
    }

    /**
     * Gets the value of the codigoFalso property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the codigoFalso property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCodigoFalso().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TBCRetroInfo.CodigoFalso }
     *
     *
     * @return 
     */
    public List<CodigosNoValidos> getCodigosNoValidos() {
        if (codigosNoValidos == null) {
            codigosNoValidos = new ArrayList<CodigosNoValidos>();
        }
        return this.codigosNoValidos;
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
     * Gets the value of the fecha property.
     *
     * @return possible object is {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getFecha() {
        return fecha;
    }

    /**
     * Sets the value of the fecha property.
     *
     * @param value allowed object is {@link XMLGregorianCalendar }
     *
     */
    public void setFecha(XMLGregorianCalendar value) {
        this.fecha = value;
    }

    /**
     * Gets the value of the rfcProveedorCertificado property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getRFCProveedorCertificado() {
        return rfcProveedorCertificado;
    }

    /**
     * Sets the value of the rfcProveedorCertificado property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setRFCProveedorCertificado(String value) {
        this.rfcProveedorCertificado = value;
    }
}
