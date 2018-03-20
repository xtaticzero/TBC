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
public class RangoFolioServiceExcepcion extends Exception{
    private static final long serialVersionUID = -4469534750078327677L;
    
    public RangoFolioServiceExcepcion(Throwable throwable) {
        super(throwable);
    }

    public RangoFolioServiceExcepcion(String string, Throwable throwable) {
        super(string, throwable);
    }

    public RangoFolioServiceExcepcion(String string) {
        super(string);
    }

    public RangoFolioServiceExcepcion() {
        super();
    }
}
