import server.*;

import java.io.IOException;

import client.Client;
import contract.Caller;
import contract.Service;
import helper.ServiceImpl;

/**
 * @author Yevgeny Go
 *
 */
public class Invoker {
    static volatile boolean keepRunning = true; 
    
    /**
     * @param args
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws InterruptedException {
        // Parse input args TODO
        String type = args[0]; // start client or server
                
        if (type.equalsIgnoreCase("server")) {
            startServerInfra(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
        } else {
            System.out.println("Args " + args.length); // TODO
            startClient(args[1], Integer.parseInt(args[2]), args[3], args[4],
                args.length > 5 ? new Object[] {args[5]} : null); // TODO 
        }
    }
    
    /**
     * 
     * @param port
     * @throws FileNotFoundException 
     */
    private static void startServerInfra(int port, int capacity) {
        // Create service objects
        PropHandler props = null;
        try {
            props = new PropHandler("../resources/services.properties"); // TODO
            System.out.println(props);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            System.err.println("ERROR");
            return;
        }
        Service services = new ServiceImpl(props.getProperties());
        services.createObjects(props.getProperties());
        
        // Create a thread pool
        Executor executor = new Executor(capacity);

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
        System.out.println("Service " + service + "." + method + " result: " +
            caller.remoteCall(service, method, parameters));
    }
}
