package mx.gob.sat.mat.tabacos.vista.proveedor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import static mx.gob.sat.mat.tabacos.constants.Constantes.ARCHIVO_PDF;
import mx.gob.sat.mat.tabacos.constants.enums.DatosSATEnum;
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
import mx.gob.sat.mat.tabacos.negocio.CommonService;
import mx.gob.sat.mat.tabacos.negocio.ProveedorService;
import mx.gob.sat.mat.tabacos.negocio.ReporterService;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ProveedorServiceException;
import mx.gob.sat.mat.tabacos.negocio.excepcion.ReporterJasperException;
import mx.gob.sat.mat.tabacos.util.FechaUtil;
import mx.gob.sat.mat.tabacos.vista.AbstractManagedBean;
import static mx.gob.sat.mat.tabacos.vista.AbstractManagedBean.ERROR;
import mx.gob.sat.mat.tabacos.vista.helper.BajaProveedorHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Ing Salvador Pocteco Saldaña
 */
@Controller("bajaProveedorMBean")
@Scope(value = "view")
public class BajaProveedorMBean extends AbstractManagedBean {

    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    @Qualifier("commonService")
    private CommonService commonService;

    @Autowired
    private ReporterService reporterService;

    private Proveedor proveedor;
    private RelacionTabProv relacionProveedor;
    private Baja baja;
    private Acuse acuse;
    private BajaProveedorHelper bajaProveedorHelper;

    private List<Proveedor> proveedorList;
    private List<Estatus> estatusList;
    private List<Baja> bajaList;

    private static final String MSGEXITOBAJA = "La información se dio de baja de forma exitosa";
    private static final String MSGERROR = "Error al guardar la información";
    private static final String MSGERRORSERVICERFC = "El RFC ingresado no existe";
    private static final String MSGERRORRFCBAJA = "El RFC ingresado ya fue dado de baja";

    private Long opcionEstatus;

    private boolean deshabilitaBtnGuardarBaja;
    private boolean pnlAcuse;
    private boolean pnlPrincipal;

    private String idAcuseRecibo;
    private String fechaAcuse;

    @PostConstruct
    public void init() {
        validaAccesoProceso(IdentificadorProcesoEnum.PE_AUTORIZAR_REGISTRO.getDescripcion());
        habilitarPnlPrincipal();

        proveedor = new Proveedor();
        relacionProveedor = new RelacionTabProv();
        acuse = new Acuse();
        baja = new Baja();
        bajaProveedorHelper = new BajaProveedorHelper();

        baja.setFecBaja(new Date());
        this.deshabilitaBtnGuardarBaja = true;
    }

    public void buscaDatosProveedorBaja() {
        try {
            if (this.buscaExistenciaRFC() && validaProveedorPorDefecto(proveedor.getRfc())) {
                if (proveedorService != null) {
                    habilitaBtnGuardarBaja();

                    proveedorList = proveedorService.consultaDatosProveedor(proveedor);
                    for (Proveedor prov : proveedorList) {
                        proveedor.setIdProveedor(prov.getIdProveedor());
                        proveedor.setIdEstatus(prov.getIdEstatus());
                        proveedor.setRazonSocial(prov.getRazonSocial());
                        proveedor.setFecRegistro(prov.getFecRegistro());
                    }

                    bajaList = proveedorService.consultaDatosProveedorBaja(proveedor);
                    for (Baja bajaProv : bajaList) {
                        baja.setIdBaja(bajaProv.getIdBaja());
                        baja.setIdTabacalera(bajaProv.getIdTabacalera());
                        baja.setIdProveedor(bajaProv.getIdProveedor());
                        baja.setDescMotivoBaja(bajaProv.getDescMotivoBaja());
                        baja.setFecRegistro(bajaProv.getFecRegistro());
                        baja.setFecBaja(bajaProv.getFecBaja());
                    }

                    estatusList = proveedorService.selectedEstatus(proveedor);
                    if (estatusList.isEmpty()) {
                        setOpcionEstatus(null);
                    } else {
                        estatusList.remove(estatusList.size() - 1);
                        estatusList.remove(estatusList.size() - 1);
                    }

                    if (proveedor.getIdEstatus() == 1) {
                        opcionEstatus = estatusList.get(0).getIdEstatus();
                        habilitaCamposBaja();
                        baja.setDescMotivoBaja("");
                        baja.setFecBaja(new Date());
                        this.deshabilitaBtnGuardarBaja = true;
                    } else {
                        opcionEstatus = estatusList.get(1).getIdEstatus();
                        super.msgInfo(MSGERRORRFCBAJA);
                        bajaProveedorHelper.setDeshabilitaEstatusBaja(true);
                        bajaProveedorHelper.setDeshabilitaDescripcionBaja(true);
                        bajaProveedorHelper.setDeshabilitaFechaHoraBaja(true);
                        this.deshabilitaBtnGuardarBaja = true;
                    }
                }
            } else {
                if (!validaProveedorPorDefecto(proveedor.getRfc())) {
                    addMessage(INFO, getMessageResourceString("msg.error.informacion.no.modificable"));
                } else {
                    super.addErrorMessage(ERROR, MSGERRORSERVICERFC);
                }
                limpiarFormulario();
                limpiaListas();
                habilitaCamposBaja();
                this.deshabilitaBtnGuardarBaja = true;
            }
        } catch (ProveedorServiceException e) {
            getLogger().error("Error al consultar los datos del proveedor - Baja" + e.getMessage(), e);
        }

    }

