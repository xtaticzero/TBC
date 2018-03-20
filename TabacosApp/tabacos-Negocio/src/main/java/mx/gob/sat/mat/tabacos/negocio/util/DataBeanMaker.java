/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.negocio.util;

import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
public class DataBeanMaker {
    private List<?> lstDetalle;

    public List<?> getLstDetalle() {
        return lstDetalle;
    }

    public void setLstDetalle(List<?> lstDetalle) {
        this.lstDetalle = lstDetalle;
    }
    
    public JRDataSource getDsDetalle() {
        return new JRBeanCollectionDataSource(lstDetalle);
    }
    
}
