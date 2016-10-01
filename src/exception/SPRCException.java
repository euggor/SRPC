/**
 * 
 */
package exception;

/**
 * @author Yevgeny Go
 *
 */
public class SPRCException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public SPRCException() {
        super();
    }

    /**
     * @param message
     */
    public SPRCException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public SPRCException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public SPRCException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public SPRCException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
