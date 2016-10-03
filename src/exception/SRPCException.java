package exception;

/**
 * @author Yevgeny Go
 *
 */
public class SRPCException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public SRPCException() {
        super();
    }

    /**
     * @param message
     */
    public SRPCException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public SRPCException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public SRPCException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public SRPCException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
