package mx.gob.sat.mat.tabacos.vista.proveedor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import static mx.gob.sat.mat.tabacos.constants.Constantes.ARCHIVO_PDF;
import mx.gob.sat.mat.tabacos.constants.enums.FileExtensionEnum;
import mx.gob.sat.mat.tabacos.constants.enums.IdentificadorProcesoEnum;
import mx.gob.sat.mat.tabacos.constants.enums.MIMETypesEnum;
import mx.gob.sat.mat.tabacos.constants.enums.MovimientosBitacoraEnum;
import mx.gob.sat.mat.tabacos.constants.enums.ReportesTabacosEnum;
import mx.gob.sat.mat.tabacos.constants.enums.TipoAcuse;
import mx.gob.sat.mat.tabacos.modelo.dto.archivo.Acuse;
import mx.gob.sat.mat.tabacos.modelo.dto.Baja;
import mx.gob.sat.mat.tabacos.modelo.dto.Estatus;
import mx.gob.sat.mat.tabacos.modelo.dto.Proveedor;
import mx.gob.sat.mat.tabacos.modelo.dto.RelacionTabProv;
import mx.gob.sat.mat.tabacos.modelo.dto.RepresentanteLegal;
import mx.gob.sat.mat.tabacos.modelo.dto.Tabacalera;
import mx.gob.sat.mat.tabacos.negocio.CommonService;
import mx.gob.sat.mat.tabacos.negocio.ProveedorService;
import mx.gob.sat.mat.tabacos.negocio.ReporterService;
import mx.gob.sat.mat.tabacos.negocio.TabacaleraService;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ProveedorServiceException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ReporterJasperException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.TabacaleraServiceException;
import mx.gob.sat.mat.tabacos.vista.AbstractManagedBean;
import static mx.gob.sat.mat.tabacos.vista.AbstractManagedBean.ERROR;
import mx.gob.sat.mat.tabacos.vista.helper.CambioProveedorHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Ing Salvador Pocteco Saldaña
 */
@Controller("cambioProveedorMBean")
@Scope(value = "view")
public class CambioProveedorMBean extends AbstractManagedBean {

    private static final Logger LOGGER = Logger.getLogger(CambioProveedorMBean.class);

    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    @Qualifier("commonService")
    private CommonService commonService;

    @Autowired
    private ReporterService reporterService;
    
    @Autowired
    @Qualifier("tababacaleraService")
    private TabacaleraService tababacaleraService;

    private static final String MSGEXITOACTUALIZAR = "La información se actualizó de forma exitosa";
    private static final String MSGERROR = "Error al guardar la información";
    private static final String MSGERRORSERVICERFC = "El RFC ingresado no existe";
    private static final String MSGERRORRFCBAJA = "El RFC ingresado ya fue dado de baja";

    private Proveedor proveedor;
    private RepresentanteLegal representante;
    private RelacionTabProv relacionProveedor;
    private Tabacalera tabacalera;
    private Baja baja;
    private Acuse acuse;
    private CambioProveedorHelper cambioProveedorHelper;

    private List<Proveedor> proveedorList;
    private List<RepresentanteLegal> representanteList;
    private List<Tabacalera> tabacaleraList;
    private List<Tabacalera> tabacaleraListAux;
    private List<Estatus> estatusList;

    private Long opcionRfcCliente;
    private Long opcionEstatus;
    private Long idTabacaleraTem;

    private boolean deshabilitaBtnGuardarCambio;
    private boolean pnlAcuse;
    private boolean pnlPrincipal;

    private String idAcuseRecibo;
    private String fechaAcuse;

    @PostConstruct
    public void init() {
        validaAccesoProceso(IdentificadorProcesoEnum.PE_AUTORIZAR_REGISTRO.getDescripcion());
        habilitarPnlPrincipal();

        proveedor = new Proveedor();
        representante = new RepresentanteLegal();
        tabacalera = new Tabacalera();
        relacionProveedor = new RelacionTabProv();
        acuse = new Acuse();
        cambioProveedorHelper = new CambioProveedorHelper();
        this.deshabilitaBtnGuardarCambio = true;
    }

