package homework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Counter {

    private static final Logger log = LoggerFactory.getLogger(Counter.class);
    private int count = 1;
    private int i = 1;
    private int last = 1;

    public synchronized void count() {
        try {
            while(i < 20) {
                log.info(Thread.currentThread().getName() + ":" + count);
                if (i < 10) {
                    count++;
                } else {
                    count--;
                }
                i++;
                wait();
            }
        } catch (InterruptedException ex) {
            log.error(ex.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
