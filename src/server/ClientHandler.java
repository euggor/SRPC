/**
 * 
 */
package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.CountDownLatch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import contract.Request;
import contract.Service;
import helper.ResponseImpl;
import helper.VoidResponseImpl;
import exception.SRPCServiceException;

/**
 * @author Yevgeny Go
 *
 */
public class ClientHandler implements Runnable {
    private static Logger logger = LogManager.getLogger(ClientHandler.class);
    private Socket clientSocket;
    private Executor executor;
    private Service service;
    private boolean running = true;
    
    /**
     * 
     * @param clientSocket
     * @param executor
     * @param service
     */
    public ClientHandler(Socket clientSocket, Executor executor, Service service) {
        this.clientSocket = clientSocket;
        this.executor = executor;
        this.service = service;
    }

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        logger.info("-> Accepted the client: " +
            clientSocket.getInetAddress().getHostName() + " [" +
            clientSocket.getRemoteSocketAddress() + "]");
        
        try {
            ObjectInputStream in =
                new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream out =
                    new ObjectOutputStream(clientSocket.getOutputStream());
            while (running) {
                Request request = (Request) in.readObject();
                int id = request.getId();
                logger.debug("The client " + id + " requested: " + request);
                
                CountDownLatch latch = new CountDownLatch(1); 
                    
                // Invoke the requested service and wait for its finish
                RequestHandler requestHandler = new RequestHandler(request, service, latch);
                executor.executeService(requestHandler);
                try { 
                    latch.await(); 

                    // Get the service result
                    try {
                        if (requestHandler.getResult() == null) { // a void method
                            out.writeObject(new VoidResponseImpl(id));
                        } else {
                            out.writeObject(new ResponseImpl(id, requestHandler.getResult()));
                        }

                        if (!request.hasNext()) {
                            running = false;
                        }
                    } catch (SocketException se) {
                        running = false;
                        logger.warn("Connection with the client " + id +
                            " aborted: " + se.getMessage());
                    }
                } catch (InterruptedException exc) {
                    running = false;
                    logger.warn("Unable to get service result for the client " +
                        id + ": " + exc.getMessage());
                }
                
                if (!running) {
                    logger.info("<- Closing thread for the client " + id);
                }
            }

            clientSocket.close();
        } catch (IOException e) {
            logger.warn("Unable to establish connection: " + e.getMessage());
        } catch( ClassNotFoundException | SRPCServiceException e) {
            logger.error("Catch: ", e);
        }
    }
}
