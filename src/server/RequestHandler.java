/**
 * 
 */
package server;

import java.util.concurrent.CountDownLatch;

import contract.Request;
import contract.Service;
import exception.SRPCServiceException;
import exception.SRPCServiceMethodNotFoundException;
import exception.SRPCServiceMethodWrongParameterException;
import exception.SRPCServiceNotFoundException;

/**
 * @author Yevgeny Go
 *
 */
public class RequestHandler implements Runnable {
    private Request request;
    private Service service;
    private CountDownLatch latch;
    private volatile boolean ready = false;
    private Object result;
    
    /**
     * 
     * @param request
     * @param service
     * @param latch
     */
    public RequestHandler(Request request, Service service, CountDownLatch latch) {
        this.request = request;
        this.service = service;
        this.latch = latch;
    }

    @Override
    public void run() {
        System.out.println("RequestHandler: " + request + " service: " + service);
        
        if (service.isServiceExist(request.getServiceName())) {
            if (service.isMethodExist(request.getServiceName(),
                    request.getMethodName(), request.getParameters())) { // the method found
                result = service.invokeMethod(request.getServiceName(),
                    request.getMethodName(), request.getParameters());
                
                if (service.isMethodVoid(request.getServiceName(),
                        request.getMethodName(), request.getParameters())) {
                    System.out.println("VOID"); // TODO
                    result = null; // explicitly set a null result for a void method
                }
            } else { // the method not found, check other possible signatures
                if (service.isMethodExist(request.getServiceName(),
                        request.getMethodName())) { // other signature found
                    System.out.println("RequestHandler: wrong parameters of the service method for " + request);
                    result = new SRPCServiceMethodWrongParameterException("Attempt to invoke method " + request.getServiceName() +
                        "." + request.getMethodName() + " with wrong parameters");
                } else { // other signature not found
                    System.out.println("RequestHandler: not existing service method for " + request);
                    result = new SRPCServiceMethodNotFoundException("Method " + request.getServiceName() +
                        "." + request.getMethodName() + " not found");
                }
            }
        } else { // Service not found
            System.out.println("RequestHandler: not existing service for " + request);
            result = new SRPCServiceNotFoundException("Service " +
                request.getServiceName() + " not found");
        }
        System.out.println("RequestHandler: result: " + result);
/*        try {
            Thread.sleep(3000); // commented out: test code just to check sync
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } */
        
        ready = true;
        latch.countDown(); // notify invoker that the service is done
    }
    
    /**
     * 
     * @return Object result
     * @throws SRPCServiceException
     */
    public Object getResult() throws SRPCServiceException {
        if (!ready) {
            throw new SRPCServiceException("Result not ready for the service " +
                request.getServiceName() + "." + request.getMethodName() +
                "(" + request.getParameters() + ")");
        }
        return result;
    }
}
