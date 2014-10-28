package radium.schnitzel.rx;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable.Operator;
import rx.Scheduler;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class BufferUntilTimeoutOperator<T> implements Operator<List<T>, T> {

    private Scheduler scheduler;
    private int duration;
    private TimeUnit unit;

    public BufferUntilTimeoutOperator(int duration, TimeUnit unit, Scheduler scheduler) {
        super();

        this.scheduler = scheduler;
        this.duration = duration;
        this.unit = unit;
    }

    @Override
    public Subscriber<? super T> call(Subscriber<? super List<T>> subscriber) {
        return new BufferUntilTimeoutSubscriber<T>(subscriber, duration, unit, scheduler);
    }

    public BufferUntilTimeoutOperator(int duration, TimeUnit unit) {
        this(duration, unit, Schedulers.computation());
    }

}
