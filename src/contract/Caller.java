/**
 * 
 */
package contract;

/**
 * @author Yevgeny Go
 *
 */
public interface Caller {
    /**
     * 
     * @param serviceName
     * @param methodName
     * @param params
     * @return
     */
    public Object remoteCall(String serviceName, String methodName, Object[] params);
}
