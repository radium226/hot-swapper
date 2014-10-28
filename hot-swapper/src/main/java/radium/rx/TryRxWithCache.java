package radium.rx;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

public class TryRxWithCache {

    public static class UnlockLockAction0 implements Action0 {

        private Lock lock;

        public UnlockLockAction0(Lock lock) {
            super();

            this.lock = lock;
        }

        @Override
        public void call() {
            lock.unlock();
        }

    }

    public static void main(String[] arguments) throws Throwable {

        final Lock lock = new ReentrantLock();

        Observable<Integer> observable = Observable.create(new CountOnSubscribe());

        Subscription secondSubscription = observable.map(IntegerToStringFunc1.INSTANCE).doOnCompleted(new UnlockLockAction0(lock)).subscribe(PrintlnSubscriber.STANDARD_OUTPUT.prefix("println2"));
        Subscription firstSubscription = observable.map(IntegerToStringFunc1.INSTANCE).doOnCompleted(new UnlockLockAction0(lock)).subscribe(PrintlnSubscriber.STANDARD_OUTPUT.prefix("println1"));

        lock.lock();
        Subscription[] subscriptions = new Subscription[]{firstSubscription, secondSubscription};

        while (lock.tryLock()) {
            for (Subscription subscription : subscriptions) {
                if (!subscription.isUnsubscribed()) {
                    break;
                }
            }
        }

    }

}
