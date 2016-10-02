package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import contract.Caller;
import contract.Request;
import contract.Response;
import contract.VoidResponse;
import exception.SRPCClientException;
import exception.SRPCServiceException;
import helper.RequestImpl;

/**
 * @author Yevgeny Go
 *
 */
public class Client implements Caller {
    private String serverName;
    private int port;
    private int sessionId = (int) (Math.random()*10000) + 1;
    
    /**
     * 
     * @param serverName
     * @param port
     */
    public Client(String serverName, int port) {
        this.serverName = serverName;
        this.port = port;
    }

    /**
     * 
     * @param serverName
     * @param port
     */
    public Client(String serverName, String port) {
        this.serverName = serverName;
        this.port = Integer.parseInt(port);
    }

    /**
     * 
     * @param serviceName
     * @param methodName
     * @param params
     * @return remote call result object
     * @throws SRPCClientException 
     */
    public Object remoteCall(String serviceName, String methodName, Object[] params) throws SRPCClientException {
        Response res = null;
        
        try {
            Socket client = new Socket(serverName, port);
            System.out.println("Client: connected to "  + serverName + ", " + port + 
                ", " + client.getRemoteSocketAddress());
                        
            ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(client.getInputStream());

            Request req = new RequestImpl(sessionId, serviceName, methodName, params, false);
            System.out.println("Client: requested : " + req);
            out.writeObject(req);
            
            res = (Response) in.readObject();
            System.out.println("Client: server responded: " + res);
            
            client.close();

            if (res.getServiceResult() instanceof SRPCServiceException) {
                throw new SRPCClientException("Remote call failed: " + res.getServiceResult());
            }
            if (res instanceof VoidResponse) {
                System.out.println("Void call (no value returned)");
            }
        } catch(IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        return res.getServiceResult();
    }
}
