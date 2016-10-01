package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import contract.Caller;
import contract.Request;
import contract.Response;
import exception.SPRCServiceException;
import helper.RequestImpl;

/**
 * @author Yevgeny Go
 *
 */
public class Client implements Caller {
    private String serverName;
    private int port;
    private int sessionId = (int) (Math.random()*10000) + 1;
    
    public Client(String serverName, int port) {
        this.serverName = serverName;
        this.port = port;
    }

    /**
     * 
     * @param serviceName
     * @param methodName
     * @param params
     * @return remote call result object
     */
    public Object remoteCall(String serviceName, String methodName, Object[] params) {
        Response res = null;
        
        try {
            System.out.println("Client: connecting to server " + serverName +
                " on port " + port);
            Socket client = new Socket(serverName, port);
            System.out.println("Client: connected to " 
                + client.getRemoteSocketAddress());
            
            Request req = new RequestImpl(sessionId, serviceName, methodName, params, false);
            
            ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(client.getInputStream());

            System.out.println("Client: requested : " + req);
            out.writeObject(req);
            res = (Response) in.readObject();
            System.out.println("Client: <- The server says: " + res
            + "\nClient: exiting.");
            if (res.getServiceResult() instanceof SPRCServiceException) {
                System.out.println("WARNING: Call failed");
            }
            
            client.close();
        } catch(IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        return res.getServiceResult();
    }
}
