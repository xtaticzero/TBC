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
public class TabacaleraDaoException extends Exception{
    private static final long serialVersionUID = 3953412141601976156L;
    
    private Throwable throwable;

    public TabacaleraDaoException(String message) {
        super(message);
    }

    public TabacaleraDaoException(String message, Throwable throwable) {
        super(message);
        this.throwable = throwable;
    }

    public TabacaleraDaoException(Throwable throwable) {
        super(throwable);
        this.throwable = throwable;
    }

    @Override
    public Throwable getCause() {
        return throwable;
    }
    
}
