package radium.schnitzel;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.*;
import radium.rx.PrintlnSubscriber;
import radium.schnitzel.rx.BufferUntilTimeoutOperator;
import radium.schnitzel.rx.ToStringFunc1;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class TryBufferUntilTimeout {

    public static void main(String[] arguments) {
        Observable<String> wordObservable = Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello");
                sleep(1, SECONDS);
                subscriber.onNext("Word! ");

                sleep(5, SECONDS);

                for (int i = 0; i < 10; i++) {
                    subscriber.onNext("I");
                    sleep(1, MICROSECONDS);
                    subscriber.onNext("Hope");
                    sleep(1, SECONDS);
                    subscriber.onNext("It's");
                    sleep(1, SECONDS);
                    subscriber.onNext("Fine");
                }

                sleep(5, SECONDS);

                subscriber.onNext("Done! ");
                sleep(5, MICROSECONDS);

                subscriber.onCompleted();
            }

        });

        wordObservable
                .lift(new BufferUntilTimeoutOperator<String>(2, SECONDS, Schedulers.computation()))
                .map(new ToStringFunc1<List<String>>())
                .subscribe(PrintlnSubscriber.STANDARD_OUTPUT.prefix("--> "));
    }

    public static void sleep(int duration, TimeUnit unit) {
        try {
            Thread.sleep(unit.toMillis(duration));
        } catch (InterruptedException e) {

        }
    }

}
