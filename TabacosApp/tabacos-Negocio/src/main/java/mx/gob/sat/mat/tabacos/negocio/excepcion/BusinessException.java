/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.negocio.excepcion;

import java.io.Serializable;

/**
 *
 * @author Ing. Gregorio Serapio Ramirez
 */
public class BusinessException extends Exception implements Serializable {

    private static final long serialVersionUID = 8932799789013707943L;

    public BusinessException(Throwable throwable) {
        super(throwable);
    }

    public BusinessException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public BusinessException(String string) {
        super(string);
    }

    public BusinessException() {
        super();
    }
}
