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
public class ReporteDaoException extends Exception{
    private static final long serialVersionUID = 2161696736468883356L;
    
    private Throwable throwable;

    public ReporteDaoException(String message) {
        super(message);
    }

    public ReporteDaoException(String message, Throwable throwable) {
        super(message);
        this.throwable = throwable;
    }

    public ReporteDaoException(Throwable throwable) {
        super(throwable);
        this.throwable = throwable;
    }

    public Throwable getCause() {
        return throwable;
    }
}
