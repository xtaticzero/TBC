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
public class QRCodeUtilException extends Exception {

    private static final long serialVersionUID = 4738263598916139550L;

    public QRCodeUtilException(Throwable throwable) {
        super(throwable);
    }

    public QRCodeUtilException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public QRCodeUtilException(String string) {
        super(string);
    }

    public QRCodeUtilException() {
        super();
    }
}
