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
public class CodigosFalsoseInvalidosException extends Exception {

    private static final long serialVersionUID = 5330101959655943533L;

    public CodigosFalsoseInvalidosException(Throwable throwable) {
        super(throwable);
    }

    public CodigosFalsoseInvalidosException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public CodigosFalsoseInvalidosException(String string) {
        super(string);
    }

    public CodigosFalsoseInvalidosException() {
        super();
    }
}
