package mx.gob.sat.mat.tabacos.vista.proveedor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import mx.gob.sat.mat.rfc.service.ConsultaRFCService;
import mx.gob.sat.mat.rfc.service.InformacionUsuario;
import static mx.gob.sat.mat.tabacos.constants.Constantes.ARCHIVO_PDF;
import mx.gob.sat.mat.tabacos.constants.enums.FileExtensionEnum;
import mx.gob.sat.mat.tabacos.constants.enums.IdentificadorProcesoEnum;
import mx.gob.sat.mat.tabacos.constants.enums.MIMETypesEnum;
import mx.gob.sat.mat.tabacos.constants.enums.MovimientosBitacoraEnum;
import mx.gob.sat.mat.tabacos.constants.enums.ReportesTabacosEnum;
import mx.gob.sat.mat.tabacos.constants.enums.TipoAcuse;
import mx.gob.sat.mat.tabacos.modelo.dto.archivo.Acuse;
import mx.gob.sat.mat.tabacos.modelo.dto.Proveedor;
import mx.gob.sat.mat.tabacos.modelo.dto.RelacionTabProv;
import mx.gob.sat.mat.tabacos.modelo.dto.RepresentanteLegal;
import mx.gob.sat.mat.tabacos.modelo.dto.Tabacalera;
import mx.gob.sat.mat.tabacos.negocio.CommonService;
import mx.gob.sat.mat.tabacos.negocio.ProveedorService;
import mx.gob.sat.mat.tabacos.negocio.ReporterService;
import mx.gob.sat.mat.tabacos.negocio.TabacaleraService;
import mx.gob.sat.mat.tabacos.negocio.excepcion.CommonServiceException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ProveedorServiceException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ReporterJasperException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.TabacaleraServiceException;
import mx.gob.sat.mat.tabacos.vista.AbstractManagedBean;
import static mx.gob.sat.mat.tabacos.vista.AbstractManagedBean.ERROR;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Ing Salvador Pocteco Saldaña
 */
@Controller("altaProveedorMBean")
@Scope(value = "view")
public class AltaProveedorMBean extends AbstractManagedBean {

    private static final Logger LOGGER = Logger.getLogger(AltaProveedorMBean.class);

    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private ConsultaRFCService consultaRFCService;

    @Autowired
    @Qualifier("commonService")
    private CommonService commonService;
    
    @Autowired
    @Qualifier("tababacaleraService")
    private TabacaleraService tababacaleraService;

    @Autowired
    private ReporterService reporterService;

    private static final String MSGEXITOGUARDAR = "La información se guardó de forma exitosa";
    private static final String MSGERROR = "Error al guardar la información";
    private static final String MSGERRORRFC = "El RFC ingresado ya fue dado de alta";
    private static final String MSGERRORSERVICERFC = "El RFC ingresado no existe";

    private Proveedor proveedor;
    private RepresentanteLegal representante;
    private RelacionTabProv relacionProveedor;
    private Tabacalera tabacalera;
    private Acuse acuse;

    private List<Proveedor> proveedorList;
    private List<Tabacalera> tabacaleraList;

    private Long opcionRfcCliente;

    private boolean deshabilitaBtnGuardarAlta;
    private boolean pnlAcuse;
    private boolean pnlPrincipal;

    private String rfcProveedor;
    private Date fechaMaxima;
    private String fechaAcuse;
    private String idAcuseRecibo;

    @PostConstruct
    public void init() {
        validaAccesoProceso(IdentificadorProcesoEnum.PE_AUTORIZAR_REGISTRO.getDescripcion());
        habilitarPnlPrincipal();

        proveedor = new Proveedor();
        representante = new RepresentanteLegal();
        tabacalera = new Tabacalera();
        relacionProveedor = new RelacionTabProv();
        acuse = new Acuse();

        fechaMaxima = new Date();
        proveedor.setFecRegistro(new Date());
        this.deshabilitaBtnGuardarAlta = true;
    }

