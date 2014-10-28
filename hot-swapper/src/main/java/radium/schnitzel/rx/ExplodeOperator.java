package radium.schnitzel.rx;

import java.util.List;

import rx.Observable;
import rx.Observable.Operator;
import rx.Observable.Transformer;
import rx.Subscriber;

public class ExplodeOperator<T> implements Operator<T, List<T>> {

    public ExplodeOperator() {
        super();
    }

    @Override
    public Subscriber<? super List<T>> call(final Subscriber<? super T> subscriber) {
        return new Subscriber<List<T>>() {

            @Override
            public void onCompleted() {
                subscriber.onCompleted();
            }

            @Override
            public void onError(Throwable throwable) {
                subscriber.onError(throwable);
            }

            @Override
            public void onNext(List<T> items) {
                for (T item : items) {
                    subscriber.onNext(item);
                }
            }
        };
    }

}
