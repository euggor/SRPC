package contract;

import exception.SRPCClientException;

/**
 * @author Yevgeny Go
 *
 */
public interface Caller {
    /**
     * Remote call method
     * 
     * @param serviceName
     * @param methodName
     * @param params
     * @return Object result
     * @throws SRPCClientException 
     */
    public Object remoteCall(String serviceName, String methodName, Object[] params)
        throws SRPCClientException;

    /**
     * Remote call method allowing to explicitly specify session ID
     * 
     * @param sessionId
     * @param serviceName
     * @param methodName
     * @param params
     * @return Object result
     * @throws SRPCClientException
     */
    public Object remoteCall(int sessionId, String serviceName, String methodName, Object[] params)
        throws SRPCClientException;
}
