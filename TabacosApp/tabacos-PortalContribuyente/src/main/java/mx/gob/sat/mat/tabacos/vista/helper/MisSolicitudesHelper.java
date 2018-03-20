/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.vista.helper;

import java.io.Serializable;
import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dto.solicitud.SolicitudResolucion;
import mx.gob.sat.mat.tabacos.modelo.dto.Tabacalera;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
public class MisSolicitudesHelper implements Serializable{
    private static final long serialVersionUID = -2106509303827257587L;
    
    private String rfcTabacalera;
    private List<Tabacalera> lstTabacaleras;    
    private List<SolicitudResolucion> lstSolicitudes;

    public MisSolicitudesHelper(List<Tabacalera> lstTabacaleras, List<SolicitudResolucion> lstSolicitudes) {
        this.lstTabacaleras = lstTabacaleras;
        this.lstSolicitudes = lstSolicitudes;
    }
    
    public List<Tabacalera> getLstTabacaleras() {
        return lstTabacaleras;
    }

    public void setLstTabacaleras(List<Tabacalera> lstTabacaleras) {
        this.lstTabacaleras = lstTabacaleras;
    }

    public List<SolicitudResolucion> getLstSolicitudes() {
        return lstSolicitudes;
    }

    public void setLstSolicitudes(List<SolicitudResolucion> lstSolicitudes) {
        this.lstSolicitudes = lstSolicitudes;
    }

    public String getRfcTabacalera() {
        return rfcTabacalera;
    }

    public void setRfcTabacalera(String rfcTabacalera) {
        this.rfcTabacalera = rfcTabacalera;
    }
}