    public void buscaRFCService() {
        try {
            rfcProveedor = proveedor.getRfc();
            rfcProveedor = rfcProveedor.toUpperCase();
            InformacionUsuario informacionUsuario;
            String razonSocial;
            String domicilioCompleto;

            if (consultaRFCService != null) {
                informacionUsuario = consultaRFCService.consultaRFC(rfcProveedor);

                if (informacionUsuario != null) {

                    domicilioCompleto = concatenaDomicilioFiscal(informacionUsuario);
                    proveedor.setDomicilio(domicilioCompleto);

                    if (informacionUsuario.getVoIdentificacion().getNombreCompleto() != null) {
                        razonSocial = informacionUsuario.getVoIdentificacion().getNombreCompleto();
                        if (razonSocial != null) {
                            proveedor.setRazonSocial(razonSocial);
                        } else {
                            proveedor.setRazonSocial("");
                        }
                    }
                    if (proveedorService != null) {
                        tabacaleraList = tababacaleraService.selectedRfcClienteAlta();
                    } else {
                        setOpcionRfcCliente(null);
                    }
                } else {
                    super.addErrorMessage(ERROR, MSGERRORSERVICERFC);
                    limpiarFormulario();
                    this.deshabilitaBtnGuardarAlta = true;
                }
            }
        } catch (TabacaleraServiceException e) {
            addErrorMessage(ERROR, "Error al consultar El RFC del servicio");
            LOGGER.error("Error al consultar El RFC del servicio" + e.getMessage(), e);
        }
    }

    private String concatenaDomicilioFiscal(InformacionUsuario infoUsuario) {
        StringBuilder cadena = new StringBuilder();

        if (infoUsuario != null) {
            if (infoUsuario.getVoUbicacion().getCalle() != null) {
                cadena.append(infoUsuario.getVoUbicacion().getCalle());
                cadena.append(" ");
            }
            if (infoUsuario.getVoUbicacion().getNumeroInterior() != null) {
                cadena.append(infoUsuario.getVoUbicacion().getNumeroInterior());
                cadena.append(" ");
            }
            if (infoUsuario.getVoUbicacion().getNumeroExterior() != null) {
                cadena.append(infoUsuario.getVoUbicacion().getNumeroExterior());
                cadena.append(" ");
            }
            if (infoUsuario.getVoUbicacion().getDescripcionColonia() != null) {
                cadena.append(infoUsuario.getVoUbicacion().getDescripcionColonia());
                cadena.append(" ");
            }
            if (infoUsuario.getVoUbicacion().getDescripcionMunicipio() != null) {
                cadena.append(infoUsuario.getVoUbicacion().getDescripcionMunicipio());
            }
        } else {
            cadena.append("");
        }
        return cadena.toString();
    }

