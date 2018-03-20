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
public class RangosException extends Exception implements Serializable {

    private static final long serialVersionUID = 2622321096749142264L;

    public RangosException(Throwable throwable) {
        super(throwable);
    }

    public RangosException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public RangosException(String string) {
        super(string);
    }

    public RangosException() {
        super();
    }
}
