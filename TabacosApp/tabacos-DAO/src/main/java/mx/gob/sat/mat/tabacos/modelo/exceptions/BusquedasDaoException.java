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
public class BusquedasDaoException extends Exception {

    private static final long serialVersionUID = 5630961071809049529L;
    private Throwable throwable;

    public BusquedasDaoException(String message) {
        super(message);
    }

    public BusquedasDaoException(String message, Throwable throwable) {
        super(message);
        this.throwable = throwable;
    }

    public BusquedasDaoException(Throwable throwable) {
        super(throwable);
        this.throwable = throwable;
    }

    @Override
    public Throwable getCause() {
        return throwable;
    }
}
