/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.negocio.util.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import mx.gob.sat.mat.tabacos.constants.enums.HorasDeConsultaEnum;
import mx.gob.sat.siat.modelo.dto.AccesoUsr;
import org.apache.log4j.Logger;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
public final class Utilerias {

    private static final Logger LOG = Logger.getLogger(Utilerias.class);

    public static final String DDMMYYYYHHMM = "dd-MM-yyyy HH:mm";
    
    private Utilerias() {
    }

    /**
     *
     * @param date objeto fecha que se desea formatear
     * @return cadena con el formato de fecha yyyy-mm-dd
     */
    public static String formatearFechaAAAAMMDD(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    /**
     *
     * @param date objeto fecha que se desea formatear
     * @return cadena con el formato de fecha yyyy-mm-dd
     */
    public static String formatearFechaDDMMYYYY(Date date) {
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }

    /**
     *
     * @param date objeto fecha que se desea formatear
     * @return cadena con el formato de fecha dd-mm-yyyy
     */
    public static String formatearFechaDDMMAAAA(Date date) {
        return new SimpleDateFormat("dd-MM-yyyy").format(date);
    }

    /**
     *
     * @param date objeto fecha que se desea formatear
     * @return cadena con el formato de fecha yyyy-mm-dd
     * @throws java.text.ParseException
     */
    public static Date formatearFechaAAAAMMDDHHMM(String date) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
    }

    public static Date formatearFechaDDMMAAAAHHMM(String date) throws ParseException {
        return new SimpleDateFormat("dd/MM/yyyy").parse(date);
    }

    public static String formatearFechaAAAAMMDDHHMMSS(Date date) {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(date);
    }

    public static String formatearFechaDDMMAAAAHHMM(Date date) {
        return new SimpleDateFormat("dd-MM-yyyy HH:mm").format(date);
    }

    public static String formatearFechaDDMMAAAAHHMMSS(Date date) {
        return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(date);
    }

    public static String formatearFechaYYYYMMDDHHMMSS(Date date) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    public static Date formatearFechaYYYYMMDDHHMMSS(String date) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
    }

    public static XMLGregorianCalendar fechaHoy() {
        XMLGregorianCalendar xgcal = null;
        try {
            GregorianCalendar gcal = new GregorianCalendar();
            xgcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
        } catch (DatatypeConfigurationException e) {
            LOG.error(e);
        }
        return xgcal;
    }

    public static XMLGregorianCalendar dateToXMLGCalendar(Date date) {
        XMLGregorianCalendar xgcal = null;
        try {
            if (date != null) {
                GregorianCalendar gcal = new GregorianCalendar();
                gcal.setTime(date);
                xgcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
            }
        } catch (DatatypeConfigurationException e) {
            LOG.error(e);
        }
        return xgcal;
    }

    public static Date xmlgCalendarToDate(XMLGregorianCalendar xgcal) {
        if (xgcal == null) {
            return Calendar.getInstance().getTime();
        }
        return xgcal.toGregorianCalendar().getTime();
    }

    public static AccesoUsr obtenerAccesoUsrEmpleados() {
        AccesoUsr usuario = new AccesoUsr();

        usuario.setNombreCompleto("");
        usuario.setUsuario("TCU3706081A6");
        usuario.setMenu("");
        usuario.setTipoAuth("");
        usuario.setNumeroEmp("");
        usuario.setSessionID("");
        usuario.setSessionIDNovell("");
        usuario.setLocalidad("");
        usuario.setLocalidadOp("");
        usuario.setUsuarioOficina("");
        usuario.setLocalidadCRM("");
        usuario.setRoles("");
        usuario.setIdTipoPersona("");
        usuario.setRfcCorto("");

        return usuario;
    }

    public static String obtenerNobreArchivo(String rutaAbsoluta) {
        String nombreArchivo;
        nombreArchivo = rutaAbsoluta;
        String[] segmentos;
        segmentos = nombreArchivo.split("/");
        if (segmentos.length > 1) {
            nombreArchivo = segmentos[segmentos.length - 1];
        } else {
            segmentos = null;
            segmentos = rutaAbsoluta.split("\\\\");
            if (segmentos.length > 1) {
                nombreArchivo = segmentos[segmentos.length - 1];
            }
        }

        return nombreArchivo;
    }

    public static Date chageFecha(Date fechaCambio, HorasDeConsultaEnum horaFijada) {
        String fecha = new SimpleDateFormat(DDMMYYYYHHMM).format(fechaCambio);
        String fechaModificada;

        String[] fechaHora = fecha.split(" ");

        if (fechaHora.length > 0) {
            try {
                fechaHora[fechaHora.length-1] = horaFijada.getDescripcion();
                fechaModificada = fechaHora[fechaHora.length-fechaHora.length] + " " + fechaHora[fechaHora.length-1];
                return new SimpleDateFormat(DDMMYYYYHHMM).parse(fechaModificada);
            } catch (ParseException ex) {
                return fechaCambio;
            }
        }

        return fechaCambio;
    }
}
