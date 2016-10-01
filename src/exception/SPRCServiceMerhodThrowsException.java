/**
 * 
 */
package exception;

/**
 * @author Yevgeny Go
 *
 */
public class SPRCServiceMerhodThrowsException extends SPRCServiceException {
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public SPRCServiceMerhodThrowsException() {
        super();
    }

    /**
     * @param message
     */
    public SPRCServiceMerhodThrowsException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public SPRCServiceMerhodThrowsException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public SPRCServiceMerhodThrowsException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public SPRCServiceMerhodThrowsException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
