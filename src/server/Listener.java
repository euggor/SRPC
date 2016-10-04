/**
 * 
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import contract.Service;

/**
 * @author Yevgeny Go
 *
 */
public class Listener implements Runnable {
    private static Logger logger = LogManager.getLogger(Listener.class);
    private static final int defaultPort = 2222;
    private volatile boolean keepRunning = true;
    private Thread serverThread;
    private Thread connectionThread;
    private ServerSocket socket;
    private Executor executor;
    private Service services;
    private volatile int num = 0; // number of served clients
    
    /**
     * 
     * @param executor
     * @param services
     * @throws IOException 
     */
    public Listener(Executor executor, Service services) throws IOException {
        this(defaultPort, executor, services);
    }

    /**
     * 
     * @param port
     * @param executor
     * @param services
     * @throws IOException 
     */
    public Listener(int port, Executor executor, Service services)
            throws IOException {
        socket = new ServerSocket(port);
//        socket.setSoTimeout(10000); // milliseconds
        
        this.executor = executor;
        this.services = services;
        
        serverThread = new Thread(this, "Listener");
        serverThread.start();
    }

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        while(this.keepRunning) {
            Socket clientSocket = null;
            try {
                logger.debug("Listening the port " +
                    socket.getLocalPort() + "...");
                clientSocket = socket.accept();
                connectionThread = new Thread(new ClientHandler(clientSocket,
                    executor, services), "ClientHandler");
                connectionThread.start();
                num++;
            } catch (IOException e) {
                logger.info("Socket time out");
            }
        }
        logger.info("Thread exited");
    }

    public void shutdown() {
        this.keepRunning = false;
    }
    
    /**
     * 
     * @return number of served clients
     */
    public int getClientNumbers() {
        return num;
    }
}
