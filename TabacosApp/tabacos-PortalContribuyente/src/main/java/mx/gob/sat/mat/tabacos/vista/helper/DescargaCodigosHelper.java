/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.vista.helper;

import java.io.Serializable;
import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dto.solicitud.AutorizacionResol;
import mx.gob.sat.mat.tabacos.modelo.dto.Tabacalera;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
public class DescargaCodigosHelper implements Serializable{
    private static final long serialVersionUID = -7971139058860341245L;
    
    private Long idSolicitud;
    private String rfcTabacalera;
    private List<Tabacalera> lstTabacaleras; 
    private AutorizacionResol autorizacionResol;
    private List<AutorizacionResol> lstSolicitudResolucions;

    public DescargaCodigosHelper(List<Tabacalera> lstTabacaleras) {
        this.lstTabacaleras = lstTabacaleras;
    }

    public Long getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getRfcTabacalera() {
        return rfcTabacalera;
    }

    public void setRfcTabacalera(String rfcTabacalera) {
        this.rfcTabacalera = rfcTabacalera;
    }

    public List<Tabacalera> getLstTabacaleras() {
        return lstTabacaleras;
    }

    public void setLstTabacaleras(List<Tabacalera> lstTabacaleras) {
        this.lstTabacaleras = lstTabacaleras;
    }

    public List<AutorizacionResol> getLstSolicitudResolucions() {
        return lstSolicitudResolucions;
    }

    public void setLstSolicitudResolucions(List<AutorizacionResol> lstSolicitudResolucions) {
        this.lstSolicitudResolucions = lstSolicitudResolucions;
    }

    public AutorizacionResol getAutorizacionResol() {
        return autorizacionResol;
    }

    public void setAutorizacionResol(AutorizacionResol autorizacionResol) {
        this.autorizacionResol = autorizacionResol;
    }
}