    private boolean validaProveedorPorDefecto(String rfc) {
        return !(rfc.equals(DatosSATEnum.DATOS_FISCALES.getRfc()));
    }

    public void bajaProveedor() {
        try {
            proveedor.setIdEstatus(opcionEstatus);
            FechaUtil.formatFecha(baja.getFecBaja());

            if (!this.habilitaBtnGuardarBaja()) {
                proveedor = proveedorService.deleteProveedor(proveedor, null, baja, relacionProveedor);
                super.msgInfo(MSGEXITOBAJA);
                guardarSolicitudAcuse();
                registroMovimientoBitacora(getSession(), IdentificadorProcesoEnum.PE_AUTORIZAR_REGISTRO, new Date(), new Date(), MovimientosBitacoraEnum.BAJA_PROVEEDOR);
                this.deshabilitaBtnGuardarBaja = true;
            }
        } catch (ProveedorServiceException e) {
            getLogger().error("ERROR: Al dar de baja los datos del proveedor " + e.getMessage(), e);
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
        } catch (Exception ex) {
            getLogger().error(ex.getMessage());
        }
    }

    public boolean buscaExistenciaRFC() {
        proveedorList = new ArrayList();
        boolean respuesta = false;
        String rfcProveedor;
        rfcProveedor = proveedor.getRfc();
        rfcProveedor = rfcProveedor.toUpperCase();
        proveedor.setRfc(rfcProveedor);
        try {
            this.proveedorList = proveedorService.buscaRfc(proveedor);
            if (proveedorList != null && proveedorList.size() == 1) {
                respuesta = true;
            }
        } catch (ProveedorServiceException e) {
            getLogger().error("Error: Al buscar la existencia del RFC" + e.getMessage(), e);
        }
        return respuesta;
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
                bytesFile = reporterService.makeReport(ReportesTabacosEnum.REPORTE_ACUSE_BAJA_PROVEEDOR, ARCHIVO_PDF, parametrosReporte, null);
                if (bytesFile != null) {
                    generaDocumento(bytesFile, MIMETypesEnum.PDF, "AcuseDescargaBajaProveedor_" + idAcuseRecibo, FileExtensionEnum.PDF);
                }
            }
        } catch (ReporterJasperException e) {
            super.addErrorMessage(ERROR, "Error al generar el acuse");
            getLogger().error("Error al generar el Reporte en PDF" + e.getMessage());
        }

    }

    public boolean habilitaBtnGuardarBaja() {
        deshabilitaBtnGuardarBaja = false;
        proveedor.setIdEstatus(opcionEstatus);

        if (proveedor.getRfc() == null) {
            deshabilitaBtnGuardarBaja = true;
        }
        if (proveedor.getIdEstatus() == null || proveedor.getIdEstatus() == 1) {
            deshabilitaBtnGuardarBaja = true;
        }
        if (this.baja.getDescMotivoBaja() == null || baja.getDescMotivoBaja().isEmpty()) {
            deshabilitaBtnGuardarBaja = true;
        }
        if (this.baja.getFecBaja() == null) {
            deshabilitaBtnGuardarBaja = true;
        }
        return deshabilitaBtnGuardarBaja;
    }

    public void habilitaCamposBaja() {
        boolean habilita = false;

        bajaProveedorHelper.setDeshabilitaEstatusBaja(habilita);
        bajaProveedorHelper.setDeshabilitaDescripcionBaja(habilita);
        bajaProveedorHelper.setDeshabilitaFechaHoraBaja(habilita);
    }

    public void limpiarFormulario() {
        proveedor.setRfc("");
        proveedor.setRazonSocial("");
        baja.setDescMotivoBaja("");
        baja.setFecBaja(new Date());
        setOpcionEstatus(null);
    }

    public void limpiaListas() {
        estatusList = new ArrayList();
        estatusList.clear();
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

    public RelacionTabProv getRelacionProveedor() {
        return relacionProveedor;
    }

    public void setRelacionProveedor(RelacionTabProv relacionProveedor) {
        this.relacionProveedor = relacionProveedor;
    }

    public Baja getBaja() {
        return baja;
    }

    public void setBaja(Baja baja) {
        this.baja = baja;
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

    public List<Estatus> getEstatusList() {
        return estatusList;
    }

    public void setEstatusList(List<Estatus> estatusList) {
        this.estatusList = estatusList;
    }

    public List<Baja> getBajaList() {
        return bajaList;
    }

    public void setBajaList(List<Baja> bajaList) {
        this.bajaList = bajaList;
    }

    public Long getOpcionEstatus() {
        return opcionEstatus;
    }

    public void setOpcionEstatus(Long opcionEstatus) {
        this.opcionEstatus = opcionEstatus;
    }

    public boolean isDeshabilitaBtnGuardarBaja() {
        return deshabilitaBtnGuardarBaja;
    }

    public void setDeshabilitaBtnGuardarBaja(boolean deshabilitaBtnGuardarBaja) {
        this.deshabilitaBtnGuardarBaja = deshabilitaBtnGuardarBaja;
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

    public BajaProveedorHelper getBajaProveedorHelper() {
        return bajaProveedorHelper;
    }

    public void setBajaProveedorHelper(BajaProveedorHelper bajaProveedorHelper) {
        this.bajaProveedorHelper = bajaProveedorHelper;
    }

    public String getIdAcuseRecibo() {
        return idAcuseRecibo;
    }

    public void setIdAcuseRecibo(String idAcuseRecibo) {
        this.idAcuseRecibo = idAcuseRecibo;
    }
}
