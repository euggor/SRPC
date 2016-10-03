/**
 * 
 */
package tests.client;

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
//        private Logger logger = Logger.getLogger(Caller.class);
        private Client c;
    
        public Caller(Client c) {
            this.c = c;
        }
    
        @Override
        public void run() {
            while(true) {
                try {
                    c.remoteCall("test", "sleep", new Object[] {new Long(1000)});
//                    logger.info("Current Date is:" + c.remoteCall("service1", "getCurrentDate", new Object[]{}));
                    System.out.println("Current Date is: " + c.remoteCall("test", "getCurrentDate", new Object[]{}));
                } catch (SRPCClientException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
}
