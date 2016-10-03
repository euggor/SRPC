/**
 * 
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import contract.Service;

/**
 * @author Yevgeny Go
 *
 */
public class Listener implements Runnable {
    private volatile boolean keepRunning = true;
    private Thread serverThread;
    private Thread connectionThread;
    private ServerSocket socket;
    private Executor executor;
    private Service services;
    private volatile int num = 0; // number of served clients
    
    /**
     * 
     * @param port
     * @param executor
     * @param services
     */
    public Listener(int port, Executor executor, Service services) {
        try {
            socket = new ServerSocket(port);
//            socket.setSoTimeout(100000); // milliseconds
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        this.executor = executor;
        this.services = services;
        
        serverThread = new Thread(this, "ServerRunner");
        serverThread.start();
    }

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    public void run() {
        System.out.println("ClientHandler running");
        while(this.keepRunning) {
            Socket clientSocket = null;
            try {
                System.out.println("Server: waiting for client on port " +
                    socket.getLocalPort() + "...");
                clientSocket = socket.accept();
                connectionThread = new Thread(new ClientHandler(clientSocket,
                    executor, services),
                    "ClientHandler");
                connectionThread.start();
                num++;
            } catch (IOException e) {
                System.out.println("Server: time out");
            }
        }
        System.out.println("ClientHandler's thread exited");
    }

    public void shutdown() {
        System.out.println("... ClientHandler done");
        this.keepRunning = false;
    }
    
    /**
     * 
     * @return int number of served clients
     */
    public int getClientNumbers() {
        return num;
    }
}
