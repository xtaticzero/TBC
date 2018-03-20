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
public class ReporteServiceException extends Exception{
    private static final long serialVersionUID = -1140543112418205880L;
    
    public ReporteServiceException(Throwable throwable) {
        super(throwable);
    }

    public ReporteServiceException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public ReporteServiceException(String string) {
        super(string);
    }

    public ReporteServiceException() {
        super();
    }
    
}
