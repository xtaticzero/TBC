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
public class RetroCodigosException extends Exception {

    private static final long serialVersionUID = 839680993890575926L;

    public RetroCodigosException(Throwable throwable) {
        super(throwable);
    }

    public RetroCodigosException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public RetroCodigosException(String string) {
        super(string);
    }

    public RetroCodigosException() {
        super();
    }
}
