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
public class SelloDigitalException extends Exception {

    private static final long serialVersionUID = 3937161955419296165L;

    public SelloDigitalException(Throwable throwable) {
        super(throwable);
    }

    public SelloDigitalException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public SelloDigitalException(String string) {
        super(string);
    }

    public SelloDigitalException() {
        super();
    }
}
