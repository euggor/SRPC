import server.*;

import java.io.IOException;

//import org.apache.log4j.Logger;

import client.Client;
import contract.Caller;
import contract.Service;
import helper.ServiceImpl;
import exception.SRPCMalformedServiceException;

/**
 * Client/server invocation wrapper
 * 
 * @author Yevgeny Go
 *
 */
public class Invoker {
//    private static final Logger log = Logger.getLogger(Invoker.class);
    
    static volatile boolean keepRunning = true; 
    
    /**
     * @param args
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws InterruptedException {
        // Parse input args TODO
        String type = args[0]; // start client or server
                
        if (type.equalsIgnoreCase("server")) {
            if (args.length > 2) {
                startServerInfra(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
            } else {
                startServerInfra(Integer.parseInt(args[1]), -1);
            }
        } else {
            startClient(args[1], Integer.parseInt(args[2]), args[3], args[4],
                args.length > 5 ? new Object[] {args[5]} : null); // TODO 
        }
    }
    
    /**
     * 
     * @param port
     * @param capacity
     */
    private static void startServerInfra(int port, int capacity) {
        // Create service objects
        PropHandler props = null;
        try {
            props = new PropHandler("../resources/services.properties"); // TODO
//            log.debug(props);
        } catch (IOException ioe) {
            System.err.println(ioe + "\nABORTING");
            return;
        }
        Service services = new ServiceImpl(props.getProperties());
        try {
            services.createObjects(props.getProperties());
        } catch (SRPCMalformedServiceException mse) {
            System.err.println(mse + "\nABORTING");
            return;
        }
        
        // Create a thread pool
        Executor executor = capacity == -1 ?
            new Executor() : new Executor(capacity) ;

        // Start a thread listener
        Listener listener = new Listener(port, executor, services);
        
        // Add shutdown hook
        Thread mainThread = Thread.currentThread();
        Runtime.getRuntime().addShutdownHook(
            new Thread() {
                public void run() {
                    executor.shutdown();
                    listener.shutdown();
                    keepRunning = false;
                    try {
                        mainThread.join();
                        System.out.println("... Invoker done. Number of served clients: " +
                            listener.getClientNumbers());
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
        
        // TODO Never-ending loop
        while(keepRunning) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 
     * @param serverName
     * @param port
     * @param service
     * @param method
     * @param parameters
     */
    private static void startClient(String serverName, int port, String service,
            String method, Object[] parameters) {
        Caller caller = new Client(serverName, port);
        try {
            System.out.println("Service " + service + "." + method + " result: " +
                caller.remoteCall(service, method, parameters));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
