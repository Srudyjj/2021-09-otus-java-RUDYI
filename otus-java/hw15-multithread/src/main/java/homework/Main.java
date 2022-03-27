package homework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);
    private int count = 1;
    private int i = 1;
    private int last = 1;

    public static void main(String[] args) {
        log.info("Hello");
        var main = new Main();
        new Thread(main::count).start();
        new Thread(main::count).start();
    }

    private synchronized void count() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                while(i < 20) {
                    log.info(Thread.currentThread().getName() + ":" + count);

                    while (last == count) {
                        wait();
                    }

                    if (i < 10) {
                        count++;
                    } else {
                        count--;
                    }
                    i++;
                    last = count;
                    sleep();
                    notifyAll();
                }
            } catch (InterruptedException ex) {
                log.error(ex.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }

    private static void sleep() {
        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
