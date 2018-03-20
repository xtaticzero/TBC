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
public class ProduccionDaoException extends Exception {
    private static final long serialVersionUID = -5618597618488535093L;

    private Throwable throwable;

    public ProduccionDaoException(String message) {
        super(message);
    }

    public ProduccionDaoException(String message, Throwable throwable) {
        super(message);
        this.throwable = throwable;
    }

    public ProduccionDaoException(Throwable throwable) {
        super(throwable);
        this.throwable = throwable;
    }

    public Throwable getCause() {
        return throwable;
    }
}