    public void guardaProveedor() {
        try {
            rfcProveedor = rfcProveedor.toUpperCase();
            proveedor.setRfc(rfcProveedor);
            tabacalera.setIdTabacalera(opcionRfcCliente);

            if (!this.habilitaBtnGuardarAlta()) {
                if (!this.buscaExistenciaRFC()) {
                    proveedor = proveedorService.insertProveedor(proveedor, representante, tabacalera.getIdTabacalera(), relacionProveedor);
                    super.msgInfo(MSGEXITOGUARDAR);
                    guardarSolicitudAcuse();
                    registroMovimientoBitacora(getSession(),IdentificadorProcesoEnum.PE_AUTORIZAR_REGISTRO,new Date(),new Date(),MovimientosBitacoraEnum.REGISTRO_PROVEEDOR);
                    this.deshabilitaBtnGuardarAlta = true;
                } else {
                    super.msgWarn(MSGERRORRFC);
                    limpiarFormulario();
                    tabacaleraList = new ArrayList();
                    tabacaleraList.clear();
                    this.deshabilitaBtnGuardarAlta = true;
                }
            }
        } catch (ProveedorServiceException e) {
            LOGGER.error("ERROR: Al guardar los datos del proveedor" + e.getMessage(), e);
            super.msgError(MSGERROR);
        }
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
        } catch (CommonServiceException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * Metodo encargado de generar el Reporte del acuse en formato PDF
     *
     */
    public void btnGenerarReportePDF() {
        try {
            byte[] bytesFile;
            bytesFile = null;
            Map parametrosReporte;
            parametrosReporte = new HashMap();

            parametrosReporte.put("rfc", proveedor.getRfc());
            parametrosReporte.put("fecha", new Date());
            parametrosReporte.put("folio", idAcuseRecibo);

            if (reporterService != null) {
                bytesFile = reporterService.makeReport(ReportesTabacosEnum.REPORTE_ACUSE_ALTA_PROVEEDOR, ARCHIVO_PDF, parametrosReporte, null);
                if (bytesFile != null) {
                    generaDocumento(bytesFile, MIMETypesEnum.PDF, "AcuseDescargaAltaProveedor_" + idAcuseRecibo, FileExtensionEnum.PDF);
                }
            }
        } catch (ReporterJasperException e) {
            super.addErrorMessage(ERROR, "Error al generar el acuse");
            LOGGER.error("Error al generar el Reporte en PDF" + e.getMessage());
        }

    }

    public boolean buscaExistenciaRFC() {
        proveedorList = new ArrayList();
        boolean respuesta = false;
        rfcProveedor = proveedor.getRfc();
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

    public boolean habilitaBtnGuardarAlta() {
        tabacalera.setIdTabacalera(opcionRfcCliente);
        deshabilitaBtnGuardarAlta = false;
        
        if (representante.getNombre().length() == 0) {
            deshabilitaBtnGuardarAlta = true;
        }
        if (representante.getApellidoPaterno().length() == 0) {
            deshabilitaBtnGuardarAlta = true;
        }
        if (representante.getApellidoMaterno().length() == 0) {
            deshabilitaBtnGuardarAlta = true;
        }
        if (representante.getCorreoElectronico().length() == 0) {
            deshabilitaBtnGuardarAlta = true;
        }
        if (representante.getTelefono().length() == 0) {
            deshabilitaBtnGuardarAlta = true;
        }
        if (tabacalera.getIdTabacalera() == null) {
            deshabilitaBtnGuardarAlta = true;
        }
        return deshabilitaBtnGuardarAlta;
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
    }

    public void habilitarPnlAcuse() {
        pnlPrincipal = false;
        pnlAcuse = true;
    }

    public void habilitarPnlPrincipal() {
        pnlPrincipal = true;
        pnlAcuse = false;
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

    public List<Proveedor> getProveedorList() {
        return proveedorList;
    }

    public void setProveedorList(List<Proveedor> proveedorList) {
        this.proveedorList = proveedorList;
    }

    public List<Tabacalera> getTabacaleraList() {
        return tabacaleraList;
    }

    public void setTabacaleraList(List<Tabacalera> tabacaleraList) {
        this.tabacaleraList = tabacaleraList;
    }

    public Long getOpcionRfcCliente() {
        return opcionRfcCliente;
    }

    public void setOpcionRfcCliente(Long opcionRfcCliente) {
        this.opcionRfcCliente = opcionRfcCliente;
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

    public void setFechaMaxima(final Date fechaMaxima) {
        this.fechaMaxima = (fechaMaxima != null) ? (Date) fechaMaxima.clone() : null;
    }

    public Date getFechaMaxima() {
        return (fechaMaxima != null) ? (Date) fechaMaxima.clone() : null;
    }

    public boolean isDeshabilitaBtnGuardarAlta() {
        return deshabilitaBtnGuardarAlta;
    }

    public void setDeshabilitaBtnGuardarAlta(boolean deshabilitaBtnGuardarAlta) {
        this.deshabilitaBtnGuardarAlta = deshabilitaBtnGuardarAlta;
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
