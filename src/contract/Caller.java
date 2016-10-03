package contract;

import exception.SRPCClientException;

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
     * @return Object result
     * @throws SRPCClientException 
     */
    public Object remoteCall(String serviceName, String methodName, Object[] params) throws SRPCClientException;
}
