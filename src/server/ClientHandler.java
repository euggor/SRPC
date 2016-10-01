/**
 * 
 */
package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

import contract.Request;
import contract.Service;
import exception.SPRCServiceException;
import helper.ResponseImpl; // TODO

/**
 * @author Yevgeny Go
 *
 */
public class ClientHandler implements Runnable {
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
    public void run() {
        System.out.println("-> Accepted Client: " +
            clientSocket.getInetAddress().getHostName());
        
        try {
            System.out.println("Server: connected to "
                + clientSocket.getRemoteSocketAddress());
                
            ObjectInputStream in =
                new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream out =
                    new ObjectOutputStream(clientSocket.getOutputStream());
            while (running) {
                Request request = (Request) in.readObject();
                int id = request.getId();
                System.out.println("Client requested: " + request);
                
                CountDownLatch latch = new CountDownLatch(1); 
                    
                // Invoke the requested service and wait for its finish
                RequestHandler requestHandler = new RequestHandler(request, service, latch);
                executor.executeService(requestHandler);
                try { 
                    latch.await(); 
                } catch (InterruptedException exc) { // TODO
                    System.out.println(exc); 
                }
                out.writeObject(new ResponseImpl(id, requestHandler.getResult()));
                
                if (!request.hasNext()) {
                    running = false;
                    System.out.println("<- Stopping thread for the client " + id);
                    clientSocket.close();
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SPRCServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
