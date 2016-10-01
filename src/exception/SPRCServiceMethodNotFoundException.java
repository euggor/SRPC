/**
 * 
 */
package exception;

/**
 * @author Yevgeny Go
 *
 */
public class SPRCServiceMethodNotFoundException extends SPRCServiceException {
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public SPRCServiceMethodNotFoundException() {
        super();
    }

    /**
     * @param message
     */
    public SPRCServiceMethodNotFoundException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public SPRCServiceMethodNotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public SPRCServiceMethodNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public SPRCServiceMethodNotFoundException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
