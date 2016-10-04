import server.*;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    private static Logger logger = LogManager.getLogger(Invoker.class);
    private static final String propsFile = "../resources/services.properties"; // TODO: hard-coded value
    static volatile boolean keepRunning = true; 
    
    /**
     * @param args
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws InterruptedException {
        // TODO: graceful parsing of input arguments
        String type = args[0]; // start client or server       
        if (type.equalsIgnoreCase("server")) {
            switch (args.length) {
            case 3:
                startServerInfra(propsFile, Integer.parseInt(args[1]), Integer.parseInt(args[2]));
                break;
            case 2:
                startServerInfra(propsFile, Integer.parseInt(args[1]), -1);
                break;
            default:
                startServerInfra(propsFile, -1, -1);
                break;
            }
        } else {
            startClient(args[1], Integer.parseInt(args[2]), args[3], args[4],
                args.length > 5 ? new Object[] {args[5]} : null); 
        }
    }
    
    /**
     * 
     * @param propertyFile
     * @param port
     * @param capacity
     */
    private static void startServerInfra(String propertyFile, int port, int capacity) {
        // Create service objects
        PropHandler props = null;
        try {
            props = new PropHandler(propertyFile);
            logger.debug(props);
        } catch (IOException ioe) {
            logger.warn("Wrong server configuration: " + ioe.getMessage());
            return;
        }
        Service services = new ServiceImpl(props.getProperties());
        try {
            services.createObjects(props.getProperties());
        } catch (SRPCMalformedServiceException mse) {
            logger.warn("Wrong server configuration: " + mse.getMessage());
            return;
        }
        
        // Create an executing pool
        Executor executor = capacity == -1 ?
            new Executor() : new Executor(capacity) ;

        // Start a socket listener
        Listener listener;
        try {
            listener = port == -1 ?
                new Listener(executor, services) :
                new Listener(port, executor, services);
        } catch (IOException ioe) {
            logger.error("Unable to start a socket listener: " + ioe.getMessage());
            return;
        }
        
        // Add a shutdown hook
        Thread mainThread = Thread.currentThread();
        Runtime.getRuntime().addShutdownHook(
            new Thread() {
                public void run() {
                    executor.shutdown();
                    listener.shutdown();
                    keepRunning = false;
                    try {
                        mainThread.join();
                        logger.info("Main server thread done. Number of served clients: " +
                            listener.getClientNumbers());
                    } catch (InterruptedException e) {
                        logger.warn("Shutdown hook catch:", e);
                    }
                }
            });
        
        // Never-ending loop for main thread
        while(keepRunning) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                logger.warn("Main server thread catch:", e);
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
            logger.info("Service " + service + "." + method + " result: " +
                caller.remoteCall(service, method, parameters));
        } catch (Exception e) {
            logger.error("Client catch:", e);
        }
    }
    
/*    private static void printCP() {
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        URL[] urls = ((URLClassLoader) cl).getURLs();
        for (URL url: urls) {
            logger.debug(url.getFile());
        }
    }*/
}
