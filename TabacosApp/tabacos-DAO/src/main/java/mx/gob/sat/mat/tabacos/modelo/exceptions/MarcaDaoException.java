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
public class MarcaDaoException extends Exception {

    private static final long serialVersionUID = 7154418753303192393L;
    private Throwable throwable;

    public MarcaDaoException(String message) {
        super(message);
    }

    public MarcaDaoException(String message, Throwable throwable) {
        super(message);
        this.throwable = throwable;
    }

    public MarcaDaoException(Throwable throwable) {
        super(throwable);
        this.throwable = throwable;
    }

    @Override
    public Throwable getCause() {
        return throwable;
    }
}
