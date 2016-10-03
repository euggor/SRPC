package contract;

import java.io.Serializable;

/**
 * @author Yevgeny Go
 *
 */
public interface Response extends Serializable {
    /**
     * 
     * @return session id
     */
    public int getId();
    
    /**
     * 
     * @return service result
     */
    public Object getServiceResult();
}
