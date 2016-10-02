/**
 * 
 */
package exception;

/**
 * @author Yevgeny Go
 *
 */
public class SRPCServiceException extends SRPCException {
    private static final long serialVersionUID = 1L;

    public SRPCServiceException() {
        super();
    }

    /**
     * @param message
     */
    public SRPCServiceException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public SRPCServiceException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public SRPCServiceException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public SRPCServiceException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
