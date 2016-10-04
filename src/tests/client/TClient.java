/**
 * 
 */
package tests.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import client.Client;
import exception.SRPCClientException;

/**
 * A simple multi-threaded test implementing a customer's checking scenario
 * 
 * @author Yevgeny Go
 */
public class TClient {
    /**
     * @param args
     */
    public static void main(String[] args) {
        Client client = new Client(args[0], args[1]);
        for (int i=0; i<10; i++) {
            new Thread(new Caller(client)).start();
        }
    }

    private static class Caller implements Runnable {
        private static Logger logger = LogManager.getLogger(Caller.class);
        private int sessionId = (int) (Math.random()*10000) + 1;
        private Client c;
    
        public Caller(Client c) {
            this.c = c;
        }
    
        @Override
        public void run() {
            while(true) {
                try {
                    c.remoteCall(sessionId, "test", "sleep", new Object[] {new Long(1000)});
//                    c.remoteCall("test", "sleep", new Object[] {new Long(1000)});
                    logger.info("Current Date is:" + c.remoteCall("test", "getCurrentDate", new Object[]{}));
                } catch (SRPCClientException e) {
                    logger.error("Catch:", e);
                }
            }
        }
    }
}
