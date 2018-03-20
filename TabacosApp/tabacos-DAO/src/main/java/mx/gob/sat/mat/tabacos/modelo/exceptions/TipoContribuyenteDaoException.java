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
public class TipoContribuyenteDaoException extends Exception{
    private static final long serialVersionUID = -1228230107904432326L;
    
    private Throwable throwable;

    public TipoContribuyenteDaoException(String message) {
        super(message);
    }

    public TipoContribuyenteDaoException(String message, Throwable throwable) {
        super(message);
        this.throwable = throwable;
    }

    public TipoContribuyenteDaoException(Throwable throwable) {
        super(throwable);
        this.throwable = throwable;
    }

    public Throwable getCause() {
        return throwable;
    }
}
