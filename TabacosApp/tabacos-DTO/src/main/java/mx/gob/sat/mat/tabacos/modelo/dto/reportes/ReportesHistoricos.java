/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.modelo.dto.reportes;

import java.io.Serializable;
import java.util.List;
import mx.gob.sat.mat.tabacos.modelo.dto.Historico;

/**
 *
 * @author MTI Agustin Romero Mata
 */
public class ReportesHistoricos implements Serializable {

    private static final long serialVersionUID = -2919581623859090715L;
    private List<Historico> historicos;

    /**
     *
     */
    public ReportesHistoricos() {
        this.historicos = null;
    }

    /**
     *
     * @param historicos
     */
    public ReportesHistoricos(List<Historico> historicos) {
        this.historicos = historicos;
    }

    /**
     *
     * @return
     */
    public List<Historico> getHistoricos() {
        return historicos;
    }

    /**
     *
     * @param historicos
     */
    public void setHistoricos(List<Historico> historicos) {
        this.historicos = historicos;
    }
}
