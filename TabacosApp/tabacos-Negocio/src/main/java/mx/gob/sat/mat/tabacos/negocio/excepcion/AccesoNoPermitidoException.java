package mx.gob.sat.mat.tabacos.negocio.excepcion;

import java.io.Serializable;

/**
 *  @author Jonathan Josue Gomez Bustamante 
 *  Clase para identificar los accesos a componentes por parte de usuarios no permitidos.
 * */
public class AccesoNoPermitidoException extends Exception implements Serializable {
    private static final long serialVersionUID = 8759567998193769965L;
    
    public AccesoNoPermitidoException(Throwable throwable) {
        super(throwable);
    }

    public AccesoNoPermitidoException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public AccesoNoPermitidoException(String string) {
        super(string);
    }

    public AccesoNoPermitidoException() {
        super();
    }
}
