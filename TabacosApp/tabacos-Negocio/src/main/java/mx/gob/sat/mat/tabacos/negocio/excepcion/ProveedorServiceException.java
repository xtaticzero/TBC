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
public class ProveedorServiceException extends Exception{
    private static final long serialVersionUID = 1812058793320610555L;
    
    public ProveedorServiceException(Throwable throwable) {
        super(throwable);
    }

    public ProveedorServiceException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public ProveedorServiceException(String string) {
        super(string);
    }

    public ProveedorServiceException() {
        super();
    }
    
}
