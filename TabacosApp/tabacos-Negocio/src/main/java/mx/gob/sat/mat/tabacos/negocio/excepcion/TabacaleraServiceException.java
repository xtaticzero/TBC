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
public class TabacaleraServiceException extends Exception{
    private static final long serialVersionUID = -4071390704611366473L;
    
    public TabacaleraServiceException(Throwable throwable) {
        super(throwable);
    }

    public TabacaleraServiceException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public TabacaleraServiceException(String string) {
        super(string);
    }

    public TabacaleraServiceException() {
        super();
    }
}
