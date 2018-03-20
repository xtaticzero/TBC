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
public class ArchivoFoliosServiceException extends Exception{
    private static final long serialVersionUID = -2955371934948971186L;
    
    public ArchivoFoliosServiceException(Throwable throwable) {
        super(throwable);
    }

    public ArchivoFoliosServiceException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public ArchivoFoliosServiceException(String string) {
        super(string);
    }

    public ArchivoFoliosServiceException() {
        super();
    }
}
