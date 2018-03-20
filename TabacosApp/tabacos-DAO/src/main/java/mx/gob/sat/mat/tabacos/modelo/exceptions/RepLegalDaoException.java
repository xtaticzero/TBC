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
public class RepLegalDaoException extends Exception {

    private static final long serialVersionUID = -3311771760006680232L;
    private Throwable throwable;

    public RepLegalDaoException(String message) {
        super(message);
    }

    public RepLegalDaoException(String message, Throwable throwable) {
        super(message);
        this.throwable = throwable;
    }

    public RepLegalDaoException(Throwable throwable) {
        super(throwable);
        this.throwable = throwable;
    }

    public Throwable getCause() {
        return throwable;
    }
}
