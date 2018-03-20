package mx.gob.sat.mat.tabacos.negocio.excepcion;

public class ServiceException extends Exception {

    private static final long serialVersionUID = 2194177296241385128L;
    private String message;
    private Throwable cause;

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.cause = cause;
    }

    public ServiceException(String message) {
        super(message);
        this.message = message;
    }

    public ServiceException(Throwable cause) {
        super(cause);
        this.message = cause.getMessage();
        this.cause = cause;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }
}
