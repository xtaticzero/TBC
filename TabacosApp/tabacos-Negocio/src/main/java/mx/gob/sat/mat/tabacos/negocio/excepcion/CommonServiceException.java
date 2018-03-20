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
public class CommonServiceException extends Exception {

    private static final long serialVersionUID = 5021562679676752473L;

    public CommonServiceException(Throwable throwable) {
        super(throwable);
    }

    public CommonServiceException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public CommonServiceException(String string) {
        super(string);
    }

    public CommonServiceException() {
        super();
    }
}
