package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import contract.Caller;
import contract.Request;
import contract.Response;
import contract.VoidResponse;
import helper.RequestImpl;
import exception.SRPCClientException;
import exception.SRPCServiceException;

/**
 * @author Yevgeny Go
 *
 */
public class Client implements Caller {
    private static Logger logger = LogManager.getLogger(Client.class);
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
    @Override
    public Object remoteCall(String serviceName, String methodName, Object[] params)
            throws SRPCClientException {
        Response res = null;
        
        try {
            Socket client = new Socket(serverName, port);
            logger.info("-> " + sessionId + ": connected to "  +
                serverName + ", " + port + ", " + client.getRemoteSocketAddress());
                        
            ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(client.getInputStream());

            Request req = new RequestImpl(sessionId, serviceName, methodName, params, false);
            logger.debug(sessionId + ": requested: " + req);
            out.writeObject(req);
            
            res = (Response) in.readObject();
            logger.debug(sessionId + ": server responded: " + res);
            
            client.close();

            if (res.getServiceResult() instanceof SRPCServiceException) {
                throw new SRPCClientException("Remote call failed: " + res.getServiceResult());
            }
            if (res instanceof VoidResponse) {
                logger.info("Void call (no value returned)");
            }
        } catch(IOException e) {
            logger.error("Catch: ", e);
            throw new SRPCClientException("Remote call failed: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            logger.error("Catch: ", e);
            throw new SRPCClientException("Remote call failed: " + e.getMessage());
        }
        
        Object result = res.getServiceResult();
        logger.info("<- " + sessionId + " exiting with result " + result);
        return result;
    }

    /**
     * @param sessionId
     * @param serviceName
     * @param methodName
     * @param params
     * @return remote call result object
     * @throws SRPCClientException 
     */
    @Override
    public Object remoteCall(int sessionId, String serviceName, String methodName, Object[] params)
            throws SRPCClientException {
        this.sessionId = sessionId;
        return remoteCall(serviceName, methodName, params);
    }
}
