/**
 * 
 */
package helper;

import contract.Response;

/**
 * @author Yevgeny Go
 *
 */
public class ResponseImpl implements Response {
    private static final long serialVersionUID = 1L;
    private int id;
    private Object result;
    
    /**
     * 
     */
    public ResponseImpl(int id, Object result) {
        this.id = id;
        this.result = result;
    }

    /* (non-Javadoc)
     * @see contract.Response#getId()
     */
    @Override
    public int getId() {
        return id;
    }

    /* (non-Javadoc)
     * @see contract.Response#getServiceResult()
     */
    @Override
    public Object getServiceResult() {
        return result;
    }

    public String toString() {
        return "Session id: " + getId() +
            " result: " + result;
    }
}
