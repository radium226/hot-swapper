package radium.rx;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Func1;
import rx.observables.ConnectableObservable;
import rx.observables.GroupedObservable;
import rx.schedulers.Schedulers;

public class TryConnectableObservable {

    public static void main(String[] arguments) {

        ConnectableObservable<Integer> observableCounter = Observable.create(new CountOnSubscribe())
                .subscribeOn(Schedulers.newThread())
                .publish();

        observableCounter.connect();

        sleep(2, TimeUnit.SECONDS);

        System.out.println(" ----- ");

        observableCounter
                .map(IntegerToStringFunc1.INSTANCE)
                .observeOn(Schedulers.newThread())
                .subscribe(PrintlnSubscriber.STANDARD_OUTPUT.prefix("println1"));

        observableCounter
                .filter(KeepEvenIntegerFunc1.INSTANCE)
                .map(IntegerToStringFunc1.INSTANCE)
                .observeOn(Schedulers.newThread())
                .subscribe(PrintlnSubscriber.STANDARD_OUTPUT.prefix("println2"));

        sleep(10, TimeUnit.MINUTES);
    }

    public static void sleep(int duration, TimeUnit unit) {
        try {
            Thread.sleep(unit.toMillis(duration));
        } catch (InterruptedException e) {

        }
    }

}
