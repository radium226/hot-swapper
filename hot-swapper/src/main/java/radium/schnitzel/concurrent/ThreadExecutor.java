package radium.schnitzel.concurrent;

import java.util.Arrays;
import java.util.concurrent.Executor;

public class ThreadExecutor implements Executor {

    private Thread thread;

    public ThreadExecutor() {
        super();
    }

    @Override
    public void execute(Runnable runnable) {
        thread = new Thread(runnable) {
            
            @Override
            public void interrupt() {
                System.out.println("!!!!!");
                
                StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
                System.out.println(Arrays.toString(stackTraceElements));
                
                super.interrupt();
            }
            
        };
        thread.start();
    }

    public Thread getThread() {
        if (thread == null) {
            throw new IllegalStateException("Nothing has been run. ");
        }

        return thread;
    }

    public void join() throws InterruptedException {
        getThread().join();
    }

}
