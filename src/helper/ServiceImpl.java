/**
 * 
 */
package helper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Properties;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import contract.Service;
import server.ReflectHandler;
import exception.SRPCMalformedServiceException;
import exception.SRPCServiceException;
import exception.SRPCServiceMerhodThrowsException;
import exception.SRPCServiceMethodWrongParameterException;

/**
 * @author Yevgeny Go
 *
 */
public class ServiceImpl implements Service {
    private static Logger logger = LogManager.getLogger(ServiceImpl.class);
    private Properties properties;
    private HashMap<String, Object> services;
    
    /**
     * 
     * @param properties
     */
    public ServiceImpl(Properties properties) {
        this.properties = properties;
    }

    /* (non-Javadoc)
     * @see contract.Service#createServiceObjects(java.util.Properties)
     */
    @Override
    public void createObjects(Properties properties) throws SRPCMalformedServiceException {
        services = new HashMap<String, Object>();
        
        for (Entry<Object, Object> entry : properties.entrySet()) {
            logger.debug("Creating service object from the class '" +
                entry.getValue() + "'...");
            
            Object service = ReflectHandler.createObject((String) entry.getValue());
            services.put((String) entry.getKey(), service);
        }
        logger.debug("Service objects created: " + toString());
    }
    
    /* (non-Javadoc)
     * @see contract.Service#isServiceExist(String)
     */
    @Override
    public boolean isServiceExist(String serviceName) {
        return (properties.getProperty(serviceName) == null ? false : true);
    }

    /* (non-Javadoc)
     * @see contract.Service#isMethodExist(String, String, Object[])
     */
    @Override
    public boolean isMethodExist(String serviceName, String methodName,
            Object[] parameters) {
        Method m = ReflectHandler.getMethod(properties.getProperty(serviceName),
            methodName, parameters);
        
        return (m == null ? false : true);
    }

    /* (non-Javadoc)
     * @see contract.Service#isMethodExist(String, String)
     */
    @Override
    public boolean isMethodExist(String serviceName, String methodName) {
        Method m = ReflectHandler.getMethod(properties.getProperty(serviceName),
            methodName);
        
        return (m == null ? false : true);
    }

    /* (non-Javadoc)
     * @see contract.Service#isMethodVoid(String, String, Object[])
     */
    @Override
    public boolean isMethodVoid(String serviceName, String methodName,
            Object[] parameters) {
        Method m = ReflectHandler.getMethod(properties.getProperty(serviceName),
                methodName, parameters);
        
        return (m.getReturnType().toString().equalsIgnoreCase("void") ? true : false);
    }

    /* (non-Javadoc)
     * @see contract.Service#invokeMethod(String, String, Object[])
     */
    @Override
    public Object invokeMethod(String serviceName, String methodName,
            Object[] parameters) {
        Method method = ReflectHandler.getMethod(properties.getProperty(serviceName),
            methodName, parameters);
        Object result = null;
        try {
//            Class[] argTypes = new Class[] { String[].class };
//            result = method.invoke(getObject(serviceName), (Object) argTypes);
            result = method.invoke(getObject(serviceName), parameters);
        } catch (InvocationTargetException e) {
            result = new SRPCServiceMerhodThrowsException("Method " + serviceName +
                "." + methodName + " throws the exception: " + e.getTargetException());
        } catch (IllegalArgumentException e) {
            result = new SRPCServiceMethodWrongParameterException("Method " + serviceName +
                "." + methodName + " throws the exception: " + e);
        } catch (IllegalAccessException e) {
            logger.error("Catch:", e);
            result = new SRPCServiceException("Method " + serviceName +
                "." + methodName + " call caused the exception: " + e.getMessage());
        }
        
        return result;
    }
    
    @Override
    public String toString() {
        StringBuffer propStr = new StringBuffer("Service object map: ");
        for (Entry<String, Object> entry : services.entrySet()) {
            propStr.append(entry.getKey() + " => " + entry.getValue() + "\n");
        }
        
        return propStr.toString();
    }

    private Object getObject(String serviceName) {
        logger.debug("Requested service object: " + services.get(serviceName));
        
        return services.get(serviceName);
    }
}
