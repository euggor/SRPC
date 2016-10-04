/**
 * 
 */
package server;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import exception.SRPCMalformedServiceException;

/**
 * @author Yevgeny Go
 *
 */
public class ReflectHandler {
    private static Logger logger = LogManager.getLogger(ReflectHandler.class);
    
    /**
     * Create an object from the class name
     * 
     * @param className
     * @return Object created object
     * @throws SRPCMalformedServiceException 
     */
    public static Object createObject(String className) throws SRPCMalformedServiceException {
        Object obj = null;
        try {
            Class<?> clazz = Class.forName(className);
            Constructor<?> ctor = clazz.getConstructor(); // TODO: Parameters in constructor
            logger.debug("Constructor " + ctor);
            obj = ctor.newInstance();
            logger.info("Created service object: " + obj);
        } catch (InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException
                | ClassNotFoundException | NoSuchMethodException
                | SecurityException e) {
            throw new SRPCMalformedServiceException("Unable to create new instance from the object " +
                className + ": " + e);
        }
        
        return obj;
    }

    /**
     * Get method
     * 
     * @param className
     * @param methodName
     * @param methodParameters
     * @return Method
     */
    public static Method getMethod(String className, String methodName,
            Object[] methodParameters) {
        return getMethodInternal(className, methodName, methodParameters, false);
    }

    /**
     * Get method ignoring its parameters
     * 
     * @param className
     * @param methodName
     * @return Method
     */
    public static Method getMethod(String className, String methodName) {
        return getMethodInternal(className, methodName, null, true);
    }

    /**
     * 
     * @param className
     * @param methodName
     * @param methodParameters
     * @param ignoreParameters
     * @return
     */
    private static Method getMethodInternal(String className, String methodName,
            Object[] methodParameters, boolean ignoreParameters) {
        logger.debug("Getting method from " + className + "." + methodName);

        Class<?> clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            logger.warn("Catch: ", e);
            return null;
        }
        
        Method[] allMethods = clazz.getDeclaredMethods();
        Method method = null;
        for (Method m : allMethods) {
             if (m.getName().equals(methodName)) {
                 if (ignoreParameters) {
                     method = m;
                     break;
                 }
                 
                 Parameter[] params = m.getParameters();
                 if (methodParameters != null && methodParameters.length > 0) { // parameterized method
                     if (params.length == methodParameters.length) { // TODO more precise work with method signatures
                         logger.debug("Method " + className + "." +
                             methodName + "(" + params + ") found:\n" + methodDetails(m));
                         method = m;
                         break;
                     }
                 } else {
                     if (params.length == 0) { // method with no parameters
                         logger.debug("Method " + className + "." +
                             methodName + "() found:\n\n" + methodDetails(m));
                         method = m;
                         break;
                     }
                 }
             }
        }
        
        if (method == null) {
            logger.debug("Method " + className + "." +
                methodName + " not found!");
        }
        return method;
    }

    private static String methodDetails(Method method) {
        StringBuffer detailsStr = new StringBuffer(">>>>>>>>>>\nMethod name: " +
            method.getName() + "\n\tGeneric name: " + method.toGenericString() +
            "\n\tReturn type: " + method.getReturnType() +
            "\n\tGeneric return type: " + method.getGenericReturnType());
                
        Parameter[] allParams = method.getParameters();
        int i = 1;
        for (Parameter p : allParams) {
            detailsStr.append("\n" + i + ". Parameter type: " + p.getType() +
                "\n\tParameter name: " + p.getName() +
                "\n\tModifiers: " + p.getModifiers() +
                "\n\tIs implicit? " + p.isImplicit() +
                "\n\tIs name present? " + p.isNamePresent() +
                "\n\tIs synthetic? " + p.isSynthetic());
        }
        detailsStr.append("\n<<<<<<<<<<\n");
        
        return detailsStr.toString();
    }
}
