/**
 * 
 */
package server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Yevgeny Go
 *
 */
public class Executor {
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
        System.out.println("Creating pool of " + poolCapacity + " threads"); 
        this.eService = Executors.newFixedThreadPool(poolCapacity);
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
                System.err.println("WARNING: Executor pool did not terminate");
            }
        } catch (InterruptedException ie) {
            // (Re-)Cancel if current thread also interrupted
            eService.shutdownNow();
            // Preserve interrupt status
            Thread.currentThread().interrupt();
        }
        
        System.out.println("... Executor pool terminated"); // TODO
    }
}
