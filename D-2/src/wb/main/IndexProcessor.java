package wb.main;

import java.util.logging.Logger;

public class IndexProcessor implements Runnable {

//	private static final Logger LOGGER = LoggerFactory.getLogger(IndexProcessor.class);
    private volatile boolean running = true;

    public void terminate() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            try {
//                LOGGER.debug("Sleeping...");
            	
            	System.out.println("Sleeping...: \t" + Thread.currentThread().getName());
            	
                Thread.sleep((long) 1000);
//                Thread.sleep((long) 15000);

//                LOGGER.debug("Processing");
            } catch (InterruptedException e) {
//                LOGGER.error("Exception", e);
                running = false;
            }
        }

    }
}
