/**
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package mx.gob.sat.mat.tabacos.modelo.exceptions;

/**
 *
 * @author MOLD8871
 */
public class PersistenceException extends RuntimeException {

    private static final long serialVersionUID = 2194177296241385128L;
    private String message;
    private Throwable cause;

    public PersistenceException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.cause = cause;
    }

    public PersistenceException(String message) {
        super(message);
        this.message = message;
    }

    public PersistenceException(Throwable cause) {
        super(cause);
        this.message = cause.getMessage();
        this.cause = cause;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getCause() {
        return cause;
    }
}
