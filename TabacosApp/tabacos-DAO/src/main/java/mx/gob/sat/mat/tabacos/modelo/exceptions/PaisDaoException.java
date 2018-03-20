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
public class PaisDaoException extends Exception{
    private static final long serialVersionUID = 4335034209251998740L;
    
    private Throwable throwable;

    public PaisDaoException(String message) {
        super(message);
    }

    public PaisDaoException(String message, Throwable throwable) {
        super(message);
        this.throwable = throwable;
    }

    public PaisDaoException(Throwable throwable) {
        super(throwable);
        this.throwable = throwable;
    }

    public Throwable getCause() {
        return throwable;
    }
    
}
