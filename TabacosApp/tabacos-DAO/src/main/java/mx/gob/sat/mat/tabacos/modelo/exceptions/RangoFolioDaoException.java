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
public class RangoFolioDaoException extends Exception{
    private static final long serialVersionUID = -7319421340093561691L;
    private Throwable throwable;

    public RangoFolioDaoException(String message) {
        super(message);
    }

    public RangoFolioDaoException(String message, Throwable throwable) {
        super(message);
        this.throwable = throwable;
    }

    public RangoFolioDaoException(Throwable throwable) {
        super(throwable);
        this.throwable = throwable;
    }

    public Throwable getCause() {
        return throwable;
    }
}
