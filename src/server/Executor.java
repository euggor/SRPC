/**
 * 
 */
package server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Yevgeny Go
 *
 */
public class Executor {
    private static Logger logger = LogManager.getLogger(Executor.class);
    private static final int capacity = 10;
    private ExecutorService eService;
    
    /**
     * Default executor pool capacity
     */
    public Executor() {
        this(capacity);
    }

    /**
     * 
     * @param poolCapacity
     */
    public Executor(int poolCapacity) {
        this.eService = Executors.newFixedThreadPool(poolCapacity);
        logger.info("Executing pool of " + poolCapacity + " threads created"); 
    }

    /**
     * 
     * @param service
     */
    public void executeService(RequestHandler service) {
        eService.execute(service); 
    }
    
    public void shutdown() {
        eService.shutdown();
        try {
            // Wait a while for existing tasks to terminate
            if (!eService.awaitTermination(60, TimeUnit.SECONDS)) {
                eService.shutdownNow(); // Cancel currently executing tasks
            // Wait a while for tasks to respond to being cancelled
            if (!eService.awaitTermination(60, TimeUnit.SECONDS))
                logger.warn("Executing pool did not terminated");
            }
        } catch (InterruptedException ie) {
            // (Re-)Cancel if current thread also interrupted
            eService.shutdownNow();
            
            // Preserve interrupt status
            Thread.currentThread().interrupt();
        }
        
        logger.info("Executing pool shutdown completed");
    }
}
