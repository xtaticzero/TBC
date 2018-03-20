package mx.gob.sat.mat.tabacos.modelo.exceptions;

public class DaoException extends Exception {

    @SuppressWarnings("compatibility:5827960623862007844")
    private static final long serialVersionUID = 1L;
    private Throwable throwable;

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable throwable) {
        super(message);
        this.throwable = throwable;
    }

    public DaoException(Throwable throwable) {
        super(throwable);
        this.throwable = throwable;
    }

    public Throwable getCause() {
        return throwable;
    }

}
