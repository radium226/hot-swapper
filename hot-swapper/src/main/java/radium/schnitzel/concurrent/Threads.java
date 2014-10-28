package radium.schnitzel.concurrent;

public class Threads {

    private Threads() {
        super();
    }

    public static void join(Thread... threads) throws InterruptedException {
        for (Thread thread : threads) {
            thread.join();
        }
    }

    public static void join(ThreadExecutor... executors) throws InterruptedException {
        for (ThreadExecutor executor : executors) {
            executor.join();
        }
    }

}
