/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.vista.busquedas;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import mx.gob.sat.mat.tabacos.modelo.dto.DatosBusqueda;
import mx.gob.sat.mat.tabacos.modelo.dto.Proveedor;
import mx.gob.sat.mat.tabacos.negocio.BusquedasService;
import mx.gob.sat.mat.tabacos.negocio.excepcion.BusinessException;
import mx.gob.sat.mat.tabacos.vista.AbstractManagedBean;
import mx.gob.sat.mat.tabacos.vista.util.Expresiones;
import mx.gob.sat.mat.tabacos.vista.util.ValidaRFC;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author MMMF
 */
@Controller("busquedas")
@Scope(value = "view")
public class BusquedasMBean extends AbstractManagedBean {

    protected static final Logger LOGGER = Logger.getLogger(BusquedasMBean.class);

    private static final String ERROR_FORMULARIO = "Se requiere por lo menos un campo para la búsqueda";
    private static final String ERROR_TBCODIGO = "No se puede realizar la búsqueda, no existe una tabla para el código proporcionado";
    private String errorFechas = "";

    private List<Proveedor> proveedores;
    private List<DatosBusqueda> busquedas;
    private String msgError;

    private String[] terminacionTabCod;
    
    //para obtener la terminacion de la tabla codigo
    private static final int INDICE_TERMINACION_TABLABD = 2; 

    private boolean incluyeCodigo;
    private Date maxFecha;
    private Date minFecha;
    private static final String ERROR_RFC = "El RFC que ingreso no es válido";
    
    @Autowired
    private BusquedasService busquedasService;

    private DatosBusqueda busquedaBean = new DatosBusqueda();

    public BusquedasMBean() {
        super();
    }

