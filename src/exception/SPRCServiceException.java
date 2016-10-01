/**
 * 
 */
package exception;

/**
 * @author Yevgeny Go
 *
 */
public class SPRCServiceException extends SPRCException {
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public SPRCServiceException() {
        super();
    }

    /**
     * @param message
     */
    public SPRCServiceException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public SPRCServiceException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public SPRCServiceException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public SPRCServiceException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