    public void buscaDatosProveedorCambio() {
        try {
            if (this.buscaExistenciaRFC()) {
                if (proveedorService != null) {
                    proveedorList = proveedorService.consultaDatosProveedor(proveedor);
                    representanteList = proveedorService.consultaDatosRepresentante(representante, proveedor);

                    for (Proveedor prov : proveedorList) {
                        proveedor.setIdProveedor(prov.getIdProveedor());
                        proveedor.setRazonSocial(prov.getRazonSocial());
                        proveedor.setDomicilio(prov.getDomicilio());
                        proveedor.setFecRegistro(prov.getFecRegistro());
                    }
                    for (RepresentanteLegal rep : representanteList) {
                        representante.setNombre(rep.getNombre());
                        representante.setApellidoPaterno(rep.getApellidoPaterno());
                        representante.setApellidoMaterno(rep.getApellidoMaterno());
                        representante.setCorreoElectronico(rep.getCorreoElectronico());
                        representante.setTelefono(rep.getTelefono());
                        representante.setIdEstatus(rep.getIdEstatus());
                        representante.setIdProveedor(rep.getIdProveedor());
                        representante.setIdTabacalera(rep.getIdTabacalera());
                        representante.setIdRepLegal(rep.getIdRepLegal());
                    }

                    tabacaleraList = tababacaleraService.selectedRfcClienteAlta();
                    tabacaleraListAux = tababacaleraService.selectedRfcClienteCambio(proveedor);
                    asignaOpcionCliente(tabacaleraList, tabacaleraListAux);
                    estatusList = proveedorService.selectedEstatus(proveedor);

                    if (estatusList.isEmpty()) {
                        setOpcionRfcCliente(null);
                    } else {
                        estatusList.remove(estatusList.size() - 1);
                        estatusList.remove(estatusList.size() - 1);
                    }
                    if (tabacaleraListAux == null || tabacaleraListAux.isEmpty()) {
                        setOpcionRfcCliente(null);
                    } else {
                        opcionRfcCliente = tabacaleraListAux.get(0).getIdTabacalera();
                        idTabacaleraTem = opcionRfcCliente;
                    }
                    if (representante.getIdEstatus() == 1) {
                        opcionEstatus = estatusList.get(0).getIdEstatus();
                        habilitaCamposCambio();
                    } else {
                        opcionEstatus = estatusList.get(1).getIdEstatus();
                        super.msgInfo(MSGERRORRFCBAJA);
                        deshabilitaCamposCambio();
                    }
                }
            } else {
                super.addErrorMessage(ERROR, MSGERRORSERVICERFC);
                limpiarFormulario();
                limpiaListas();
                habilitaCamposCambio();
                this.deshabilitaBtnGuardarCambio = true;
            }
        } catch (TabacaleraServiceException e) {
            LOGGER.error("Error al consultar los datos del tabacalera" + e.getMessage(), e);
        } catch (ProveedorServiceException ex) {
            LOGGER.error("Error al consultar los datos del proveedor" + ex.getMessage(), ex);
        }
    }

    /*Asigna al combo la opcion con el que fue dado de alta*/
    private void asignaOpcionCliente(List<Tabacalera> tabacaleraList, List<Tabacalera> tabacaleraListAux) {
        for (Tabacalera tabacaleraSelected : tabacaleraListAux) {
            for (Tabacalera tabacaleraOpc : tabacaleraList) {
                if (tabacaleraSelected.getRfc().equals(tabacaleraOpc.getRfc())) {
                    opcionRfcCliente = tabacaleraSelected.getIdTabacalera();
                }
            }
        }
    }

    public boolean buscaExistenciaRFC() {
        proveedorList = new ArrayList();
        boolean respuesta = false;
        String rfcProveedor = proveedor.getRfc();
        rfcProveedor = rfcProveedor.toUpperCase();
        proveedor.setRfc(rfcProveedor);
        try {
            this.proveedorList = proveedorService.buscaRfc(proveedor);
            if (proveedorList != null && proveedorList.size() == 1) {
                respuesta = true;
            }
        } catch (ProveedorServiceException e) {
            LOGGER.error("Error: Al buscar la existencia del RFC" + e.getMessage(), e);
        }
        return respuesta;
    }