    @PostConstruct
    public void init() {
        //inicializacion de fechas
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - 1);
        minFecha = new Date(cal.getTimeInMillis());
        cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, cal.get(Calendar.DAY_OF_WEEK) + 1);
        maxFecha = new Date(cal.getTimeInMillis());

        // Asignaciones para prueba
        incluyeCodigo = false;
        busquedaBean = new DatosBusqueda();

        inicializaTerminacionCod();
    }

    public void btnBuscar() {

        boolean validacionCorrecta = true;
        msgError = "";
        errorFechas = "";

        if (validaCamposFormulario()) {
            validacionCorrecta = validaFechas(busquedaBean.getFecha(), busquedaBean.getFechaFin());

            if (validaCampo(busquedaBean.getCodigo())) {
                if (validaTerminacionTabCodigo(busquedaBean.getCodigo().trim())) {
                    //Dejar que siga
                    incluyeCodigo = true;
                } else {
                    //si no hay una tabla que termine con esa letra-numero, no permitir que continue la busqueda
                    validacionCorrecta = false;
                    incluyeCodigo = false;
                    msgError = ERROR_TBCODIGO;
                    busquedas = new ArrayList<DatosBusqueda>();
                }
            }
            if (validacionCorrecta) {
                try {
                    LOGGER.info("DATOS PARA BUSQ: " + busquedaBean);
                    busquedas = busquedasService.busquedaPorFiltros(busquedaBean);

                } catch (BusinessException ser) {
                    msgError = ser.getMessage();
                    LOGGER.error("Fallo la busqueda: " + msgError);
                }
            }

        } else {
            busquedas = new ArrayList<DatosBusqueda>();
            msgError = ERROR_FORMULARIO;
        }

    }

    public void validaRfcContribuyente() {
        String componente = "myForm:contribuyente";
        String rfc = busquedaBean.getRfcContribuyente();
        validarRfc(rfc, componente);

    }

    public void validaRfcProveedor() {
        String componente = "myForm:proveedor";
        String rfc = busquedaBean.getRfcProveedor();
        validarRfc(rfc, componente);
    }
    
    private boolean validaFechas(Date fechaIni, Date fechaFin) {
        boolean validacionCorrecta = true;
        if (fechaIni != null && fechaFin != null) {
            //es mayor la fecha inicial
            if (fechaFin.before(fechaIni)) {
                errorFechas = "Los intervalos de la fecha estan invertidos";
                validacionCorrecta = false;
            }
        } else if ((fechaIni != null && fechaFin == null)
                || (fechaIni == null && fechaFin != null)) {
            errorFechas = "Se debe seleccionar Fecha Inicio y Fecha Fin";
            validacionCorrecta = false;
        }
        return validacionCorrecta;
    }

    private void validarRfc(String texto, String compMsgError) {
        String patron = "^([A-Z&Ññ]{3}|[A-Z][AEIOU][A-Z]{2})\\d{2}((01|03|05|07|08|10|12)(0[1-9]|[12]\\d|3[01])|02(0[1-9]|[12]\\d)|(04|06|09|11)(0[1-9]|[12]\\d|30))([A-Z0-9]{2}[0-9A])?$";
        Pattern p = Pattern.compile(patron);

        if (texto != null && !texto.isEmpty()) {
            //Busca patron en el texto
            Matcher matcher = p.matcher(texto);
            if (matcher.matches()) {
                //Si cumple con la validacion del patron
            } else {
                //No cumple con el patron de RFC
            }

            if (!ValidaRFC.validaRFC(texto)) {
                super.msgError(compMsgError, ERROR_RFC);
            }
            if (texto.length() == Expresiones.RFC_LONGITUD) {
                if (!ValidaRFC.validaRFCFisica(texto)) {
                    super.msgError(compMsgError, ERROR_RFC);
                }
            } else if (texto.length() == Expresiones.RFCM_LONGITUD) {
                if (!ValidaRFC.validaRFCMoral(texto)) {
                    super.msgError(compMsgError, ERROR_RFC);
                }
            } else {
                super.msgError(compMsgError, ERROR_RFC);
            }
        }
    }

    private void inicializaTerminacionCod() {
        String[] temp = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        terminacionTabCod = temp;
    }

    private boolean validaTerminacionTabCodigo(String codigo) {
        if (validaCampo(codigo) && codigo.trim().length() >= INDICE_TERMINACION_TABLABD) {
            String terminaCod = "" + codigo.charAt(INDICE_TERMINACION_TABLABD);
            for (int i = 0; i < terminacionTabCod.length; i++) {
                if (terminacionTabCod[i].compareToIgnoreCase(terminaCod) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public void btnReset() {
        busquedas = new ArrayList<DatosBusqueda>();
        proveedores = new ArrayList<Proveedor>();
        busquedaBean = new DatosBusqueda();
        incluyeCodigo = false;
        msgError = "";
    }

    public List<DatosBusqueda> creaDatosPrueba() {
        List<DatosBusqueda> datosTest = new ArrayList<DatosBusqueda>();
        DatosBusqueda dato;
        final int numDatos = 10;

        for (int i = 0; i < numDatos; i++) {
            dato = new DatosBusqueda();
            dato.setFecha(new Date());
            dato.setLoteproduccion("LOTE " + i);
            dato.setMaquinaproduccion("MAQ " + i);
            dato.setMarca("MARCA " + i);
            dato.setPlantaproduccion("PLANTA " + i);
            dato.setRfcContribuyente("CONTRIBUYENTE: " + i);
            dato.setRfcProveedor("PROVEEDOR " + i);
            datosTest.add(dato);
        }

        return datosTest;
    }

    private boolean validaCamposFormulario() {
        boolean valido = true;
        
        
        if (busquedaBean != null) {
            boolean camposValidos = validaCampos (busquedaBean.getCodigo(),
                        busquedaBean.getLoteproduccion(),
                        busquedaBean.getMaquinaproduccion(),
                        busquedaBean.getMarca(),
                        busquedaBean.getPlantaproduccion(),
                        busquedaBean.getRfcContribuyente(),
                        busquedaBean.getRfcProveedor());
            
            if (!camposValidos && (busquedaBean.getFecha() == null && busquedaBean.getFechaFin() == null)) {
                valido = false;
            }
        }
        return valido;
    }
    
    private boolean validaCampos (String... campos) {
        int noCampos = campos.length;
        int noCamposInvalidos = 0;
        for (String campo : campos) {
            if (!validaCampo(campo)) {
                noCamposInvalidos ++;
            }
        }
        if (noCampos == noCamposInvalidos) {
            return false;
        }
        return true;
    }
    

    private boolean validaCampo(String campo) {
        return campo != null && !campo.isEmpty() && campo.trim().length() > 0;
    }

    public List<DatosBusqueda> getBusquedas() {
        return busquedas;
    }

    public void setBusquedas(List<DatosBusqueda> busquedas) {
        this.busquedas = busquedas;
    }

    public DatosBusqueda getBusquedaBean() {
        return busquedaBean;
    }

    public void setBusquedaBean(DatosBusqueda busquedaBean) {
        this.busquedaBean = busquedaBean;
    }

    public List<Proveedor> getProveedores() {
        return proveedores;
    }

    public BusquedasService getGenericoService() {
        return busquedasService;
    }

    public void setGenericoService(BusquedasService genericoService) {
        this.busquedasService = genericoService;
    }

    public boolean isIncluyeCodigo() {
        return incluyeCodigo;
    }

    public void setIncluyeCodigo(boolean incluyeCodigo) {
        this.incluyeCodigo = incluyeCodigo;
    }

    public String getMsgError() {
        return msgError;
    }

    public void setMsgError(String msgError) {
        this.msgError = msgError;
    }

    public String getErrorFechas() {
        return errorFechas;
    }

    public void setErrorFechas(String errorFechas) {
        this.errorFechas = errorFechas;
    }

    public Date getMinFecha() {
        return (minFecha != null) ? (Date)minFecha.clone() : null;
    }

    public void setMinFecha(Date minFecha) {
        this.minFecha = (minFecha != null) ? (Date)minFecha.clone() : null;
    }

    public Date getMaxFecha() {
        return (maxFecha != null) ? (Date)maxFecha.clone() : null;
    }

    public void setMaxFecha(Date maxFecha) {
        this.maxFecha = (maxFecha != null) ? (Date)maxFecha.clone() : null;
    }
}
