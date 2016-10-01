/**
 * 
 */
package contract;

import java.util.Properties;

/**
 * @author Yevgeny Go
 *
 */
public interface Service { // TODO
    /**
     * Create service objects 
     * @param props
     */
    public void createObjects(Properties props);

    /**
     * Check that service exists
     * @param serviceName
     * @return boolean true if the service exists
     */
    public boolean isServiceExist(String serviceName);
    
    /**
     * Check that method exists
     * @param serviceName
     * @param methodName
     * @param parameters
     * @return boolean true if the method exists
     */
    public boolean isMethodExist(String serviceName, String methodName, Object[] parameters);

    /**
     * 
     * @param serviceName
     * @param methodName
     * @param parameters
     * @return
     */
    public boolean isMethodVoid(String serviceName, String methodName, Object[] parameters);

    /**
     * 
     * @param serviceName
     * @param methodName
     * @param parameters
     * @return Object result
     */
    public Object invokeMethod(String serviceName, String methodName, Object[] parameters);
}
