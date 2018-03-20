/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.negocio.excepcion;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
public class SolicitudServiceException extends Exception {
    private static final long serialVersionUID = 3162203978998677881L;

    public SolicitudServiceException(Throwable throwable) {
        super(throwable);
    }

    public SolicitudServiceException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public SolicitudServiceException(String string) {
        super(string);
    }

    public SolicitudServiceException() {
        super();
    }
}
