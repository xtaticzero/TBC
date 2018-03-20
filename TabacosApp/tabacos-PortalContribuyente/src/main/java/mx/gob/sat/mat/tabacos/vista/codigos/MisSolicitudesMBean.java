/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.vista.codigos;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import mx.gob.sat.mat.tabacos.constants.enums.IdentificadorProcesoEnum;
import mx.gob.sat.mat.tabacos.modelo.dto.PaisOrigen;
import mx.gob.sat.mat.tabacos.modelo.dto.solicitud.SolicitudResolucion;
import mx.gob.sat.mat.tabacos.modelo.dto.ValidarAccesoRespuesta;
import mx.gob.sat.mat.tabacos.negocio.SolicitudService;
import mx.gob.sat.mat.tabacos.negocio.excepcion.SolicitudServiceException;
import mx.gob.sat.mat.tabacos.vista.AbstractManagedBean;
import static mx.gob.sat.mat.tabacos.vista.AbstractManagedBean.SESSION_ACCESO;
import mx.gob.sat.mat.tabacos.vista.helper.MisSolicitudesHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
@Controller("misSolicitudesMB")
@Scope(value = "view")
public class MisSolicitudesMBean extends AbstractManagedBean {

    private MisSolicitudesHelper misSolicitudesHelper;
    private ValidarAccesoRespuesta accesoRespuesta;
    public static final String TABLE_MSGEMTY ="No se encontraron resultados";

    @Autowired
    @Qualifier("solicitudService")
    private SolicitudService solicitudService;

    @PostConstruct
    public void init() {
        validaAccesoProceso(IdentificadorProcesoEnum.CONTRIBUYENTE_TRAMITES.getDescripcion());
        accesoRespuesta = (ValidarAccesoRespuesta) getSession().getAttribute(SESSION_ACCESO);
        misSolicitudesHelper = new MisSolicitudesHelper(accesoRespuesta.getTabacaleras(), new ArrayList<SolicitudResolucion>());
        if (!misSolicitudesHelper.getLstTabacaleras().isEmpty()) {
            misSolicitudesHelper.setRfcTabacalera(misSolicitudesHelper.getLstTabacaleras().get(0).getRfc());
            consultarSolicitudes();
        }
    }

    public void consultarSolicitudes() {
        try {
            List<PaisOrigen> lstPaises;
            misSolicitudesHelper.setLstSolicitudes(solicitudService.buscarSolicitudesTabacalera(misSolicitudesHelper.getRfcTabacalera()));
            
            if ((misSolicitudesHelper.getLstSolicitudes() == null) || (misSolicitudesHelper.getLstSolicitudes().isEmpty())) {
                addMessage(INFO, TABLE_MSGEMTY);
            }else{
                Long[] idPaises = new Long[misSolicitudesHelper.getLstSolicitudes().size()];
                int indice=0;
                for(SolicitudResolucion solRes:misSolicitudesHelper.getLstSolicitudes()){
                    idPaises[indice]=solRes.getIdPaisOrigen();
                    indice++;
                }
                lstPaises = solicitudService.getLstOrigen(idPaises);
                
                if((lstPaises!=null)&&(!lstPaises.isEmpty())){
                    solicitudService.asignarPaisOrigen(lstPaises, misSolicitudesHelper.getLstSolicitudes());
                }
            }
        } catch (SolicitudServiceException ex) {
            getLogger().error(ex);
            super.msgInfo(TABLE_MSGEMTY);
        }

    }
    
    public MisSolicitudesHelper getMisSolicitudesHelper() {
        return misSolicitudesHelper;
    }

    public void setMisSolicitudesHelper(MisSolicitudesHelper misSolicitudesHelper) {
        this.misSolicitudesHelper = misSolicitudesHelper;
    }

    public ValidarAccesoRespuesta getAccesoRespuesta() {
        return accesoRespuesta;
    }

    public void setAccesoRespuesta(ValidarAccesoRespuesta accesoRespuesta) {
        this.accesoRespuesta = accesoRespuesta;
    }

}
