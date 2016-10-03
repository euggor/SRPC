/**
 * 
 */
package server;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import exception.SRPCMalformedServiceException;

/**
 * @author Yevgeny Go
 *
 */
public class ReflectHandler {
//    private static final String format = "%24s: %s%n";
    
    /**
     * Create an object from the class name
     * 
     * @param className
     * @return Object created object
     * @throws SRPCMalformedServiceException 
     */
    public static Object createObject(String className) throws SRPCMalformedServiceException {
        System.out.println("Creating object from class name " + className);
        
        Object obj = null;
        try {
            Class<?> clazz = Class.forName(className);
            Constructor<?> ctor = clazz.getConstructor(); // TODO: No parameters in constructor
            System.out.println("Constructor " + ctor); // TODO
            obj = ctor.newInstance(); // TODO constructor parameters
            System.out.println("Created service object: " + obj);
        } catch (InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException
                | ClassNotFoundException | NoSuchMethodException
                | SecurityException e) {
            throw new SRPCMalformedServiceException("Unable to create new instance from the object: "
                + e);
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
        System.out.println("Getting method from " + className + "." + methodName);

        Class<?> clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
                     if (params.length == methodParameters.length) { // TODO
//                         printMethod(m);
                         System.out.println("Method " + className + "." +
                             methodName + "(" + params + ") found!"); // TODO
                         method = m;
                         break;
                     }
                 } else {
                     if (params.length == 0) { // method with no parameters
//                         printMethod(m);
                         System.out.println("Method " + className + "." +
                                 methodName + "() found!"); // TODO
                         method = m;
                         break;
                     }
                 }
             }
        }
        
        if (method == null) {
            System.out.println("Method " + className + "." +
                 methodName + " not found!");
        }
        return method;
    }

/*    private static void printMethod(Method method) { // TODO
        System.out.format("Name %s%n", method.getName());
        System.out.format("Generic %s%n", method.toGenericString());
        System.out.format(format, "Return type", method.getReturnType());
        System.out.format(format, "Generic return type", method.getGenericReturnType());
                
        Parameter[] allParams = method.getParameters();
        for (Parameter p : allParams) {
            System.out.format(format, "Parameter class", p.getType());
            System.out.format(format, "Parameter name", p.getName());
            System.out.format(format, "Modifiers", p.getModifiers());
            System.out.format(format, "Is implicit?", p.isImplicit());
            System.out.format(format, "Is name present?", p.isNamePresent());
            System.out.format(format, "Is synthetic?", p.isSynthetic());
        }
    }*/
}