    /*
     *Metodo encargado de generar el Acuse
     */
    public void guardarSolicitudAcuse() {
        try {
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            proveedor.setFecRegistro(new Date());
            fechaAcuse = formato.format(proveedor.getFecRegistro());
            acuse.setSerieAcuse(TipoAcuse.SOLICITUD_ALTA_PROVEEDOR);
            acuse.setFecCaptura(new Date());
            acuse.setIdProveedor(proveedor.getIdProveedor());
            idAcuseRecibo = commonService.crearAcuse(acuse);
            habilitarPnlAcuse();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    public void actualizaProveedor() {
        try {
            if (!this.habilitaBtnGuardarCambio()) {
                tabacalera.setIdTabacalera(opcionRfcCliente);
                if (!idTabacaleraTem.equals(tabacalera.getIdTabacalera())) {
                    proveedor = proveedorService.updateProveedorRelacional(relacionProveedor, proveedor, tabacalera.getIdTabacalera(), representante, baja, idTabacaleraTem);
                } else {
                    proveedor = proveedorService.updateProveedor(proveedor, representante, tabacalera.getIdTabacalera(), relacionProveedor);
                }
                super.msgInfo(MSGEXITOACTUALIZAR);
                guardarSolicitudAcuse();
                registroMovimientoBitacora(getSession(),IdentificadorProcesoEnum.PE_AUTORIZAR_REGISTRO,new Date(),new Date(),MovimientosBitacoraEnum.MODIFICACION_PROVEEDOR);
                this.deshabilitaBtnGuardarCambio = true;
            }
        } catch (ProveedorServiceException e) {
            LOGGER.error("ERROR: Al actualizar los datos del proveedor " + e.getMessage(), e);
            super.msgError(MSGERROR);
        }
    }

    /**
     * Metodo encargado de generar el Reporte del acuse en formato PDF
     *
     */
    public void btnGenerarReportePDF() {
        try {
            byte[] bytesFile;
            Map parametrosReporte;
            parametrosReporte = new HashMap();

            parametrosReporte.put("rfc", proveedor.getRfc());
            parametrosReporte.put("fecha", new Date());
            parametrosReporte.put("folio", idAcuseRecibo);

            if (reporterService != null) {
                bytesFile = reporterService.makeReport(ReportesTabacosEnum.REPORTE_ACUSE_CAMBIO_PROVEEDOR, ARCHIVO_PDF, parametrosReporte, null);
                if (bytesFile != null) {
                    generaDocumento(bytesFile, MIMETypesEnum.PDF, "AcuseDescargaCambioProveedor_" + idAcuseRecibo, FileExtensionEnum.PDF);
                }
            }
        } catch (ReporterJasperException e) {
            super.addErrorMessage(ERROR, "Error al generar el acuse");
            LOGGER.error("Error al generar el Reporte en PDF" + e.getMessage());
        }

    }

    public boolean habilitaBtnGuardarCambio() {
        tabacalera.setIdTabacalera(opcionRfcCliente);
        deshabilitaBtnGuardarCambio = false;

        if (representante.getNombre().length() == 0) {
            deshabilitaBtnGuardarCambio = true;
        }
        if (representante.getApellidoPaterno().length() == 0) {
            deshabilitaBtnGuardarCambio = true;
        }
        if (representante.getApellidoMaterno().length() == 0) {
            deshabilitaBtnGuardarCambio = true;
        }
        if (representante.getCorreoElectronico().length() == 0) {
            deshabilitaBtnGuardarCambio = true;
        }
        if (representante.getTelefono().length() == 0) {
            deshabilitaBtnGuardarCambio = true;
        }
        if (tabacalera.getIdTabacalera() == null) {
            deshabilitaBtnGuardarCambio = true;
        }
        return deshabilitaBtnGuardarCambio;
    }

    public void limpiarFormulario() {
        proveedor.setRfc("");
        proveedor.setRazonSocial("");
        proveedor.setDomicilio("");
        representante.setNombre("");
        representante.setApellidoMaterno("");
        representante.setApellidoPaterno("");
        representante.setCorreoElectronico("");
        representante.setTelefono("");
        setOpcionRfcCliente(null);
        setOpcionEstatus(null);
    }

    public void limpiaListas() {
        estatusList = new ArrayList();
        tabacaleraListAux = new ArrayList();
        tabacaleraList = new ArrayList();
        estatusList.clear();
        tabacaleraListAux.clear();
        tabacaleraList.clear();
    }

    public void habilitarPnlAcuse() {
        pnlPrincipal = false;
        pnlAcuse = true;
    }

    public void habilitarPnlPrincipal() {
        pnlPrincipal = true;
        pnlAcuse = false;
    }

    public void deshabilitaCamposCambio() {
        boolean bandera = true;

        cambioProveedorHelper.setDeshabilitaFechaCambio(bandera);
        cambioProveedorHelper.setDeshabilitaRepresenCambio(bandera);
        cambioProveedorHelper.setDeshabilitaPaternoCambio(bandera);
        cambioProveedorHelper.setDeshabilitaMaternoCambio(bandera);
        cambioProveedorHelper.setDeshabilitaTelefonoCambio(bandera);
        cambioProveedorHelper.setDeshabilitaCorreoCambio(bandera);
        cambioProveedorHelper.setDeshabilitaRfcClienteCambio(bandera);
        deshabilitaBtnGuardarCambio = true;
    }

    public void habilitaCamposCambio() {
        boolean bandera = false;

        cambioProveedorHelper.setDeshabilitaFechaCambio(bandera);
        cambioProveedorHelper.setDeshabilitaRepresenCambio(bandera);
        cambioProveedorHelper.setDeshabilitaPaternoCambio(bandera);
        cambioProveedorHelper.setDeshabilitaMaternoCambio(bandera);
        cambioProveedorHelper.setDeshabilitaTelefonoCambio(bandera);
        cambioProveedorHelper.setDeshabilitaCorreoCambio(bandera);
        cambioProveedorHelper.setDeshabilitaRfcClienteCambio(bandera);
        this.deshabilitaBtnGuardarCambio = false;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public RepresentanteLegal getRepresentante() {
        return representante;
    }

    public void setRepresentante(RepresentanteLegal representante) {
        this.representante = representante;
    }

    public RelacionTabProv getRelacionProveedor() {
        return relacionProveedor;
    }

    public void setRelacionProveedor(RelacionTabProv relacionProveedor) {
        this.relacionProveedor = relacionProveedor;
    }

    public Tabacalera getTabacalera() {
        return tabacalera;
    }

    public void setTabacalera(Tabacalera tabacalera) {
        this.tabacalera = tabacalera;
    }

    public Acuse getAcuse() {
        return acuse;
    }

    public void setAcuse(Acuse acuse) {
        this.acuse = acuse;
    }

    public CambioProveedorHelper getCambioProveedorHelper() {
        return cambioProveedorHelper;
    }

    public void setCambioProveedorHelper(CambioProveedorHelper cambioProveedorHelper) {
        this.cambioProveedorHelper = cambioProveedorHelper;
    }

    public List<Proveedor> getProveedorList() {
        return proveedorList;
    }

    public void setProveedorList(List<Proveedor> proveedorList) {
        this.proveedorList = proveedorList;
    }

    public List<RepresentanteLegal> getRepresentanteList() {
        return representanteList;
    }

    public void setRepresentanteList(List<RepresentanteLegal> representanteList) {
        this.representanteList = representanteList;
    }

    public List<Tabacalera> getTabacaleraList() {
        return tabacaleraList;
    }

    public void setTabacaleraList(List<Tabacalera> tabacaleraList) {
        this.tabacaleraList = tabacaleraList;
    }

    public List<Tabacalera> getTabacaleraListAux() {
        return tabacaleraListAux;
    }

    public void setTabacaleraListAux(List<Tabacalera> tabacaleraListAux) {
        this.tabacaleraListAux = tabacaleraListAux;
    }

    public List<Estatus> getEstatusList() {
        return estatusList;
    }

    public void setEstatusList(List<Estatus> estatusList) {
        this.estatusList = estatusList;
    }

    public Long getOpcionRfcCliente() {
        return opcionRfcCliente;
    }

    public void setOpcionRfcCliente(Long opcionRfcCliente) {
        this.opcionRfcCliente = opcionRfcCliente;
    }

    public Long getOpcionEstatus() {
        return opcionEstatus;
    }

    public void setOpcionEstatus(Long opcionEstatus) {
        this.opcionEstatus = opcionEstatus;
    }

    public boolean isDeshabilitaBtnGuardarCambio() {
        return deshabilitaBtnGuardarCambio;
    }

    public void setDeshabilitaBtnGuardarCambio(boolean deshabilitaBtnGuardarCambio) {
        this.deshabilitaBtnGuardarCambio = deshabilitaBtnGuardarCambio;
    }

    public boolean isPnlAcuse() {
        return pnlAcuse;
    }

    public void setPnlAcuse(boolean pnlAcuse) {
        this.pnlAcuse = pnlAcuse;
    }

    public boolean isPnlPrincipal() {
        return pnlPrincipal;
    }

    public void setPnlPrincipal(boolean pnlPrincipal) {
        this.pnlPrincipal = pnlPrincipal;
    }

    public String getFechaAcuse() {
        return fechaAcuse;
    }

    public void setFechaAcuse(String fechaAcuse) {
        this.fechaAcuse = fechaAcuse;
    }

    public String getIdAcuseRecibo() {
        return idAcuseRecibo;
    }

    public void setIdAcuseRecibo(String idAcuseRecibo) {
        this.idAcuseRecibo = idAcuseRecibo;
    }

}
