/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.modelo.dto.filtro;

import java.io.Serializable;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
public class ProveedorFiltroDTO implements Serializable{
    private static final long serialVersionUID = -6511730252118908531L;
    
    private String rfc;

    /**
     * Get the value of rfc
     *
     * @return the value of rfc
     */
    public String getRfc() {
        return rfc;
    }

    /**
     * Set the value of rfc
     *
     * @param rfc new value of rfc
     */
    public void setRfc(String rfc) {
        this.rfc = rfc;
    }
}
