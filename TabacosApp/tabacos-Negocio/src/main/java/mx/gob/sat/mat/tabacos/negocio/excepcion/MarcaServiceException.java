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
public class MarcaServiceException extends Exception{
    private static final long serialVersionUID = 2923441653386780100L;
    
    public MarcaServiceException(Throwable throwable) {
        super(throwable);
    }

    public MarcaServiceException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public MarcaServiceException(String string) {
        super(string);
    }

    public MarcaServiceException() {
        super();
    }
}
