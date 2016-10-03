package contract;

import java.io.Serializable;

/**
 * @author Yevgeny Go
 *
 */
public interface Request extends Serializable {
    /**
     * 
     * @return session id
     */
    public int getId();
    
    /**
     * 
     * @return service name
     */
    public String getServiceName();
    
    /**
     * 
     * @return service method name
     */
    public String getMethodName();
    
    /**
     * 
     * @return service method's parameters
     */
    public Object[] getParameters();

    /**
     * 
     * @return TRUE if a client has the next request or FALSE otherwise
     */
    public boolean hasNext();
}
