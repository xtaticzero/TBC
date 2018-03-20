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
public class ReporterJasperException extends Exception {

    private static final long serialVersionUID = -1349976240286307429L;

    public ReporterJasperException(Throwable throwable) {
        super(throwable);
    }

    public ReporterJasperException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public ReporterJasperException(String string) {
        super(string);
    }

    public ReporterJasperException() {
        super();
    }
}
