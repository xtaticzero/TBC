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
public class ProduccionServiceException extends Exception {
    private static final long serialVersionUID = 2941004236825560608L;

    public ProduccionServiceException(Throwable throwable) {
        super(throwable);
    }

    public ProduccionServiceException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public ProduccionServiceException(String string) {
        super(string);
    }

    public ProduccionServiceException() {
        super();
    }
}
