import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WorkManager {

    private CountDownLatch countDownLatch;
    private static final int NUMBER_OF_TASKS = 5;
    public ExecutorService executorService;

    public WorkManager() {
        countDownLatch = new CountDownLatch(NUMBER_OF_TASKS);
    }

    public void finishWork() {
        try {
            System.out.println("START WAITING");
            countDownLatch.await();
            System.out.println("DONE WAITING");
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public void startWork() {
        for (int i = 0; i < NUMBER_OF_TASKS; i++) {
            Worker worker = new Worker(countDownLatch, String.valueOf(i));
            executorService.execute(worker);
        }
        executorService.shutdown();
    }

    public void addMoreWork() {
        if (executorService.isShutdown()){
            System.out.println("executor is shutdown");
            return;
        }

        Worker worker = new Worker(countDownLatch, String.valueOf(5));
        executorService.execute(worker);
    }

    public static void main(String[] args) {
        WorkManager workManager = new WorkManager();
        workManager.executorService = Executors.newSingleThreadExecutor();
        System.out.println("START WORK");
        workManager.startWork();
        System.out.println("WORK STARTED");
        workManager.finishWork();
        System.out.println("FINISHED WORK");
        workManager.addMoreWork();
    }

}
