package radium.schnitzel;

import static radium.rx.RX.rx;

import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.util.List;
import java.util.concurrent.TimeUnit;

import radium.commons.DistinctFunction;
import radium.commons.FilterFunction;
import radium.commons.IsEmptyPredicate;
import radium.commons.MatchFileNamePredicate;
import radium.schnitzel.concurrent.ThreadExecutor;
import radium.schnitzel.rx.BufferUntilTimeoutOperator;
import radium.schnitzel.rx.WatchEventContextFunc1;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import com.google.common.base.Predicates;
import radium.maven.Maven;

public class TrySchitzel {

    final public static int TIMEOUT = 2;
    final public static String SOURCES = "/home/adrien/Projects/By Name/hot-swapper/webapp/src";
    final public static String POM = "/home/adrien/Projects/By Name/hot-swapper/webapp/pom.xml";

    public static void main(String[] arguments) throws Throwable {

        ThreadExecutor sourcesWatcherThreadExecutor = new ThreadExecutor();

        Observable<WatchEvent<Path>> sourcesWatcherObservable = WatcherObservable.create(SOURCES);

        sourcesWatcherObservable
                .map(new WatchEventContextFunc1<Path>()) // WatchEvent<Path> to Path
                .lift(new BufferUntilTimeoutOperator<Path>(TIMEOUT, TimeUnit.SECONDS)) // We buffer the events until nothing happend during 1 entire second. 
                .map(rx(new DistinctFunction<Path>())) // We keep only distinct Path
                .map(rx(new FilterFunction<>(Predicates.not(new MatchFileNamePredicate("^.*\\.class$"))))) // We filter the .class files
                .filter(rx(Predicates.not(new IsEmptyPredicate<Path>())))
                //.subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.from(sourcesWatcherThreadExecutor))
                .subscribe(new Subscriber<List<Path>>() {

                    private Maven.Run compileRun;
                    
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace(System.err);
                    }

                    @Override
                    public void onNext(List<Path> paths) {
                        if (compileRun != null && !compileRun.isTerminated()) {
                            compileRun.terminate();
                        }
                        
                        System.out.println(Thread.currentThread().getName());
                        
                        System.out.println(Thread.currentThread().isInterrupted());
                        System.out.println("Before running Maven");
                        compileRun = Maven.forPOM(POM).run("compile");
                        
                    }

                });

        System.out.println(" ===>");
        sourcesWatcherThreadExecutor.join();
        /*try {
            Thread.sleep(60000 * 5);
        } catch (InterruptedException e) {
            
        }*/
        System.out.println(" ------> ");
    }

}
