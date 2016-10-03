package contract;

import java.util.Properties;

import exception.SRPCMalformedServiceException;

/**
 * @author Yevgeny Go
 *
 */
public interface Service {
    /**
     * Create the service objects 
     * @param props
     * @throws SRPCMalformedServiceException 
     */
    public void createObjects(Properties props) throws SRPCMalformedServiceException;

    /**
     * Check that the service exists
     * 
     * @param serviceName
     * @return boolean true if the service exists
     */
    public boolean isServiceExist(String serviceName);
    
    /**
     * Check that the method exists
     * 
     * @param serviceName
     * @param methodName
     * @param parameters
     * @return boolean true if the method exists
     */
    public boolean isMethodExist(String serviceName, String methodName, Object[] parameters);

    /**
     * Check that the method exists ignoring its parameters
     * 
     * @param serviceName
     * @param methodName
     * @return boolean true if the method exists
     */
    public boolean isMethodExist(String serviceName, String methodName);

    /**
     * Check that the method is void
     * 
     * @param serviceName
     * @param methodName
     * @param parameters
     * @return
     */
    public boolean isMethodVoid(String serviceName, String methodName, Object[] parameters);

    /**
     * Invoke the method
     * 
     * @param serviceName
     * @param methodName
     * @param parameters
     * @return Object result
     */
    public Object invokeMethod(String serviceName, String methodName, Object[] parameters);
}
