package homework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);
    private int count = 1;
    private int i = 1;
    private boolean skip = true;

    public static void main(String[] args) throws Exception {
        var main = new Main();
        var t1 = new Thread(main::count);
        var t2 = new Thread(main::count);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }

    private synchronized void count() {
        try {
            while (i < 20 && !Thread.currentThread().isInterrupted()) {
                sleep();
                log.info(Thread.currentThread().getName() + ":" + count);

                if (skip) {
                    skip = false;
                    continue;
                } else if (i < 10) {
                    count++;
                } else {
                    count--;
                }
                i++;
                skip = true;
                notify();
                wait();
            }
        } catch (InterruptedException ex) {
            log.error(ex.getMessage());
            Thread.currentThread().interrupt();
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
