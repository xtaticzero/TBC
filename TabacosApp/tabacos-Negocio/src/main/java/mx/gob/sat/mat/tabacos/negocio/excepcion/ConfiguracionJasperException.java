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
public class ConfiguracionJasperException extends Exception{

    private static final long serialVersionUID = 3070508731914374917L;

    public ConfiguracionJasperException() {
    }

    public ConfiguracionJasperException(String mensaje) {
        super(mensaje);
    }

    public ConfiguracionJasperException(Throwable causa) {
        super(causa);
    }

    public ConfiguracionJasperException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
