/**
 * 
 */
package helper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Properties;
import java.util.Map.Entry;

import contract.Service;
import server.ReflectHandler;
import exception.SPRCServiceMerhodThrowsException;

/**
 * @author Yevgeny Go
 *
 */
public class ServiceImpl implements Service {
    private Properties properties;
    private HashMap<String, Object> services;
    
    public ServiceImpl(Properties properties) {
        this.properties = properties;
    }

    /* (non-Javadoc)
     * @see contract.Service#createServiceObjects(java.util.Properties)
     */
    @Override
    public void createObjects(Properties properties) {
        services = new HashMap<String, Object>();
        
        for (Entry<Object, Object> entry : properties.entrySet()) {
            System.out.println("Creating service object from the class '" +
                entry.getValue() + "'...");
            
            Object service = ReflectHandler.createObject((String) entry.getValue());
            services.put((String) entry.getKey(), service);
        }
        System.out.println("Service objects created: " + toString());
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
     * @see contract.Service#isMethodVoid(java.util.Properties)
     */
    @Override
    public boolean isMethodVoid(String serviceName, String methodName,
            Object[] parameters) {
        // TODO Auto-generated method stub
        return false;
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
//            Class[] argTypes = new Class[] { String[].class }; // TODO
//            result = method.invoke(getObject(serviceName), (Object) argTypes);
            result = method.invoke(getObject(serviceName), parameters);
        } catch (InvocationTargetException e) {
            result = new SPRCServiceMerhodThrowsException("Method " + serviceName +
                "." + methodName + " throws the exception: " + e.getTargetException());
        } catch (IllegalAccessException | IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } // TODO
        
        return result;
    }
    
    public String toString() {
        StringBuffer propStr = new StringBuffer("Service object map: ");
        for (Entry<String, Object> entry : services.entrySet()) {
            propStr.append(entry.getKey() + " => " + entry.getValue() + "\n");
        }
        return propStr.toString();
    }

    private Object getObject(String serviceName) {
        System.out.println("Requested service object:" + services.get(serviceName));
        return services.get(serviceName); // TODO: no such service/method handling
    }
}
