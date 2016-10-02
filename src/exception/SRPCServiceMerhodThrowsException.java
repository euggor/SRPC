/**
 * 
 */
package exception;

/**
 * @author Yevgeny Go
 *
 */
public class SRPCServiceMerhodThrowsException extends SRPCServiceException {
    private static final long serialVersionUID = 1L;

    public SRPCServiceMerhodThrowsException() {
        super();
    }

    /**
     * @param message
     */
    public SRPCServiceMerhodThrowsException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public SRPCServiceMerhodThrowsException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public SRPCServiceMerhodThrowsException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public SRPCServiceMerhodThrowsException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
