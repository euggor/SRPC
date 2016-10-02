/**
 * 
 */
package exception;

/**
 * @author Yevgeny Go
 *
 */
public class SRPCMalformedServiceException extends SRPCServiceException {
    private static final long serialVersionUID = 1L;

    public SRPCMalformedServiceException() {
        super();
    }

    /**
     * @param message
     */
    public SRPCMalformedServiceException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public SRPCMalformedServiceException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public SRPCMalformedServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public SRPCMalformedServiceException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
