/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.sat.mat.tabacos.negocio.excepcion;

/**
 *
 * @author Ing Emmanuel Estrada Gonzalez
 */
public class PacServiceException extends Exception{
    private static final long serialVersionUID = -3462173632426863681L;
    
    public PacServiceException(Throwable throwable) {
        super(throwable);
    }

    public PacServiceException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public PacServiceException(String string) {
        super(string);
    }

    public PacServiceException() {
        super();
    }
}
