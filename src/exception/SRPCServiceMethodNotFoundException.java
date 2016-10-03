package exception;

/**
 * @author Yevgeny Go
 *
 */
public class SRPCServiceMethodNotFoundException extends SRPCServiceException {
    private static final long serialVersionUID = 1L;

    public SRPCServiceMethodNotFoundException() {
        super();
    }

    /**
     * @param message
     */
    public SRPCServiceMethodNotFoundException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public SRPCServiceMethodNotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public SRPCServiceMethodNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public SRPCServiceMethodNotFoundException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
