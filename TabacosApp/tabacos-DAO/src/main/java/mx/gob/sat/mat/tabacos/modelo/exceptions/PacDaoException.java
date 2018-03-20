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
public class PacDaoException extends Exception {

    private static final long serialVersionUID = 4232061822376989530L;

    private Throwable throwable;

    public PacDaoException(String message) {
        super(message);
    }

    public PacDaoException(String message, Throwable throwable) {
        super(message);
        this.throwable = throwable;
    }

    public PacDaoException(Throwable throwable) {
        super(throwable);
        this.throwable = throwable;
    }

    public Throwable getCause() {
        return throwable;
    }
}
