/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.modelo.exceptions;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
public class SolicitudDaoException extends Exception{
    private static final long serialVersionUID = 5176972736925323648L;
    
    private Throwable throwable;

    public SolicitudDaoException(String message) {
        super(message);
    }

    public SolicitudDaoException(String message, Throwable throwable) {
        super(message);
        this.throwable = throwable;
    }

    public SolicitudDaoException(Throwable throwable) {
        super(throwable);
        this.throwable = throwable;
    }

    public Throwable getCause() {
        return throwable;
    }
}
