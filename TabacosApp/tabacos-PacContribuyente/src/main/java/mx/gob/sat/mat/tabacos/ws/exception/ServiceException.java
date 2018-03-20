/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.ws.exception;

import java.io.Serializable;

/**
 *
 * @author Ing. Gregorio Serapio Ramirez
 */
public class ServiceException extends Exception implements Serializable {

    private static final long serialVersionUID = 7922427091688796440L;

    public ServiceException(Throwable throwable) {
        super(throwable);
    }

    public ServiceException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public ServiceException(String string) {
        super(string);
    }

    public ServiceException() {
        super();
    }
}
