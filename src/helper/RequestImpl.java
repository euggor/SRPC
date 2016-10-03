/**
 * 
 */
package helper;

import contract.Request;

/**
 * @author Yevgeny Go
 *
 */
public class RequestImpl implements Request {
    private static final long serialVersionUID = 1L;

    private int id;
    private String serviceName;
    private String methodName;
    private Object[] parameters;
    private boolean hasNext;

    /**
     * 
     * @param id
     * @param service
     * @param method
     * @param parameters
     */
    public RequestImpl(int id, String service, String method,
            Object[] parameters, boolean hasNext) {
        this.id = id;
        this.serviceName = service;
        this.methodName = method;
        this.parameters = parameters;
        this.hasNext = hasNext;
    }

    /* (non-Javadoc)
     * @see contract.Request#getId()
     */
    @Override
    public int getId() {
        return id;
    }

    /* (non-Javadoc)
     * @see contract.Request#getServiceName()
     */
    @Override
    public String getServiceName() {
        return serviceName;
    }

    /* (non-Javadoc)
     * @see contract.Request#getMethodName()
     */
    @Override
    public String getMethodName() {
        return methodName;
    }

    /* (non-Javadoc)
     * @see contract.Request#getParameters()
     */
    @Override
    public Object[] getParameters() {
        return parameters;
    }

    @Override
    public boolean hasNext() {
        return hasNext;
    }
    
    @Override
    public String toString() {
        return "Session id: " + getId() +
            " service: " + getServiceName() + "." + getMethodName() +
            "; " + (hasNext() ? "bunch of requests" : "a single request");
    }
}
