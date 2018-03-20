/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.modelo.dto;

import java.io.Serializable;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
public abstract class SolicitudDTO implements Serializable {

    private Long idSolicitud;

    public Long getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }
}
