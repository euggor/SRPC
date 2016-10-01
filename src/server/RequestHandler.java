/**
 * 
 */
package server;

import java.util.concurrent.CountDownLatch;

import contract.Request;
import contract.Service;
import exception.SPRCServiceException;
import exception.SPRCServiceMethodNotFoundException;
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
     * @param latch 
     * @param requst
     */
    public RequestHandler(Request request, Service service, CountDownLatch latch) {
        this.request = request;
        this.service = service;
        this.latch = latch;
    }

    /**
     * 
     */
    public void run() {
        // TODO Auto-generated method stub
        System.out.println("RequestHandler: " + request + " service: " + service);
        
        if (service.isServiceExist(request.getServiceName())) {
            if (service.isMethodExist(request.getServiceName(),
                request.getMethodName(), request.getParameters())) {
                result = service.invokeMethod(request.getServiceName(),
                    request.getMethodName(), request.getParameters());
            } else { // Method not found
                System.out.println("RequestHandler: not existing service method for " + request);
                result = new SPRCServiceMethodNotFoundException("Method " + request.getServiceName() +
                    "." + request.getMethodName() + " not found");
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
    
    public Object getResult() throws SPRCServiceException {
        if (!ready) {
            throw new SPRCServiceException("Result not ready for the service " +
                request.getServiceName() + "." + request.getMethodName() +
                "(" + request.getParameters() + ")");
        }
        return result;
    }
}
