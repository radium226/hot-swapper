package radium.schnitzel.rx;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Scheduler;
import rx.functions.Action0;
import rx.Subscriber;
import rx.Subscription;

public class BufferUntilTimeoutSubscriber<T> extends Subscriber<T> {

    private Subscriber<? super List<T>> subscriber;
    private Scheduler scheduler;
    private List<T> buffer;

    private int duration;
    private TimeUnit unit;

    private Subscription workerSubscription;

    private boolean completed = false;

    public BufferUntilTimeoutSubscriber(Subscriber<? super List<T>> subscriber, int duration, TimeUnit unit, Scheduler scheduler) {
        super();
        this.subscriber = subscriber;
        this.scheduler = scheduler;
        this.duration = duration;
        this.unit = unit;

        this.buffer = new ArrayList<T>();
    }

    @Override
    public void onCompleted() {
        synchronized (this) {
            cancelSchedule();
            emitBuffer();
            subscriber.onCompleted();
        }
    }

    @Override
    public void onError(Throwable throwable) {
        cancelSchedule();
        subscriber.onError(throwable);
    }

    @Override
    public void onNext(T t) {
        synchronized (this) {
            cancelSchedule();
            buffer.add(t);
            createSchedule(new Action0() {

                @Override
                public void call() {
                    synchronized (this) {
                        cancelSchedule();
                        emitBuffer();
                    }
                }
            });
        }

    }

    public void createSchedule(Action0 action) {
        workerSubscription = scheduler.createWorker().schedule(action, duration, unit);
    }

    public void cancelSchedule() {
        if (workerSubscription != null) {
            workerSubscription.unsubscribe();
            workerSubscription = null;
        }
    }

    public void emitBuffer() {
        if (!buffer.isEmpty()) {
            subscriber.onNext(buffer);
            buffer = new ArrayList<T>();
        }
    }

}
