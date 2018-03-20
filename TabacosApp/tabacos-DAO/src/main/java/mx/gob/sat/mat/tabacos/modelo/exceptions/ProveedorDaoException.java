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
public class ProveedorDaoException extends Exception{
    private static final long serialVersionUID = 7755756344593672493L;
    
    private Throwable throwable;

    public ProveedorDaoException(String message) {
        super(message);
    }

    public ProveedorDaoException(String message, Throwable throwable) {
        super(message);
        this.throwable = throwable;
    }

    public ProveedorDaoException(Throwable throwable) {
        super(throwable);
        this.throwable = throwable;
    }

    @Override
    public Throwable getCause() {
        return throwable;
    }
}
