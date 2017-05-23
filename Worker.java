import java.util.concurrent.CountDownLatch;
import java.util.Random;

public class Worker implements Runnable {

    private CountDownLatch countDownLatch;
    private String taskName;

    public Worker(CountDownLatch countDownLatch, String taskName) {
        this.countDownLatch = countDownLatch;
        this.taskName = taskName;
    }
 
    @Override
    public void run() {
        try {
            Thread.sleep(getRandomSeconds()); // sleep random time to simulate long running task
            System.out.println("Counting down:" + Thread.currentThread().getName() + " task: " + this.taskName);
             this.countDownLatch.countDown();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    // returns a long between 0 and 9999
    private long getRandomSeconds() {
        Random generator = new Random();
        return Math.abs(generator.nextLong() % 10000);
    }
}
